import java.util.Random;
import java.util.ArrayList;

/**
 * Representa un robot en el simulador SilkRoad.
 * (Actualizado para Ciclo 2, con todas las correcciones)
 */
public class Robot {
    // Atributos visuales (del paquete shapes)
    private Rectangle cabeza;
    private Circle ojoIzq;
    private Circle ojoDer;
    private Rectangle boca;
    private String color;

    // Atributos lógicos
    private String nombre;
    private int location;       // Posición 1D actual en la ruta
    private int startLocation;  // Posición 1D inicial
    private int tenges;         // Dinero que lleva el robot (en este movimiento/día)
    
    // --- NUEVO CICLO 2 ---
    private ArrayList<Integer> profitPerMove; // Lista de ganancias por movimiento [cite: 108, 114]
    private int totalTenges; // Ganancia total histórica (para parpadear) [cite: 117]
    
    // Atributos de posición 2D (para referencia)
    private int fila;
    private int col;

    /**
     * Constructor para un Robot.
     * @param nombre El nombre identificador del robot.
     * @param location La posición 1D inicial en la ruta.
     */
    public Robot(String nombre, int location) {
        this.nombre = nombre;
        this.startLocation = location;
        this.location = location;
        this.tenges = 0; 
        
        // --- NUEVO CICLO 2 ---
        this.profitPerMove = new ArrayList<>();
        this.totalTenges = 0;
        
        // --- GRÁFICOS (tu código) ---
        String[] colores = {"yellow", "blue", "green", "red"};
        Random rand = new Random();
        this.color = colores[rand.nextInt(colores.length)]; // [cite: 187]

        // Cabeza
        cabeza = new Rectangle();
        cabeza.changeSize(25, 25);
        cabeza.changeColor(color);

        // Ojo izquierdo
        ojoIzq = new Circle();
        ojoIzq.changeSize(5);
        ojoIzq.changeColor("black");
        
        // Ojo derecho
        ojoDer = new Circle();
        ojoDer.changeSize(5);
        ojoDer.changeColor("black");

        // Boca
        boca = new Rectangle();
        boca.changeSize(5, 15);
        boca.changeColor("red");
    }

    /** Muestra el robot */
    public void makeVisible() {
        cabeza.makeVisible();
        ojoIzq.makeVisible();
        ojoDer.makeVisible();
        boca.makeVisible();
    }
    
    /** Oculta el robot */
    public void makeInvisible() {
        cabeza.makeInvisible();
        ojoIzq.makeInvisible();
        ojoDer.makeInvisible();
        boca.makeInvisible();
    }
    
    /** Mueve el robot y todas sus partes a una nueva coordenada (x, y) */
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
        
        // Re-posiciona los ojos y boca relativo a la cabeza
        ojoIzq.setPosition(cabeza.getxPosition() + 5, cabeza.getyPosition() + 5);
        ojoDer.setPosition(cabeza.getxPosition() + 15, cabeza.getyPosition() + 5);
        boca.setPosition(cabeza.getxPosition() + 5, cabeza.getyPosition() + 15);
    }
    
    // --- MÉTODOS LÓGICOS ---

    public int getLocation() {
        return location;
    }
    
    public void setLocation(int newLocation) {
        this.location = newLocation;
    }

    public int getTenges() {
        return tenges;
    }

    /** Añade tenges y lo registra en el historial de movimientos */
    public void addTenges(int amount) {
        if (amount > 0) {
            this.tenges += amount;
            this.totalTenges += amount; 
            this.profitPerMove.add(amount); 
        }
    }
    
    /** Resetea el robot a su posición inicial (para returnRobots) */
    public void reset() {
        this.location = this.startLocation;
        this.tenges = 0;
    }
    
    /** NUEVO CICLO 2: Reseteo para un nuevo día (reboot) */
    public void resetForNewDay() {
        this.location = this.startLocation;
        this.tenges = 0;
        this.profitPerMove.clear(); 
    }
    
    /** NUEVO CICLO 2: Obtiene el historial de ganancias */
    public ArrayList<Integer> getProfitPerMove() {
        return profitPerMove;
    }
    
    /** NUEVO CICLO 2: Obtiene la ganancia total histórica */
    public int getTotalTenges() {
        return totalTenges;
    }
    
    /**
     * NUEVO CICLO 2: Lógica de parpadeo (Req. Usabilidad C2) [cite: 117]
     * (Con la corrección final: 'if' eliminado)
     */
    public void blink() {
        for (int i = 0; i < 3; i++) {
            cabeza.makeInvisible();
            ojoIzq.makeInvisible();
            ojoDer.makeInvisible();
            boca.makeInvisible();
            
            Canvas.getCanvas().wait(150);
            
            cabeza.makeVisible();
            ojoIzq.makeVisible();
            ojoDer.makeVisible();
            boca.makeVisible();
            
            Canvas.getCanvas().wait(150);
        }
    }
    
    // --- GETTERS / SETTERS (Los que ya tenías) ---
    
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
    
    public String getNombre() { 
        return nombre; 
    }
}