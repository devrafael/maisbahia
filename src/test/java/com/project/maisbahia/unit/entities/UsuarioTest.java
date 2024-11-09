package com.project.maisbahia.unit.entities;

import com.project.maisbahia.controllers.dtos.requests.AutenticacaoRequestRecord;
import com.project.maisbahia.entities.usuarios.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioTest {

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    private Usuario usuario;
    private AutenticacaoRequestRecord authRequest;

    @BeforeEach
    void setUp() {
        // Inicializar os mocks do Mockito
        MockitoAnnotations.openMocks(this);

        // Criando um usuário de exemplo
        usuario = new Usuario();
        usuario.setId(UUID.randomUUID());
        usuario.setNome("Rafael");
        usuario.setLogin("admin@gmail.com");

        String senhaCodificada = new BCryptPasswordEncoder().encode("senha123");
        usuario.setSenha(senhaCodificada);

        // Criando um objeto de autenticação de exemplo
        authRequest = new AutenticacaoRequestRecord("admin@gmail.com", "senha123");
    }

    @Test
    void testSucessoAoLogar() {
        // Definindo comportamento do mock do passwordEncoder
        when(passwordEncoder.matches(authRequest.senha(), usuario.getSenha())).thenReturn(true);

        // Chamando o método loginCorreto e verificando se retorna true
        boolean resultado = usuario.loginCorreto(authRequest, passwordEncoder);

        assertTrue(resultado, "O login deveria ser bem-sucedido com a senha correta.");
    }

    @Test
    void testFalhaAoLogar() {
        // Definindo comportamento do mock do passwordEncoder para senha incorreta
        when(passwordEncoder.matches(authRequest.senha(), usuario.getSenha())).thenReturn(false);

        // Chamando o método loginCorreto e verificando se retorna false
        boolean resultado = usuario.loginCorreto(authRequest, passwordEncoder);

        assertFalse(resultado, "O login não deveria ser bem-sucedido com a senha incorreta.");
    }
}
