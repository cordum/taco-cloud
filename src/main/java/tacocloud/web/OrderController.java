package tacocloud.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import tacocloud.model.TacoOrder;
import tacocloud.repository.OrderRepository;

import javax.validation.Valid;

@Slf4j// Логгер
@Controller// обрабатывает HTTP-запросы и передает запрос в представление
// @RestController записывает данные непосредственно в тело ответа
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {

    private OrderRepository orderRepo;

    public OrderController(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }
    @GetMapping("/current")
    public String orderForm(Model model) {
//      с помощью модели передадим параметры запроса в представление        
//      ключ "tacoOrder"
//      значение new TacoOrder()
        model.addAttribute("tacoOrder", new TacoOrder());
        return "orderForm";
    }

    @PostMapping
    // @Valid выполняет проверку запоненного данными из
    // формы Taco taco перед вызовом processTaco
    // ошибки будут помещены в объект errors
    // Order order объект из сессии
    public String processOrder(@Valid TacoOrder tacoOrder, Errors errors, SessionStatus sessionStatus) {
        if (errors.hasErrors()) {
            return "orderForm";
        }
        log.info("Order submitted: " + tacoOrder);
        orderRepo.save(tacoOrder);
        sessionStatus.setComplete();
//      Очищаем Spring Session в целях безопасности личных данных
//      Если не очистить его, заказ остается в сеансе, включая связанные с ним тако
//      и следующий заказ начнется с тако, содержащимися в старом заказе
//      SessionAttributes будут удалены, сохранив при этом HTTP сессию
        return "redirect:/";
    }
}
