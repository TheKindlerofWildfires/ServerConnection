package game;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL;

import assets.Assets;
import gui.Gui;
import io.Timer;
import io.Window;
import render.Camera;
import render.Shader;
import world.TileRenderer;
import world.World;

import java.awt.*;

public class Main {
	public Main() {
		Window.setCallbacks();
		
		if (!glfwInit()) {
			System.err.println("GLFW Failed to initialize!");
			System.exit(1);
		}
		
		Window window = new Window();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//window.setSize((int)screenSize.getWidth(), (int)screenSize.getHeight());
		window.setSize(1920, 1080);
		window.setFullscreen(false);
		window.setFullscreen(false);
		window.createWindow("Game");
		
		GL.createCapabilities();
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		Camera camera = new Camera(window.getWidth(), window.getHeight());
		glEnable(GL_TEXTURE_2D);
		
		TileRenderer tiles = new TileRenderer();
		Assets.initAsset();

		Shader shader = new Shader("shader");
		
		World world = new World("test_level", camera);
		world.calculateView(window);
		
		Gui gui = new Gui(window);
		
		double frame_cap = 1.0 / 60.0;
		
		double frame_time = 0;
		int frames = 0;
		
		double time = Timer.getTime();
		double unprocessed = 0;
		
		while (!window.shouldClose()) {
			boolean can_render = false;
			
			double time_2 = Timer.getTime();
			double passed = time_2 - time;
			unprocessed += passed;
			frame_time += passed;
			
			time = time_2;
			
			while (unprocessed >= frame_cap) {
				if (window.hasResized()) {
					camera.setProjection(window.getWidth(), window.getHeight());
					gui.resizeCamera(window);
					world.calculateView(window);
					glViewport(0, 0, window.getWidth(), window.getHeight());
				}
				
				unprocessed -= frame_cap;
				can_render = true;
				
				if (window.getInput().isKeyReleased(GLFW_KEY_ESCAPE)) {
					glfwSetWindowShouldClose(window.getWindow(), true);
				}
				
				gui.update(window.getInput());
				
				world.update((float) frame_cap, window, camera);
				
				world.correctCamera(camera, window);
				
				window.update();
				
				if (frame_time >= 1.0) {
					frame_time = 0;
					System.out.println("FPS: " + frames);
					frames = 0;
				}
			}
			
			if (can_render) {
				glClear(GL_COLOR_BUFFER_BIT);
				
				world.render(tiles, shader, camera);
				
				window.swapBuffers();
				frames++;
			}
		}
		
		Assets.deleteAsset();
		
		glfwTerminate();
	}
	
	public static void main(String[] args) {
		new Main();
	}
	
}
