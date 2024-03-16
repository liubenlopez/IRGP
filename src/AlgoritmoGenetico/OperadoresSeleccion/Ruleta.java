package AlgoritmoGenetico.OperadoresSeleccion;

import Microarray.RedGenes;
import AlgoritmoGenetico.iSelector;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ruleta
        implements iSelector {

    public List<RedGenes> getPoblacionSeleccionada(List<RedGenes> poblacionRG, List<RedGenes> hijosRG) {
        List<RedGenes> poblacionRuleta = new ArrayList();
        poblacionRuleta.addAll(poblacionRG);
        poblacionRuleta.addAll(hijosRG);
        List poblacionSelecionada = new ArrayList();

        for (int j = 0; j < poblacionRG.size(); j++) {
            double fitnessTotal = 0.0D;
            int[] ruleta = new int[100];
            Random rnd = new Random();

            for (RedGenes rg : poblacionRuleta) {
                fitnessTotal += rg.getFitness();
            }

            int posicion = 0;
            int index = 1;
            for (RedGenes rg : poblacionRuleta) {
                double probabilidad = rg.getFitness() / fitnessTotal;
                for (int i = 0; i < (int) (probabilidad * 100.0D); i++) {
                    ruleta[posicion] = index;
                    posicion++;
                }
                index++;
            }
            //rnd.setSeed(1);
            int genSeleccionado = ruleta[rnd.nextInt(100)];
            while (genSeleccionado == 0) {//porque hay un grupo de posiciones que no se cubrirar
                genSeleccionado = ruleta[rnd.nextInt(100)];
            }
            poblacionSelecionada.add(poblacionRuleta.get(genSeleccionado - 1));
            poblacionRuleta.remove(genSeleccionado - 1);
        }
        //poblacionSelecionada.addAll(hijosRG);
        return poblacionSelecionada;
    }
}
