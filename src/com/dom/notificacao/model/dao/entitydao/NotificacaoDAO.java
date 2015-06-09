package com.dom.notificacao.model.dao.entitydao;

import com.dom.notificacao.model.dao.DAOImpl;
import com.dom.notificacao.model.entity.Notificacao;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
import java.util.List;

/**
 * Created by DOM on 23/05/2015.
 * Email: douglas.janerson@gmail.com
 * Project: PAC_NOTIFICACAO.
 */
public class NotificacaoDAO extends DAOImpl<Notificacao , Integer> {
    public NotificacaoDAO() {
        super(Notificacao.class);
    }


    public List<Notificacao> filterBetweenDate(Date in , Date out){
        Criteria cri = getSession().createCriteria(Notificacao.class)
                .add(Restrictions.between("hoje" , in , out));
        return (List<Notificacao>) cri.list();
    }
}
