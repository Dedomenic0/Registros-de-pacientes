package Dedomenic0.registroPacientes.controller;

import Dedomenic0.registroPacientes.domain.AmostraHemato;
import Dedomenic0.registroPacientes.domain.AmostraHemostasia;
import Dedomenic0.registroPacientes.service.AmostraHematoService;
import Dedomenic0.registroPacientes.service.AmostraHemostasiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/amostras")
public class AmostrasController {

    @Autowired
    private AmostraHematoService amostraHematoService;

    @Autowired
    private AmostraHemostasiaService amostraHemostasiaService;

    @GetMapping("/hemato")
    public ResponseEntity<Page<AmostraHemato>> listaAmostrasHemato(@PageableDefault(size = 10, sort = "data") Pageable pageable) {
        Page<AmostraHemato> amostrasHemato = amostraHematoService.pegaAmostras(pageable);

        return ResponseEntity.ok().body(amostrasHemato);
    }

    @GetMapping("/hemostasia")
    public ResponseEntity<Page<AmostraHemostasia>> listaAmostrasHemosta(@PageableDefault(size = 10, sort = "data") Pageable pageable) {
        Page<AmostraHemostasia> amostrasHemostasia = amostraHemostasiaService.pegaAmostras(pageable);

        return ResponseEntity.ok().body(amostrasHemostasia);
    }

    @Transactional
    @PostMapping("/hemato")
    public void salvarAmostra(@RequestBody AmostraHemato amostra){
        amostraHematoService.salvaAmostra(amostra);
    }

    @Transactional
    @PostMapping("/hemostasia")
    public void salvarAmostraHemostasia(@RequestBody AmostraHemostasia amostra){
        amostraHemostasiaService.salvaAmostra(amostra);
    }

    @PostMapping("/contagem")
    public ResponseEntity<String> contagem(@RequestBody String dados) {
        String[] data = dados.split(",");
        amostraHematoService.contagem(LocalDate.parse(data[0]), LocalDate.parse(data[1]));
        amostraHematoService.listaAmostraDoMes(LocalDate.parse(data[0]), LocalDate.parse(data[1]));
        amostraHemostasiaService.contagem(LocalDate.parse(data[0]), LocalDate.parse(data[1]));
        amostraHemostasiaService.listaAmostraDoMes(LocalDate.parse(data[0]), LocalDate.parse(data[1]));
        return ResponseEntity.ok().build();
    }
}
