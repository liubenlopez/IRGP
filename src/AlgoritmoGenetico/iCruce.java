/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package AlgoritmoGenetico;

import Microarray.Gen;
import java.util.List;

public abstract interface iCruce
{
  public abstract List<Gen> getListaCruzada(List<Gen> paramList1, List<Gen> paramList2);
}