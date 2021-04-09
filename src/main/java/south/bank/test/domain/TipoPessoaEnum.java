package south.bank.test.domain;

import lombok.Getter;

@Getter
public enum TipoPessoaEnum {

    PF("Pessoa Física"),
    PJ("Pessoa Jurídica");

    final String descricao;

    TipoPessoaEnum(String descricao){ this.descricao = descricao;}
}
