/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package figuras;

import figuras.base.Sprite;
import logic.GameLogic;

/**
 *
 * @author victor
 */
public class Breakout extends Sprite {
    
    public Breakout(GameLogic logic) {
        super(new String[]{
            "assets/img/breakout.png",
            "assets/img/breakout_big.png",
        });
        // cambiar y terminar
        setX(300);
        setY(570);
    }
    
    public void moverIzquierda() {
        setX(getX() - 10);
    }
    
    public void moverDerecha() {
        setX(getX() + 10);
    }
    
}
