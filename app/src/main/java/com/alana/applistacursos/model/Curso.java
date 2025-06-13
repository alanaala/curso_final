package com.alana.applistacursos.model;

public class Curso {
    private String nomeCurso;

    public Curso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    @Override
    public String toString() {
        return "Curso: " + nomeCurso;
    }
}