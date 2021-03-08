package org.jsonapp.gestaologin;

public class AlteracaoSenhaDto {
    String senhaAtual;
    String novaSenha;
    String confirmacaoSenha;

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public void setConfirmacaoSenha(String confirmacaoSenha) {
        this.confirmacaoSenha = confirmacaoSenha;
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public String getConfirmacaoSenha() {
        return confirmacaoSenha;
    }
}
