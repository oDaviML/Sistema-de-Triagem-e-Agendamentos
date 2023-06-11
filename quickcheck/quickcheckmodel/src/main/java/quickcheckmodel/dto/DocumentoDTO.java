package quickcheckmodel.dto;

public class DocumentoDTO {
    private String tipo, nome, link, cpf;


    public DocumentoDTO(String tipo, String nome, String link, String cpf) {
        this.tipo = tipo;
        this.nome = nome;
        this.link = link;
        this.cpf = cpf;
    }

    public DocumentoDTO() {
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    
}
