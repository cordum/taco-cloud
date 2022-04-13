package tacos.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.asm.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tacos.Ingredient;
import tacos.IngredientRef;
import tacos.Order;
import tacos.Taco;

import java.sql.Types;
import java.util.*;

import static sun.security.krb5.Confounder.longValue;

@Repository
public class JdbcOrderRepository implements OrderRepository {

    private JdbcOperations jdbcOperations;
    private JdbcTemplate jdbc;
    private SimpleJdbcInsert orderInserter;
    private SimpleJdbcInsert orderTacoInserter;
    private ObjectMapper objectMapper;
// 1 способ
//    public JdbcOrderRepository(JdbcOperations jdbcOperations) {
//        this.jdbcOperations = jdbcOperations;
//    }

//  2 способ с SimpleJdbcInsert
    @Autowired
    public JdbcOrderRepository(JdbcTemplate jdbc) {
        this.orderInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("Taco_Order")
                .usingGeneratedKeyColumns("id");
        this.orderTacoInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("Taco_Order_Tacos");
        this.objectMapper = new ObjectMapper();
    }

// 1 способ с методом update()
//    @Override
//    @Transactional
//    public Order save(Order order) {
//        // Запрос
//        PreparedStatementCreatorFactory pscf =
//                new PreparedStatementCreatorFactory(
//                        "insert into Taco_Order "
//                                + "(delivery_name, delivery_street, delivery_city, "
//                                + "delivery_state, delivery_zip, cc_number, "
//                                + "cc_expiration, cc_cvv, placed_at) "
//                                + "values (?,?,?,?,?,?,?,?,?)",
//                        Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
//                        Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
//                        Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP
//                );
//        pscf.setReturnGeneratedKeys(true);
//        order.setCreatedAt(new Date());
//        // Передаем переменные в запрос
//        PreparedStatementCreator psc =
//                pscf.newPreparedStatementCreator(
//                        Arrays.asList(
//                                order.getName(),
//                                order.getStreet(),
//                                order.getCity(),
//                                order.getState(),
//                                order.getZip(),
//                                order.getCcNumber(),
//                                order.getCcExpiration(),
//                                order.getCcCVV(),
//                                order.getCreatedAt()));
//        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
//        jdbcOperations.update(psc, keyHolder);
//        long orderId = keyHolder.getKey().longValue();
//        order.setId(orderId);
//        List<Taco> tacos = order.getTacos();
//        int i=0;
//        for (Taco taco : tacos) {
//            saveTaco(orderId, i++, taco);
//        }
//        return order;
//    }
    private long saveTaco(Long orderId, int orderKey, Taco taco) {
        taco.setCreatedAt(new Date());
        PreparedStatementCreatorFactory pscf =
                new PreparedStatementCreatorFactory(
                        "insert into Taco "
                                + "(name, created_at, taco_order, taco_order_key) "
                                + "values (?, ?, ?, ?)",
                        Types.VARCHAR, Types.TIMESTAMP, Type.LONG, Type.LONG
                );
        pscf.setReturnGeneratedKeys(true);
        PreparedStatementCreator psc =
                pscf.newPreparedStatementCreator(
                        Arrays.asList(
                                taco.getName(),
                                taco.getCreatedAt(),
                                orderId,
                                orderKey));
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psc, keyHolder);
        long tacoId = keyHolder.getKey().longValue();
        taco.setId(tacoId);
        saveIngredientRefs(tacoId, taco.getIngredients());
        return tacoId;
    }
    private void saveIngredientRefs(long tacoId, List<Ingredient> ingredientRefs) {
        int key = 0;
        for (Ingredient ingredientRef : ingredientRefs) {
            jdbcOperations.update(
                    "insert into Ingredient_Ref (ingredient, taco, taco_key) "
                            + "values (?, ?, ?)",
                    ingredientRef, tacoId, key++);
        }
    }
//  2 способ с SimpleJdbcInsert
    @Override
    public Order save(Order order) {
        order.setCreatedAt(new Date());
        long orderId = saveOrderDetails(order);
        order.setId(orderId);
        List<Taco> tacos = order.getTacos();
        for (Taco taco : tacos) {
            saveTacoToOrder(taco, orderId);
        }
        return order;
    }
    private long saveOrderDetails(Order order) {
        @SuppressWarnings("unchecked")
        Map<String, Object> values = objectMapper.convertValue(order, Map.class);
        values.put("CreatedAt", order.getCreatedAt());
        long orderId =orderInserter.executeAndReturnKey(values).longValue();
        return orderId;
    }
    private void saveTacoToOrder(Taco taco, long orderId) {
        Map<String, Object> values = new HashMap<>();
        values.put("tacoOrder", orderId);
        values.put("taco", taco.getId());
        orderTacoInserter.execute(values);
    }

}
