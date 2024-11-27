console.log('script buscar produtos carregado')

const token = localStorage.getItem("token")

document.getElementById('btnBuscar').addEventListener('click', async function () {



    const produtoNome = document.getElementById('inputProduto').value;

    const responseListaProdutos = await fetch(`http://localhost:8080/produtos/buscar?nomeProduto=${produtoNome}`, {
        method: "GET",
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        },
    });

    if (!responseListaProdutos.ok) {
        console.error(`Erro: ${responseListaProdutos.status} - ${responseListaProdutos.statusText}`);
        return;
    }

    // Converte a resposta para JSON
    const ListaProdutos = await responseListaProdutos.json();

    // Exibe os produtos no console
    console.log("Produtos encontrados:", ListaProdutos);


    criarTabela(ListaProdutos);






});




function criarTabela(ListaProdutos) {
    const tabelaContainer = document.getElementById("tabela-container");
    tabelaContainer.innerHTML = "";

    const tabela = document.createElement("table");
    tabela.classList.add("table", "table-striped", "table-bordered", "text-center")

    const thead = document.createElement("thead");
    const trCabeçalho = document.createElement("tr");

    const tituloCampos = ["ID", "Nome do Produto"];
    tituloCampos.forEach(titulo => {
        const th = document.createElement("th");
        th.textContent = titulo;
        trCabeçalho.appendChild(th);
    });

    thead.appendChild(trCabeçalho);
    tabela.appendChild(thead);


    const tbody = document.createElement("tbody");

    ListaProdutos.forEach((produto, index) => {
        const trCorpo = document.createElement("tr");
        trCorpo.dataset.tdNome = produto;

        const tdId = document.createElement("td");
        tdId.textContent = index + 1;
        trCorpo.appendChild(tdId);

        const tdNome = document.createElement("td");
        tdNome.textContent = produto;
        trCorpo.appendChild(tdNome);


        // Evento de clique na linha (tr)
        trCorpo.addEventListener("click", async function () {
            const nomeProduto = this.dataset.tdNome;
            console.log("nomeProduto: ", nomeProduto)
            detalhesProduto(nomeProduto)

        });

        tbody.appendChild(trCorpo);
    });

    tabela.appendChild(tbody);

    tabelaContainer.appendChild(tabela);
}


async function detalhesProduto(nomeProduto) {
    const responseDetalhesProduto = await fetch(`http://localhost:8080/produtos/buscar/detalhes?nomeProduto=${nomeProduto}`, {
        method: "GET",
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        },
    });

    if (!responseDetalhesProduto.ok) {
        console.error(`Erro: ${responseDetalhesProduto.status} - ${responseDetalhesProduto.statusText}`);
        return;
    }

    // Converte a resposta para JSON
    const listaDetalhesProduto = await responseDetalhesProduto.json();

    // Exibe os produtos no console
    console.log("Detalhes dos Produtos encontrados:", listaDetalhesProduto);
    
    window.location.href = `detalhesProduto.html?nomeProduto=${nomeProduto}`;
    localStorage.setItem("produtos", JSON.stringify(listaDetalhesProduto));

    console.log('console depois de ser redirecionado')
   
}

