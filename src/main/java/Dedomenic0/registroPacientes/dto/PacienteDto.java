package Dedomenic0.registroPacientes.dto;

import Dedomenic0.registroPacientes.domain.Paciente;

import java.time.LocalDate;


public record PacienteDto(
        Long Id,
        LocalDate data,
        String nome,
        String revisor,
        String achados,
        String diagnosticoFinal
) {
    public PacienteDto (Paciente paciente){
        this(paciente.getId(),paciente.getData(), paciente.getNome(),
                paciente.getRevisor(), paciente.getAchados(), paciente.getDiagnosticoFinal());
    }
}
