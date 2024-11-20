package com.project.maisbahia.controllers.records.responses;
import java.time.Instant;


public record AutenticacaoResponseRecord(String token, Instant dataExpiracaoToken) {

}
