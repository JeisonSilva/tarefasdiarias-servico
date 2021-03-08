package org.jsonapp.gestaotarefas.entidades;
import org.jsonapp.gestaologin.entidades.Perfil;
import org.jsonapp.gestaotarefas.exceptions.*;
import org.jsonapp.gestaotarefas.objetosvalor.*;

public class Professor extends Perfil{

	final ProfessorId professorId;
	final Nome nome;

	public Professor(ProfessorId professorId, Nome nome) {
		this.professorId = professorId;
		this.nome = nome;
	}

	public String getEmail() {
		return this.professorId.toString();
	}

	public String getNome() {
		return nome.toString();
	}

	public void alterarTitulo(Titulo novoTitulo, Tarefa tarefa) throws TarefaDisponibilizadaException {
		tarefa.setTitulo(novoTitulo);
	}

	public void alterarDisciplina(DisciplinaId novaDisciplina, Tarefa tarefa) throws TarefaDisponibilizadaException {
		tarefa.setDisciplina(novaDisciplina);
	}

	public Tarefa criarNovaTarefa(Titulo titulo, AlunoId aluno, DisciplinaId disciplina, double pontuacao)
			throws TituloNuloException, AlunoNuloException, DisciplinaNuloException {
		Tarefa tarefa = new Tarefa(titulo, aluno, disciplina, pontuacao);
		tarefa.associarProfessor(this.professorId);

		return tarefa;
	}

	public void alterarAluno(AlunoId novoAluno, Tarefa tarefa) throws TarefaDisponibilizadaException {
		tarefa.setAluno(novoAluno);
	}

	public void alterarPontuacao(double pontuacao, Tarefa tarefa) throws TarefaDisponibilizadaException {
		tarefa.setPontuacao(pontuacao);
	}

	public void disponibilizarTarefaParaAluno(Tarefa tarefa) {
		tarefa.disponibilizarTarefa();
	}

	public void excluir(Tarefa tarefa) {
		tarefa.excluir();
	}
    
}
