package csg.com.torneos;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


import static android.util.Log.*;

public class resultados extends AppCompatActivity implements View.OnClickListener{


    DBResultados tabla_resultados;
    SQLiteDatabase db;
    String torneo;



    String id ;
    LinearLayout linear1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);



        torneo =  getIntent().getStringExtra("torneo");
        Log.i("Info","El torneo que se consulta es "+torneo);


        ArrayList grupoA  = new ArrayList<String>();
         grupoA = getIntent().getStringArrayListExtra("grupoA");
        ArrayList grupoB  = new ArrayList<String>();
        grupoB = getIntent().getStringArrayListExtra("grupoB");
        ArrayList grupoC  = new ArrayList<String>();
        grupoC = getIntent().getStringArrayListExtra("grupoC");
        ArrayList grupoD  = new ArrayList<String>();
        grupoD = getIntent().getStringArrayListExtra("grupoD");




        tabla_resultados  = new DBResultados(getApplicationContext(), "TORNEOS", null, 8);
        db = tabla_resultados.getReadableDatabase();

        Integer [] jornadas = {R.id.jornada1, R.id.jornada2,R.id.jornada3, R.id.jornada4,R.id.jornada5, R.id.jornada6,R.id.jornada7, R.id.jornada8,R.id.jornada9, R.id.jornada10,R.id.jornada11, R.id.jornada12, R.id.jornada13, R.id.jornada14, R.id.jornada15, R.id.jornada16, R.id.jornada17, R.id.jornada18, R.id.jornada19};



        if (finalizoFase("Grupos")) {
            obtenerTablaCuartos();
            //http://www.javatpoint.com/Comparator-interface-in-collection-framework
            if (finalizoFase("CUARTOS")){
                obtenerTablaSemis();
                if (finalizoFase("SEMIFINAL")){
                    obtenerTablaFinal();
                    if (finalizoFase("FINAL")){
                        obtenerTablaCampeon();
                    }
                }

            }

        }




        for(int k = 0, p = 1; k<jornadas.length; k++,p++) {

            LinearLayout jornada = (LinearLayout) findViewById(jornadas[k]);


            String[] campo = resultadoPartido(p,torneo);// consulto el resultado en base de datos //p inicia en uno por que es el numero del partido o jornada de la estructura de la base de datos


            //por cada jornada apliquele un click
            jornada.setOnClickListener(this);
            jornada.setTag(campo[0]);// guarda en el layout un valor significativo que es el id de la base de datos para obtenerlo en el onclick

            //armo un array para recibir los texview
            int textViewCount = 4;
            TextView[] textViewArray = new TextView[textViewCount];
            //recorro los textview  que se encuentran en la jornada uno a la 20
            int count = jornada.getChildCount();
            TextView  v = null;
            for (int i = 0; i < count; i++) {
                v = (TextView) jornada.getChildAt(i);
                //Log.e("Error", "los ids de la jornada son " + v.getId() + " y el text es " + v.getText());
                textViewArray[i] = v;
            }
            textViewArray[0].setText(campo[1]);// nombre equipo 1
            textViewArray[1].setText(campo[2]);// resultado equipo 1
            textViewArray[2].setText(campo[3]);// resultado equipo 2
            textViewArray[3].setText(campo[4]);// nombre equipo 2

        }


    }

    protected String[] resultadoPartido(Integer id_partido, String torneo){

        String[] args = new String[]{torneo, String.valueOf(id_partido)}, resultado = new String[5];
        Cursor c = db.rawQuery("SELECT id, equipo1,equipo2,fecha,marcador_equipo1,marcador_equipo2 FROM Partidos WHERE torneo = ? AND id_partido = ?;",args);
        if (c.moveToFirst()){
            do {
                resultado[0] = (c.getString(0));
                resultado[1]=(c.getString(1));
                resultado[2]=(c.getString(4));
                resultado[3]=(c.getString(5));
                resultado[4]=( c.getString(2));

            }while(c.moveToNext());
        }
        return resultado;
    }

    @Override
    public void onClick(View v) {

        Log.e("Error","El id que envía la tabla es "+v.getTag());
        Intent jornada = new Intent(getApplicationContext(), ingresarResultados.class);
        jornada.putExtra("id", (String) v.getTag());
        Log.i("Info","el tag del partido es "+id + " -- "+(String) v.getTag());
        jornada.putExtra("torneo",torneo);

        //startActivity(jornada);
        startActivityForResult(jornada,1);


        switch(v.getId()){

            case R.id.mas1:
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Log.e("","OnActivityResult");
            Intent refresh = new Intent(this, resultados.class);
            refresh.putExtra("torneo",torneo);
            startActivity(refresh);
            this.finish();
        }else{
            Intent refresh = new Intent(this, resultados.class);
            refresh.putExtra("torneo",torneo);
            startActivity(refresh);
            this.finish();

        }
    }



    protected void actualizarTabla() {



        String[] args = new String[]{torneo, String.valueOf("3")}, resultado = new String[5];
        Cursor c = db.rawQuery("SELECT id, equipo1,equipo2,fecha,marcador_equipo1,marcador_equipo2 FROM Partidos WHERE torneo = ? AND id_partido = ?;",args);
        if (c.moveToFirst()){
            do {
                resultado[0] = (c.getString(0));
                resultado[1]=(c.getString(1));
                resultado[2]=(c.getString(4));
                resultado[3]=(c.getString(5));
                resultado[4]=( c.getString(2));

            }while(c.moveToNext());
        }


    }

    protected boolean finalizoFase(String fase) {


        int numPartidos = 0;

        switch (fase) {
            case "Grupos":
                numPartidos = 12;
                break;
            case "CUARTOS":
                numPartidos = 4;
                break;
            case "SEMIFINAL":
                numPartidos = 2;
                break;
            case "FINAL":
                numPartidos = 1;
                break;
        }



        String[] args = new String[]{torneo, fase, "JUGADO"};
        Cursor c = db.rawQuery("SELECT estado FROM Partidos WHERE torneo = ? AND fecha = ? AND estado = ?;",args);// fecha = Grupos, CUARTOS,SEMIFINAL, FINAL


        Log.e("comparar fase", Integer.toString(c.getCount()));
        Log.e("comparar fase", Integer.toString(numPartidos));


        //numPartidos; viene de la estructura del torneo dada en  clase elegir_quipos
        if (c.getCount() == numPartidos){
            return true;
        }else {
            return false;
        }


    }



    protected boolean obtenerTablaCuartos() {

        String[] partido;
        String[] grupoA = {torneo,"1","2","3"};
        String[] grupoB = {torneo,"4","5","6"};
        String[] grupoC = {torneo,"7","8","9"};
        String[] grupoD = {torneo,"10","11","12"};

        List grupos = new ArrayList();
        grupos.add(grupoA);
        grupos.add(grupoB);
        grupos.add(grupoC);
        grupos.add(grupoD);


        ArrayList equiposOrdenados = new ArrayList();
        ListIterator<TablaPosiciones> iTp = null;
        ContentValues cv = new ContentValues();


        TablaPosiciones equipo ;

        Iterator i = grupos.iterator();

            while(i.hasNext()) {
                partido = (String[]) i.next();

                equiposOrdenados.add(ordenarGrupo(partido));

                iTp = ordenarGrupo(partido).listIterator();

               // Iterator itr2 = equiposOrdenados.iterator();
                if (iTp.hasNext()) {
                    equipo = (TablaPosiciones) iTp.next();
                    db = tabla_resultados.getWritableDatabase();

                   if (partido.equals(grupoA)){

                    cv.put("equipo1", (String) equipo.equipo);
                    db.update("Partidos", cv, "torneo = ? AND id_partido=?", new String[]{torneo, "13"});//primero del grupo A
                       cv.clear();
                       if (iTp.hasNext()) {
                           equipo = (TablaPosiciones) iTp.next();
                           cv.put("equipo2", (String) equipo.equipo);
                           db.update("Partidos", cv, "torneo = ? AND id_partido=?", new String[]{torneo, "16"});//Segundo del grupo A
                           cv.clear();
                       }
                    }else if (partido.equals(grupoB)){
                    cv.put("equipo1", (String) equipo.equipo);
                    db.update("Partidos", cv, "torneo = ? AND id_partido=?", new String[]{torneo, "14"});//primero del grupo A
                       cv.clear();
                       if (iTp.hasNext()) {
                           equipo = (TablaPosiciones) iTp.next();
                           cv.put("equipo2", (String) equipo.equipo);
                           db.update("Partidos", cv, "torneo = ? AND id_partido=?", new String[]{torneo, "15"});//Segundo del grupo A
                           cv.clear();
                       }
                    }else if (partido.equals(grupoC)){
                    cv.put("equipo1", (String) equipo.equipo);
                    db.update("Partidos", cv, "torneo = ? AND id_partido=?", new String[]{torneo, "15"});//primero del grupo A
                       cv.clear();
                       if (iTp.hasNext()) {
                           equipo = (TablaPosiciones) iTp.next();
                           cv.put("equipo2", (String) equipo.equipo);
                           db.update("Partidos", cv, "torneo = ? AND id_partido=?", new String[]{torneo, "14"});//Segundo del grupo A
                           cv.clear();
                       }
                    }else if (partido.equals(grupoD)){
                    cv.put("equipo1", (String) equipo.equipo);
                    db.update("Partidos", cv, "torneo = ? AND id_partido=?", new String[]{torneo, "16"});//primero del grupo A
                       cv.clear();
                       if (iTp.hasNext()) {
                           equipo = (TablaPosiciones) iTp.next();
                           cv.put("equipo2", (String) equipo.equipo);
                           db.update("Partidos", cv, "torneo = ? AND id_partido=?", new String[]{torneo, "13"});//Segundo del grupo A
                           cv.clear();
                       }
                    }
                }
            }
        return true;
    }


    protected void obtenerTablaSemis() {

        ContentValues cv = new ContentValues();

        db = tabla_resultados.getWritableDatabase();



        cv.put("equipo1", obtenerGanadorEnfrentamientoDirecto("13") );
        db.update("Partidos", cv, "torneo = ? AND id_partido=?", new String[]{torneo, "17"});//primer llave
        cv.clear();
        cv.put("equipo2", obtenerGanadorEnfrentamientoDirecto("14") );
        db.update("Partidos", cv, "torneo = ? AND id_partido=?", new String[]{torneo, "17"});
        cv.clear();
        cv.put("equipo1", obtenerGanadorEnfrentamientoDirecto("15") );
        db.update("Partidos", cv, "torneo = ? AND id_partido=?", new String[]{torneo, "18"});
        cv.clear();
        cv.put("equipo2", obtenerGanadorEnfrentamientoDirecto("16") );
        db.update("Partidos", cv, "torneo = ? AND id_partido=?", new String[]{torneo, "18"});
        cv.clear();

    }
    protected void obtenerTablaFinal() {


        ContentValues cv = new ContentValues();

        db = tabla_resultados.getWritableDatabase();

        cv.put("equipo1", obtenerGanadorEnfrentamientoDirecto("17") );
        db.update("Partidos", cv, "torneo = ? AND id_partido=?", new String[]{torneo, "19"});
        cv.clear();

        cv.put("equipo2", obtenerGanadorEnfrentamientoDirecto("18") );
        db.update("Partidos", cv, "torneo = ? AND id_partido=?", new String[]{torneo, "19"});
        cv.clear();


    }
    protected String obtenerTablaCampeon() {

        return obtenerGanadorEnfrentamientoDirecto("19");
    }

    protected String obtenerGanadorEnfrentamientoDirecto(String id_partido) {


        TablaPosiciones equipo;
        ArrayList tp = new ArrayList();

        Cursor c = db.rawQuery("SELECT equipo1,puntos_equipo1,marcador_equipo1,punto_invisible_equipo1, (marcador_equipo1-marcador_equipo2) as diferencia1, equipo2, puntos_equipo2,marcador_equipo2,punto_invisible_equipo2, (marcador_equipo2-marcador_equipo1) as diferencia2  FROM Partidos WHERE torneo = ? AND id_partido = ?;",new String[]{torneo, id_partido });
        if(c.moveToFirst()){

            //tabla de posiciones entre dos equipos tipo copa eliminacion directa
            //TablaPosiciones(String equipo,int totalPuntos,int diferenciaGol,int golesMarcados, int puntoInvisible)
            tp.add(new TablaPosiciones(c.getString(0),
                    c.getInt(1),
                    c.getInt(4),
                    c.getInt(2),
                    c.getInt(3))
            );
            tp.add(new TablaPosiciones(c.getString(5),
                    c.getInt(6),
                    c.getInt(9),
                    c.getInt(7),
                    c.getInt(8))
            );


        }

        Collections.sort(tp, new comparadorPuntos());


        Iterator itr2 = tp.iterator();
        while (itr2.hasNext()) {
            equipo = (TablaPosiciones) itr2.next();
            Log.i("Ordenamiento enfrentamiento directo", equipo.equipo + ":eq:" + equipo.totalPuntos + ":tp:" + equipo.diferenciaGol + ":gd:" + equipo.golesMarcados + ":gm:" + equipo.puntoInvisible + "pi");
        }

        tp.remove(tp.size()-1);

        equipo = (TablaPosiciones) tp.get(0);

        return (String) equipo.equipo;

    }














    protected ArrayList ordenarGrupo(String[] partido) {

            // String[] both = (String[])ArrayUtils.addAll(first, second);
            // String[] both = ObjectArrays.concat(first, second, String.class);

            //SELECCIONAR EQUIPOS QUE JUEGAN UN GRUPO
            //String[] argsGrupo = new String[]{torneo,"1","2","3",torneo,"1","2","3"};
            String[] argsGrupo = new String[]{partido[0], partido[1], partido[2], partido[3], partido[0], partido[1], partido[2], partido[3]};
            Cursor cEquipos = db.rawQuery("SELECT distinct equipo1 From Partidos WHERE torneo = ? AND (id_partido = ? OR id_partido = ? OR id_partido = ?) UNION SELECT distinct equipo2 From Partidos WHERE torneo = ? AND (id_partido = ? OR id_partido = ? OR id_partido = ?);", argsGrupo);

            ArrayList tp = new ArrayList();

            if (cEquipos.moveToFirst()) {
                do {
                    //SELECT puntos_equipo1,marcador_equipo1,punto_invisible_equipo1, sum(marcador_equipo1-marcador_equipo2) as diferencia   FROM Partidos WHERE (equipo1 = "Napoli")  AND (id_partido = 1 OR id_partido = 2 OR id_partido = 3);
                    //SELECT puntos_equipo2,marcador_equipo2,punto_invisible_equipo2, sum(marcador_equipo2-marcador_equipo1) as diferencia     FROM Partidos WHERE (equipo2 = "Napoli")  AND (id_partido = 1 OR id_partido = 2 OR id_partido = 3);
                    String[] argsResultados = new String[]{cEquipos.getString(0),torneo, partido[1], partido[2], partido[3]};//nombre de equipo , id de partido identifican grupo
                    Cursor cResultadosEquipo1 = db.rawQuery("SELECT sum(puntos_equipo1),sum(marcador_equipo1),sum(punto_invisible_equipo1), (sum(marcador_equipo1)-sum(marcador_equipo2)) as diferencia   FROM Partidos WHERE (equipo1 = ?) AND torneo = ?  AND (id_partido = ? OR id_partido = ? OR id_partido = ?);", argsResultados);
                    Cursor cResultadosEquipo2 = db.rawQuery("SELECT sum(puntos_equipo2),sum(marcador_equipo2),sum(punto_invisible_equipo2), (sum(marcador_equipo2)-sum(marcador_equipo1)) as diferencia     FROM Partidos WHERE (equipo2 = ?) AND torneo = ?  AND (id_partido = ? OR id_partido = ? OR id_partido = ?);", argsResultados);

                    argsResultados = new String[]{cEquipos.getString(0), partido[1], partido[2], partido[3]};//nombre de equipo , id de partido identifican grupo
                   // Cursor prueba = db.rawQuery("SELECT sum(puntos_equipo1) FROM Partidos WHERE (equipo1 = ?)  AND (id_partido = ? OR id_partido = ? OR id_partido = ?);", argsResultados);

                    if (cResultadosEquipo1.moveToFirst() && cResultadosEquipo2.moveToFirst()) {
                        //TablaPosiciones(String equipo,int totalPuntos,int diferenciaGol,int golesMarcados, int puntoInvisible){
                        tp.add(new TablaPosiciones(cEquipos.getString(0),
                                cResultadosEquipo1.getInt(0) + cResultadosEquipo2.getInt(0),
                                cResultadosEquipo1.getInt(3) + cResultadosEquipo2.getInt(3),
                                cResultadosEquipo1.getInt(1) + cResultadosEquipo2.getInt(1),
                                cResultadosEquipo1.getInt(2) + cResultadosEquipo2.getInt(2))
                        );
                    }

                } while (cEquipos.moveToNext());
            }


/*
        String[] argsResultados = new String[]{cEquipos.getString(0), partido[1], partido[2], partido[3]};//nombre de equipo , id de partido identifican grupo
        Cursor c1 = db.rawQuery("SELECT sum(puntos_equipo1),sum(marcador_equipo1),sum(punto_invisible_equipo1), (sum(marcador_equipo1)-sum(marcador_equipo2)) as diferencia   FROM Partidos WHERE (equipo1 = ?)  AND (id_partido = ? OR id_partido = ? OR id_partido = ?);", argsResultados);
        Cursor c2 = db.rawQuery("SELECT sum(puntos_equipo2),sum(marcador_equipo2),sum(punto_invisible_equipo2), (sum(marcador_equipo2)-sum(marcador_equipo1)) as diferencia     FROM Partidos WHERE (equipo2 = ?)  AND (id_partido = ? OR id_partido = ? OR id_partido = ?);", argsResultados);

        */


            //Collections.reverseOrder();
            Collections.sort(tp, new comparadorPuntos());
            tp.remove(tp.size()-1);


            Iterator itr2 = tp.iterator();
            while (itr2.hasNext()) {
                TablaPosiciones equipo = (TablaPosiciones) itr2.next();
                Log.i("Ordenamiento de grupos", equipo.equipo + ":eq:" + equipo.totalPuntos + ":tp:" + equipo.diferenciaGol + ":gd:" + equipo.golesMarcados + ":gm:" + equipo.puntoInvisible + "pi");
            }
        return tp;



    }

    protected boolean obtenerEquiposTorneo() {

        String[] args = new String[] {torneo, "Grupos"};

        //Selecciona los equipos que hay en la columna equipos1 y equipos 2 y no trae los duplicados
        Cursor c = db.rawQuery ("SELECT distinct equipo1 FROM Partidos WHERE torneo = ?, fecha=? UNION SELECT distinct equipo2 FROM Partidos WHERE torneo = ?, fecha=?;",args);


        String[] resultado = new String[5];
        if (c.moveToFirst()){
            do {
                resultado[0] = (c.getString(0));
                resultado[1]=(c.getString(1));
                resultado[2]=(c.getString(4));
                resultado[3]=(c.getString(5));
                resultado[4]=( c.getString(2));

            }while(c.moveToNext());
        }

        return true;
    }

}
