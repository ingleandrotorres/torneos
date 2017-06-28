package csg.com.torneos;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class elegir_equipos extends AppCompatActivity {

    Spinner equipo1T1;
    Spinner equipo2T1;
    Spinner equipo3T1;
    Spinner equipo4T1;
    Spinner equipo1T2;
    Spinner equipo2T2;
    Spinner equipo3T2;
    Spinner equipo4T2;
    Spinner equipo1T3;
    Spinner equipo2T3;
    Spinner equipo3T3;
    Spinner equipo4T3;
    String torneo = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elegir_equipos);


        torneo = getIntent().getStringExtra("torneo");

         equipo1T1 = (Spinner) findViewById(R.id.selectEquiposT1E1);
         equipo2T1 = (Spinner) findViewById(R.id.selectEquiposT1E2);
         equipo3T1 = (Spinner) findViewById(R.id.selectEquiposT1E3);
         equipo4T1 = (Spinner) findViewById(R.id.selectEquiposT1E4) ;
         equipo1T2 = (Spinner) findViewById(R.id.selectEquiposT2E1) ;
         equipo2T2 = (Spinner) findViewById(R.id.selectEquiposT2E2) ;
         equipo3T2 = (Spinner) findViewById(R.id.selectEquiposT2E3) ;
         equipo4T2 = (Spinner) findViewById(R.id.selectEquiposT2E4);
         equipo1T3 = (Spinner) findViewById(R.id.selectEquiposT3E1);
         equipo2T3 = (Spinner) findViewById(R.id.selectEquiposT3E2);
         equipo3T3 = (Spinner) findViewById(R.id.selectEquiposT3E3);
         equipo4T3 = (Spinner) findViewById(R.id.selectEquiposT3E4);

        poblarSpinner(equipo1T1,1);
        poblarSpinner(equipo2T1,2);
        poblarSpinner(equipo3T1,3);
        poblarSpinner(equipo4T1,4);

        poblarSpinner(equipo1T2,6);
        poblarSpinner(equipo2T2,7);
        poblarSpinner(equipo3T2,8);
        poblarSpinner(equipo4T2,9);

        poblarSpinner(equipo1T3,10);
        poblarSpinner(equipo2T3,11);
        poblarSpinner(equipo3T3,12);
        poblarSpinner(equipo4T3,13);



        //Log.e("INFO","pase antes de click boton ");


        Button seleccionarEquipos = (Button) findViewById(R.id.buttonSeleccionarEquipos);

        seleccionarEquipos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


        List<String> tecnico1 = new ArrayList<>();

        tecnico1.add(obtenerDatoSpinner(equipo1T1));
        tecnico1.add(obtenerDatoSpinner(equipo2T1));
        tecnico1.add(obtenerDatoSpinner(equipo3T1));
        tecnico1.add(obtenerDatoSpinner(equipo4T1));
        Collections.shuffle(tecnico1);//numeros del uno al 4 en desorden



        List<String> tecnico2 = new ArrayList<>();
        tecnico2.add(obtenerDatoSpinner(equipo1T2));
        tecnico2.add(obtenerDatoSpinner(equipo2T2));
        tecnico2.add(obtenerDatoSpinner(equipo3T2));
        tecnico2.add(obtenerDatoSpinner(equipo4T2));
        Collections.shuffle(tecnico2);//numeros del uno al 4 en desorden



        List<String> tecnico3 = new ArrayList<>();
        tecnico3.add(obtenerDatoSpinner(equipo1T3));
        tecnico3.add(obtenerDatoSpinner(equipo2T3));
        tecnico3.add(obtenerDatoSpinner(equipo3T3));
        tecnico3.add(obtenerDatoSpinner(equipo4T3));
        Collections.shuffle(tecnico3);//numeros del uno al 4 en desorden



        List<String> grupoA = new ArrayList<>();
                grupoA.add(tecnico1.get(0));
                grupoA.add(tecnico2.get(0));
                grupoA.add(tecnico3.get(0));

                List<String> grupoB = new ArrayList<>();
                grupoB.add(tecnico1.get(1));
                grupoB.add(tecnico2.get(1));
                grupoB.add(tecnico3.get(1));

                List<String> grupoC = new ArrayList<>();
                grupoC.add(tecnico1.get(2));
                grupoC.add(tecnico2.get(2));
                grupoC.add(tecnico3.get(2));

                List<String> grupoD = new ArrayList<>();
                grupoD.add(tecnico1.get(3));
                grupoD.add(tecnico2.get(3));
                grupoD.add(tecnico3.get(3));



                // Perform action on click
                Intent intent = new Intent(getBaseContext(), resultados.class);
                intent.putStringArrayListExtra("grupoA", (ArrayList<String>) grupoA);
                intent.putStringArrayListExtra("grupoB", (ArrayList<String>) grupoB);
                intent.putStringArrayListExtra("grupoC", (ArrayList<String>) grupoC);
                intent.putStringArrayListExtra("grupoD", (ArrayList<String>) grupoD);

                intent.putStringArrayListExtra("grupoA", (ArrayList<String>) grupoA);
                intent.putExtra("torneo",torneo);



               DBResultados tabla_resultados = new DBResultados(getApplicationContext(), "TORNEOS", null, 8);
                SQLiteDatabase db = tabla_resultados.getWritableDatabase();


                if (db != null){
                    db.execSQL("INSERT INTO Partidos (torneo,id_partido,equipo1,equipo2,fecha,tecnico_equipo1,tecnico_equipo2,marcador_equipo1,marcador_equipo2,penales_equipo1,penales_equipo2,punto_invisible_equipo1,punto_invisible_equipo2) " +
                            "VALUES('"+torneo+"',"+1+",'"+grupoA.get(0)+"','"+grupoA.get(1)+"', 'Grupos',1,2,0,0,0,0,0,0)," +
                            "('"+torneo+"',"+2+",'"+grupoA.get(0)+"','"+grupoA.get(2)+"', 'Grupos',1,3,0,0,0,0,0,0)," +
                            "('"+torneo+"',"+3+",'"+grupoA.get(1)+"','"+grupoA.get(2)+"', 'Grupos',2,3,0,0,0,0,0,0)," +
                            "('"+torneo+"',"+4+",'"+grupoB.get(0)+"','"+grupoB.get(1)+"', 'Grupos',1,2,0,0,0,0,0,0)," +
                            "('"+torneo+"',"+5+",'"+grupoB.get(0)+"','"+grupoB.get(2)+"', 'Grupos',1,3,0,0,0,0,0,0)," +
                            "('"+torneo+"',"+6+",'"+grupoB.get(1)+"','"+grupoB.get(2)+"', 'Grupos',2,3,0,0,0,0,0,0)," +
                            "('"+torneo+"',"+7+",'"+grupoC.get(0)+"','"+grupoC.get(1)+"', 'Grupos',1,2,0,0,0,0,0,0)," +
                            "('"+torneo+"',"+8+",'"+grupoC.get(0)+"','"+grupoC.get(2)+"', 'Grupos',1,3,0,0,0,0,0,0)," +
                            "('"+torneo+"',"+9+",'"+grupoC.get(1)+"','"+grupoC.get(2)+"', 'Grupos',2,3,0,0,0,0,0,0)," +
                            "('"+torneo+"',"+10+",'"+grupoD.get(0)+"','"+grupoD.get(1)+"', 'Grupos',1,2,0,0,0,0,0,0)," +
                            "('"+torneo+"',"+11+",'"+grupoD.get(0)+"','"+grupoD.get(2)+"', 'Grupos',1,3,0,0,0,0,0,0)," +
                            "('"+torneo+"',"+12+",'"+grupoD.get(1)+"','"+grupoD.get(2)+"', 'Grupos',2,3,0,0,0,0,0,0)," +
                            "('"+torneo+"',"+13+",'1GRUPOA','2GRUPOD', 'CUARTOS',0,0,0,0,0,0,0,0)," +
                            "('"+torneo+"',"+14+",'1GRUPOB','2GRUPOC', 'CUARTOS',0,0,0,0,0,0,0,0)," +
                            "('"+torneo+"',"+15+",'1GRUPOC','2GRUPOB', 'CUARTOS',0,0,0,0,0,0,0,0)," +
                            "('"+torneo+"',"+16+",'1GRUPOD','2GRUPOA', 'CUARTOS',0,0,0,0,0,0,0,0)," +
                            "('"+torneo+"',"+17+",'GANADOR_1A_2D','GANADOR_1B_2C', 'SEMIFINAL',0,0,0,0,0,0,0,0)," +
                            "('"+torneo+"',"+18+",'GANADOR_1C_2B','GANADOR_1D_2A', 'SEMIFINAL',0,0,0,0,0,0,0,0)," +
                            "('"+torneo+"',"+19+",'GANADOR_1A_2D_1B_2C','GANADOR_1C_2B_1D_2A', 'FINAL',0,0,0,0,0,0,0,0)");
                }



                //Toast.makeText(getApplicationContext(), "grupoA "+grupoA.get(0)+grupoA.get(1)+grupoA.get(2), Toast.LENGTH_LONG).show();


                startActivity(intent);
                //Toast.makeText(getApplicationContext(), "entro al boton", Toast.LENGTH_LONG).show();
                //TextView numero = (TextView)findViewById(R.id.tecnico1);
                //numero.setText("este es el dato uno "+equipo1T1);
            }
        });

        //this.finish(); //acabar la instancia para que no tenga posibilidad de back



    }

    private void poblarSpinner(Spinner spiner, int spinnerPosition){

        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(this, R.array.array_equipos, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //int spinnerPosition = staticAdapter.getPosition("Napoli");
        // Apply the adapter to the spinner
        spiner.setAdapter(staticAdapter);

        spiner.setSelection(spinnerPosition);





    }
    private String obtenerDatoSpinner(Spinner spiner){
        return spiner.getSelectedItem().toString();
    }


}




