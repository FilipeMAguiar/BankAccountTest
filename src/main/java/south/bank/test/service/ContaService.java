package south.bank.test.service;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import south.bank.test.domain.TipoContaEnum;
import south.bank.test.domain.TipoPessoaEnum;
import south.bank.test.dto.request.PessoaRequest;
import south.bank.test.entity.Conta;
import south.bank.test.entity.Pessoa;
import south.bank.test.exception.BusinessException;
import south.bank.test.repository.ContaRepository;

import java.util.*;

@Service
@AllArgsConstructor
public class ContaService {

    private static final String ERROR_MESSAGE_AGENCIA = "A 'agencia' deverá ser preenchido com um valor de 4 caractres";

    private final ContaRepository contaRepository;

    public List<Conta> listarContas() {
        return this.contaRepository.findAll();
    }

    public Conta criarConta(Conta conta, Pessoa pessoa, PessoaRequest request) throws BusinessException {
        validaTipoConta(conta, pessoa);
        setAgencia(request, conta);
        checkScoreAndConta(pessoa, conta);
        setNumeroConta(conta);
        this.contaRepository.save(conta);
        return conta;
    }

    private void setNumeroConta(Conta conta) {
        Random random = new Random();
        conta.setNumeroConta(random.nextInt(999999));
    }

    private void validaTipoConta(Conta conta, Pessoa pessoa) {
        if (pessoa.getTipoPessoa().equals(TipoPessoaEnum.PF)){
            conta.setTipoConta(TipoContaEnum.C);
        } else {
            conta.setTipoConta(TipoContaEnum.E);
        }
    }

    private void setAgencia(PessoaRequest request, Conta conta) throws BusinessException {
        Random random = new Random();
        if (Objects.isNull(request.getAgencia()) || StringUtils.isEmpty(request.getAgencia())){
            conta.setAgencia(random.nextInt(9999));
        } else if (request.getAgencia().toString().length() == 4){
            conta.setAgencia(request.getAgencia());
        } else {
            throw new BusinessException(ERROR_MESSAGE_AGENCIA);
        }
    }

    private void checkScoreAndConta(Pessoa pessoa, Conta conta) {
        if (pessoa.getScore() <= 1){
            conta.setCartao(false);
            conta.setLimiteCartao(0.00);
            conta.setChequeEspecial(false);
        } else if (pessoa.getScore() >=2 && pessoa.getScore() <= 5){
            conta.setCartao(true);
            conta.setLimiteCartao(200.00);
            conta.setChequeEspecial(true);
            conta.setLimiteChequeEspecial(1000.00);
        } else if (pessoa.getScore() >= 6 && pessoa.getScore() <= 8){
            conta.setCartao(true);
            conta.setLimiteCartao(2000.00);
            conta.setChequeEspecial(true);
            conta.setLimiteChequeEspecial(2000.00);
        } else if (pessoa.getScore() >= 9 && pessoa.getScore() <= 10){
            conta.setCartao(true);
            conta.setLimiteCartao(15000.00);
            conta.setChequeEspecial(true);
            conta.setLimiteChequeEspecial(5000.00);
        }
    }
}
