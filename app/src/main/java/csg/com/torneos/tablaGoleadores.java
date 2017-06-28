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

public class tablaGoleadores extends AppCompatActivity {



    DBResultados tabla_resultados;
    SQLiteDatabase db;
    String torneo;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabla_goleadores);

        TableLayout t ;
        t = (TableLayout)findViewById(R.id.tablalayoutgoleadores);

        //t.addView(label_hello);

        torneo =  getIntent().getStringExtra("torneo");

        tabla_resultados  = new DBResultados(getApplicationContext(), "TORNEOS", null, 8);
        db = tabla_resultados.getReadableDatabase();


        String string = "";
        String[] args = new String[]{torneo};
        ArrayList resultado = new ArrayList();
        ArrayList contResultado = new ArrayList();
        Cursor c = db.rawQuery("SELECT jugador,equipo, sum(gol) as suma_goles, sum(amarilla), sum(roja),sum(lesion_amarilla),sum(lesion_roja),sum(autogol), sum(lesion_amarilla),sum(lesion_roja) FROM Goleadores WHERE torneo = ? Group By jugador,equipo ORDER BY suma_goles DESC;",args);


        int numeroCamposDB = 10;
        int numeroCamposTB = numeroCamposDB;

        TextView[] campo = new TextView[c.getCount()*numeroCamposDB];
        String texto;
        if (c.moveToFirst()){
            c.getCount();
            int j=0;
            do {
                for(int i=0; i<numeroCamposDB ;i++){
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
                    j++;
                }
                string = string+"\n"+c.getString(0).toString()+"---"+c.getString(1).toString()+"---"+c.getString(2);
            }while(c.moveToNext());
        }


        TableRow[] fila = new TableRow[c.getCount()];


        int numeroFilas = 0,k=0,j=0,i=0;
        numeroFilas = campo.length /numeroCamposDB;

        k=0;
        for ( j=0 ;j<numeroFilas;j++) {
            fila[j] = new TableRow(this);
            //fila[i].setId(i+111);
            fila[j].setBackgroundColor(Color.blue(292));


            for (i = 0; i < numeroCamposTB; i++) {

                if (campo[k] == null) {
                } else {
                    if (i==0) {
                        TextView pos = new TextView(this);
                        pos.setText(String.valueOf(k));
                        fila[j].addView(pos);
                    }
                    fila[j].addView(campo[k]);
                }

                k++;

            }
            t.addView(fila[j], new TableLayout.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

        }
/*
        TextView result = (TextView) findViewById(R.id.goleadores);
        result.setText(string);
*/

    }
}
