package Engine;

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

        float[] vertices = new float[]{
                -.5f,.5f,0,
                .5f,.5f,0,
                .5f,-.5f,0,
                -.5f,-.5f,0
        };

        float[] textures = new float[]{
                0,0,
                1,0,
                1,1,
                0,1,

        };
        int[] indices = new int[]{
                0,1,2,
                2,3,0
        };
        Model model = new Model(vertices, textures,indices);
        Shader shader = new Shader("shader");
        Texture tex = new Texture("./resources/TheVoid.png");

        Matrix4f scale = new Matrix4f().scale(64);
        Matrix4f target = new Matrix4f();

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
                target = scale;
                if(win.getInput().isKeyDown(GLFW_KEY_ESCAPE)){
                    glfwSetWindowShouldClose(win.getWindow(), true);
                }
                glfwPollEvents();
            }

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println(updates + " UPS, " + frames + " FPS");
                frames = 0;
                updates = 0;
            }
            glClear(GL_COLOR_BUFFER_BIT);
            shader.bind();
            shader.setUniform("sampler", 0);
            shader.setUniform("projection", camera.getProjection().mul(target));
            tex.bind(0);
            model.render();
            win.swapBuffers();
            frames++;
        }
        glfwTerminate();
    }
}
