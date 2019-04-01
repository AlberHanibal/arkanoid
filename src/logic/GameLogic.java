/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import audio.StdSound;
import figuras.Bloque;
import figuras.Bola;
import figuras.Breakout;
import figuras.Fondo;
import figuras.Marcador;
import figuras.base.Animable;
import figuras.base.Dibujable;
import figuras.base.Eliminable;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.Set;

/**
 *
 * @author victor
 */
public class GameLogic {
    // CONSTANTES DEL JUEGO
    public static final int NUM_VIDAS = 3;
    // añade las constantes que estimes oportuno.
    
    private int vidas;
    private int puntos;
    
    /** Lista de todos los objetos del juego, para dibujar, automover y eliminar */
    private List<Dibujable> listaObjetosDibujables;
   
    // --- Los objetos de los que quieras tener una referencia
    private Breakout breakout;
    private Bola bola;
    // TODO Añadir la pelota, una colección con los ladrillos, etc..
    private List<Bloque> listaBloques;

    public GameLogic() {
        listaObjetosDibujables = new LinkedList<>();
        listaBloques = new LinkedList<>();
        empezar();
    }
    /**
     * Invocado en cada fotograma desde el frame
     * @param g 
     */
    public void dibujarYActualizarTodo(Graphics g) {    
        Iterator<Dibujable> iter = listaObjetosDibujables.iterator();
        while (true) {
            if (!iter.hasNext()) { // Si no hay siguiente, salir del bucle
                break;
            }
            Dibujable objetoDelJuego = iter.next(); // Acceder al objeto
            if (objetoDelJuego instanceof Eliminable) { // Si está eliminado lo quitamos
                if (((Eliminable) objetoDelJuego).estaEliminado()) {
                    iter.remove();
                    continue;
                }
            }
            objetoDelJuego.dibujar(g); // lo dibujamos
            if (objetoDelJuego instanceof Animable) { // Y si está auto-animado, lo movemos
                ((Animable) objetoDelJuego).mover();
            }
        } 
    }
    
    /**
     * Invocado en cada fotograma desde el frame
     * @param teclas 
     */
    public void gestionarTeclas(Set<Integer> teclas) {
        if (teclas.contains(KeyEvent.VK_LEFT)) {
            breakout.moverIzquierda();
        }
        if (teclas.contains(KeyEvent.VK_RIGHT)) {
            breakout.moverDerecha();
        }
        
    }

    public List<Dibujable> getListaObjetos() {
        return listaObjetosDibujables;
    }
    
    public void empezar() {
        // TO-DO Inicia el juego!
        listaObjetosDibujables.clear();
        inicializarNivel(0);
    }
    
    public void inicializarNivel(int nivel) {
        // TO-DO
        if (nivel == 0) {
            StdSound.playMidi(("assets/audio/start_level.mid"));
            vidas = NUM_VIDAS;
            puntos = 0;
            // TODO
            listaObjetosDibujables.add(new Fondo("assets/img/fondo00.jpg"));
            listaObjetosDibujables.add(new Marcador(this)); // inyección de dependencias
            breakout = new Breakout(this); // inyección de dependencias
            listaObjetosDibujables.add(breakout);
            bola = new Bola(this);
//            bola.setModoInvencible(true);
            listaObjetosDibujables.add(bola);
            inicializarBloques(listaBloques, MapaNivel.mapa, nivel);
            listaObjetosDibujables.addAll(listaBloques);
            // TODO 
        }
        
        // TODO 
        
    }

    private void inicializarBloques(List<Bloque> bloques, String[][] mapa, int nivel) {
        int vertical = 80;
        char c;
        for (int fila = 0; fila < mapa[nivel].length; fila++) {
            int horizontal = 30;
            for (int i = 0; i < mapa[nivel][fila].length(); i++) {
                c = mapa[nivel][fila].charAt(i);
                if (c != '-') {
                    Bloque brick = new Bloque(this, charParaBloque(c));
                    brick.setX(horizontal);
                    brick.setY(vertical);
                    bloques.add(brick);    
                }
                horizontal = horizontal + 44;
                
            }
            vertical = vertical + 22;
        }
    }
    
    private String charParaBloque(char c) {
        if (c == 'b') {
            return "brick_blue.png";
        } else if (c == 'c') {
            return "brick_cyan.png";
        } else if (c == 'g') {
            return "brick_green.png";
        } else if (c == 'm') {
            return "brick_magenta.png";
        } else if (c == 'o') {
            return "brick_orange.png";
        } else if (c == 'r') {
            return "brick_red.png";
        } else if (c == 'y') {
            return "brick_yellow.png";
        } else if (c == 'h') {
            return "hard.png";
        } else {
            return "error";
//            throw new IllegalArgumentException();
        }
    }
    
    public int getVidas() {
        return vidas;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    public int getPuntos() {
        return puntos;
    }

    public Breakout getBreakout() {
        return breakout;
    }

    public void setBreakout(Breakout breakout) {
        this.breakout = breakout;
    }

    public Bola getBola() {
        return bola;
    }

    public List<Bloque> getListaBloques() {
        return listaBloques;
    }
    
}
