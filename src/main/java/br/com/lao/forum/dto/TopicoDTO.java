package br.com.lao.forum.dto;

import java.time.LocalDate;

public record TopicoDTO(String titulo, String msg, String autor, String curso) {

    @Override
    public String titulo() {
        return titulo;
    }
    @Override
    public String msg() {
        return msg;
    }
    @Override
    public String autor() {
        return autor;
    }
    @Override
    public String curso() {
        return curso;
    }
}
