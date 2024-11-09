package com.project.maisbahia.entities.usuarios;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.maisbahia.entities.enums.PerfilEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_perfis")
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "perfil_id")
    private Long idPerfil;

    @Column(name = "nome")
    @Enumerated(EnumType.STRING)
    private PerfilEnum nome;

    @ManyToMany(mappedBy = "perfis")
    @JsonIgnore
    private Set<Usuario> usuarios;

}
