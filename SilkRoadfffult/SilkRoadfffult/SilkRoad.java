import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Clase principal del simulador SilkRoad.
 * (Actualizado para Ciclo 2 y modificado para Ciclo 3)
 * (Corrección final en método 'finish')
 */
public class SilkRoad {
    private Canvas canvas;
    private ArrayList<Robot> robots;
    private ArrayList<Store> stores;
    private Rectangle[][] celdas; 
    private int tamaño; 
    private int tamCelda;
    
    private ArrayList<int[]> spiralMap;
    private boolean lastActionOk;
    private int ganancias;
    private boolean isVisible; 
    private int routeLength; 

    // --- CONSTRUCTORES ---

    /**
     * Constructor Ciclo 1.
     */
    public SilkRoad(int length) {
        this.routeLength = length; 
        this.tamaño = (int) Math.ceil(Math.sqrt(length));
        if (this.tamaño == 0) this.tamaño = 1; 
        
        this.tamCelda = 60; 
        this.canvas = Canvas.getCanvas();
        this.robots = new ArrayList<>();
        this.stores = new ArrayList<>();
        this.celdas = new Rectangle[tamaño][tamaño];
        this.spiralMap = new ArrayList<>(); 
        this.ganancias = 0;
        this.lastActionOk = true; 
        this.isVisible = false; 
        
        dibujarEspiralCuadrada(length);
    }
    
    /**
     * NUEVO CONSTRUCTOR CICLO 2 (Requisito 10)
     */
    public SilkRoad(int[][] days) {
        this(days[0][0]); // Llama al constructor original con la longitud
        
        for (int i = 1; i < days[0].length; i += 2) {
            int location = days[0][i];
            int tenges = days[0][i+1];
            if (location >= 0 && location < this.routeLength) {
                this.placeStore(location, tenges);
            }
        }
        
        this.placeRobot(0);
    }
    
    // --- MÉTODOS DE DIBUJADO Y HELPERS ---

    /** Dibuja la espiral (Req. Usabilidad C1) */
    private void dibujarEspiralCuadrada(int length) {
        int x = 50, y = 50;
        int minFila = 0, maxFila = tamaño - 1;
        int minCol = 0, maxCol = tamaño - 1;
        int count = 0; 

        while (minFila <= maxFila && minCol <= maxCol && count < length) {
            // Derecha
            for (int j = minCol; j <= maxCol && count < length; j++) {
                crearCelda(minFila, j, x + j * tamCelda, y + minFila * tamCelda);
                count++;
            }
            // Abajo
            for (int i = minFila + 1; i <= maxFila && count < length; i++) {
                crearCelda(i, maxCol, x + maxCol * tamCelda, y + i * tamCelda);
                count++;
            }
            // Izquierda
            if (minFila < maxFila) {
                for (int j = maxCol - 1; j >= minCol && count < length; j--) {
                    crearCelda(maxFila, j, x + j * tamCelda, y + maxFila * tamCelda);
                    count++;
                }
            }
            // Arriba
            if (minCol < maxCol) {
                for (int i = maxFila - 1; i > minFila && count < length; i--) {
                    crearCelda(i, minCol, x + minCol * tamCelda, y + i * tamCelda);
                    count++;
                }
            }
            minFila++; minCol++; maxFila--; maxCol--;
        }
    }

    private void crearCelda(int fila, int col, int x, int y) {
        Rectangle celda = new Rectangle();
        celda.changeColor("black");
        celda.changeSize(tamCelda - 2, tamCelda - 2);
        celda.setPosition(x + 1, y + 1);
        celdas[fila][col] = celda;
        spiralMap.add(new int[]{fila, col});
    }

    private Rectangle getCelda(int location) {
        if (location < 0 || location >= spiralMap.size()) {
            return null; 
        }
        int[] coords = spiralMap.get(location);
        return celdas[coords[0]][coords[1]];
    }
    
    private Robot getRobotAt(int location) {
        for (Robot r : robots) {
            if (r.getLocation() == location) return r;
        }
        return null;
    }
    
