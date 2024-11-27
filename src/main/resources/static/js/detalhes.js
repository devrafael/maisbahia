console.log('script de detalhes carregado!')

document.addEventListener("DOMContentLoaded", () => {
    const tabelaContainer2 = document.getElementById("tabela-container2");

    // Recupera os produtos do localStorage
    const listaDetalhesProduto = JSON.parse(localStorage.getItem("produtos"));

    if (!listaDetalhesProduto || listaDetalhesProduto.length === 0) {
        tabelaContainer2.textContent = "Nenhum produto encontrado.";
        return;
    }

    // Cria os elementos da tabela
    const tabela2 = document.createElement("table");
    tabela2.classList.add("table", "table-striped", "table-bordered", "text-center");

    const thead2 = document.createElement("thead");
    const trCabeçalho2 = document.createElement("tr");

    const tituloCampos2 = ["ID", "Nome", "Fabricante", "Data Validade", "Lote", "Quantidade", "Editar", "Excluir"];
    tituloCampos2.forEach(titulo => {
        const th2 = document.createElement("th");
        th2.textContent = titulo;
        trCabeçalho2.appendChild(th2);
    });

    thead2.appendChild(trCabeçalho2);
    tabela2.appendChild(thead2);

    // Cria o corpo da tabela
    const tbody2 = document.createElement("tbody");
    listaDetalhesProduto.forEach((produto, index) => {
        const trCorpo = document.createElement("tr");

        const tdId = document.createElement("td");
        tdId.textContent = index+1;
        trCorpo.appendChild(tdId);

        const tdNome = document.createElement("td");
        tdNome.textContent = produto.nomeProduto;
        trCorpo.appendChild(tdNome);

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

        const tdEditar = document.createElement("td");
        const botaoEditar = document.createElement("button");
       
        
        const tdExcluir = document.createElement("td");
        const botaoExcluir = document.createElement("button");

        const imgEditar = document.createElement("img");
        imgEditar.src = "../img/icons8-editar-24.png"; // Caminho para sua imagem
        imgEditar.alt = "Editar";
        imgEditar.style.width = "20px"; // Ajusta o tamanho da imagem, se necessário
        imgEditar.style.height = "20px";

        botaoEditar.appendChild(imgEditar);
        botaoEditar.classList.add("btn", "btn-primary", "btn-sm");
        botaoEditar.style.borderRadius = "50%";


        const imgExcluir = document.createElement("img");
        imgExcluir.src = "../img/icons8-lixo-24.png"; // Caminho para sua imagem
        imgExcluir.alt = "Excluir";
        imgExcluir.style.width = "20px"; // Ajusta o tamanho da imagem, se necessário
        imgExcluir.style.height = "20px";

        botaoExcluir.appendChild(imgExcluir);
        botaoExcluir.classList.add("btn", "btn-primary", "btn-sm");
        botaoExcluir.style.borderRadius = "50%";

    

        tdEditar.appendChild(botaoEditar);
        tdExcluir.appendChild(botaoExcluir)

        trCorpo.appendChild(tdEditar);
        trCorpo.appendChild(tdExcluir);

        tbody2.appendChild(trCorpo);
    });

    tabela2.appendChild(tbody2);
    tabelaContainer2.appendChild(tabela2);
});
