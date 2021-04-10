package south.bank.test.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import south.bank.test.entity.Conta;

import java.util.List;

public interface ContaRepository extends MongoRepository<Conta, Long> {
    List<Conta> findAll();
}
