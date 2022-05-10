package br.com.consultorio.entity;

public enum TipoAtendimento {
    PARTICULAR("Particular"), CONVENIO("Convenio");

    private String descricao;

    TipoAtendimento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
