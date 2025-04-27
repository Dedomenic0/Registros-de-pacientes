package Dedomenic0.registroPacientes.controller;

import Dedomenic0.registroPacientes.domain.AmostraHemato;
import Dedomenic0.registroPacientes.domain.LocalColeta;
import Dedomenic0.registroPacientes.domain.Motivo;
import Dedomenic0.registroPacientes.dto.AmostraHematoDto;
import Dedomenic0.registroPacientes.service.AmostraHematoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/amostras")
public class AmostrasController {

    @Autowired
    private AmostraHematoService amostraHematoService;

    @Transactional
    @PostMapping
    public void salvarAmostra(@RequestBody AmostraHemato amostra){
        amostraHematoService.salvaAmostra(amostra);
    }

    @PostMapping("/contagem")
    public ResponseEntity<String> contagem(@RequestBody LocalDate dataInicio, @RequestBody LocalDate dataFim) {
        amostraHematoService.contagem(dataInicio, dataFim);
        return ResponseEntity.ok().build();
    }
}
