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
    private GameLogic logica;
    private List<Mejora> mejoras;
    
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
        logica = logic;
        mejoras = logic.getMejoras();
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
                    random = new Random();
                    if (random.nextInt(7) == 1) {
                        Mejora m = generarMejora(brick.getX(), brick.getY());
                        mejoras.add(m);
                        logica.getListaObjetosDibujables().add(m);
                    }
                    iter.remove();
                    logica.setPuntos(logica.getPuntos() + brick.getPuntuacion());
                    
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

    private Mejora generarMejora(int x, int y) {
        Random random = new Random();
        int n = random.nextInt(2);
        String skin = "special_red.png";
        if (n == 0) {
            skin = "special_blue.png";
        } else if (n == 1) {
            skin = "special_green.png";
        }
        Mejora m = new Mejora(skin, x, y, breakout);
        return m;
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
