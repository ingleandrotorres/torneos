package csg.com.torneos;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class tipo_anotacion extends AppCompatActivity implements View.OnClickListener {



    String id;
    String id_partido;
    String id_jugador;
    String torneo;
    String equipo;
    String jugador;

    DBResultados tabla_resultados;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_anotacion);




        id = getIntent().getStringExtra("id");
        id_partido = getIntent().getStringExtra("id_partido");
        id_jugador = getIntent().getStringExtra("id_jugador");
        torneo = getIntent().getStringExtra("torneo");
        equipo = getIntent().getStringExtra("equipo");
        jugador = getIntent().getStringExtra("jugador");

        Log.e("Error", "hasta aqui torneo es"+torneo);
        Intent i = getIntent();
        // Le metemos el resultado que queremos mandar a la
        // actividad principal.
        i.putExtra("RESULTADO", "GOL");
        i.putExtra("torneo",torneo);
        // Establecemos el resultado, y volvemos a la actividad
        // principal. La variable que introducimos en primer lugar
        // "RESULT_OK" es de la propia actividad, no tenemos que
        // declararla nosotros.
        setResult(RESULT_OK, i);




        ImageView gol = (ImageView) findViewById(R.id.gol);
        ImageView autogol = (ImageView) findViewById(R.id.autogol);
        ImageView amarilla = (ImageView) findViewById(R.id.amarilla);
        ImageView roja = (ImageView) findViewById(R.id.roja);
        ImageView lecionAmarilla = (ImageView) findViewById(R.id.lecionAmarilla);
        ImageView lecionRoja = (ImageView) findViewById(R.id.lecionRoja);


        gol.setOnClickListener((View.OnClickListener) this);
        autogol.setOnClickListener((View.OnClickListener) this);
        amarilla.setOnClickListener((View.OnClickListener) this);
        roja.setOnClickListener((View.OnClickListener) this);
        lecionAmarilla.setOnClickListener((View.OnClickListener) this);
        lecionRoja.setOnClickListener((View.OnClickListener) this);


    }


    public void onClick(View v) {

        tabla_resultados = new DBResultados(getApplicationContext(), "TORNEOS", null, 8);
        db = tabla_resultados.getWritableDatabase();

        ContentValues args = new ContentValues();

        args.put("torneo", torneo);
        args.put("id_partido", id_partido);
        args.put("equipo", equipo);
        args.put("id_jugador", id_jugador);
        args.put("jugador", jugador);



        String equipo1_2 = null;
        int nuevoMarcador = -1;// lo dejo en -1 para saber si sucede un error darme cuenta que es un numero errado



        switch(v.getId()){

            case R.id.gol:

                args.put("gol", 1);
                db.insert("Goleadores",null,args);



                Cursor c = db.rawQuery("SELECT equipo1, equipo2, marcador_equipo1, marcador_equipo2 FROM Partidos WHERE id = ?;",new String[]{id });
                if(c.moveToFirst()){
                    String codigo= c.getString(0);

                    if(equipo.equals(c.getString(0))){
                        equipo1_2 = "1";
                        nuevoMarcador = c.getInt(2)+1;
                    }else if (equipo.equals(c.getString(1))){
                        equipo1_2 = "2";
                        nuevoMarcador = c.getInt(3)+1;
                    }
                    ContentValues actualice = new ContentValues();
                    actualice.put("marcador_equipo"+equipo1_2, nuevoMarcador );
                    db.update("Partidos", actualice, " id=?", new String[]{id});
                }





                Toast.makeText(getApplicationContext(),"Gollll", Toast.LENGTH_SHORT).show();
                break;
            case R.id.autogol:
                args.put("autogol", 1);
                db.insert("Goleadores",null,args);
                Toast.makeText(getApplicationContext(),"Mucha res !!! autogol", Toast.LENGTH_SHORT).show();



                Cursor c2 = db.rawQuery("SELECT equipo1, equipo2, marcador_equipo1, marcador_equipo2 FROM Partidos WHERE id = ?;",new String[]{id });
                if(c2.moveToFirst()){
                    String codigo= c2.getString(0);

                    if(equipo.equals(c2.getString(0))){ // si el equipo que marca autogol coinside en nombre sumele al equipo contrario
                        equipo1_2 = "2";
                        nuevoMarcador = c2.getInt(3)+1;
                    }else if (equipo.equals(c2.getString(1))){
                        equipo1_2 = "1";
                        nuevoMarcador = c2.getInt(2)+1;
                    }
                    ContentValues actualice2 = new ContentValues();
                    actualice2.put("marcador_equipo"+equipo1_2, nuevoMarcador );
                    db.update("Partidos", actualice2, " id=?", new String[]{id});
                }


                break;
            case R.id.amarilla:
                args.put("amarilla", 1);
                db.insert("Goleadores",null,args);
                Toast.makeText(getApplicationContext(),"Falta para amarilla", Toast.LENGTH_SHORT).show();
                break;
            case R.id.roja:
                args.put("roja", 1);
                db.insert("Goleadores",null,args);
                Toast.makeText(getApplicationContext(),"Roja juez!!!!!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.lecionAmarilla:
                args.put("lesion_amarilla", 1);
                db.insert("Goleadores",null,args);
                Toast.makeText(getApplicationContext(),"Jugador tendido en el piso parece que se ha lecionado LA", Toast.LENGTH_SHORT).show();
                break;
            case R.id.lecionRoja:
                args.put("lesion_roja", 1);
                db.insert("Goleadores",null,args);
                Toast.makeText(getApplicationContext(),"Lesión tendra que salir del terreno de juego ", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;


        }
        finish();
    }
}
