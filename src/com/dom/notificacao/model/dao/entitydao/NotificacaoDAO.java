package com.dom.notificacao.model.dao.entitydao;

import com.dom.notificacao.model.dao.DAOImpl;
import com.dom.notificacao.model.entity.Notificacao;

/**
 * Created by DOM on 23/05/2015.
 * Email: douglas.janerson@gmail.com
 * Project: PAC_NOTIFICACAO.
 */
public class NotificacaoDAO extends DAOImpl<Notificacao , Integer> {
    public NotificacaoDAO() {
        super(Notificacao.class);
    }
}
