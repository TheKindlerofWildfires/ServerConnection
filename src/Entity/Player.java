package Entity;

import Collision.AABB;
import Collision.Collision;
import Engine.*;
import Render.*;
import World.World;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;

public class Player {
    private Model model;
    private AABB boundingBox;
    private Animation texture;
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
        //texture = new Texture("TheVoid.png");
        texture = new Animation(3, 5, "void");
        transform = new Transform();
        transform.scale = new Vector3f(32,32,1);
        boundingBox = new AABB(new Vector2f(transform.pos.x, transform.pos.y), new Vector2f(1,1));

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

        boundingBox.getCenter().set(transform.pos.x, transform.pos.y);
        AABB[] boxes = new AABB[25];
        //more x2 error here?
        for(int i = 0; i<5;i++){
            for(int j = 0; j<5;j++){
                boxes[i+j*5]= world.getTileBoundingBox((int)(transform.pos.x/2)-(5/2)+i,
                        (int)(-transform.pos.y/2)-(5/2)+j);
            }
        }
        AABB box = null;
        for(int i = 0; i<boxes.length; i++){
            if(boxes[i] != null){
                if(box ==null){
                    box = boxes[i];
                }
                Vector2f lenght1 = box.getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());
                Vector2f length2 = boxes[i].getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());

                if(lenght1.lengthSquared()>length2.lengthSquared()){
                    box = boxes[i];
                }
            }
        }
        if(box!=null) {
            Collision data = boundingBox.getCollision(box);
            if (data.isIntersecting) {
                boundingBox.correctPosition(box, data);
                transform.pos.set(boundingBox.getCenter(),0);
            }
            camera.setPosition(transform.pos.mul(-world.getScale(), new Vector3f()));
        }
    }
    public void render(Shader shader, Camera camera){
        shader.bind();
        shader.setUniform("sampler",0);
        shader.setUniform("projection",transform.getProjection(camera.getProjection()));
        texture.bind(0);
        model.render();
    }

}
