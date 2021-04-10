package south.bank.test.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import south.bank.test.dto.request.PessoaRequest;
import south.bank.test.entity.Pessoa;
import south.bank.test.exception.BusinessException;
import south.bank.test.service.PessoaService;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequestMapping("/pessoa")
@AllArgsConstructor
public class PessoaController {

    private final PessoaService service;

    @GetMapping
    public List<Pessoa> listarPessoas(@RequestParam(required = false) Long id) throws BusinessException {
        return this.service.listarPessoa(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pessoa cadastrarPessoa(@RequestBody @Valid PessoaRequest pessoa) throws BusinessException { return this.service.criarPessoa(pessoa);}
}
