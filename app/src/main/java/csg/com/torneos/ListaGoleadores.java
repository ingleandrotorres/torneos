package csg.com.torneos;

/**
 * Created by jaitor on 23/12/16.
 */
public class ListaGoleadores {


    public String id ;
    public String  nombre;
    public String  equipo;
    public int tarjeta;



    public ListaGoleadores(){
        super();
    }
    public ListaGoleadores(String id,String  nombre, String equipo,int tarjeta){
        super();
        this.id  = id;
        this.nombre  = nombre;
        this.equipo = equipo;
        this.tarjeta  = tarjeta;
    }

}
