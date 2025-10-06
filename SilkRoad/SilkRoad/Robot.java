public class Robot {
    private String nombre;
    private int startPos;
    private int currentPos;
    private String color;

    // Partes del robot
    private Circle cuerpo;
    private Rectangle cabeza;
    private Rectangle brazoIzq;
    private Rectangle piernaDer;

    public Robot(String nombre, int pos) {
        this.nombre = nombre;
        this.startPos = pos;
        this.currentPos = pos;

        // Cabeza (rectángulo)
        cabeza = new Rectangle();
        cabeza.changeSize(40, 40);
        cabeza.changeColor("green");
        cabeza.moveVertical(60);
        cabeza.moveHorizontal(pos);

        // Cuerpo (círculo debajo de la cabeza)
        cuerpo = new Circle();
        cuerpo.changeSize(80);
        cuerpo.changeColor("blue");
        cuerpo.moveVertical(100);
        cuerpo.moveHorizontal(cabeza.getxPosition() - 40);

        // Brazo izquierdo
        brazoIzq = new Rectangle();
        brazoIzq.changeSize(15, 50);
        brazoIzq.changeColor("red");
        brazoIzq.moveVertical(110);
        brazoIzq.moveHorizontal(cabeza.getxPosition() - 30);

        // Pierna derecha
        piernaDer = new Rectangle();
        piernaDer.changeSize(50, 15);
        piernaDer.changeColor("black");
        piernaDer.moveVertical(180);
        piernaDer.moveHorizontal(cabeza.getxPosition() - 60);

    }

    /** Muestra al robot */
    public void makeVisible() {
        cuerpo.makeVisible();
        cabeza.makeVisible();
        brazoIzq.makeVisible();
        piernaDer.makeVisible();
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

    /** Mueve todo el robot a la derecha */
    public void moveRight() {
        cabeza.moveHorizontal(20);
        currentPos += 20;
        updateParts();
    }

    /** Mueve todo el robot a la izquierda */
    public void moveLeft() {
        cabeza.moveHorizontal(-20);
        currentPos -= 20;
        updateParts();
    }

    /** Recoloca las demás partes en base a la cabeza */
    private void updateParts() {
        int xCabeza = cabeza.getxPosition();
        int yCabeza = cabeza.getyPosition();

        // Cuerpo debajo de la cabeza
        cuerpo.setPosition(xCabeza - 20, yCabeza + 40);

        // Brazo izquierdo
        brazoIzq.setPosition(xCabeza + 30, yCabeza + 50);

        // Pierna derecha
        piernaDer.setPosition(xCabeza + 10, yCabeza + 120);
    }
}
