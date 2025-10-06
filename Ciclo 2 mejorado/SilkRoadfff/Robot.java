public class Robot {
    private String nombre;
    private int startPos;
    private int currentPos;
    private String color;
    private Circle cuerpo;

    public Robot(String nombre, int pos, int tamCelda) {
        this.nombre = nombre;
        this.startPos = pos;
        this.currentPos = pos;
        cuerpo = new Circle();
        cuerpo.changeSize(tamCelda - 4); 
        cuerpo.changeColor("green");
        cuerpo.makeVisible();
    }

    public String getNombre() {
        return nombre;
    }

    public int getCurrentPos() {
        return currentPos;
    }

    public void moveTo(int x, int y) {
        cuerpo.setPosition(x + 2, y + 2); 
    }

    public void makeVisible() {
        cuerpo.makeVisible();
    }

    public void setTamaño(int tamaño) {
        cuerpo.changeSize(tamaño);
    }
}