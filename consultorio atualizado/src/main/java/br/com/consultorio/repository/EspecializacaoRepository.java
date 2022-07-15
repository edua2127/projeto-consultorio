package br.com.consultorio.repository;

import br.com.consultorio.entity.Especialidacao;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface EspecializacaoRepository extends JpaRepository<Especialidacao, Long> {

    @Modifying
    @Query("update Especialidacao especialidacao set especialidacao.ativo = :ativo where especialidacao.id = :especialidacaoId")
    public void updateStatusExcluido(@Param("especialidacaoId") Long idEspecialidade, @Param("ativo")Boolean ativo);
}
