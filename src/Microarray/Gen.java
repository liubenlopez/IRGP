package Microarray;

public class Gen {

    private String tipo;
    private Integer posicion;
    private Integer grado;

    public Integer getGrado() {
        return grado;
    }

    public void setGrado(Integer grado) {
        this.grado = grado;
    }
    
    public void addGrado() {
        this.grado++;
    }

    public Gen() {
    }

    public Gen(String tipo, Integer posicion) {
        this.tipo = tipo;
        this.posicion = posicion;
        this.grado = 0;
    }
    

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getPosicion() {
        return this.posicion;
    }

    public void setPosicion(Integer posicion) {
        this.posicion = posicion;
    }
}
