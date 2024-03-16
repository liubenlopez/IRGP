package Microarray;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

public class DrawMincroarray extends JPanel
{
  private float[][] ma;
  float max = 0.0F;
  float min = 0.0F;
  int zoon = 2;

  public void paint(Graphics g)
  {
    setPreferredSize(new Dimension(this.zoon * this.ma[0].length, this.zoon * this.ma.length));
    for (int i = 0; i < this.ma.length; i++)
      for (int j = 0; j < this.ma[0].length; j++) {
        g.setColor(getColor(this.ma[i][j]));
        g.fillRect(j * this.zoon, i * this.zoon, this.zoon, this.zoon);
      }
  }

  public void setMicroarray(microarray _ma)
  {
    this.ma = _ma.getMicroarrayData();
    for (int i = 0; i < this.ma.length; i++)
      for (int j = 0; j < this.ma[0].length; j++) {
        if (this.min > this.ma[i][j]) {
          this.min = this.ma[i][j];
        }
        if (this.max < this.ma[i][j])
          this.max = this.ma[i][j];
      }
  }

  private Color getColor(float valor)
  {
    return new Color((int)(valor * 255.0F / this.max));
  }

  public void setZoon(int valor)
  {
    this.zoon = (valor >= 1 ? valor : 1);
  }
}