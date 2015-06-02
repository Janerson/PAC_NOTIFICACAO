package com.dom.notificacao.model.entity;

import javax.persistence.*;

/**
 * Created by DOM on 21/05/2015.
 * Email: douglas.janerson@gmail.com
 * Project: PAC_NOTIFICACAO.
 */
@Entity(name = "tb_responsavel")
public class Responsavel {

    private Integer id;
    private String nome;

    public Responsavel() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resp_id" , nullable = false , unique = true)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "resp_nome" , length = 72 , nullable = false)
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
