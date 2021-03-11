package org.jsonapp.gestaologin;

public class PerfilDto {
    String email;
    String nome;
    private String emailProfessor;

    public PerfilDto(String email, String nome) {
        super();

        this.email = email;
        this.nome = nome;
    }

    public PerfilDto(String email, String nome, String emailProfessor) {
        super();

        this.email = email;
        this.nome = nome;
        this.emailProfessor = emailProfessor;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getEmailProfessor() {
        return emailProfessor;
    }
}
