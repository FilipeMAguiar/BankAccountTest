package south.bank.test.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "chequeEspecial")
public class ChequeEspecial {

    @Transient
    public static final String SEQUENCE_NAME = "ce_sequence";

    @Id
    private long id;

    private Double limiteChequeEspecial;

    private boolean isChequeEspecial;

    private Pessoa pessoa;

    private ContaCorrente contaCorrente;

    private CartaoCredito cartaoCredito;
}
