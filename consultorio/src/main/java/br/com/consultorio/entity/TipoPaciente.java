package br.com.consultorio.entity;

public enum TipoPaciente {
    PARTICULAR("Particular"), CONVENIO("Convenio");

    private String descricao;

    TipoPaciente(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
