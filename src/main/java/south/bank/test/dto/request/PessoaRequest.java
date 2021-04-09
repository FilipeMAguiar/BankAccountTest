package south.bank.test.dto.request;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class PessoaRequest {
    private String nome;
    private String numeroDocumento;
    private Integer agencia;
}
