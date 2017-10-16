package pt.itmanager.www.mywebviewapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Vitor on 13/10/2017.
 */

public class PopUpInfo extends Activity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popupinfo);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        //getWindow().setLayout((int)(width),(int)(height*.15));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.x = 0;
        params.height = (int)(height*.20);
        params.width = width;
        params.y = (int)(height*.36);

        getWindow().setAttributes(params);

        Intent intent = getIntent();
        String nome = intent.getExtras().getString("nome");
        String ultimonome = intent.getExtras().getString("ultimonome");
        String numero = intent.getExtras().getString("telefone");

        TextView textnome = (TextView) findViewById(R.id.textNome);
        textnome.setText(nome+" "+ultimonome);

        TextView textnumero = (TextView) findViewById(R.id.textNumero);
        textnumero.setText(numero);

        ImageView image = (ImageView) findViewById(R.id.imageView2);
        image.setImageResource(R.mipmap.ic_launcher);

        ImageView image1 = (ImageView) findViewById(R.id.imageView3);
        image1.setImageResource(R.drawable.ic_phone_black_24dp);


    }
}
