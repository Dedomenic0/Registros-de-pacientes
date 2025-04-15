package Dedomenic0.registroPacientes.repository;

import Dedomenic0.registroPacientes.domain.Paciente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Paciente, Long> {

    Page<Paciente> findAllByDeletadoIsNotNull(Pageable pageable);

}
