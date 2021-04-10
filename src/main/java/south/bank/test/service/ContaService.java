package south.bank.test.service;

import lombok.AllArgsConstructor;
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

    public List<Conta> listarContas(Long id) throws BusinessException {
        if (Objects.isNull(id)){
            List<Conta> contas = this.contaRepository.findAll();
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
