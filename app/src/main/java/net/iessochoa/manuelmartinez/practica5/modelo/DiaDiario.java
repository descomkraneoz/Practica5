package net.iessochoa.manuelmartinez.practica5.modelo;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class DiaDiario implements Parcelable {
    private Date fecha;
    private int valoracionDia;
    private String resumen;
    private String contenido;
    private String fotoUri;
    private String latitud;
    private String longitud;


    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getValoracionDia() {
        return valoracionDia;
    }

    public void setValoracionDia(int valoracionDia) {
        this.valoracionDia = valoracionDia;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getFotoUri() {
        return fotoUri;
    }

    public void setFotoUri(String fotoUri) {
        this.fotoUri = fotoUri;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public DiaDiario(Date fecha, int valoracionDia, String resumen, String contenido) {
        this.fecha = fecha;
        if(valoracionDia>=0 || valoracionDia<=10){
            this.valoracionDia = valoracionDia;
        }else{
            this.valoracionDia=0;
        }
        this.resumen = resumen;
        this.contenido = contenido;
    }

    public DiaDiario(Date fecha, int valoracionDia, String resumen, String contenido, String fotoUri, String latitud, String longitud) {
        this.fecha = fecha;
        if(valoracionDia>=0 || valoracionDia<=10){
            this.valoracionDia = valoracionDia;
        }else{
            this.valoracionDia=0;
        }
        this.resumen = resumen;
        this.contenido = contenido;
        this.fotoUri = fotoUri;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public int getValoracionResumida(){
        if (valoracionDia<5){
            return 1;
        }else if(valoracionDia>=5 && valoracionDia<=8){
            return 2;
        }else {
            return 3;
        }
    }

    public static String fechaToFechaDB(Date fecha) {
        DateFormat f = new SimpleDateFormat("MM/dd/yyyy", Locale.ROOT);
        return f.format(fecha);
    }

    @Override
    public String toString() {
        return "DiaDiario{" +
                "fecha=" + fechaToFechaDB(fecha) +
                ", valoracionDia=" + getValoracionResumida() +
                ", resumen='" + resumen + '\'' +
                ", contenido='" + contenido + '\'' +
                ", fotoUri='" + fotoUri + '\'' +
                ", latitud='" + latitud + '\'' +
                ", longitud='" + longitud + '\'' +
                '}';
    }

    public String mostrarDatosBonitos() {
        return fechaToFechaDB(fecha) + "\n" +
                "VALORACIÓN DEL DÍA: " + getValoracionResumida() + "\n" +
                "BREVE RESUMEN: " + resumen + "\n" +
                "CONTENIDO: " + contenido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiaDiario diaDiario = (DiaDiario) o;
        return fechaToFechaDB(fecha).equals(diaDiario.fechaToFechaDB(fecha));
    }

    @Override
    public int hashCode() {
        return Objects.hash(fecha);
    }

    //Hacemos los metodos parcelable


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(fechaToFechaDB(fecha));
        parcel.writeInt(valoracionDia);
        parcel.writeString(resumen);
        parcel.writeString(contenido);
        parcel.writeString(fotoUri);
        parcel.writeString(latitud);
        parcel.writeString(longitud);
    }

    protected DiaDiario(Parcel in) {
        fecha = new Date(in.readString());
        valoracionDia = in.readInt();
        resumen = in.readString();
        contenido = in.readString();
        fotoUri = in.readString();
        latitud = in.readString();
        longitud = in.readString();
    }

    public static final Creator<DiaDiario> CREATOR = new Creator<DiaDiario>() {
        @Override
        public DiaDiario createFromParcel(Parcel in) {
            return new DiaDiario(in);
        }

        @Override
        public DiaDiario[] newArray(int size) {
            return new DiaDiario[size];
        }
    };
}
