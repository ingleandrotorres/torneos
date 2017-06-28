package csg.com.torneos;

import android.util.Log;

import java.util.Comparator;

/**
 * Created by jaitor on 31/03/17.
 */
public class comparadorPuntos implements Comparator {
    @Override
    public int compare(Object equipo1, Object equipo2) {


        TablaPosiciones tablaEquipo1 = (TablaPosiciones)equipo1;
        TablaPosiciones tablaEquipo2 = (TablaPosiciones)equipo2;


        if(tablaEquipo1.totalPuntos == tablaEquipo2.totalPuntos) {
            if(tablaEquipo1.diferenciaGol == tablaEquipo2.diferenciaGol) {
                if(tablaEquipo1.golesMarcados == tablaEquipo2.golesMarcados) {

                    if(tablaEquipo1.puntoInvisible == tablaEquipo2.puntoInvisible)
                        return 0;

                    else if(tablaEquipo1.puntoInvisible < tablaEquipo2.puntoInvisible)
                        return 1;
                    else
                        return -1;

                }else if(tablaEquipo1.golesMarcados < tablaEquipo2.golesMarcados) {
                    return 1;
                }else{
                    return -1;
                }
            }else if(tablaEquipo1.diferenciaGol < tablaEquipo2.diferenciaGol) {
                return 1;
            }else{
                return -1;
            }
        }
        else if(tablaEquipo1.totalPuntos < tablaEquipo2.totalPuntos) {
            return 1;
        }
        else{
            return -1;
        }
    }
}
