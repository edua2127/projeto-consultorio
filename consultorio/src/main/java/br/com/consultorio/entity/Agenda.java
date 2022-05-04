package br.com.consultorio.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
@Entity
@Table(name = "agendas", schema = "public")
@NoArgsConstructor
public class Agenda extends AbstractEntity{
    @Getter @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "paciente_id")
   private Paciente paciente;

    @Getter @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "medico_id",nullable = false)
   private Medico medico;

    @Getter @Setter
    @Column(nullable = false, name = "status")
    @Enumerated(EnumType.STRING)
   private StatusAgendamento statusAgendamento;

    @Getter @Setter @Column(name = "dataDe", nullable = false)
    private LocalDateTime dataDe;

    @Getter @Setter @Column(name = "dataAte", nullable = false)
    private LocalDateTime dataAte;

    @Getter @Setter @Column(name = "encaixe", columnDefinition = "boolean Default false")
   private Boolean encaixe;


}
