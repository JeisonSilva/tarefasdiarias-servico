package org.jsonapp.gestaoadministrativa.apps;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;

import org.jsonapp.gestaoadministrativa.ClasseDto;
import org.jsonapp.gestaoadministrativa.IClasseProfessorApp;
import org.jsonapp.gestaoadministrativa.IClasseRepository;
import org.jsonapp.gestaoadministrativa.ProfessorSubstitutoDto;
import org.jsonapp.gestaoadministrativa.entidades.Classe;
import org.jsonapp.gestaoadministrativa.exceptions.ConflictException;
import org.jsonapp.gestaoadministrativa.objetosvalor.ProfessorId;
import org.jsonapp.gestaotarefas.IProfessorRepository;
import org.jsonapp.gestaotarefas.entidades.Professor;

@ApplicationScoped
public class ClasseProfessorApp implements IClasseProfessorApp {

    final IClasseRepository classeRepository;
    final IProfessorRepository professorRepository;

    public ClasseProfessorApp(IClasseRepository classeRepository, IProfessorRepository professorRepository) {
        super();

        this.classeRepository = classeRepository;
        this.professorRepository = professorRepository;
    }

    @Override
    public void criar(ClasseDto classe) throws Exception {
        ProfessorId id = new ProfessorId(classe.getEmailProfessor());
        Classe classeCadastrada = this.classeRepository.obterPorEmailProfessor(id);
        Professor professor = this.professorRepository.obter(classe.getEmailProfessor());

        if(classeCadastrada != null)
            throw new ConflictException();
        
        if(professor == null)
            throw new NotFoundException();
        
        Classe novaClasse = new Classe(classe.getCodigo(), id);

        this.classeRepository.salvar(novaClasse);

    }
    
    @Override
    public void substituirProfessor(String email, ProfessorSubstitutoDto professorSubstitutoDto) throws Exception {
        Classe classe = this.classeRepository.obterPorEmailProfessor(new ProfessorId(email));
        Professor professor = this.professorRepository.obter(professorSubstitutoDto.getEmail());

        if(classe == null)
            throw new NotFoundException();
        
        if(professor == null)
            throw new NotFoundException();

        classe.substituirProfessor(professor);

        this.classeRepository.atualizar(classe);

    }
    
}
