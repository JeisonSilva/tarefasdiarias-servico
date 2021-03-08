package org.jsonapp.gestaotarefas;

public class TarefaDto {
    String titulo;
    String emailProfessor;
    String emailAluno;
    int disciplina;
    double pontuacao;

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setEmailProfessor(String emailProfessor) {
        this.emailProfessor = emailProfessor;
    }

    public String getEmailProfessor() {
        return emailProfessor;
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

    public int getDisciplina() {
        return disciplina;
    }

    public void setPontuacao(double pontuacao) {
        this.pontuacao = pontuacao;
    }

    public double getPontuacao() {
        return pontuacao;
    }
}
