const html = {
    get(elemento) {
        return document.querySelector(elemento)
    }
}
const tabela = html.get("#tabela");


async function fetchUmPaciente(nomePaciente) {
    try {
        await fetch(`http://localhost:8080/paciente/${nomePaciente}?&sort=data,desc`, {
            method: "GET",
            mode: "cors",
            headers: {
                "Content-Type": "application/json",
            },
        })
            .then(response => response.json())
            .then(data => {
                if(data.length == 0) {
                    alert("Nenhum paciente encontrado")
                }
                data.forEach(data => {
                    const tr = document.createElement("tr");
                    tr.setAttribute("id", data.Id)
                    const dia = data.data.split("-");
                    const diaFormatado = `${dia[2]}/${dia[1]}/${dia[0]}`
                    tr.innerHTML = `
                            <td>${diaFormatado}</td>
                            <td>${data.nome}</td>
                            <td>${data.revisor}</td>
                            <td>${data.achados}</td>
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
        alert("Preencha todos os campos")
        return;
    }
    
    try {
        await fetch(`http://localhost:8080/paciente`, {
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
        alert("Selecione uma imagem");
        return;
    }
    const formData = new FormData();
    formData.append("imagem", dados.imagem);
    try {
        await fetch(`http://localhost:8080/paciente/imagem/${dados.Id}`, {
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
        await fetch(`http://localhost:8080/paciente/${id}`, {
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
        await fetch(`http://localhost:8080/paciente/${dados.id}`, {
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
        await fetch(`http://localhost:8080/paciente/id/${id}`, {
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
    html.get("#tabela").innerHTML = `<tr>
    <td><input type="date" id="data_txt" value="${paciente.data}" ></td>
    <td><input type="text" id="nome_paciente_txt" value="${paciente.nome}" readonly></td>
    <td><input type="text" id="responsavel_txt" value="${paciente.revisor}" ></td>
    <td><textarea id="achados_txt" >${paciente.achados}</textarea></td>
    </tr>
    <div class="div_botao">
    <button id="b_editar">salvar edição</button>
    </div>
    `
    html.get(".add_novo_botao").innerHTML = ""

    html.get("#b_editar").addEventListener("click", () => {
        const dados = {
            data: html.get("#data_txt").value,
            nome: html.get("#nome_paciente_txt").value,
            revisor: html.get("#responsavel_txt").value,
            achados: html.get("#achados_txt").value,
            id: paciente.Id,
        };
        
            fetchModificarPaciente(dados);
            tabela.innerHTML = "";
            fetchUmPaciente(dados.nome);
    })
}

async function fetchPegarImagem(id) {
    try {
        await fetch(`http://localhost:8080/paciente/imagem/${id}`, {
            method: "GET",
            mode: "cors",
            
        })
            .then(response => response.blob())
            .then(data => {
                const url = URL.createObjectURL(data);
                if (data.size == 0) {
                   alert("Nenhuma imagem encontrada");
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