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
    @Column(name = "tipoPaciente", nullable = false)
    @Enumerated(EnumType.STRING)
    @Getter @Setter
    private TipoAtendimento tipoAtendimento;
    @Getter
    @Setter
    @JoinColumn(name = "convenio_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Convenio convenio;
    @Getter @Setter
    @Column( length = 100)
    private String numeroCartaoConvenio;
    @Getter @Setter
    @Column()
    private LocalDateTime dataVencimento;

    @Override
    public String toString() {
        return "Paciente{" +
                "tipoAtendimento=" + tipoAtendimento +
                ", convenio=" + convenio +
                ", numeroCartaoConvenio='" + numeroCartaoConvenio + '\'' +
                ", dataVencimento=" + dataVencimento +
                '}';
    }
}
