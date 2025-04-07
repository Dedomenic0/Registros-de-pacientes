package Dedomenic0.registroPacientes.service;

import Dedomenic0.registroPacientes.VerificacaoException;
import Dedomenic0.registroPacientes.domain.Paciente;
import Dedomenic0.registroPacientes.dto.PacienteDto;
import Dedomenic0.registroPacientes.repository.PacienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository repository;
    @Autowired
    private ImagenService imagenSave;

    public Page<PacienteDto> pegaPacientes(Pageable pageable) {
        try {
            return repository.findAllByDeletadoIsNull(pageable);
        }catch (EntityNotFoundException e){
            throw new VerificacaoException("Paciente não encontrado");
        }
    }

    public void cadastrarPaciente(PacienteDto paciente)  {
        repository.save(new Paciente(paciente));
    }

    public List<PacienteDto> pegaUmPaciente(String nome) {
            List<PacienteDto> pacienteDto = repository.findLikeNome(nome);
            if (pacienteDto == null) {
                throw  new VerificacaoException("Paciente nao encontrado");
            }
            return pacienteDto;

    }

    public PacienteDto pegaUmPacienteId(Long id) {
        PacienteDto pacienteDto = repository.findPacienteById(id);
        if (pacienteDto == null) {
            throw  new VerificacaoException("Paciente nao encontrado");
        }
        return pacienteDto;

    }

    public void deletarPaciente(Long id) {
        Paciente paciente = repository.findAllById(id);
        if (paciente == null) {
            throw new VerificacaoException("Paciente nao encontrado");
        }
        paciente.deletarPaciente();
        repository.save(paciente);
    }

    public Paciente atualizarPaciente(Long id, PacienteDto pacienteDto) {
        Paciente paciente = repository.findAllById(id);
        if (paciente == null) {
            throw new VerificacaoException("Paciente nao encontrado");
        }
        paciente.atualizarPaciente(pacienteDto);
        repository.save(paciente);
        return repository.findAllById(id);
    }
}
