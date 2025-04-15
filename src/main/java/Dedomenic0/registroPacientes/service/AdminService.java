package Dedomenic0.registroPacientes.service;

import Dedomenic0.registroPacientes.VerificacaoException;
import Dedomenic0.registroPacientes.domain.Paciente;
import Dedomenic0.registroPacientes.repository.AdminRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository repository;

    public Page<Paciente> pacientesDeletados(Pageable pageable) {
        try {
            return repository.findAllByDeletadoIsNotNull(pageable);
        }catch (EntityNotFoundException e){
            throw new VerificacaoException("Nenhum registro encontrado");
        }
    }

    public void reverterDelecao(Long id) {
        Optional<Paciente> paciente = repository.findById(id);
        if (paciente.isEmpty()) {
            throw new VerificacaoException("Paciente nao encontrado");
        }
        paciente.get().reverterDelecao();
        repository.save(paciente.get());
    }

    public void deletarPermanentemente(Long id) {
        repository.deleteById(id);
    }
}
