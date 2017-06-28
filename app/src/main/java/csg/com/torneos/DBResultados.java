package csg.com.torneos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.Toast;

/**
 * Created by jaitor on 6/12/16.
 */
public class DBResultados extends SQLiteOpenHelper {



    String sqlCreate = "CREATE TABLE Partidos (id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "torneo TEXT," +
            "id_partido INTEGER," +
            "equipo1 TEXT," +
            "equipo2 TEXT," +
            "fecha TEXT," +
            "tecnico_equipo1 TEXT," +
            "tecnico_equipo2 TEXT," +
            "marcador_equipo1 INTEGER," +
            "marcador_equipo2 INTEGER," +
            "penales_equipo1 INTEGER," +
            "penales_equipo2 INTEGER," +
            "punto_invisible_equipo1 INTEGER," +
            "punto_invisible_equipo2 INTEGER," +
            "estado TEXT,"+
            "puntos_equipo1 INTEGER,"+
            "puntos_equipo2 INTEGER"+
            " )";

    String sqlCreateGoleadores = "CREATE TABLE Goleadores (id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "torneo TEXT," +
            "id_partido INTEGER," +
            "equipo TEXT," +
            "id_jugador TEXT," +
            "jugador TEXT," +
            "gol INTEGER," +
            "autogol INTEGER," +
            "amarilla INTEGER," +
            "roja INTEGER," +
            "lesion_amarilla INTEGER," +
            "lesion_roja INTEGER," +
            "suspension TEXT" +
            " )";

    public DBResultados(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
        db.execSQL(sqlCreateGoleadores);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Partidos");
        db.execSQL("DROP TABLE IF EXISTS Goleadores");
        onCreate(db);
        //Toast.makeText(this, "Se actualizo la base de datos se han borrado los datos", Toast.LENGTH_LONG).show();
    }

}
