package com.project.maisbahia.entities.usuarios;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.project.maisbahia.controllers.dtos.requests.AutenticacaoRequestRecord;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "usuario_id")
    private UUID id;

    @Column(unique = true, nullable = false, length = 60)
    private String nome;

    @Column(unique = true, nullable = false, length = 60)
    private String login;

    @Column(length = 60, nullable = false)
    @JsonProperty(access = Access.WRITE_ONLY)
    private String senha;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_usuarios_perfis",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "perfil_id")
    )
    private Set<Perfil> perfis;

    @Column(name = "cargo")
    private String cargo;

    public boolean loginCorreto(AutenticacaoRequestRecord authRequest, BCryptPasswordEncoder passwordEncoder) {
       return passwordEncoder.matches(authRequest.senha(), this.senha);
    }
}
