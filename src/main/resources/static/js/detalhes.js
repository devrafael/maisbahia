console.log('script de detalhes carregado!')



const token = localStorage.getItem("token")
let listaDetalhesProduto = JSON.parse(localStorage.getItem("produtos"));
let produtoClicadoExcluir="";
let produtoClicadoEditar=""
const containerNome = document.getElementById("nomeProduto")


document.addEventListener("DOMContentLoaded", () => {
    const tabelaContainer2 = document.getElementById("tabela-container2");

    const primeiroProduto = listaDetalhesProduto[0];
    containerNome.textContent = primeiroProduto.nomeProduto;
    produtoClicadoEditar = listaDetalhesProduto[0].nomeProduto;
    console.log("produtoClicadoEditar: ", produtoClicadoEditar)
    botaoEditar()
  


    const tabela2 = document.createElement("table");
    tabela2.classList.add("table", "table-striped", "table-bordered", "text-center");

    const thead2 = document.createElement("thead");
    const trCabeçalho2 = document.createElement("tr");

    const tituloCampos2 = ["ID", "Fabricante", "Data Validade", "Lote", "Quantidade", "Excluir"];
    tituloCampos2.forEach(titulo => {
        const th2 = document.createElement("th");
        th2.textContent = titulo;
        trCabeçalho2.appendChild(th2);
    });

    thead2.appendChild(trCabeçalho2);
    tabela2.appendChild(thead2);

    const tbody2 = document.createElement("tbody");
    listaDetalhesProduto.forEach((produto, index) => {

        
        const trCorpo = document.createElement("tr");

        const tdId = document.createElement("td");
        tdId.textContent = index+1;
        trCorpo.appendChild(tdId);

        const tdFabricante = document.createElement("td");
        tdFabricante.textContent = produto.fabricante;
        trCorpo.appendChild(tdFabricante);

        const tdDataValidade = document.createElement("td");
        tdDataValidade.textContent = produto.dataValidade;
        trCorpo.appendChild(tdDataValidade);

        const tdLote = document.createElement("td");
        tdLote.textContent = produto.lote;
        trCorpo.appendChild(tdLote);

        const tdQuantidade = document.createElement("td");
        tdQuantidade.textContent = produto.quantidade;
        trCorpo.appendChild(tdQuantidade);
        
        const tdExcluir = document.createElement("td");
        const botaoExcluir = document.createElement("button");
        
        const imgExcluir = document.createElement("img");
        imgExcluir.src = "../img/icons8-lixo-24.png"; // Caminho para sua imagem
        imgExcluir.alt = "Excluir";
        imgExcluir.style.width = "20px"; // Ajusta o tamanho da imagem, se necessário
        imgExcluir.style.height = "20px";

        botaoExcluir.appendChild(imgExcluir);
        botaoExcluir.classList.add("btn", "btn-primary", "btn-sm");
        botaoExcluir.style.borderRadius = "50%";
        botaoExcluir.setAttribute("data-bs-toggle", "modal");
        botaoExcluir.setAttribute("data-bs-target", "#modalExcluir");
        botaoExcluir.addEventListener("click", (e) => {
            produtoClicadoExcluir = produto.idProduto;
            console.log("ID do Produto clicado excluir:", produtoClicadoExcluir);

        });
       
        tdExcluir.appendChild(botaoExcluir)

        trCorpo.appendChild(tdExcluir);

        tbody2.appendChild(trCorpo);
    });

    

    tabela2.appendChild(tbody2);
    tabelaContainer2.appendChild(tabela2);


});


function botaoEditar(){
    const botaoEditar = document.createElement("button");
    const imgEditar = document.createElement("img");
    imgEditar.src = "../img/icons8-editar-24.png"; // Caminho para sua imagem
    imgEditar.alt = "Editar";
    imgEditar.style.width = "20px"; // Ajusta o tamanho da imagem, se necessário
    imgEditar.style.height = "20px";

    botaoEditar.appendChild(imgEditar);
    botaoEditar.classList.add("btn", "btn-primary", "btn-sm");
    botaoEditar.style.borderRadius = "50%";
    botaoEditar.style.marginLeft = "15px"
    botaoEditar.setAttribute("data-bs-toggle", "modal");
    botaoEditar.setAttribute("data-bs-target", "#modalEditar");
    containerNome.appendChild(botaoEditar);

}


document.getElementById('btn-confirmar').addEventListener('click', async function () {

        console.log("ID do Produto clicado após confirmar:", produtoClicadoExcluir);
        
        const responseExcluirProduto = await fetch(`http://localhost:8080/produtos/${produtoClicadoExcluir}`, {
            method: "DELETE",
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
        });
    
        if (!responseExcluirProduto.ok) {
            console.error(`Erro: ${responseExcluirProduto.status} - ${responseExcluirProduto.statusText}`);
            return;
        }else {
            console.log('Produto excluido com sucesso!')
        }

        removerProdutoPorId(produtoClicadoExcluir)


});

function removerProdutoPorId(id) {
    listaDetalhesProduto = listaDetalhesProduto.filter(produto => produto.idProduto !== id);
    localStorage.setItem("produtos", JSON.stringify(listaDetalhesProduto));

}
    


document.getElementById('btn-editar').addEventListener('click', async function () {

        let nomeProduto = "";
        let categoria = "";
        let quantidade = "";
        let lote = ""; 
        let dataValidade = "";
        let dataFabricacao = "";
        let fabricante = "";
    
    
        let nomeProdutoInput = document.getElementById('inputEditar').value.trim();
    
            listaDetalhesProduto.forEach((produto) => {
                produto.nomeProduto = nomeProdutoInput
                
                nomeProduto = produto.nomeProduto
                categoria = produto.categoria
                quantidade = produto.quantidade
                lote = produto.lote
                dataValidade = produto.dataValidade
                dataFabricacao = produto.dataFabricacao
                fabricante = produto.fabricante
    
            });
    
            const requestBody = {
                nomeProduto: nomeProduto,
                categoria: categoria,
                quantidade: quantidade,
                lote: lote,
                dataValidade: dataValidade,
                dataFabricacao: dataFabricacao,
                fabricante: fabricante
            };

            console.log("nomeProduto:", nomeProduto);

        
            const responseEditarProduto = await fetch(`http://localhost:8080/produtos/${produtoClicadoEditar}`, {
                method: "PUT",
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(requestBody)
            });

       
            localStorage.setItem("produtos", JSON.stringify(listaDetalhesProduto));
            
            
});


