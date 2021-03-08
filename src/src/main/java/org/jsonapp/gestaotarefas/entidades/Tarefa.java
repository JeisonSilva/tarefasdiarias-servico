package org.jsonapp.gestaotarefas.entidades;

import org.jsonapp.gestaotarefas.exceptions.*;
import org.jsonapp.gestaotarefas.objetosvalor.*;

public class Tarefa {

	private Titulo titulo;
	private AlunoId aluno;
	private DisciplinaId disciplina;
	private double pontuacao;
	private boolean tarefaDisponibilizada;
	private ProfessorId professor;
	private boolean tarefaExcluida;
	private int id;
	private boolean tarefaEntregue;

	public Tarefa(Titulo titulo, AlunoId alunoId, DisciplinaId disciplinaId, double pontuacao)
			throws TituloNuloException, AlunoNuloException, DisciplinaNuloException {
		this.titulo = titulo;
		this.aluno = alunoId;
		this.disciplina = disciplinaId;
		this.pontuacao = pontuacao;

		this.validarDados();
	}

	public Tarefa(
		int id
		, Titulo titulo
		, AlunoId alunoId
		, DisciplinaId disciplinaId
		, double pontuacao
		, boolean tarefaDisponibilizada
		, Boolean tarefaExcluida
		, ProfessorId professorId)
			throws TituloNuloException, AlunoNuloException, DisciplinaNuloException {
		
		this.id = id;
		this.titulo = titulo;
		this.aluno = alunoId;
		this.disciplina = disciplinaId;
		this.pontuacao = pontuacao;
		this.tarefaDisponibilizada = tarefaDisponibilizada;
		this.tarefaExcluida =  tarefaExcluida;
		this.professor = professorId;

		this.validarDados();
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	private void validarDados() throws TituloNuloException, AlunoNuloException, DisciplinaNuloException {
		if(this.titulo == null)
			throw new TituloNuloException();
		
		if(this.aluno == null)
			throw new AlunoNuloException();
		
		if(this.disciplina == null)
			throw new DisciplinaNuloException();
	}

	public Titulo getTitulo() {
		return this.titulo;
	}

	public DisciplinaId getDisciplina() {
		return this.disciplina;
	}

	public AlunoId getAluno() {
		return this.aluno;
	}

	public Double getPontuacao() {
		return this.pontuacao;
	}

	public boolean getDisponibilizadoParaAluno() {
		return this.tarefaDisponibilizada;
	}

	public void setTitulo(Titulo novoTitulo) throws TarefaDisponibilizadaException {
		if(this.getDisponibilizadoParaAluno())
			throw new TarefaDisponibilizadaException();
			
		this.titulo = novoTitulo;
	}

	public void setDisciplina(DisciplinaId novaDisciplina) throws TarefaDisponibilizadaException {
		if(this.getDisponibilizadoParaAluno())
			throw new TarefaDisponibilizadaException();
			
		this.disciplina = novaDisciplina;
	}

	public void setAluno(AlunoId novoAluno) throws TarefaDisponibilizadaException {
		if(this.getDisponibilizadoParaAluno())
			throw new TarefaDisponibilizadaException();
			
		this.aluno = novoAluno;
	}

	public void setPontuacao(double pontuacao) throws TarefaDisponibilizadaException {
		if(this.getDisponibilizadoParaAluno())
			throw new TarefaDisponibilizadaException();
			
		this.pontuacao = pontuacao;
	}

	public void disponibilizarTarefa() {
		this.tarefaDisponibilizada = true;
	}

	public void associarProfessor(ProfessorId professorId) {
		this.professor = professorId;
	}

	public ProfessorId getProfessor() {
		return professor;
	}

	public void excluir() {
		this.tarefaExcluida = true;
	}

	public boolean getTarefaExcluida(){
		return this.tarefaExcluida;
	}

	public boolean getTarefaEntrege(){
		return this.tarefaEntregue;
	}

	public void entregar() {
		this.tarefaDisponibilizada = false;
		this.tarefaEntregue = true;
	}
    
}
