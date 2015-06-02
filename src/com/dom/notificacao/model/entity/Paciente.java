package com.dom.notificacao.model.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by DOM on 21/05/2015.
 * Email: douglas.janerson@gmail.com
 * Project: PAC_NOTIFICACAO.
 */
@Entity(name = "tb_paciente")
public class Paciente {

    private Integer id;
    private String nome;
    private Date dataNascimento;
    private String sexo;

    public Paciente() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pac_cod" , nullable = false , unique = true)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "pac_nome" , length = 72)
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "pac_data_nascimento" , nullable = true)
    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    @Column(columnDefinition = "VARCHAR(10)", name = "pac_sexo" , nullable = false)
    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
}
