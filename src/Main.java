import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL;
public class Main {
    public static int height;
    public static int width;
    public static void main(String[] args){
        new Main();
    }
    public Main(){
        if(!glfwInit()){
            throw new IllegalStateException("GLFW no init");
        }
        height = 640; width = 480;
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        long window = glfwCreateWindow(height,width,"Game Name From Main",0,0);
        if(window==0){
            throw new IllegalStateException("Window no init");
        }

        GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (videoMode.width()-height)/2, (videoMode.height()-width)/2);

        glfwShowWindow(window);
        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        Camera camera = new Camera(640, 480);
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

        camera.setPosition(new Vector3f(-100,0,0));

        while(!glfwWindowShouldClose(window)){

            if(glfwGetKey(window, GLFW_KEY_ESCAPE)==GL_TRUE){
                glfwSetWindowShouldClose(window, true);
            }
            glfwPollEvents();
            target = scale;

            glClear(GL_COLOR_BUFFER_BIT);
            shader.bind();
            shader.setUniform("sampler",0);
            shader.setUniform("projection",camera.getProjection().mul(target));
            tex.bind(0);
            model.render();
            glfwSwapBuffers(window);
        }
        glfwTerminate();
    }
}
