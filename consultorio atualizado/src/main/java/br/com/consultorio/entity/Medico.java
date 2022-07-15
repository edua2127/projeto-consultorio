package br.com.consultorio.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
@Entity
@Table(schema = "public", name = "medicos")
@NoArgsConstructor //cria um contrutor vazio

public class Medico extends Pessoa{
    @Getter @Setter
    @Column(name = "CRM", nullable = false)
    private String CRM;
    @Getter @Setter
    @Digits(integer = 3, fraction = 2) @Column(name = "porcentagemParticipacao", nullable = false)
    private BigDecimal porcentParticipacao;
    @Getter @Setter
    @Column(name = "consultorio")
    private String consultorio;

    @Getter @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "especializacao_id")
    private Especialidacao especialidacao;





}
