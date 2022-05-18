package tacocloud.web.api;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tacocloud.model.Taco;
import tacocloud.model.TacoOrder;
import tacocloud.repository.TacoRepository;

import java.util.List;
import java.util.Optional;

// @RestController записывает данные непосредственно в тело ответа
// В качестве альтернативы, можно аннотировать DesignTacoController с помощью @Controller,
// но тогда нужно аннотировать все методы-обработчики с помощью @ResponseBody для достижения того же результата
// Еще один вариант - вернуть объект ResponseEntity
@RestController
// path="/api/tacos"           Обрабатываем запросы, путь к которым начинается /api/tacos
// Атрибут consumes запрашивает ввод, атрибут produces запрашивает вывод
// produces="application/json" Любой из методов-обработчиков в TacoController будет
// обрабатывать запросы только в том случае, если заголовок Accept запроса включает в себя «application/json»
// produces={"application/json", "text/xml"} Если хотим несколько форматов
@RequestMapping(path="/api/tacos", produces="application/json")
// Позволяет клиентам из любого домена использовать API
@CrossOrigin(origins="*")//"http://tacocloud:8080")
public class TacoController {

    private TacoRepository tacoRepo;

    public TacoController(TacoRepository tacoRepo) {
        this.tacoRepo = tacoRepo;
    }
//  Вариант 1
//    @GetMapping("/recent")
//    Формирует последние тако, созданные пользователем
//    public Iterable<Taco> recentTacos() {
//      Хотим получить первую (нулевую) страницу с первыми 12 результатами сортировки в порядке убывания по дате создания тако
//        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
//      Для findAll(page).getContent() добавим в TacoRepository extends PagingAndSortingRepository<Taco, Long>
//        return tacoRepo.findAll(page).getContent();
//    }
//   Вариант 2 HATEOAS Внедрение гиперссылок
    @GetMapping(params="/recent")
    public CollectionModel<EntityModel<Taco>> recentTacos() {
        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
        List<Taco> tacos = tacoRepo.findAll(page).getContent();
        CollectionModel<EntityModel<Taco>> recentResources = CollectionModel.wrap(tacos);
        recentResources.add(Link.of("http://localhost:8080/design/recent", LinkRelation.of("recents")));
        return recentResources;
    }
    //    Вариант 1
//    @GetMapping("/{id}")
//    public Taco tacoById(@PathVariable("id") Long id) {
//        Optional<Taco> optTaco = tacoRepo.findById(id);
//        if (optTaco.isPresent()) {
//            return optTaco.get();
//        }
//      Если такого тако нет вернется null и код HTTP 200 (OK)
//      Это неправильно поэтому смотрим Вариант 2
//        return null;
//    }
//  Вариант 2
    @GetMapping("/{id}")
    public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
    Optional<Taco> optTaco = tacoRepo.findById(id);
    if (optTaco.isPresent()) {
        return new ResponseEntity<>(optTaco.get(), HttpStatus.OK);
    }
    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
//  Атрибут consumes запрашивает ввод
//  Method will only handle requests whose Content-type matches application/json
    @PostMapping(consumes="application/json")
//  При успешном получении ответа приходит статус HTTP 200 (ОК), что не информативно,
//  поэтому установим HttpStatus.CREATED
    @ResponseStatus(HttpStatus.CREATED)
//  @RequestBody Значение taco подставляется из тела запроса
//  @RequestBody Body of the request should be converted to a Taco object and bound to the parameter
    public Taco postTaco(@RequestBody Taco taco) {
        return tacoRepo.save(taco);
    }

//  Обновляет все поля ресурса
    @PutMapping(path="/{id}", consumes="application/json")
    public Taco putTaco(@PathVariable("id") Long id, @RequestBody Taco taco) {
        return tacoRepo.save(taco);
    }
//  Обновляет некоторые поля ресурса
    @PatchMapping(path="/{id}", consumes="application/json")
    public Taco patchTaco(@PathVariable("id") Long id, @RequestBody Taco patch) {

        Taco taco = tacoRepo.findById(id).get();

        if (patch.getName() != null) {
            taco.setName(patch.getName());
        }
        if (patch.getIngredients() != null) {
        taco.setIngredients(patch.getIngredients());
        }
        return tacoRepo.save(taco);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code=HttpStatus.NO_CONTENT)
    public void deleteTaco(@PathVariable("id") Long id) {
        try {
            tacoRepo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {}
    }

}