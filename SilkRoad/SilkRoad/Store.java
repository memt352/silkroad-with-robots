public class Store {
    private String nombre;
    private int posicion;
    private int monto;
    private int startMonto;
    private Rectangle rectangulo;

    public Store(String nombre, int posicion, int tenges) {
        this.nombre = nombre;
        this.posicion = posicion;
        this.monto = tenges;
        this.startMonto = tenges;

        // crear el rectángulo que representa a la tienda
        rectangulo = new Rectangle();
        rectangulo.changeColor("black");  
        rectangulo.changeSize(40, 20);    
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

    public void setMonto(int nuevoMonto) {
        this.monto = nuevoMonto;
    }

    public void resetMonto() {
        this.monto = startMonto;
    }
}
