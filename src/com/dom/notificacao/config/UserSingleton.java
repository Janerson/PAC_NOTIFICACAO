package com.dom.notificacao.config;

import com.dom.notificacao.model.entity.User;

/**
 * Created by DOM on 23/05/2015.
 * Email: douglas.janerson@gmail.com
 * Project: PAC_NOTIFICACAO.
 */
public class UserSingleton {

    private static UserSingleton ourInstance = new UserSingleton();

    private  User user;
    public static synchronized UserSingleton getInstance() {
        if(ourInstance == null){
            ourInstance = new UserSingleton();
        }
        return ourInstance;
    }

    private UserSingleton() {
    }

    public   User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
