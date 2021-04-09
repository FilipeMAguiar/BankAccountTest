package south.bank.test.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import south.bank.test.dto.request.PessoaRequest;
import south.bank.test.entity.ContaCorrente;
import south.bank.test.entity.Pessoa;
import south.bank.test.service.PessoaService;

import javax.validation.Valid;
import java.util.List;

@RestController()
@AllArgsConstructor
public class PessoaController {

    private final PessoaService service;

    @GetMapping("pessoas")
    public List<Pessoa> getPessoa(@RequestParam(required = false) Long id) throws Exception {
        return this.service.listarPessoa(id);
    }

    @GetMapping("contas")
    public List<ContaCorrente> listarContas(@RequestParam(required = false) Long id) throws Exception { return this.service.listarContas(id);}

    @PostMapping("pessoas")
    @ResponseStatus(HttpStatus.CREATED)
    public Pessoa create(@RequestBody @Valid PessoaRequest pessoa) throws Exception { return this.service.criarPessoa(pessoa);}
}
