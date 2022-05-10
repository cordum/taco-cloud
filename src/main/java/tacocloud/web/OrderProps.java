package tacocloud.web;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@Component
@Validated
// При установке свойства pageSize нужно обращаться taco.orders.pageSize
@ConfigurationProperties(prefix="taco.orders")
public class OrderProps {
    @Min(value=5, message="must be between 5 and 25")
    @Max(value=25, message="must be between 5 and 25")
    private int pageSize = 5;// TODO проверить
}