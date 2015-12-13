/*
 * The physical player playing the game
 * Shows the status on the screen, such as mana and hand
 */

package player;

import java.util.List;

import javax.swing.JOptionPane;

import exception.SkillCardUnusableException;
import exception.UnableToLoadGameException;
import map.GameMap;
import map.TutorialMap;

import java.awt.Graphics2D;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import render.Renderable;
import res.Resource;

public class PlayerStatus implements Renderable, Serializable {
	
	private static PlayerStatus player;
	private int currentMana, maxMana;
	private List<SkillCard> hand;
	private GameMap currentMap;
	private transient PlayerCharacter playerCharacter;
	private Point currentPosition;
	private String saveLocation;
	
	public static synchronized PlayerStatus newPlayer(String saveLocation) {
		player = new PlayerStatus();
		player.saveLocation = saveLocation;
		player.savePlayer();
		return player;
	}
	
	public static synchronized void loadPlayer(String saveLocation) throws UnableToLoadGameException {
		try (FileInputStream fileIn = new FileInputStream(saveLocation)) {
			try (ObjectInputStream in = new ObjectInputStream(fileIn)) {
				player = (PlayerStatus)in.readObject();
			} catch (ClassNotFoundException e1) {
				JOptionPane.showMessageDialog(null, "Unable to load the game", "Error", JOptionPane.ERROR_MESSAGE);
				throw new UnableToLoadGameException();
			} catch (IOException e2) {
				JOptionPane.showMessageDialog(null, "Unable to load the game", "Error", JOptionPane.ERROR_MESSAGE);
				throw new UnableToLoadGameException();
			}
		} catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(null, "File is not found", "Error", JOptionPane.ERROR_MESSAGE);
			throw new UnableToLoadGameException();
		} catch (IOException e2) {
			JOptionPane.showMessageDialog(null, "Unable to load the game", "Error", JOptionPane.ERROR_MESSAGE);
			throw new UnableToLoadGameException();
		}
		player.saveLocation = saveLocation;
	}
	
	public synchronized void savePlayer() {
		currentPosition.setLocation(playerCharacter.getX(), playerCharacter.getY());
		try (FileOutputStream fileOut = new FileOutputStream(saveLocation)){
			try (ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
				out.writeObject(player);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Unable to save the game", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(null, "File is not found", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e2) {
			JOptionPane.showMessageDialog(null, "Unable to save the game", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static synchronized PlayerStatus getPlayer() {
		return player;
	}

	private PlayerStatus() {
		// Mana debug
		maxMana = 10;
		currentMana = 10;
		hand = new ArrayList<SkillCard>();
//		currentMap = new TutorialMap();
		currentMap = new GameMap(Resource.map5to8);
		currentPosition = currentMap.getInitialPosition();
		playerCharacter = new PlayerCharacter();
		playerCharacter.setPosition(currentPosition);
		
		// Debug
		addCard(SkillCard.createSkillCard("Sky Uppercut"));
		addCard(SkillCard.createSkillCard("Double Jump"));
		addCard(SkillCard.createSkillCard("Glacial Drift"));
		addCard(SkillCard.createSkillCard("Ice Summon"));
		addCard(SkillCard.createSkillCard("Ice Summon"));
		addCard(SkillCard.createSkillCard("Ice Summon"));
		addCard(SkillCard.createSkillCard("Concentration 2 S D"));

	}
	
	public PlayerCharacter getPlayerCharacter() {
		return playerCharacter;
	}
	
	private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
		in.defaultReadObject();
		playerCharacter = new PlayerCharacter();
		playerCharacter.setPosition(currentPosition);
	}
	
	public int getCurrentMana() {
		return currentMana;
	}
	
	public int getMaxMana() {
		return maxMana;
	}
	
	public void chargeMana(SkillCard charged) {
		if (hand.remove(charged)) {
			maxMana++;
			currentMana++;
		}
	}
	
	public List<SkillCard> getHand() {
		return hand;
	}
	
	public void addCard(SkillCard skillCard) {
		hand.add(skillCard);
		Collections.sort(hand);
	}
	
	public void useCard(SkillCard used) throws SkillCardUnusableException {
		try {
			SkillCard using = hand.get(hand.indexOf(used));
			if (currentMana >= using.cost) {
				using.activate();
//				currentMana -= using.cost;
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						synchronized (using.getActivateAnimationThread()) {
							try {
								using.getActivateAnimationThread().wait();
							} catch (InterruptedException e) {}
//							hand.remove(using);
						}
					}
				}).start();
			}
			else throw new SkillCardUnusableException(SkillCardUnusableException.UnusableType.NOT_ENOUGH_MANA);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new SkillCardUnusableException(SkillCardUnusableException.UnusableType.NO_SUCH_CARD_IN_HAND);
		}
	}
	
	public GameMap getCurrentMap() {
		return currentMap;
	}

	@Override
	public void render(Graphics2D g) {
		// render mana
		g.drawImage(Resource.mana[currentMana], null, 20, 20);
		g.drawImage(Resource.slash, null, 80, 20);
		g.drawImage(Resource.maxMana[maxMana], null, 130, 50);
		
		// render cards in hand
		synchronized (hand) {
			int n = hand.size();
			for (int i = 0; i < n; i++) {
				hand.get(i).render(g, i);
			}
		}
	}

}
