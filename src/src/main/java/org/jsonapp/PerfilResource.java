package org.jsonapp;

import java.sql.SQLException;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jsonapp.gestaoadministrativa.AlunoDto;
import org.jsonapp.gestaoadministrativa.IAlunoApp;
import org.jsonapp.gestaoadministrativa.IProfessorApp;
import org.jsonapp.gestaoadministrativa.ProfessorDto;

@Path("api/v1/administrador/perfis")
@Tag(name = "Administrador (Gestão do perfil)", description = "Gerenciamento do perfil")
public class PerfilResource {

    final IProfessorApp professorApp;
    final IAlunoApp alunoApp;

    public PerfilResource(IProfessorApp professorApp, IAlunoApp alunoApp) {
        super();

        this.professorApp = professorApp;
        this.alunoApp = alunoApp;
    }

    @POST
    @Path("professor")
    @APIResponse(responseCode = "201", description = "Cria um perfil para gerenciamento de tarefas")
    @APIResponse(responseCode = "404", description = "Login não existe")
    @APIResponse(responseCode = "400", description = "Dados inválidos")
    @APIResponse(responseCode = "500", description = "Falha no servidor")
    @Operation(summary = "Cria um perfil professor")
    public Response postProfessor(ProfessorDto professorDto) {
        try{
            this.professorApp.criar(professorDto);
            return Response.status(Status.CREATED).build();
        }catch(BadRequestException ex) {
            return Response.status(Status.BAD_REQUEST).build();
        }catch(InternalServerErrorException | SQLException ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("aluno")
    @APIResponse(responseCode = "201", description = "Cria um perfil para gerenciamento de tarefas")
    @APIResponse(responseCode = "404", description = "Login não existe")
    @APIResponse(responseCode = "400", description = "Dados inválidos")
    @APIResponse(responseCode = "500", description = "Falha no servidor")
    @Operation(summary = "Cria um perfil professor")
    public Response postAluno(AlunoDto alunoDto) {
        try{
            this.alunoApp.criar(alunoDto);
            return Response.status(Status.CREATED).build();
        }catch(BadRequestException ex) {
            return Response.status(Status.BAD_REQUEST).build();
        }catch(InternalServerErrorException ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}
