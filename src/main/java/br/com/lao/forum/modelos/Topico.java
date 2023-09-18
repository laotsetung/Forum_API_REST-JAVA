package br.com.lao.forum.modelos;

import br.com.lao.forum.dataFormat.DataFormat;
import br.com.lao.forum.estados.Estados;

import java.time.LocalDate;

public class Topico {

    private int id;

    private String titulo;
    private String msg;
    private LocalDate data;
    private int estado;
    private String autor;
    private String curso;

    //Construtor para criação de um novo topico na database (SEM ID)
    public Topico(String titulo, String msg, String autor, String curso) {
        this.titulo = titulo;
        this.msg = msg;
        this.data = LocalDate.now(); //data de hoje
        this.estado = 0; //estado 0 = 'Não Respondido'
        this.autor = autor;
        this.curso = curso;
    }

    //Construtor para receber um topico do database (COM ID)
    public Topico(int id, String titulo, String msg, LocalDate data, int estado, String autor, String curso) {
        this.id = id;
        this.titulo = titulo;
        this.msg = msg;
        this.data = data;
        this.estado = estado;
        this.autor = autor;
        this.curso = curso;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(String data) { this.data = DataFormat.dateStringToLocale(data); }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString(){
        String txt = "{\n\"id\" : \"" + this.id+"\",\n";
        txt += "\"titulo\" : \"" + this.titulo+"\",\n";
        txt += "\"msg\" : \"" + this.msg+"\",\n";
        txt += "\"data\" : \"" + this.data+"\",\n";
        txt += "\"estado\" : \"" + Estados.getEstado(this.estado) +"\",\n";
        txt += "\"autor\" : \"" + this.autor+"\",\n";
        txt += "\"curso\" : \"" + this.curso+"\"\n}";
        return txt;
    }
}
