package org.jsonapp.gestaologin.objetosvalor;

public class Senha {
    final String value;

    public Senha(String value) {
        super();

        this.value = value; 
    }

    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Senha) {
            Senha senha = (Senha) obj;
            return this.value.equals(senha.toString());
        }

        return false;
    }

}
