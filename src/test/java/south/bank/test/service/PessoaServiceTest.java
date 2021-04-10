package south.bank.test.service;

import org.jeasy.random.EasyRandom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import south.bank.test.domain.TipoContaEnum;
import south.bank.test.domain.TipoPessoaEnum;
import south.bank.test.dto.request.PessoaRequest;
import south.bank.test.entity.Pessoa;
import south.bank.test.exception.BusinessException;
import south.bank.test.repository.PessoaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PessoaServiceTest {

    @InjectMocks
    private PessoaService service;

    @Mock
    private PessoaRepository repository;

    @Mock
    private SequenceGeneratorService generatorService;
/*
    @Test
    public void listarPessoaTest() throws BusinessException {
        Pessoa pessoa = new EasyRandom().nextObject(Pessoa.class);
        Long request = 123L;
        when(this.repository.findById(request)).thenReturn(java.util.Optional.ofNullable(pessoa));

        List<Pessoa> response = this.service.listarPessoa(request);
        assertNotNull(response);
    }

    @Test(expected = BusinessException.class)
    public void listarPessoaEmptyTest() throws BusinessException {
        Long request = 123L;
        when(this.repository.findById(request)).thenReturn(Optional.empty());
        when(this.service.listarPessoa(request)).thenThrow(BusinessException.class);
        verify(this.service, atLeastOnce()).listarPessoa(any());
    }

    @Test
    public void listarPessoaWithoutIdTest() throws BusinessException {
        List<Pessoa> list = new EasyRandom().objects(Pessoa.class,5).collect(Collectors.toList());
        when(this.repository.findAll()).thenReturn(list);

        List<Pessoa> response = this.service.listarPessoa(null);
        assertNotNull(response);
    }

    @Test(expected = BusinessException.class)
    public void listarPessoaWithoutIdEmptyTest() throws BusinessException {
        List<Pessoa> list = this.repository.findAll();
        when(service.listarPessoa(null)).thenThrow(BusinessException.class);

        assertTrue(list.isEmpty());
        verify(this.service, atLeastOnce()).listarPessoa(null);
    }
/*
    @Test
    public void criarPessoaFisicaTest() throws BusinessException {
        Pessoa pessoa = new EasyRandom().nextObject(Pessoa.class);
        PessoaRequest request = new EasyRandom().nextObject(PessoaRequest.class);
        request.setAgencia(1234);
        request.setNumeroDocumento("02316141013");
        pessoa.setScore(1);
        pessoa.setTipoPessoa(TipoPessoaEnum.PF);
        pessoa.getNumeroConta().setTipoConta(TipoContaEnum.C);
        when(this.service.criarPessoa(request)).thenReturn(pessoa);


        Pessoa response = this.service.criarPessoa(request);
        verify(this.repository, atLeastOnce()).save(any());
        assertSame(TipoContaEnum.C, pessoa.getNumeroConta().getTipoConta());
        assertSame(TipoPessoaEnum.PF, pessoa.getTipoPessoa());

        assertNotNull(response);
    }

    @Test
    public void criarPessoaJuridicaTest() throws BusinessException {
        Pessoa pessoa = new EasyRandom().nextObject(Pessoa.class);
        PessoaRequest request = new EasyRandom().nextObject(PessoaRequest.class);
        request.setAgencia(null);
        request.setNumeroDocumento("02316141013123");
        pessoa.setTipoPessoa(TipoPessoaEnum.PJ);
        pessoa.getNumeroConta().setTipoConta(TipoContaEnum.E);
        pessoa.setScore(3);
        when(this.service.criarPessoa(request)).thenReturn(pessoa);

        Pessoa response = this.service.criarPessoa(request);
        assertSame(TipoContaEnum.E, pessoa.getNumeroConta().getTipoConta());
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
        pessoa.setNumeroDocumento("123");
        when(this.service.criarPessoa(pessoa)).thenThrow(BusinessException.class);
        verify(this.service, atLeastOnce()).criarPessoa(pessoa);
    }

    @Test(expected = BusinessException.class)
    public void criarPessoaAgenciaThrowTest() throws BusinessException {
        PessoaRequest pessoa = new EasyRandom().nextObject(PessoaRequest.class);
        pessoa.setNumeroDocumento("02316141013");
        pessoa.setAgencia(142);
        when(this.service.criarPessoa(pessoa)).thenThrow(BusinessException.class);
        verify(this.service, atLeastOnce()).criarPessoa(pessoa);
    }

    @Test
    public void criarPessoaAgenciaTest() throws BusinessException {
        PessoaRequest request = new EasyRandom().nextObject(PessoaRequest.class);
        Pessoa pessoa = new EasyRandom().nextObject(Pessoa.class);
        request.setNumeroDocumento("02316141013");
        request.setAgencia(1423);
        pessoa.setTipoPessoa(TipoPessoaEnum.PF);
        pessoa.getNumeroConta().setTipoConta(TipoContaEnum.C);
        when(this.service.criarPessoa(request)).thenReturn(pessoa);
        assertSame(TipoContaEnum.C, pessoa.getNumeroConta().getTipoConta());
        assertSame(TipoPessoaEnum.PF, pessoa.getTipoPessoa());
    }

    @Test
    public void criarPessoaScore7Test() throws BusinessException {
        Pessoa pessoa = new EasyRandom().nextObject(Pessoa.class);
        PessoaRequest request = new EasyRandom().nextObject(PessoaRequest.class);
        request.setAgencia(1234);
        request.setNumeroDocumento("02316141013123");
        pessoa.setTipoPessoa(TipoPessoaEnum.PJ);
        pessoa.getNumeroConta().setTipoConta(TipoContaEnum.E);
        pessoa.setScore(7);
        when(this.service.criarPessoa(request)).thenReturn(pessoa);

        Pessoa response = this.service.criarPessoa(request);
        assertSame(TipoContaEnum.E, pessoa.getNumeroConta().getTipoConta());
        assertSame(TipoPessoaEnum.PJ, pessoa.getTipoPessoa());

        assertNotNull(response);
    }

    @Test
    public void criarPessoaScore9Test() throws BusinessException {
        Pessoa pessoa = new EasyRandom().nextObject(Pessoa.class);
        PessoaRequest request = new EasyRandom().nextObject(PessoaRequest.class);
        request.setAgencia(1234);
        request.setNumeroDocumento("02316141013123");
        pessoa.setTipoPessoa(TipoPessoaEnum.PJ);
        pessoa.getNumeroConta().setTipoConta(TipoContaEnum.E);
        pessoa.setScore(9);
        when(this.service.criarPessoa(request)).thenReturn(pessoa);

        Pessoa response = this.service.criarPessoa(request);
        assertSame(TipoContaEnum.E, pessoa.getNumeroConta().getTipoConta());
        assertSame(TipoPessoaEnum.PJ, pessoa.getTipoPessoa());

        assertNotNull(response);
    }

 */
}
