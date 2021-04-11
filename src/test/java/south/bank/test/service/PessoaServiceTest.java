package south.bank.test.service;

import org.jeasy.random.EasyRandom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import south.bank.test.domain.TipoPessoaEnum;
import south.bank.test.dto.request.PessoaRequest;
import south.bank.test.entity.Conta;
import south.bank.test.entity.Pessoa;
import south.bank.test.exception.BusinessException;
import south.bank.test.repository.PessoaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PessoaServiceTest {

    @InjectMocks
    private PessoaService service;

    @Mock
    private PessoaRepository repository;

    @Mock
    private ContaService contaService;

    @Test
    public void listarPessoaSuccessTest() throws BusinessException {
        List<Pessoa> pessoas = new EasyRandom().objects(Pessoa.class, 5).collect(Collectors.toList());
        when(this.repository.findAll()).thenReturn(pessoas);
        List<Pessoa> response = this.service.listarPessoa();
        assertNotNull(response);
    }

    @Test(expected = BusinessException.class)
    public void listarPessoaErrorTest() throws BusinessException {
        List<Pessoa> pessoas = new EasyRandom().objects(Pessoa.class, 5).collect(Collectors.toList());
        when(this.service.listarPessoa()).thenReturn(pessoas);
    }

    @Test
    public void criarPessoaFisicaTest() throws BusinessException {
        Pessoa pessoa = new EasyRandom().nextObject(Pessoa.class);
        PessoaRequest request = new EasyRandom().nextObject(PessoaRequest.class);
        Conta conta = new EasyRandom().nextObject(Conta.class);

        request.setAgencia(1234);
        request.setNumeroDocumento("02316141013");
        pessoa.setScore(1);
        pessoa.setTipoPessoa(TipoPessoaEnum.PF);
        when(this.service.criarPessoa(request)).thenReturn(pessoa);
        when(this.contaService.criarConta(conta, pessoa, request)).thenReturn(conta);
        Pessoa response = this.service.criarPessoa(request);
        verify(this.repository, atLeastOnce()).save(any());
        assertSame(TipoPessoaEnum.PF, pessoa.getTipoPessoa());

        assertNotNull(response);
    }

    @Test
    public void criarPessoaJuridicaTest() throws BusinessException {
        Pessoa pessoa = new EasyRandom().nextObject(Pessoa.class);
        PessoaRequest request = new EasyRandom().nextObject(PessoaRequest.class);
        Conta conta = new EasyRandom().nextObject(Conta.class);
        request.setAgencia(null);
        request.setNumeroDocumento("02316141013123");
        pessoa.setTipoPessoa(TipoPessoaEnum.PJ);
        pessoa.setScore(3);
        when(this.service.criarPessoa(request)).thenReturn(pessoa);
        when(this.contaService.criarConta(conta, pessoa, request)).thenReturn(conta);

        Pessoa response = this.service.criarPessoa(request);
        assertSame(TipoPessoaEnum.PJ, pessoa.getTipoPessoa());

        assertNotNull(response);
    }

    @Test(expected = BusinessException.class)
    public void criarPessoaNomeThrowTest() throws BusinessException {
        PessoaRequest request = new PessoaRequest();
        request.setNome(null);
        when(this.service.criarPessoa(request)).thenThrow(BusinessException.class);
        verify(this.service, atLeastOnce()).criarPessoa(request);
    }

    @Test(expected = BusinessException.class)
    public void criarPessoaNroDocThrowTest() throws BusinessException {
        PessoaRequest pessoa = new EasyRandom().nextObject(PessoaRequest.class);
        pessoa.setNumeroDocumento(null);
        when(this.service.criarPessoa(pessoa)).thenThrow(BusinessException.class);
        verify(this.service, atLeastOnce()).criarPessoa(pessoa);
    }

    @Test(expected = BusinessException.class)
    public void criarPessoaNroDocSizeThrowTest() throws BusinessException {
        PessoaRequest pessoa = new EasyRandom().nextObject(PessoaRequest.class);
        Optional<Pessoa> optionalPessoa = Optional.of(new Pessoa());
        pessoa.setNumeroDocumento("123");
        service.criarPessoa(pessoa);
        when(this.repository.findByNumeroDocumento(pessoa.getNumeroDocumento())).thenReturn(optionalPessoa);
        when(this.service.criarPessoa(pessoa)).thenThrow(BusinessException.class);
        verify(this.service, atLeastOnce()).criarPessoa(pessoa);
    }

    @Test(expected = BusinessException.class)
    public void validaNumeroDocumentoFailTest() throws BusinessException {
        PessoaRequest pessoa = new EasyRandom().nextObject(PessoaRequest.class);
        Pessoa optionalPessoa = new EasyRandom().nextObject(Pessoa.class);
        pessoa.setNumeroDocumento("123");
        optionalPessoa.setNumeroDocumento("123");
        when(this.repository.findByNumeroDocumento("123")).thenReturn(Optional.of(optionalPessoa));
        when(this.service.criarPessoa(pessoa)).thenReturn(optionalPessoa);
        verify(this.service, atLeastOnce()).criarPessoa(pessoa);
        assertSame(pessoa.getNumeroDocumento(), optionalPessoa.getNumeroDocumento());
    }
}
