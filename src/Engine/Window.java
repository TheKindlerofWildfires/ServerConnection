package Engine;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;

public class Window {
    private long window;

    private int width, height;
    public Window(){
        setHeight(480);
        setWidth(640);
    }
    public void createWindow(String title){
        window = glfwCreateWindow(width, height, title, 0,0);
        //window = glfwCreateWindow(width, height, title, glfwGetPrimaryMonitor(),0); //fullscreen
        if(window==0){
            throw new IllegalStateException("Failed to create window");
        }

        GLFWVidMode vid = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (vid.width()-width)/2, (vid.height()-height)/2);
        glfwShowWindow(window);

        glfwMakeContextCurrent(window);


    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }
    public boolean shouldClose(){
        return glfwWindowShouldClose(window);
    }
    public void swapBuffers(){
        glfwSwapBuffers(window);
    }
}
