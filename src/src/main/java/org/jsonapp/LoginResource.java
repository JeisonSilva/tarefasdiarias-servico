package org.jsonapp;

import java.sql.SQLException;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jsonapp.gestaologin.ILoginApp;
import org.jsonapp.gestaologin.IPerfilApp;
import org.jsonapp.gestaologin.LoginDto;

@Path("api/v1/administrador/logins")
@Tag(name = "Administrador (Gestão do perfil)", description = "Gerenciamento do perfil")
public class LoginResource {
    
    final ILoginApp loginApp;
    final IPerfilApp perfilApp;

    public LoginResource(ILoginApp loginApp, IPerfilApp perfilApp) {
        super();

        this.loginApp = loginApp;
        this.perfilApp = perfilApp;
    }

    @POST
    @APIResponse(responseCode = "201", description = "Cria um login")
    @APIResponse(responseCode = "400", description = "Login já existe")
    @APIResponse(responseCode = "500", description = "Falha no servidor")
    @Operation(summary = "Cria novo login")
    public Response post(LoginDto loginDto) {
        try{
            this.loginApp.criar(loginDto);
            return Response.status(Status.CREATED).build();
        }catch(BadRequestException ex) {
            return Response.status(Status.BAD_REQUEST).build();
        }catch(InternalServerErrorException | SQLException ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
        
    }

    @DELETE
    @Path("{email}")
    @APIResponse(responseCode = "204", description = "Login desabilitado")
    @APIResponse(responseCode = "404", description = "Login não existe ou já se encontra desabilitado")
    @APIResponse(responseCode = "500", description = "Falha no servidor")
    @Operation(summary = "Desabilita um login")
    public Response delete(@PathParam("email")String email) {
        try{
            this.loginApp.desabilitarConta(email);
            return Response.status(Status.NO_CONTENT).build();
        }catch(NotFoundException ex) {
            return Response.status(Status.NOT_FOUND).build();
        }catch(InternalServerErrorException | SQLException ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}
