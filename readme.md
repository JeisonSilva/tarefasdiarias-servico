# Projeto TarefasDiarias
Este projeto foi desenvolvido para conclusão do terceiro módulo do curso Nextu referente ao desenvolvimento de aplicativos android. Este projeto consiste nas seguintes etapas:

* Criar um aplicativo de gestão para o professor onde será disponibilizado um ambiente de criação de tarefas. Neste ambiente o professor terá acesso a sua lista de tarefas, criação de novas tarefas, acompanhando de tarefas finalizadas, lista de alunos e lista das disciplinas.

* Criar um aplicativo de atendimento para o aluno. Neste ambiente o aluno poderá acompanhar suas tarefas, notificar tarefas finalizadas, visualizar os colegas das aulas e disciplinas de sua sala de aula.

TarefasDiarias cria uma comunicação entre professor e alunos em relação as atividades fora do ambiente escolar. Desta forma os professores poderão gerenciar as tarefas que treinam o conhecimento do aluno e um ambiente de acompanhamento das atividades atribuídas para o aluno. 

# Arquitetura

![](doc/arquitetura.png)

## Link dos projetos mobile.
[Aplicativo do professor](https://github.com/JeisonSilva/tarefasdiarias-app-professor)

[![Build status](https://build.appcenter.ms/v0.1/apps/f8df5c6a-c6cb-4caf-ac7c-d7bcb2d8d847/branches/main/badge)](https://appcenter.ms)

[Aplicativo do aluno](https://github.com/JeisonSilva/tarefasdiarias-app-aluno)

[![Build status](https://build.appcenter.ms/v0.1/apps/06a91d01-4cb7-4dd1-b874-0eb648f6aea4/branches/main/badge)](https://appcenter.ms)

# Utilização do serviço

#### Clone do serviço.
https://github.com/JeisonSilva/tarefasdiarias-servico.git

1. Acessar a pasta SRC.

![](doc/Screenshot_1.png)

2. Abrir o projeto no editor de sua preferência e inserir o IP de sua máquina no arquivo **application.properties**

![](doc/Screenshot_2.png)

3. Copiar os certificados para a pasta c:\temp

![](doc/Screenshot_3.png)

4. Executar a aplicação

```cmd
    .\mvnw quarkus:dev

```

