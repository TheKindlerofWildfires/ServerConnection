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
        glEnable(GL_TEXTURE_2D);

        float[] vertices = new float[]{
                -.5f,.5f,
                .5f,.5f,
                .5f,-.5f,
                .5f,-.5f,
                -.5f,-.5f,
                -.5f,.5f,
        };

        float[] textures = new float[]{
                0,0,
                1,0,
                1,1,

                1,1,
                0,1,
                0,0,

        };
        Model model = new Model(vertices, textures);
        Texture tex = new Texture("./resources/TheVoid.png");


        while(!glfwWindowShouldClose(window)){

            if(glfwGetKey(window, GLFW_KEY_ESCAPE)==GL_TRUE){
                glfwSetWindowShouldClose(window, true);
            }
            glfwPollEvents();
            tex.bind();
            glClear(GL_COLOR_BUFFER_BIT);
            model.render();
            glfwSwapBuffers(window);
        }
        glfwTerminate();
    }
}
