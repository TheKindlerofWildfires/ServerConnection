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

        Window win = new Window();
        win.setWidth(1920); win.setHeight(1080);
        win.createWindow("Game");
        win.setCallbacks();
        GL.createCapabilities();

        Camera camera = new Camera(win.getWidth(), win.getHeight());
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

        while(!win.shouldClose()){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1.0) {
                updates++;
                delta--;
                if(win.getInput().isKeyPressed(GLFW_KEY_ESCAPE)){
                    glfwSetWindowShouldClose(win.getWindow(), true);
                }//input should be here
                if(win.getInput().isKeyDown(GLFW_KEY_A)){
                    camera.getPosition().sub(new Vector3f(-5,0,0));
                }
                if(win.getInput().isKeyDown(GLFW_KEY_W)){
                    camera.getPosition().sub(new Vector3f(0,5,0));
                }
                if(win.getInput().isKeyDown(GLFW_KEY_S)){
                    camera.getPosition().sub(new Vector3f(0,-5,0));
                }
                if(win.getInput().isKeyDown(GLFW_KEY_D)){
                    camera.getPosition().sub(new Vector3f(5,0,0));
                }
                win.update();
                world.correctCamera(camera, win);
            }

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println(updates + " UPS, " + frames + " FPS");
                frames = 0;
                updates = 0;
            }
            //if can render should be here
            glClear(GL_COLOR_BUFFER_BIT);
            world.render(tiles, shader, camera);
            win.swapBuffers();
            frames++;
        }
        glfwTerminate();
    }
}
