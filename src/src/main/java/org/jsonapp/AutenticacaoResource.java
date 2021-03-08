package org.jsonapp;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jsonapp.gestaologin.*;
import org.jsonapp.gestaologin.exceptions.PerfilNaoAssociadoException;

@Path("api/v1/autenticacoes")
@Tag(name = "Gerenciamento de autenticação", description = "Realiza a autenticação do sistema")
public class AutenticacaoResource {

    final ILoginApp loginApp;
    final IPerfilApp perfilApp;

    public AutenticacaoResource(ILoginApp loginApp, IPerfilApp perfilApp) {
        super();

        this.loginApp = loginApp;
        this.perfilApp = perfilApp;
    }

    @PUT
    @Path("{email}")
    @APIResponse(responseCode = "204", description = "Senha auterada")
    @APIResponse(responseCode = "404", description = "Login não existe")
    @APIResponse(responseCode = "500", description = "Falha no servidor")
    @Operation(summary = "Altera a senha do login")
    public Response put(@PathParam("email")String email, AlteracaoSenhaDto alteracaoSenhaDto) {
        try{
            this.loginApp.alterar(email, alteracaoSenhaDto);
            return Response.status(Status.NO_CONTENT).build();
        }catch(NotFoundException ex) {
            return Response.status(Status.NOT_FOUND).build();
        }catch(InternalServerErrorException | SQLException ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("{email}/autenticacao")
    @APIResponse(responseCode = "200", description = "Senha autorizada, retorna o perfil")
    @APIResponse(responseCode = "400", description = "Login inválido")
    @APIResponse(responseCode = "404", description = "Login não existe")
    @APIResponse(responseCode = "500", description = "Falha no servidor")
    @Operation(summary = "Autoriza login")
    @Tag(name = "FUNCIONALIDADES FRONTEND (ANTENÇÃO)", description = "Endpoint para utilização dos clientes")
    public Response putAutorizacao(@PathParam("email")String email, AutorizacaoDto autorizacaoDto) {
        try{
            this.loginApp.autorizar(email, autorizacaoDto);
            return Response.status(Status.OK).build();
        }catch(NotFoundException ex) {
            return Response.status(Status.NOT_FOUND).build();
        }catch(PerfilNaoAssociadoException ex){
            return Response.status(Status.BAD_REQUEST).build();
        }catch(InternalServerErrorException | SQLException ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("{email}/logout")
    @APIResponse(responseCode = "204", description = "login fechado")
    @APIResponse(responseCode = "404", description = "Login não existe")
    @APIResponse(responseCode = "500", description = "Falha no servidor")
    @Operation(summary = "Finaliza uma autorizacao")    
    @Tag(name = "FUNCIONALIDADES FRONTEND (ANTENÇÃO)", description = "Endpoint para utilização dos clientes")
    public Response putLogout(@PathParam("email")String email) {
        try{
            this.loginApp.logout(email);
            return Response.status(Status.NO_CONTENT).build();
        }catch(NotFoundException ex) {
            return Response.status(Status.NOT_FOUND).build();
        }catch(InternalServerErrorException | SQLException ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("{email}/perfil")
    @APIResponse(responseCode = "200")
    @APIResponse(responseCode = "404", description = "Perfil não autorizado")
    @APIResponse(responseCode = "500", description = "Falha no servidor")
    @Operation(summary = "Retorna perfil autorizado")
    @Tag(name = "FUNCIONALIDADES FRONTEND (ANTENÇÃO)", description = "Endpoint para utilização dos clientes")
    public Response get(@PathParam("email")String email) {
        try{
            PerfilDto perfil = this.perfilApp.obterPerfil(email);
            return Response.status(Status.OK).entity(perfil).build();
        }catch(NotFoundException ex) {
            return Response.status(Status.NOT_FOUND).build();
        }catch(InternalServerErrorException | SQLException ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }


}
