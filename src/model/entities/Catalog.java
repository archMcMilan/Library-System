package model.entities;

/**
 * @author Artem Pryzhkov
 * Class provides which field Catalog object must have.
 * Inherit of the Entity class that provides to all entities have id
 */
public class Catalog extends Entity{
    private String name;

    public Catalog(long id) {
        super(id);
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getName() {
		return name;
	}
    
    
}
