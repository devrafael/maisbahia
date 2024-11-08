package com.project.maisbahia.entities.enums;

import lombok.Getter;

@Getter
public enum CargoEnum {
    GERENTE(1L, "Gerente"),
    ASSISTENTE_LOG(2L, "Assistente de Logistica"),
    COORDENADOR_LOG(3L, "Coordenador de Logistica"),;

    private final Long id;
    private final String nomeCargo;

    CargoEnum(Long id, String nomeCargo) {
        this.id = id;
        this.nomeCargo = nomeCargo;
    }



}
