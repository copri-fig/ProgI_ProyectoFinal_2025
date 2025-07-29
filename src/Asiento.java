public class Asiento {
    private int fila;
    private int columna;
    private boolean ocupado;
    private Pasajero pasajero;
    private String tipoUbicacion;

    public Asiento(int fila, int columna, String tipoUbicacion) {
        this.fila = fila;
        this.columna = columna;
        this.ocupado = ocupado;
        this.tipoUbicacion = tipoUbicacion;
        this.ocupado = false;
        this.pasajero = null;
    }

    public boolean estaOcupado() {
        return ocupado;
    }

    public boolean reservarPasajero(Pasajero pasajero) {
        if(!ocupado) {
            this.ocupado = true;
            this.pasajero = pasajero;
            return true;
        }
        return false;
    }

    public void liberarAsiento(){
        this.ocupado = false;
        this.pasajero = null;
    }

    public String imprimirBoleto(String clase){
        if(pasajero != null){
            return pasajero.toString()+
                    "\nClase: " + clase +
                    "\nAsiento: " + fila + "-" + columna + "(" + tipoUbicacion + ")";
        }
        return "";
    }

    public String getTipoUbicacion() {
        return tipoUbicacion;
    }

    public void setTipoUbicacion(String tipoUbicacion) {
        this.tipoUbicacion = tipoUbicacion;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }
}
