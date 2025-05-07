import erro from "./popupErros.js";

const route = "http://localhost:8080";

const html = {
    get(elemento) {
        return document.querySelector(elemento)
    }
}
const tabela = html.get("#tabela");

async function fetchUmPaciente(nomePaciente) {
    try {
        await fetch(`${route}/paciente/${nomePaciente}?&sort=data,desc`, {
            method: "GET",
            mode: "cors",
            headers: {
                "Content-Type": "application/json",
            },
        })
            .then(response => response.json())
            .then(data => {
                if(data.length == 0) {
                    erro("Nenhum paciente encontrado")
                }
                data.forEach(data => {
                    const tr = document.createElement("tr");
                    tr.setAttribute("id", data.Id)
                    const dia = data.data.split("-");
                    const diaFormatado = `${dia[2]}/${dia[1]}/${dia[0]}`
                    const diagnostico = data.diagnosticoFinal || "N/A";
                    tr.innerHTML = `
                            <td>${diaFormatado}</td>
                            <td>${data.nome}</td>
                            <td>${data.revisor}</td>
                            <td>${data.achados}</td>
                            <td>${diagnostico}</td>
                        `
                    tabela.appendChild(tr)
                })
            })


    } catch (error) {
        console.error("Erro ao buscar os dados:", error);
    }

}

async function fetchSalvar({dados}) {

    if(dados.data == "" || dados.nome == "" || dados.revisor == "" || dados.achados == "") {
        erro("Preencha todos os campos")
        return;
    }

    try {
        await fetch(`${route}/paciente`, {
            method: "POST",
            mode: "cors",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                data: dados.data,
                nome: dados.nome,
                revisor: dados.revisor,
                achados: dados.achados,
            })
        })
            .then(response => response)
            .then(data => {
            })

    } catch (error) {
        console.error("Erro ao salvar dados:", error);
    }

}

async function fetchSalvarImagem({dados}) {
    if(dados.imagem == null) {
        erro("Selecione uma imagem");
        return;
    }
    const formData = new FormData();
    formData.append("imagem", dados.imagem);
    try {
        await fetch(`${route}/paciente/imagem/${dados.Id}`, {
            method: "POST",
            mode: "cors",
            body: formData
        })
            .then(response => response)
            .then(data => {

            })
    } catch (error) {
        console.error("Erro ao salvar imagem:", error);
    }
}

async function fetchDeletar(id) {

    try {
        await fetch(`${route}/paciente/${id}`, {
            method: "DELETE",
            mode: "cors",
            headers: {
                "Content-Type": "application/json",
            },
        })
            .then(response => response
            )
            .then(data => {
            })

    } catch (error) {
        console.error("Erro ao deletar paciente:", error);
    }
}

async function fetchModificarPaciente(dados) {
    try {
        await fetch(`${route}/paciente/${dados.id}`, {
            method: "PUT",
            mode: "cors",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                data: dados.data,
                nome: dados.nome,
                revisor: dados.revisor,
                achados: dados.achados,
                diagnosticoFinal: dados.diagnosticoFinal,
            })
        })
            .then(response => response)
            .then(data => {
            })

    } catch (error) {
        console.error("Erro ao modificar paciente:", error);
    }
}

async function fetchUmPacienteEditar(id) {
    try {
        await fetch(`${route}/paciente/id/${id}`, {
            method: "GET",
            mode: "cors",
            headers: {
                "Content-Type": "application/json",
            },
        })
            .then(response => response.json())
            .then(data => {
                jogarDadosParaInputs(data);
            })
    } catch (error) {
        console.error("Erro ao buscar os dados:", error);
    }
}

function jogarDadosParaInputs(paciente){
    const diagnostico = paciente.diagnosticoFinal || "N/A";
    html.get("#tabela").innerHTML = `<tr id="editar_paciente_tabela">
    <td><input type="date" id="data_txt" value="${paciente.data}" ></td>
    <td><textarea id="nome_paciente_txt" readonly>${paciente.nome}</textarea></td>
    <td><textarea id="responsavel_txt" >${paciente.revisor}</textarea></td>
    <td><textarea id="achados_txt" >${paciente.achados}</textarea></td>
    <td><textarea id="diagnostico_final_txt">${diagnostico}</textarea></td>
    </tr>
    <div class="div_botao">
    <button id="b_editar">salvar edição</button>
    </div>
    `
    html.get("#b_editar").addEventListener("click", () => {
        const dados = {
            data: html.get("#data_txt").value,
            nome: html.get("#nome_paciente_txt").value,
            revisor: html.get("#responsavel_txt").value,
            achados: html.get("#achados_txt").value,
            diagnosticoFinal: html.get("#diagnostico_final_txt").value,
            id: paciente.Id,
        };
            fetchModificarPaciente(dados)
            tabela.innerHTML = "";
            setTimeout(() => fetchUmPaciente(dados.nome), 100) ;
    })
}

async function fetchPegarImagem(id) {
    try {
        await fetch(`${route}/paciente/imagem/${id}`, {
            method: "GET",
            mode: "cors",
        })
            .then(response => response.blob())
            .then(data => {
                const url = URL.createObjectURL(data);
                if (data.size == 0) {
                    return;
                }
                html.get("#imagem_paciente").src = url;
                showPopup();
            })
    } catch (error) {
        console.error("Erro ao buscar os dados:", error);
    }
}

function showPopup() {
    document.getElementById("overlay").style.display = "block";
    document.getElementById("popup").style.display = "block";
  }

export {fetchPegarImagem, fetchSalvar, fetchUmPaciente, fetchDeletar, fetchUmPacienteEditar, fetchSalvarImagem };