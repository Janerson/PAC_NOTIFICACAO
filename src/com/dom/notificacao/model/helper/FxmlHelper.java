package com.dom.notificacao.model.helper;

import java.net.URL;

/**
 * Created by DOM on 22/05/2015.
 * Email: douglas.janerson@gmail.com
 * Project: PAC_NOTIFICACAO.
 */
public class FxmlHelper {

    public static URL loadFxml(String fxml){
        return FxmlHelper.class.getResource("../../view/fxml/"+fxml+".fxml");

    }


}
