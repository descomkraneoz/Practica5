package net.iessochoa.manuelmartinez.practica5.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import net.iessochoa.manuelmartinez.practica5.R;
import net.iessochoa.manuelmartinez.practica5.modelo.DiaDiario;
import net.iessochoa.manuelmartinez.practica5.modelo.DiarioContract;
import net.iessochoa.manuelmartinez.practica5.modelo.DiarioDB;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public final static int REQUEST_OPTION_NUEVA_ENTRADA_DIARIO = 0;
    public static final int REQUEST_OPTION_EDITAR_ENTRADA_DIARIO = 1;
    public static String STATE_LISTA_POBLACIONES = "net.iessochoa.manuelmartinez.practica5.activities.EdicionDiaActivity.lista_dias";

    private DiarioDB db;
    Button btAcercade;
    Button btAnyadir;
    Button btOrdenar;
    Button btFecha;
    Button btValoracion;
    Button btResumen;
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
        btFecha = findViewById(R.id.btFecha);
        btValoracion = findViewById(R.id.btValoracion);
        btResumen = findViewById(R.id.btResumen);



        //BASE DE DATOS
        db = new DiarioDB(this);
        db.open();
        cargarDatosDePrueba();
        mostrarDias(DiarioContract.DiaDiarioEntries.FECHA);



    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
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
                menuOrdenarPor();
                break;
            case R.id.btBorrar:
                //db.borraDia(DiarioDB.deCursorADia(db.obtenDiario(DiarioContract.DiaDiarioEntries.ID)));
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
     * Metodo para mostrar
     */

    private void mostrarDias(String ordenadoPor) {
        Cursor c = db.obtenDiario(ordenadoPor);
        DiaDiario dia;
        tvPrincipal.setText("");//limpiamos el campo de texto
        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                dia = DiarioDB.deCursorADia(c);
                //podéis sobrecargar toString en DiaDiario para mostrar los datos
                //tvPrincipal.append(dia.toString() + "\n");
                tvPrincipal.append(dia.mostrarDatosBonitos() + "\n");
            } while (c.moveToNext());
        }
    }

    private View.OnClickListener menuOrdenarPor() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.btFecha:
                        mostrarDias(DiarioContract.DiaDiarioEntries.FECHA);
                        break;
                    case R.id.btValoracion:
                        mostrarDias(DiarioContract.DiaDiarioEntries.VALORACION);
                        break;
                    case R.id.btResumen:
                        mostrarDias(DiarioContract.DiaDiarioEntries.RESUMEN);
                        break;
                }

            }
        };

    }

    private void cargarDatosDePrueba() {
        DiaDiario d = new DiaDiario(new Date("11/02/2002"),
                5, "Examen de Lenguaje de Marcas",
                "Los temas que entran son HTML y CSS, deberas hacer una página" +
                        " web con la estructura típica, y contestar veinte preguntas de " +
                        "tipo test en 30 minutos");
        db.insertaDia(d);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_OPTION_NUEVA_ENTRADA_DIARIO:
                    DiaDiario p = data.getParcelableExtra(EdicionDiaActivity.EXTRA_DIA_A_GUARDAR);
                    db.insertaDia(p);
                    mostrarDias(DiarioContract.DiaDiarioEntries.FECHA);
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
