const tabela = document.querySelector(".resposta__tabela__amostras");

pegaAmostras("hemato")

async function pegaAmostras(url) {
    try {
        await fetch (`http://localhost:8080/amostras/${url}`, {
             method: "GET"
         })
         .then(response => response.json())
         .then(data => {
            data.forEach(d => {
                console.log(d)
                criaTags(d)
            })
         })
    } catch(e) {
        console.log(e);
    }
};

function criaTags (dados) {
    const tr = document.createElement("tr");
    const colunas = document.createElement("td");
    colunas.textContent = dados.data;
    tr.appendChild(colunas);
}