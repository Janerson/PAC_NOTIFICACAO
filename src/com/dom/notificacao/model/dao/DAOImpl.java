package com.dom.notificacao.model.dao;

import com.dom.notificacao.model.interfaces.IDAO;
import com.dom.notificacao.model.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DOM on 12/05/2015.
 * Email: douglas.janerson@gmail.com
 * Project: DerbyProject.
 *
 * @param <T> Classe
 * @param <PK> tipo da chave prim�ria
 */
 public abstract class DAOImpl<T, PK extends Serializable> implements IDAO<T , PK> {

    private final Class classe;

    protected DAOImpl(Class c) {
        classe = c;
    }

    /**
     * Persiste um Bean no Banco de Dados
     * @param entity entidade a ser persistida
     * @return TRUE | FALSE
     */
    @Override
    public boolean save(T entity) {
        Transaction tx = getSession().beginTransaction();
        try{
            getSession().clear();
            getSession().saveOrUpdate(entity);
            tx.commit();
        }catch (Exception e){
             e.printStackTrace();
            tx.rollback();
            return false;
        }finally {
            //closeSession();
        }

        return true;
    }

    /**
     * Remover
     * @param entity entidade a ser removida
     * @return TRUE | FALSE
     */
    @Override
    public boolean remove(T entity) {
        Transaction tx = getSession().beginTransaction();
        try{
            getSession().clear();
            getSession().delete(entity);
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
            tx.rollback();
            return false;
        }finally {
            //closeSession();
        }

        return true;
    }

    /**
     *
     * @param pk Ch�ve para consulta
     * @return T entity
     */
    @Override
    public T getById(PK pk) {
        return (T) getSession().get(classe , pk);
    }

    /**
     *
     * @return List
     */
    @Override
    public List<T> getAll() {
        return (List<T>) getSession().createCriteria(classe).list();
    }

    /**
     *
     * @return Session
     */
    @Override
    public Session getSession() {
        return HibernateUtil.getSession();
    }

    /**
     * Fecha a sess�o
     */
    @Override
    public void closeSession(){
        HibernateUtil.getSession().close();
    }


}