    private Store getStoreAt(int location) {
        for (Store s : stores) {
            if (s.getLocation() == location) return s;
        }
        return null;
    }

    // --- MÉTODOS REQUERIDOS (CICLO 1) ---

    public void placeStore(int location, int tenges) {
        lastActionOk = false;
        Rectangle celda = getCelda(location);
        if (celda != null && getStoreAt(location) == null) {
            Store s = new Store("Store " + location, location, tenges);
            stores.add(s);
            s.mover(celda.getxPosition(), celda.getyPosition(), tamCelda);
            
            if (!this.isVisible) {
                s.makeInvisible();
            }
            lastActionOk = true;
        }
    }

    public void removeStore(int location) {
        lastActionOk = false;
        Store s = getStoreAt(location);
        if (s != null) {
            s.makeInvisible(); 
            stores.remove(s);  
            lastActionOk = true;
        }
    }

    public void resupplyStores() {
        for (Store s : stores) {
            s.resupply();
        }
        lastActionOk = true;
    }

    public void placeRobot(int location) {
        lastActionOk = false;
        Rectangle celda = getCelda(location);
        if (celda != null && getRobotAt(location) == null) {
            Robot r = new Robot("Robot " + location, location);
            robots.add(r);
            r.moveTo(celda.getxPosition(), celda.getyPosition());
            
            if (this.isVisible) {
                r.makeVisible();
            }
            lastActionOk = true;
        }
    }

    public void removeRobot(int location) {
        lastActionOk = false;
        Robot r = getRobotAt(location);
        if (r != null) {
            r.makeInvisible();
            robots.remove(r);
            lastActionOk = true;
        }
    }

    public void returnRobots() {
        for (Robot r : robots) {
            r.reset();
            Rectangle celda = getCelda(r.getLocation());
            if (celda != null) {
                r.moveTo(celda.getxPosition(), celda.getyPosition());
            }
        }
        lastActionOk = true;
    }
    
    public void moveRobot(int location, int meters) {
        lastActionOk = false;
        Robot r = getRobotAt(location);
        if (r != null) {
            int newLocation = location + meters;
            Rectangle celdaDestino = getCelda(newLocation);
            
            if (celdaDestino != null) {
                r.setLocation(newLocation); 
                r.moveTo(celdaDestino.getxPosition(), celdaDestino.getyPosition()); 
                
                Store s = getStoreAt(newLocation);
                if (s != null) {
                    int collected = s.collectTenges();
                    r.addTenges(collected);
                    this.ganancias += collected; 
                }
                lastActionOk = true;
            }
        }
    }

    public void reboot() {
        resupplyStores();
        this.ganancias = 0;
        
        for (Robot r : robots) {
            r.resetForNewDay(); // Usa el nuevo método de reseteo C2
            Rectangle celda = getCelda(r.getLocation());
            if (celda != null) {
                r.moveTo(celda.getxPosition(), celda.getyPosition());
            }
        }
        
        lastActionOk = true;
    }

    public int porfit() { 
        lastActionOk = true;
        return this.ganancias;
    }

    public int[][] stores() { 
        Collections.sort(stores, Comparator.comparingInt(Store::getLocation));
        
        int[][] result = new int[stores.size()][2];
        for (int i = 0; i < stores.size(); i++) {
            Store s = stores.get(i);
            result[i][0] = s.getLocation();
            result[i][1] = s.getMonto();
        }
        lastActionOk = true;
        return result;
    }

    public int[][] robots() { 
        Collections.sort(robots, Comparator.comparingInt(Robot::getLocation));
        
        int[][] result = new int[robots.size()][2];
        for (int i = 0; i < robots.size(); i++) {
            Robot r = robots.get(i);
            result[i][0] = r.getLocation();
            result[i][1] = r.getTenges();
        }
        lastActionOk = true;
        return result;
    }

