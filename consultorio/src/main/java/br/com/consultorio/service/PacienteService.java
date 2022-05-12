package br.com.consultorio.service;

import br.com.consultorio.entity.Paciente;
import br.com.consultorio.entity.Secretaria;
import br.com.consultorio.entity.TipoAtendimento;
import br.com.consultorio.repository.PacienteRepository;
import br.com.consultorio.repository.SecretariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;

    public Optional<Paciente> selectById(Long id) {
        return this.pacienteRepository.findById(id);
    }

    public Page<Paciente> selectAll(Pageable pageable) {
        return this.pacienteRepository.findAll(pageable);
    }

    public void insert(Paciente paciente) {
        this.validation(paciente);
        saveTransactional(paciente);
    }

    @Transactional
    public void saveTransactional(Paciente paciente) {
        pacienteRepository.save(paciente);
    }

    public void update(Long id, Paciente paciente) {
        if (id == paciente.getId()) {
            this.validation(paciente);
            this.saveTransactional(paciente);
        } else {
            throw new RuntimeException();
        }
    }

    public void updateStatusExcluido(Long id, Paciente paciente) {
        if (id == paciente.getId()) {
            this.pacienteRepository.updateStatusExcluido(LocalDateTime.now(), paciente.getId());
        } else {
            throw new RuntimeException();
        }
    }

    public void validation(Paciente paciente) {
        //é necessario verificar se o convenio foi informado para evitar erro e null point exception
        if (paciente.getTipoAtendimento().equals(TipoAtendimento.CONVENIO)) {
            if (paciente.getConvenio().getId() == null || paciente.getConvenio() == null) {
                throw new RuntimeException("tipo de atendimento == convenio. convenio não informado");
            }
            if (paciente.getDataVencimento().compareTo(LocalDateTime.now()) <= 0) {
                throw new RuntimeException("data de vencimento espirado");
            }
            if (paciente.getNumeroCartaoConvenio() == null) {
                throw new RuntimeException("tipo de atendimento == convenio. numero do cartão não informado");
            }
            if (paciente.getDataVencimento() == null) {
                throw new RuntimeException("tipo de atendimento == convenio. data de vencimento não informado");
            }
        }
        if (paciente.getTipoAtendimento().equals(TipoAtendimento.PARTICULAR)){
            paciente.setConvenio(null);
            paciente.setDataVencimento(null);
            paciente.setNumeroCartaoConvenio(null);
        }
    }

}
