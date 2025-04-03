import { fetchUmPaciente, fetchSalvar } from "./fetchs.js";

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
    if (e.key =="Enter") {
        tabela.innerHTML = "";
        fetchUmPaciente(html.get("#pesquisa").value);
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

async function fetchData() {

    try {
        await fetch(`http://localhost:8080/paciente?size=${perPage}&page=${page}&sort=data,desc`, {
            method: "GET",
            mode: "cors",
            headers: {
                "Content-Type": "application/json",
            },
        })
            .then(response => response.json())
            .then(data => {
                addDadosTabela(data);
                totalPages = data.totalPages;
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
        console.log(page);
    },
    anterior() {
        page--
        if (page < 0) {
            page++
        }
        tabela.innerHTML = "";
        fetchData();
        console.log(page);
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
        console.log(page);

    }
}

function addDadosTabela(data) {
    console.log(data);

    data.content.forEach(data => {
        const tr = document.createElement("tr")
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
    }
    
createListeners();

function salvarDados() {
    const dados = {
        data: html.get("#data").value,
        nome: html.get("#nome_paciente").value,
        revisor: html.get("#responsavel").value,
        achados: html.get("#achados").value
    }
    fetchSalvar({dados})
    console.log({dados});
}