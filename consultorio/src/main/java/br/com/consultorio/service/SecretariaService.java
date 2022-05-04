package br.com.consultorio.service;

import br.com.consultorio.entity.Secretaria;
import br.com.consultorio.repository.SecretariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SecretariaService {


    @Autowired
    private SecretariaRepository secretariaRepository;

    @Transactional
    public void insertSecretaria(Secretaria secretaria) {
        secretariaRepository.save(secretaria);
    }

    @Transactional
    public void updateSecretaria(Long id, Secretaria secretaria) {
        if(id == secretaria.getId()) {
            secretariaRepository.save(secretaria);
        } else {
            throw new RuntimeException();
        }
    }

    @Transactional
    public void updateExcluido(LocalDateTime dataExcluido, Long id) {
        this.secretariaRepository.updateExcluido(dataExcluido, id);
    }

    public Optional<Secretaria> selectById(Long id) {
        return secretariaRepository.findById(id);
    }



    public Page<Secretaria> selectAll(Pageable pageable) {
        return secretariaRepository.findAll(pageable);
    }
}
