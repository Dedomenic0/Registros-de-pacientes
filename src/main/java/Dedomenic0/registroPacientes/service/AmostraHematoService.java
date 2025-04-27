package Dedomenic0.registroPacientes.service;

import Dedomenic0.registroPacientes.domain.AmostraHemato;
import Dedomenic0.registroPacientes.domain.LocalColeta;
import Dedomenic0.registroPacientes.domain.Motivo;
import Dedomenic0.registroPacientes.dto.AmostraHematoDto;
import Dedomenic0.registroPacientes.repository.AmostraHematoRepository;
import Dedomenic0.registroPacientes.repository.LocalColetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AmostraHematoService {

    @Autowired
    private AmostraHematoRepository repository;

    @Autowired
    private LocalColetaRepository localColetaRepository;

    public void salvaAmostra(AmostraHemato amostra) {
        repository.save(new AmostraHemato(amostra));
    }

    public List<AmostraHematoDto> listaAmostraDoMes(LocalDate dataInicio, LocalDate dataFim) {
        return repository.findAllByDataBetween(dataInicio, dataFim);
    }

    public void contagem (LocalDate dataInicio, LocalDate dataFim) {
        List<String> locaisColeta = localColetaRepository.findAll().stream()
                    .map(LocalColeta::getLocal).toList();
        List<String> resultadoFinal = new ArrayList<>();

        for (Motivo motivo : Motivo.values()) {
            for (String local : locaisColeta) {
            Optional<Integer> resultado = repository.amostrasPorMes(dataInicio, dataFim, local, motivo);
                resultado.ifPresent(integer -> resultadoFinal
                        .add(String.format("%s, %s, %d", local, motivo.toString(), integer)));
            }
        }

        System.out.println (resultadoFinal);
    }

}
