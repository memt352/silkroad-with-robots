public class Store {
    private String nombre;
    private int posicion;
    private int monto;
    private int startMonto;
    private Rectangle rectangulo;

    public Store(String nombre, int posicion, int tenges, int tamCelda) {
        this.nombre = nombre;
        this.posicion = posicion;
        this.monto = tenges;
        this.startMonto = tenges;
        rectangulo = new Rectangle();
        rectangulo.changeColor("yellow");
        rectangulo.changeSize(tamCelda - 4, tamCelda - 4); 
        rectangulo.makeVisible();
    }

    public String getNombre() {
        return nombre;
    }

    public int getPosicion() {
        return posicion;
    }

    public int getMonto() {
        return monto;
    }

    public void resetMonto() {
        this.monto = startMonto;
    }

    public void mover(int x, int y) {
        rectangulo.setPosition(x + 2, y + 2);
    }

    public void setTamaño(int tamaño) {
        rectangulo.changeSize(tamaño, tamaño);
    }
}
