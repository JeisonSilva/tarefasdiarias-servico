-- CRIA DADOS PARA DISCIPLINA
INSERT INTO disciplinas(descricao) VALUES('PORTUGUES');
INSERT INTO disciplinas(descricao) VALUES('MATEMATICA');
INSERT INTO disciplinas(descricao) VALUES('GEOGRAFIA');

-- CRIA LOGIN DO USUÁRIO
INSERT INTO logins(email, senha) VALUES('aluno@aluno.com', '123456');
INSERT INTO logins(email, senha) VALUES('professor@professor.com', '123456');

-- CRIA UMA ALUNO PARA TESTES
INSERT INTO alunos(email, nome)VALUES('aluno@aluno.com', 'Aluno para testes');

-- CRIA UM PROFESSOR PARA TESTES
INSERT INTO professores(email, nome)VALUES('professor@professor.com', 'Professor para testes');


-- CREATE USER 'TAREFAS_DIARIAS'@'localhost' IDENTIFIED BY 'tarefasDiarias';
-- PERMISSÕES (DELETE, INSERT, SELECT, UPDATE)
-- GRANT DELETE, INSERT, SELECT, UPDATE ON TAREFAS_DIARIAS.* TO 'TAREFAS_DIARIAS'@'%';
-- flush privileges;