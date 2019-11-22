package application;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundEffect {
	private MediaPlayer mediaPlayer;

	
	/**
	 * This method plays the sword swing sound effect.
	 */
	public void swingSound() {
		String musicFile = "./src/swing2.wav";
		Media sound = new Media(new File(musicFile).toURI().toString());
		mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.play();
		mediaPlayer.setVolume(0.1);
	}

	/**
	 * This method plays the poof sound effect when magic is used.
	 */
	public void magicSound() {
		String musicFile = "./src/fireball.mp3";
		Media sound2 = new Media(new File(musicFile).toURI().toString());
		mediaPlayer = new MediaPlayer(sound2);
		mediaPlayer.play();
		mediaPlayer.setVolume(0.06);
	}
	
	/**
	 * This method plays a sound when the enemy is killed.
	 */
	public void enemyDeathSound() {
		String musicFile = "./src/enemyDeath.wav";
		Media sound3 = new Media(new File(musicFile).toURI().toString());
		mediaPlayer = new MediaPlayer(sound3);
		mediaPlayer.play();
		mediaPlayer.setVolume(0.3);		
	}
	
	/**
	 * This method plays a sound when the player buy or sell an item. 
	 */
	public void moneySound() {
		String musicFile = "./src/goldSound.wav";
		Media sound = new Media(new File(musicFile).toURI().toString());		
	
		MediaPlayer mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.play();
		mediaPlayer.setVolume(0.3);		
	}

}
