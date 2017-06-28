package csg.com.torneos;

/**
 * Created by jaitor on 31/03/17.
 */
class TablaPosiciones {

    String equipo = "";
    int totalPuntos = 0;
    int diferenciaGol = 0;
    int golesMarcados = 0;
    int puntoInvisible = 0;


    TablaPosiciones(String equipo,int totalPuntos,int diferenciaGol,int golesMarcados, int puntoInvisible){

            this.equipo = equipo;
            this.totalPuntos = totalPuntos;
            this.diferenciaGol = diferenciaGol;
            this.golesMarcados = golesMarcados;
            this.puntoInvisible = puntoInvisible;
    }

}

