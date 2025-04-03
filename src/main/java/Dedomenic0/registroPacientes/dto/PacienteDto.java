package Dedomenic0.registroPacientes.dto;

import Dedomenic0.registroPacientes.domain.Paciente;

import java.time.LocalDate;


public record PacienteDto(
        LocalDate data,
        String nome,
        String revisor,
        String achados
) {
    public PacienteDto (Paciente paciente){
        this(paciente.getData(), paciente.getNome(), paciente.getRevisor(), paciente.getAchados());
    }
}
