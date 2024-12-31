package gamestates;

/**
 * Enumeracion de los posibles estados de juego que se puede tener
 * @author Santiago
 */
public enum GameState {
	
	PLAYING, MENU, OPTIONS, QUIT, CREDITS, PLAYER_SELECTION;

	public static GameState state = MENU;

}