package tacocloud.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import tacocloud.model.TacoOrder;
import tacocloud.model.User;
import tacocloud.repository.OrderRepository;
import tacocloud.repository.UserRepository;

import javax.validation.Valid;
import java.security.Principal;

@Slf4j// Логгер
@Controller// обрабатывает HTTP-запросы и передает запрос в представление
// @RestController записывает данные непосредственно в тело ответа
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {

    private OrderRepository orderRepo;
    private UserRepository userRepo;

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
    public String processOrder(@Valid TacoOrder tacoOrder, Errors errors, SessionStatus sessionStatus, @AuthenticationPrincipal User user) {
        if (errors.hasErrors()) {
            return "orderForm";
        }
        log.info("Order submitted: " + tacoOrder);
        orderRepo.save(tacoOrder);
//      1 вар
//        public String processOrder(@Valid TacoOrder tacoOrder, Errors errors, SessionStatus sessionStatus, Principal principal)
//        User user = userRepo.findByUsername(principal.getName());
//      2 вар
//        public String processOrder(@Valid TacoOrder tacoOrder, Errors errors, SessionStatus sessionStatus, Authentication authentication)
//        User user = (User) authentication.getPrincipal();
//      3 вар
//        public String processOrder(@Valid TacoOrder tacoOrder, Errors errors, SessionStatus sessionStatus, @AuthenticationPrincipal User user)
//      4 вар
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = (User) authentication.getPrincipal();
        tacoOrder.setUser(user);
        sessionStatus.setComplete();
//      Очищаем Spring Session в целях безопасности личных данных
//      Если не очистить его, заказ остается в сеансе, включая связанные с ним тако
//      и следующий заказ начнется с тако, содержащимися в старом заказе
//      SessionAttributes будут удалены, сохранив при этом HTTP сессию
        return "redirect:/";
    }
}
