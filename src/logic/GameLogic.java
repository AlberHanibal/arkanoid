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
import figuras.Mejora;
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
    private List<Bola> listaBolas;
    private List<Bloque> listaBloques;
    private Mejora mejoraActiva;
    private int nivel;
    
    public GameLogic() {
        listaObjetosDibujables = new LinkedList<>();
        listaBloques = new LinkedList<>();
        listaBolas = new LinkedList<>();
        empezar();
    }
    /**
     * Invocado en cada fotograma desde el frame
     * @param g 
     */
    public void dibujarYActualizarTodo(Graphics g) {
        if (mejoraActiva != null && !listaObjetosDibujables.contains(mejoraActiva)) {
            listaObjetosDibujables.add(mejoraActiva);
        }
        Iterator<Dibujable> iter = listaObjetosDibujables.iterator();
        while (true) {
            if (!iter.hasNext()) { // Si no hay siguiente, salir del bucle
                break;
            }
            Dibujable objetoDelJuego = iter.next(); // Acceder al objeto
            if (objetoDelJuego instanceof Eliminable) { // Si está eliminado lo quitamos
                if (((Eliminable) objetoDelJuego).estaEliminado()) {
                    iter.remove();
                    if (objetoDelJuego instanceof Bola) {
                        listaBolas.remove(objetoDelJuego);
                    }
                    continue;
                }
            }
            objetoDelJuego.dibujar(g); // lo dibujamos
            if (objetoDelJuego instanceof Animable) { // Y si está auto-animado, lo movemos
                ((Animable) objetoDelJuego).mover();
            }
        }
        if (listaBolas.isEmpty()) {
            setVidas(getVidas() - 1);
            continuar();
        }
        if (listaBloques.isEmpty()) {
            listaObjetosDibujables.clear();
            listaBolas.clear();
            nivel = nivel + 1;
            inicializarNivel();
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
        // Inicia el juego!
        nivel = 0;
        puntos = 0;
        inicializarNivel();
    }
    
    public void continuar() {
        if (vidas == 0) {
            StdSound.playMidi(("assets/audio/game_over.mid"));
            listaObjetosDibujables.clear();
        } else if (vidas > 0) {
            Bola bola = new Bola(this);
            listaBolas.add(bola);
            listaObjetosDibujables.add(bola);      
        }
    }
        
    public void inicializarNivel() {
        listaObjetosDibujables.clear();
        if (nivel == 0) {
            StdSound.playMidi(("assets/audio/start_level.mid"));
        } else {
            StdSound.playMidi(("assets/audio/start_level2.mid"));
        }
        vidas = NUM_VIDAS;
        // TODO
        listaObjetosDibujables.add(new Fondo("assets/img/fondo00.jpg"));
        listaObjetosDibujables.add(new Marcador(this)); // inyección de dependencias
        breakout = new Breakout(this); // inyección de dependencias
        listaObjetosDibujables.add(breakout);
        Bola bola = new Bola(this);
        bola.setModoInvencible(true);
        listaBolas.add(bola);
        listaObjetosDibujables.add(bola);
        if (nivel == 0) {
            inicializarBloques(listaBloques, MapaNivel.mapa, 0);
        } else {
            inicializarBloques(listaBloques, MapaNivel.mapa, 1);
        }
        listaObjetosDibujables.addAll(listaBloques);
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

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public Breakout getBreakout() {
        return breakout;
    }

    public void setBreakout(Breakout breakout) {
        this.breakout = breakout;
    }

    public Mejora getMejoraActiva() {
        return mejoraActiva;
    }

    public List<Bloque> getListaBloques() {
        return listaBloques;
    }

    public List<Dibujable> getListaObjetosDibujables() {
        return listaObjetosDibujables;
    }
    
}
