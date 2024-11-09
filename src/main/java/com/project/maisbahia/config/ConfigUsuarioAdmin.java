package com.project.maisbahia.config;

import com.project.maisbahia.entities.usuarios.Perfil;
import com.project.maisbahia.entities.usuarios.Usuario;
import com.project.maisbahia.entities.enums.CargoEnum;
import com.project.maisbahia.repositories.PerfilRepository;
import com.project.maisbahia.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;


@Configuration
@Slf4j
public class ConfigUsuarioAdmin implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private PerfilRepository perfilRepositoryitory;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        var roleAdmin = perfilRepositoryitory.findByNome(Perfil.Values.GERENTE.name());
        var usuarioAdmin = usuarioRepository.findByLogin("admin@gmail.com");

        usuarioAdmin.ifPresentOrElse(
                usuario -> log.info("admin ja existe!"),
                () -> {
                    var usuario = new Usuario();
                    usuario.setNome("rafael");
                    usuario.setLogin("admin@gmail.com");
                    usuario.setSenha(passwordEncoder.encode("123"));
                    usuario.setPerfis(Set.of(roleAdmin));
                    usuario.setCargo(CargoEnum.GERENTE.getNomeCargo());
                    usuarioRepository.save(usuario);
                }
        );

    }
}