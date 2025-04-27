package Dedomenic0.registroPacientes.service;

import Dedomenic0.registroPacientes.domain.LocalColeta;
import Dedomenic0.registroPacientes.repository.LocalColetaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalColetaService {

    private final LocalColetaRepository repository;

    public LocalColetaService(LocalColetaRepository repository) {
        this.repository = repository;
    }

    public List<LocalColeta> pegaLocais() {
        return repository.findAll();
    }

    public void salvaLocalColeta(LocalColeta localColeta) {
        repository.save(localColeta);
    }

    public void deletaLocalColeta(String nome) {
        repository.deleteByLocal(nome);
    }
}
