/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

import characters.*;
import data_level.DatosNivel;
import java.util.ArrayList;
import location.Punto;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.tiled.*;

/**
 *
 * @author Senapi Aroal
 */
public class Nivel1 extends BasicGameState{

    //Arraylist donde guardamos todas las escenas de ese nivel
    private final ArrayList<Escena> escenas = new ArrayList<>(); 
    
    //Velocidad
    private final float VELOCIDAD = 100.0f; 
    
    //Variable para obtener las funciones del teclado
    private Input entrada;
    
    //Variable que representa al jugador
    private Jugador j; 
    
    //Variable que indica cuantas escenas tiene este nivel
    private final int numEscenas = 3;  
    
    //Cantidad de objetos que tendrá cada escena
    private final int[] numObjetos = {2,0,0}; 
    
    //Cantidad de enemigos que tendrá cada escena
    private final int[] numEnemigos = {1,0,2};
    
    //Variable para extraer toda la información acerca del nivel especificado
    private final DatosNivel datos;

    //Reloj para controlar movimiento
    private int reloj;
    
    /**
     * Constructor de la clase Nivel1
     * 
     */   
    public Nivel1(){
        datos = new DatosNivel(numEscenas,numObjetos,numEnemigos);
        datos.datosNivel1();
        reloj = 0;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
           j = new Jugador(200,datos.getRespawn(0),new SpriteSheet("./res/Character1.png",48,72),VELOCIDAD,0,50);
           j.getHud().iniciarJugador();
           for(int i = 0;i<numEscenas;i++){ 
               Escena es = new Escena(new TiledMap("./map/level1/test_escena"+(i+1)+".tmx","map/level1"),datos.mapasNivel(i),datos.objetosNivel(i),datos.entradasNivel(i),datos.salidasNivel(i),datos.enemigosNivel(i));
               escenas.add(es);
           }
           entrada = container.getInput(); 
           
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        escenas.get(j.getEscenario()).getMapa_escena().render(0,0);
        j.getHud().imprimirCorazones();
        /*for(int i = 0;i<escenas.get(j.getEscenario()).getMapa_objetos().size();i++){
            g.draw(escenas.get(j.getEscenario()).getMapa_objetos().get(i));
        }*/
        for(int i = 0;i<escenas.get(j.getEscenario()).getEnemigos().size();i++){
            g.draw(escenas.get(j.getEscenario()).getEnemigos().get(i).getRango());
            g.draw(escenas.get(j.getEscenario()).getEnemigos().get(i).getPersDown());
            g.draw(escenas.get(j.getEscenario()).getEnemigos().get(i).getPersL());
            g.draw(escenas.get(j.getEscenario()).getEnemigos().get(i).getPersR());
            g.draw(escenas.get(j.getEscenario()).getEnemigos().get(i).getPersUp());
        }
        /*g.draw(escenas.get(j.getEscenario()).getArea_entrada());
        g.draw(escenas.get(j.getEscenario()).getArea_salida());
        g.draw(escenas.get(j.getEscenario()).getMapa_colision());*/
        j.getArco().getFlecha().draw(g);
        g.draw(j.getPersL());
        g.draw(j.getPersR());
        g.draw(j.getPersUp());
        g.draw(j.getPersDown());
        g.drawString("escenario " + j.getEscenario(),20,20);
        g.drawString("municion " + j.getArco().getMunicion(),20,40);
        g.drawString("Número enemigos: " + datos.enemigosNivel(j.getEscenario()).size(),20,60);
        for(int i = 0;i<escenas.get(j.getEscenario()).getEnemigos().size();i++){
            g.drawString("Velocidad: "+escenas.get(j.getEscenario()).getEnemigos().get(i).getVelocidad(),20,80+(20*i));
        }
        for(int i = 0;i<escenas.get(j.getEscenario()).getEnemigos().size();i++){
            g.drawString("Vida: "+escenas.get(j.getEscenario()).getEnemigos().get(i).getHp(),20,120+(20*i));
        }
        g.drawString("Vida del jugador: " + j.getHp(),20,160);
        
    }
    
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        reloj+= delta;
        j.gestionarJugador(container, game, numEscenas, delta, entrada, datos, escenas);
        for(int i = 0;i<escenas.get(j.getEscenario()).getEnemigos().size();i++){
            escenas.get(j.getEscenario()).getEnemigos().get(i).realizarMovimiento(j, escenas.get(j.getEscenario()), delta, reloj);
        }
        if(reloj >2000){
            reloj = 0;
        }
    }
    
    @Override
    public void mouseClicked(int button, int x, int y, int clickCount){
            System.out.println("Eje x: " +x + " Eje y: " + y);
    }
       
    @Override
    public int getID() {
        return 1;
    }
       
}