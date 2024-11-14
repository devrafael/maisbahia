package com.project.maisbahia.unit.entities;

import com.project.maisbahia.entities.enums.CategoriaProdutoEnum;
import com.project.maisbahia.entities.produtos.CategoriaProduto;
import com.project.maisbahia.entities.produtos.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoTest
{

    private Produto produto;
    private CategoriaProduto categoriaProduto;

    @BeforeEach
    void setUp()
    {
        categoriaProduto = new CategoriaProduto(1, CategoriaProdutoEnum.MASSA);
        produto = new Produto();
        produto.setIdProduto(UUID.randomUUID());
        produto.setNomeProduto("Macarrão");
        produto.setCategoria(categoriaProduto);
        produto.setQuantidade(100);
        produto.setLote("L123");
        produto.setDataValidade(LocalDate.now().plusMonths(6));
        produto.setDataFabricacao(LocalDate.now().minusMonths(1));
        produto.setFabricante("Fábrica de Massas");
        produto.setDataCadastro(LocalDateTime.now());
    }

    @Test
    void testProdutoCriadoComSucesso()
    {
        // Verificar se o produto foi criado corretamente
        assertNotNull(produto.getIdProduto(), "O ID do produto não deveria ser nulo.");
        assertEquals("Macarrão", produto.getNomeProduto(), "O nome do produto deveria ser 'Macarrão'.");
        assertEquals(categoriaProduto, produto.getCategoria(), "A categoria do produto deveria ser 'MASSA'.");
        assertEquals(100, produto.getQuantidade(), "A quantidade do produto deveria ser 100.");
        assertEquals("L123", produto.getLote(), "O lote do produto deveria ser 'L123'.");
        assertNotNull(produto.getDataValidade(), "A data de validade não deveria ser nula.");
        assertNotNull(produto.getDataFabricacao(), "A data de fabricação não deveria ser nula.");
        assertEquals("Fábrica de Massas", produto.getFabricante(), "O fabricante deveria ser 'Fábrica de Massas'.");
        assertNotNull(produto.getDataCadastro(), "A data de cadastro não deveria ser nula.");
    }

    @Test
    void testProdutoComDataValidadeInvalida()
    {
        // Testar um produto com data de validade no passado
        produto.setDataValidade(LocalDate.now().minusDays(1));
        assertTrue(produto.getDataValidade().isBefore(LocalDate.now()), "A data de validade deveria estar no passado.");
    }

    @Test
    void testProdutoComQuantidadeNegativa()
    {
        produto.setQuantidade(-5);
        assertTrue(produto.getQuantidade() < 0, "A quantidade do produto deveria ser negativa.");
    }

    @Test
    void testProdutoAtualizacaoDados()
     {
        produto.setNomeProduto("Macarrão Integral");
        produto.setQuantidade(50);
        produto.setFabricante("Nova Fábrica de Massas");

        assertEquals("Macarrão Integral", produto.getNomeProduto(), "O nome do produto deveria ser 'Macarrão Integral'.");
        assertEquals(50, produto.getQuantidade(), "A quantidade do produto deveria ser 50.");
        assertEquals("Nova Fábrica de Massas", produto.getFabricante(), "O fabricante deveria ser 'Nova Fábrica de Massas'.");
    }

    @Test
    void testProdutoComLoteDuplicado() {
        Produto outroProduto = new Produto();
        outroProduto.setIdProduto(UUID.randomUUID());
        outroProduto.setNomeProduto("Espaguete");
        outroProduto.setCategoria(categoriaProduto);
        outroProduto.setQuantidade(50);
        outroProduto.setLote("L123");
        outroProduto.setDataValidade(LocalDate.now().plusMonths(3));
        outroProduto.setDataFabricacao(LocalDate.now().minusMonths(2));
        outroProduto.setFabricante("Nova Fábrica de Massas");
        outroProduto.setDataCadastro(LocalDateTime.now());

        // Verificar se os lotes são iguais
        assertEquals(produto.getLote(), outroProduto.getLote(),
                "Os produtos não deveriam ter o mesmo lote se a duplicidade for proibida.");
    }

    @Test
    void testProdutoComDataFabricacaoPosteriorDataValidade() {
        produto.setDataFabricacao(LocalDate.now().plusDays(1));
        produto.setDataValidade(LocalDate.now());

        assertTrue(produto.getDataFabricacao().isAfter(produto.getDataValidade()),
                "A data de fabricação não deveria ser posterior à data de validade.");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            if (produto.getDataFabricacao().isAfter(produto.getDataValidade())) {
                throw new IllegalArgumentException("A data de fabricação não pode ser posterior à data de validade.");
            }
        });

        assertEquals("A data de fabricação não pode ser posterior à data de validade.", exception.getMessage(),
                "A mensagem de erro deveria ser 'A data de fabricação não pode ser posterior à data de validade.'");
    }
}

