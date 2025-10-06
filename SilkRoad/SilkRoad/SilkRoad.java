import java.util.ArrayList;

public class SilkRoad {
    private Canvas canvas;
    private ArrayList<Robot> robots;
    private ArrayList<Store> stores;
    private Rectangle camino;   // 👈 rectángulo que representa el camino

    public SilkRoad() {
        canvas = Canvas.getCanvas();
        robots = new ArrayList<Robot>();
        stores = new ArrayList<Store>();

        // Crear un rectángulo como camino
        camino = new Rectangle();
        camino.changeSize(20, 500);   // altura 20, ancho 500 (parece una línea)
        camino.changeColor("gray");   // color gris tipo carretera
        camino.makeVisible();         // mostrar en el canvas
    }

    public void placeRobot(String nombre, int posicion) {
        Robot r = new Robot(nombre, posicion);
        robots.add(r);
    }

    public void placeStore(String nombre, int posicion, int tenges) {
        Store s = new Store(nombre, posicion, tenges);
        stores.add(s);
    }

    public void removeRobot(String nombre) {
        for (int i = 0; i < robots.size(); i++) {
            if (robots.get(i).getNombre().equals(nombre)) {
                robots.remove(i);
                break;
            }
        }
    }

    public void removeStore(String nombre) {
        for (int i = 0; i < stores.size(); i++) {
            if (stores.get(i).getNombre().equals(nombre)) {
                stores.remove(i);
                break;
            }
        }
    }

//    public void resetDay() {
//        for (Robot r : robots) {
//            r.resetPos();
//        }
//        for (Store s : stores) {
//            s.resetMonto();
//        }
//    }

    public int maxProfit() {
        int mejor = 0;
        for (Robot r : robots) {
            for (Store s : stores) {
                int distancia = Math.abs(r.getCurrentPos() - s.getPosicion());
                int ganancia = s.getMonto() - distancia;
                if (ganancia > mejor) {
                    mejor = ganancia;
                }
            }
        }
        return mejor;
    }
}
