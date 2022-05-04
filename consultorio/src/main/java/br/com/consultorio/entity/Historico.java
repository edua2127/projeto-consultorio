package br.com.consultorio.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "historicos", schema = "public")
@NoArgsConstructor
public class Historico extends AbstractEntity{

    @JoinColumn(name = "agenda_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Agenda agenda;

    @Column(name = "observacao")
    private String observacao;

    @JoinColumn(name = "secretaria_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Secretaria secretaria;

    @JoinColumn(name = "paciente_id")
    @ManyToOne(fetch = FetchType.EAGER)//pega todos os dados da secretaria, ao inves de somente o id
    private Paciente paciente;

    @Column(name = "data_historico", nullable = false)
    private LocalDateTime data;
    @Enumerated(EnumType.STRING)
    private StatusAgendamento statusAgenda;


}
