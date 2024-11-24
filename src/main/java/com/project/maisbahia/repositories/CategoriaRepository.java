package com.project.maisbahia.repositories;

import com.project.maisbahia.entities.enums.CategoriaProdutoEnum;
import com.project.maisbahia.entities.produtos.CategoriaProduto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<CategoriaProduto, Long> {
    CategoriaProduto findByCategoriaEnum(CategoriaProdutoEnum categoriaEnum);
}
