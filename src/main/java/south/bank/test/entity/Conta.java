package south.bank.test.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import south.bank.test.domain.TipoContaEnum;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(collection = "conta")
public class Conta {

    @Transient
    public static final String SEQUENCE_NAME = "conta_sequence";

    @Id
    private long idConta;

    //atributos de relação
    private long idPessoa;
    private String nomePessoa;

    //atributos da entidade ContaCorrente
    private Boolean hasCartao;
    private Boolean hasChequeEspecial;
    private Double limiteChequeEspecial;
    private Double limiteCartaoCredito;
    private Integer numero;
    private Integer agencia;
    private TipoContaEnum tipoConta;
}
