package org.jsonapp.gestaoadministrativa;

import java.sql.SQLException;

import org.jsonapp.gestaoadministrativa.entidades.Classe;
import org.jsonapp.gestaoadministrativa.objetosvalor.ProfessorId;

public interface IClasseRepository {

	Classe obterPorEmailProfessor(ProfessorId id) throws SQLException;

	void salvar(Classe novaClasse) throws SQLException;

	void atualizar(Classe classe) throws SQLException;
    
}
