console.log('script buscar produtos carregado')

const token = localStorage.getItem("token")

let paginaAtual = 0;
const tamanhoPagina = 10;  
let totalDePaginas = 0;

document.getElementById('btnBuscar').addEventListener('click', async function () {

    const produtoNome = document.getElementById('inputProduto').value;
    if(produtoNome == ""){
        console.log("produtoNome vazio")
    }
    

   await buscarProdutos(produtoNome, paginaAtual, tamanhoPagina)



});

async function buscarProdutos(produtoNome, paginaAtual, tamanhoPagina){
    const responseListaProdutos = await fetch(`http://localhost:8080/produtos/buscar?nomeProduto=${produtoNome}&page=${paginaAtual}&size=${tamanhoPagina}`, {
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

    const ListaProdutos = await responseListaProdutos.json();
    totalDePaginas = ListaProdutos.totalPages;


    console.log("Produtos encontrados:", ListaProdutos);

    criarTabela(ListaProdutos.content);

    criarBotoesNavegacao();
}


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
        tdId.textContent = index + 1 + paginaAtual * tamanhoPagina;
        tdId.style.width = '100px'; 
        trCorpo.appendChild(tdId);

        const tdNome = document.createElement("td");
        tdNome.textContent = produto;
        trCorpo.appendChild(tdNome);

        trCorpo.addEventListener("click", async function () {
            const nomeProduto = this.dataset.tdNome;
            console.log("nomeProduto: ", nomeProduto)
            detalhesProduto(nomeProduto)

        });

        tbody.appendChild(trCorpo);
    });

    tabela.appendChild(tbody);
    tabela.style.cursor = "pointer"
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

    const listaDetalhesProduto = await responseDetalhesProduto.json();

    console.log("Detalhes dos Produtos encontrados:", listaDetalhesProduto);
    
    window.location.href = `detalhesProduto.html?nomeProduto=${nomeProduto}`;
    localStorage.setItem("produtos", JSON.stringify(listaDetalhesProduto));
   
}

function criarBotoesNavegacao(){
    const tabelaContainer = document.getElementById("tabela-container");

    const navContainer = document.createElement("div");
    navContainer.classList.add("d-flex", "justify-content-between", "mt-3");

    const btnVoltar = document.createElement("button");
    btnVoltar.classList.add("btn", "btn-primary");
    btnVoltar.style.width = "10%";
    btnVoltar.style.fontSize = "20px";
    btnVoltar.style.height = "10%";
    btnVoltar.textContent = "<";
    btnVoltar.disabled = paginaAtual === 0;
    btnVoltar.addEventListener('click', function () {
        if (paginaAtual > 0) {
            paginaAtual--;
            buscarProdutos(document.getElementById('inputProduto').value, paginaAtual, tamanhoPagina);
        }
    });

    const btnProximo = document.createElement("button");
    btnProximo.classList.add("btn", "btn-primary");
    btnProximo.style.width = "10%";
    btnProximo.style.fontSize = "20px"
    btnProximo.style.height = "10%"
    btnProximo.textContent = ">";
    btnProximo.disabled = paginaAtual === totalDePaginas - 1;
    btnProximo.addEventListener('click', function () {
        if (paginaAtual < totalDePaginas - 1) {
            paginaAtual++;
            buscarProdutos(document.getElementById('inputProduto').value, paginaAtual, tamanhoPagina);
        }
    });

    navContainer.appendChild(btnVoltar);
    navContainer.appendChild(btnProximo);

    tabelaContainer.appendChild(navContainer);
}