package com.me.fong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player extends BaseShip {

	public Player(SpriteBatch batch, Texture texture, float x, float y,
			EntityManager entityManager, boolean ignoreLighting) {
		super(batch, texture, y, y, entityManager, ignoreLighting, false);
		setSpeed(800);
		setPowerUp(EnumPowerUp.None);
		setIsPlayer(true);
	}

	@Override
	public void onTick(float delta) {
		fireProjectile();
		float oldPos = getX();
		
		if (Gdx.input.isTouched()
				&& Gdx.input.getY() > MyGame.screenHeight * 0.1f
						* MyGame.scaleY) {
			if (Gdx.input.getX() > getX() + getTexture().getWidth() * 0.5f
					* MyGame.scaleX + 10.0f * MyGame.scaleX) {
				setX(getX() + getSpeed() * delta * MyGame.scaleX);
			}

			else if (Gdx.input.getX() < getX() + getTexture().getWidth() * 0.5f
					* MyGame.scaleX - 10.0f * MyGame.scaleX) {
				setX(getX() - getSpeed() * delta * MyGame.scaleX);
			}
		} else {

			if (Gdx.input.isKeyPressed(Keys.RIGHT)
					|| Gdx.input.isKeyPressed(Keys.D)) {
				setX(getX() + getSpeed() * delta * MyGame.scaleX);
			}

			if (Gdx.input.isKeyPressed(Keys.LEFT)
					|| Gdx.input.isKeyPressed(Keys.A)) {
				setX(getX() - getSpeed() * delta * MyGame.scaleX);
			}
		}
		
		MyGame.backgroundStrafe -= (getX()-oldPos)*0.5f;

		if (getX() < 0)
			setX(0);
		if (getX() > MyGame.screenWidth - getTexture().getWidth()
				* MyGame.scaleX)
			setX(MyGame.screenWidth - getTexture().getWidth() * MyGame.scaleX);
	}

	@Override
	public void onCollision(Object o) {
		super.onCollision(o);
		if (o instanceof Projectile
				&& ((Projectile) o).getProjectileParent() != getIsPlayer()) {
			setAlive(false);
			dispose();
		}
		if (o instanceof PowerUpPickup){
			PowerUpPickup p = (PowerUpPickup)o;
			powerUp = p.getPowerUp();
			updatePowerUps();
		}
		if (o instanceof Ai || o instanceof Meteor) {
			dispose();
		}

	}

	@Override
	public void dispose() {
		super.dispose();
		getEntityManager().game.switchToScreen(GameState.GameOver);
	}
}
