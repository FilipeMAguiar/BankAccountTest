package south.bank.test.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import south.bank.test.domain.TipoContaEnum;
import south.bank.test.domain.TipoPessoaEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(collection = "pessoa")
public class Pessoa {

    @Transient
    public static final String SEQUENCE_NAME = "pessoa_sequence";

    @Id
    private Long idPessoa;

    @NotNull
    @NotBlank
    private String numeroDocumento;

    @NotNull
    @NotBlank
    private String nome;

    @NotNull
    @NotBlank
    private TipoPessoaEnum tipoPessoa;

    private Integer score;

    //atributos de relação
    private long idConta;
    private long numeroContaCorrente;
    private Integer agenciaContaCorrente;
    private TipoContaEnum tipoContaCorrente;
}
