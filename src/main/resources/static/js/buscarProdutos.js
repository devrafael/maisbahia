console.log('script buscar produtos carregado')
const token = ""
document.getElementById('btnBuscar').addEventListener('click', async function () {
    const produtoNome = document.getElementById('inputProduto').value;

    const response = await fetch(`http://localhost:8080/produtos`, {
        method: "GET",
        headers: {
            'Authorization': `Bearer ${token}`, // Inclui o token no cabeçalho Authorization
            'Content-Type': 'application/json'
        },
    });

    // Verifica se a resposta foi bem-sucedida
    if (!response.ok) {
        console.error(`Erro: ${response.status} - ${response.statusText}`);
        return;
    }

    // Converte a resposta para JSON
    const produtos = await response.json();

    // Exibe os produtos no console
    console.log("Produtos encontrados:", produtos);

    // Imprime todos os produtos no console, ou pode fazer outro tipo de manipulação
    produtos.forEach(produto => {
        console.log(`Nome do Produto: ${produto.nomeProduto}`);
        console.log(`Fabricante: ${produto.fabricante}`);
        console.log(`Lote: ${produto.lote}`);
        console.log(`Data de Cadastro: ${produto.dataCadastro}`);
        console.log(`Data de Fabricação: ${produto.dataFabricacao}`);
        console.log(`Data de Validade: ${produto.dataValidade}`);
        console.log(`Quantidade: ${produto.quantidade}`);
        console.log(`Categoria: ${produto.categoriaNome}`);
        console.log('-----------------------------------');
    });
});