    public void makeVisible() { 
        this.isVisible = true; 
        canvas.setVisible(true); 
        
        for(int i = 0; i < spiralMap.size(); i++) {
            Rectangle celda = getCelda(i);
            if(celda != null) celda.makeVisible();
        }
        
        for(Store s : stores) {
            Rectangle celda = getCelda(s.getLocation());
            if (celda != null) s.mover(celda.getxPosition(), celda.getyPosition(), tamCelda);
        }
        for(Robot r : robots) r.makeVisible();
        
        lastActionOk = true;
    }

    public void makeInvisible() { 
        this.isVisible = false; 
        
        for(int i = 0; i < spiralMap.size(); i++) {
             Rectangle celda = getCelda(i);
            if(celda != null) celda.makeInvisible();
        }
        for(Store s : stores) s.makeInvisible();
        for(Robot r : robots) r.makeInvisible();
        
        canvas.setVisible(false); 
        lastActionOk = true;
    }

    /**
     * Requisito 9: Terminar (MODIFICADO para C3)
     * System.exit(0) cierra toda la aplicación, rompiendo la simulación.
     * Ahora, solo oculta la ventana.
     */
    public void finish() { 
        // System.exit(0); // NO USAR ESTO
        makeInvisible(); // Simplemente oculta la ventana
        // La línea 'Canvas.getCanvas().close();' se eliminó porque 'close' no existe.
        lastActionOk = true;
    }
    
    public boolean ok() { 
        return lastActionOk;
    }
    
    
    // --- MÉTODOS NUEVOS CICLO 2 ---

    public void moveRobots() { 
        lastActionOk = false;
        if (robots.isEmpty()) {
            lastActionOk = true;
            return;
        }

        Collections.sort(robots, Comparator.comparingInt(Robot::getLocation));

        for (Robot r : robots) {
            Store targetStore = findNearestStoreWithMoney(r.getLocation());
            
            if (targetStore != null) {
                int newLocation = targetStore.getLocation();
                Rectangle celdaDestino = getCelda(newLocation);
                
                r.setLocation(newLocation);
                r.moveTo(celdaDestino.getxPosition(), celdaDestino.getyPosition());
                
                int collected = targetStore.collectTenges();
                r.addTenges(collected); 
                this.ganancias += collected;
                
                if(isVisible) Canvas.getCanvas().wait(200);
            }
        }
        
        updateBlinkingRobot();
        lastActionOk = true;
    }
    
    private Store findNearestStoreWithMoney(int robotLocation) {
        Store nearestStore = null;
        int minDistance = Integer.MAX_VALUE;

        for (Store s : stores) {
            if (s.getMonto() > 0 && s.getLocation() > robotLocation) {
                int distance = s.getLocation() - robotLocation;
                
                if (distance < minDistance) {
                    minDistance = distance;
                    nearestStore = s;
                }
            }
        }
        return nearestStore; 
    }

    private void updateBlinkingRobot() { 
        if (robots.isEmpty() || !isVisible) return;
        
        Robot maxRobot = null;
        int maxProfit = -1; 
        
        for (Robot r : robots) {
            if (r.getTotalTenges() > maxProfit) {
                maxProfit = r.getTotalTenges();
                maxRobot = r;
            }
        }
        
        if (maxRobot != null && maxProfit > 0) {
            maxRobot.blink();
        }
    }

    public int[][] emptiedStores() { 
        Collections.sort(stores, Comparator.comparingInt(Store::getLocation));
        
        int[][] result = new int[stores.size()][2];
        for (int i = 0; i < stores.size(); i++) {
            Store s = stores.get(i);
            result[i][0] = s.getLocation();
            result[i][1] = s.getEmptiedCount();
        }
        lastActionOk = true;
        return result;
    }

    public int[][] profitPerMove() { 
        Collections.sort(robots, Comparator.comparingInt(Robot::getLocation));
        
        int[][] result = new int[robots.size()][];
        for (int i = 0; i < robots.size(); i++) {
            Robot r = robots.get(i);
            ArrayList<Integer> profits = r.getProfitPerMove();
            
            result[i] = new int[1 + profits.size()];
            result[i][0] = r.getLocation(); 
            
            for (int j = 0; j < profits.size(); j++) {
                result[i][j+1] = profits.get(j);
            }
        }
        lastActionOk = true;
        return result;
    }
}