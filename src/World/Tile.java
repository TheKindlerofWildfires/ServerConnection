package World;

public class Tile {
    public static Tile tiles[] = new Tile[16];
    public static byte NOT = 0;

    public static final Tile testTile = new Tile("TheVoid");
    public static final Tile testTile2 = new Tile( "example");

    private byte id;
    private String texture;

    public Tile(String texture){
        this.id = NOT;
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
}
