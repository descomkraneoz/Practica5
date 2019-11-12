package net.iessochoa.manuelmartinez.practica5.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import net.iessochoa.manuelmartinez.practica5.R;
import net.iessochoa.manuelmartinez.practica5.modelo.DiarioDB;

public class MainActivity extends AppCompatActivity {

    private DiarioDB db;
    Button btAcercade;
    Button btAnyadir;
    Button btOrdenar;
    Button btBorrar;

    //Voy x crear la Activity EdicionDiaActivity


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btAcercade = findViewById(R.id.btAcercade);
        btAnyadir = findViewById(R.id.btAnyadir);
        btOrdenar = findViewById(R.id.btOrdenar);
        btBorrar = findViewById(R.id.btBorrar);

        //BASE DE DATOS
        db = new DiarioDB(this);
        db.open();
        db.close();
    }

    /**
     * @param menu, para poder mostrar el menu hace falta inflarlo antes.
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Metodo para crear el mensaje de alerta al pulsar sobre el boton de Acerca de del Menu de la app
     */

    public void MensajeAcercade() {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        // titulo y mensaje
        dialogo.setTitle(getResources().getString(R.string.acercadeTitulo));
        dialogo.setMessage(getResources().getString(R.string.acercadeMensaje));

        // agregamos botón Ok y su evento
        dialogo.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Qué hacemos en caso ok
                        onRestart();
                    }
                });
        dialogo.show();
    }

    /**
     * Metodo del boton (+) del menu que llama a la PoblacionActivity
     */

    public void agregaPoblacion() {
        //Intent intent = new Intent(MainActivity.this, PoblacionActivity.class);
        //startActivityForResult(intent, REQUEST_OPTION_NUEVA_POBLACIONES);
    }

    /**
     * Llamada a los botones o elementos del menu de la app
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btAcercade:
                MensajeAcercade();
                break;
            case (R.id.btAnyadir):
                agregaPoblacion();
                break;
            case R.id.btOrdenar:
                Toast.makeText(getApplicationContext(), getResources().getText(R.string.tmMensajeERROR), Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
