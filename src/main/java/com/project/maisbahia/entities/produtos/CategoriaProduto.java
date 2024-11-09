package com.project.maisbahia.entities.produtos;

import com.project.maisbahia.entities.enums.CategoriaProdutoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Table(name = "tb_categoria_produtos")
public class CategoriaProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCategoria;

    @ManyToOne
    @JoinColumn(name = "produto_id_produto")
    private Produto produto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaProdutoEnum categoria;

}


