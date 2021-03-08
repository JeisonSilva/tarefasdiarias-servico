package org.jsonapp.gestaoalunos.infra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.jsonapp.gestaoalunos.AlunoMatriculadoDto;
import org.jsonapp.gestaoalunos.IAlunoMatriculadoRepository;
import org.jsonapp.gestaoalunos.objetosvalor.ProfessorId;

import io.agroal.api.AgroalDataSource;
import io.agroal.api.AgroalDataSource.FlushMode;

@ApplicationScoped
public class AlunoMatriculadoRepository implements IAlunoMatriculadoRepository {

    final AgroalDataSource agroalDataSource;

    public AlunoMatriculadoRepository(AgroalDataSource agroalDataSource) {
        super();

        this.agroalDataSource = agroalDataSource;
    }

    @Override
    public List<AlunoMatriculadoDto> obterAlunosMatriculadosPorEmailProfessor(ProfessorId id) throws SQLException {
        List<AlunoMatriculadoDto> alunosMatriculados = null;

        try {
            PreparedStatement preparedStatement = criarQueryRetornarndoAlunosMatriculadosPorProfessor(id,
                this.agroalDataSource.getConnection());
            alunosMatriculados = estanciarListaDeAlunos(preparedStatement);
            finalize();
        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return alunosMatriculados;
    }

    private PreparedStatement criarQueryRetornarndoAlunosMatriculadosPorProfessor(ProfessorId id,
            Connection connection) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT a.email, a.nome  ");
        stringBuilder.append("FROM alunos a ");
        stringBuilder.append("INNER JOIN matriculas m ON a.email = m.alunos_email ");
        stringBuilder.append("INNER JOIN classes c ON m.classes_codigo = c.codigo ");
        stringBuilder.append("INNER JOIN professores p ON c.professores_email = p.email ");
        stringBuilder.append("WHERE p.email = ?");
        PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());
        preparedStatement.setString(1, id.toString());

        return preparedStatement;
    }

    private List<AlunoMatriculadoDto> estanciarListaDeAlunos(PreparedStatement preparedStatement) throws SQLException {
        ResultSet dados = preparedStatement.executeQuery();
        List<AlunoMatriculadoDto> listaAlunosmatriculados = new ArrayList<>(dados.getRow());
        while (dados.next()){
            AlunoMatriculadoDto alunoMatriculadoDto = new AlunoMatriculadoDto(dados.getString("email"), dados.getString("nome"));
            listaAlunosmatriculados.add(alunoMatriculadoDto);
        }
        
        return listaAlunosmatriculados;
    }

    @Override
    protected void finalize() throws Throwable {
        this.agroalDataSource.flush(FlushMode.ALL);
    }
    
}
