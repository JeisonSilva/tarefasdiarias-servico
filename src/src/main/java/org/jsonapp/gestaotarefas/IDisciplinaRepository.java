package org.jsonapp.gestaotarefas;

import org.jsonapp.gestaotarefas.objetosvalor.DisciplinaId;

public interface IDisciplinaRepository {

	DisciplinaId obterDisciplinaId(int disciplina) throws Exception;
    
}
