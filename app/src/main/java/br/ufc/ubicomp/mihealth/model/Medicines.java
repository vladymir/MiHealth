package br.ufc.ubicomp.mihealth.model;


public class Medicines {
    private Integer _id;
    private String nome;
    private String horario;

    public Medicines (){};
    public Medicines(Integer id, String nome, String horario){
        this._id = id;
        this.nome = nome;
        this.horario = horario;
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

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }


}
