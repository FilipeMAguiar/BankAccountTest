package south.bank.test.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import south.bank.test.entity.Conta;

public interface ContaRepository extends MongoRepository<Conta, Long> {
}
