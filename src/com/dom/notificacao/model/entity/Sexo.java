package com.dom.notificacao.model.entity;

/**
 * Created by DOM on 22/05/2015.
 * Email: douglas.janerson@gmail.com
 * Project: PAC_NOTIFICACAO.
 */
public enum Sexo {

    M("MASCULINO") , F("FEMININO");

    String genero;

    Sexo(String sexo) {
        genero = sexo;
    }

    @Override
    public String toString() {
        return genero;
    }
}
