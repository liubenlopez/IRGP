package Microarray;

import Microarray.Gen;
import Microarray.Aristas;
import java.util.ArrayList;
import java.util.List;

public class RedGenes {

    private List<Gen> listGenes = new ArrayList();
    private List<Aristas> listArista = new ArrayList();
    private double fitness = 0.0D;
    private int nodosInternos = 0;

    public int getNodosInternos() {
        return nodosInternos;
    }

    public void setNodosInternos(int nodosInternos) {
        this.nodosInternos = nodosInternos;
    }

    public List<Aristas> getListArista() {
        return this.listArista;
    }

    public void setListArista(List<Aristas> listArista) {
        this.listArista = listArista;
    }

    public void agregarArista(Aristas arista) {
        this.listArista.add(arista);
    }

    public void agregarArista(Gen g1, Gen g2, float peso) {
        Aristas newArista = new Aristas();
        newArista.setGen1(g1);
        newArista.setGen2(g2);
        newArista.setPesos(peso);
        this.listArista.add(newArista);
    }

    public void agregarGen(String nombreGen, int Posicion) {
        Gen newGen = new Gen();
        newGen.setTipo(nombreGen);
        newGen.setPosicion(Integer.valueOf(Posicion));
        this.listGenes.add(newGen);
    }

    public List<Gen> getListGenes() {
        return this.listGenes;
    }

    public void setListGenes(List<Gen> listGenes) {
        this.listGenes = listGenes;
    }

    public double getFitness() {
        return this.fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
}
