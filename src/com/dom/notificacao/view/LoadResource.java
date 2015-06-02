/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dom.notificacao.view;

import javafx.scene.image.Image;

/**
 * Classe para auxiliar no load de imagens.
 *
 * @author Janerson Douglas <douglas.janerson@gmail.com>
 */
public class LoadResource {

    private static final String PATH_RESOURCE = "com/dom/notificacao/view/images/";

    public static final Image WARNING             = new Image(PATH_RESOURCE + "dialog-warning.png");
    public static final Image INFORMATION         = new Image(PATH_RESOURCE + "information.png");
    public static final Image ERRO                = new Image(PATH_RESOURCE + "warning.png");
    public static final Image OK_SAVE             = new Image(PATH_RESOURCE + "ok-save.png");
   
    
}
