const link = "http://localhost:8080";
pegaLocais()



//envia os inputs recebidos para o link pelo metodo post
async function salvarTexto() {
    const texto = document.getElementById("texto").value;
    const coleta = document.getElementById("Coleta").value;
    const motivo = document.getElementById("motivo").value;
    const verificacao = document.getElementById("locais").textContent;
    const rota = document.getElementById("setor").value;
    
    
    //verifica se os campos foram preenchidos corretamente
    if (texto == "" || coleta == "" || motivo == "opt" || rota == "opt") {
        alert("Preencha todos os campos");
        return;
    }
    
    //verifica se o campo local da coleta foi preencido com um local existente
    if (!verificacao.includes(coleta)) {
        alert("Local de coleta não encontrado");
        return;
    }
    let setor = "";
    rota == "hematologia" ? setor = "hemato" : setor = "hemostasia";
    
    try {
        await fetch(`${link}/amostras/${setor}`, {
            method: "POST",
            mode: "cors",
            headers:{
                "Content-Type":"application/json",
            }, 
            body: JSON.stringify({
                codigoAmostra : texto,
                localColeta : coleta,
                motivo : motivo,
            })
        })
        .then(response => 
            console.log(response.status),
            apagarInputs()
        );
        
    }catch (err){
    console.error(err);
    alert("Falha ao conectar com o servidor!");
    }
}


async function enviarFormulario() {
    const data = document.getElementById("data").value;
    const ano = new Date().getFullYear();
    if (data == "12" && new Date().getMonth() == 1) { ano - 1; }
    const dataInicio = new Date(ano, data - 1, 1).toISOString().split("T")[0];
    const dataFinal = new Date(ano, data, 0).toISOString().split("T")[0];


    //verifica o mês informado, se foi informada uma data e se ela é maior que o mês atual
    if (data == "00") {
        alert ("Selecione um mês valido!");
        return;
    };
    
    try {
       await fetch(`${link}/amostras/contagem`,{
            method: "POST",
            mode: "cors",
            headers:{
                "Content-Type":"text/plain",
            }, 
            body: `${dataInicio},${dataFinal}`
        } 
    )
    .then(response => {
        response.status == 200 ? alert("Contagem realizada!") : alert("Falha ao conectar com o servidor!")
        apagarInputs()
    }
)
    
} catch(err) {
    console.error(err);
    alert("Falha ao conectar com o servidor!");
} 
}

//retorna os inputs ao estado padrao após envio
function apagarInputs(){
    document.getElementById("texto").value = "";
    document.getElementById("motivo").value = "opt";
    document.getElementById("Coleta").value = "";
    document.getElementById("setor").value = "opt";
}

//pega os locais pela rota Get e transforma em options no HTML
async function pegaLocais() {    
try {
    await fetch (`${link}/localColeta`, {
        method: "GET",
            mode: "cors",
            headers:{
                "Content-Type":"application/json",
            } 
    })
    .then(response => response.json())
    .then(data => {
        const listaLocais = document.getElementById('locais');

        data.forEach(data => {
        const option = document.createElement('option')
        option.textContent = data.local;
        option.value = data.local
        listaLocais.appendChild(option);
        })
    })
} catch (err) {
    console.error(err);
}
}
