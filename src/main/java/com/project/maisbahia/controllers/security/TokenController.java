package com.project.maisbahia.controllers.security;

import com.project.maisbahia.controllers.records.requests.AutenticacaoRequestRecord;
import com.project.maisbahia.controllers.records.responses.AutenticacaoResponseRecord;
import com.project.maisbahia.entities.usuarios.Usuario;
import com.project.maisbahia.services.UsuarioService;
import com.project.maisbahia.services.security.TokenService;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Slf4j

public class TokenController {

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AutenticacaoResponseRecord> auth(@RequestBody AutenticacaoRequestRecord authRequest){

        var x =  tokenService.getToken(authRequest);

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(x)).getTokenValue();


        Optional<Usuario> u = usuarioService.buscarPorLogin(authRequest);

        tokenService.saveToken(jwtValue, u.get().getId());
        
        return ResponseEntity.ok().body(new AutenticacaoResponseRecord(jwtValue, tokenService.getDateExpirationToken()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody Map<String, String> request, HttpServletResponse response) {
        String token = request.get("token");
        if (token != null && !token.isEmpty()) {
            tokenService.revokeToken(token);
        } else {
            log.info("Token vazio!");
        }
        
        return ResponseEntity.noContent().build();
    }

}
