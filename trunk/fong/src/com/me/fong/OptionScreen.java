package com.me.fong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class OptionScreen implements Screen {
	private MyGame game;
	private Shadable header;
	private TextButton backButton;
	private MenuButton toggleMusicButton;
	private MenuButton toggleSoundFxButton;
	private MenuButton toggleLightFxButton;
	private Table innerTable;
	private Label music;
	private Label soundFx;
	private Label lightFx;
	private Preferences prefs;

	public OptionScreen(MyGame myGame) {
		this.game = myGame;

		this.innerTable = new Table(game.skin);
		music = new Label("Music", game.mediumlabelStyle);
		soundFx = new Label("Sound", game.mediumlabelStyle);
		lightFx = new Label("Light", game.mediumlabelStyle);
		backButton = new MenuButton("Back", game.mediumButtonStyle,
				GameState.MainMenu, game);

		System.out.println("new OptionScreen created");
	}

	@Override
	public void render(float delta) {
		game.batch.begin();
		game.drawBackground(delta);
		update(delta);
		draw(delta);
		game.batch.end();
	}

	public void update(float delta) {
		if (Gdx.input.isKeyPressed(Keys.BACK)) {
			game.switchToScreen(GameState.MainMenu);
		}
		game.entityManager.tick(delta);
	}

	public void draw(float delta) {
		game.table.draw(game.batch, 1);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		this.header = new Shadable(game.batch, Assets.options,
				(MyGame.screenWidth * 0.5f)
						- (Assets.options.getWidth() * 0.5f * MyGame.scaleX),
				MyGame.screenHeight * 0.7f, game.entityManager, false);
		game.entityManager.addEntity(header);
		this.toggleMusicButton = new MenuButton("", game.mediumButtonStyle,
				MyGame.musicOn);
		this.toggleSoundFxButton = new MenuButton("", game.mediumButtonStyle,
				MyGame.soundOn);
		this.toggleLightFxButton = new MenuButton("", game.mediumButtonStyle,
				MyGame.lightOn);
		setupMenuLayout();
	}

	@Override
	public void hide() {
		game.entityManager.clearEntityList();
		saveOptionState();
		game.table.clearChildren();
		innerTable.clearChildren();
	}

	private void saveOptionState() {
		MyGame.musicOn = toggleMusicButton.getBoolean();
		MyGame.soundOn = toggleSoundFxButton.getBoolean();
		MyGame.lightOn = toggleLightFxButton.getBoolean();

		prefs = Gdx.app.getPreferences("FongSaveFile");
		prefs.putBoolean("musicOn", MyGame.musicOn);
		prefs.putBoolean("soundOn", MyGame.soundOn);
		prefs.putBoolean("lightOn", MyGame.lightOn);
		prefs.flush();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

	private void setupMenuLayout() {

		innerTable.add().row().padBottom(25.0f * MyGame.scaleY);
		innerTable.add(music).align(Align.left)
				.padRight(100.0f * MyGame.scaleX);
		innerTable.add(toggleMusicButton).align(Align.right).row()
				.padBottom(25.0f * MyGame.scaleY);

		innerTable.add(soundFx).align(Align.left)
				.padRight(100.0f * MyGame.scaleX);
		innerTable.add(toggleSoundFxButton).align(Align.right).row()
				.padBottom(25.0f * MyGame.scaleY);

		innerTable.add(lightFx).align(Align.left)
				.padRight(100.0f * MyGame.scaleX);
		innerTable.add(toggleLightFxButton).align(Align.center).row()
				.padBottom(25.0f * MyGame.scaleY);

		game.table.add(innerTable).row();
		game.table.add(backButton);

		game.table.padTop(header.getTexture().getHeight() * 1.5f * MyGame.scaleY);
	}
}
