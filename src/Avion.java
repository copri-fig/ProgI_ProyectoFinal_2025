public class Avion {
    private static final int FILAS_EJECUTIVO = 3;
    private static final int ASIENTOS_POR_FILA_EJECUTIVO = 4;
    private static final int FILAS_ECONOMICO = 6;
    private static final int ASIENTOS_POR_FILA_ECONOMICO = 6;

    private Asiento[][] ejecutivo;
    private Asiento[][] economico;

    public Avion() {
        this.ejecutivo = new Asiento[FILAS_EJECUTIVO][ASIENTOS_POR_FILA_EJECUTIVO];
        for (int i = 0; i < FILAS_EJECUTIVO; i++) {
            ejecutivo[i][0] = new Asiento(i + 1, 1, "ventana");
            ejecutivo[i][1] = new Asiento(i + 1, 2, "pasillo");
            ejecutivo[i][2] = new Asiento(i + 1, 3, "pasillo");
            ejecutivo[i][3] = new Asiento(i + 1, 4, "ventana");
        }
        this.economico = new Asiento[FILAS_ECONOMICO][ASIENTOS_POR_FILA_ECONOMICO];
        for (int i = 0; i < FILAS_ECONOMICO; i++) {
            economico[i][0] = new Asiento(i + 1, 1, "ventana");
            economico[i][1] = new Asiento(i + 1, 2, "centro");
            economico[i][2] = new Asiento(i + 1, 3, "pasillo");
            economico[i][3] = new Asiento(i + 1, 4, "pasillo");
            economico[i][4] = new Asiento(i + 1, 5, "centro");
            economico[i][5] = new Asiento(i + 1, 6, "ventana");
        }
    }

    public Asiento[][] getEjecutivo() { return ejecutivo; }
    public Asiento[][] getEconomico() { return economico; }

    public Asiento asignarAutomaticamente(boolean esEconomico, String preferencia) {
        Asiento[][] seccion = esEconomico ? economico : ejecutivo;

        for (int i = 0; i < seccion.length; i++) {
            for (int j = 0; j < seccion[i].length; j++) {
                if (!seccion[i][j].estaOcupado() &&
                        seccion[i][j].getTipoUbicacion().equals(preferencia)) {
                    return seccion[i][j];
                }
            }
        }
        for (int i = 0; i < seccion.length; i++) {
            for (int j = 0; j < seccion[i].length; j++) {
                if (!seccion[i][j].estaOcupado()) {
                    return seccion[i][j];
                }
            }
        }
        return null;
    }

    public boolean reservarAsiento(Asiento asiento, Pasajero pasajero) {
        return asiento.reservarPasajero(pasajero);
    }

}
