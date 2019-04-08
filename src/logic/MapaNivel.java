/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

/**
 *
 * @author victor
 */
public class MapaNivel {
    // Los ladrillos como caracteres por comodidad
    // b=blue; c=cyan; g = green; m = magenta
    // o = orange, h = ladrillo duro
    // - = hueco
    public static final String[][] mapa = {
        // Nivel 0
        {
            "bbbb--bbbb",
            "gggghhgggg", 
            "mmmm--mmmm"

        },
        // Nivel 1
        {
            "ccoohhmmbb",
            "oocchhbbmm",
            "gggghhgggg",
            "bbbbhhbbbb"
        }
    };

}
