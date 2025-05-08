package Dedomenic0.registroPacientes.repository;

import Dedomenic0.registroPacientes.domain.AmostraHemato;
import Dedomenic0.registroPacientes.domain.Motivo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AmostraHematoRepository extends JpaRepository<AmostraHemato, Long> {

    List<AmostraHemato> findAllByDataBetween(LocalDate dataAfter, LocalDate dataBefore);

    @Query(value = "SELECT count(a) FROM AmostraHemato a where a.data between :dataInicio AND :dataFim AND a.localColeta = :local and a.motivo = :motivo")
    Optional<Integer> amostrasPorMes(LocalDate dataInicio, LocalDate dataFim, String local, Motivo motivo);

    Page<AmostraHemato> findAll(Pageable pageable);
}
