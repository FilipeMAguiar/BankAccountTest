package south.bank.test.domain;

import lombok.Getter;

@Getter
public enum TipoContaEnum {

    C("Corrente"),
    E("Empresarial");

    final String descricao;

    TipoContaEnum(String descricao){ this.descricao = descricao;}
}
