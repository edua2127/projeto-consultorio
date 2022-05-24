package br.com.consultorio.service;

import br.com.consultorio.entity.Agenda;
import br.com.consultorio.entity.Historico;
import br.com.consultorio.entity.Secretaria;
import br.com.consultorio.entity.StatusAgendamento;
import br.com.consultorio.repository.HistoricoPaciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class HistorioService {
    @Autowired
    private HistoricoPaciente historicoPaciente;

    @Transactional
    public void insert(Historico historico) {
        this.historicoPaciente.save(historico);
    }


}
