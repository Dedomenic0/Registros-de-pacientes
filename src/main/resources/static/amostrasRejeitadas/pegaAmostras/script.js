const botaoHemato = document.querySelector("#deletar-hemato");
const botaoHemostasia = document.querySelector("#deletar-hemostasia");
const inputId = document.querySelector(".container__deletar_id");

const tabelaHemato = document.querySelector(".resposta__tabela__amostras-hemato");
const tabelaHemostasia = document.querySelector(".resposta__tabela__amostras-hemostasia");    

pegaAmostras("hemato");
pegaAmostras("hemostasia");

async function pegaAmostras(url) {
    tabelaHemato.innerHTML = "";
    tabelaHemostasia.innerHTML = "";

    try {
        await fetch (`http://localhost:8080/amostras/${url}`, {
             method: "GET"
         })
         .then(response => response.json())
         .then(data => {
            data.content.forEach(d => {
                criaTags(d, url);
            })
         })
    } catch(e) {
        console.log(e);
    }
};

function criaTags (dados, url) {
   
    const tr = document.createElement("tr");
    const colunaId = document.createElement("td");
    const colunaData = document.createElement("td");
    const colunaCodigo = document.createElement("td");
    const colunaLocal = document.createElement("td");
    const colunaMotivo = document.createElement("td");

    colunaId.textContent = dados.id;
    colunaData.textContent = dados.data;
    colunaCodigo.textContent = dados.codigoAmostra;
    colunaLocal.textContent = dados.localColeta;
    colunaMotivo.textContent = dados.motivo;

    tr.appendChild(colunaId);
    tr.appendChild(colunaData);
    tr.appendChild(colunaCodigo);
    tr.appendChild(colunaLocal);
    tr.appendChild(colunaMotivo);

    url === "hemato" ? tabelaHemato.appendChild(tr) : tabelaHemostasia.appendChild(tr)
}

document.addEventListener("click", (c) => {
    if (c.target == botaoHemato) {
        deletaAmostra("hemato", inputId.value);
    }
    if (c.target == botaoHemostasia) {
        deletaAmostra("hemostasia", inputId.value)   
     }
}
)

async function deletaAmostra(url, id) {
    try {
        await fetch (`http://localhost:8080/amostras/${url}/${id}`, {
             method: "DELETE"
         })
         .then(response => 
            console.log(response.status))
         
    } catch(e) {
        console.log(e);
    }
    setTimeout(() => {
        pegaAmostras("hemato");
        pegaAmostras("hemostasia");
    }, 200);
};