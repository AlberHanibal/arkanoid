package figuras;

import figuras.base.Animable;
import figuras.base.Eliminable;
import figuras.base.Sprite;

/**
 *
 * @author Alberto
 */
public class Mejora extends Sprite implements Animable, Eliminable {

    private boolean eliminar;
    private int incrY;
    private Breakout breakout;
    
    public Mejora(String skin, int x, int y, Breakout breakout) {
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
