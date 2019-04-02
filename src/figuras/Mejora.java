package figuras;

import figuras.base.Animable;
import figuras.base.Eliminable;
import figuras.base.Sprite;
import logic.GameLogic;

/**
 *
 * @author Alberto
 */
public class Mejora extends Sprite implements Animable, Eliminable {

    private boolean eliminar;
    private int incrY;
    private Breakout breakout;
    private GameLogic logica;
    
    public Mejora(String skin, int x, int y, Breakout breakout, GameLogic logic) {
        super(new String[] {"assets/img/" + skin});
        eliminar = false;
        incrY = - 4;
        setX(x);
        setY(y);
        this.breakout = breakout;
    }

    @Override
    public void mover() {
        setY(getY() + incrY);
        if (this.colisonaCon(breakout)) {
            eliminar = true;
        }
    }

    @Override
    public boolean estaEliminado() {
        return eliminar;
    }
    
}
