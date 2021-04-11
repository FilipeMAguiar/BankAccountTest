package south.bank.test.controller;

import org.jeasy.random.EasyRandom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import south.bank.test.dto.request.PessoaRequest;
import south.bank.test.entity.Pessoa;
import south.bank.test.exception.BusinessException;
import south.bank.test.service.PessoaService;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PessoaControllerTest {

    @InjectMocks
    private PessoaController controller;

    @Mock
    private PessoaService service;

    @Test
    public void listarPessoasTest() throws BusinessException {
        List<Pessoa> list = new EasyRandom().objects(Pessoa.class,5).collect(Collectors.toList());
        when(this.service.listarPessoa()).thenReturn(list);
        List<Pessoa> response = this.controller.listarPessoas();

        verify(service, atLeastOnce()).listarPessoa();
        assertNotNull(response);
    }

    @Test
    public void criarPessoaTest() throws BusinessException {
        Pessoa pessoa = new EasyRandom().nextObject(Pessoa.class);
        PessoaRequest request = new EasyRandom().nextObject(PessoaRequest.class);
        when(this.service.criarPessoa(request)).thenReturn(pessoa);
        Pessoa response = this.controller.cadastrarPessoa(request);

        verify(service, atLeastOnce()).criarPessoa(request);
        assertNotNull(response);
    }
}
