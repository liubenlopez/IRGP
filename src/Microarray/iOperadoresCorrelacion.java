package Microarray;

public abstract interface iOperadoresCorrelacion {

    public abstract double[][] getCorrelationMatrix();

    public abstract void setData(float[][] paramArrayOfFloat);
}
