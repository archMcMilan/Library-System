package model.entities;

/**
 * @author Artem Pryzhkov
 * Class provides which field Author object must have
 * Inherit of the Entity class that provides to all entities have id
 */
public class Author extends Entity{
    private String name;
    private String surname;
    private String patromynic;

    public Author(long id) {
        super(id);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPatromynic(String patromynic) {
        this.patromynic = patromynic;
    }

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getPatromynic() {
		return patromynic;
	}
    
    
}
