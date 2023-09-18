package br.com.lao.forum.estados;

public class Estados {

    public static String getEstado(int e){
        if(e == 0){
            return "Não Respondido";
        }else if(e == 1){
            return "Não Solucionado";
        }else if(e == 2){
            return "Solucionado";
        }else{
            return "Fechado";
        }
    }
}
