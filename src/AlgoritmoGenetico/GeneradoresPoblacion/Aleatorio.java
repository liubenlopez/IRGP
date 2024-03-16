package AlgoritmoGenetico.GeneradoresPoblacion;

import Microarray.Aristas;
import Microarray.Gen;
import Microarray.RedGenes;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Aleatorio {

    double[][] MatrixdeCorrelacion;
    int[][] MatrixdeAdyacencia;
    String[] listGenes;
    int maxG;
    float Probabilidades;
    int nodosInternos; //nodos con mayor grado

    public Aleatorio(double[][] MatrixdeCorrelacion, int[][] MatrixdeAdyacencia, String[] listGenes, int maxG, float Probabilidades) {
        this.MatrixdeCorrelacion = MatrixdeCorrelacion;
        this.MatrixdeAdyacencia = MatrixdeAdyacencia;
        this.listGenes = listGenes;
        this.maxG = maxG;
        this.Probabilidades = Probabilidades;
    }

    public RedGenes getNuevaRed() {
        RedGenes nuevaRedGenes = new RedGenes();
        int iteraciones = 0;
        do {
            iteraciones++;
            nuevaRedGenes.setListGenes(generarNumeroAleatGenes());
        } while (evaluarListaGenes(nuevaRedGenes.getListGenes()) && iteraciones <= 10);//evaluar que los genes seleccionados cumplan con la libre escala en la matriz de ady
        nuevaRedGenes.setListArista(crearlistAleatArista(nuevaRedGenes.getListGenes()));
        nuevaRedGenes.setNodosInternos(nodosInternos);

        return nuevaRedGenes;
    }

    public List<Gen> generarNumeroAleatGenes() {
        List listAleatGen1 = new ArrayList();
        Random rnd = new Random();
        int newCantGEN;

        do {
            rnd = new Random();
            newCantGEN = rnd.nextInt(this.maxG + 1);
        } while (newCantGEN < 3);

        List<Gen> listAleatGenesTemp = new ArrayList();
        for (int i = 0; i < listGenes.length; i++) {
            listAleatGenesTemp.add(new Gen(listGenes[i], i));
        }

        while (listAleatGen1.size() < newCantGEN && !listAleatGenesTemp.isEmpty()) {
            int numAleator = 0;
            rnd = new Random();
            numAleator = rnd.nextInt(listAleatGenesTemp.size());
            listAleatGen1.add(new Gen(listAleatGenesTemp.get(numAleator).getTipo(), listAleatGenesTemp.get(numAleator).getPosicion()));
            listAleatGenesTemp.remove(numAleator);
        }

        return listAleatGen1;
    }

    public List<Aristas> crearlistAleatArista(List<Gen> listAleatGen) {
        ConstructorAristas constructorAristas = new ConstructorAristas(this.MatrixdeCorrelacion, this.MatrixdeAdyacencia, listAleatGen, nodosInternos, this.Probabilidades);
        return constructorAristas.crearArista();
    }

    private boolean evaluarListaGenes(List<Gen> listGenes) {//buscar la libre escala
        int[] cantGrados = new int[listGenes.size()];
        for (int i = 0; i < listGenes.size(); i++) {
            int gradoTemp = 0;
            for (int j = 0; j < listGenes.size(); j++) {
                gradoTemp += MatrixdeAdyacencia[listGenes.get(i).getPosicion()][listGenes.get(j).getPosicion()];
            }
            cantGrados[i] = gradoTemp;
        }
        ordenarQuicksort(cantGrados, 0, cantGrados.length - 1, listGenes);//para ver la distribucion de los grados de los nodos

        int diferencia = Integer.MIN_VALUE;
        int porciento = (cantGrados.length - 1) / 10;
        ArrayList<Integer> cantRepeticiones = new ArrayList<Integer>();
        cantRepeticiones.add(1);
        for (int i = cantGrados.length - 1; i > 0; i--) {//las diferencias mas grandes tienen que estar entre los de mayor grado
            if (diferencia < cantGrados[i] - cantGrados[i - 1]) {
                diferencia = cantGrados[i] - cantGrados[i - 1];
                if (cantGrados.length - 1 - i > porciento) {
                    return true;
                }
            }
            if (cantGrados[i] != cantGrados[i - 1]) {
                cantRepeticiones.add(1);
            } else {
                cantRepeticiones.set(cantRepeticiones.size() - 1, cantRepeticiones.get(cantRepeticiones.size() - 1) + 1);
            }
        }

        int posicion = 0;
        int repeticiones = cantRepeticiones.get(0);
        for (int i = 0; i < cantRepeticiones.size(); i++) {//la menor cantidad de repeticiones tiene que estar en los de mayor grado
            if (repeticiones <= cantRepeticiones.get(i)) {
                repeticiones = cantRepeticiones.get(i);
            } else {
                return true;
            }
            if (posicion + cantRepeticiones.get(i) < porciento || posicion < 1) {
                posicion += cantRepeticiones.get(i);
            }
        }

        nodosInternos = posicion;
        return false;
    }

    private void ordenarQuicksort(int[] vector, int primero, int ultimo, List<Gen> listGenes) {
        int i = primero, j = ultimo;
        int pivote = vector[(primero + ultimo) / 2];
        int auxiliar;

        do {
            while (vector[i] < pivote) {
                i++;
            }
            while (vector[j] > pivote) {
                j--;
            }

            if (i <= j) {
                auxiliar = vector[j];
                vector[j] = vector[i];
                vector[i] = auxiliar;
                Gen gen = listGenes.get(j);
                listGenes.set(j, listGenes.get(i));
                listGenes.set(i, gen);
                i++;
                j--;
            }

        } while (i <= j);

        if (primero < j) {
            ordenarQuicksort(vector, primero, j, listGenes);
        }
        if (ultimo > i) {
            ordenarQuicksort(vector, i, ultimo, listGenes);
        }
    }

}
