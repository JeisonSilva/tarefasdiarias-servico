package org.jsonapp;

import javax.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;

@OpenAPIDefinition(
    tags = {},
    info = @Info(
        title = "Tarefas diárias",
        version = "0.2.0",
        contact = @Contact(
            name = "API para gerenciamento de tarefas",
            url = "https://github.com/JeisonSilva/tarefasdiarias",
            email = "jeison.desenvolvimento@gmail.com"

        )
    )
)
public class RegistroTarefasApplication extends Application{
    
}
