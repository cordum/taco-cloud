package tacos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data// генерирует конструктор, геттеры/сеттеры
@NoArgsConstructor(access=AccessLevel.PRIVATE, force=true)
// JPA требует, чтобы сущности имели конструктор без аргументов
// но нам не надо, чтобы имелась возможность использовать его, поэтому установим атрибут доступа AccessLevel.PRIVATE
// force = true,значит все final поля инициализируются 0 / false / null
// если это невозможно, то возникает ошибка компиляции
@RequiredArgsConstructor
// @NoArgsConstructor удаляет конструктор созданный @Data, поэтому генерируем
// конструктор с 1 параметром для каждого поля, которое требует специальной обработки
//@AllArgsConstructor генерирует конструктор с 1 параметром для каждого поля в классе
//@Entity Объявляем сущностью JPA
public class Ingredient {

    @Id// Поле Id для БД
    private final String id;
    private final String name;
    private final Type type;

    public static enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
