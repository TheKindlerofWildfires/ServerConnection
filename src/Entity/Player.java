package Entity;

import Engine.*;
import World.World;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;

public class Player {
    private Model model;
    private Texture texture;
    private Transform transform;
    public Player(){
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
        model = new Model(vertices, textures,indices);
        texture = new Texture("TheVoid.png");
        transform = new Transform();
        transform.scale = new Vector3f(32,32,1);

    }
    public void update(float delta, Window window, Camera camera, World world){
        if(window.getInput().isKeyDown(GLFW_KEY_A)){
            transform.pos.add(new Vector3f(-5*delta,0,0));
        }
        if(window.getInput().isKeyDown(GLFW_KEY_W)){
            transform.pos.add(new Vector3f(0,5*delta,0));
        }
        if(window.getInput().isKeyDown(GLFW_KEY_S)){
            transform.pos.add(new Vector3f(0,-5*delta,0));
        }
        if(window.getInput().isKeyDown(GLFW_KEY_D)){
            transform.pos.add(new Vector3f(5*delta,0,0));
        }
        camera.setPosition(transform.pos.mul(-world.getScale(), new Vector3f()));
    }
    public void render(Shader shader, Camera camera){
        shader.bind();
        shader.setUniform("sampler",0);
        shader.setUniform("projection",transform.getProjection(camera.getProjection()));
        texture.bind(0);
        model.render();
    }

}
