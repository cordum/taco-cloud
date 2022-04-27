package tacocloud.repository;

import org.springframework.data.repository.CrudRepository;
import tacocloud.model.Taco;

public interface TacoRepository extends CrudRepository<Taco, Long> {
}
