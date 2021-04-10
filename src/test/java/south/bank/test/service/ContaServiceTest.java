package south.bank.test.service;

import org.jeasy.random.EasyRandom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import south.bank.test.entity.Conta;
import south.bank.test.exception.BusinessException;
import south.bank.test.repository.ContaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeastOnce;

@RunWith(MockitoJUnitRunner.Silent.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContaServiceTest {

    @InjectMocks
    private ContaService service;

    @Mock
    private ContaRepository repository;
/*
    @Test(expected = BusinessException.class)
    public void listarContaEmptyTest() throws BusinessException {
        Long request = 123L;
        when(this.repository.findById(request)).thenReturn(Optional.empty());
        when(this.service.listarContas(request)).thenThrow(BusinessException.class);
        verify(this.service, atLeastOnce()).listarContas(any());
    }

    @Test
    public void listarContaIdTest() throws BusinessException {
        Conta list = new EasyRandom().nextObject(Conta.class);
        Long request = 123L;
        when(this.repository.findById(request)).thenReturn(Optional.ofNullable(list));
        List<Conta> response = service.listarContas(request);
        assertNotNull(response);
    }

    @Test
    public void listarPessoaWithoutIdTest() throws BusinessException {
        List<Conta> list = new EasyRandom().objects(Conta.class,5).collect(Collectors.toList());
        when(this.repository.findAll()).thenReturn(list);

        List<Conta> response = this.service.listarContas(null);
        assertNotNull(response);
    }

    @Test(expected = BusinessException.class)
    public void listarPessoaWithoutIdEmptyTest() throws BusinessException {
        List<Conta> list = this.repository.findAll();
        when(service.listarContas(null)).thenThrow(BusinessException.class);

        assertTrue(list.isEmpty());
        verify(this.service, atLeastOnce()).listarContas(any());
    }

 */
}