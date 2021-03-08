package org.jsonapp.gestaoadministrativa;

public class ListaDeDisciplinasDisponiveisDto {
    int id;
    String descricao;

    public ListaDeDisciplinasDisponiveisDto(int id, String descricao) {
        super();

        this.id = id;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }
    public String getDescricao() {
        return descricao;
    }
}
