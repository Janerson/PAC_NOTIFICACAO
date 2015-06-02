package com.dom.notificacao.Services;

import com.dom.notificacao.model.dao.entitydao.NotificacaoDAO;
import com.dom.notificacao.model.entity.Notificacao;
import com.dom.notificacao.model.entity.User;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

/**
 * Created by DOM on 01/06/2015.
 * Email: douglas.janerson@gmail.com
 * Project: PAC_NOTIFICACAO.
 */
public class TableTask extends Task<ObservableList<Notificacao>>{
    private User user;
    private NotificacaoDAO dao;

    public TableTask(User user, NotificacaoDAO dao) {
        this.user = user;
        this.dao = dao;
    }

    @Override
    protected ObservableList<Notificacao> call() throws Exception {
        for(int i= 0; i<500; i++){
            updateProgress(i,500);
            Thread.sleep(5);
        }
        return user.listar(dao);
    }
}
