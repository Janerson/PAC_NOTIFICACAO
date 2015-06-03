package com.dom.notificacao.model.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.Date;

/**
 * Created by DOM on 21/05/2015.
 * Email: douglas.janerson@gmail.com
 * Project: PAC_NOTIFICACAO.
 */
@Entity(name = "tb_notificacao")
public class Notificacao {

    private Integer id;
    private Date hoje ;
    private Paciente paciente = new Paciente();
    private String notification;
    private String cid;
    private String Bairro;
    private Responsavel responsavel = new Responsavel();

    public Notificacao() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @Temporal(TemporalType.DATE)
    @Column(name = "not_hoje" , nullable = false)
    public Date getHoje() {
        return hoje;
    }

    public void setHoje(Date hoje) {
        this.hoje = hoje;
    }

    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }


    @Column(name = "COD_CID")
    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    @Column(name = "bairro")
    public String getBairro() {
        return Bairro;
    }

    public void setBairro(String bairro) {
        Bairro = bairro;
    }

    @OneToOne(cascade = CascadeType.ALL)
    public Responsavel getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Responsavel responsavel) {
        this.responsavel = responsavel;
    }

    @Column(name = "notificacao")
    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    @Override
    public String toString() {
        return "Notificacao";
    }
}
