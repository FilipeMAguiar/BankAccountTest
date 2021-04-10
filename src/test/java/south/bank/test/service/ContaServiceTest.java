package south.bank.test.service;

import org.jeasy.random.EasyRandom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import south.bank.test.entity.ContaCorrente;
import south.bank.test.entity.Pessoa;
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

    @Test(expected = BusinessException.class)
    public void listarContaEmptyTest() throws BusinessException {
        Long request = 123L;
        when(this.repository.findById(request)).thenReturn(Optional.empty());
        when(this.service.listarContas(request)).thenThrow(BusinessException.class);
        verify(this.service, atLeastOnce()).listarContas(any());
    }

    @Test
    public void listarContaIdTest() throws BusinessException {
        ContaCorrente list = new EasyRandom().nextObject(ContaCorrente.class);
        Long request = 123L;
        when(this.repository.findById(request)).thenReturn(Optional.ofNullable(list));
        List<ContaCorrente> response = service.listarContas(request);
        assertNotNull(response);
    }

    @Test
    public void listarPessoaWithoutIdTest() throws BusinessException {
        List<ContaCorrente> list = new EasyRandom().objects(ContaCorrente.class,5).collect(Collectors.toList());
        when(this.repository.findAll()).thenReturn(list);

        List<ContaCorrente> response = this.service.listarContas(null);
        assertNotNull(response);
    }

    @Test(expected = BusinessException.class)
    public void listarPessoaWithoutIdEmptyTest() throws BusinessException {
        List<ContaCorrente> list = this.repository.findAll();
        when(service.listarContas(null)).thenThrow(BusinessException.class);

        assertTrue(list.isEmpty());
        verify(this.service, atLeastOnce()).listarContas(any());
    }
}