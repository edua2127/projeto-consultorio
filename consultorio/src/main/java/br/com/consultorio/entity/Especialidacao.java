package br.com.consultorio.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(schema = "public", name = "especialidades")
@NoArgsConstructor
public class Especialidacao extends AbstractEntity{
    @Getter
    @Setter
    @Column(nullable = false, name = "nome")
    private String nome;


}
