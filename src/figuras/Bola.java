package figuras;

import audio.StdSound;
import figuras.base.Animable;
import figuras.base.Eliminable;
import figuras.base.Sprite;
import java.util.Random;
import logic.GameLogic;

/**
 *
 * @author Alber
 */
public class Bola extends Sprite implements Animable, Eliminable {

    private boolean modoInvencible;
    private boolean eliminar;
    private int incrX;
    private int incrY;
    private Breakout breakout;
    
    public Bola (GameLogic logic) {
        super(new String[] {
            "assets/img/ball.png"
            // añadir bola roja para invencible
        });
        Random random = new Random();
        incrX = random.nextInt(20) - 10;
        incrY = - 7;
//        incrX = 0;
//        incrY = 0;
        modoInvencible = false;
        eliminar = false;
        breakout = logic.getBreakout();
        setX(300);
        setY(500);
    }
    
    @Override
    public void mover() {
        setX(getX() + incrX);
        setY(getY() + incrY);
        if (getX() < MARGEN_IZQ + 13 || getX() > MARGEN_DCHO - 13) {
                incrX = - incrX;
                StdSound.play("assets\\audio\\ding.au");
            } else if (getY() < MARGEN_SUP + 40 ) {
                incrY = - incrY;
                StdSound.play("assets\\audio\\ding.au");
        } else if (getY() > MARGEN_INF) {
            setEliminar(true);
        }
        if (this.colisonaCon(breakout)) {
            incrY = - incrY;
            StdSound.play("assets\\audio\\ding.au");
            Random random = new Random();
            incrX = incrX + (random.nextInt(4) - 2);
        }
    }

    @Override
    public boolean estaEliminado() {
        return eliminar;
    }

    public void setModoInvencible(boolean modoInvencible) {
        this.modoInvencible = modoInvencible;
    }

    public void setEliminar(boolean eliminar) {
        this.eliminar = eliminar;
    }

    public boolean isModoInvencible() {
        return modoInvencible;
    }
    
}
