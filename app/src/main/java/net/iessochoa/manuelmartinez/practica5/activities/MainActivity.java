package net.iessochoa.manuelmartinez.practica5.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import net.iessochoa.manuelmartinez.practica5.R;
import net.iessochoa.manuelmartinez.practica5.modelo.DiaDiario;
import net.iessochoa.manuelmartinez.practica5.modelo.DiarioDB;

public class MainActivity extends AppCompatActivity {
    public final static int REQUEST_OPTION_NUEVA_ENTRADA_DIARIO = 0;
    public static final int REQUEST_OPTION_EDITAR_ENTRADA_DIARIO = 1;
    public static String STATE_LISTA_POBLACIONES = "net.iessochoa.manuelmartinez.practica5.activities.EdicionDiaActivity.lista_dias";

    private DiarioDB db;
    Button btAcercade;
    Button btAnyadir;
    Button btOrdenar;
    Button btBorrar;
    TextView tvPrincipal;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btAcercade = findViewById(R.id.btAcercade);
        btAnyadir = findViewById(R.id.btAnyadir);
        btOrdenar = findViewById(R.id.btOrdenar);
        btBorrar = findViewById(R.id.btBorrar);
        tvPrincipal = findViewById(R.id.tvPrincipal);

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

    public void agregaDiaDiario() {
        Intent intent = new Intent(MainActivity.this, EdicionDiaActivity.class);
        startActivityForResult(intent, REQUEST_OPTION_NUEVA_ENTRADA_DIARIO);
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
                agregaDiaDiario();
                break;
            case R.id.btOrdenar:
                Toast.makeText(getApplicationContext(), getResources().getText(R.string.tmMensajeERROR), Toast.LENGTH_LONG).show();
                break;
            case R.id.btBorrar:
                Toast.makeText(getApplicationContext(), getResources().getText(R.string.tmMensajeERROR), Toast.LENGTH_LONG).show();
                break;
            case R.id.btValorarVida:
                Toast.makeText(getApplicationContext(), getResources().getText(R.string.tmMensajeERROR), Toast.LENGTH_LONG).show();
                break;
            case R.id.btMostrarDesdeHasta:
                Toast.makeText(getApplicationContext(), getResources().getText(R.string.tmMensajeERROR), Toast.LENGTH_LONG).show();
                break;
            case R.id.btOpciones:
                Toast.makeText(getApplicationContext(), getResources().getText(R.string.tmMensajeERROR), Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Metodos CRUD del ActivityMain
     */

    private void anyadirDia(DiaDiario d) {
        if (db.equals(d)) {
            db.borraDia(d);
        }
        db.insertaDia(d);
        //adaptador.notifyDataSetChanged();
    }

    private void borrarDia(DiaDiario d) {
        db.borraDia(d);
        //adaptador.notifyDataSetChanged();
    }

    private void editarDia(final DiaDiario d) {
        db.actualizaDia(d);
        //adaptador.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_OPTION_NUEVA_ENTRADA_DIARIO:
                    DiaDiario p = data.getParcelableExtra(EdicionDiaActivity.EXTRA_DIA_A_GUARDAR);
                    anyadirDia(p);
                    break;
                /*case REQUEST_OPTION_EDITAR_POBLACIONES:
                    Poblacion pi = data.getParcelableExtra(PoblacionActivity.EXTRA_POBLACION_A_GUARDAR);
                    editarPoblacion(pi);
                    lvListaPoblaciones.getAdapter();
                    break;*/
            }
        }
    }
}
