/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package AlgoritmoGenetico;

import Microarray.RedGenes;
import java.util.List;

public abstract interface iSelector
{
  public abstract List<RedGenes> getPoblacionSeleccionada(List<RedGenes> paramList1, List<RedGenes> paramList2);
}
