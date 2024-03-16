/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package AlgoritmoGenetico.Recorridos;

/**
 *
 * @author Liuben
 */
public class Vertice {
    private Object vertice;
    private boolean siVisitado;
    private float peso;
    private Object modificadoPor;

    public Vertice(Object vertice) {
        this.vertice = vertice;
        this.siVisitado = false;
        peso = Float.MAX_VALUE;
        modificadoPor = null;
    }

    public boolean siVisitado() {
        return siVisitado;
    }

    public void setSiVisitado(boolean siVisitado) {
        this.siVisitado = siVisitado;
    }

    public Object getVertice() {
        return vertice;
    }

    public void setVertice(Object vertice) {
        this.vertice = vertice;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public Object getModificadoPor() {
        return modificadoPor;
    }

    public void setModificadoPor(Object modificadoPor) {
        this.modificadoPor = modificadoPor;
    }

    



}
