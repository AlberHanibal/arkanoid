package figuras;

import figuras.base.Dibujable;
import figuras.base.Eliminable;
import figuras.base.Sprite;
import logic.GameLogic;

/**
 *
 * @author Alber
 */
public class Bloque extends Sprite implements Eliminable, Dibujable {

    private int vida;
    private boolean eliminar;
    private int puntuacion;
    
    public Bloque (GameLogic logic, String skin) {
        super(new String[] {"assets/img/" + skin});
        if (skin.equals("hard.png")) {
            vida = 2;
            puntuacion = 100;
        } else {
            vida = 1;
            puntuacion = 50;
        }
        eliminar = false;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }
    
    @Override
    public boolean estaEliminado() {
        return eliminar;
    }

    public int getVida() {
        return vida;
    }

    public void restarVida() {
        vida = vida - 1;
    }

    public void setEliminar(boolean eliminar) {
        this.eliminar = eliminar;
    }
    
}
