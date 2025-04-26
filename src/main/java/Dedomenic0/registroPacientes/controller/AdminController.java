package Dedomenic0.registroPacientes.controller;

import Dedomenic0.registroPacientes.domain.Paciente;
import Dedomenic0.registroPacientes.service.AdminService;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping
    public ResponseEntity<Page<Paciente>> pacientesDeletados(Pageable pageable) {
        Page<Paciente> pacientes = adminService.pacientesDeletados(pageable);
        return ResponseEntity.ok(pacientes);
    }

    @Transactional
    @PutMapping("{id}")
    public ResponseEntity<Paciente> reverterDelecao(@PathVariable Long id) {
        adminService.reverterDelecao(id);
        return ResponseEntity.ok().build();
    }

    @Transactional
    @DeleteMapping("{id}")
    public ResponseEntity<String> deletarPermanete(@PathVariable Long id) {
        adminService.deletarPermanentemente(id);
        return ResponseEntity.noContent().build();
    }
}
