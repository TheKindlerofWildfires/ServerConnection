package World;

import Render.Model;
import Render.Texture;

import Render.Camera;
import Render.Shader;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.HashMap;

public class TileRenderer {
    private HashMap<String, Texture> tileTextures;
    private Model model;

    public TileRenderer(){
        tileTextures = new HashMap<String, Texture>();

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

        for(int i =0; i<Tile.tiles.length;i++){
            if(Tile.tiles[i]!= null) {
                if (!tileTextures.containsKey(Tile.tiles[i].getTexture())) {
                    String tex = Tile.tiles[i].getTexture();
                    tileTextures.put(tex, new Texture(tex + ".png"));
                }
            }
        }
    }
    public void renderTile(Tile id, int x, int y, Shader shader, Matrix4f world, Camera cam){
        shader.bind();
        if(tileTextures.containsKey(id.getTexture())){
            tileTextures.get(id.getTexture()).bind(0);
        }
        Matrix4f tilePos = new Matrix4f().translate(new Vector3f(x, y,0));
        Matrix4f target = new Matrix4f();
        cam.getProjection().mul(world, target);
        target.mul(tilePos);

        shader.setUniform("sampler", 0);
        shader.setUniform("projection", target);

        model.render();
    }
}
