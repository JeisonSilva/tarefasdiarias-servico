package org.jsonapp.gestaotarefas.infra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.enterprise.context.ApplicationScoped;

import org.jsonapp.gestaotarefas.IDisciplinaRepository;
import org.jsonapp.gestaotarefas.objetosvalor.DisciplinaId;

import io.agroal.api.AgroalDataSource;
import io.agroal.api.AgroalDataSource.FlushMode;

@ApplicationScoped
public class DisciplinaRepository implements IDisciplinaRepository {

    private AgroalDataSource agroalDataSource;

    public DisciplinaRepository(AgroalDataSource agroalDataSource) {
        super();
        this.agroalDataSource = agroalDataSource;
    }

    @Override
    public DisciplinaId obterDisciplinaId(int disciplina) throws Exception {
        try {
            PreparedStatement preparedStatement = criarQueryDisciplinaPorId(disciplina,this.agroalDataSource.getConnection());
            DisciplinaId id = selectionarDisciplina(preparedStatement);
            
            finalize();
            return id; 
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        } catch (Throwable e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    private PreparedStatement criarQueryDisciplinaPorId(int disciplina, Connection connection) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT d.id as id ");
        stringBuilder.append("FROM disciplinas d ");
        stringBuilder.append("WHERE d.id = ?");

        PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());
        preparedStatement.setInt(1, disciplina);
        return preparedStatement;
    }

    private DisciplinaId selectionarDisciplina(PreparedStatement preparedStatement) throws SQLException {
        ResultSet dados = preparedStatement.executeQuery();
        DisciplinaId id = null;

        while (dados.next()) {
            id = new DisciplinaId(dados.getInt("id"));
        }
        
        dados.close();
        return id;
    }

    @Override
    protected void finalize() throws Throwable {
        this.agroalDataSource.flush(FlushMode.ALL);
    }
    
}
