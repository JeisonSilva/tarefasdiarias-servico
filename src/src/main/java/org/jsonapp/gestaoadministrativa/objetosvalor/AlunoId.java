package org.jsonapp.gestaoadministrativa.objetosvalor;

public class AlunoId {
    String email;

    public AlunoId(String email) {
        super();

        this.email = email;
    }
    
    @Override
    public String toString() {
        return this.email;
    }
    
}
