package br.com.consultorio.service;

import br.com.consultorio.entity.Convenio;
import br.com.consultorio.entity.Paciente;
import br.com.consultorio.repository.ConvenioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ConvenioService {

    @Autowired
    private ConvenioRepository convenioRepository;

    @Transactional
    public void insert(Convenio convenio) {
        this.convenioRepository.save(convenio);
    }

    @Transactional
    public void update(Long id, Convenio convenio) {
        if( id == convenio.getId()) {
            this.convenioRepository.save(convenio);
        } else {
            throw new RuntimeException();
        }
    }

    @Transactional
    public void updateExcluido(Long id, Convenio convenio) {
        if (id == convenio.getId()) {
            this.convenioRepository.updateStatusExcluido(id, false);
        } else {
            throw new RuntimeException();
        }

    }

    public Optional<Convenio> selectById(Long id) {
        return this.convenioRepository.findById(id);
    }

    public Page<Convenio> selectAll(Pageable pageable) {
        return this.convenioRepository.findAll(pageable);
    }


}
