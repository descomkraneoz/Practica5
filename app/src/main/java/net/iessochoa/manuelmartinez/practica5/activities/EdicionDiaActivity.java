package net.iessochoa.manuelmartinez.practica5.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import net.iessochoa.manuelmartinez.practica5.R;

public class EdicionDiaActivity extends AppCompatActivity {
    public static final String EXTRA_DIA_A_GUARDAR = "net.iessochoa.manuelmartinez.practica5.activities.dia_guardar";
    //public static final String EXTRA_POBLACION_RECIBIDA_A_EDITAR = "net.iessochoa.manuelmartinez.practica4.PoblacionActivity.poblacion_editar";


    EditText etFecha;
    Button btFecha;
    EditText etResumenBreve;
    Spinner spValorarVida;
    EditText etResumenGeneral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicion_dia);
        etFecha = findViewById(R.id.etFecha);
        btFecha = findViewById(R.id.btFecha);
        etResumenBreve = findViewById(R.id.etResumenBreve);
        spValorarVida = findViewById(R.id.spValorarVida);
        etResumenGeneral = findViewById(R.id.etResumenGeneral);
    }
}
