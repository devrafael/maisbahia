package com.project.maisbahia.controllers.dtos.responses;
import java.time.Instant;


public record AutenticacaoResponseRecord(String token, Instant dataExpiracaoToken) {

}
