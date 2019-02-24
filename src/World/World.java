package World;

import Collision.AABB;
import Render.Camera;
import Render.Shader;
import Engine.Window;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class World {
    private int viewX;
    private int viewY;
    private byte[] tiles;
    private AABB[] boundingBoxes;
    private int width;
    private int height;
    private int scale;
    private final int view = 64;
    private Matrix4f world;

    public World() {
        width = 64;
        height = 64;
        scale = 32;

        tiles = new byte[width * height];
        boundingBoxes = new AABB[width * height];
        world = new Matrix4f().setTranslation(new Vector3f(0));
        world.scale(scale);
    }

    public void render(TileRenderer render, Shader shader, Camera cam, Window win) {

        int posX = ((int) cam.getPosition().x + win.getWidth() / 2) / (scale * 2);//two bad?
        int posY = ((int) cam.getPosition().y - win.getHeight() / 2) / (scale * 2);

        for (int i = 0; i < view; i++) {
            for (int j = 0; j < view; j++) {
                Tile t = getTile(i - posX, j + posY);
                if (t != null) {
                    render.renderTile(t, i - posX, -j - posY, shader, world, cam);

                }
            }
        }
    }

    public void setTile(Tile tile, int x, int y) {
        tiles[x + y * width] = tile.getId();
        if (tile.isSolid()) {
            boundingBoxes[x + y * width] = new AABB(new Vector2f(x, -y), new Vector2f(1, 1));
        } else {
            boundingBoxes[x + y * width] = null;
        }
    }

    public void correctCamera(Camera cam, Window window) {
        Vector3f pos = cam.getPosition();
        int w = -width * scale;
        int h = height * scale;//two bad?
        //one of these isborked because i had to take scale off
        if (pos.x > -(window.getWidth() / 2) + scale) pos.x = -(window.getWidth() / 2) + scale;
        if (pos.x < w + (window.getWidth() / 2) + scale) pos.x = w + (window.getWidth() / 2) + scale;

        if (pos.y < (window.getHeight() / 2) - scale) pos.y = (window.getHeight() / 2) - scale;
        if (pos.y > h - (window.getHeight() / 2) - scale) pos.y = h - (window.getHeight() / 2) - scale;
    }

    public Tile getTile(int x, int y) {
        try {
            return Tile.tiles[tiles[x + y * width]];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public AABB getTileBoundingBox(int x, int y) {
        try {
            return boundingBoxes[x + y * width];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public int getScale() {
        return scale;
    }

    public void calculateView(Window window) {
        viewX = (window.getWidth() / (scale * 2)) + 4;
        viewY = (window.getHeight() / (scale * 2)) + 4;
    }
}
