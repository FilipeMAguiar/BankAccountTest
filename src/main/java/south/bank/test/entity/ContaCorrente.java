package south.bank.test.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import south.bank.test.domain.TipoContaEnum;

@Data
@Document(collection = "conta")
public class ContaCorrente {

    @Transient
    public static final String SEQUENCE_NAME = "conta_sequence";

    @Id
    private long id;

    private Integer numero;

    private Integer agencia;

    private TipoContaEnum tipoConta;

    private Pessoa pessoa;

    private ChequeEspecial chequeEspecial;

    private CartaoCredito cartaoCredito;
}
