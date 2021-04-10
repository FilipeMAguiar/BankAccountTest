package south.bank.test.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PessoaRequest {

    @NotBlank(message = "O nome deve ser preenchido.")
    @NotNull(message = "O nome deve ser informado.")
    private String nome;

    @NotBlank(message = "O numeroDocumento deve ser preenchido.")
    @NotNull(message = "O numeroDocumento deve ser informado.")
    private String numeroDocumento;

    private Integer agencia;
}
