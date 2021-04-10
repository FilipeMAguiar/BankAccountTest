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
    private long id;

    @NotBlank(message = "O campo nome deve ser preenchido.")
    private String nome;

    @NotNull(message = "O campo tipoPessoa deve ser preenchido com: PF (Pessoa Física) ou PJ (Pessoa Jurídica).")
    private TipoPessoaEnum tipoPessoa;

    @NotBlank(message = "O campo numeroDocumento deve ser preenchido")
    private String numeroDocumento;

    private Integer score;

    private Conta conta;
}
