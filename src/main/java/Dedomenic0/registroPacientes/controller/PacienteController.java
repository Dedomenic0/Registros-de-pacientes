package Dedomenic0.registroPacientes.controller;

import Dedomenic0.registroPacientes.VerificacaoException;
import Dedomenic0.registroPacientes.domain.Paciente;
import Dedomenic0.registroPacientes.dto.PacienteDto;
import Dedomenic0.registroPacientes.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    PacienteService service;

    @GetMapping
    public ResponseEntity<Page<PacienteDto>> pegaPaciente(Pageable pageable) {
        Page<PacienteDto> pacientes = service.pegaPacientes(pageable);
        return ResponseEntity.ok().body(pacientes);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> addPaciente(@RequestBody Paciente paciente) {
        service.cadastrarPaciente(paciente);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{nome}")
    public ResponseEntity<List<PacienteDto>> pegaPacientePorNome(@PathVariable String nome) {
        try {
            List<PacienteDto> paciente = service.pegaUmPaciente(nome);
            return ResponseEntity.ok().body(paciente);
        } catch (VerificacaoException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{nome}")
    @Transactional
    public ResponseEntity<String> deletePaciente(@PathVariable String nome) {
        try {
            service.deletarPaciente(nome);
            return ResponseEntity.noContent().build();
        } catch (VerificacaoException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{nome}")
    @Transactional
    public ResponseEntity<String> updatePaciente(@PathVariable String nome, @RequestBody PacienteDto paciente) {
        try {
            PacienteDto p = service.atualizarPaciente(nome, paciente);
            return ResponseEntity.ok().body(p.toString());
        } catch (VerificacaoException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
