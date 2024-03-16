package AlgoritmoGenetico.Recorridos;

import Microarray.Aristas;
import Microarray.Gen;
import java.lang.Object;
import java.util.ArrayList;

public class Grafo_ND_Ponderado {

    ArrayList<Vertice> vertices; //vertices del grafo
    Celda[][] aristas; //aristas y pesos asociados

    public Grafo_ND_Ponderado(ArrayList<Gen> genes, ArrayList<Aristas> aristasL) {
        vertices = new ArrayList<Vertice>();
        for (Gen gen : genes) {
            vertices.add(new Vertice(gen.getTipo()));
        }
        aristas = new Celda[vertices.size()][vertices.size()];
        for (int i = 0; i < aristas.length; i++) {
            for (int j = 0; j < aristas.length; j++) {
                aristas[i][j] = new Celda();
            }
        }
        for (int i = 0; i < aristasL.size(); i++) {
            int x = buscarVertice(aristasL.get(i).getGen1().getTipo());
            int y = buscarVertice(aristasL.get(i).getGen2().getTipo());
            aristas[x][y] = new Celda(true, 1);
            aristas[y][x] = new Celda(true, 1);
        }
        /*vertices = new ArrayList<Vertice>();
         vertices.add(new Vertice("1"));
         vertices.add(new Vertice("2"));
         vertices.add(new Vertice("3"));
         vertices.add(new Vertice("4"));
         vertices.add(new Vertice("5"));
        
         aristas = new Celda[5][5];
         aristas[0][0] = new Celda();
         aristas[0][1] = new Celda(true, 1);
         aristas[0][2] = new Celda(true, 1);
         aristas[0][3] = new Celda(true, 1);
         aristas[0][4] = new Celda();
         aristas[1][0] = new Celda(true, 1);
         aristas[1][1] = new Celda();
         aristas[1][2] = new Celda();
         aristas[1][3] = new Celda();
         aristas[1][4] = new Celda();
         aristas[2][0] = new Celda(true, 1);
         aristas[2][1] = new Celda();
         aristas[2][2] = new Celda();
         aristas[2][3] = new Celda();
         aristas[2][4] = new Celda();
         aristas[3][0] = new Celda(true, 1);
         aristas[3][1] = new Celda();
         aristas[3][2] = new Celda();
         aristas[3][3] = new Celda();
         aristas[3][4] = new Celda(true, 1);
         aristas[4][0] = new Celda();
         aristas[4][1] = new Celda();
         aristas[4][2] = new Celda();
         aristas[4][3] = new Celda(true, 1);
         aristas[4][4] = new Celda();*/
    }
   
    public int buscarVertice(Object V) {
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i).getVertice().equals(V)) {
                return i;
            }
        }
        return -1;
    }

    public ArrayList<Vertice> todosAdyacentes(Object V) {
        ArrayList<Vertice> lista = new ArrayList<Vertice>();
        int n = -1;
        n = buscarVertice(V);
        if (n != -1) {
            for (int i = 0; i < vertices.size(); i++) {
                if (aristas[n][i].get_hay_arista() == true) {
                    lista.add(vertices.get(i));
                }
            }
        }
        return lista;
    }

    public void ponerValoresOriginales() {
        for (int i = 0; i < vertices.size(); i++) {//el peso de todos los vertices a Float.Max para poder recorrerlo otra vez
            vertices.get(i).setPeso(Float.MAX_VALUE);
            vertices.get(i).setSiVisitado(false);
            vertices.get(i).setModificadoPor(null);
        }
    }

    public ArrayList<Vertice> Dijkstra(Vertice V) {
        int vPartida = buscarVertice(V.getVertice());//busco el indice donde esta el vertice de partida
        vertices.get(vPartida).setPeso(0);//le pongo peso 0 al vertice de partida
        vertices.get(vPartida).setModificadoPor("*");//lo marco con * porque es de donde parto
        V.setPeso(0);//al que paso le tengo que poner peso 0 para la 1 suma
        V.setModificadoPor("*");
        marcarComoNoVisitados(vertices);//los marco todos como no visitados
        return buscarCaminoMasCorto(V);
    }

    private void marcarComoNoVisitados(ArrayList<Vertice> vertices) {
        for (int i = 0; i < vertices.size(); i++) {
            vertices.get(i).setSiVisitado(false);
        }
    }

    private ArrayList<Vertice> buscarCaminoMasCorto(Vertice V) {
        Vertice actual = V;
        Vertice Vi = null;
        while (!todosVisitados()) {
            ArrayList<Vertice> adyacentes = todosAdyacentes(actual.getVertice());//cojo los adyacentes al nodo
            if (adyacentes.isEmpty()) {
                return vertices;
            }
            for (int i = 0; i < adyacentes.size(); i++) { //para cada ady pruebo si el peso el peso de Vi es mayor que actual.getPeso() + getPesoArista(actual, Vi)
                Vi = adyacentes.get(i);
                float aux = actual.getPeso() + getPesoArista(actual, Vi);//uso un aux para hacer la busqueda una sola vez
                if (Vi.getPeso() > aux && Vi.siVisitado() == false) {
                    int n = buscarVertice(Vi.getVertice());//busco el indice del vertice a modificar
                    vertices.get(n).setPeso(aux);//le modifico el peso al vertice
                    vertices.get(n).setModificadoPor(actual.getVertice());//le pongo por quien se modifico
                }
            }
            vertices.get(buscarVertice(actual.getVertice())).setSiVisitado(true);//visito el vertice actual
            actual = verticePesoMinimo();//cojo el minimo de los no visitados
        }
        return vertices;
    }

    private boolean todosVisitados() {
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i).siVisitado() == false) {
                return false;
            }
        }
        return true;
    }

    private float getPesoArista(Vertice V, Vertice Vi) {
        int i = buscarVertice(V.getVertice());
        int j = buscarVertice(Vi.getVertice());
        return aristas[i][j].get_peso();
    }

    public Object getVertices() {
        return vertices;
    }

    private Vertice verticePesoMinimo() {
        Vertice v = new Vertice(null);//el peso de este nuevo es el max float
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i).getPeso() < v.getPeso() && !vertices.get(i).siVisitado()) {
                v = vertices.get(i);
            }
        }
        return v;
    }

    public float caminoEntreDos(Object vertice, Object vertice0) {
        Vertice V1 = new Vertice(vertice0);//vert de llegada
        ArrayList<Vertice> aux = Dijkstra(V1);
        int i = buscarVertice(vertice);//indice del de salida
        float pesoDelCamino = aux.get(i).getPeso();//como lo hago alrevez cojo el peso del camino
        ponerValoresOriginales();//el peso de todos los vertices a Float.Max para poder recorrerlo otra vez
        return pesoDelCamino;
    }

    ArrayList<Vertice> aux;
    public void ejecutarDijkstra(Object vertice0){
        Vertice V1 = new Vertice(vertice0);//vert de llegada
        aux = Dijkstra(V1);
    }

    public float distanciaAlNodo(Object vertice) {
        int i = buscarVertice(vertice);//indice del de salida
        float pesoDelCamino = aux.get(i).getPeso();//como lo hago alrevez cojo el peso del camino
        return pesoDelCamino;
    }
}
