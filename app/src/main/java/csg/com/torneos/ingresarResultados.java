package csg.com.torneos;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.HttpGet;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.loopj.android.http.*;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.*;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;


public class ingresarResultados extends AppCompatActivity implements View.OnClickListener {

    DBResultados tabla_resultados;
    SQLiteDatabase db;
    String torneo = "";
    String id_partido;

    ListView listaJugadores;

    ImageView botongoleadores = null;

    RadioGroup radioButtonGroup;
    Integer puntoInvisibleE1 = 0;
    Integer puntoInvisibleE2 = 0;

    String nombreEquipo1 = "";
    String nombreEquipo2 = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_resultados);


        id_partido = getIntent().getStringExtra("id");
        torneo =  getIntent().getStringExtra("torneo");


        tabla_resultados  = new DBResultados(getApplicationContext(), "TORNEOS", null, 8);
        db = tabla_resultados.getReadableDatabase();



        String [] campo = resultadoPartidoById(id_partido);

        TextView equipo1 = (TextView)findViewById(R.id.equipo1);
        equipo1.setText((String) campo[1]);

        TextView equipo1_resultado = (TextView)findViewById(R.id.equipo1_resultado);
        equipo1_resultado.setText((String) campo[2]);

        TextView equipo2_resultado = (TextView)findViewById(R.id.equipo2_resultado);
        equipo2_resultado.setText((String) campo[3]);

        TextView equipo2 = (TextView)findViewById(R.id.equipo2);
        equipo2.setText((String) campo[4]);


        nombreEquipo1 = campo[1];
        nombreEquipo2 = campo[4];




        Button mas1 = (Button)findViewById(R.id.mas1);
        Button menos1 = (Button)findViewById(R.id.menos1);
        Button mas2 = (Button)findViewById(R.id.mas2);
        Button menos2 = (Button)findViewById(R.id.menos2);
        Button botonGuardar = (Button) findViewById(R.id.guardarPartido);
        Button verEstadisticas = (Button)findViewById(R.id.verEstadisticas);
        Button vertabla = (Button)findViewById(R.id.vertabla);




        mas1.setOnClickListener( (View.OnClickListener)this);
        menos1.setOnClickListener( (View.OnClickListener)this);
        mas2.setOnClickListener((View.OnClickListener)this);
        menos2.setOnClickListener( (View.OnClickListener)this);
        botonGuardar.setOnClickListener((View.OnClickListener)this);
        verEstadisticas.setOnClickListener((View.OnClickListener)this);
        vertabla.setOnClickListener((View.OnClickListener)this);

        //botongoleadores = (ImageView) findViewById(R.id.imageView);
        //botongoleadores.setOnClickListener((View.OnClickListener)this);



        listaJugadores = (ListView)findViewById(R.id.listView);




        String[] datos2 ={"Agregar","Jugadores"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_expandable_list_item_1,datos2);
        listaJugadores.setAdapter(adapter);

        radioButtonGroup = (RadioGroup)findViewById(R.id.puntoInvisible);
        radioButtonGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonE1){
                    puntoInvisibleE1 = 1;
                    puntoInvisibleE2 = 0;
                }else if(checkedId == R.id.radioButtonE2){
                    puntoInvisibleE1=0;
                    puntoInvisibleE2=1;

                }
            }
        });


        new JsonTask(this).execute("https://spreadsheets.google.com/feeds/list/1NfEQ7yGhkW6BD5vHWJfp_67go6jgHblegrc_Pxzj9jA/od6/public/values?alt=json");


    }

    protected String[]  resultadoPartidoById(String id){

        String[] args = new String[]{id}, resultado = new String[5];
        Cursor c = db.rawQuery("SELECT id, equipo1,equipo2,fecha,marcador_equipo1,marcador_equipo2 FROM Partidos WHERE id = ?;",args);
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
    public void onClick(View v) {


        TextView equipo1_resultado = (TextView)findViewById(R.id.equipo1_resultado);
        TextView equipo2_resultado = (TextView)findViewById(R.id.equipo2_resultado);
        //TextView equipo1 = (TextView)findViewById(R.id.equipo1);
        //TextView equipo2 = (TextView)findViewById(R.id.equipo2);


        Log.e("temp", "getid"+v.getId());

        switch(v.getId()){

            case R.id.mas1:
                equipo1_resultado.setText(String.valueOf(Integer.parseInt(equipo1_resultado.getText().toString())+1));
                break;
            case R.id.menos1:
                equipo1_resultado.setText(String.valueOf(Integer.parseInt(equipo1_resultado.getText().toString())-1));
                break;
            case R.id.mas2:
                equipo2_resultado.setText(String.valueOf(Integer.parseInt(equipo2_resultado.getText().toString())+1));
                break;
            case R.id.menos2:
                equipo2_resultado.setText(String.valueOf(Integer.parseInt(equipo2_resultado.getText().toString())-1));
                break;
            case R.id.guardarPartido:

                ContentValues cv = new ContentValues();
                //esta opcion tambien es valida cv.put("marcador_equipo1",Integer.parseInt(equipo1_resultado.getText().toString()));
                //esta opcion tambien es valida cv.put("marcador_equipo2", equipo2_resultado.getText().toString());
                cv.put("marcador_equipo1",String.valueOf(equipo1_resultado.getText()));
                cv.put("marcador_equipo2", String.valueOf(equipo2_resultado.getText()));
                cv.put("punto_invisible_equipo1",puntoInvisibleE1);
                cv.put("punto_invisible_equipo2", puntoInvisibleE2);
                cv.put("estado", "JUGADO");


                asignarPuntos(cv);


                guardarPartidoId(cv);

/*
                Intent refresh = new Intent(this, resultados.class);
                startActivity(refresh);
                finish(); //*/
                //acabar esta actividad e iniciar otra

                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                setResult(RESULT_OK,intent);
                finish();


                break;
          //  case R.id.imageView:


              //  new JsonTask(this).execute("https://spreadsheets.google.com/feeds/list/1NfEQ7yGhkW6BD5vHWJfp_67go6jgHblegrc_Pxzj9jA/od6/public/values?alt=json");
            //    break;

            case R.id.verEstadisticas:

                Log.e("temp", "pasa por el click");
                Intent intentTablaGoleadores = new Intent(getApplicationContext(), tablaGoleadores.class);
                intentTablaGoleadores.putExtra("torneo", torneo);
                startActivity(intentTablaGoleadores);


                break;

            case R.id.vertabla:


                Intent intentTablaPruebas = new Intent(getApplicationContext(), tabla.class);
                intentTablaPruebas.putExtra("torneo", torneo);
                startActivity(intentTablaPruebas);


                break;


            case R.id.listView:


                        Toast.makeText(getApplicationContext(),"el id del list view..hola ", Toast.LENGTH_LONG);


                break;

        }
    }


    private void guardarPartidoId(ContentValues cv) {

        db = tabla_resultados.getWritableDatabase();

        //nombre de la base de datos; valores campos vs actualizar;clausula where en string; argumentos
        db.update("Partidos", cv, " id=?", new String[]{id_partido});
        Log.i("Error","Se actualizo partido con id principal"+id_partido);

    }



    private  ContentValues asignarPuntos(ContentValues cv){


        if (cv.getAsInteger("marcador_equipo1") > cv.getAsInteger("marcador_equipo2")){
            cv.put("puntos_equipo1",3);
            cv.put("puntos_equipo2",0);
        }else if(cv.getAsInteger("marcador_equipo1") < cv.getAsInteger("marcador_equipo2")){
            cv.put("puntos_equipo1",0);
            cv.put("puntos_equipo2",3);
        }else if(cv.getAsInteger("marcador_equipo1") == cv.getAsInteger("marcador_equipo2")){
            cv.put("puntos_equipo1",1);
            cv.put("puntos_equipo2",1);
        }

        return cv;
    }



    public class JsonTask extends AsyncTask <String, Void, List<ListaGoleadores> >{

        //public JsonTask(){}
        Context myactividad;
        protected JsonTask(Context actividad){
            this.myactividad = actividad;
        }


        //ListaGoleadores listaGoleadoresDatos[] = new ListaGoleadores[9];
        List <ListaGoleadores> listaGoleadoresDatos = new ArrayList<ListaGoleadores>();

        @Override
        protected List<ListaGoleadores> doInBackground(String... urls) {


            HttpClient client = new DefaultHttpClient();
            HttpPost post =null;
            HttpGet get = null;
            get =new HttpGet(String.valueOf(urls[0]));
            post = new HttpPost(String.valueOf(urls[0]));// primer parametro de los que se envía
            HttpResponse response = null;
            try {
                response = client.execute(get);
            } catch (IOException e) {
                e.printStackTrace();
            }


            int status = response.getStatusLine().getStatusCode();
            Log.e("Error", "El estado de traer el json fue: "+String.valueOf(status));
            if (status == 200){
                HttpEntity entity = response.getEntity();
                try {

                    String data = EntityUtils.toString(entity);
                    JSONObject json = new JSONObject(data);

                    //Estructura correcta;
                    //String jugador1 = json.getJSONObject("feed").getJSONArray("entry").getJSONObject(0).getJSONObject("gsx$nombre").getString("$t");
                    //String jugador2 = json.getJSONObject("feed").getJSONArray("entry").getJSONObject(1).getJSONObject("gsx$nombre").getString("$t");

                    JSONArray jsonArray = json.getJSONObject("feed").getJSONArray("entry");
                    for(int i=0;i < jsonArray.length();i++){

                        String nombrejugador = jsonArray.getJSONObject(i).getJSONObject("gsx$nombre").getString("$t");
                        String nombreequipo = jsonArray.getJSONObject(i).getJSONObject("gsx$equipo").getString("$t");
                        String id_jugador = jsonArray.getJSONObject(i).getJSONObject("gsx$id").getString("$t");
                        //listado.add(jsonArray.getJSONObject(i).getJSONObject("gsx$nombre").getString("$t"));
                        Log.e("Errro","nombre de los equipos"+nombreEquipo1+nombreEquipo2+" ---- "+  nombreequipo);
                        if (nombreEquipo1.equals(nombreequipo) || nombreEquipo2.equals(nombreequipo) ) {//filtro para jugadores de los dos equipos que se enfrentan
                            //public ListaGoleadores(String id,String  nombre, String equipo,int tarjeta){
                            listaGoleadoresDatos.add(new ListaGoleadores(id_jugador, nombrejugador,nombreequipo, R.drawable.jugador));
                            Log.e("INFO","Recuperacion de datos de google docs");
                        }

                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    Log.e("Error","se fue por el jsonexception");
                    e.printStackTrace();
                }
            }

        return listaGoleadoresDatos;
        }

        @Override
        protected void onPostExecute(List<ListaGoleadores> listado) {


            ListaGoleadoresAdapter adapter = new ListaGoleadoresAdapter(myactividad,R.layout.listview_agregar_gol,listado);// xml listview_agregar_gol estructura del listview, getaplicationcontext o this


            listaJugadores.setAdapter(adapter);

            listaJugadores.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    ListaGoleadores obj = (ListaGoleadores) listaJugadores.getAdapter().getItem(position);


                    Intent tipoAnotacion = new Intent(getApplicationContext(), tipo_anotacion.class);
                    tipoAnotacion.putExtra("id", id_partido);
                    tipoAnotacion.putExtra("id_partido", id_partido);//no se necesita eso solo indica en que id estructura torneo está
                    tipoAnotacion.putExtra("id_jugador", obj.id);
                    tipoAnotacion.putExtra("equipo", obj.equipo);
                    tipoAnotacion.putExtra("jugador",obj.nombre);
                    tipoAnotacion.putExtra("torneo",torneo);


                    startActivityForResult(tipoAnotacion, 3);// 2 identificador proceso

                }
            });

        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }




    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Log.e("","OnActivityResult para tipo de gol falta");
            Intent refresh = new Intent(this, ingresarResultados.class);
            refresh.putExtra("torneo",torneo);
            refresh.putExtra("id",id_partido);
            startActivity(refresh);
            this.finish();
        }
    }
    @Override
    public void onBackPressed() {
    }

}




// Android se agrega dependencia libreri a httoclient
//http://loopj.com/android-async-http/



