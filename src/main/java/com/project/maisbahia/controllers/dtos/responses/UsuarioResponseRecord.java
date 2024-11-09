package com.project.maisbahia.controllers.dtos.responses;
import com.project.maisbahia.entities.usuarios.Perfil;

import java.util.Set;
import java.util.UUID;

public record UsuarioResponseRecord(UUID id, String nome, String login, Set<Perfil> perfil) {

}
