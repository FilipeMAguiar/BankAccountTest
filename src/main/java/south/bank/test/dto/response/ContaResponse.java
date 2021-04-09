package south.bank.test.dto.response;

import lombok.Data;
import south.bank.test.dto.ContaDTO;

import java.util.List;

@Data
public class ContaResponse {
    private List<ContaDTO> contas;
}
