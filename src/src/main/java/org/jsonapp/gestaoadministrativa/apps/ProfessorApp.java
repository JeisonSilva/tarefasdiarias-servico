package org.jsonapp.gestaoadministrativa.apps;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.BadRequestException;

import org.jsonapp.gestaoadministrativa.IProfessorApp;
import org.jsonapp.gestaoadministrativa.ProfessorDto;
import org.jsonapp.gestaologin.ILoginRepository;
import org.jsonapp.gestaologin.entidades.Login;
import org.jsonapp.gestaotarefas.IProfessorRepository;
import org.jsonapp.gestaotarefas.entidades.Professor;
import org.jsonapp.gestaotarefas.objetosvalor.Nome;
import org.jsonapp.gestaotarefas.objetosvalor.ProfessorId;

@ApplicationScoped
public class ProfessorApp implements IProfessorApp {

    final ILoginRepository loginRepository;
    final IProfessorRepository professorRepository;

    public ProfessorApp(
        ILoginRepository loginRepository
        , IProfessorRepository professorRepository) {
        super();

        this.loginRepository = loginRepository;
        this.professorRepository = professorRepository;
    }

    @Override
    public void criar(ProfessorDto professorDto) throws Exception {
        Login login = this.loginRepository.obter(professorDto.getEmail());
        Professor professor = this.professorRepository.obter(professorDto.getEmail());

        if(login == null)
            throw new BadRequestException();
        
        if(professor != null)
            throw new BadRequestException();
        
        Professor novoPerfilProfessor = new Professor(new ProfessorId(professorDto.getEmail()), new Nome(professorDto.getNome()));
        this.professorRepository.salvar(novoPerfilProfessor);

    }
    
}
