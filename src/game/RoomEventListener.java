package game;

/**
 * 
 * Interfaccia che definisce la gestione degli eventi della Room.
 * 
 * @author Micieli
 * @date 2015/04/25
 *
 */

public interface RoomEventListener {

	/**
	 * Metodo chiamato quando un nuovo giocatore entra nella stanza aggiorna
	 * tuti i giocatori del nuovo ingresso.
	 * 
	 * @param player
	 *            il giocatore nuovo entrato
	 */
	public void onNewPlayerAdded(Player player);

	/**
	 * Metodo chiamato quando la room è piena da il segnale di inizio gioco ai
	 * giocatori presenti.
	 */
	public void onRoomFull();

	/**
	 * Metodo chiamato qundo un gioctre esce dalla stanza, aggiorna tutti gli
	 * altri giocatori presenti dell'evento.
	 * 
	 * @param player
	 */
	public void onPlayerRemoved(Player player);

}
