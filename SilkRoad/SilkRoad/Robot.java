import java.util.Random;

public class Robot {
    private String nombre;
    private int startPos;
    private int currentPos;
    private String color;
    private Circle circle;   // círculo que representa gráficamente al robot

    // lista de colores permitidos
    private static final String[] COLORES = {
        "red", "yellow", "blue", "green", "magenta", "black"
    };

    public Robot(String nombre, int posicion) {
        this.nombre = nombre;
        this.startPos = posicion;
        this.currentPos = posicion;

        // elegir un color aleatorio
        Random rand = new Random();
        this.color = COLORES[rand.nextInt(COLORES.length)];

        // crear el círculo con ese color
        circle = new Circle();
        circle.changeColor(color);
        circle.makeVisible();
    }

    public String getNombre() {
        return nombre;
    }

    public int getCurrentPos() {
        return currentPos;
    }

    public String getColor() {
        return color;
    }

    // devolver el robot a su posición inicial
    public void resetPos() {
        int desplazamiento = startPos - currentPos;
        currentPos = startPos;
        circle.moveHorizontal(desplazamiento);
    }

    // movimientos básicos a derecha e izquierda
    public void moveRight() {
        circle.moveRight();
        currentPos += 20;
    }

    public void moveLeft() {
        circle.moveLeft();
        currentPos -= 20;
    }
}
