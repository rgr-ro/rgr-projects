import java.awt.*;

public enum Figure_Color {
    WHITE(Color.WHITE),BLACK(Color.BLACK);

    private Color color;

    Figure_Color(Color color) {
        this.color = color;
    }
    public java.awt.Color getColor​(){
        return this.color;
    }

    public static java.awt.Color getColor​(int valor){
        Figure_Color[] colores = Figure_Color.values();
        return colores[valor].getColor​();
    }
    public int	getValue​(){
        return this.ordinal();
    }
}
