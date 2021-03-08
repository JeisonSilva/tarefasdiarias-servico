package org.jsonapp.gestaoadministrativa.apps;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.BadRequestException;

import org.jsonapp.gestaoadministrativa.AlunoDto;
import org.jsonapp.gestaoadministrativa.IAlunoApp;
import org.jsonapp.gestaologin.ILoginRepository;
import org.jsonapp.gestaologin.entidades.Aluno;
import org.jsonapp.gestaologin.entidades.Login;
import org.jsonapp.gestaotarefas.IAlunoRepository;
import org.jsonapp.gestaotarefas.objetosvalor.AlunoId;
import org.jsonapp.gestaotarefas.objetosvalor.Nome;

@ApplicationScoped
public class AlunoApp implements IAlunoApp {

    final IAlunoRepository alunoRepository;
    final ILoginRepository loginRepository;

    public AlunoApp(ILoginRepository loginRepository, IAlunoRepository alunoRepository) {
        super();

        this.loginRepository = loginRepository;
        this.alunoRepository = alunoRepository;
    }

    @Override
    public void criar(AlunoDto alunoDto) throws Exception {
        Login login = this.loginRepository.obter(alunoDto.getEmail());
        AlunoId alunoId = this.alunoRepository.obterAlunoId(alunoDto.getEmail());

        if(login == null)
            throw new BadRequestException();
        
        if(alunoId != null)
            throw new BadRequestException();

        Aluno novoAluno = new Aluno(new AlunoId(alunoDto.getEmail()), new Nome(alunoDto.getNome()));
        this.alunoRepository.salvar(novoAluno);

    }
    
}
