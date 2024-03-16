package AlgoritmoGenetico;

class Grado {
    private int idGrado;
    private int repeticiones;

    public Grado() {
    }

    public Grado(int idGrado) {
        this.idGrado = idGrado;
        this.repeticiones = 1;
    }
   
    public int getIdGrado() {
        return idGrado;
    }

    public void setIdGrado(int idGrado) {
        this.idGrado = idGrado;
    }

    public int getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }
    
    public void addRepeticiones() {
        this.repeticiones ++;
    }
    
}
