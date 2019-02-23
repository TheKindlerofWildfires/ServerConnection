package Engine;

import World.TileRenderer;
import World.World;
import World.Tile;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWVidMode;

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
        window.setCallbacks();
        GL.createCapabilities();

        Camera camera = new Camera(window.getWidth(), window.getHeight());
        glEnable(GL_TEXTURE_2D);

        TileRenderer tiles = new TileRenderer();

        Shader shader = new Shader("shader");

        World world = new World();
        world.setTile(Tile.testTile2, 0,0);

        camera.setPosition(new Vector3f(0,0,0));

        long lastTime = System.nanoTime();
        double delta = 0.0;
        double ns = 1000000000.0 / 60.0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;

        while(!window.shouldClose()){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1.0) {
                updates++;
                delta--;
                if(window.getInput().isKeyPressed(GLFW_KEY_ESCAPE)){
                    glfwSetWindowShouldClose(window.getWindow(), true);
                }//input should be here
                if(window.getInput().isKeyDown(GLFW_KEY_A)){
                    camera.getPosition().sub(new Vector3f(-5,0,0));
                }
                if(window.getInput().isKeyDown(GLFW_KEY_W)){
                    camera.getPosition().sub(new Vector3f(0,5,0));
                }
                if(window.getInput().isKeyDown(GLFW_KEY_S)){
                    camera.getPosition().sub(new Vector3f(0,-5,0));
                }
                if(window.getInput().isKeyDown(GLFW_KEY_D)){
                    camera.getPosition().sub(new Vector3f(5,0,0));
                }
                window.update();
                world.correctCamera(camera, window);
            }

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println(updates + " UPS, " + frames + " FPS");
                frames = 0;
                updates = 0;
            }
            //if can render should be here
            glClear(GL_COLOR_BUFFER_BIT);
            world.render(tiles, shader, camera, window);
            window.swapBuffers();
            frames++;
        }
        glfwTerminate();
    }
}
