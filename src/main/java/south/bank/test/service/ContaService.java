package south.bank.test.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import south.bank.test.entity.ContaCorrente;
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

    public List<ContaCorrente> listarContas(Long id) throws BusinessException {
        if (Objects.isNull(id)){
            List<ContaCorrente> contas = this.contaRepository.findAll();
            if (contas.isEmpty()){
                throw new BusinessException(NOT_FOUND_MESSAGE_CONTA);
            }
            return contas;
        } else {
            List<ContaCorrente> contas = new ArrayList<>();
            Optional<ContaCorrente> conta = this.contaRepository.findById(id);
            if (conta.isEmpty()){
                throw new BusinessException(NOT_FOUND_MESSAGE_CONTA);
            }
            conta.ifPresent(contas::add);
            return contas;
        }
    }
}
