package com.dom.notificacao.model.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
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

    @NotEmpty(message = "Paciente: campo Obrigatório")
    @Column(name = "pac_nome" , length = 72)
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Past(message = "Data de nascimento deve ser anterior a data atual.")
    @NotNull(message = "Data de Nascimento deve ser preenchida.")
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
