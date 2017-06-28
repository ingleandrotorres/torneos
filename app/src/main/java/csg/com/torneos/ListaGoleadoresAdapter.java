package csg.com.torneos;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by jaitor on 23/12/16.
 */
public class ListaGoleadoresAdapter extends ArrayAdapter<ListaGoleadores>  implements View.OnClickListener {

    Context mycontext;
    int mylayoutResurseID;
    List<ListaGoleadores> mydata =null;


    public ListaGoleadoresAdapter (Context context, int layoutResurseID, List<ListaGoleadores> data){
        super(context,layoutResurseID,data);
        this.mycontext = context;
        this.mylayoutResurseID = layoutResurseID;
        this.mydata = data;


    }

    public View getView(int position, View convertView, ViewGroup parent){

        View row = convertView;

        ListaGoleadoresHolder holder = null;

        if (row == null){
            LayoutInflater inflater = ((Activity)mycontext).getLayoutInflater();
            row = inflater.inflate(mylayoutResurseID,parent,false);

            holder = new ListaGoleadoresHolder();

            holder.imagen = (ImageView) row.findViewById(R.id.jugadorImage);
            holder.imagen.setOnClickListener(this);
            holder.imagen.setTag("imagen click");
            holder.textView = (TextView) row.findViewById(R.id.nombrejugador);
            holder.textView.setOnClickListener(this);
            holder.textView.setTag("texview click");

            row.setTag(holder);


        }else{
            holder = (ListaGoleadoresHolder)row.getTag();
        }


        ListaGoleadores lg = mydata.get(position);
        holder.textView.setText(lg.nombre); //ListaGoleadores.nombre// estos nombres salen de listagoleadores.java
        holder.imagen.setImageResource(lg.tarjeta);

        return row;

    }
    static class ListaGoleadoresHolder{

        ImageView imagen;
        TextView textView;

    }



    public void onClick(View v) {


        Log.e("Error","click sobre"+v.getId()+" con tag "+v.getTag()+" y text");




    }
}
