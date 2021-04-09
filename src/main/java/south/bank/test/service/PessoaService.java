package south.bank.test.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import south.bank.test.domain.TipoContaEnum;
import south.bank.test.domain.TipoPessoaEnum;
import south.bank.test.dto.request.PessoaRequest;
import south.bank.test.entity.CartaoCredito;
import south.bank.test.entity.ChequeEspecial;
import south.bank.test.entity.ContaCorrente;
import south.bank.test.entity.Pessoa;
import south.bank.test.exception.BusinessException;
import south.bank.test.repository.ContaRepository;
import south.bank.test.repository.PessoaRepository;

import java.util.*;

@Service
@AllArgsConstructor
public class PessoaService {

    private final PessoaRepository repository;
    private final ContaRepository contaRepository;
    private final SequenceGeneratorService generatorService;

    private static final String NOT_FOUND_MESSAGE_PESSOA = "Não existe nenhuma pessoa cadastrada";
    private static final String NOT_FOUND_MESSAGE_CONTA = "Não existe nenhuma conta cadastrada";
    private static final String ERROR_MESSAGE_AGENCIA = "O campo 'agencia' deverá ser preenchido com um valor de 4 caractres";
    private static final String ERROR_MESSAGE_NUMERO_DOCUMENTO = "O campo numeroDocumento deve conter 11 caracteres para pessoa física ou 14 caracteres para pessoa jurídica.";

    public List<Pessoa> listarPessoa(Long id) throws BusinessException {
        if (Objects.isNull(id)){
            List<Pessoa> pessoas = this.repository.findAll();
            if (pessoas.isEmpty()){
                throw new BusinessException(NOT_FOUND_MESSAGE_PESSOA);
            }
            return pessoas;
        } else {
            List<Pessoa> pessoas = new ArrayList<>();
            Optional<Pessoa> pessoa = this.repository.findById(id);
            if (pessoa.isEmpty()){
                throw new BusinessException(NOT_FOUND_MESSAGE_PESSOA);
            }
            pessoa.ifPresent(pessoas::add);
            return pessoas;
        }
    }

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

    public Pessoa criarPessoa(PessoaRequest request) throws BusinessException {
        Random random = new Random();
        Pessoa pessoa = new Pessoa();
        ContaCorrente conta = new ContaCorrente();
        CartaoCredito cartaoCredito = new CartaoCredito();
        ChequeEspecial chequeEspecial = new ChequeEspecial();

        setAgenciaAndNumero(request, random, conta);
        validaNumeroDocumentoAndSetTipoConta(request, pessoa, conta);
        checkScoreAndSetLimites(pessoa, cartaoCredito, chequeEspecial, random);
        setPessoa(request, pessoa, conta, cartaoCredito, chequeEspecial);

        this.repository.save(pessoa);
        return pessoa;
    }

    private void setAgenciaAndNumero(PessoaRequest request, Random random, ContaCorrente conta) throws BusinessException {
        if (Objects.isNull(request.getAgencia()) || StringUtils.isEmpty(request.getAgencia())){
            conta.setAgencia(random.nextInt(9999));
        } else if (request.getAgencia() == 4){
            conta.setAgencia(request.getAgencia());
        } else {
            throw new BusinessException(ERROR_MESSAGE_AGENCIA);
        }
        conta.setNumero(random.nextInt(999999));
    }

    private void setPessoa(PessoaRequest request, Pessoa pessoa, ContaCorrente conta, CartaoCredito cartaoCredito, ChequeEspecial chequeEspecial) {
        pessoa.setId(generatorService.generateSequence(Pessoa.SEQUENCE_NAME));
        conta.setId(generatorService.generateSequence(ContaCorrente.SEQUENCE_NAME));
        cartaoCredito.setId(generatorService.generateSequence(CartaoCredito.SEQUENCE_NAME));
        chequeEspecial.setId(generatorService.generateSequence(ChequeEspecial.SEQUENCE_NAME));
        pessoa.setNome(request.getNome());
        pessoa.setNumeroDocumento(request.getNumeroDocumento());
        pessoa.setCartaoCredito(cartaoCredito);
        pessoa.setChequeEspecial(chequeEspecial);
        pessoa.setContaCorrente(conta);
    }

    private void checkScoreAndSetLimites(Pessoa pessoa, CartaoCredito cartaoCredito, ChequeEspecial chequeEspecial, Random random) {
        pessoa.setScore(random.nextInt(10));
        if (pessoa.getScore() <= 1){
            cartaoCredito.setCartao(false);
            cartaoCredito.setLimiteCartao(0.00);
            chequeEspecial.setChequeEspecial(false);
        } else if (pessoa.getScore() >=2 && pessoa.getScore() <= 5){
            cartaoCredito.setCartao(true);
            cartaoCredito.setLimiteCartao(200.00);
            chequeEspecial.setChequeEspecial(true);
            chequeEspecial.setLimiteChequeEspecial(1000.00);
        } else if (pessoa.getScore() >= 6 && pessoa.getScore() <= 8){
            cartaoCredito.setCartao(true);
            cartaoCredito.setLimiteCartao(2000.00);
            chequeEspecial.setChequeEspecial(true);
            chequeEspecial.setLimiteChequeEspecial(2000.00);
        } else {
            cartaoCredito.setCartao(true);
            cartaoCredito.setLimiteCartao(15000.00);
            chequeEspecial.setChequeEspecial(true);
            chequeEspecial.setLimiteChequeEspecial(5000.00);
        }
    }

    private void validaNumeroDocumentoAndSetTipoConta(PessoaRequest request, Pessoa pessoa, ContaCorrente conta) throws BusinessException {
        if (request.getNumeroDocumento().length() == 11 || request.getNumeroDocumento().length() == 14) {
            if (request.getNumeroDocumento().length() == 11) {
                conta.setTipoConta(TipoContaEnum.C);
                pessoa.setTipoPessoa(TipoPessoaEnum.PF);
            } else {
                conta.setTipoConta(TipoContaEnum.E);
                pessoa.setTipoPessoa(TipoPessoaEnum.PJ);
            }
        } else {
            throw new BusinessException(ERROR_MESSAGE_NUMERO_DOCUMENTO);
        }
    }
}
