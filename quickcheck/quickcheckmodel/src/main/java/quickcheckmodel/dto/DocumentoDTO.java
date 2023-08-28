package quickcheckmodel.dto;

public class DocumentoDTO {
    private Integer id;
    private String tipo, nome, link, cpf, data;

    public DocumentoDTO(String tipo, String nome, String link, String cpf, String data, Integer id) {
        this.tipo = tipo;
        this.nome = nome;
        this.link = link;
        this.cpf = cpf;
        this.data = data;
        this.id = id;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    

}
