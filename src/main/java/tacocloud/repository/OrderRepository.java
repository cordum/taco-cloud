package tacocloud.repository;

import org.springframework.data.repository.CrudRepository;
import tacocloud.model.TacoOrder;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {
//    Если аннотация вернет false, метод не выполнится
//    @PreAuthorize("hasRole('ADMIN')")
//    public void deleteAllOrders() {
//        orderRepository.deleteAll();
//    }

}
