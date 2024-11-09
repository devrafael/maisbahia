package com.project.maisbahia.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum PerfilEnum {
    GERENTE(1L, "Gerente"),
    ASSISTENTE_LOG(2L, "Assistente de Logistica"),
    COORDENADOR_LOG(3L, "Coordenador de Logistica"),;

    private final Long id;
    private final String nomeCargo;

    PerfilEnum(Long id, String nomeCargo) {
        this.id = id;
        this.nomeCargo = nomeCargo;
    }



}
