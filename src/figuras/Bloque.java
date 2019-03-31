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
    
    public Bloque (GameLogic logic, String skin) {
        super(new String[] {"assets/img/" + skin});
        if (skin.equals("hard.png")) {
            vida = 2;
        } else {
            vida = 1;
        }
        eliminar = false;
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
