package Dedomenic0.registroPacientes.service;

import Dedomenic0.registroPacientes.domain.Paciente;
import Dedomenic0.registroPacientes.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImagenService {
    private final String destino = System.getProperty("user.dir") + "\\src\\main\\resources\\storage\\";

    @Autowired
    PacienteRepository pacienteRepository;

    public void salvar(MultipartFile imagem, Long id) throws IOException {

        Optional<Paciente> paciente = pacienteRepository.findById(id);

        String nome = this.gerarNovoNome(imagem.getOriginalFilename());
        String caminho = destino + nome;
        imagem.transferTo(new File(caminho));

        paciente.ifPresent(p -> p.salvarImagem(nome));
    }

    public Resource retornaImagem(Long id) throws IOException {

        Optional<Paciente> paciente = pacienteRepository.findById(id);

        if (paciente.get().getImagem() == null) {
            return null;
        }
        String caminhoImagem = destino + paciente.get().getImagem();
        return new FileUrlResource(caminhoImagem);
    }

    private String gerarNovoNome(String nomeOriginal)
    {
        String[] nomeSplit = nomeOriginal.split("\\.");
        String extensao = "." + nomeSplit[nomeSplit.length - 1];

        return UUID.randomUUID() + extensao;
    }
}
