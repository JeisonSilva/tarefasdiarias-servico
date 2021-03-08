package org.jsonapp.gestaotarefas;

public class TarefaEdicaoDto {
    String titulo;
    String emailAluno;
    int disciplina;
    double pontuacao;

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setEmailAluno(String emailAluno) {
        this.emailAluno = emailAluno;
    }

    public String getEmailAluno() {
        return emailAluno;
    }

    public void setDisciplina(int disciplina) {
        this.disciplina = disciplina;
    }

    public void setPontuacao(double pontuacao) {
        this.pontuacao = pontuacao;
    }

    public double getPontuacao() {
        return pontuacao;
    }

    public int getDisciplina() {
        return disciplina;
    }
}
