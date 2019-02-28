package entity;

import collision.AABB;
import collision.Collision;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import io.Window;
import render.Animation;
import render.Camera;
import world.World;

public class Projectile extends Entity {
    public static final int ANIM_HIT = 0;
    public static final int ANIM_MOVE = 1;
    public static final int ANIM_SIZE = 2;
    public int speed;
    public Vector2f direction;
    public Projectile(Transform transform, Vector2f location) {
        super(ANIM_SIZE, transform);//Haven't checked this yet, this class itself will be extended eventually
        setAnimation(ANIM_HIT, new Animation(4, 1, "projectile/idle")); //alter to explode
        setAnimation(ANIM_MOVE, new Animation(4, 1, "projectile/walking")); //set to beam
        speed = 15;
        this.direction = direction;
    }

    @Override
    public void update(float delta, Window window, Camera camera, World world) {
        Vector2f movement = new Vector2f();
        //movement.add(direction.mul(speed).mul(delta));
        //movement.add(1,0);

        move(movement);// Haven't checked this yet (Should do same collision stuff but if connect should be different
        //useAnimation(ANIM_WALK);
        //probably want some culling code right here if camera is off screen
        //camera.getPosition().lerp(transform.pos.mul(-world.getScale(), new Vector3f()), 0.05f);
    }
    public void collideWithEntity(Entity entity){
        //code here to find which entity, explode, hurt them?
        Collision collision = bounding_box.getCollision(entity.bounding_box);

        if (collision.isIntersecting) {

            //Actually just explode
            collision.distance.x /= 2;
            collision.distance.y /= 2;

            bounding_box.correctPosition(entity.bounding_box, collision);
            transform.pos.set(bounding_box.getCenter().x, bounding_box.getCenter().y, 0);

            entity.bounding_box.correctPosition(bounding_box, collision);
            entity.transform.pos.set(entity.bounding_box.getCenter().x, entity.bounding_box.getCenter().y, 0);
        }
    }
    public void collideWithTiles(World world) {
        //destroy projectile
        AABB[] boxes = new AABB[25];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                boxes[i + j * 5] = world.getTileBoundingBox((int) (((transform.pos.x / 2) + 0.5f) - (5 / 2)) + i, (int) (((-transform.pos.y / 2) + 0.5f) - (5 / 2)) + j);
            }
        }

        AABB box = null;
        for (int i = 0; i < boxes.length; i++) {
            if (boxes[i] != null) {
                if (box == null) box = boxes[i];

                Vector2f length1 = box.getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());
                Vector2f length2 = boxes[i].getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());

                if (length1.lengthSquared() > length2.lengthSquared()) {
                    box = boxes[i];
                }
            }
        }
        if (box != null) {
            Collision data = bounding_box.getCollision(box);
            if (data.isIntersecting) {
                //Destroy
                bounding_box.correctPosition(box, data);
                transform.pos.set(bounding_box.getCenter(), 0);
            }

            for (int i = 0; i < boxes.length; i++) {
                if (boxes[i] != null) {
                    if (box == null) box = boxes[i];

                    Vector2f length1 = box.getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());
                    Vector2f length2 = boxes[i].getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());

                    if (length1.lengthSquared() > length2.lengthSquared()) {
                        box = boxes[i];
                    }
                }
            }

            data = bounding_box.getCollision(box);
            if (data.isIntersecting) {
                //Destroy
                bounding_box.correctPosition(box, data);
                transform.pos.set(bounding_box.getCenter(), 0);
            }
        }
    }
}
