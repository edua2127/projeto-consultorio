package br.com.consultorio.service;

import br.com.consultorio.entity.Medico;
import br.com.consultorio.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Transactional
    public void insert(Medico medico) {
        this.medicoRepository.save(medico);
    }

    @Transactional
    public void updateMedico(Long id,Medico medico) {
        if(id == medico.getId()) {
            this.medicoRepository.save(medico);
        }
    }

    public void updateDesativar(Long id, Medico medico) {
        medico.setAtivo(false);
        this.updateMedico(id, medico);
    }
    public Optional<Medico> selectById(Long id) {
        return this.medicoRepository.findById(id);
    }

    public Page<Medico> selectAll(Pageable pageable) {
        return this.medicoRepository.findAll(pageable);
    }

    @Transactional
    public void updateExcluido(boolean ativo, Long id) {
        this.medicoRepository.updateExcluido(ativo, id);
    }
}
