/**
 * Representa una tienda en el simulador SilkRoad.
 * (Actualizado para Ciclo 2)
 */
public class Store {
    // Atributos visuales
    private Circle circulo;

    // Atributos lógicos
    private String nombre;
    private int tenges;
    private int location;
    private int initialTenges; // Para poder reabastecer
    
    // --- NUEVO CICLO 2 ---
    private int emptiedCount; // Contador de veces vaciada [cite: 106, 113]

    /**
     * Constructor para una Tienda.
     */
    public Store(String nombre, int location, int tenges) {
        this.nombre = nombre;
        this.tenges = tenges;
        this.initialTenges = tenges;
        this.location = location;
        
        // --- NUEVO CICLO 2 ---
        this.emptiedCount = 0;
        
        // --- GRÁFICOS (tu código) ---
        this.circulo = new Circle();
        this.circulo.changeColor("magenta"); // [cite: 187]
        this.circulo.changeSize(40);
    }

    /** Mueve la tienda a la celda (x, y) y la hace visible. */
    public void mover(int x, int y, int tamCelda) {
        int centroX = x + (tamCelda - 40) / 2;
        int centroY = y + (tamCelda - 40) / 2;
        circulo.setPosition(centroX, centroY);
        circulo.makeVisible();
    }
    
    /** Oculta la tienda */
    public void makeInvisible() {
        circulo.makeInvisible();
    }
    
    // --- MÉTODOS LÓGICOS ---

    public int getLocation() {
        return location;
    }
    
    /** Un robot recoge el dinero de la tienda. */
    public int collectTenges() {
        int collected = this.tenges;
        if (collected > 0) {
            // --- NUEVO CICLO 2 ---
            this.emptiedCount++; // Incrementa el contador [cite: 113]
            circulo.changeColor("grey"); // Requisito Usabilidad C2 [cite: 116]
        }
        this.tenges = 0;
        return collected;
    }

    /** Reabastece la tienda a su monto inicial */
    public void resupply() { // [cite: 148]
        this.tenges = this.initialTenges;
        if (this.tenges > 0) {
            circulo.changeColor("magenta");
        }
    }
    
    public int getMonto() {
        return tenges;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    /** NUEVO CICLO 2: Obtiene el contador de veces vaciada */
    public int getEmptiedCount() {
        return emptiedCount;
    }
}