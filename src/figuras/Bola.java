package figuras;

import audio.StdSound;
import figuras.base.Animable;
import figuras.base.Eliminable;
import figuras.base.Sprite;
import java.awt.Rectangle;
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
    private Mejora mejoraActiva;
    
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
        mejoraActiva = logic.getMejoraActiva();
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
//            if (this.colisonaCon(brick)) {
//                incrY = - incrY;
//                StdSound.play("assets\\audio\\ding.au");
//                Random random = new Random();
//                incrX = incrX + (random.nextInt(2) - 1);
//                brick.restarVida();
//                if (brick.getVida() == 0) {
//                    brick.setEliminar(true);
//                    random = new Random();
//                    //if (random.nextInt(7) == 1) {
//                        //Mejora m = generarMejora(brick.getX(), brick.getY());
//                        //logica.getListaObjetosDibujables().add(m);
//                    //}
//                    iter.remove();
//                    logica.setPuntos(logica.getPuntos() + brick.getPuntuacion());
//                    
//                }
//                colisionConBloque = true;
//            }
            if (this.colisionVertical(brick)) {
                incrY = - incrY;
                colisionConBloque = true;
            } else if (this.colisionHorizontal(brick)) {
                incrX = - incrX;
                colisionConBloque = true;
            }
            if (colisionConBloque) {
                StdSound.play("assets\\audio\\ding.au");
                Random random = new Random();
                incrX = incrX + (random.nextInt(2) - 1);
                brick.restarVida();
                if (brick.getVida() == 0) {
                    brick.setEliminar(true);
//                    random = new Random();
//                    if (random.nextInt(7) == 1) {
//                        Mejora m = generarMejora(brick.getX(), brick.getY());
//                        logica.getListaObjetosDibujables().add(m);
//                    }
                    iter.remove();
                    logica.setPuntos(logica.getPuntos() + brick.getPuntuacion());
                }
            }
        }
        // con mejora roja
        if (modoInvencible && colisionConBloque) {
            incrX = incrXIni;
            incrY = incrYIni;
        }
    }

    private boolean colisionHorizontal(Bloque brick) {
        Rectangle recBola = new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        if ( new Rectangle(brick.x, brick.y, 1, brick.height).intersects(recBola) || 
                new Rectangle(brick.x + brick.width, brick.y, 1, brick.height).intersects(recBola) ) {
            return true;
        } else {
            return false;
        }
//        if ((this.getX() == brick.arribaIzq.x && this.getY() > brick.arribaIzq.y && this.getY() <= brick.abajoIzq.y) 
//        || this.getX() == brick.arribaDcha.x && this.getY() > brick.arribaDcha.y && this.getY() <= brick.abajoDcha.y){
//            return true;
//        } else {
//            return false;
//        }
    }
    
    private boolean colisionVertical(Bloque brick) {
       Rectangle recBola = new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        if ( new Rectangle(brick.x, brick.y, brick.width, 1).intersects(recBola) || 
                new Rectangle(brick.x, brick.y + brick.height, brick.width, 1).intersects(recBola) ) {
            return true;
        } else {
            return false;
        }
//        this.getY() == brick.getY() && this.getX() > brick.getX() && this.getX() <= brick.getX() + brick.getWidth() ||
//                this.getY() == brick.getY() + brick.getHeight() && this.getX() > brick.getX() && this.getX() <= brick.getX() + brick.getWidth())
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
        Mejora m = new Mejora(skin, x, y, breakout, logica);
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
