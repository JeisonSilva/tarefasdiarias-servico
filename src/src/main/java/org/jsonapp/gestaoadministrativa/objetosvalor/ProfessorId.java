package org.jsonapp.gestaoadministrativa.objetosvalor;

public class ProfessorId {
    String email;

    public ProfessorId(String email) {
        super();

        this.email = email;
    }

    @Override
    public String toString() {
        return this.email;
    }
}
