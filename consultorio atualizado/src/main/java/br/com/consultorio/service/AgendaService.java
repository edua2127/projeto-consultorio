package br.com.consultorio.service;

import br.com.consultorio.entity.*;
import br.com.consultorio.repository.AgendaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

@Service
public class AgendaService {
    @Autowired
    private AgendaRepository agendaRepository;

    @Autowired
    private HistorioService historioService;

    @Autowired
    private PacienteService pacienteService;

    public Optional<Agenda> selectById(Long id) {
        return this.agendaRepository.findById(id);
    }

    public Page<Agenda> selectAll(Pageable pageable) {
        return this.agendaRepository.findAll(pageable);
    }


    //------------------------------------------------------------------------------------------------------------------

    public void insert(Agenda agenda, Secretaria secretaria) {
        this.validationInsert(agenda, secretaria);
        this.insertTransactional(agenda);
    }

    @Transactional
    public void insertTransactional(Agenda agenda) {
        this.agendaRepository.save(agenda);
    }

    public void validationInsert(Agenda agenda, Secretaria secretaria) {
        //verifique quem esta cadastrando(paciente ou secretaria)
        //paciente que esta cadastrando
        if (secretaria == null) {
            this.encaixeFalsePaciente(agenda);
            this.agendamentoComMesmoMedicoInsert(agenda);
            this.pacienteComAgendamentoComOutrosMedicosInsert(agenda);
            this.validacoesPadroes(agenda);
            this.eDiferenteDePendente(agenda);
        } else { //secretaria que esta cadastrando
            this.encaixeParaInsert(agenda);
            this.validacoesPadroes(agenda);
            this.eDiferenteDeAprovado(agenda);
        }

    }

    public void encaixeFalsePaciente(Agenda agenda) {
        if (agenda.getEncaixe()) {
            throw new RuntimeException();
        }
    }
    public void encaixeParaInsert(Agenda agenda) {
        if (agenda.getEncaixe()) {
            agendamentoComMesmoMedicoInsert(agenda);
            pacienteComAgendamentoComOutrosMedicosInsert(agenda);
        }
    }

    public void eDiferenteDePendente(Agenda agenda) {
        if (!(agenda.getStatusAgendamento().equals(StatusAgendamento.PENDENTE))) {
            throw new RuntimeException();
        }
    }

    public void eDiferenteDeAprovado(Agenda agenda) {
        if (!(agenda.getStatusAgendamento().equals(StatusAgendamento.APROVADO))) {
            throw new RuntimeException();
        }
    }

    public void agendamentoComMesmoMedicoInsert(Agenda agenda) {
        List<Agenda> agendamentos = this.agendaRepository.agendamentoComOMesmoMedicoInsert(
                agenda.getDataDe(),
                agenda.getDataAte(),
                agenda.getMedico().getId(),
                StatusAgendamento.APROVADO);
        if (agendamentos.size() > 0) {
            throw new RuntimeException();
        }
    }

    public void pacienteComAgendamentoComOutrosMedicosInsert(Agenda agenda) {
        List<Agenda> agendamentos = this.agendaRepository.pacienteComAgendamentoComMedicoDiferentesInsert(
                agenda.getPaciente().getId(),
                agenda.getDataDe(),
                agenda.getDataAte(),
                agenda.getMedico().getId(),
                StatusAgendamento.APROVADO);
        if (agendamentos.size() > 0) {
            throw new RuntimeException();
        }
    }

    //------------------------------------------------------------------------------------------------------------------

    @Transactional
    public void excluido(Long id, Agenda agenda) {
        if (agenda.getId().equals(id)) {
            this.agendaRepository.excluido(LocalDateTime.now(), id);
        }

    }

    public void updateStatus(Agenda agenda, Secretaria secretaria, StatusAgendamento novoStatus) {
        this.updateStatusValidation(agenda, secretaria, novoStatus);
        this.updateStatusTransactional(novoStatus, agenda.getId());

        //converte o Optional para a classe Paciente
        Optional<Paciente> tmp = this.pacienteService.selectById(agenda.getPaciente().getId());
        Paciente paciente = tmp.get();
        //cadastro um novo registro no historico
        this.historioService.insert(new Historico(agenda, "update de status realizado", secretaria, paciente, agenda.getStatusAgendamento()));

    }
    @Transactional
    public void updateStatusTransactional(StatusAgendamento statusAgendamento, Long idAgenda) {
        this.agendaRepository.updateStatus(statusAgendamento, idAgenda);
    }

    private void updateStatusValidation(Agenda agenda, Secretaria secretaria, StatusAgendamento novoStatus) {
        switch (novoStatus) {
            case REJEITADO -> this.casoRejeitado(agenda, secretaria);
            case APROVADO -> this.casoAprovado(agenda, secretaria);
            case CANCELADO -> this.casoCancelado(agenda, secretaria);
            case COMPARECEU, NAO_COMPARECEU -> this.casoCompareceuOuNaoCompereu(agenda, secretaria);
        }

    }

    public void casoRejeitado(Agenda agenda, Secretaria secretaria) {
        if (!agenda.getStatusAgendamento().equals(StatusAgendamento.PENDENTE)) {
            throw new RuntimeException();
        }
        if (secretaria == null) {
            throw new RuntimeException();
        }
    }

