import java.util.Random;
public class Robot {
    private String nombre;
    private int startPos;
    private int currentPos;
    private String color;
    // Partes de la cabeza
    private Rectangle cabeza;
    private Circle ojoIzq;
    private Circle ojoDer;
    private Rectangle boca;
    private int fila;
    private int col;

    
    public Robot(String nombre, int pos, int tamCelda) {
        this.nombre = nombre;
        this.startPos = pos;
        this.currentPos = pos;
        
        String[] colores = {"yellow", "blue", "green", "red"};
        Random rand = new Random();
        this.color = colores[rand.nextInt(colores.length)];

        // Cabeza
        cabeza = new Rectangle();
        cabeza.changeSize(25, 25);
        cabeza.changeColor(color);
        cabeza.moveVertical(60);
        cabeza.moveHorizontal(pos);

        // Ojo izquierdo
        ojoIzq = new Circle();
        ojoIzq.changeSize(5);
        ojoIzq.changeColor("black");
        ojoIzq.setPosition(cabeza.getxPosition() + 5, cabeza.getyPosition() + 5);

        // Ojo derecho
        ojoDer = new Circle();
        ojoDer.changeSize(5);
        ojoDer.changeColor("black");
        ojoDer.setPosition(cabeza.getxPosition() + 15, cabeza.getyPosition() + 5);

        // Boca
        boca = new Rectangle();
        boca.changeSize(5, 15);
        boca.changeColor("red");
        boca.setPosition(cabeza.getxPosition() + 5, cabeza.getyPosition() + 15);
    }

    /** Muestra la cabeza */
    public void makeVisible() {
        cabeza.makeVisible();
        ojoIzq.makeVisible();
        ojoDer.makeVisible();
        boca.makeVisible();
    }

    /** Mueve la cabeza a la derecha */
    public void moveRight() {
        cabeza.moveHorizontal(30);
        ojoIzq.moveHorizontal(30);
        ojoDer.moveHorizontal(30);
        boca.moveHorizontal(30);
        currentPos += 30;
    }

    public void moveLeft() {
        cabeza.moveHorizontal(-30);
        ojoIzq.moveHorizontal(-30);
        ojoDer.moveHorizontal(-30);
        boca.moveHorizontal(-30);
        currentPos -= 30;
    }
    
    /** Recoloca ojos y boca en base a la cabeza */
    private void updateParts() {
        int xCabeza = cabeza.getxPosition();
        int yCabeza = cabeza.getyPosition();

        ojoIzq.setPosition(xCabeza + 10, yCabeza + 15);
        ojoDer.setPosition(xCabeza + 35, yCabeza + 15);
        boca.setPosition(xCabeza + 15, yCabeza + 40);
    }

    // Getters opcionales
    public String getNombre() { 
        return nombre; 
    }
    public int getCurrentPos() {
        return currentPos; 
    }
    public String getColor() { 
        return color; 
    }
    
    public void moveTo(int x, int y) {
        int deltaX = x - cabeza.getxPosition();
        int deltaY = y - cabeza.getyPosition();
    
        
        cabeza.moveHorizontal(deltaX);
        ojoIzq.moveHorizontal(deltaX);
        ojoDer.moveHorizontal(deltaX);
        boca.moveHorizontal(deltaX);
    
        cabeza.moveVertical(deltaY);
        ojoIzq.moveVertical(deltaY);
        ojoDer.moveVertical(deltaY);
        boca.moveVertical(deltaY);
    
        currentPos = x;
    }
    
    public void setPosicion(int fila, int col) {
        this.fila = fila;
        this.col = col;
    }
    
    public int getFila() {
        return fila;
    }
    
    public int getCol() {
        return col;
    }

}
