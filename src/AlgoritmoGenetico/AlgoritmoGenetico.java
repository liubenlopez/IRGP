package AlgoritmoGenetico;

import AlgoritmoGenetico.GeneradoresPoblacion.Aleatorio;
import Microarray.Aristas;
import AlgoritmoGenetico.GeneradoresPoblacion.ConstructorAristas;
import Microarray.Gen;
import Microarray.RedGenes;
import AlgoritmoGenetico.Recorridos.Grafo_ND_Ponderado;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AlgoritmoGenetico {

    private double[][] MatrixdeCorrelacion;
    private int[][] matrizAdyacencia;
    private String[] listGenes;
    private List<RedGenes> poblacionRG = new ArrayList();
    private float Probabilidades;
    private int[] listSolapamiento;
    private List<List<RedGenes>> generaciones = new ArrayList();
    private int Poblacion;
    private int NumGeneraciones;
    private int cantGenes;
    //private String GenPoblacion; Aleatorio
    private String Cruce;
    private String Seleccion;
    private String Tmutadores;

    public AlgoritmoGenetico(double[][] MatrixdeCorrelacion, int[][] matrizAdyacencia, String[] listGenes, String seleccion, String cruce, String tMutadores, int poblacion, int numGeneraciones, int catGenes) {
        this.MatrixdeCorrelacion = MatrixdeCorrelacion;
        this.matrizAdyacencia = matrizAdyacencia;
        this.listGenes = listGenes;
        this.Poblacion = poblacion;
        this.NumGeneraciones = numGeneraciones;
        this.cantGenes = catGenes;
        this.Probabilidades = (Float.valueOf(cantGenes).floatValue() * 100.0F);
        this.listSolapamiento = new int[listGenes.length];
        this.Seleccion = seleccion;
        this.Cruce = cruce;
        this.Tmutadores = tMutadores;
    }

    public List<RedGenes> correAlgGenetico() {
        int t = 1;
        this.poblacionRG = inicializarPoblacion(this.cantGenes, this.Poblacion);
        evaluar(this.poblacionRG);
        this.generaciones.add(this.poblacionRG);
        while (t != this.NumGeneraciones) {
            List hijosRG = cruzarP();
            if (cuandoMutuar()) {
                mutaciónP(hijosRG);
            }
            evaluar(hijosRG);
            this.poblacionRG = Seleccion(this.poblacionRG, hijosRG);
            this.generaciones.add(this.poblacionRG);
            t++;
        }
        return this.poblacionRG;
    }

    private List<RedGenes> inicializarPoblacion(int maxG, int maxP) {
        List listRG = new ArrayList();
        Aleatorio aleatorio = new Aleatorio(this.MatrixdeCorrelacion, this.matrizAdyacencia, this.listGenes, maxG, this.Probabilidades);
        for (int i = 0; i < maxP - 1; i++) {
            listRG.add(aleatorio.getNuevaRed());
        }
        return listRG;
    }

    private List<RedGenes> Seleccion(List<RedGenes> poblacionRG, List<RedGenes> hijosRG) {
        List poblacionSeleccionada = new ArrayList();
        iSelector selector = inicializarSelectores(this.Seleccion);
        poblacionSeleccionada = selector.getPoblacionSeleccionada(poblacionRG, hijosRG);
        return poblacionSeleccionada;
    }

    private List<RedGenes> cruzarP() {
        List hijosRG = new ArrayList();
        for (int i = 0; i < this.poblacionRG.size(); i++) {
            for (int j = i + 1; j < this.poblacionRG.size(); j++) {
                iCruce cruce = inicializarCruzadores(this.Cruce);
                List listResul;
                RedGenes nuevaRedGenes;
                //agregando al cromosoma 1
                listResul = cruce.getListaCruzada(((RedGenes) this.poblacionRG.get(i)).getListGenes(), ((RedGenes) this.poblacionRG.get(j)).getListGenes());
                nuevaRedGenes = new RedGenes();
                nuevaRedGenes.setListGenes(listResul);
                ConstructorAristas constructorAristas = new ConstructorAristas(this.MatrixdeCorrelacion, this.matrizAdyacencia, listResul, this.poblacionRG.get(i).getNodosInternos(), this.Probabilidades);
                nuevaRedGenes.setListArista(constructorAristas.crearArista());
                nuevaRedGenes.setNodosInternos(this.poblacionRG.get(i).getNodosInternos());
                hijosRG.add(nuevaRedGenes);
                //agregando al cromosoma 2
                listResul = cruce.getListaCruzada(((RedGenes) this.poblacionRG.get(j)).getListGenes(), ((RedGenes) this.poblacionRG.get(i)).getListGenes());
                nuevaRedGenes.setListGenes(listResul);
                constructorAristas = new ConstructorAristas(this.MatrixdeCorrelacion, this.matrizAdyacencia, listResul, this.poblacionRG.get(j).getNodosInternos(), this.Probabilidades);
                nuevaRedGenes.setListArista(constructorAristas.crearArista());
                nuevaRedGenes.setNodosInternos(this.poblacionRG.get(j).getNodosInternos());
                hijosRG.add(nuevaRedGenes);
            }
        }
        return hijosRG;
    }

    private void mutaciónP(List<RedGenes> hijosRG) {
        Random rnd = new Random();
        int numAleat = rnd.nextInt(hijosRG.size());
        RedGenes redGenes = hijosRG.get(numAleat);
        List<Gen> listGenes = redGenes.getListGenes();
        List<Aristas> listAristas = redGenes.getListArista();
        int posicionGen = -1;
        int posicionArista = -1;
        double menorCorrelacio = Double.MAX_VALUE;
        for (int i = 0; i < listGenes.size(); i++) {
            if (listGenes.get(i).getGrado() == 1) {
                for (int j = 0; j < listAristas.size(); j++) {
                    if (listAristas.get(j).getGen1().equals(listGenes.get(i)) || listAristas.get(j).getGen2().equals(listGenes.get(i))) {
                        if (menorCorrelacio > MatrixdeCorrelacion[listAristas.get(j).getGen1().getPosicion()][listAristas.get(j).getGen2().getPosicion()]) {
                            menorCorrelacio = MatrixdeCorrelacion[listAristas.get(j).getGen1().getPosicion()][listAristas.get(j).getGen2().getPosicion()];
                            posicionGen = i;
                            posicionArista = j;
                        }
                    }
                }
            }
        }
        if (posicionArista != -1 && posicionGen != -1) {
            listAristas.remove(posicionArista);
            iMutadores TipoMutacion = inicializarMutaciones(this.Tmutadores);
            TipoMutacion.mutar(((RedGenes) hijosRG.get(numAleat)).getListGenes(), this.listGenes, posicionGen);
        }
    }

    private void evaluar(List<RedGenes> poblacion) {
        float corrMedia = 0.0F;
        double fitness = 0.0D;
        for (RedGenes rg : poblacion) {
            //correlacion
            for (Aristas a : rg.getListArista()) {
                corrMedia = (float) (corrMedia + a.getPesos());
            }
            int numAristas = rg.getListArista().size();
            corrMedia /= numAristas;

            //moda
            ArrayList<Grado> listaModa = new ArrayList<Grado>();
            for (Gen gen : rg.getListGenes()) {
                boolean agregar = true;
                for (Grado grado : listaModa) {
                    if (grado.getIdGrado() == gen.getGrado()) {
                        agregar = false;
                        grado.addRepeticiones();
                    }
                }
                if (agregar) {
                    listaModa.add(new Grado(gen.getGrado()));
                }
            }
            int gradoModa = listaModa.get(0).getRepeticiones();
            for (Grado grado : listaModa) {
                if (grado.getRepeticiones() > gradoModa) {
                    gradoModa = grado.getRepeticiones();
                }
            }
            //camino medio
            ArrayList<Float> caminos = new ArrayList<Float>();
            Grafo_ND_Ponderado gndp = new Grafo_ND_Ponderado((ArrayList<Gen>) rg.getListGenes(), (ArrayList<Aristas>) rg.getListArista());
            gndp.ejecutarDijkstra(rg.getListGenes().get(0).getTipo());
            for (int i = 0; i < rg.getListGenes().size(); i++) {
                caminos.add(gndp.distanciaAlNodo(rg.getListGenes().get(i).getTipo()));
            }
            /*for (int i = 0; i < rg.getListGenes().size(); i++) { //variante de todos con todos mas pesada
            for (int j = i + 1; j < rg.getListGenes().size(); j++) {
            caminos.add(gndp.caminoEntreDos(rg.getListGenes().get(i).getTipo(), rg.getListGenes().get(j).getTipo()));
            }
            }*/
            float caminoMedio = 0;
            for (int i = 0; i < caminos.size(); i++) {
                caminoMedio += caminos.get(i);
            }
            caminoMedio /= caminos.size();

            fitness = 0.5f * corrMedia + 0.25f / gradoModa + 0.25f / caminoMedio;
            rg.setFitness(fitness);
        }
    }

    private iCruce inicializarCruzadores(String clase) {
        iCruce myAlgorithm = null;
        try {
            Class newClass = Class.forName("AlgoritmoGenetico.OperadoresCruzamiento." + clase);
            myAlgorithm = (iCruce) newClass.newInstance();
        } catch (ClassNotFoundException ex) {
        } catch (InstantiationException ex) {
        } catch (IllegalAccessException ex) {
        }
        return myAlgorithm;
    }

    private iSelector inicializarSelectores(String clase) {
        iSelector myAlgorithm = null;
        try {
            Class newClass = Class.forName("AlgoritmoGenetico.OperadoresSeleccion." + clase);
            myAlgorithm = (iSelector) newClass.newInstance();
        } catch (ClassNotFoundException ex) {
        } catch (InstantiationException ex) {
        } catch (IllegalAccessException ex) {
        }
        return myAlgorithm;
    }

    private iMutadores inicializarMutaciones(String clase) {
        iMutadores myAlgorithm = null;
        try {
            Class newClass = Class.forName("AlgoritmoGenetico.Mutaciones." + clase);
            myAlgorithm = (iMutadores) newClass.newInstance();
        } catch (ClassNotFoundException ex) {
        } catch (InstantiationException ex) {
        } catch (IllegalAccessException ex) {
        }
        return myAlgorithm;
    }

    public List<List<RedGenes>> getGeneraciones() {
        return this.generaciones;
    }

    private boolean cuandoMutuar() {
        boolean result = false;

        Random rnd = new Random();

        int numAleat = rnd.nextInt(100);

        if (numAleat < 10) {
            result = true;
        }
        return result;
    }
}
