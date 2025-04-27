package Dedomenic0.registroPacientes.controller;

import Dedomenic0.registroPacientes.domain.LocalColeta;
import Dedomenic0.registroPacientes.service.LocalColetaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/localColeta")
public class LocalColetaController {

    @Autowired
    private LocalColetaService service;

    @Transactional
    @PostMapping
    public ResponseEntity<String> salvarLocalColeta(@RequestBody LocalColeta local){
        service.salvaLocalColeta(local);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<LocalColeta>> pegaLocalColeta(){
        var locais = service.pegaLocais();
        return ResponseEntity.ok().body(locais);
    }

    @Transactional
    @DeleteMapping("/{local}")
    public ResponseEntity<String> deletaLocalColeta(@PathVariable String local){
        service.deletaLocalColeta(local);
        return ResponseEntity.noContent().build();
    }
}
