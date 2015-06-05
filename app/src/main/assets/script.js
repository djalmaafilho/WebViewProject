function irParaPagina2(){
    JavaScriptInterface.updateProgressBarVisibility(true);
    window.open("./page2.html");
}

function irParaPagina3(){
    JavaScriptInterface.updateProgressBarVisibility(true);
    window.open("http://www.google.com");
}

function toast() {
     JavaScriptInterface.toast("Ola mundo");
}

function vibrar() {
     JavaScriptInterface.vibrarAparelho();
}

function carregarBotaoNome(){
    var nome = JavaScriptInterface.botaoNome();
    var elemento = document.getElementById("btCarregarNome");
    elemento.value = nome;
}

function setarBtAltura(){
    var altura = JavaScriptInterface.dpToPx(40);
    var elemento = document.getElementById("btCarregarAltura");
    elemento.style.height = altura;
}

function init(){
    carregarBotaoNome();
    setarBtAltura();
}

function dialog(){
    JavaScriptInterface.dialog();
}

function sair(){
    JavaScriptInterface.sair();
}

function alertar(){
    alert("Alerta padrão java Script");
}

function spinner(){
    JavaScriptInterface.telaSpinner();
}