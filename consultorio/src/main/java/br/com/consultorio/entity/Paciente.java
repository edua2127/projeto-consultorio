package br.com.consultorio.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
@Entity
@Table(schema = "public", name = "pacientes")
@NoArgsConstructor
public class Paciente extends Pessoa{
    @Column(name = "tipoPaciente")
    @Enumerated(EnumType.STRING)
    private TipoPaciente tipoPaciente;
    @Getter
    @Setter
    @JoinColumn(name = "convenio_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Convenio convenio;
    @Getter @Setter
    @Column(nullable = false, length = 100)
    private String numeroCartaoConvenio;
    @Getter @Setter
    @Column(nullable = false)
    private Timestamp dataVencimento;


}