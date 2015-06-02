/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dom.notificacao.validator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 *
 * @author Janerson Douglas <douglas.janerson@gmail.com>
 * @param <T>
 */
public class BeanCheckConstraints<T> {

    private final Object o;
    private String erros="";

    public BeanCheckConstraints(T t) {
        this.o = t;
    }

    public Set<ConstraintViolation<Object>> validar() {
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        final Set<ConstraintViolation<Object>> violations = validator.validate(o);

        return violations;
    }

}
