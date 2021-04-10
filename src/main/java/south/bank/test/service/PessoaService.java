package south.bank.test.service;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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

    private static final String NOME_NAO_INFORMADO = "O campo 'nome' deverá ser informado";
    private static final String NOT_FOUND_MESSAGE_PESSOA = "Não existe nenhuma pessoa cadastrada.";
    private static final String NRO_DOCTO_NAO_INFORMADO = "O campo 'numeroDocumento' deverá ser informado";
    private static final String ERROR_MESSAGE_NUMERO_DOCUMENTO = "O numeroDocumento deve conter 11 caracteres para pessoa física ou 14 caracteres para pessoa jurídica.";

    private final PessoaRepository repository;
    private final ContaService contaService;
    private final MongoOperations mongoOperation;

    public List<Pessoa> listarPessoa() throws BusinessException {
        List<Pessoa> pessoas = repository.findAll();
        if (pessoas.isEmpty()){
            throw new BusinessException(NOT_FOUND_MESSAGE_PESSOA);
        }
        return pessoas;
    }

    public Pessoa criarPessoa(PessoaRequest request) throws BusinessException {
        Pessoa pessoa = new Pessoa();
        Conta conta = new Conta();

        validaNumeroDocumento(request);
        validaNome(request);
        validaNumeroDocumentoAndSetTipoConta(request, pessoa);
        setPessoa(request, pessoa);
        contaService.criarConta(conta, pessoa, request);
        this.repository.save(pessoa);
        return pessoa;
    }

    private void validaNumeroDocumento(PessoaRequest request) throws BusinessException {
        Long nroDoc = Long.parseLong(removeCaracteresEspeciais(request.getNumeroDocumento()));
        Optional <Pessoa> pessoa = repository.findById(nroDoc);
        if (pessoa.isPresent()){
            throw new BusinessException("Cadastro existente.");
        }
    }

    private void validaNome(PessoaRequest request) throws BusinessException {
        String nome = request.getNome();
        if (Objects.isNull(nome) || StringUtils.isEmpty(nome)) {
            throw new BusinessException(NOME_NAO_INFORMADO);
        }
    }

    private void validaNumeroDocumentoAndSetTipoConta(PessoaRequest request, Pessoa pessoa) throws BusinessException {
        if (StringUtils.isEmpty(request.getNumeroDocumento())){
            throw new BusinessException(NRO_DOCTO_NAO_INFORMADO);
        }
        String numeroDocumento = removeCaracteresEspeciais(request.getNumeroDocumento());
         if (numeroDocumento.length() == 11 || numeroDocumento.length() == 14) {
            if (numeroDocumento.length() == 11) {
                pessoa.setTipoPessoa(TipoPessoaEnum.PF);
            } else {
                pessoa.setTipoPessoa(TipoPessoaEnum.PJ);
            }
        } else {
            throw new BusinessException(ERROR_MESSAGE_NUMERO_DOCUMENTO);
        }
    }

    private void setPessoa(PessoaRequest request, Pessoa pessoa) {
        Random random = new Random();
        pessoa.setNumeroDocumento(removeCaracteresEspeciais(request.getNumeroDocumento()));
        pessoa.setNomePessoa(request.getNome());
        pessoa.setNumeroDocumento(removeCaracteresEspeciais(request.getNumeroDocumento()));
        pessoa.setScore(random.nextInt(10));
    }

    public static String removeCaracteresEspeciais(String str){
        return str.replaceAll("[^0-9]+", "");
    }
}
