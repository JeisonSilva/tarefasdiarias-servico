package org.jsonapp.gestaoadministrativa;

public class ClasseDto {
    int codigo;
    String emailProfessor;

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setEmailProfessor(String emailProfessor) {
        this.emailProfessor = emailProfessor;
    }

    public String getEmailProfessor() {
        return emailProfessor;
    }
}
