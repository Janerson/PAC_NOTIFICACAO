package com.dom.notificacao.model.entity;

import com.dom.notificacao.model.dao.entitydao.NotificacaoDAO;
import com.dom.notificacao.model.helper.ValidationHelper;
import com.dom.notificacao.validator.BeanCheckConstraints;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * Created by DOM on 22/05/2015.
 * Email: douglas.janerson@gmail.com
 * Project: PAC_NOTIFICACAO.
 */
@Entity(name = "tb_user")
public class User {

    private Integer id;
    private String username;
    private String password;

    public User() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_user", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NotEmpty(message = "Usuário não pode estar em branco")
    @Column(name = "username" , unique = true , nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotEmpty(message = "Senha não pode estar em branco")
    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean notificar(Notificacao n , NotificacaoDAO dao){
        Set<ConstraintViolation<Object>> obj = new BeanCheckConstraints<>(n).validar();
        try{
           ValidationHelper.validarBean(obj,n,dao ,"Notificação Incluída.");

        }catch (Exception e){
            ValidationHelper.showException(e ,"Ops" ,n);
            e.printStackTrace();
        }
        return true;
    }

    public ObservableList<Notificacao> listar(NotificacaoDAO dao){
        ObservableList<Notificacao> notificacaos =
                FXCollections.observableArrayList(dao.getAll());
        return notificacaos;
    }
    public boolean deletar(NotificacaoDAO dao, Notificacao n){
        try {
            if(dao.remove(n)){
                ValidationHelper.showInformation("Removido com sucesso" , n);
            }
        }catch (Exception e){
            ValidationHelper.showException(e , "Ops" , n);
            return false;
        }
        return true;
    }
}
