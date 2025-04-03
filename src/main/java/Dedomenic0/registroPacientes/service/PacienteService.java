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

    public Page<PacienteDto> pegaPacientes(Pageable pageable) {
        try {
            return repository.findAllByDeletadoIsNull(pageable);
        }catch (EntityNotFoundException e){
            throw new VerificacaoException("Paciente não encontrado");
        }
    }

    public void cadastrarPaciente(Paciente paciente) {
        repository.save(paciente);
    }

    public List<PacienteDto> pegaUmPaciente(String nome) {
            List<PacienteDto> pacienteDto = repository.findLikeNome(nome);
            if (pacienteDto == null) {
                throw  new VerificacaoException("Paciente nao encontrado");
            }
            return pacienteDto;

    }

    public void deletarPaciente(String nome) {
        Paciente paciente = repository.findPacienteByNome(nome);
        if (paciente == null) {
            throw new VerificacaoException("Paciente nao encontrado");
        }
        paciente.deletarPaciente();
        repository.save(paciente);
    }

    public PacienteDto atualizarPaciente(String nome, PacienteDto pacienteDto) {
        Paciente paciente = repository.findPacienteByNome(nome);
        if (paciente == null) {
            throw new VerificacaoException("Paciente nao encontrado");
        }
        paciente.atualizarPaciente(pacienteDto);
        repository.save(paciente);
        return repository.findByNome(nome);

    }
}
