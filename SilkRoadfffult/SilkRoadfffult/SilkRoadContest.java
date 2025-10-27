import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Collections; // <-- SOLUCIÓN AL ERROR 1

/**
 * REQUISITOS CICLO 3 (DOPO-I03)
 * Resuelve y simula el "Problem J" de la maratón.
 * Cumple con la restricción de NO usar SilkRoad para resolver.
 */
public class SilkRoadContest {

    // --- Clases 'helper' internas para 'solve' (No usar SilkRoad) ---
    
    private class AlgorithmStore implements Comparable<AlgorithmStore> {
        int location;
        int initialTenges;
        int currentTenges;
        int id; // ID único (índice)

        AlgorithmStore(int loc, int tenges, int id) {
            this.location = loc;
            this.initialTenges = tenges;
            this.currentTenges = tenges;
            this.id = id;
        }
        void resupply() { 
            this.currentTenges = this.initialTenges;
        }
        
        // Necesario para Collections.sort()
        public int compareTo(AlgorithmStore other) {
            return this.location - other.location;
        }
    }
    
    private class AlgorithmRobot {
        int startLocation;
        int id; // ID único (índice)
        AlgorithmRobot(int loc, int id) {
            this.startLocation = loc;
            this.id = id;
        }
    }
    
    // --- Memoization (DP) Cache ---
    // memo[robot_index][store_mask] = max_profit
    private HashMap<Long, Long> memo;

    /**
     * Constructor para SilkRoadContest.
     */
    public SilkRoadContest() {
        // Constructor vacío
    }

    /**
     * REQUISITO 14: Soluciona el problema de la maratón (Problem J).
     * @param days El arreglo de días (formato ICPC).
     * @return int[] Un arreglo con la ganancia máxima para cada día.
     */
    public int[] solve(int[][] days) {
        int numDays = days.length;
        int[] results = new int[numDays];
        
        List<AlgorithmRobot> robots = new ArrayList<>();
        List<AlgorithmStore> stores = new ArrayList<>();

        for (int i = 0; i < numDays; i++) {
            
            // 1. Resupply de tiendas
            for (AlgorithmStore s : stores) {
                s.resupply();
            }
            
            // 2. Añadir el nuevo elemento del día
            int type = days[i][0];
            int location = days[i][1];
            
            if (type == 1) { // Añadir Robot
                robots.add(new AlgorithmRobot(location, robots.size()));
            } else { // Añadir Tienda
                int tenges = days[i][2];
                stores.add(new AlgorithmStore(location, tenges, stores.size()));
            }
            
            // 3. Calcular la ganancia máxima para el estado actual
            this.memo = new HashMap<>();
            
            // Hacemos el cast a 'int' al final
            long profit = calculateMaxProfit(robots, stores, 0, (1L << stores.size()) - 1);
            results[i] = (int) profit;
        }
        
        return results;
    }

    /**
     * HELPER PARA SOLVE() - Algoritmo de Programación Dinámica (DP)
     * Resuelve el problema de asignación de tiendas a robots.
     *
     * @param robots Lista de todos los robots.
     * @param stores Lista de todas las tiendas.
     * @param robotIndex El robot que estamos considerando (para la recursión).
     * @param storeMask Un bitmask de las tiendas que QUEDAN por visitar.
     * @return La máxima ganancia posible con esta configuración.
     */
    private long calculateMaxProfit(List<AlgorithmRobot> robots, List<AlgorithmStore> stores, 
                                    int robotIndex, long storeMask) {
        
        // --- Base Case ---
        if (robotIndex == robots.size()) {
            return 0;
        }

        // --- Memoization ---
        long state = ((long) robotIndex << stores.size()) | storeMask;
        if (memo.containsKey(state)) {
            return memo.get(state);
        }

        // --- Decisión 1: Este robot no visita ninguna tienda ---
        long maxProfit = calculateMaxProfit(robots, stores, robotIndex + 1, storeMask);

        // --- Decisión 2: Este robot visita *algun* subconjunto de tiendas ---
        AlgorithmRobot robot = robots.get(robotIndex);
        
        for (long subMask = storeMask; subMask > 0; subMask = (subMask - 1) & storeMask) {
            
            long pathProfit = solveForOneRobot(robot, stores, subMask);

            if (pathProfit > 0) {
                long remainingMask = storeMask & (~subMask);
                long otherRobotsProfit = calculateMaxProfit(robots, stores, robotIndex + 1, remainingMask);
                
                maxProfit = Math.max(maxProfit, pathProfit + otherRobotsProfit);
            }
        }

        memo.put(state, maxProfit);
        return maxProfit;
    }

