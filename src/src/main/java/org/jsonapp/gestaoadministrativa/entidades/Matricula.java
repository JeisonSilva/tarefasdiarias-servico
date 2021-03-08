package org.jsonapp.gestaoadministrativa.entidades;

import org.jsonapp.gestaoadministrativa.objetosvalor.AlunoId;
import org.jsonapp.gestaoadministrativa.objetosvalor.MatriculaId;

public class Matricula {
    MatriculaId id;
    AlunoId aluno;
    boolean matriculaCancelada;
    Classe classe;

    public Matricula(AlunoId aluno, Classe classe) {
        this.aluno = aluno;
        this.classe = classe;
    }

    public Matricula(MatriculaId id, AlunoId aluno, Classe classe) {
        this.id = id;
        this.aluno = aluno;
        this.classe = classe;
    }

    public Matricula(MatriculaId id, AlunoId aluno) {
        this.id = id;
        this.aluno = aluno;
    }

    public int getId() {
        return Integer.valueOf(id.toString());
    }

    public int getClasse() {
        return classe.getCodigo();
    }

    public String getAluno() {
        return aluno.toString();
    }
    

    
    public Matricula(AlunoId aluno, boolean cancelada) {
        this.aluno = aluno;
        this.matriculaCancelada = cancelada;
	}

	public void cancelar() {
        matriculaCancelada = true;
	}
}
