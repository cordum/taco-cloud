package tacocloud;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import tacocloud.model.Ingredient;
import tacocloud.repository.IngredientRepository;

@SpringBootApplication//=
// @SpringBootConfiguration обозначает, что этот класс является классом конфигурации.
// Эта аннотация является, фактически, специализированной формой аннотации @Configuration
// @EnableAutoConfiguration включает автоматическое Spring Boot конфигурирование.
// Эта аннотация говорит Spring Boot автоматически настраивать любые компоненты, которые, по его мнению, нам понадобятся.
// @ComponentScan включает сканирование компонентов в пакете, в котором TacoCloudApplication находится,
// т.е. в tacos. @ComponentScan не видит классы за пределами tacos и надо обьявлять их:
//@ComponentScans(
//        value = {
//                @ComponentScan("tacos"),
//                @ComponentScan("burgers")
//        }
//)
// Это позволяет объявлять классы с аннотациями
// @Component @Controller @Service @Repository и другими, чтобы Spring
// автоматически обнаруживала их и регистрировала как компоненты в контексте приложения Spring.
public class TacoCloudApplication {
// Spring Boot автоматически запускает файлы с именами schema.sql и data.sql находящиеся в src/main/resources
    public static void main(String[] args) {
        SpringApplication.run(TacoCloudApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(IngredientRepository repo) {
        return args -> {
            repo.save(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP));
            repo.save(new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP));
            repo.save(new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN));
            repo.save(new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN));
            repo.save(new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES));
            repo.save(new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES));
            repo.save(new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE));
            repo.save(new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE));
            repo.save(new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE));
            repo.save(new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE));
        };
    }
}
