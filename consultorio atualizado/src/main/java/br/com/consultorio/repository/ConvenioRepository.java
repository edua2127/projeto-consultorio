package br.com.consultorio.repository;

import br.com.consultorio.entity.Convenio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ConvenioRepository extends JpaRepository<Convenio, Long> {

    @Modifying
    @Query("update Convenio convenio set convenio.excluido = :dataExcluido where convenio.id = :convenioId")
    public void updateStatusExcluido(@Param("convenioId") Long id, @Param("dataExcluido") LocalDateTime dataExcluido);
}
