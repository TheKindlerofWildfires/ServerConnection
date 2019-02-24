package Render;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {
    private Vector3f position;
    private Matrix4f projection;

    public Camera(int width, int height){
        position = new Vector3f(0,0,0);
        projection = new Matrix4f().setOrtho2D(-width/2, width/2, -height/2, height/2);

    }
    public void setPosition(Vector3f pos){
        position = pos;
    }
    public void addPositon(Vector3f pos){
        position.add(pos);//does this work?
    }
    public Vector3f getPosition(){
        return position;
    }
    public Matrix4f getProjection(){
        return projection.translate(position, new Matrix4f());
    }
}