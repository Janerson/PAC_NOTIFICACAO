package com.dom.notificacao.model.interfaces;

import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DOM on 12/05/2015.
 * Email: douglas.janerson@gmail.com
 * Project: DerbyProject.
 *
 * @param <T> Classe
 * @param <PK> tipo da chave primária
 */
public interface IDAO<T, PK extends Serializable> {

    boolean save(T entity);

    boolean remove(T entity);

     T getById(PK pk);

     List<T> getAll();

    Session getSession();

    void closeSession();


}
