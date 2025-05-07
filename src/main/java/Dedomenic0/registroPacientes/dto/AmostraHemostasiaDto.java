package Dedomenic0.registroPacientes.dto;

import Dedomenic0.registroPacientes.domain.Motivo;

import java.time.LocalDate;

public record AmostraHemostasiaDto(
        Long id,
        LocalDate data,
        String codigoAmostra,
        String localColeta,
        String motivo
) {
}
