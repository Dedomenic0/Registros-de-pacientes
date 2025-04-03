package Dedomenic0.registroPacientes.repository;

import Dedomenic0.registroPacientes.domain.Paciente;
import Dedomenic0.registroPacientes.dto.PacienteDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    PacienteDto findByNome(String nome);

    Paciente findPacienteByNome(String nome);

    Page<PacienteDto> findAllByDeletadoIsNull(Pageable pageable);

    @Query(value = "SELECT p FROM Paciente p WHERE p.nome like %:nome% AND p.deletado is null")
    List<PacienteDto> findLikeNome(String nome);
}
