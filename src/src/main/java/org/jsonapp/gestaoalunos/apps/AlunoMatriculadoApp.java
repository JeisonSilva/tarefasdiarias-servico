package org.jsonapp.gestaoalunos.apps;

import java.sql.SQLException;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;

import org.jsonapp.gestaoadministrativa.objetosvalor.AlunoId;
import org.jsonapp.gestaoalunos.AlunoMatriculadoDto;
import org.jsonapp.gestaoalunos.IAlunoMatriculadoApp;
import org.jsonapp.gestaoalunos.IAlunoMatriculadoRepository;
import org.jsonapp.gestaoalunos.objetosvalor.ProfessorId;

@ApplicationScoped
public class AlunoMatriculadoApp implements IAlunoMatriculadoApp {

    final IAlunoMatriculadoRepository alunoMatriculadoRepository;

    public AlunoMatriculadoApp(IAlunoMatriculadoRepository alunoMatriculadoRepository) {
        super();

        this.alunoMatriculadoRepository = alunoMatriculadoRepository;
    }

    @Override
    public List<AlunoMatriculadoDto> obterAlunosMatriculadosPorEmailProfessor(String email) throws SQLException {
        ProfessorId id = new ProfessorId(email);
        List<AlunoMatriculadoDto> alunosMatriculados = this.alunoMatriculadoRepository.obterAlunosMatriculadosPorEmailProfessor(id);
        
        if(alunosMatriculados == null || alunosMatriculados.size() == 0)
            throw new NotFoundException();

        return alunosMatriculados;
    }

    @Override
    public List<AlunoMatriculadoDto> obterAlunosMatriculadosPorEmailAluno(String email) throws SQLException {
        AlunoId id = new AlunoId(email);
        List<AlunoMatriculadoDto> alunosMatriculados = this.alunoMatriculadoRepository.obterAlunosMatriculadosPorEmailAluno(id);
        
        if(alunosMatriculados == null || alunosMatriculados.size() == 0)
            throw new NotFoundException();

        return alunosMatriculados;
    }
    
}
