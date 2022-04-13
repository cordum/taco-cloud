package tacos;

//import com.sun.istack.internal.NotNull;
import lombok.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class Taco {

    private Long id;
    private Date createdAt = new Date();

    public Taco() {}

    //Validating form input
    @NotNull//поле не должно быть пустым
    @Size(min=5, message="Name must be at least 5 characters long")//не менее 5 символов
    private String name;

    @NotNull
    @Size(min=1, message="You must choose at least 1 ingredient")
    private List<Ingredient> ingredients = new ArrayList<>();
}
