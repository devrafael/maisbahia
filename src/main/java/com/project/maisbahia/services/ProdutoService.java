package com.project.maisbahia.services;


import com.project.maisbahia.controllers.records.requests.ProdutoRequestRecord;
import com.project.maisbahia.controllers.records.responses.ProdutoResponseRecord;
import com.project.maisbahia.entities.enums.CategoriaProdutoEnum;
import com.project.maisbahia.entities.produtos.CategoriaProduto;
import com.project.maisbahia.entities.produtos.Produto;
import com.project.maisbahia.repositories.CategoriaRepository;
import com.project.maisbahia.repositories.ProdutoRepository;
import com.project.maisbahia.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProdutoService
{

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public ProdutoResponseRecord criarProduto(ProdutoRequestRecord request)
    {
        CategoriaProduto categoriaProduto = categoriaRepository.findByCategoriaEnum(
                CategoriaProdutoEnum.valueOf(request.categoria().getCategoriaEnum().name())
        );

        Produto produto = new Produto(
                null,
                categoriaProduto,
                request.quantidade(),
                request.lote(),
                request.dataValidade(),
                request.dataFabricacao(),
                request.fabricante(),
                request.nomeProduto(),
                LocalDateTime.now()
        );
        produto = produtoRepository.save(produto);
        return mapToResponseRecord(produto);
    }

    public List<ProdutoResponseRecord> listarProdutos()
    {
        return produtoRepository.findAll().stream().map(this::mapToResponseRecord).collect(Collectors.toList());
    }

public List<CategoriaProduto> listarCategorias(){
        return categoriaRepository.findAll();
}

    public ProdutoResponseRecord buscarProdutoPorId(UUID id)
    {
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
        return mapToResponseRecord(produto);
    }

    public List<String> filtro(String nomeProduto) {
        return produtoRepository.findDistinctByNomeProdutoContaining(nomeProduto);
    }

    public List<ProdutoResponseRecord> detalhesProduto(String nomeProduto){
        List<Produto> produtos = produtoRepository.findByNomeProduto(nomeProduto);
        return produtos.stream()
                .map(produto -> new ProdutoResponseRecord(
                        produto.getIdProduto(),
                        produto.getNomeProduto(),
                        produto.getCategoria().getCategoriaEnum(),
                        produto.getQuantidade(),
                        produto.getLote(),
                        produto.getDataValidade(),
                        produto.getDataFabricacao(),
                        produto.getFabricante(),
                        produto.getDataCadastro()
                ))
                .collect(Collectors.toList());
    }

    public ProdutoResponseRecord atualizarProduto(UUID id, ProdutoRequestRecord request)
    {
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        produto.setNomeProduto(request.nomeProduto());
        produto.setQuantidade(request.quantidade());
        produto.setLote(request.lote());
        produto.setDataValidade(request.dataValidade());
        produto.setDataFabricacao(request.dataFabricacao());
        produto.setFabricante(request.fabricante());

        produto = produtoRepository.save(produto);
        return mapToResponseRecord(produto);
    }

    public void exluirProduto(UUID id)
    {
        if (!produtoRepository.existsById(id))
        {
            throw new RuntimeException("Produto não encontrado");
        }
        produtoRepository.deleteById(id);
    }

    public ProdutoResponseRecord mapToResponseRecord(Produto produto)
    {
        return new ProdutoResponseRecord(
                produto.getIdProduto(),
                produto.getNomeProduto(),
                produto.getCategoria().getCategoriaEnum(),
                produto.getQuantidade(),
                produto.getLote(),
                produto.getDataValidade(),
                produto.getDataFabricacao(),
                produto.getFabricante(),
                produto.getDataCadastro()
        );
    }
}
