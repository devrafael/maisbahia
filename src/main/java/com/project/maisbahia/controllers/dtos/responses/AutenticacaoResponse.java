package com.project.maisbahia.controllers.dtos.responses;
import java.time.Instant;


public record AutenticacaoResponse(String token, Instant dataExpiracaoToken) {

}
