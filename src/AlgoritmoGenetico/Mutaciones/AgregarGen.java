package AlgoritmoGenetico.Mutaciones;

import Microarray.Gen;
import AlgoritmoGenetico.iMutadores;
import java.util.List;
import java.util.Random;

public class AgregarGen implements iMutadores {

    public List<Gen> mutar(List<Gen> listGenesMutar, String[] listGenesGeneral, int position) {
        boolean incrementeGen = false;

        if (listGenesMutar.size() != listGenesGeneral.length) {
            while (!incrementeGen) {
                Random rnd = new Random(199);
                int numAleat = rnd.nextInt(listGenesGeneral.length);
                boolean bandera = true;
                for (int i = 0; i < listGenesMutar.size(); i++) {
                    if (((Gen) listGenesMutar.get(i)).getTipo().equals(listGenesGeneral[numAleat])) {
                        bandera = false;
                    }
                }

                if (bandera == true) {
                    Gen newgen = new Gen();
                    newgen.setPosicion(Integer.valueOf(numAleat));
                    newgen.setTipo(listGenesGeneral[numAleat]);
                    listGenesMutar.add(newgen);
                    incrementeGen = true;
                }
            }
        }
        return listGenesMutar;
    }
}
