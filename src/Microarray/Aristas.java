package Microarray;

public class Aristas {

    private Gen gen1;
    private Gen gen2;
    private double pesos;

    public Aristas() {
    }

    public Aristas(Gen gen1, Gen gen2) {
        this.gen1 = gen1;
        this.gen2 = gen2;
    }

    public Gen getGen1() {
        return this.gen1;
    }

    public void setGen1(Gen gen1) {
        this.gen1 = gen1;
    }

    public Gen getGen2() {
        return this.gen2;
    }

    public void setGen2(Gen gen2) {
        this.gen2 = gen2;
    }

    public double getPesos() {
        return this.pesos;
    }

    public float getFitness(int valor1, int valor2) {
        float fitness = 0.0F;

        return fitness;
    }

    public void setPesos(double pesos) {
        this.pesos = pesos;
    }
}
