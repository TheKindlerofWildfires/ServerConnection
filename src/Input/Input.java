package Input;

import Engine.Window;

import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;

public class Input {
    private long win;
    public Input(long win){
        this.win = win;
    }
    public boolean isKeyDown(int key){
        return glfwGetKey(win, key)==1;

    }
    public boolean isMouseButtonDown(int key){
        return glfwGetMouseButton(win, key)==1;

    }
}
