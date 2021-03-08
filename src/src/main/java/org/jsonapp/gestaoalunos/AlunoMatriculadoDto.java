package org.jsonapp.gestaoalunos;

public class AlunoMatriculadoDto {
    String email;
    String nome;

    public AlunoMatriculadoDto(String email, String nome) {
        super();

        this.email = email;
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }
}
