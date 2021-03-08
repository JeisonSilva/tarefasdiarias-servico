package org.jsonapp.gestaoadministrativa.entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jsonapp.gestaoadministrativa.objetosvalor.AlunoId;
import org.jsonapp.gestaoadministrativa.objetosvalor.ProfessorId;
import org.jsonapp.gestaotarefas.entidades.Professor;

public class Classe {
    int codigo;
    ProfessorId professorId;
    LocalDate data;
    List<Matricula> matriculas;

    public Classe(int codigo, ProfessorId professor) {
        super();

        this.codigo = codigo;
        this.professorId = professor;
        this.data = LocalDate.now();
        this.matriculas = new ArrayList<>();
    }

    public int getCodigo() {
        return codigo;
    }

    public String getProfessorId() {
        return professorId.toString();
    }

    public LocalDate getData() {
        return data;
    }

	public void substituirProfessor(Professor professor) {
        this.professorId = new ProfessorId(professor.getEmail());
	}

	public Matricula matricular(AlunoId aluno) {
        Matricula matricula = new Matricula(aluno, this);
        return matricula;
	}

	public void cancelarMatricula(AlunoId aluno) {
        Optional<Matricula> matricula = this.matriculas.stream().filter(x->x.aluno.toString() == aluno.toString()).findFirst();
        
        if(!matricula.isEmpty()){
            matricula.get().cancelar();
        }
    
    }
}
