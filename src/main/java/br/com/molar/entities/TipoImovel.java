package br.com.molar.entities;

public enum TipoImovel {
    APARTAMENTO("APARTAMENTO"),
    CASA("CASA"),
    KITNET("KITNET");

    private final String text;

    TipoImovel(final String text){
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
