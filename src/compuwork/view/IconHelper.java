package compuwork.view;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class IconHelper {

    /**
     * Carga un icono desde compuwork/resources/images/ y lo escala al tamaño indicado.
     * Si no encuentra el archivo, retorna null sin romper la app.
     */
    public static ImageIcon get(String nombre, int ancho, int alto) {
        try {
            URL url = IconHelper.class.getClassLoader()
                .getResource("compuwork/resources/images/" + nombre);
            if (url == null) return null;
            ImageIcon icon = new ImageIcon(url);
            Image scaled = icon.getImage()
                .getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        } catch (Exception e) {
            return null;
        }
    }

    // Tamaños predefinidos
    public static ImageIcon tab(String nombre)    { return get(nombre, 20, 20); }
    public static ImageIcon boton(String nombre)  { return get(nombre, 18, 18); }
    public static ImageIcon header(String nombre) { return get(nombre, 36, 36); }
}