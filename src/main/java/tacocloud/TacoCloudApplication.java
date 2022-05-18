package tacocloud;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import tacocloud.model.Ingredient;
import tacocloud.model.Taco;
import tacocloud.repository.IngredientRepository;
import tacocloud.repository.TacoRepository;
import tacocloud.repository.UserRepository;

import java.util.Arrays;

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
}
