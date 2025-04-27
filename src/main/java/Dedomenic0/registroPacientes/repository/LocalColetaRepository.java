package Dedomenic0.registroPacientes.repository;

import Dedomenic0.registroPacientes.domain.LocalColeta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalColetaRepository extends JpaRepository<LocalColeta, Long> {
    void deleteByLocal(String nome);
}
