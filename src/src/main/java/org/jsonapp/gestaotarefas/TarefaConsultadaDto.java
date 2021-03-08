package org.jsonapp.gestaotarefas;

public class TarefaConsultadaDto {
    final int id;
    final String professor;
    final String aluno;
    final String disciplina;
    final double pontuacao;
    final String titulo;
    private boolean tarefaEntregue;

    public TarefaConsultadaDto(
        int id
        , String titulo
        , String professor
        , String aluno
        , String disciplina
        , double pontuacao
        , boolean tarefaEntregue) {
        super();

        this.id = id;
        this.titulo = titulo;
        this.professor = professor;
        this.aluno = aluno;
        this.disciplina = disciplina;
        this.pontuacao = pontuacao;
        this.tarefaEntregue = tarefaEntregue;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getProfessor() {
        return professor;
    }

    public String getAluno() {
        return aluno;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public double getPontuacao() {
        return pontuacao;
    }

    public boolean getTarefaEntregue(){
        return this.tarefaEntregue;
    }
}
