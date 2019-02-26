package entity;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import io.Window;
import render.Animation;
import render.Camera;
import world.World;

public class Player extends Entity {
	public static final int ANIM_IDLE = 0;
	public static final int ANIM_WALK = 1;
	public static final int ANIM_SIZE = 2;
	public int direction = 0; //0N 1E 2S 3W
	//My proposed solution is to cut down on anim size, and make 4 options for each anim (nesw)
	//Previous size 4*3 = 12 = 4*states;
	//New size = 3*3*4 = 36 = 12* states;
	public int speed;
	public Player(Transform transform) {
		super(ANIM_SIZE, transform);
		setAnimation(ANIM_IDLE, new Animation(4, 1, "player/idle"));
		setAnimation(ANIM_WALK, new Animation(4, 1, "player/walking"));
		speed = 15;
	}
	
	@Override
	public void update(float delta, Window window, Camera camera, World world) {
		Vector2f movement = new Vector2f();
		if (window.getInput().isKeyDown(GLFW.GLFW_KEY_A)){
			direction = 3;
			movement.add(-speed * delta, 0);
		}
		
		if (window.getInput().isKeyDown(GLFW.GLFW_KEY_D)) {
			direction = 1;
			movement.add(speed * delta, 0);
		}
		
		if (window.getInput().isKeyDown(GLFW.GLFW_KEY_W)){
			direction = 0;
			movement.add(0, speed * delta);
		}
		
		if (window.getInput().isKeyDown(GLFW.GLFW_KEY_S)){
			direction = 2;
			movement.add(0, -speed * delta);
		}
		
		move(movement);
		
		if (movement.x != 0 || movement.y != 0)
			useAnimation(ANIM_WALK);
		else useAnimation(ANIM_IDLE);
		
		camera.getPosition().lerp(transform.pos.mul(-world.getScale(), new Vector3f()), 0.05f);
	}
}
