package org.jsonapp.gestaoadministrativa.entidades;

public class Disciplina {
    int id;
    String descricao;
    private boolean desabilitado;

    public Disciplina(int id, String descricao) {
        super();

        this.id = id;
        this.descricao = descricao;
    }

    public Disciplina(String descricao) {
        super();

        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean getDesabilitado(){
        return this.desabilitado;
    }

	public void alterarDescricao(String descricao) {
        this.descricao = descricao;
	}

	public void desabilitar() {
        this.desabilitado = true;
	}
}
