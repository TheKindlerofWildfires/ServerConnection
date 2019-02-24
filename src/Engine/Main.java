package Engine;

import Collision.AABB;
import Entity.Player;
import Render.Camera;
import Render.Shader;
import World.TileRenderer;
import World.World;
import World.Tile;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL;
public class Main {
    public static void main(String[] args){
        new Main();
    }
    public Main(){
        if(!glfwInit()){
            throw new IllegalStateException("GLFW no init");
        }

        Window window = new Window();
        window.setWidth(1920); window.setHeight(1080);
        window.createWindow("Game");
        Window.setCallbacks();
        GL.createCapabilities();

        Camera camera = new Camera(window.getWidth(), window.getHeight());
        glEnable(GL_TEXTURE_2D);

        TileRenderer tiles = new TileRenderer();

        Shader shader = new Shader("shader");

        World world = new World();
        world.setTile(Tile.testTile2, 5,0);
        world.setTile(Tile.testTile2, 6,0);

        Player player = new Player();

        camera.setPosition(new Vector3f(0,0,0));

        double frameCap = 1.0 / 60.0;

        double frameTime = 0;
        int frames = 0;

        double time = Timer.getTime();
        double unprocessed = 0;

        while(!window.shouldClose()){
            boolean can_render = false;

            double time2 = Timer.getTime();
            double passed = time2 - time;
            unprocessed += passed;
            frameTime += passed;

            time = time2;
            while (unprocessed >= frameCap) {
                unprocessed -= frameCap;
                can_render = true;
                if(window.getInput().isKeyPressed(GLFW_KEY_ESCAPE)){
                    glfwSetWindowShouldClose(window.getWindow(), true);
                }//input should be here

                player.update((float)frameCap, window, camera, world );
                world.correctCamera(camera, window);
                window.update();

                if (frameTime >= 1.0) {
                    frameTime = 0;
                    System.out.println("FPS: " + frames);
                    frames = 0;
                }            }

            if (can_render) {
                glClear(GL_COLOR_BUFFER_BIT);
                world.render(tiles, shader, camera, window);
                player.render(shader,camera);
                window.swapBuffers();
                frames++;
            }
        }
        glfwTerminate();
    }
}
