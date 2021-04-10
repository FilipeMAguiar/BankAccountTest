package south.bank.test.service;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import south.bank.test.entity.Conta;
import south.bank.test.exception.BusinessException;
import south.bank.test.repository.ContaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service
@AllArgsConstructor
public class ContaService {

    private static final String NOT_FOUND_MESSAGE_CONTA = "Não existe nenhuma conta cadastrada";

    private final ContaRepository contaRepository;
    private final MongoTemplate mongoTemplate;

    public List<Conta> listarContas(Long id) throws BusinessException {
        Query query = new Query();
        if (Objects.isNull(id)){
            query.addCriteria(Criteria.where("conta").is(Conta.class));
            List<Conta> contas = mongoTemplate.find(query, Conta.class);
            if (contas.isEmpty()){
                throw new BusinessException(NOT_FOUND_MESSAGE_CONTA);
            }
            return contas;
        } else {
            List<Conta> contas = new ArrayList<>();
            Optional<Conta> conta = this.contaRepository.findById(id);
            if (conta.isEmpty()){
                throw new BusinessException(NOT_FOUND_MESSAGE_CONTA);
            }
            conta.ifPresent(contas::add);
            return contas;
        }
    }
}
