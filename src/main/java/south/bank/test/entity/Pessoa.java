package south.bank.test.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import south.bank.test.domain.TipoPessoaEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Document(collection = "pessoa")
public class Pessoa {

    @Transient
    public static final String SEQUENCE_NAME = "pessoa_sequence";

    @Id
    private String numeroDocumento;
    private String nomePessoa;
    private TipoPessoaEnum tipoPessoa;
    private Integer score;
}
