package AlgoritmoGenetico.OperadoresCruzamiento;

import Microarray.Gen;
import AlgoritmoGenetico.iCruce;
import java.util.ArrayList;
import java.util.List;

public class CruzaMitades
        implements iCruce {

    public List<Gen> getListaCruzada(List<Gen> listGen1, List<Gen> listGen2) {
        List listResul = new ArrayList();

        boolean sabersiEstaLista = true;

        int mitaLista1 = listGen1.size() / 2 + 1;
        int mitaLista2 = listGen2.size() / 2 - 1;

        for (int i = 0; i < mitaLista1; i++) {
            listResul.add(listGen1.get(i));
        }
        for (int i = mitaLista2; i < listGen2.size(); i++) {
            sabersiEstaLista = true;
            for (int j = 0; j < listResul.size(); j++) {
                if (((Gen) listResul.get(j)).getTipo().equals(((Gen) listGen2.get(i)).getTipo())) {
                    sabersiEstaLista = false;
                    j = listResul.size();
                }
            }

            if (sabersiEstaLista) {
                listResul.add(listGen2.get(i));
            }

        }

        return listResul;
    }
}
