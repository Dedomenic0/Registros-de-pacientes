package Dedomenic0.registroPacientes.service;

import Dedomenic0.registroPacientes.domain.AmostraHemato;
import Dedomenic0.registroPacientes.domain.LocalColeta;
import Dedomenic0.registroPacientes.domain.Motivo;
import Dedomenic0.registroPacientes.dto.AmostraHematoDto;
import Dedomenic0.registroPacientes.repository.AmostraHematoRepository;
import Dedomenic0.registroPacientes.repository.LocalColetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AmostraHematoService {

    @Autowired
    private AmostraHematoRepository repository;

    @Autowired
    private LocalColetaRepository localColetaRepository;

    ArquivoService arquivoService = new ArquivoService();

    public void salvaAmostra(AmostraHemato amostra) {
        repository.save(new AmostraHemato(amostra));
    }

    public void listaAmostraDoMes(LocalDate dataInicio, LocalDate dataFim) {
        List<AmostraHematoDto> amostras =  repository.findAllByDataBetween(dataInicio, dataFim);
        List<String> amostraString = new ArrayList<>(amostras.stream().map(a ->
                a.data().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "," + a.codigoAmostra() + "," + a.localColeta() + "," + a.motivo()).toList());
        try {
            arquivoService.gravaExel(amostraString, "amostrasHematoMes_" + dataInicio.getMonthValue());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        amostras.clear();
        amostraString.clear();
    }

    public void contagem (LocalDate dataInicio, LocalDate dataFim) {
        List<String> locaisColeta = new ArrayList<>(localColetaRepository.findAll().stream()
                .map(LocalColeta::getLocal).toList());
        List<String> resultadoFinal = new ArrayList<>();

        for (Motivo motivo : Motivo.values()) {
            for (String local : locaisColeta) {
                Optional<Integer> resultado = repository.amostrasPorMes(dataInicio, dataFim, local, motivo);
                if (resultado.get() != 0) {
                    resultado.ifPresent(integer -> resultadoFinal
                            .add(String.format("%s, %s, %d \n", local, motivo.toString(), integer)));
                }
            }
        }
        try {
        arquivoService.gravaExel(resultadoFinal, "resultadoHemotoMes_" + dataInicio.getMonthValue());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao gravar arquivo");
        }

        resultadoFinal.clear();
        locaisColeta.clear();
    }

    public Page<AmostraHemato> pegaAmostras (Pageable pageable) {
        return repository.findAll(pageable);
    }
}
