package org.jsonapp.gestaotarefas.infra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.jsonapp.gestaotarefas.IConsultaTarefaRepository;
import org.jsonapp.gestaotarefas.TarefaConsultadaDto;
import org.jsonapp.gestaotarefas.TarefaEdicaoDto;

import io.agroal.api.AgroalDataSource;
import io.agroal.api.AgroalDataSource.FlushMode;

@ApplicationScoped
public class ConsultaTarefaRepository implements IConsultaTarefaRepository {


    private AgroalDataSource agroalDataSource;

    public ConsultaTarefaRepository(AgroalDataSource agroalDataSource) {
        super();
        this.agroalDataSource = agroalDataSource;
    }

    @Override
    public TarefaEdicaoDto obterTarefaParaEdicao(int id) throws Exception {
        try {
            PreparedStatement preparedStatement = criarQueryTarefaPorId(id, this.agroalDataSource.getConnection());            
            TarefaEdicaoDto tarefaEdicaoDto = selectionarTarefa(preparedStatement);
            finalize();
            return tarefaEdicaoDto;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        } catch (Throwable e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    
    

    @Override
    public List<TarefaConsultadaDto> obterTarefasNaoEnviadasParaAluno(String email) throws Exception {
        try {
            PreparedStatement preparedStatement = criarQueryTarefasNaoEnviadasParaAluno(email,this.agroalDataSource.getConnection());
            List<TarefaConsultadaDto> tarefas = selectionarTarefas(preparedStatement);
            finalize();
            return tarefas;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        } catch (Throwable e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    private PreparedStatement criarQueryTarefasNaoEnviadasParaAluno(String email, Connection connection)
            throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT t.id, t.titulo, p.nome as professor, a.nome as aluno, d.descricao as disciplina, t.pontuacao as pontuacao, t.tarefaEntregue as tarefaEntregue ");
        stringBuilder.append("FROM tarefas t ");
        stringBuilder.append("INNER JOIN alunos a ON t.alunos_email = a.email ");
        stringBuilder.append("INNER JOIN professores p ON t.professores_email = p.email ");
        stringBuilder.append("INNER JOIN disciplinas d ON t.disciplinas_id = d.id ");
        stringBuilder.append("WHERE p.email = ? and tarefaDisponibilizada=0 and tarefaExcluida=0");

        PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());
        preparedStatement.setString(1, email);
        return preparedStatement;
    }

    private List<TarefaConsultadaDto> selectionarTarefas(PreparedStatement preparedStatement)
            throws SQLException {

        List<TarefaConsultadaDto> tarefas = null;
        ResultSet dados = preparedStatement.executeQuery();

        while (dados.next()) {
            if(tarefas == null)
                tarefas = new ArrayList<>(dados.getRow());


            TarefaConsultadaDto tarefa = new TarefaConsultadaDto(
                dados.getInt("id"),
                dados.getString("titulo"),
                dados.getString("professor"),
                dados.getString("aluno"),
                dados.getString("disciplina"),
                dados.getDouble("pontuacao"),
                dados.getBoolean("tarefaEntregue")
            );
            
            tarefas.add(tarefa);
        }

        dados.close();
        return tarefas;
    }

    private PreparedStatement criarQueryTarefaPorId(int id, Connection connection) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT ");
        stringBuilder.append("titulo, pontuacao, disciplinas_id, alunos_email ");
        stringBuilder.append("FROM tarefas ");
        stringBuilder.append("WHERE id = ? AND tarefaExcluida=0");

        PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }

    private TarefaEdicaoDto selectionarTarefa(PreparedStatement preparedStatement) throws SQLException {
        ResultSet dados = preparedStatement.executeQuery();
        TarefaEdicaoDto tarefa = null;

        while (dados.next()) {
            tarefa = new TarefaEdicaoDto();
            tarefa.setTitulo(dados.getString("titulo"));
            tarefa.setDisciplina(dados.getInt("disciplinas_id"));
            tarefa.setEmailAluno(dados.getString("alunos_email"));
            tarefa.setPontuacao(dados.getDouble("pontuacao"));
        }

        dados.close();
        return tarefa;
    }

    @Override
    protected void finalize() throws Throwable {
        this.agroalDataSource.flush(FlushMode.ALL);
    }

    @Override
    public List<TarefaConsultadaDto> obterTarefasDisponibilizadas() throws Exception {
        try {
            PreparedStatement preparedStatement = criarQueryTarefasDisponibilizadas(this.agroalDataSource.getConnection());
            List<TarefaConsultadaDto> tarefas = selectionarTarefas(preparedStatement);
            finalize();
            return tarefas;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        } catch (Throwable e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    private PreparedStatement criarQueryTarefasDisponibilizadas(Connection connection) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT t.id, t.titulo, p.nome as professor, a.nome as aluno, d.descricao as disciplina, t.pontuacao as pontuacao, t.tarefaEntregue as tarefaEntregue ");
        stringBuilder.append("FROM tarefas t ");
        stringBuilder.append("INNER JOIN alunos a ON t.alunos_email = a.email ");
        stringBuilder.append("INNER JOIN professores p ON t.professores_email = p.email ");
        stringBuilder.append("INNER JOIN disciplinas d ON t.disciplinas_id = d.id ");
        stringBuilder.append("WHERE t.tarefaDisponibilizada=1 and t.tarefaExcluida=0");
        PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());
        return preparedStatement;
    }
    
}
