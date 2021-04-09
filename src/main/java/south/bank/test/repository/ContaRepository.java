package south.bank.test.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import south.bank.test.entity.ContaCorrente;

import java.util.List;

public interface ContaRepository extends MongoRepository<ContaCorrente, Long> {

    List<ContaCorrente> findAll();

}
