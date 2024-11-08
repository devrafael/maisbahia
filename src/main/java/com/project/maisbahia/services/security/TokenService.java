package com.project.maisbahia.services.security;

import com.auth0.jwt.exceptions.JWTCreationException;

import com.project.maisbahia.controllers.dtos.requests.AutenticacaoRequest;
import com.project.maisbahia.entities.Perfil;
import com.project.maisbahia.entities.Token;
import com.project.maisbahia.entities.Usuario;
import com.project.maisbahia.repositories.TokenRepository;
import com.project.maisbahia.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TokenService {

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private TokenRepository tokenRepository;


    public JwtClaimsSet getToken(AutenticacaoRequest authRequest) {
        Optional<Usuario> usuario = usuarioService.buscarPorLogin(authRequest);

        return createToken(usuario.orElse(null));
    }

    public JwtClaimsSet createToken(Usuario usuario) {

        try {
            var scopes = usuario.getPerfis()
                    .stream()
                    .map(Perfil::getNome)
                    .collect(Collectors.joining(" "));

            return JwtClaimsSet.builder()
                    .issuer("project-api")
                    .subject(usuario.getId().toString())
                    .issuedAt(Instant.now())
                    .expiresAt(getDateExpirationToken())
                    .claim("scope", scopes)
                    .build();

        }catch (JWTCreationException e){
            throw new RuntimeException("Erro ao tentar gerar o token! " + e.getMessage());
        }
    }

    private Instant dateExpiration() {
        return LocalDateTime.now().plusHours(8).toInstant(ZoneOffset.of("-03:00"));
    }

    public Instant getDateExpirationToken(){
        return dateExpiration();
    }


    public void saveToken(String token, UUID userId) {
        Instant expiration = getDateExpirationToken();
        Token tokenNovo = new Token();
        tokenNovo.setToken(token.toString());
        tokenNovo.setUserId(userId);
        tokenNovo.setExpiration(expiration);
        tokenNovo.setRevoked(false);
        tokenRepository.save(tokenNovo);
    }

    public void revokeToken(String token) {
        Optional<Token> userToken = tokenRepository.findByToken(token);
        System.out.println("TOKEN ENCONTRADO: " + userToken);
        userToken.ifPresent(tokenParaRevogacao -> {
            
            tokenParaRevogacao.setRevoked(true);
            System.out.println("token revogado!!!!!!!!!");
            System.out.println(tokenParaRevogacao.isRevoked());
            tokenRepository.save(tokenParaRevogacao);
        });
    }

    public boolean isTokenValid(String token) {
        // Busca o token no banco de dados
        Optional<Token> optionalToken = tokenRepository.findByToken(token);
        
        if (optionalToken.isPresent()) {
            Token storedToken = optionalToken.get();
            // Verifica se o token foi nao foi revogado ou expirado
            return !storedToken.isRevoked() && !isTokenExpired(storedToken.getExpiration());
        }
        return false; // Token não encontrado ou inválido
    }

 
    public boolean isTokenExpired(Instant expiration) {
        return expiration.isBefore(Instant.now()); // Retorna true se o token já expirou
    }
    
}
