package org.jsonapp.gestaotarefas.infra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.enterprise.context.ApplicationScoped;

import org.jsonapp.gestaotarefas.IProfessorRepository;
import org.jsonapp.gestaotarefas.entidades.Professor;
import org.jsonapp.gestaotarefas.objetosvalor.Nome;
import org.jsonapp.gestaotarefas.objetosvalor.ProfessorId;

import io.agroal.api.AgroalDataSource;
import io.agroal.api.AgroalDataSource.FlushMode;

@ApplicationScoped
public class ProfessorRepository implements IProfessorRepository {

    private AgroalDataSource agroalDataSource;

    public ProfessorRepository(AgroalDataSource agroalDataSource) {
        super();
        this.agroalDataSource = agroalDataSource;
    }

    @Override
    public Professor obter(String emailProfessor) throws Exception {
        try {
            PreparedStatement preparedStatement = criarQueryProfessorPorEmail(emailProfessor,
                    this.agroalDataSource.getConnection());
            Professor professor = selectionarProfessor(preparedStatement);
            finalize();
            return professor;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        } catch (Throwable e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Professor obter(ProfessorId professor) throws Exception {
        try {
            PreparedStatement preparedStatement = criarQueryProfessorPorEmail(professor.toString(),
                    this.agroalDataSource.getConnection());
            Professor professorClasse = selectionarProfessor(preparedStatement);
            finalize();
            return professorClasse;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        } catch (Throwable e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void salvar(Professor professor) throws SQLException {
        
        try {
            PreparedStatement preparedStatement = criarInsertSalvarProfessor(professor,
                this.agroalDataSource.getConnection());
            preparedStatement.execute();
            finalize();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private PreparedStatement criarInsertSalvarProfessor(Professor professor,
            Connection connection) throws SQLException {
        
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO professores(email, nome) VALUES(?, ?)");
        PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());
        preparedStatement.setString(1, professor.getEmail());
        preparedStatement.setString(2, professor.getNome());
        
        return preparedStatement;
    }

    private PreparedStatement criarQueryProfessorPorEmail(String emailProfessor, Connection connection)
            throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT p.email as email, p.nome as nome ");
        stringBuilder.append("FROM professores p ");
        stringBuilder.append("WHERE p.email = ?");

        PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());
        preparedStatement.setString(1, emailProfessor);
        return preparedStatement;
    }

    private Professor selectionarProfessor(PreparedStatement preparedStatement) throws SQLException {
        ResultSet dados = preparedStatement.executeQuery();
        Professor professor = null;

        while (dados.next()) {
            ProfessorId professorId = new ProfessorId(dados.getString("email"));
            Nome nome = new Nome(dados.getString("email"));
            professor = new Professor(professorId, nome);
        }

        dados.close();
        return professor;
    }
    
    @Override
    protected void finalize() throws Throwable {
        this.agroalDataSource.flush(FlushMode.ALL);
    }
}
