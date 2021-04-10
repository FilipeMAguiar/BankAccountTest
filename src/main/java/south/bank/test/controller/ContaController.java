package south.bank.test.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import south.bank.test.entity.ContaCorrente;
import south.bank.test.exception.BusinessException;
import south.bank.test.service.ContaService;

import java.util.List;

@RestController()
@RequestMapping("/conta")
@AllArgsConstructor
public class ContaController {

    private final ContaService service;

    @GetMapping
    public List<ContaCorrente> listarContas(@RequestParam(required = false) Long id) throws BusinessException {
        return this.service.listarContas(id);
    }
}
