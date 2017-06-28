package csg.com.torneos;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class index extends AppCompatActivity {




    DBResultados tabla_resultados;
    SQLiteDatabase db;
    String torneo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });






        tabla_resultados  = new DBResultados(getApplicationContext(), "TORNEOS", null, 8);
        db = tabla_resultados.getReadableDatabase();

        String[] args = new String[0];





        Cursor c2 = db.rawQuery("SELECT max(torneo) FROM Partidos",args);
        //Nos aseguramos de que existe al menos un registro
        if (c2.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                torneo = c2.getString(0);
                //String nombre = c.getString(1);
            } while(c2.moveToNext());
        }


        Cursor c = db.rawQuery("SELECT torneo FROM Partidos  GROUP BY torneo",args);
        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                String tagtorneo = c.getString(0);


                //crear boton - Editar Torneo
                LinearLayout layout = (LinearLayout) findViewById(R.id.activity_index);
                Button actualizarTorneo = new Button(this);
                actualizarTorneo.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                actualizarTorneo.setText("Actualizar torneo "+tagtorneo);
                //botonCrearTorneo.setId("id_boton_crear_torneo");
                actualizarTorneo.setTag(tagtorneo);

                actualizarTorneo.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(), resultados.class);
                        String torneoaactualizar = (String) v.getTag();
                         i.putExtra("torneo",torneoaactualizar);
                        startActivity(i);
                    }
                });

                //add button to the layout
                layout.addView(actualizarTorneo);

                //String nombre = c.getString(1);
            } while(c.moveToNext());
        }





        //crear boton - CREAR TORNEO
        LinearLayout layout = (LinearLayout) findViewById(R.id.activity_index);
        Button botonCrearTorneo = new Button(this);
        botonCrearTorneo.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        botonCrearTorneo.setText("Pulse aquí para crear un torneo");
        //botonCrearTorneo.setId("id_boton_crear_torneo");
        botonCrearTorneo.setTag(torneo);


        botonCrearTorneo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), elegir_equipos.class);
                String newTorneo = obtenerIdTorneoTipoFecha();
                i.putExtra("torneo",newTorneo);
                startActivity(i);
            }
        });

        //add button to the layout
        layout.addView(botonCrearTorneo);




/*
        Button editarTorneo = (Button) findViewById(R.id.button);


        editarTorneo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Intent i = new Intent(getApplicationContext(), resultados.class);
                i.putExtra("torneo", "2-2-2017-");
                startActivity(i);

            }
        });*/
/*
        //String[] args = new String[]{id}, resultado = new String[5];
        Cursor c = db.rawQuery("SELECT id, torneo FROM Partidos GRUP BY torneos;", null);
        if (c.moveToFirst()){
            do {
                //resultado[0] = (c.getString(0));
            }while(c.moveToNext());
        }

*/



    }


    private String  obtenerIdTorneoTipoFecha(){

        Calendar c = new GregorianCalendar();
        String dia = Integer.toString(c.get(Calendar.DATE));
        String mes = Integer.toString(c.get(Calendar.MONTH));
        String annio = Integer.toString(c.get(Calendar.YEAR));
        String hora = Integer.toString(c.get(Calendar.HOUR));
        String minuto = Integer.toString(c.get(Calendar.MINUTE));
        String segundo = Integer.toString(c.get(Calendar.SECOND));

        //return dia+"-"+mes+"-"+annio+"-"+hora+"-"+minuto;

        return annio+"-"+mes+"-"+dia+"-"+hora+"-"+minuto+"-"+segundo;

    }

}
