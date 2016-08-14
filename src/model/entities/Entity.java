package model.entities;


/**
 * @author Artem Pryzhkov
 * Class provides instruction to all entities to have unique id 
 */
public abstract class Entity {
    private long id;

    public Entity(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
