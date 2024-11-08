package com.project.maisbahia.controllers.dtos.responses;
import com.project.maisbahia.entities.Perfil;

import java.util.Set;
import java.util.UUID;

public record UsuarioResponse(UUID id, String nome, String login, Set<Perfil> role) {

}
