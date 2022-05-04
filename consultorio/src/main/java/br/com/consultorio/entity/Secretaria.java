package br.com.consultorio.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.transaction.Transactional;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
*
*
*
 */
@Entity
@Table(schema = "public", name = "secretarias")
@NoArgsConstructor
public class Secretaria extends Pessoa{
    @Getter @Setter
    @Digits(integer = 10, fraction = 2)
    private BigDecimal salario;
    @Getter @Setter
    private LocalDateTime dataContracacao;
    @Getter @Setter
    @Column(nullable = false)
    private String pis;






}
