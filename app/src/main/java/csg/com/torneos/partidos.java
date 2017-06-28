package csg.com.torneos;

import java.util.UUID;

/**
 * Created by jaitor on 2/12/16.
 */
class resultado {


    private String id;
    private String torneo;
    private String equipo1;
    private String equipo2;
    private String fecha;//grupos, octavos cuartos semifinal final
    private String tecnico_equipo1;
    private String tecnico_equipo2;
    private int marcador_equipo1;
    private int marcador_equipo2;
    private int penales_equipo1;
    private int penales_equipo2;
    private int punto_invisible_equipo1;
    private int punto_invisible_equipo2;


    public resultado(String id,
                  String torneo, String equipo1,
                  String equipo2, String fecha) {
        this.id = UUID.randomUUID().toString();
        this.torneo = torneo;
        this.equipo1 = equipo1;
        this.equipo2 = equipo2;
        this.fecha = fecha;//grupos, octavos cuartos semifinal final
        this.tecnico_equipo1 = tecnico_equipo1;
        this.tecnico_equipo2 = tecnico_equipo2;
        this.marcador_equipo1 =  marcador_equipo1;
        this.marcador_equipo2 = marcador_equipo2;
        this.penales_equipo1 = penales_equipo1;
        this.penales_equipo2= penales_equipo2;
        this.punto_invisible_equipo1 = punto_invisible_equipo1;
        this.punto_invisible_equipo2 = punto_invisible_equipo2;
    }

    public String getId() {
        return id;
    }

    public String getTorneo() {
        return torneo;
    }

    public int getmarcadorEquipo1() {
        return marcador_equipo1;
    }

    public int getMarcadorEquipo1() {
        return marcador_equipo2;
    }




}
