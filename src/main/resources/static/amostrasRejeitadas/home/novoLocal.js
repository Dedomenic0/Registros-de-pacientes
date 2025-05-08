const rota = "http://localhost:8080";

const divBotao = document.querySelector(".novo__local");
const divNovoLocal = document.querySelector(".add-novo-local");
const addLocal = document.getElementById("submit");

addLocal.addEventListener("click", (e) => {
    e.preventDefault();
    novoLocal();
})

divBotao.addEventListener("click", () =>{
    divNovoLocal.classList.toggle("hidden");
})

async function novoLocal() {
    let local = document.getElementById("novoLocal").value;
    if (!local) return;

    try {
        await fetch(`${rota}/localColeta`,{
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    local: local
                })
            }
        )
            .then(response => {
                if(response.status === 200) {
                    document.getElementById("novoLocal").value = "";
                    alert("Local de coleta adicionado com sucesso! \nAtualize a pagina principal");
                    divNovoLocal.classList.toggle("hidden");
                }
            })
            
    } catch(err) {
        console.error(err);
        alert("Falha ao conectar com o servidor!");
    }
}