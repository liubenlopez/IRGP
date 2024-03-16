/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package AlgoritmoGenetico;

import Microarray.Gen;
import java.util.List;

public abstract interface iMutadores
{
  public abstract List<Gen> mutar(List<Gen> paramList, String[] paramArrayOfString, int position);
}