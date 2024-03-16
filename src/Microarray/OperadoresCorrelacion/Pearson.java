package Microarray.OperadoresCorrelacion;

import Microarray.iOperadoresCorrelacion;

public class Pearson  implements iOperadoresCorrelacion {

    private float[][] datos;
    private double[][] MatrizCorr;

    public double[][] getCorrelationMatrix() {
        int c = this.datos[0].length;
        this.MatrizCorr = new double[c][c];

        for (int i = 0; i < c - 1; i++) {
            for (int j = i + 1; j < c; j++) {
                this.MatrizCorr[i][j] = CalcularPearson(i, j);
            }
        }
        return this.MatrizCorr;
    }

    private double CalcularPearson(int i, int j) {
        int n = this.datos.length;

        float[] varX = new float[n];
        float[] varY = new float[n];
        float mediaX = 0.0F;
        float mediaY = 0.0F;
        for (int f = 0; f < n; f++) {
            varX[f] += this.datos[f][i];
            varY[f] += this.datos[f][j];
            mediaX += varX[f];
            mediaY += varY[f];
        }
        mediaX /= n;
        mediaY /= n;
        float p1 = 0.0F;
        float p2 = 0.0F;
        float p3 = 0.0F;
        for (int f = 0; f < n; f++) {
            p1 += (varX[f] - mediaX) * (varY[f] - mediaY);
            p2 += (varX[f] - mediaX) * (varX[f] - mediaX);
            p3 += (varY[f] - mediaY) * (varY[f] - mediaY);
        }
        p2 = (float) Math.sqrt(p2 * p3);
        return p2 != 0.0F ? p1 / p2 : 0.0D;
    }

    public void setData(float[][] datos) {
        this.datos = datos;
    }
}
