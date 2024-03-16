package AlgoritmoGenetico.OperadoresCruzamiento;

import Microarray.Gen;
import AlgoritmoGenetico.iCruce;
import java.util.ArrayList;
import java.util.List;

public class GenesDiferentes
        implements iCruce {

    public List<Gen> getListaCruzada(List<Gen> listGen1, List<Gen> listGen2) {
        List listnoRepetida = new ArrayList();

        for (int i = 0; i < listGen1.size(); i++) {
            listnoRepetida.add(listGen1.get(i));
        }

        for (int i = 0; i < listGen2.size(); i++) {
            boolean sabersiesta = false;
            for (int j = 0; j < listGen1.size(); j++) {
                if (((Gen) listGen2.get(i)).getTipo().equals(((Gen) listGen1.get(j)).getTipo())) {
                    sabersiesta = true;
                    j = listGen1.size();
                }
            }
            if (!sabersiesta && listGen2.get(i).getGrado() == 1) {
                listnoRepetida.add(listGen2.get(i));
            }
        }

        return listnoRepetida;
    }
}
