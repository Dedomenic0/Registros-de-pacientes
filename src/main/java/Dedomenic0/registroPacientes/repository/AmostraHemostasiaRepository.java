package Dedomenic0.registroPacientes.repository;

import Dedomenic0.registroPacientes.domain.AmostraHemostasia;
import Dedomenic0.registroPacientes.domain.Motivo;
import Dedomenic0.registroPacientes.dto.AmostraHemostasiaDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AmostraHemostasiaRepository extends JpaRepository<AmostraHemostasia, Integer> {

    List<AmostraHemostasiaDto> findAllByDataBetween(LocalDate dataAfter, LocalDate dataBefore);

    @Query(value = "SELECT count(a) FROM AmostraHemostasia a where a.data between :dataInicio AND :dataFim AND a.localColeta = :local and a.motivo = :motivo")
    Optional<Integer> amostrasPorMes(LocalDate dataInicio, LocalDate dataFim, String local, Motivo motivo);
}
