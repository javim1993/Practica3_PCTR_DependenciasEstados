package src.p03.c01;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase ActividadSalidaParque
 *  
 * @author <a href="jmf1017@alu.ubu.es">Javier Muñoz</a>
 * @author <a href="jgh1006@alu.ubu.es">Josue Gabriel Granados</a>
 * @since 1.0
 * @version 1.0
 * 
 *          Clase ActividadSalidaPuerta que implementa la interfaz Runnable 
 *          para ejecutar thread con run
 */

public class ActividadSalidaPuerta implements Runnable{
	/** Numero máximo de salidas por puerta*/
	private static final int NUMSALIDAS = 100;
	/** String que indica la puerta de salida*/
	private String puerta;
	/** Objeto de la interfaz Iparque*/
	private IParque parque;
	
	/**
	 * Metodo Constructor de la clase
	 * 
	 * Se le pasan como parámetros:
	 * 
	 * @param puerta puerta por la que ejecuta el thread
	 * @param parque parque de la interfaz IParque
	 */
	public ActividadSalidaPuerta(String puerta, IParque parque) {
		this.puerta = puerta;
		this.parque = parque;
	}
	
	/**
	 * Metodo run sobreescrito de la interfaz runnable
	 */
	@Override
	public void run() {	
		for (int i = 0; i < NUMSALIDAS; i ++) {
			try {
				parque.salirDelParque(puerta);
				TimeUnit.MILLISECONDS.sleep(new Random().nextInt(5)*1000);
			} catch (InterruptedException e) {
				Logger.getGlobal().log(Level.INFO, "Salida interrumpida");
				Logger.getGlobal().log(Level.INFO, e.toString());
				return;
			}
		}
	}
}
