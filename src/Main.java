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

        Texture tex = new Texture("./resources/example.png");


        while(!glfwWindowShouldClose(window)){

            if(glfwGetKey(window, GLFW_KEY_ESCAPE)==GL_TRUE){
                glfwSetWindowShouldClose(window, true);
            }
            glfwPollEvents();

            glClear(GL_COLOR_BUFFER_BIT);

            tex.bind();
            glBegin(GL_QUADS);
            glTexCoord2f(0,0);
            glVertex2f(-0.5f, 0.5f);
            glTexCoord2f(1,0);
            glVertex2f(0.5f, 0.5f);
            glTexCoord2f(1,1);
            glVertex2f(0.5f, -0.5f);
            glTexCoord2f(0,1);
            glVertex2f(-0.5f, -0.5f);
            glEnd();

            glfwSwapBuffers(window);
        }
        glfwTerminate();
    }
}
