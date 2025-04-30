const route = "http://localhost:8080/admin";
const tabela = document.querySelector("#pacientes");
const alerta = document.querySelector("#caixa_alerta");
const reverter = document.querySelector("#reverter");
const deletar = document.querySelector("#deletar");
const idPaciente = document.querySelector("#id_paciente");

get();

reverter.addEventListener("click", () => put(idPaciente.value));
deletar.addEventListener("click", () => delet(idPaciente.value));

async function get() {
    const pacientes = await fetch(`${route}?&sort=id`, {
        method: "GET"
    })
    if(pacientes.status != 200) {
        alerta.textContent = "Erro"
        setTimeout(() => alerta.textContent = "", 2000);
        return;
    }
    pacientes.json().then(data => {
        if(data.length == 0) {
            alert("Nenhum paciente encontrado")
        }
        data.content.forEach(data => {
            const tr = document.createElement("tr");
            tr.innerHTML = `
                        <td>${data.id}</td>
                        <td>${data.nome}</td>
                        <td>${data.deletado}</td>
                    `
            tabela.appendChild(tr);
        });
    })
};



async function put(id) {
    const pacientes = await fetch(`${route}/${id}`, {
        method: "PUT"
    })
    if(pacientes.status != 200) {
        alerta.textContent = "Erro";
        setTimeout(() => alerta.textContent = "", 2000);
        return;
    }
   
};

async function delet(id) {
    const pacientes = await fetch(`${route}/${id}`, {
        method: "DELETE"
    })
    if(pacientes.status != 200) {
        alerta.textContent = "Erro";
        setTimeout(() => alerta.textContent = "", 2000); 
        return;
    }
  
};