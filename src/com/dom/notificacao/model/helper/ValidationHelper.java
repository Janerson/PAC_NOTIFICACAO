/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dom.notificacao.model.helper;

import com.dom.notificacao.model.dao.DAOImpl;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import jfxtras.labs.dialogs.DialogFX;

import javax.validation.ConstraintViolation;
import java.util.Set;


/**
 *
 * @author DOM
 */
public abstract class ValidationHelper {

    private static String erro = "";


    public static boolean validarBean(Set<ConstraintViolation<Object>> violations, Object entity, DAOImpl dao) throws InstantiationException {
        if (violations.isEmpty()) {
            validarSalvar(dao.save(entity), entity);
            return true;
        } else {
            erro = "";
            for(ConstraintViolation<Object> v :violations ){
                 erro += v.getMessage()+"\n";
            }
            showWarning(erro ,entity ,"Opss");
        }
        return false;
    }

    public static boolean validarBean(Set<ConstraintViolation<Object>> violations, Object entity, DAOImpl dao, String msg) throws InstantiationException {
        if (violations.isEmpty()) {
            validarSalvar((dao).save(entity), msg);
            return true;
        } else {
            erro = "";
            for(ConstraintViolation<Object> v :violations ){
                erro += v.getMessage()+"\n";
            }
            showWarning(erro ,entity ,"Opss");

        }
        return false;
    }

    private static void validarSalvar(boolean dao, Object entity) {
        if (dao) {
            showInformation("Salvo com Sucesso!!" ,entity);
        }
    }


    public static boolean validarRemover(boolean dao, Object entity) {
        if (dao) {

            showInformation("Removido com Sucesso!" , entity);
            return true;
        }
        return false;
    }




    public static void showInformation(String msg , Object header) {
        //Dialogs.showInformationDialog(null ,msg ,header.toString() , header.toString());
        //Dialog.showInfo(header.toString() , msg);
        DialogFX dialog = new DialogFX();
        dialog.setTitleText(header.toString());
        dialog.setMessage(msg);
        dialog.showDialog();

    }
    public static void showWarning(String erro , Object title , String top) {
        //Dialog.showError(title.toString() , erro);
        DialogFX dialog = new DialogFX(DialogFX.Type.ERROR);
        dialog.setTitleText(title.toString());
        dialog.setMessage(top + "\n\n"+erro);
        dialog.showDialog();

    }

    public static void showException(Exception e ,String msg , Object header){
        //Dialogs.showErrorDialog(null ,msg ,header.toString() , header.toString() , e);
        //Dialog.showThrowable(header.toString(),msg,e);
        DialogFX dialog = new DialogFX(DialogFX.Type.ERROR);
        dialog.setTitleText(header.toString());
        dialog.setMessage(msg + "\n"+e.getLocalizedMessage());
        dialog.showDialog();
    }


    public static void TextFieldToUpperCase(final  TextField txt){
        txt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                txt.setText(t1.toUpperCase());
            }
        });
    }

}
