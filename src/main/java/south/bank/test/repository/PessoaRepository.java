package south.bank.test.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import south.bank.test.entity.Pessoa;

import java.util.Optional;

public interface PessoaRepository extends MongoRepository<Pessoa, Long> {
    Optional<Pessoa> findByNumeroDocumento(String numeroDocumento);
}
