package figuras.base;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public abstract class Sprite implements Dibujable {

    private BufferedImage[] skin;
    private int selectedSkin;
    public int x;
    public int y;
    public int width;
    public int height;

    private boolean skinAutoRotation;
    private int skinIni;
    private int skinEnd;
    private int skinFrames;
    private int skinFrameCountDown;

    /**
     * Inicia el sprite con las pieles indicadas. La anchura y altura se fija
     * automáticamente a la primera piel
     *
     * @param filename Array con los nombres de los ficheros de imagen
     */
    public Sprite(String[] filename) {
        skin = new BufferedImage[filename.length];
        if (filename.length < 1) {
            throw new IllegalArgumentException("Insuficiente cantidad de nombres de fichero");
        }
        for (int i = 0; i < filename.length; i++) {
            try {

                skin[i] = ImageIO.read(new File(filename[i]));

            } catch (IOException ex) {
                throw new IllegalArgumentException("No se pudo cargar fichero" + filename[i]);
            }
        }
        selectedSkin = 0;
        width = skin[selectedSkin].getWidth();
        height = skin[selectedSkin].getHeight();
        skinAutoRotation = false;
    }
    
    /**
     * Inicializa el Sprite con las pieles indicadas, y fija x e y.
     * 
     * @param filenames Array con los nombres de ficheros
     * @param x Coordenada x
     * @param y Coordenada y
     */
    public Sprite(String[] filenames, int x, int y) {
        this(filenames); // Invoca al constructor de un solo parámetro
        this.x = x;
        this.y = y;
    }

    /**
     * Fija la piel número i
     * @param i El índice de la piel.. entre 0, y la cantidad de pieles - 1
     */
    public void setSkin(int i) {
        if (i < 0 || i >= skin.length) {
            throw new IllegalArgumentException("No existe piel indice " + i);
        }
        selectedSkin = i;
    }

    /**
     * Dibuja el sprite.
     * @param g Un contexto gráfico.
     */
    public void dibujar(Graphics g) {
        g.drawImage(skin[selectedSkin], x, y, width, height, null);
    }

    /**
     * Inicia la rotación de skins.
     * Se indica el índice del skin inicial, y el índice final (excluido), así
     * como el número de frames. 
     * ej: <code>skinStartRotation(2,5,3)</code>, rota las pieles 2,3,4,2,3,4...
     * cada tres frames.
     * 
     * En cada frame que se desea rotación se debe invocar a skinRotate()
     *
     * @param ini Indice de piel inicial
     * @param end índice de piel final (excluido)
     * @param frames Cantidad de frames entre cambio de pieles. >= 1
     */
    public void skinStartRotation(int ini, int end, int frames) {
        if (ini < 0 || ini > skin.length || end < 0 || end > skin.length || frames < 1) {
            throw new IllegalArgumentException();
        }
        skinAutoRotation = true;
        skinIni = ini;
        skinEnd = end;
        skinFrames = frames;
        skinFrameCountDown = frames;
        selectedSkin = ini;
    }

    /**
     * Detiene la rotación
     */
    public void skinStopRotation() {
        skinAutoRotation = false;
    }

    /**
     * Indica que se está dibujando un fotograma. Hace que el sprite sea
     * avisado para rotar la piel si procede.
     */
    public void skinRotate() {
        if (!skinAutoRotation) {
            return;
        }
        skinFrameCountDown--;
        if (skinFrameCountDown == 0) {
            skinFrameCountDown = skinFrames;
            int skinCount = skinEnd - skinIni;
            // suma 1 modular
            selectedSkin = (selectedSkin - skinIni + 1) % skinCount + skinIni;
        }
    }
    
    /**
     * Comrpueba si <strong>éste sprite</strong> colisiona con otro. Utiliza
     * colisión rectangular.
     * @param s el otro sprite.
     * @return true si los rectángulos que enmarcan a los sprites se intersectan
     */
    public boolean colisonaCon(Sprite s) {
        boolean result = 
                new Rectangle(x, y, width, height).intersects(
                new Rectangle(s.getX(), s.getY(), s.getWidth(), s.getHeight())
        );
        return result;
    }

    // ----------------- ACCESORES Básicos.
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getSelectedSkin() {
        return selectedSkin;
    }

    public boolean isSkinAutoRotation() {
        return skinAutoRotation;
    }

    
    // Los setters son protegidos para evitar su uso desde el exterior.
    // Sólo este propio objeto y sus descendientes pueden usarlos.
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    protected void setWidth(int width) {
        this.width = width;
    }

    protected void setHeight(int height) {
        this.height = height;
    }

}
