package br.com.consultorio.repository;

import br.com.consultorio.entity.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long> {

    @Query("from Agenda agenda " +
            "where :dataDe BETWEEN agenda.dataDe and agenda.dataAte " +
            "and :dataAte BETWEEN agenda.dataDe and agenda.dataAte")
    public List<Agenda> findAllValidationPaciente(
            @Param("dataDe")LocalDateTime dataDe,
            @Param("dataAte") LocalDateTime dataAte);



    @Query("from Agenda agenda " +
            "where agenda.id <> :idAgendaPassado " +
            "and :dataDe BETWEEN agenda.dataDe and agenda.dataAte " +
            "and :dataAte BETWEEN agenda.dataDe and agenda.dataAte")
    public List<Agenda> findAllValidationUpdate(
            @Param("idAgendaPassado") Long idAgendaPassado,
            @Param("dataDe") LocalDateTime dataDe,
            @Param("dataAte") LocalDateTime dataAte);

    @Modifying
    @Query("update Agenda agenda set agenda.excluido = :dataExcluido where agenda.id = :idPassado")
    public void updateStatusExcluido(@Param("dataExcluido") LocalDateTime dataExcluido,@Param("idPassado")  Long idPassado);

}
