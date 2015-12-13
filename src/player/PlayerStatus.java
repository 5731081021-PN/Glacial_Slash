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
import java.awt.image.BufferedImage;
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
	private List<SkillCard> hand, originalHand;
	private GameMap currentMap;
	private transient PlayerCharacter playerCharacter;
	private Point currentPosition;
	private String saveLocation;
	private transient SkillCard using;
	private transient Thread removeCardFromHandThread;
	
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
		currentPosition.setLocation(playerCharacter.getX(), playerCharacter.getY() + Resource.standSprite[0].getHeight());
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
		originalHand = new ArrayList<>();
		hand = new ArrayList<>();
//		currentMap = new TutorialMap();
		currentMap = GameMap.getGameMap("map9to12");
		currentPosition = currentMap.getInitialPosition();
		playerCharacter = new PlayerCharacter();
		playerCharacter.setPosition(currentPosition);
		
		// Debug

		originalHand.add(SkillCard.createSkillCard("Sky Uppercut"));
		originalHand.add(SkillCard.createSkillCard("Double Jump"));
		originalHand.add(SkillCard.createSkillCard("Glacial Drift"));
		originalHand.add(SkillCard.createSkillCard("Ice Summon"));
		originalHand.add(SkillCard.createSkillCard("Ice Summon"));
		originalHand.add(SkillCard.createSkillCard("Ice Summon"));
		originalHand.add(SkillCard.createSkillCard("Concentration 2 S D"));
		Collections.sort(originalHand);
		hand.addAll(originalHand);

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
	
	public void chargeMana() {
		maxMana++;
		currentMana++;
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
			using = hand.get(hand.indexOf(used));
			if (currentMana >= using.cost) {
				using.activate();
//				currentMana -= using.cost;
				removeCardFromHandThread = new Thread(new Runnable() {
					
					@Override
					public void run() {
						synchronized (using.getActivateAnimationThread()) {
							try {
								using.getActivateAnimationThread().wait();
							} catch (InterruptedException e) {
								return;
							}
							synchronized (hand) {
								hand.remove(using);
							}
						}
					}
				});
				removeCardFromHandThread.start();
			}
			else throw new SkillCardUnusableException(SkillCardUnusableException.UnusableType.NOT_ENOUGH_MANA);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new SkillCardUnusableException(SkillCardUnusableException.UnusableType.NO_SUCH_CARD_IN_HAND);
		}
	}
	
	public GameMap getCurrentMap() {
		return currentMap;
	}

	public void returnToLastCheckpoint() {
		removeCardFromHandThread.interrupt();
		if (using instanceof IceSummon) {
			((IceSummon) using).getIceSummonThread().interrupt();
		}
		else if (using instanceof Concentration) {
			((Concentration) using).getConcentrationThread().interrupt();
		}
		currentMap.clearIceTiles();
		hand.clear();
		hand.addAll(originalHand);
		playerCharacter.setSprite(Resource.standSprite[(playerCharacter.getFacingDirection()+1)/2]);
		playerCharacter.stopAllMotion();
		playerCharacter.setPosition(currentPosition);
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
