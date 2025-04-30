const link = "http://localhost:8080";

async function novoLocal() {
    let local = document.getElementById("novoLocal").value;

    try {
        await fetch(`${link}/localColeta`,{
                method: "POST",
                mode: "cors",
                headers:{
                    "Content-Type":"application/json",
                },
                body: JSON.stringify({
                    local : local
                })
            }
        )
            .then(response => {
                if(response.status == 200) {
                alert("Local de coleta adicionado com sucesso! \nAtualize a pagina principal");
                }
            })
            
    } catch(err) {
        console.error(err);
        alert("Falha ao conectar com o servidor!");
    }
    local = document.getElementById("novoLocal").value = '';
}