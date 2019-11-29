package application;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * This class is responsible for creating and playing the sound 
 * effects of the game. It has a single media player as its 
 * instance variable.
 * 
 * @author JiayuZhu and Shari Sinclair
 *
 */
public class SoundEffect {
	private MediaPlayer mediaPlayer;
	
	/**
	 * This method adds the necessary music file to the media player 
	 * instance variable and sets the volume
	 * @param musicFile The string name of the music/sound file path
	 * @param volume The double of the volume to be set
	 */
	private MediaPlayer createSound(String musicFile, double volume) {
	    	Media sound = new Media(new File(musicFile).toURI().toString());
		mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.setVolume(volume);
		return mediaPlayer;
	}
	// Battle sound effects//
	/**
	 * This method plays the sword swing sound effect.
	 */
	public void swingSound() {
	    createSound("./src/swing2.wav", 0.1).play();
	}

	/**
	 * This method plays the sound effect when magic is used.
	 */
	public void magicSound() {
	    createSound("./src/fireball.mp3", 0.06).play();
	}
	
	/**
	 * This method plays a sound when the enemy is killed.
	 */
	public void enemyDeathSound() {
	    createSound("./src/enemyDeath.wav", 0.5).play();		
	}
	/**
	 * This method plays a sound when the hero is killed.
	 */
	public void heroDeathSound() {
	    createSound("./src/heroDeath2.wav", 0.5).play();		
	}
	
	/**
	 * This method plays a sound when the hero uses any types of the potion. 
	 */
	public void healSound() {
	    createSound("./src/healSound.wav", 0.5).play();		
	}
	
	
	// Shop sound effect//
	/**
	 * This method plays a sound when the player enters the shop. 
	 */
	public void shopSound() {
	    createSound("./src/shopWelcome.wav", 0.3).play();	
	}	

	
	/**
	 * This method plays a sound when the player buy or sell an item. 
	 */
	public void moneySound() {
	    createSound("./src/goldSound.wav", 0.3).play();	
	}
	
	// Event sound effect//
	/**
	 * This method creates the chest opening sound effect. 
	 */
	
	public void openChestSound() {
	    createSound("./src/chestOpening.wav", 0.8).play();	
	}
	
	// General sound effect//
	
	/**
	 * This method plays a sound when there is an error.
	 */
	public void errorSound() {
	    createSound("./src/error.wav", 0.6).play();	
	}
	/**
	 * Method allows us to play the same sound when buttons are pressed
	 */
	public void transitionSound() {
	    createSound("./src/startSound.wav", 0.1).play();
	}
	
	// Creating media players for songs//
	
	/**
	 * Method creates opening music media player
	 * 
	 * @return mediaPlayer music media player
	 */
	public MediaPlayer openingMusic() {
	    
	    return createSound( "./src/startMusic.wav", 0.2);
	}
	
	/**
	 * Method creates music media player for when the game is over
	 * 
	 * @return mediaPlayer  music media player
	 */
	public MediaPlayer gameOverMusic() {
	    return createSound("./src/gameoverMusic.wav", 0.2);
	}

	/**
	 * Method creates music media player for when you win the game
	 * 
	 * @return mediaPlayer  music media player
	 */
	public MediaPlayer youWinMusic() {
	    return createSound("./src/youWinSong.wav", 0.2);
	}
	
	/**
	 * Method creates background/battle music media player
	 * 
	 * @return mediaPlayer music media player
	 */
	public MediaPlayer backgroundMusic() {
	    return createSound("./src/fightmusiccut.mp3", 0.03);
	}

	
	
}
