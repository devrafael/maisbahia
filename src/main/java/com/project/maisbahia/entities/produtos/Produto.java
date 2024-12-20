package com.project.maisbahia.entities.produtos;

import com.project.maisbahia.entities.enums.CategoriaProdutoEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idProduto;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "categoria_nome")
    private CategoriaProduto categoria;

    private Integer quantidade;
    private String lote;
    private LocalDate dataValidade;
    private LocalDate dataFabricacao;
    private String fabricante;
    private String nomeProduto;
    private LocalDateTime dataCadastro;

}
