package Dedomenic0.registroPacientes.dto;

import java.time.LocalDate;

public record AmostraHematoDto(
        Long id,
        LocalDate data,
        String codigoAmostra,
        String localColeta,
        String motivo
) {
}
