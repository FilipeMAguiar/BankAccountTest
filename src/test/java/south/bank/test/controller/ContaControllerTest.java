package south.bank.test.controller;

import org.jeasy.random.EasyRandom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import south.bank.test.entity.ContaCorrente;
import south.bank.test.exception.BusinessException;
import south.bank.test.service.ContaService;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContaControllerTest {
    
    @InjectMocks
    private ContaController controller;

    @Mock
    private ContaService service;

    @Test
    public void listarContaCorrentesTest() throws BusinessException {
        List<ContaCorrente> list = new EasyRandom().objects(ContaCorrente.class,5).collect(Collectors.toList());
        when(this.service.listarContas(123L)).thenReturn(list);
        List<ContaCorrente> response = this.controller.listarContas(123L);

        verify(service, atLeastOnce()).listarContas(123L);
        assertNotNull(response);
    }
}
