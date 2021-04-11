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
import south.bank.test.entity.Conta;
import south.bank.test.entity.Pessoa;
import south.bank.test.exception.BusinessException;
import south.bank.test.repository.ContaRepository;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContaServiceTest {

    @InjectMocks
    private ContaService service;

    @Mock
    private ContaRepository repository;

    @Test
    public void listarPessoaSuccessTest() throws BusinessException {
        List<Conta> contas = new EasyRandom().objects(Conta.class, 5).collect(Collectors.toList());
        when(this.repository.findAll()).thenReturn(contas);
        List<Conta> response = this.service.listarContas();

        assertNotNull(response);
    }

    @Test(expected = BusinessException.class)
    public void listarContaErrorTest() throws BusinessException {
        List<Conta> contas = new EasyRandom().objects(Conta.class, 5).collect(Collectors.toList());
        when(this.service.listarContas()).thenReturn(contas);
    }

    @Test
    public void criarContaPF() throws BusinessException {
        Conta conta = new EasyRandom().nextObject(Conta.class);
        Pessoa pessoa = new EasyRandom().nextObject(Pessoa.class);
        PessoaRequest request = new EasyRandom().nextObject(PessoaRequest.class);
        request.setAgencia(1234);
        pessoa.setTipoPessoa(TipoPessoaEnum.PF);
        when(this.service.criarConta(conta, pessoa, request)).thenReturn(conta);

        assertEquals(TipoContaEnum.C, conta.getTipoConta());
    }

    @Test
    public void criarContaPJ() throws BusinessException {
        Conta conta = new EasyRandom().nextObject(Conta.class);
        Pessoa pessoa = new EasyRandom().nextObject(Pessoa.class);
        PessoaRequest request = new EasyRandom().nextObject(PessoaRequest.class);
        request.setAgencia(1234);
        pessoa.setTipoPessoa(TipoPessoaEnum.PJ);

        when(this.service.criarConta(conta, pessoa, request)).thenReturn(conta);
        assertEquals(TipoContaEnum.E, conta.getTipoConta());
    }

    @Test
    public void criarContaWithoutAgencia() throws BusinessException {
        Conta conta = new EasyRandom().nextObject(Conta.class);
        Pessoa pessoa = new EasyRandom().nextObject(Pessoa.class);
        PessoaRequest request = new EasyRandom().nextObject(PessoaRequest.class);
        request.setAgencia(null);

        when(this.service.criarConta(conta, pessoa, request)).thenReturn(conta);
        assertNotNull(conta.getAgencia());
    }

    @Test(expected = BusinessException.class)
    public void criarContaThrowAgencia() throws BusinessException {
        Conta conta = new EasyRandom().nextObject(Conta.class);
        Pessoa pessoa = new EasyRandom().nextObject(Pessoa.class);
        PessoaRequest request = new EasyRandom().nextObject(PessoaRequest.class);
        request.setAgencia(12345);

        when(this.service.criarConta(conta, pessoa, request)).thenThrow(BusinessException.class);
        assertNotNull(request.getAgencia());
    }

    @Test
    public void criarContaScore3() throws BusinessException {
        Conta conta = new EasyRandom().nextObject(Conta.class);
        Pessoa pessoa = new EasyRandom().nextObject(Pessoa.class);
        PessoaRequest request = new EasyRandom().nextObject(PessoaRequest.class);
        request.setAgencia(1234);
        pessoa.setScore(3);
        pessoa.setTipoPessoa(TipoPessoaEnum.PF);

        when(this.service.criarConta(conta, pessoa, request)).thenReturn(conta);
        assertEquals(TipoContaEnum.C, conta.getTipoConta());
    }
    @Test
    public void criarContaScore7() throws BusinessException {
        Conta conta = new EasyRandom().nextObject(Conta.class);
        Pessoa pessoa = new EasyRandom().nextObject(Pessoa.class);
        PessoaRequest request = new EasyRandom().nextObject(PessoaRequest.class);
        request.setAgencia(4312);
        pessoa.setScore(7);
        pessoa.setTipoPessoa(TipoPessoaEnum.PF);

        when(this.service.criarConta(conta, pessoa, request)).thenReturn(conta);
        assertEquals(TipoContaEnum.C, conta.getTipoConta());
    }
    @Test
    public void criarContaScore9() throws BusinessException {
        Conta conta = new EasyRandom().nextObject(Conta.class);
        Pessoa pessoa = new EasyRandom().nextObject(Pessoa.class);
        PessoaRequest request = new EasyRandom().nextObject(PessoaRequest.class);
        request.setAgencia(4444);
        pessoa.setScore(9);
        pessoa.setTipoPessoa(TipoPessoaEnum.PF);

        when(this.service.criarConta(conta, pessoa, request)).thenReturn(conta);
        assertEquals(TipoContaEnum.C, conta.getTipoConta());
    }

    @Test
    public void criarContaScore10() throws BusinessException {
        Conta conta = new EasyRandom().nextObject(Conta.class);
        Pessoa pessoa = new EasyRandom().nextObject(Pessoa.class);
        PessoaRequest request = new EasyRandom().nextObject(PessoaRequest.class);
        request.setAgencia(5423);
        pessoa.setScore(10);
        pessoa.setTipoPessoa(TipoPessoaEnum.PF);

        when(this.service.criarConta(conta, pessoa, request)).thenReturn(conta);
        assertEquals(TipoContaEnum.C, conta.getTipoConta());
    }
}