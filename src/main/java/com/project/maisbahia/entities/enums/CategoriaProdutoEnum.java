package com.project.maisbahia.entities.enums;

import lombok.Getter;

@Getter
public enum CategoriaProdutoEnum
{
    MASSA(1L, "MASSA"),
    GRAOS(2L, "GRAOS"),
    HIGIENE_PESSOAL(3L, "HIGIENE_PESSOAL"),
    LIMPEZA(4L, "LIMPEZA"),
    ACOUGUE(5L, "ACOUGUE"),;

    private final Long id;
    private final String nomeCategoria;

    CategoriaProdutoEnum(Long id, String nomeCategoria) {
        this.id = id;
        this.nomeCategoria = nomeCategoria;
    }
}
