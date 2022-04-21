package tacos.data;

import org.springframework.data.repository.CrudRepository;
import tacos.TacoOrder;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {
//    Order save(Order order);
//    если нужен метод которого нет в CrudRepository
//    напр. нужно получить все заказы, доставленные по заданному почтовому индексу,
//    просто пишем:
//    List<Order> findByDeliveryZip(String deliveryZip);
//    При создании реализации репозитория Spring Data проверяет все методы
//    в интерфейсе репозитория, анализирует имя метода и пытается понять
//    назначение метода в контексте сохраняемого объекта (в данном случае-порядок).  
//    По сути, Spring Data определяет своего рода миниатюрный доменный язык (DSL),
//    в котором сведения о сохраняемости выражаются в сигнатурах методов репозитория.
//    Spring Data знает, что этот метод предназначен для поиска заказов,
//    потому что вы параметризовали CrudRepository с Order.  
//    Имя метода, findByDeliveryZip(), дает понять, что этот метод должен найти
//    все сущности Order, сопоставив их свойство deliveryZip со значением, переданным в качестве параметра в метод

//    Рассмотрим другой пример. Предположим, что вам нужно запросить все заказы,
//    доставленные по заданному почтовому индексу в заданном диапазоне дат,
//    просто пишем:
//    List<Order> readOrdersByDeliveryZipAndPlacedAtBetween(String deliveryZip, Date startDate, Date endDate);
//    см картинку \Java\Spring Data методы

//    если все таки нужно написать свой запрос:
//    @Query("Order o where o.deliveryCity='Seattle'")
//    в этом случае название метода может быть любым
//    List<Order> readOrdersDeliveredInSeattle();

}
