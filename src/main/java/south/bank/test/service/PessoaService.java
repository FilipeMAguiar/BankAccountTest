package south.bank.test.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
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

    private final PessoaRepository repository;
    private final ContaService contaService;

    public List<Pessoa> listarPessoa() {
        return this.repository.findAll();
    }

    public Pessoa criarPessoa(PessoaRequest request) throws BusinessException {
        Pessoa pessoa = new Pessoa();
        Conta conta = new Conta();

        validaNumeroDocumentoAndSetTipoConta(request, pessoa);
        setPessoa(request, pessoa);
        contaService.criarConta(conta, pessoa, request);
        this.repository.save(pessoa);
        return pessoa;
    }

    private void validaNumeroDocumentoAndSetTipoConta(PessoaRequest request, Pessoa pessoa) {
        if (request.getNumeroDocumento().length() == 11 || request.getNumeroDocumento().length() == 14) {
            if (request.getNumeroDocumento().length() == 11) {
                pessoa.setTipoPessoa(TipoPessoaEnum.PF);
            } else {
                pessoa.setTipoPessoa(TipoPessoaEnum.PJ);
            }
        }
    }

    private void setPessoa(PessoaRequest request, Pessoa pessoa) {
        Random random = new Random();
        pessoa.setNumeroDocumento(request.getNumeroDocumento());
        pessoa.setNomePessoa(request.getNome());
        pessoa.setNumeroDocumento(request.getNumeroDocumento());
        pessoa.setScore(random.nextInt(10));
    }
}
