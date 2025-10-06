import java.util.ArrayList;

public class SilkRoad {
    private Canvas canvas;
    private ArrayList<Robot> robots;
    private ArrayList<Store> stores;
    private Rectangle[][] celdas;
    private int tamaño;
    private int tamCelda;
    Mapa mapa = new Mapa(10, 60); 
    
    public SilkRoad(int tamaño, int tamCelda) {
        this.tamaño = tamaño;
        this.tamCelda = tamCelda;
        this.canvas = Canvas.getCanvas();
        this.robots = new ArrayList<Robot>();
        this.stores = new ArrayList<Store>();
        this.celdas = new Rectangle[tamaño][tamaño];
        dibujarEspiralCuadrada();
    }

    private void dibujarEspiralCuadrada() {
        int x = 50;
        int y = 50;
        int minFila = 0, maxFila = tamaño - 1;
        int minCol = 0, maxCol = tamaño - 1;
        
        while (minFila <= maxFila && minCol <= maxCol) {
            for (int j = minCol; j <= maxCol; j++) crearCelda(minFila, j, x + j * tamCelda, y + minFila * tamCelda);
            for (int i = minFila + 1; i <= maxFila; i++) crearCelda(i, maxCol, x + maxCol * tamCelda, y + i * tamCelda);
            if (minFila < maxFila) {
                for (int j = maxCol - 1; j >= minCol; j--) crearCelda(maxFila, j, x + j * tamCelda, y + maxFila * tamCelda);
            }
            if (minCol < maxCol) {
                for (int i = maxFila - 1; i > minFila; i--) crearCelda(i, minCol, x + minCol * tamCelda, y + i * tamCelda);
            }
            minFila++;
            minCol++;
            maxFila--;
            maxCol--;
        }
        canvas.wait(1000);
    }

    private void crearCelda(int fila, int col, int x, int y) {
        Rectangle celda = new Rectangle();
        celda.changeColor("black"); 
        celda.changeSize(tamCelda - 2, tamCelda - 2); 
        celda.setPosition(x + 1, y + 1); 
        celda.makeVisible();
        celdas[fila][col] = celda; 
    }

    public void placeRobot(String nombre, int fila, int col) {
        if (fila >= 0 && fila < tamaño && col >= 0 && col < tamaño) {
            Robot r = new Robot(nombre, 0, this.tamCelda); 
            robots.add(r);
            Rectangle celda = celdas[fila][col];
            if (celda != null) r.moveTo(celda.getxPosition(), celda.getyPosition());
        }
    }

    public void placeStore(String nombre, int fila, int col, int tenges) {
        if (fila >= 0 && fila < tamaño && col >= 0 && col < tamaño) {
            Store s = new Store(nombre, 0, tenges, this.tamCelda); 
            stores.add(s);
            Rectangle celda = celdas[fila][col];
            if (celda != null) s.mover(celda.getxPosition(), celda.getyPosition());
        }
    }

    public static void main(String[] args) {
        SilkRoad road = new SilkRoad(15, 30);
        road.placeRobot("R1", 0, 0);
        road.placeStore("S1", 7, 7, 50);
        road.placeStore("S2", 10, 10, 30);
    }
}
