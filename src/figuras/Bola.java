package figuras;

import audio.StdSound;
import figuras.base.Animable;
import figuras.base.Eliminable;
import figuras.base.Sprite;
import java.util.Iterator;
import java.util.List;
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
    private List<Bloque> bloques;
    
    public Bola (GameLogic logic) {
        super(new String[] {
            "assets/img/ball.png"
            // añadir bola roja para invencible
        });
        Random random = new Random();
        incrX = random.nextInt(20) - 10;
        incrY = - 7;
        modoInvencible = false;
        eliminar = false;
        breakout = logic.getBreakout();
        bloques = logic.getListaBloques();
        setX(300);
        setY(500);
    }
    
    @Override
    public void mover() {
        int incrXIni = incrX;
        int incrYIni = incrY;
        boolean colisionConBloque = false;
        setX(getX() + incrX);
        setY(getY() + incrY);
        // margenes
        if (getX() < MARGEN_IZQ + 13 || getX() > MARGEN_DCHO - 13) {
                incrX = - incrX;
                StdSound.play("assets\\audio\\ding.au");
            } else if (getY() < MARGEN_SUP + 40 ) {
                incrY = - incrY;
                StdSound.play("assets\\audio\\ding.au");
        } else if (getY() > MARGEN_INF) {
            setEliminar(true);
        }
        // chocar con breakout
        if (this.colisonaCon(breakout)) {
            incrY = - incrY;
            StdSound.play("assets\\audio\\ding.au");
            Random random = new Random();
            incrX = incrX + (random.nextInt(4) - 2);
        }
        // chocar con bloques
        Iterator<Bloque> iter = bloques.iterator();
        while (iter.hasNext()) {
            Bloque brick = iter.next();
            if (this.colisonaCon(brick)) {
                incrY = - incrY;
                StdSound.play("assets\\audio\\ding.au");
                Random random = new Random();
                incrX = incrX + (random.nextInt(2) - 1);
                brick.restarVida();
                if (brick.getVida() == 0) {
                    brick.setEliminar(true);
                    iter.remove();
                    // soltar mejora
                    // sumar puntos
                    
                }
                colisionConBloque = true;
            }
        }
        // con mejora roja
        if (modoInvencible && colisionConBloque) {
            incrX = incrXIni;
            incrY = incrYIni;
        }
    }

    @Override
    public boolean estaEliminado() {
        return eliminar;
    }

    public void setModoInvencible(boolean modoInvencible) {
        // cambiar skin a roja
        this.modoInvencible = modoInvencible;
    }

    public void setEliminar(boolean eliminar) {
        this.eliminar = eliminar;
    }

    public boolean isModoInvencible() {
        return modoInvencible;
    }
    
}
