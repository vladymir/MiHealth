package br.ufc.ubicomp.mihealth.model;


public class Contatos {
    private Integer _id;
    private String nome;
    private String telefone;

    public Contatos (){};
    public Contatos(Integer id, String nome, String telefone){
        this._id = id;
        this.nome = nome;
        this.telefone = telefone;
    };



    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }


}
