package Engine;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;

public class Window {
    private long window;
    private boolean fullscreen = false;
    private int width, height;
    public Window(){
        setHeight(10);
        setWidth(10);
    }
    public void createWindow(String title){
        window = glfwCreateWindow(width, height, title, fullscreen ? glfwGetPrimaryMonitor() :0,0);

        if(window==0){
            throw new IllegalStateException("Failed to create window");
        }
        if(!fullscreen) {
            GLFWVidMode vid = glfwGetVideoMode(glfwGetPrimaryMonitor());
            glfwSetWindowPos(window, (vid.width() - width) / 2, (vid.height() - height) / 2);
            glfwShowWindow(window);

            glfwMakeContextCurrent(window);
        }

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
    public boolean isFullscreen(){
        return fullscreen;
    }
    public void setFullscreen(){
        this.fullscreen = true;
    }
    public boolean shouldClose(){
        return glfwWindowShouldClose(window);
    }
    public void swapBuffers(){
        glfwSwapBuffers(window);
    }
    public long getWindow(){
        return window;
    }
}
