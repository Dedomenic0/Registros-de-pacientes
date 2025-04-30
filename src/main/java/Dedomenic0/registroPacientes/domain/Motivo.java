package Dedomenic0.registroPacientes.domain;

public enum Motivo {

    AMOSTRA_COAGULADA("Amostra coagulada"),
    VOLUME_INADEQUADO("Volume inadequado"),
    COLETA_EM_TUBO_ERRADO("Coleta em tubo errado"),
    OUTROS("Outros");

    private final String descricao;

    Motivo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return this.descricao;
    }
}
