package br.com.consultorio.repository;

import br.com.consultorio.entity.Agenda;
import br.com.consultorio.entity.StatusAgendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long> {


    @Query("from Agenda agenda where agenda.id <> :meuIdAgenda and (agenda.paciente.id = :idPaciente or " +
            "agenda.medico.id = :idMedico)" +
            "and (:dataDe BETWEEN agenda.dataDe and agenda.dataAte " +
            "or :dataAte BETWEEN agenda.dataDe and agenda.dataAte) and agenda.statusAgendamento = :statusAprovado")
    List<Agenda> findAllagendamentosUpdateConflitantes(
            @Param("meuIdAgenda") Long meuIdAgenda,
            @Param("idPaciente") Long idPaciente,
            @Param("dataDe") LocalDateTime dataDe,
            @Param("dataAte") LocalDateTime dataAte,
            @Param("idMedico")Long idmedico,
            @Param("statusAprovado") StatusAgendamento status);





    //verifique se tem algum agendamento com o mesmo medico (CADASTRO)(ainda nao tenho um agendamento)
    @Query("from Agenda agenda where (:dataDe BETWEEN agenda.dataDe and agenda.dataAte " +
            "or :dataAte BETWEEN agenda.dataDe and agenda.dataAte) and agenda.medico.id = :idMedico and agenda.statusAgendamento = :statusAprovado")
    List<Agenda> agendamentoComOMesmoMedicoInsert( @Param("dataDe") LocalDateTime dataDe,
                                                          @Param("dataAte") LocalDateTime dataAte,
                                                          @Param("idMedico") Long idMedico,
                                                          @Param("statusAprovado") StatusAgendamento status);

    @Query("from Agenda agenda where agenda.paciente.id = :idPaciente and (:dataDe BETWEEN agenda.dataDe and agenda.dataAte " +
            "and :dataAte BETWEEN agenda.dataDe and agenda.dataAte) and agenda.statusAgendamento = :statusAprovado")
    List<Agenda> pacienteComAgendamentoComMedicoDiferentesInsert( @Param("idPaciente")Long idPaciente,
                                                                         @Param("dataDe") LocalDateTime dataDe,
                                                                         @Param("dataAte") LocalDateTime dataAte,
                                                                         @Param("statusAprovado") StatusAgendamento status);

    @Modifying
    @Query("update Agenda agenda set agenda.statusAgendamento = :novoStatus where agenda.id = :idAgenda")
    void updateStatus(
            @Param("novoStatus") StatusAgendamento statusAgendamento,
            @Param("idAgenda") Long idAgenda);



    @Modifying
    @Query("update Agenda agenda set agenda.ativo = :ativo where agenda.id = :idPassado")
    void excluido(@Param("ativo") boolean ativo, @Param("idPassado")  Long idPassado);

}
