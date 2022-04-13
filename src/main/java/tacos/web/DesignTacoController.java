package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Order;
import tacos.Taco;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;

import javax.validation.Valid;

@Slf4j// Lombok logger
@Controller// Пометка для компонентного сканирования
@RequestMapping("/design")// Обрабатываем запросы, путь к которым начинается с /design
@SessionAttributes("order")// Создаем объект order для сессии и делаем его доступным для нескольких запросов
// Сессия это временной промежуток, охватывающий период использования сайта с момента,
// когда пользователь кликнул и перешел по начальному URL и до самого закрытия последнего URL
public class DesignTacoController {

    private final IngredientRepository ingredientRepo;
    private TacoRepository tacoRepo;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository tacoRepo) {
        this.ingredientRepo = ingredientRepo;
        this.tacoRepo = tacoRepo;
    }
    @ModelAttribute(name = "order")// @SessionAttributes("order") тот же объект, содержит состояние заказа
    // помещаем его в модель
    public Order order() {
        return new Order();
    }
    @ModelAttribute(name = "taco")// Модель передает данные между контроллером и представлением
    public Taco taco() {
        return new Taco();
    }
    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        List<Ingredient> ingredients = ingredientRepo.findAll();
        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
    }
    @GetMapping
    public String showDesignForm() {
        return "design";
    }
    // Параметр Order аннотируется @ModelAttribute, чтобы указать, что его значение должно исходить из модели
    @PostMapping
    public String processTaco(@Valid Taco taco, Errors errors, @ModelAttribute Order order) {
        if (errors.hasErrors())
            return "design";
        Taco saved = tacoRepo.save(taco);
        order.addTaco(saved);// order сохраняется в сеансе
        log.info("Processing taco: {}", taco);
        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {

        return ingredients.stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
