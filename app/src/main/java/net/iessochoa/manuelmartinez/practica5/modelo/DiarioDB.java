package net.iessochoa.manuelmartinez.practica5.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import net.iessochoa.manuelmartinez.practica5.modelo.DiarioContract.DiaDiarioEntries;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DiarioDB {

    //Constantes de versión y fichero de la base de datos
    private final static int DB_VERSION = 1;
    private final static String DB_NOMBRE = "diario.db";

    //Sentencias de sql de creacion y eliminación de la tabla

    private final static String SQL_CREATE = "CREATE TABLE if not exists " + DiaDiarioEntries.TABLE_NAME + " (" +
            DiaDiarioEntries.ID + " integer primary key autoincrement," +
            DiaDiarioEntries.FECHA + " TEXT UNIQUE NOT NULL," +
            DiaDiarioEntries.VALORACION + " INTEGER NOT NULL," +
            DiaDiarioEntries.RESUMEN + " TEXT," +
            DiaDiarioEntries.CONTENIDO + " TEXT," +
            DiaDiarioEntries.FOTO_URI + " TEXT," +
            DiaDiarioEntries.LATITUD + " TEXT," +
            DiaDiarioEntries.LONGITUD + " TEXT" +
            ")";

    private final static String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + DiaDiarioEntries.TABLE_NAME;


    //DBHelper

    //Nos permitira abrir la base de datos
    private DiaDiarioDbHelper dbH;
    private SQLiteDatabase db;

    public DiarioDB(Context context) {
        dbH = new DiaDiarioDbHelper(context);
    }

    //Abre la base de datos
    public void open() throws SQLiteException {
        if ((db == null) || (!db.isOpen())) {
            db = dbH.getWritableDatabase();
        }
    }

    public void close() throws SQLiteException {
        if (db.isOpen())
            db.close();
    }

    //Clase interna con la que decir a android que cree la BD y lo que hacer cuando se modifica la version de la BD
    private class DiaDiarioDbHelper extends SQLiteOpenHelper {

        //Constructor de la clase
        public DiaDiarioDbHelper(Context context) {
            super(context, DB_NOMBRE, null, DB_VERSION);
        }

        //Metodos de la clase que debemos sobreescribir

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_TABLE);
            db.execSQL(SQL_CREATE);
        }
    }

    //Metodo para la fecha

    public static Date fechaBDtoFecha(String f) {
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = null;
        try {
            fecha = formatoDelTexto.parse(f);

        } catch (ParseException ex) {

            ex.printStackTrace();

        }
        return fecha;
    }

    public static String fechaToFechaDB(Date fecha) {
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        return f.format(fecha);
    }


    //CRUD del sql

    //Inserta nuevo Dia en el diario pasado por parametro
    public void insertaDia(DiaDiario dia) throws SQLiteException, SQLiteConstraintException {
        ContentValues values = new ContentValues();
        if (!dia.getFecha().equals("")) {
            values.put(DiaDiarioEntries.FECHA, fechaToFechaDB(dia.getFecha()));
        }
        values.put(DiaDiarioEntries.VALORACION, dia.getValoracionResumida());
        values.put(DiaDiarioEntries.RESUMEN, dia.getResumen());
        values.put(DiaDiarioEntries.CONTENIDO, dia.getContenido());
        values.put(DiaDiarioEntries.FOTO_URI, dia.getFotoUri());
        values.put(DiaDiarioEntries.LATITUD, dia.getLatitud());
        values.put(DiaDiarioEntries.LONGITUD, dia.getLongitud());
        //Si queremos que salte la excepción en caso de problemas en la insercción
        //tenemos que utilizar insertOrThrow, en otro caso podemos utilizar insert
        db.insertOrThrow(DiaDiarioEntries.TABLE_NAME, null, values);

    }

    //Actualiza el dia pasado por parametro
    public void actualizaDia(DiaDiario dia) throws SQLiteException, SQLiteConstraintException {
        //valores a modificar
        ContentValues values = new ContentValues();
        if (!dia.getFecha().equals("")) {
            values.put(DiaDiarioEntries.FECHA, fechaToFechaDB(dia.getFecha()));
        }
        values.put(DiaDiarioEntries.VALORACION, dia.getValoracionResumida());
        values.put(DiaDiarioEntries.RESUMEN, dia.getResumen());
        values.put(DiaDiarioEntries.CONTENIDO, dia.getContenido());
        values.put(DiaDiarioEntries.FOTO_URI, dia.getFotoUri());
        values.put(DiaDiarioEntries.LATITUD, dia.getLatitud());
        values.put(DiaDiarioEntries.LONGITUD, dia.getLongitud());
        //sentencia where
        String where = DiaDiarioEntries.FECHA + "=?";
        String[] arg = new String[]{fechaToFechaDB(dia.getFecha())};
        //actualizamos
        db.update(DiaDiarioEntries.TABLE_NAME, values, where, arg);
    }

    //Borra un dia pasado por parametro
    public void borraDia(DiaDiario dia) throws SQLiteException, SQLiteConstraintException {
        db.delete(DiaDiarioEntries.TABLE_NAME, DiaDiarioEntries.FECHA + "= ?", new String[]{fechaToFechaDB(dia.getFecha())});
    }

    //Cursor
    public Cursor getCursorDia(String id) throws SQLiteException {
        String where = null;
        String[] argWhere = null;
        if (id != null) {
            where = DiaDiarioEntries.ID + "= ?";
            argWhere = new String[]{id};
        }
        Cursor cursor = db.query(DiaDiarioEntries.TABLE_NAME, null, where, argWhere, null, null, null);
        //close();
        return cursor;
        //otra forma
        //return db.rawQuery("SELECT * FROM ALUMNOS "+where,argWhere);
    }

    /**
     * Dada una posición del cursor, nos devuelve un objeto Dia
     */
    public static DiaDiario deCursorADia(Cursor cursor) {
        int indiceColumna;
        //obtenemos la posicion de la columna id
        indiceColumna = cursor.getColumnIndex(DiaDiarioEntries.ID);
        //obtenemos el valor del id
        String id = cursor.getString(indiceColumna);
        //obtenemos el resto de posiciones de las columnas
        indiceColumna = cursor.getColumnIndex(DiaDiarioEntries.FECHA);
        Date fecha = fechaBDtoFecha(cursor.getString(indiceColumna));
        indiceColumna = cursor.getColumnIndex(DiaDiarioEntries.VALORACION);
        int valoracion = cursor.getInt(indiceColumna);
        indiceColumna = cursor.getColumnIndex(DiaDiarioEntries.RESUMEN);
        String resumen = cursor.getString(indiceColumna);
        indiceColumna = cursor.getColumnIndex(DiaDiarioEntries.CONTENIDO);
        String contenido = cursor.getString(indiceColumna);
        indiceColumna = cursor.getColumnIndex(DiaDiarioEntries.FOTO_URI);
        String foto = cursor.getString(indiceColumna);
        indiceColumna = cursor.getColumnIndex(DiaDiarioEntries.LATITUD);
        String latitud = cursor.getString(indiceColumna);
        indiceColumna = cursor.getColumnIndex(DiaDiarioEntries.LONGITUD);
        String longitud = cursor.getString(indiceColumna);

        return new DiaDiario(fecha, valoracion, resumen, contenido, foto, latitud, longitud);

    }

}
