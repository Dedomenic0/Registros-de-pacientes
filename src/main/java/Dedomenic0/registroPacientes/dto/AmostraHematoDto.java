package Dedomenic0.registroPacientes.dto;

import java.time.LocalDate;

public record AmostraHematoDto(
        LocalDate data,
        String codigoAmostra,
        String localColeta,
        String motivo
) {
}
