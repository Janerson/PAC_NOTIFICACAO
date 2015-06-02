package com.dom.notificacao.model.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

/**
 * Created by DOM on 23/05/2015.
 * Email: douglas.janerson@gmail.com
 * Project: PAC_NOTIFICACAO.
 */
public class DateUtil {

    /**
     * Converte LocalDate para Date
     *
     * @param localDate
     * @return date
     */
   /* public static Date toDate(LocalDate localDate) {
        Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);

        return date;
    }*/

    /**
     * Converte Date para LocalDate
     *
     * @param d
     * @return LocalDate
     */
/*    public static LocalDate toLocalDate(Date d) {
        Instant instant = Instant.ofEpochMilli(d.getTime());
        LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
        return localDate;
    }*/
    public static Date stringToDate(String data){
        DateFormat dfmt = new SimpleDateFormat("dd/mm/yyyy");
        Date date = null;
        try {
          date = dfmt.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
