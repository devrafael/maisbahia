package com.project.maisbahia.controllers.records.requests;

import com.project.maisbahia.entities.enums.PerfilEnum;

public record UsuarioRequestRecord(String nome, String senha, String login, PerfilEnum perfilEnum){
}
