/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Microarray.OperadoresCorrelacion;

import Microarray.iOperadoresCorrelacion;

public class Euclideano
        implements iOperadoresCorrelacion {

    private float[][] datos;
    private double[][] corr;

    public double[][] getCorrelationMatrix() {
        int c = this.datos[0].length;
        this.corr = new double[c][c];

        for (int i = 0; i < c - 1; i++) {
            for (int j = i + 1; j < c; j++) {
                this.corr[i][j] = CalcularCorrelacion(i, j);
            }
        }
        return this.corr;
    }

    private double CalcularCorrelacion(int i, int j) {
        double result = 0.0D;
        for (int f = 0; f < this.datos.length; f++) {
            result += (this.datos[f][i] - this.datos[f][j]) * (this.datos[f][i] - this.datos[f][j]);
        }
        result = Math.sqrt(result);
        return result;
    }

    public void setData(float[][] datos) {
        this.datos = datos;
    }
}
