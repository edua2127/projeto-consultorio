package br.com.consultorio.entity;

public enum StatusAgendamento {
    PENDENTE("pendente"),
    APROVADO("aprovado"),
    REJEITADO("rejeitado"),
    CANCELADO("cancelado"),
    COMPARECEU("compareceu"),
    NAO_COMPARECEU("nao_compareceu");

    private String descricao;

    StatusAgendamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
