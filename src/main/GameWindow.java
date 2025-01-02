package main;

//Importamos las librerias y las constantes que vamos a usar en la clase GameWindow
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;

/**
 * Comienzo de la clase GameWindow
 * @author Santiago
 */
public class GameWindow {
	private JFrame jframe;

	/**
	 * Definimos el constructor de la clase GameWindow
	 * @param gamePanel es el gamePanel en el cual se dibuja todo el juego
	 */
	public GameWindow(GamePanel gamePanel) {

		jframe = new JFrame();

		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.add(gamePanel);
		
		jframe.setResizable(false);
		jframe.pack();
		jframe.setLocationRelativeTo(null);
		jframe.setVisible(true);
		jframe.addWindowFocusListener(new WindowFocusListener() {

			/**
			 * Definimos un metodo para hacer que la ventana pierda el enfoque cuando se sale de la ventana
			 * @param e es el evento de la ventana
			 */
			@Override
			public void windowLostFocus(WindowEvent e) {
				gamePanel.getGame().windowFocusLost();
			}

			/**
			 * Definimos un metodo para ganar el enfoque de la ventana, este metodo no se usa en el programa
			 * @param e este es el evento de la ventana
			 */
			@Override
			public void windowGainedFocus(WindowEvent e) {
				// TODO Auto-generated method stub

			}
		});

	}

}