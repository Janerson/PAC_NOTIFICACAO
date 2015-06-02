package com.dom.notificacao.Services;

import com.dom.notificacao.model.dao.entitydao.NotificacaoDAO;
import com.dom.notificacao.model.entity.Notificacao;
import com.dom.notificacao.model.entity.User;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Created by DOM on 01/06/2015.
 * Email: douglas.janerson@gmail.com
 * Project: PAC_NOTIFICACAO.
 */
public class TableService extends Service<ObservableList<Notificacao>> {
    private User u;
    private NotificacaoDAO dao;

    public TableService(User u, NotificacaoDAO dao) {
        this.u = u;
        this.dao = dao;
    }

    @Override
    protected Task<ObservableList<Notificacao>> createTask() {
        return new TableTask(u , dao);
    }
}
