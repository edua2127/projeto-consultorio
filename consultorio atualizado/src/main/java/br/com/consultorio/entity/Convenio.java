package br.com.consultorio.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@Entity
@Table(schema = "public", name = "convenios")
@NoArgsConstructor
public class Convenio extends AbstractEntity{
    @Getter @Setter
    @Column(name = "nome", nullable = false, length = 50, unique = true)
    private String nome;
    @Getter @Setter
    @Column(name = "custo", nullable = false)
    private BigDecimal custo;


}
