package br.com.consultorio.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AbstractEntity {
     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     @Getter @Setter
    private Long id;

    @Column(name = "cadastro")
    private LocalDateTime cadastro;

    @Column(name = "atualizado")
    private LocalDateTime atualizado;

    @Getter @Setter
    @Column(name = "ativo", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean ativo;

    //bigdecimal -> decimal( 8, 3)
    //localdate -> timestamp

    @PrePersist
    public void dataCadastro() {
        this.atualizado = LocalDateTime.now();
    }

    @PreUpdate
    public void dataAtualizacao() {
        this.atualizado = LocalDateTime.now();
    }

}
