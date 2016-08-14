package model.entities;

import java.util.List;

/**
 * @author Artem Pryzhkov
 * Class provides which field Book object must have.
 * Inherit of the Entity class that provides to all entities have id
 */
public class Book extends Entity{
    private String name;
    private int publicationYear;
    private int pagesAmount;
    private Catalog catalog;
    private List<Author> authors;

    public Book(long id) {
        super(id);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public void setPagesAmount(int pagesAmount) {
        this.pagesAmount = pagesAmount;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public String getName() {
        return name;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public int getPagesAmount() {
        return pagesAmount;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public List<Author> getAuthors() {
        return authors;
    }
}
