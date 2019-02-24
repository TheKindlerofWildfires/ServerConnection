package World;

public class Tile {
    public static Tile tiles[] = new Tile[16];
    public static byte NOT = 0;

    public static final Tile testTile = new Tile("grass");
    public static final Tile testTile2 = new Tile( "example").setSolid();

    private byte id;
    private boolean solid;
    private String texture;

    public Tile(String texture){
        this.id = NOT;
        this.solid = false;
        NOT++;
        this.texture = texture;
        if(tiles[id]!=null){
            throw new IllegalStateException("Tiles at ["+id+"] is already set");
        }
        tiles[id] = this;
    }
    public byte getId(){
        return id;
    }
    public String getTexture(){
        return texture;
    }
    public Tile setSolid(){this.solid = true; return this;}

    public boolean isSolid() {
        return solid;
    }
}
