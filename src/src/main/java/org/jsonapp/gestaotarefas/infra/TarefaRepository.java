package org.jsonapp.gestaotarefas.infra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.enterprise.context.ApplicationScoped;

import org.jsonapp.gestaotarefas.ITarefaRepository;
import org.jsonapp.gestaotarefas.entidades.Tarefa;
import org.jsonapp.gestaotarefas.exceptions.AlunoNuloException;
import org.jsonapp.gestaotarefas.exceptions.DisciplinaNuloException;
import org.jsonapp.gestaotarefas.exceptions.TituloNuloException;
import org.jsonapp.gestaotarefas.objetosvalor.AlunoId;
import org.jsonapp.gestaotarefas.objetosvalor.DisciplinaId;
import org.jsonapp.gestaotarefas.objetosvalor.ProfessorId;
import org.jsonapp.gestaotarefas.objetosvalor.Titulo;

import io.agroal.api.AgroalDataSource;
import io.agroal.api.AgroalDataSource.FlushMode;

@ApplicationScoped
public class TarefaRepository implements ITarefaRepository {

    private AgroalDataSource agroalDataSource;

    public TarefaRepository(AgroalDataSource agroalDataSource) {
        super();
        this.agroalDataSource = agroalDataSource;
    }

    @Override
    public void salvar(Tarefa tarefa) throws Exception {
        try {
            PreparedStatement preparedStatement = criarInsertTarefa(tarefa, this.agroalDataSource.getConnection());
            preparedStatement.execute();
            finalize();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        } catch (Throwable e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }

    }

    @Override
    public Tarefa obter(int id) throws Exception {
        try {
            PreparedStatement preparedStatement = criarQueryTarefaPorId(id, this.agroalDataSource.getConnection());
            Tarefa tarefa = selectionarTarefa(preparedStatement);
            finalize();
            return tarefa;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        } catch (Throwable e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    

    @Override
    public void atualizar(Tarefa tarefaRegistrada) throws Exception {
        try {
            PreparedStatement preparedStatement = criarUpdateTarefa(tarefaRegistrada, this.agroalDataSource.getConnection());
            preparedStatement.execute();
            finalize();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        } catch (Throwable e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }

    }

    private PreparedStatement criarUpdateTarefa(Tarefa tarefaRegistrada, Connection connection) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UPDATE tarefas SET ");
        stringBuilder.append("titulo=?");
        stringBuilder.append(",pontuacao=?");
        stringBuilder.append(",tarefaDisponibilizada=?");
        stringBuilder.append(",tarefaEntregue=?");
        stringBuilder.append(",tarefaExcluida=?");
        stringBuilder.append(",disciplinas_id=?");
        stringBuilder.append(",alunos_email=?");
        stringBuilder.append(",professores_email=? ");
        stringBuilder.append("WHERE id=?");

        PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());
        preparedStatement.setString(1, tarefaRegistrada.getTitulo().toString());
        preparedStatement.setDouble(2, tarefaRegistrada.getPontuacao());
        preparedStatement.setBoolean(3, tarefaRegistrada.getDisponibilizadoParaAluno());  
        preparedStatement.setBoolean(4, tarefaRegistrada.getTarefaEntrege());        
        preparedStatement.setBoolean(5, tarefaRegistrada.getTarefaExcluida());
        preparedStatement.setInt(6, Integer.parseInt(tarefaRegistrada.getDisciplina().toString()));
        preparedStatement.setString(7, tarefaRegistrada.getAluno().toString());
        preparedStatement.setString(8, tarefaRegistrada.getProfessor().toString());
        preparedStatement.setInt(9, tarefaRegistrada.getId());
        return preparedStatement;
    }

    private PreparedStatement criarInsertTarefa(Tarefa tarefa, Connection connection) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO tarefas");
        stringBuilder.append("(titulo, pontuacao, disciplinas_id, alunos_email, professores_email)");
        stringBuilder.append("VALUES(?, ?, ?, ?, ?)");

        PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());
        preparedStatement.setString(1, tarefa.getTitulo().toString());
        preparedStatement.setDouble(2, tarefa.getPontuacao());
        preparedStatement.setInt(3, Integer.parseInt(tarefa.getDisciplina().toString()));
        preparedStatement.setString(4, tarefa.getAluno().toString());
        preparedStatement.setString(5, tarefa.getProfessor().toString());
        return preparedStatement;
    }

    private PreparedStatement criarQueryTarefaPorId(int id, Connection connection) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT ");
        stringBuilder.append("id, titulo, pontuacao, tarefaDisponibilizada, tarefaExcluida, disciplinas_id, alunos_email, professores_email ");
        stringBuilder.append("FROM tarefas ");
        stringBuilder.append("WHERE id = ? AND tarefaExcluida=0");

        PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }

    private Tarefa selectionarTarefa(PreparedStatement preparedStatement)
            throws SQLException, TituloNuloException, AlunoNuloException, DisciplinaNuloException {
        ResultSet dados = preparedStatement.executeQuery();
        Tarefa tarefa = null;

        while (dados.next()) {
            tarefa = new Tarefa(
                dados.getInt("id"), 
                new Titulo(dados.getString("titulo")),
                new AlunoId(dados.getString("alunos_email")),
                new DisciplinaId(dados.getInt("disciplinas_id")),
                dados.getDouble("pontuacao"),
                dados.getBoolean("tarefaDisponibilizada"),
                dados.getBoolean("tarefaExcluida"),
                new ProfessorId(dados.getString("professores_email")));
        }

        dados.close();
        return tarefa;
    }

    
    @Override
    protected void finalize() throws Throwable {
        this.agroalDataSource.flush(FlushMode.ALL);
    }
    
}
