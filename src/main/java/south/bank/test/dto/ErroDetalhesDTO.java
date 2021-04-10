package south.bank.test.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErroDetalhesDTO {
    private String titulo;
    private String mensagem;
}