    public void casoAprovado(Agenda agenda, Secretaria secretaria) {
        if (!agenda.getStatusAgendamento().equals(StatusAgendamento.PENDENTE)) {
            throw new RuntimeException();
        }
        if (secretaria == null) {
            throw new RuntimeException();
        }
    }

    public void casoCancelado(Agenda agenda, Secretaria secretaria) {
        if (!(agenda.getStatusAgendamento().equals(StatusAgendamento.PENDENTE) || agenda.getStatusAgendamento().equals(StatusAgendamento.APROVADO))) {
            throw new RuntimeException();
        }
        if (!(agenda.getPaciente() != null || secretaria != null)) {
            throw new RuntimeException();
        }
    }

    public void casoCompareceuOuNaoCompereu(Agenda agenda, Secretaria secretaria) {
        if (!(agenda.getStatusAgendamento().equals(StatusAgendamento.APROVADO))) {
            throw new RuntimeException();
        }
        if (secretaria == null) {
            throw new RuntimeException();
        }
        if (!(agenda.getDataDe().compareTo(LocalDateTime.now()) < 0 && agenda.getDataAte().compareTo(LocalDateTime.now()) < 0)) {
            throw new RuntimeException();
        }
    }

    //------------------------------------------------------------------------------------------------------------------

    public void update(Long id, Agenda agenda) {
        if (agenda.getId().equals(id)) {
            this.validationUpdate(agenda);
            this.insertTransactional(agenda);
        }
    }

    public void validationUpdate(Agenda agenda) {
        //se é encaxise: e o validador for maior do que 0, o horario informado se sobrepoem com algum outro agendamento
        if (!agenda.getEncaixe()) {

            this.findAllagendamentosUpdateConflitantes(agenda);

        }
        this.validacoesPadroes(agenda);
    }


    public void findAllagendamentosUpdateConflitantes(Agenda agenda) {
        List<Agenda> agendamentos = this.agendaRepository.findAllagendamentosUpdateConflitantes(
                agenda.getId(),
                agenda.getPaciente().getId(),
                agenda.getDataDe(), agenda.getDataAte(),
                agenda.getMedico().getId(),
                StatusAgendamento.APROVADO
        );
        if (agendamentos.size() > 0) {
            throw new RuntimeException();
        }
    }
    //------------------------------------------------------------------------------------------------------------------

    //validacoes presentes no cadastro e update
    public void validacoesPadroes(Agenda agenda) {
        //verificar se o novo agendamento nao foi cadastrado no passado
        if (foiNoPassado(agenda)) {
            throw new RuntimeException();
        }
        //verificar se o inicio do agendamento comeca depois do seu fim
        if (fimAntesDoFim(agenda)) {
            throw new RuntimeException();
        }

        //verificar se o agendamento nao esta fora do horario comercial
        if (ForaDoHorarioComercial(agenda)) {
            throw new RuntimeException();
        }
        //verificar se o agendamento nao cai no final de semana
        if (foiCadastradoNoFimDeSemana(agenda)) {
            throw new RuntimeException();
        }
    }


    //------------------------------------------------------------------------------------------------------------------

    //necessario validacao
    public void necessarioValidacao(Agenda agenda) {
        //não pode existir agendamentos com status de pendente no passado
        if (agenda.getStatusAgendamento().equals(StatusAgendamento.PENDENTE)) {
            if (foiNoPassado(agenda)) {
                throw new RuntimeException();
            }
        }
        //não pode existir agendamentos com status de compareceu ou não compareceu no futuro
        if (statusCompereceuOuNaoCompareceu(agenda)) {
            if (agendamentoNofuturo(agenda)) {
                throw new RuntimeException();
            }
        }
    }

    //------------------------------------------------------------------------------------------------------------------


    public boolean ForaDoHorarioComercial(Agenda agenda) {
        return !(agenda.getDataDe().getHour() >= 8 && agenda.getDataDe().getHour() <= 11 ||
                agenda.getDataDe().getHour() >= 14 && agenda.getDataDe().getHour() <= 17) &&
                (agenda.getDataAte().getHour() >= 8 && agenda.getDataAte().getHour() <= 11 ||
                        agenda.getDataAte().getHour() >= 14 && agenda.getDataAte().getHour() <= 17);
    }

    public boolean foiNoPassado(Agenda agenda) {
        return agenda.getDataDe().compareTo(LocalDateTime.now()) < 0
                || agenda.getDataAte().compareTo(LocalDateTime.now()) < 0;
    }

    public boolean foiCadastradoNoFimDeSemana(Agenda agenda) {
        return agenda.getDataAte().getDayOfWeek().equals(DayOfWeek.SATURDAY) ||
                agenda.getDataAte().getDayOfWeek().equals(DayOfWeek.SUNDAY);
    }

    public boolean statusCompereceuOuNaoCompareceu(Agenda agenda) {
        return agenda.getStatusAgendamento().equals(StatusAgendamento.COMPARECEU)
                || agenda.getStatusAgendamento().equals(StatusAgendamento.NAO_COMPARECEU);
    }

    public boolean agendamentoNofuturo(Agenda agenda) {
        return agenda.getDataAte().compareTo(LocalDateTime.now()) > 0
                || agenda.getDataDe().compareTo(LocalDateTime.now()) > 0;
    }

    public boolean fimAntesDoFim(Agenda agenda) {
        return agenda.getDataAte().compareTo(agenda.getDataDe()) < 0;
    }
}
