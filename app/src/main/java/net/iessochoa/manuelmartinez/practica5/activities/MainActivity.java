package net.iessochoa.manuelmartinez.practica5.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import net.iessochoa.manuelmartinez.practica5.R;
import net.iessochoa.manuelmartinez.practica5.modelo.DiarioDB;

public class MainActivity extends AppCompatActivity {

    private DiarioDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DiarioDB(this);
        db.open();
        db.close();
    }
}
