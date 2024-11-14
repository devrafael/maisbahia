package com.project.maisbahia.entities.enums;

import lombok.Getter;

@Getter
public enum CategoriaProdutoEnum
{
    MASSA(1L, "MASSA"),
    GRAOS(2L, "GRAO"),
    HIGIENE_PESSOAL(3L, "HIGIENE_PESSOAL"),
    LIMPEZA(4L, "LIMPEZA"),
    ACOUGUE(5L, "ACOUGUE"),
    FRUTAS(6L, "FRUTA"),
    LEGUMES(7L, "LEGUME");

    private final Long id;
    private final String nomeCategoria;

    CategoriaProdutoEnum(Long id, String nomeCategoria) {
        this.id = id;
        this.nomeCategoria = nomeCategoria;
    }
}
