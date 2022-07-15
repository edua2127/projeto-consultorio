package br.com.consultorio.repository;

import br.com.consultorio.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    @Modifying
    @Query("update Paciente paciente set paciente.ativo = :ativo where paciente.id = :idPassado")
    public void updateStatusExcluido(@Param("ativo") boolean ativo, @Param("idPassado") Long idPassado);
}
