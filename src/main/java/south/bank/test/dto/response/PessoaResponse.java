package south.bank.test.dto.response;

import lombok.Data;
import south.bank.test.dto.PessoaDTO;

import java.util.List;

@Data
public class PessoaResponse {
    private List<PessoaDTO> pessoas;
}
