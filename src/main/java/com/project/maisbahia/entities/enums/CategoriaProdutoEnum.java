package com.project.maisbahia.entities.enums;

import lombok.Getter;

@Getter
public enum CategoriaProdutoEnum
{
    MASSA(1L, "MASSA"),
    GRAO(2L, "GRAO"),
    HIGIENE_PESSOAL(3L, "HIGIENE_PESSOAL"),
    LIMPEZA(4L, "LIMPEZA"),
    ACOUGUE(5L, "ACOUGUE"),
    FRUTA(6L, "FRUTA"),
    LEGUME(7L, "LEGUME");

    private final Long id;
    private final String nomeCategoria;

    CategoriaProdutoEnum(Long id, String nomeCategoria) {
        this.id = id;
        this.nomeCategoria = nomeCategoria;
    }
}
