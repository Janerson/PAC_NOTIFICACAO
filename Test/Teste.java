import com.dom.notificacao.model.dao.entitydao.NotificacaoDAO;
import com.dom.notificacao.model.dao.entitydao.UserDAO;
import com.dom.notificacao.model.entity.Notificacao;
import com.dom.notificacao.model.entity.User;
import javafx.collections.ObservableList;

/**
 * Created by DOM on 31/05/2015.
 * Email: douglas.janerson@gmail.com
 * Project: PAC_NOTIFICACAO.
 */
public class Teste {


    public static void main(String[] args) {


        User user = new UserDAO().getLogin("admin" , "admin");

        NotificacaoDAO dao = new NotificacaoDAO();

        ObservableList<Notificacao> listar = user.listar(dao);

        for(Notificacao n : listar){
            System.out.println(n.getPaciente().getNome());
        }
    }



}
