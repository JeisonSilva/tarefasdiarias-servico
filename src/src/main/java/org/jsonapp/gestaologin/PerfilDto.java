package org.jsonapp.gestaologin;

public class PerfilDto {
    String email;
    String nome;

    public PerfilDto(String email, String nome) {
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
