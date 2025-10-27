import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;

/**
 * Pruebas de unidad para la clase SilkRoadContest.
 * Cumple con el Requisito de entrega del Ciclo 3. 
 */
public class SilkRoadContestTest
{
    /**
     * Constructor default
     */
    public SilkRoadContestTest()
    {
    }

    /**
     * Sets up the test fixture.
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }

    /**
     * Prueba el método solve() con el "Sample Input 1" del Problema J.
     * 
     */
    @Test
    public void shouldSolveSampleInput1()
    {
        SilkRoadContest contest = new SilkRoadContest();
        
        // "Sample Input 1" del PDF 
        // {tipo, loc} o {tipo, loc, tenges}
        int[][] days = {
            {1, 20},            // Día 1: Add R@20.
            {2, 15, 15},        // Día 2: Add S@(15,15)
            {2, 40, 50},        // Día 3: Add S@(40,50)
            {2, 80, 20},        // Día 4: Add S@(80,20)
            {1, 50},            // Día 5: Add R@50
            {2, 70, 30}         // Día 6: Add S@(70,30)
        };
        
        // "Sample Output 1" del PDF 
        int[] expectedProfits = {0, 10, 35, 35, 50, 60};
        
        // Ejecutar el método
        int[] calculatedProfits = contest.solve(days);
        
        // Imprimir para depuración
        System.out.println("Salida Esperada: " + Arrays.toString(expectedProfits));
        System.out.println("Salida Calculada: " + Arrays.toString(calculatedProfits));
        
        // Comparar
        assertArrayEquals(expectedProfits, calculatedProfits);
    }
    
    /**
     * Prueba que la simulación se ejecuta sin errores.
     * (Prueba de humo)
     */
    @Test
    public void shouldRunSimulationWithoutCrashing()
    {
        SilkRoadContest contest = new SilkRoadContest();
        
        int[][] days = {
            {1, 20},
            {2, 15, 15},
            {2, 40, 50}
        };
        
        // 'false' para que la prueba sea rápida
        contest.simulate(days, false);
        
        // Si la prueba termina sin excepciones, es un éxito.
        assertTrue(true);
    }
}