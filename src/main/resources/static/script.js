import {fetchPegarImagem, fetchUmPaciente, fetchSalvar, fetchDeletar, fetchUmPacienteEditar, fetchSalvarImagem } from "./fetchs.js";

const route = "http://localhost:8080";
const html = {
    get(elemento) {
        return document.querySelector(elemento)
    }
}

const tabela = html.get("#tabela")
let perPage = 10;
let page = 0;

fetchData();
let totalPages = 0;

html.get("#pesquisa").addEventListener("keydown", (e) => {
    if (e.key == "Enter") {
        if (html.get("#pesquisa").value == "") {
            fetchData();
            return;
        }
        tabela.innerHTML = "";
        fetchUmPaciente(html.get("#pesquisa").value);
        addBotoes();
        deletarPaciente();
        editarPaciente();
        pegaLinhaSelecionada();
    }
})

html.get("#salvar").addEventListener("click", () => {
    salvarDados();
    html.get("#data").value = "";
    html.get("#nome_paciente").value = "";
    html.get("#responsavel").value = "";
    html.get("#achados").value = "";
    tabela.innerHTML = "";
    fetchData();
})

html.get("#achados").addEventListener("keydown", (e) => {
    if (e.key == "Enter") {
        salvarDados();
        html.get("#data").value = "";
        html.get("#nome_paciente").value = "";
        html.get("#responsavel").value = "";
        html.get("#achados").value = "";
        tabela.innerHTML = "";
        fetchData();
    }
})

function deletarPaciente() {

    document.querySelector("#deletar").addEventListener("click", () => {
        const pacienteSelecionado = html.get(".selected");
        if (pacienteSelecionado) {
            fetchDeletar(pacienteSelecionado.id);
            tabela.innerHTML = "";
            fetchData();
        } else {
            alert("Selecione um paciente para deletar.");
        }
    })
}

function editarPaciente() {
    document.querySelector("#editar").addEventListener("click", async () => {
        const pacienteSelecionado = html.get(".selected");

        if (pacienteSelecionado) {
            fetchUmPacienteEditar(pacienteSelecionado.id);

        } else {
            alert("Selecione um paciente para editar.");
        }
    })
}

async function fetchData() {
    tabela.innerHTML = "";
    try {
        await fetch(`${route}/paciente?size=${perPage}&page=${page}&sort=data,desc`, {
            method: "GET",
            mode: "cors",
            headers: {
                "Content-Type": "application/json",
            },
        })
            .then(response => response.json())
            .then(data => {
                pegaLinhaSelecionada();
                addDadosTabela(data);
                totalPages = data.totalPages;
                html.get(".pagina_atual").innerHTML = data.pageable.pageNumber + 1;
            })

    } catch (error) {
        console.error("Erro ao buscar os dados:", error);
    }

}

const paginas = {
    proxima() {
        page++
        if (page >= totalPages) {
            page--
        }
        tabela.innerHTML = "";
        fetchData();
    },
    anterior() {
        page--
        if (page < 0) {
            page++
        }
        tabela.innerHTML = "";
        fetchData();
    },
    setPage(pagina) {
        if (page < 0) {
            page = 0;
        }
        page = pagina;

        if (page > totalPages) {
            page = totalPages;
        }
        tabela.innerHTML = "";
        fetchData();

    }
}

function addDadosTabela(data) {

    data.content.forEach(data => {
        const tr = document.createElement("tr")
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
    const div = html.get(".add_novo_botao");
    div.innerHTML = "";
}

function createListeners() {
    html.get("#primeiro").addEventListener("click", () => {
        paginas.setPage(0)
    }),
        html.get("#ultima").addEventListener("click", () => {
            paginas.setPage(totalPages - 1)
        }),
        html.get("#proximo").addEventListener("click", () => {
            paginas.proxima()
        }),
        html.get("#anterior").addEventListener("click", () => {
            paginas.anterior()
        })

    salvarImagem();
    document.addEventListener("dblclick", (e) => {
        if (e.target.closest("td")) {
            const pacienteSelecionado = html.get(".selected");
            fetchPegarImagem(pacienteSelecionado.id);
        }
    })
}

createListeners();

function salvarDados() {
    const dados = {
        data: html.get("#data").value,
        nome: html.get("#nome_paciente").value,
        revisor: html.get("#responsavel").value,
        achados: html.get("#achados").value,
        imagem: html.get("#imagem").files[0],
    };
    fetchSalvar({ dados });
}

function salvarImagem() {
    html.get(".salvar_imagem_paciente").addEventListener("click", () => {
        const pacienteSelecionado = html.get(".selected");
        if (pacienteSelecionado) {
            const dados = {
                Id: pacienteSelecionado.id,
                imagem: html.get(".enviar_imagem").files[0],
            };
            fetchSalvarImagem({ dados });
            html.get(".enviar_imagem").value = "";
        } else {
            alert("Selecione um paciente para salvar a imagem.");
        }
    })
}

function addBotoes() {
    const div = html.get(".add_novo_botao");
    div.innerHTML = "";
    const deletar = document.createElement("button");
    const editar = document.createElement("button");
    deletar.innerHTML = "Deletar";
    deletar.id = "deletar";
    editar.innerHTML = "Editar";
    editar.id = "editar";
    div.appendChild(deletar);
    div.appendChild(editar);
}

function pegaLinhaSelecionada() {
    const tabelas = document.querySelector("#tabela");
    tabelas.addEventListener("click", (event) => {
        const linha = event.target.closest("tr");
        if (!linha || linha.parentNode.tagName === "THEAD") return;


        tabelas.querySelectorAll("tr").forEach((tr) =>
            tr.classList.remove("selected"));


        linha.classList.add("selected");
        return linha.id;
    });

}

    html.get("#closePopUp").addEventListener("click", () => {
        closePopup();
    });

function closePopup() {
    document.getElementById("overlay").style.display = "none";
    document.getElementById("popup").style.display = "none";
  }
