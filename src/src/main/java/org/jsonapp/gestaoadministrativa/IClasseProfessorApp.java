package org.jsonapp.gestaoadministrativa;

public interface IClasseProfessorApp {

	void criar(ClasseDto classe) throws Exception;

	void substituirProfessor(String email, ProfessorSubstitutoDto professorSubstitutoDto) throws Exception;
    
}
