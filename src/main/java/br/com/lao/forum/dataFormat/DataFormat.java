package br.com.lao.forum.dataFormat;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class DataFormat {

    public static String format(String d){
        String[] dt = d.split("-");
        int ano = Integer.parseInt(dt[0]);
        int mes = Integer.parseInt(dt[1])-1;
        int dia = Integer.parseInt(dt[2]);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime = LocalDate.of(ano, mes, dia);
        String formattedDateTime = dateTime.format(formatter); // "2017-04-08 12:30"

        return formattedDateTime;
    }

    public static LocalDate dateStringToLocale(String d){
        String[] dt = d.split("-");
        int ano = Integer.parseInt(dt[0]);
        int mes = Integer.parseInt(dt[1])-1;
        int dia = Integer.parseInt(dt[2]);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime = LocalDate.of(ano, mes, dia);

        return dateTime;
    }

}
