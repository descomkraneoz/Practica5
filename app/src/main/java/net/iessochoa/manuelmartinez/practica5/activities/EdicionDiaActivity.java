package net.iessochoa.manuelmartinez.practica5.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.iessochoa.manuelmartinez.practica5.R;
import net.iessochoa.manuelmartinez.practica5.fragments.DatePickerFragment;
import net.iessochoa.manuelmartinez.practica5.modelo.DiaDiario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EdicionDiaActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    public static final String EXTRA_DIA_A_GUARDAR = "net.iessochoa.manuelmartinez.practica5.activities.dia_guardar";
    //public static final String EXTRA_POBLACION_RECIBIDA_A_EDITAR = "net.iessochoa.manuelmartinez.practica4.PoblacionActivity.poblacion_editar";


    EditText etFecha;
    Button btFecha;
    EditText etResumenBreve;
    Spinner spValorarVida;
    EditText etResumenGeneral;
    FloatingActionButton fabGuardar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicion_dia);
        etFecha = findViewById(R.id.etFecha);
        btFecha = findViewById(R.id.btFecha);
        etResumenBreve = findViewById(R.id.etResumenBreve);
        spValorarVida = findViewById(R.id.spValorarVida);
        etResumenGeneral = findViewById(R.id.etResumenGeneral);
        fabGuardar = findViewById(R.id.fabGuardar);

        btFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Fecha");

            }
        });

        /**
         * Metodo para guardar y mandar la nueva entrada del diario al MainActivity
         */

        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DiaDiario d = new DiaDiario(fechaBDtoFecha(etFecha.getText().toString()),
                        Integer.parseInt(spValorarVida.getSelectedItem().toString()),
                        etResumenBreve.getText().toString(),
                        etResumenGeneral.getText().toString());
                Intent intent = new Intent();
                intent.putExtra(EXTRA_DIA_A_GUARDAR, d);
                setResult(RESULT_OK, intent);
                finish();
            }
        });



    }

    /**
     * Metodo para obtener una fecha del dialogo datepicker
     *
     * @param view
     * @param year
     * @param month
     * @param dayOfMonth
     */

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        //String currentDateString = DateFormat.getDateInstance(DateFormat.MEDIUM).format(c.getTime());
        String currentDateString = dayOfMonth + "/" + month + "/"+year;
        etFecha.setText(currentDateString);
    }

    /**
     * Metodo para tratar las fechas, recibe un String y devuelve un Date
     */

    public static Date fechaBDtoFecha(String f) {
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
        Date fecha = null;
        try {
            fecha = formatoDelTexto.parse(f);

        } catch (ParseException ex) {

            ex.printStackTrace();

        }
        return fecha;
    }

}
