package south.bank.test.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "cartaoCredito")
public class CartaoCredito {

    @Transient
    public static final String SEQUENCE_NAME = "cc_sequence";

    @Id
    private long id;

    private Double limiteCartao;

    private boolean cartao;

    private Pessoa pessoa;

    private ChequeEspecial chequeEspecial;

    private ContaCorrente contaCorrente;
}
