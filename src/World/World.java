package World;

import Engine.Camera;
import Engine.Shader;
import Engine.Window;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class World {
    private byte[] tiles;
    private int width;
    private int height;
    private int scale;
    private Matrix4f world;
    public World(){
        width = 64;
        height = 64;
        scale = 32;

        tiles = new byte[width*height];

        world = new Matrix4f().setTranslation(new Vector3f(0));
        world.scale(scale);
    }
    public void render(TileRenderer render, Shader shader, Camera cam){
        for(int i = 0; i< height; i++){
            for(int j = 0; j< width; j++){
                render.renderTile(tiles[j+i*width], j, -i, shader, world, cam );
            }
        }
    }
    public void setTile(Tile tile, int x, int y){
        tiles[x+y*width ] = tile.getId();
    }
    public void correctCamera(Camera cam, Window win){
        Vector3f pos = cam.getPosition();
        int w = -width*scale;
        int h = height*scale;//two bad?
        //one of these isborked because i had to take scale off
        if(pos.x >-(win.getWidth()/2)+scale){
            pos.x = -(win.getWidth()/2)+scale;
        }
        if(pos.x < w+(win.getWidth()/2)){
            pos.x = w+(win.getWidth()/2);
        }
        if(pos.y <(win.getHeight()/2)-scale){
            pos.y = (win.getHeight()/2)-scale;
        }
        if(pos.y >h-(win.getHeight()/2)-scale){
            pos.y = h-(win.getHeight()/2)-scale;
        }
    }
}
