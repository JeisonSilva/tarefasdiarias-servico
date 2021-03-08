package org.jsonapp.gestaoadministrativa.apps;

import java.sql.SQLException;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;

import org.jsonapp.gestaoadministrativa.IClasseRepository;
import org.jsonapp.gestaoadministrativa.IMatriculaAlunoApp;
import org.jsonapp.gestaoadministrativa.IMatriculaRepository;
import org.jsonapp.gestaoadministrativa.entidades.Classe;
import org.jsonapp.gestaoadministrativa.entidades.Matricula;
import org.jsonapp.gestaoadministrativa.objetosvalor.AlunoId;
import org.jsonapp.gestaoadministrativa.objetosvalor.ProfessorId;
import org.jsonapp.gestaotarefas.IAlunoRepository;

@ApplicationScoped
public class MatriculaAlunoApp implements IMatriculaAlunoApp {

    final IMatriculaRepository matriculaRepository;
    final IClasseRepository classeRepository;
    final IAlunoRepository alunoRepository;

    public MatriculaAlunoApp(IMatriculaRepository matriculaRepository, IClasseRepository classeRepository,
            IAlunoRepository alunoRepository) {
        super();

        this.matriculaRepository = matriculaRepository;
        this.classeRepository = classeRepository;
        this.alunoRepository = alunoRepository;
    }

    @Override
    public void matricular(String emailProfessor, String emailAluno) throws Exception {
        Classe classe = this.classeRepository.obterPorEmailProfessor(new ProfessorId(emailProfessor));
        org.jsonapp.gestaotarefas.objetosvalor.AlunoId aluno = this.alunoRepository.obterAlunoId(emailAluno);

        if (classe == null)
            throw new NotFoundException();

        if (aluno == null)
            throw new NotFoundException();

        Matricula matricula = classe.matricular(new AlunoId(aluno.toString()));

        this.matriculaRepository.salvar(matricula);

    }

    @Override
    public void cancelarMatricula(String emailProfessor, String emailAluno) throws SQLException {
        Matricula matricula = this.matriculaRepository.obterMatriculaPorIdAluno(new AlunoId(emailAluno));

        if(matricula == null)
            throw new NotFoundException();

        this.matriculaRepository.deletar(matricula);

    }
    
}
