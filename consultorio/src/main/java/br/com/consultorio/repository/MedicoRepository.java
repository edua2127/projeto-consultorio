package br.com.consultorio.repository;

import br.com.consultorio.entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {

    @Modifying
    @Query("update Medico medico set medico.excluido = :dataExcluido where medico.id = :idExcluido")
    public void updateExcluido(@Param("dataExcluido")LocalDateTime dataExcluido,@Param("idExcluido") Long id);

}
