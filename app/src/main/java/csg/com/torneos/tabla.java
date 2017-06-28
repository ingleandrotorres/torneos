package csg.com.torneos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class tabla extends AppCompatActivity {


    DBResultados tabla_resultados;
    SQLiteDatabase db;
    String torneo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabla);



        torneo =  getIntent().getStringExtra("torneo");

        Log.e("temp", "llega a la aactividad y torneo es "+torneo);

        tabla_resultados  = new DBResultados(getApplicationContext(), "TORNEOS", null, 8);
        db = tabla_resultados.getReadableDatabase();


        String string = "";
        String[] args = new String[]{torneo};
        ArrayList resultado = new ArrayList();
        ArrayList contResultado = new ArrayList();



        String head = "torneo id_partido equipo1 equipo2 fecha tecnico1 tecnico2 marcador1 marcador2 penales1 penales2  invisible1 invisible2 estado puntos1 puntos2 \n";


        Cursor c = db.rawQuery("SELECT * FROM Partidos WHERE torneo = ? ;",args);



        int j = 0, numeroCamposDB= 17,f=0;
        int numeroFilasDB = c.getCount();
        String texto = "0";

        TextView[] campo = new TextView[numeroFilasDB*numeroCamposDB];
        TableRow[] fila = new TableRow[numeroFilasDB];
        TableLayout t = (TableLayout)findViewById(R.id.tablalayout);

        if (c.moveToFirst()){
            do {

                fila[f] = new TableRow(this);
                //fila[i].setId(i+111);
                fila[f].setBackgroundColor(Color.blue(292));



                for(int i = 0; i<numeroCamposDB ;i++) {
                   // resultado.add(c.getString(i));
                   //debuguer;
                    string = string+c.getString(i)+":";


                        campo[j] = new TextView(this);
                        //campo [i].setId(i+1);
                        if (c.getString(i) == "" || c.getString(i) == null)texto="0";
                        else texto = c.getString(i);
                        campo[j].setText(texto);
                        campo[j].setTextColor(Color.WHITE);
                        campo[j].setPadding(5, 5, 5, 5);
                        campo[j].setLayoutParams(new TableRow.LayoutParams(
                                TableRow.LayoutParams.MATCH_PARENT,
                                TableRow.LayoutParams.WRAP_CONTENT));


                    fila[f].addView(campo[j]);
                    j++;
                }



                t.addView(fila[f], new TableLayout.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));

                f++;
                string = string+"\n";
            }while(c.moveToNext());
        }


        /*

        int numeroCamposDB = 8;

        TextView[] campo = new TextView[c.getCount()*numeroCamposDB];
        TableRow[] fila = new TableRow[c.getCount()];
        String texto;

        int i,i2=0,j = 0,k =0,l=0;
        for(i = 0;i < resultado.size(); i++) {
            //ArrayList label = (ArrayList) contResultado.get(j);
            //for (i = 0; i < label.size(); i++) {
            //texto = (label.get(i)==null)?"SIN DATO": (String) label.get(i);

            texto = (String) resultado.get(i);
            campo[i] = new TextView(this);
            //campo [i].setId(i+1);
            campo[i].setText(texto);
            campo[i].setTextColor(Color.WHITE);
            campo[i].setPadding(5, 5, 5, 5);
            campo[i].setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

        }
        int numeroFilas = 0;
        numeroFilas = campo.length /numeroCamposDB;

        k=0;
        for ( j=0 ;j<numeroFilas;j++) {
            for (i = 0; i < 7; i++) {

                fila[j] = new TableRow(this);
                //fila[i].setId(i+111);
                fila[j].setBackgroundColor(Color.RED);
                if (!(campo[k] == null)) {
                } else {
                    fila[j].addView(campo[k]);
                }

                k++;

            }
            t.addView(fila[j], new TableLayout.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

        }



*/



      //   TextView result = (TextView) findViewById(R.id.textViewTabla);
      //  result.setText(head+" "+string);



    }
}
