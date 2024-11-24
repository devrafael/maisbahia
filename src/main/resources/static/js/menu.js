console.log('script menu carregado corretamente!');

// Redirecionamento usando JavaScript
document.getElementById("inicio").addEventListener("click", function (event) {
  event.preventDefault(); // Evita o comportamento padrão
  window.location.href = "inicio.html"; // URL de destino
});

document.getElementById("buscar").addEventListener("click", function (event) {
  event.preventDefault(); // Evita o comportamento padrão
  window.location.href = "buscar.html"; // URL de destino
});

document.getElementById("cadastrar").addEventListener("click", function (event) {
  event.preventDefault(); // Evita o comportamento padrão
  window.location.href = "pagina-cadastrar.html"; // URL de destino
});
