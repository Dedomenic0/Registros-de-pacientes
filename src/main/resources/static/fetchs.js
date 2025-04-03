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
                console.log(data);
                if(data.length == 0) {
                    alert("Nenhum paciente encontrado")
                }
                data.forEach(data => {
                    const tr = document.createElement("tr");
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
            .then(response => response.json())
            .then(data => {
                console.log(data);
            })

    } catch (error) {
        console.error("Erro ao salvar dados:", error);
    }

}



export { fetchSalvar, fetchUmPaciente};