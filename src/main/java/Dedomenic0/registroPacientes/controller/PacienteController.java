package Dedomenic0.registroPacientes.controller;

import Dedomenic0.registroPacientes.VerificacaoException;
import Dedomenic0.registroPacientes.domain.Paciente;
import Dedomenic0.registroPacientes.dto.PacienteDto;
import Dedomenic0.registroPacientes.service.ImagenService;
import Dedomenic0.registroPacientes.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    PacienteService service;

    @Autowired
    ImagenService imagenService;

    @GetMapping
    public ResponseEntity<Page<PacienteDto>> pegaPaciente(Pageable pageable) {
        Page<PacienteDto> pacientes = service.pegaPacientes(pageable);
        return ResponseEntity.ok().body(pacientes);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> addPaciente(@RequestPart("paciente") PacienteDto paciente)  {
        service.cadastrarPaciente(paciente);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/imagem/{id}")
    @Transactional
    public ResponseEntity<String> salvarImagem(@RequestPart() MultipartFile imagem, @PathVariable Long id)  {
        try {
        imagenService.salvar(imagem, id);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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

    @GetMapping("/id/{id}")
    public ResponseEntity<PacienteDto> pegaPacientePorId(@PathVariable Long id) {
        try {
            PacienteDto paciente = service.pegaUmPacienteId(id);
            return ResponseEntity.ok().body(paciente);
        } catch (VerificacaoException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> deletePaciente(@PathVariable Long id) {
        try {
            service.deletarPaciente(id);
            return ResponseEntity.noContent().build();
        } catch (VerificacaoException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<String> updatePaciente(@PathVariable Long id, @RequestBody PacienteDto paciente) {
        try {
            Paciente p = service.atualizarPaciente(id, paciente);
            return ResponseEntity.ok().body(p.toString());
        } catch (VerificacaoException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/imagem/{id}")
    public ResponseEntity<Resource> retornarImagem(@PathVariable Long id) {
        try {
            var imagem = imagenService.retornaImagem(id);
            if (imagem == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/png").body(imagem);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
