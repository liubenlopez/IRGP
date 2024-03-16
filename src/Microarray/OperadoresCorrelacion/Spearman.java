/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Microarray.OperadoresCorrelacion;

import Microarray.iOperadoresCorrelacion;

/**
 *
 * @author Liuben
 */
public class Spearman implements iOperadoresCorrelacion {

    private float[][] datos;
    private double[][] MatrizCorr;

    public double[][] getCorrelationMatrix() {
        int c = this.datos[0].length;
        this.MatrizCorr = new double[c][c];

        for (int i = 0; i < c - 1; i++) {
            for (int j = i + 1; j < c; j++) {
                this.MatrizCorr[i][j] = CalcularSpearman(i, j);
            }
        }
        return this.MatrizCorr;
    }

    public void setData(float[][] paramArrayOfFloat) {
        this.datos = paramArrayOfFloat;
    }

    private double CalcularSpearman(int i, int j) {
        int n = this.datos.length;
        float[] datosI = new float[n];
        float[] posicionI = new float[n];
        float[] datosJ = new float[n];
        float[] datosJAux = new float[n];
        float[] emptyAux = new float[n];
        float[] posicionJ = new float[n];
        for (int f = 0; f < n; f++) {
            datosI[f] = this.datos[f][i];
            datosJ[f] = this.datos[f][j];
        }
        System.arraycopy(datosJ, 0, datosJAux, 0, datosJ.length);
        ordenarQuicksort(datosI, 0, datosI.length - 1, datosJ);//ordeno los dos en func del primero
        ordenarQuicksort(datosJAux, 0, datosJAux.length - 1, emptyAux);

        posicionar(datosI, posicionI, datosJ, posicionJ, datosJAux);

        float suma = 0.0F;
        for (int f = 0; f < n; f++) {
            suma += Math.pow(posicionI[f] - posicionJ[f], 2);
        }
        double dividendo = 6 * suma;
        double divisor = n * (Math.pow(n, 2) - 1);
        return 1 - (dividendo / divisor);
    }

    void ordenarQuicksort(float[] vector, int primero, int ultimo, float[] vector2) {
        int i = primero, j = ultimo;
        float pivote = vector[(primero + ultimo) / 2];
        float auxiliar;

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
                auxiliar = vector2[j];
                vector2[j] = vector2[i];
                vector2[i] = auxiliar;
                i++;
                j--;
            }

        } while (i <= j);

        if (primero < j) {
            ordenarQuicksort(vector, primero, j, vector2);
        }
        if (ultimo > i) {
            ordenarQuicksort(vector, i, ultimo, vector2);
        }
    }

    private void posicionar(float[] datosI, float[] posicionI, float[] datosJ, float[] posicionJ, float[] datosJAux) {
        posicionar(datosI, posicionI);
        posicionar(datosJAux, posicionJ);

        for (int i = 0; i < datosJ.length; i++) {
            for (int j = 0; j < datosJAux.length; j++) {
                if (datosJ[i] == datosJAux[j]) {
                    float aux = posicionJ[j];
                    posicionJ[j] = posicionJ[i];
                    posicionJ[i] = aux;
                }
            }
        }
    }

    private void posicionar(float[] datos, float[] posicion) {
        for (int i = 0; i < datos.length - 1; i++) {
            if (datos[i] == datos[i + 1]) {
                int x = i;
                float cont = 1;
                while (x + 1 < datos.length - 1 && datos[x] == datos[x + 1]) {
                    x++;
                    cont++;
                }
                for (int j = i; j <= x; j++) {
                    float div = cont / 2;
                    posicion[j] = i + 1 + div;
                }
                i = x;
            } else {
                posicion[i] = i + 1;
            }
        }
        posicion[datos.length - 1] = datos.length;
    }

}
