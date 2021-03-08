package org.jsonapp.gestaotarefas;

import org.jsonapp.gestaotarefas.entidades.Tarefa;

public interface ITarefaRepository {

	void salvar(Tarefa tarefa) throws Exception;

	Tarefa obter(int id) throws Exception;

	void atualizar(Tarefa tarefaRegistrada) throws Exception;
    
}
