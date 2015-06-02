package com.dom.notificacao.model.dao.entitydao;


import com.dom.notificacao.model.dao.DAOImpl;
import com.dom.notificacao.model.entity.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 * Created by DOM on 16/05/2015.
 * Email: douglas.janerson@gmail.com
 * Project: Project_DashBoard.
 */
public class UserDAO extends DAOImpl<User, Integer> {
    public UserDAO() {
        super(User.class);
    }

    public User getLogin(String user, String pass) {
        Criteria c = getSession().createCriteria(User.class);
        Criterion cri = Restrictions.and(Restrictions.eq("username", user),
                Restrictions.eq("password", pass));
        c.add(cri);
        return (User) c.uniqueResult();
    }

}