    /**
     * Sub-problema DP: Encuentra la mejor ruta para UN robot
     */
    private long solveForOneRobot(AlgorithmRobot robot, List<AlgorithmStore> allStores, long subMask) {
        
        List<AlgorithmStore> pathStores = new ArrayList<>();
        for (int i = 0; i < allStores.size(); i++) {
            if (((subMask >> i) & 1) == 1) {
                pathStores.add(allStores.get(i));
            }
        }
        
        if (pathStores.isEmpty()) {
            return 0;
        }

        // 2. Ordenarlas por ubicación para la DP
        Collections.sort(pathStores); // <-- ESTA LÍNEA AHORA FUNCIONA
        int M = pathStores.size();
        
        long[] dp = new long[M];
        long maxPathProfit = 0;

        for (int i = 0; i < M; i++) {
            AlgorithmStore currentStore = pathStores.get(i);
            
            long profitFromStart = currentStore.currentTenges - Math.abs(currentStore.location - robot.startLocation);
            dp[i] = profitFromStart;

            for (int j = 0; j < i; j++) {
                AlgorithmStore prevStore = pathStores.get(j);
                long profitFromPrev = dp[j] + currentStore.currentTenges - Math.abs(currentStore.location - prevStore.location);
                dp[i] = Math.max(dp[i], profitFromPrev);
            }
            
            maxPathProfit = Math.max(maxPathProfit, dp[i]);
        }

        return maxPathProfit;
    }


    /**
     * REQUISITO 15: Simula la solución (usando la lógica 'greedy' del C2).
     */
    public void simulate(int[][] days, boolean slow) { 
        System.out.println("Iniciando simulación...");
        
        int maxLength = 0;
        for (int[] day : days) {
            if (day[1] > maxLength) maxLength = day[1];
        }
        
        SilkRoad simulator = new SilkRoad(maxLength + 10); 
        // Canvas.getCanvas().restart(); // <-- SOLUCIÓN AL ERROR 2 (Línea eliminada)

        for (int i = 0; i < days.length; i++) {
            
            simulator.reboot(); 
            
            System.out.println("--- SIMULANDO DÍA " + (i + 1) + " ---");
            
            int type = days[i][0];
            int location = days[i][1];
            
            if (type == 1) { // Añadir Robot
                System.out.println("Añadiendo Robot en " + location);
                simulator.placeRobot(location);
            } else { // Añadir Tienda
                int tenges = days[i][2];
                System.out.println("Añadiendo Tienda en " + location + " con " + tenges + " tenges");
                simulator.placeStore(location, tenges);
            }
            
            simulator.makeVisible();
            if(slow) Canvas.getCanvas().wait(1000);

            // 3. Simular los movimientos (Usando la lógica 'greedy' de moveRobots)
            boolean robotsAreMoving = true;
            int step = 1;
            while(robotsAreMoving) {
                int[][] robotsBefore = simulator.robots();
                simulator.moveRobots(); 
                int[][] robotsAfter = simulator.robots();
                
                robotsAreMoving = false;
                if (robotsBefore.length > 0 && robotsBefore.length == robotsAfter.length) {
                     for(int j=0; j<robotsBefore.length; j++) {
                        if (robotsBefore[j][0] != robotsAfter[j][0]) { 
                            robotsAreMoving = true; 
                            System.out.println("  Día " + (i+1) + ", Movimiento " + step++);
                            break;
                        }
                    }
                }
                if(slow && robotsAreMoving) Canvas.getCanvas().wait(500);
            }
            
            System.out.println("--- FIN DÍA " + (i + 1) + ". Ganancia (simulada): " + simulator.porfit() + " ---");
            if(slow) Canvas.getCanvas().wait(2000); 
            
            simulator.makeInvisible(); 
        }
        
        simulator.finish(); 
        System.out.println("Simulación terminada.");
    }
}