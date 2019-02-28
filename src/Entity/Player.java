package entity;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import io.Window;
import render.Animation;
import render.Camera;
import world.World;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

public class Player extends Entity {
	public static final int ANIM_IDLE = 0;
	public static final int ANIM_WALK = 1;
	public static final int ANIM_FIRE = 2;
	public static final int ANIM_SIZE = 3;
	Vector2f direction = new Vector2f(1,0);


	public int speed;
	public Player(Transform transform) {
		super(ANIM_SIZE, transform);
		setAnimation(ANIM_IDLE, new Animation(4, 1, "player/idle"));
		setAnimation(ANIM_WALK, new Animation(4, 1, "player/walking"));
		setAnimation(ANIM_FIRE, new Animation(4, 1, "player/walking"));
		speed = 15;
	}
	
	@Override
	public void update(float delta, Window window, Camera camera, World world) {
		Vector2f movement = new Vector2f();
		if (window.getInput().isKeyDown(GLFW.GLFW_KEY_A)) {
			movement.add(-speed * delta, 0);
			direction = new Vector2f(0,-1);
		}


		if (window.getInput().isKeyDown(GLFW.GLFW_KEY_D)) {
			movement.add(speed * delta, 0);
			direction= new Vector2f(-1,0);}

		if (window.getInput().isKeyDown(GLFW.GLFW_KEY_W)) {
			movement.add(0, speed * delta);
			direction= new Vector2f(1,0);}

		if (window.getInput().isKeyDown(GLFW.GLFW_KEY_S)) {
			movement.add(0, -speed * delta);
			direction= new Vector2f(0,-1);}

		move(movement);
		if(window.getInput().isKeyDown(GLFW_KEY_SPACE)){
			useAnimation(ANIM_FIRE);
			Transform t = new Transform();
			t.pos.x = transform.pos.x;
			t.pos.y = transform.pos.y;
			world.addEntity(t,direction);
		}else {
			if (movement.x != 0 || movement.y != 0)
				useAnimation(ANIM_WALK);
			else useAnimation(ANIM_IDLE);
		}
		camera.getPosition().lerp(transform.pos.mul(-world.getScale(), new Vector3f()), 0.05f);
	}

}
