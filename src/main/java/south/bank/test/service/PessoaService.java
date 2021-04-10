package south.bank.test.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import south.bank.test.domain.TipoContaEnum;
import south.bank.test.domain.TipoPessoaEnum;
import south.bank.test.dto.request.PessoaRequest;
import south.bank.test.entity.Conta;
import south.bank.test.entity.Pessoa;
import south.bank.test.exception.BusinessException;
import south.bank.test.repository.PessoaRepository;

import java.util.*;

@Service
@AllArgsConstructor
public class PessoaService {

    private static final String NOT_FOUND_MESSAGE_PESSOA_ID = "Não existe nenhuma pessoa cadastrada com este ID.";
    private static final String NOT_FOUND_MESSAGE_PESSOA = "Não existe nenhuma pessoa cadastrada.";
    private static final String NOME_NAO_INFORMADO = "O campo 'nome' deverá ser informado";
    private static final String NRO_DOCTO_NAO_INFORMADO = "O campo 'numeroDocumento' deverá ser informado";
    private static final String ERROR_MESSAGE_AGENCIA = "A 'agencia' deverá ser preenchido com um valor de 4 caractres";
    private static final String ERROR_MESSAGE_NUMERO_DOCUMENTO = "O numeroDocumento deve conter 11 caracteres para pessoa física ou 14 caracteres para pessoa jurídica.";

    private final PessoaRepository repository;
    private final SequenceGeneratorService generatorService;

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
                throw new BusinessException(NOT_FOUND_MESSAGE_PESSOA_ID);
            }
            pessoa.ifPresent(pessoas::add);
            return pessoas;
        }
    }

    public Pessoa criarPessoa(PessoaRequest request) throws BusinessException {
        Random random = new Random();
        Pessoa pessoa = new Pessoa();
        Conta conta = new Conta();

        validaNome(request);
        validaNumeroDocumentoAndSetTipoConta(request, pessoa, conta);
        setAgenciaAndNumero(request, random, conta);
        checkScoreAndSetLimites(pessoa, conta, random);
        setPessoa(request, pessoa, conta);

        this.repository.save(pessoa);
        return pessoa;
    }

    private void validaNome(PessoaRequest request) throws BusinessException {
        String nome = request.getNome();
        if (Objects.isNull(nome) || StringUtils.isEmpty(nome)) {
            throw new BusinessException(NOME_NAO_INFORMADO);
        }
    }

    private void validaNumeroDocumentoAndSetTipoConta(PessoaRequest request, Pessoa pessoa, Conta conta) throws BusinessException {
        if (Objects.isNull(request.getNumeroDocumento()) || StringUtils.isEmpty(request.getNumeroDocumento())){
            throw new BusinessException(NRO_DOCTO_NAO_INFORMADO);
        } else if (request.getNumeroDocumento().length() == 11 || request.getNumeroDocumento().length() == 14) {
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

    private void setAgenciaAndNumero(PessoaRequest request, Random random, Conta conta) throws BusinessException {
        if (Objects.isNull(request.getAgencia()) || StringUtils.isEmpty(request.getAgencia())){
            conta.setAgencia(random.nextInt(9999));
        } else if (request.getAgencia().toString().length() == 4){
            conta.setAgencia(request.getAgencia());
        } else {
            throw new BusinessException(ERROR_MESSAGE_AGENCIA);
        }
        conta.setNumero(random.nextInt(999999));
    }

    private void checkScoreAndSetLimites(Pessoa pessoa, Conta conta, Random random) {
        pessoa.setScore(random.nextInt(10));
        if (pessoa.getScore() <= 1){
            conta.setHasCartao(false);
            conta.setLimiteCartaoCredito(0.00);
            conta.setHasChequeEspecial(false);
        } else if (pessoa.getScore() >=2 && pessoa.getScore() <= 5){
            conta.setHasCartao(true);
            conta.setLimiteCartaoCredito(200.00);
            conta.setHasChequeEspecial(true);
            conta.setLimiteChequeEspecial(1000.00);
        } else if (pessoa.getScore() >= 6 && pessoa.getScore() <= 8){
            conta.setHasCartao(true);
            conta.setLimiteCartaoCredito(2000.00);
            conta.setHasChequeEspecial(true);
            conta.setLimiteChequeEspecial(2000.00);
        } else if (pessoa.getScore() == 9){
            conta.setHasCartao(true);
            conta.setLimiteCartaoCredito(15000.00);
            conta.setHasChequeEspecial(true);
            conta.setLimiteChequeEspecial(5000.00);
        }
    }

    private void setPessoa(PessoaRequest request, Pessoa pessoa, Conta conta) {
        pessoa.setIdPessoa(generatorService.generateSequence(Pessoa.SEQUENCE_NAME));
        pessoa.setIdConta(conta.getIdConta());
        pessoa.setNome(request.getNome());
        pessoa.setNumeroDocumento(request.getNumeroDocumento());
        pessoa.setNumeroContaCorrente(conta.getNumero());
        pessoa.setAgenciaContaCorrente(conta.getAgencia());
        pessoa.setTipoContaCorrente(conta.getTipoConta());
    }
}
