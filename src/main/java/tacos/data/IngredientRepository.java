package tacos.data;

import org.springframework.data.repository.CrudRepository;
import tacos.Ingredient;

//Spring Data JDBC
//<dependency>
//      <groupId>org.springframework.boot</groupId>
//      <artifactId>spring-boot-starter-data-jdbc</artifactId>
//</dependency>
//<dependency>
//      <groupId>org.springframework.boot</groupId>
//      <artifactId>spring-boot-starter-jdbc</artifactId>
//</dependency>
// Для JDBC мы реализовывали интерфейсы Repository
// Со Spring Data достаточно унаследовать инт. CrudRepository
// сигнатуры методов можно не писать CrudRepository объявляет около десятка методов
// для операций CRUD (create, read, update, delete). CrudRepository параметризован,
// при этом первым параметром является тип сущности, который должен сохраняться в репозитории,
// а вторым параметром тип свойства entity ID. Для IngredientRepository параметрами должны быть Ingredient и String
// в Spring Data - нет необходимости писать реализацию инт.
// При запуске приложения Spring Data автоматически создает реализацию на лету.
// т.е. репозитории готовы к использованию
// Просто добавляем их в контроллеры, как мы делали для реализаций на основе JDBC, и все готово.
public interface IngredientRepository extends CrudRepository<Ingredient, String> {
//    List<Ingredient> findAll();
//    Optional<Ingredient> findOne(String id);
//    Ingredient save(Ingredient ingredient);
}
