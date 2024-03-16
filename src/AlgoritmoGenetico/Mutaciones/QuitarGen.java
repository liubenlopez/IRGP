/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgoritmoGenetico.Mutaciones;

import AlgoritmoGenetico.iMutadores;
import Microarray.Gen;
import java.util.List;
import java.util.Random;

public class QuitarGen implements iMutadores{

    public List<Gen> mutar(List<Gen> listGenesMutar, String[] listGenesGeneral, int position) {
        if (listGenesMutar.size() > 3) {
            listGenesMutar.remove(position);
        }
        return listGenesMutar;
    }
}
