/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package AlgoritmoGenetico.Recorridos;

/**
 *
 * @author yanirys
 */
public class Celda {
    private boolean hay_arista;
    private int peso;

    public Celda()
    {   //como comvenio si no hay arista el peso es 0
        hay_arista = false;
        peso = 0;
    }

    public Celda(boolean ha, int p)
    {
        this.hay_arista = ha;
        this.peso = p;
    }
    
    public boolean get_hay_arista()
    {
        return hay_arista;
    }

    public void set_hay_arista(boolean ha)
    {
        hay_arista = ha;
    }

    public int get_peso()
    {
        return peso;
    }

    public void set_peso(int p)
    {
        peso = p;
    }

}
