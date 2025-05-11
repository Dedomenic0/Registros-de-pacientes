const route = "http://localhost:8080/admin";
const tabela = document.querySelector("#pacientes");
const reverter = document.querySelector("#reverter");
const deletar = document.querySelector("#deletar");
const idPaciente = document.querySelector("#id_paciente");
const setor = document.querySelector("#setor-deletar");
const deletarSetorBtn = document.querySelector(".excluir-setor")

get();

reverter.addEventListener("click", (e) => {
    e.preventDefault()
    setTimeout(() => get(), 200)
    put(idPaciente.value)
});

deletar.addEventListener("click", (e) => {
    e.preventDefault();
    setTimeout(() => get(), 200)
    delet(idPaciente.value)
});

deletarSetorBtn.addEventListener("submit", (e) => {
    e.preventDefault();
    deletarSetor(setor.value);
    setor.value = "";
})

async function get() {
    tabela.innerHTML="";

    const pacientes = await fetch(`${route}?&sort=id`, {
        method: "GET"
    })
    if(pacientes.status != 200) {
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
        return;
    }
   
};

async function delet(id) {
    const pacientes = await fetch(`${route}/${id}`, {
        method: "DELETE"
    })
    if(pacientes.status != 204) {
        alert("Erro ao deletar")
        return;
    }
};

async function deletarSetor(local) {
    
    const setor = await fetch(`http://localhost:8080/localColeta/${local}`, {
        method: "DELETE"
    });
    if(setor.status != 204) {
        alert("Erro ao deletar")
    }
}