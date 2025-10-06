import java.awt.*;
import java.awt.geom.*;

public class Mapa {
    private int dimension;
    private int tamanoCelda;
    private Canvas canvas;

    public Mapa(int dimension, int tamanoCelda) {
        this.dimension = dimension;
        this.tamanoCelda = tamanoCelda;
        this.canvas = Canvas.getCanvas();
        dibujarFondo();
    }

    private void dibujarFondo() {
        int lado = dimension * tamanoCelda;
        Shape fondo = new Rectangle2D.Double(0, 0, lado, lado);
        canvas.draw(this, "white", fondo); 
    }
}
