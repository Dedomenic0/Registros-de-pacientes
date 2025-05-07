export default function erro (erro) {
    const alertaErro = document.querySelector(".popup_nome-erro");
    alertaErro.innerHTML = erro;

    document.querySelector(".popup-erros").style.display = "block";

    setTimeout(() => {
        document.querySelector(".popup-erros").style.display = "none";
    }, 3000);
}
