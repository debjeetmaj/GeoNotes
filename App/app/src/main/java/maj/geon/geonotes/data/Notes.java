package maj.geon.geonotes.data;

import java.util.Date;

/**
 * Created by debjeet on 12/4/16.
 */
//@DatabaseTable(tableName)
public class Notes {

    private int key_id;
    private String Title;
    private String Message;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    private String owner;
    private float lattitude,longitude,radius;
    private Date created_at;

    public int getKey_id() {
        return key_id;
    }

    public void setKey_id(int key_id) {
        this.key_id = key_id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public float getLattitude() {
        return lattitude;
    }

    public void setLattitude(float lattitude) {
        this.lattitude = lattitude;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
