package br.com.consultorio.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Pessoa extends AbstractEntity {
    @Column(name = "nome", nullable = false, length = 50)
    @Getter
    @Setter
    private String nome;
    @Column(length = 50)
    @Getter @Setter
    private String telefone;
    @Column(nullable = false, length = 50)
    @Getter @Setter
    private String celular;
    @Column(length = 50)
    @Getter @Setter
    private String nacionalidade;
    @Column(length = 25, nullable = false)
    @Getter @Setter
    private String cpf;
    @Column(length = 25, nullable = false)
    @Getter @Setter
    private String rg;
    @Column(length = 100, nullable = false)
    @Getter @Setter
    private String email;
    @Column(length = 100, nullable = false)
    @Getter @Setter
    private String login;
    @Column(length = 100, nullable = false)
    @Getter
    private String senha;
    @Getter @Setter
    private Sexo sexo;


}
