package tacos;

//import com.sun.istack.internal.NotNull;
import lombok.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
//@Entity
@Table
public class Taco {

    @Id
//    @GeneratedValue(strategy=GenerationType.AUTO)// Авто генерация Id
    private Long id;
    private Date createdAt;

    public Taco() {}

    // Валидация
    @NotNull// Поле не должно быть пустым
    @Size(min=5, message="Name must be at least 5 characters long")//не менее 5 символов
    private String name;

    @NotNull
//    @ManyToMany(targetEntity=Ingredient.class)
//    Taco может иметь много объектов Ingredient и Ingredient может быть частью многих Taco
    @Size(min=1, message="You must choose at least 1 ingredient")
    private List<Ingredient> ingredients = new ArrayList<>();
}
