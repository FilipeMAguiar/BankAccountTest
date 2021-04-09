package south.bank.test.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import south.bank.test.entity.Pessoa;

import java.util.List;

public interface PessoaRepository extends MongoRepository<Pessoa, Long> {

    List<Pessoa> findAll();

}
