package Microarray;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class microarray {

    private String microarrayDir = "";
    private String microarrayName = "";
    private List<String> listAtributo = new ArrayList();
    private List<String> listTipoAtributo = new ArrayList();
    private List<String> listDatos = new ArrayList();
    private List<String> listaGenes = new ArrayList();
    private float[][] microarrayData;

    public microarray(String microarray) {
        int index = -1;
        for (int i = 0; i < microarray.length() - 1; i++) {
            if ((microarray.substring(i, i + 1).equals("\\")) || (microarray.substring(i, i + 1).equals("/"))) {
                index = i;
            }
        }
        if (index != -1) {
            this.microarrayDir = microarray.substring(0, index);
            this.microarrayName = microarray.substring(index + 1, microarray.length());
            cargarMicroarray();
        }
    }

    public void cargarMicroarray() {
        try {
            FileReader fr = new FileReader(new File(this.microarrayDir + "/" + this.microarrayName));
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            boolean datos = false;
            while ((line = br.readLine()) != null) {
                leerAtributos(line);
                datos = leerDatos(datos, line);
            }
            br.close();
        } catch (Exception e) {
        }
        CrearMatriz();
    }

    private void leerAtributos(String line) {
        String[] segd = null;
        if (line.contains("\t")) {
            segd = line.split("\t");
            if (segd.length == 2) {
                String aux = segd[0];
                String[] otro = aux.split(" ");
                String[] s = new String[3];
                s[0] = otro[0];
                s[1] = otro[1];
                s[2] = segd[1];
                segd = s;
                segd[0] = segd[0].toUpperCase();
                segd[2] = segd[2].toUpperCase();
                line = segd[0]+" "+segd[1]+" "+segd[2];
            }
        } else {
            segd = line.split(" ");
            segd[0] = segd[0].toUpperCase();
            if (segd.length == 3 && segd[0].equals("@ATTRIBUTE")) {
                segd[2] = segd[2].toUpperCase();
                line = segd[0]+" "+segd[1]+" "+segd[2];
            }
        }
        if (line.startsWith("@ATTRIBUTE")) {
            if (line.contains("{")) {
                String texto = line.substring(line.indexOf("{") + 1, line.indexOf("}") - 1);
                String[] muestras = texto.split(",");
                this.listAtributo.addAll(Arrays.asList(muestras));
            } else {
                this.listaGenes.add(segd[1]);
                this.listAtributo.add(segd[2]);
            }
        }
    }

    private boolean leerDatos(boolean datos, String line) {
        if ((datos == true)
                && (!line.equals(""))) {
            this.listDatos.add(line);
        }
        String s = line.toUpperCase();
        if (s.startsWith("@DATA")) {
            datos = true;
        }
        return datos;
    }

    private void CrearMatriz() {
        int filas = this.listDatos.size();
        int columna = this.listaGenes.size();
        this.microarrayData = new float[filas][columna];
        for (int i = 0; i < filas; i++) {
            String[] s = ((String) this.listDatos.get(i)).split(",");
            for (int j = 0; j < columna; j++) {
                this.microarrayData[i][j] = Float.valueOf(s[j]).floatValue();
            }
        }
    }

    public String getMicroarrayName() {
        return this.microarrayName;
    }

    public void setMicroarrayName(String microarrayName) {
        this.microarrayName = microarrayName;
    }

    public String getMicroarrayDir() {
        return this.microarrayDir;
    }

    public void setMicroarrayDir(String microarrayDir) {
        this.microarrayDir = microarrayDir;
    }

    public List<String> getListAtributo() {
        return this.listAtributo;
    }

    public float[][] getMicroarrayData() {
        return this.microarrayData;
    }

    public List<String> getListTipoAtributo() {
        return this.listTipoAtributo;
    }

    public String[] getListaGenes() {
        String[] result = new String[this.listaGenes.size()];
        for (int i = 0; i < this.listaGenes.size(); i++) {
            result[i] = ((String) this.listaGenes.get(i));
        }
        return result;
    }

    public void setListAtributo(List<String> listAtributo) {
        this.listAtributo = listAtributo;
    }

    public void setListTipoAtributo(List<String> listTipoAtributo) {
        this.listTipoAtributo = listTipoAtributo;
    }

    public void setListDatos(List<String> listDatos) {
        this.listDatos = listDatos;
    }

    public void setListaGenes(List<String> listaGenes) {
        this.listaGenes = listaGenes;
    }

    public void setMicroarrayData(float[][] microarrayData) {
        this.microarrayData = microarrayData;
    }

}
