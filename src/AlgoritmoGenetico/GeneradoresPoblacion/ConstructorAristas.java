package AlgoritmoGenetico.GeneradoresPoblacion;

import Microarray.Aristas;
import Microarray.Gen;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConstructorAristas {

    private double[][] MatrixdeCorrelacion;
    int[][] MatrixdeAdyacencia;
    private List<Gen> listAleatGen;
    private float probabilidades = 0.0F;
    int nodosInternos;

    public ConstructorAristas(double[][] MatrixdeCorrelacion, int[][] MatrixdeAdyacencia, List<Gen> listGen, int nodosInternos, float probabilidades) {
        listAleatGen = new ArrayList<Gen>();
        this.listAleatGen.addAll(listGen);
        this.nodosInternos = nodosInternos;
        this.MatrixdeCorrelacion = MatrixdeCorrelacion;
        this.MatrixdeAdyacencia = MatrixdeAdyacencia;
        this.probabilidades = probabilidades;
    }

    public ConstructorAristas(double[][] MatrixdeCorrelacion, List listGen, float Probabilidades) {
        listAleatGen = new ArrayList<Gen>();
        this.listAleatGen.addAll(listGen);
        this.MatrixdeCorrelacion = MatrixdeCorrelacion;
        this.probabilidades = probabilidades;
    }

    public List<Aristas> crearArista() {
        List<Aristas> listAleatArista = new ArrayList();
        for (int i = listAleatGen.size() - 1; i > (listAleatGen.size() - 1) - nodosInternos; i--) {//nodos internos
            int pos = buscarNodoMasCorelacionado(i);
            if (!existeArista(i, pos, listAleatArista)) {
                listAleatArista.add(generarArista(i, pos));
                setGrados(i, pos);
            }
            int posicion = tieneAristaConInternos(i);
            if (posicion != -1) {
                if (!existeArista(i, posicion, listAleatArista)) {//para no unir los que estan
                    listAleatArista.add(generarArista(i, posicion));
                    setGrados(i, posicion);
                }
            }
        }

        for (int i = 0; i < listAleatGen.size() - nodosInternos; i++) {//nodos externos
            List<Gen> nodosAdy = buscarNodosAdyacentes(listAleatGen.get(i).getPosicion());
            if (!nodosAdy.isEmpty()) {//si tiene una conexion con otro los uno si no se lo agrego a uno de los internos
                for (int j = 0; j < nodosAdy.size(); j++) {
                    if (!existeArista(i, nodosAdy.get(j).getPosicion(), listAleatArista) && nodosAdy.get(j).getGrado() != 0) {
                        listAleatArista.add(generarArista(i, nodosAdy.get(j).getPosicion()));
                        setGrados(i, nodosAdy.get(j).getPosicion());
                    } else {
                        int posicion = buscarNodoMasCorelacionado(i);
                        if (!existeArista(i, posicion, listAleatArista)) {
                            listAleatArista.add(generarArista(i, posicion));
                            setGrados(i, posicion);
                        }
                    }
                }
            } else {
                int posicion = buscarNodoMasCorelacionado(i);
                if (!existeArista(i, posicion, listAleatArista)) {
                    listAleatArista.add(generarArista(i, posicion));
                    setGrados(i, posicion);
                }
            }
        }
        return listAleatArista;
    }

    private Aristas generarArista(int numeroaleator1, int numeroaleator2) {
        Gen miGen1 = new Gen(this.listAleatGen.get(numeroaleator1).getTipo(), this.listAleatGen.get(numeroaleator1).getPosicion());
        Gen miGen2 = new Gen(this.listAleatGen.get(numeroaleator2).getTipo(), this.listAleatGen.get(numeroaleator2).getPosicion());
        Aristas miArista = new Aristas(miGen1, miGen2);

        int p1 = miGen1.getPosicion().intValue();
        int p2 = miGen2.getPosicion().intValue();
        if (p1 < p2) {
            miArista.setPesos(this.MatrixdeCorrelacion[p1][p2]);
        } else {
            miArista.setPesos(this.MatrixdeCorrelacion[p2][p1]);
        }

        return miArista;
    }

    private boolean existeArista(int i, int j, List<Aristas> listAleatArista) {
        String gen1 = this.listAleatGen.get(i).getTipo();
        String gen2 = this.listAleatGen.get(j).getTipo();
        if (!listAleatArista.isEmpty()) {
            for (int x = 0; x < listAleatArista.size(); x++) {
                if ((listAleatArista.get(x).getGen1().getTipo().equals(gen1) && listAleatArista.get(x).getGen2().getTipo().equals(gen2))
                        || (listAleatArista.get(x).getGen1().getTipo().equals(gen2) && listAleatArista.get(x).getGen2().getTipo().equals(gen1))) {
                    return true;
                }
            }
        }
        return false;
    }

    private List<Gen> buscarNodosAdyacentes(int posicion) {
        List<Gen> nodosAdy = new ArrayList<Gen>();
        for (int i = 0; i < listAleatGen.size(); i++) {
            if (MatrixdeAdyacencia[posicion][listAleatGen.get(i).getPosicion()] == 1) {
                nodosAdy.add(new Gen(listAleatGen.get(i).getTipo(), i));
            }
        }
        return nodosAdy;
    }

    private void setGrados(int i, int j) {
        listAleatGen.get(i).addGrado();
        listAleatGen.get(j).addGrado();
    }

    private boolean buscarSiGenEsAdy(int i, int j) {
        if (MatrixdeAdyacencia[i][j] == 1) {
            return true;
        }
        return false;
    }

    private int buscarNodoMasCorelacionado(int posicion) {
        int posicionRet = 0;//si hay un solo nodo central lo devuelve
        double correlacion = -2;
        ArrayList<Gen> nodosConGradoDist0 = new ArrayList<Gen>();
        for (int i = listAleatGen.size() - 1; i > (listAleatGen.size() - 1) - nodosInternos; i--) {
            if (listAleatGen.get(i).getGrado() != 0 && i != posicion) {
                nodosConGradoDist0.add(listAleatGen.get(i));
            }
        }
        if (nodosConGradoDist0.isEmpty()) {
            for (int i = listAleatGen.size() - 1; i > (listAleatGen.size() - 1) - nodosInternos; i--) {
                if (MatrixdeCorrelacion[listAleatGen.get(posicion).getPosicion()][listAleatGen.get(i).getPosicion()] >= correlacion && i != posicion) {
                    correlacion = MatrixdeCorrelacion[listAleatGen.get(posicion).getPosicion()][listAleatGen.get(i).getPosicion()];
                    posicionRet = i;
                }
            }
        } else {
            for (int i = 0; i < nodosConGradoDist0.size(); i++) {
                if (MatrixdeCorrelacion[listAleatGen.get(posicion).getPosicion()][nodosConGradoDist0.get(i).getPosicion()] >= correlacion) {
                    correlacion = MatrixdeCorrelacion[listAleatGen.get(posicion).getPosicion()][nodosConGradoDist0.get(i).getPosicion()];
                    for (int j = listAleatGen.size() - 1; j > (listAleatGen.size() - 1) - nodosInternos; j--) {
                        if (nodosConGradoDist0.get(i).getTipo() == listAleatGen.get(j).getTipo()) {
                            posicionRet = j;
                        }
                    }
                }
            }
        }

        return posicionRet;
    }

    private int buscarNodoMasCorelacionado2(int posicion) {
        int posicionRet = -1;
        double correlacion = -2;
        if (posicion == listAleatGen.size() - 1) {//solo el primero
            for (int i = listAleatGen.size() - 2; i > (listAleatGen.size() - 1) - nodosInternos; i--) {
                if (MatrixdeCorrelacion[listAleatGen.get(posicion).getPosicion()][listAleatGen.get(i).getPosicion()] >= correlacion) {
                    correlacion = MatrixdeCorrelacion[listAleatGen.get(posicion).getPosicion()][listAleatGen.get(i).getPosicion()];
                    posicionRet = i;
                }
            }
        } else {
            for (int i = listAleatGen.size() - 1; i > (listAleatGen.size() - 1) - nodosInternos; i--) {
                if (listAleatGen.get(i).getGrado() != 0 && MatrixdeCorrelacion[listAleatGen.get(posicion).getPosicion()][listAleatGen.get(i).getPosicion()] >= correlacion) {
                    correlacion = MatrixdeCorrelacion[listAleatGen.get(posicion).getPosicion()][listAleatGen.get(i).getPosicion()];
                    posicionRet = i;
                }
            }
        }

        return posicionRet;
    }

    private int tieneAristaConInternos(int i) {
        int posicion = -1;
        for (int j = listAleatGen.size() - 1; j > (listAleatGen.size() - 1) - nodosInternos; j--) {
            if (j != i && MatrixdeAdyacencia[listAleatGen.get(i).getPosicion()][listAleatGen.get(j).getPosicion()] == 1) {
                return j;
            }
        }
        return posicion;
    }
}
