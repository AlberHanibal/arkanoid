/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package figuras;

import figuras.base.Dibujable;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import logic.GameLogic;

/**
 * Dibuja las vidas y los puntos, con sombreado
 */
public class Marcador implements Dibujable {

    GameLogic logica;
    private static final int X = 400;
    private static final int Y = 50;
    private Font font;

    /**
     * Este constructor sigue el patrón de la inyección de dependencias. Como
     * necesita saber los puntos y las vidas que están en el objeto de lógica,
     * lo inyectamos como parámetro al constructor.
     *
     * @param logica
     */
    public Marcador(GameLogic logica) {
        this.logica = logica;
        font = new Font("Tahoma", Font.PLAIN, 12);
    }

    @Override
    public void dibujar(Graphics g) {

        String cadenaVidas = "";
        for (int i = 0; i < logica.getVidas(); i++) {
            cadenaVidas += "#";
        }
        String msg = String.format("%3s/%05d", cadenaVidas, logica.getPuntos());

        // la sombra, en gris
        g.setFont(font);       
        g.setColor(Color.RED);
        g.drawString(msg, X+1, Y+1);
        g.setColor(Color.YELLOW);
        g.drawString(msg, X, Y);
        if (logica.getVidas() == 0) {
            g.setColor(Color.red);          
            g.drawString("GAME OVER", 200, 350);
        }
        
        
    }

}
