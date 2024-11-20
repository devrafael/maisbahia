console.log('login script carregado!')

document.getElementById("loginForm").addEventListener("submit", function(event) {
    event.preventDefault(); 
    
    const login = document.getElementById("email").value;
    const senha = document.getElementById("senha").value;
 
    const loginData = {
        login: login,
        senha: senha
    };

    fetch("http://localhost:8080/auth", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(loginData)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Falha na autenticação");
        }
        return response.json(); 
    })
    .then(data => {
        if (data.token) {
            localStorage.setItem("token", data.token);
            window.location.href = "inicio.html"
        } else {
            alert("Erro: Token não encontrado na resposta.");
        }
    })
    .catch(error => {
        console.error("Erro na requisição:", error);
        alert("Erro ao fazer login: " + error.message);
    });
});
