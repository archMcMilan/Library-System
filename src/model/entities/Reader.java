package model.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Artem Pryzhkov
 * Class provides which field Reader object must have.
 * Inherit of the Entity class that provides to all entities have id
 */
public class Reader extends Entity{
    private String name;
    private String address;
    private String phone;
    private Date birthday;
    private List<Copy> copies;
    private int password;
    private List<Book> orderBooks;

    public Reader(long id) {
        super(id);
        copies=new ArrayList<>();
        orderBooks=new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setCopies(List<Copy> copies) {
        this.copies = copies;
    }

	public void setPassword(int password) {
		this.password = password;
	}
	
	public void setOrderBooks(List<Book> orderBooks) {
		this.orderBooks = orderBooks;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getPhone() {
		return phone;
	}

	public Date getBirthday() {
		return birthday;
	}

	public List<Copy> getCopies() {
		return copies;
	}

	public int getPassword() {
		return password;
	}
	
	
	public List<Book> getOrderBooks() {
		return orderBooks;
	}

	/**
	 * Method to add Copy object in the list copies
	 * @param copy
	 * @return true if added successfully, otherwise false
	 */
	public boolean addCopy(Copy copy){
		try{
			copies.add(copy);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	/**
	 * Method to remove Copy object in the list copies
	 * @param copy
	 * @return true if remove successfully, otherwise false
	 */
	public boolean removeCopy(Copy copy){
		try{
			copies.remove(copy);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	/**
	 * Method to add Book object in the list orderBooks
	 * @param book
	 * @return true if added successfully, otherwise false
	 */
	public boolean addBookInOrder(Book book){
		try{
			orderBooks.add(book);
			return true;
		}catch(Exception e){
			return false;
		}
	}
    
	/**
	 * Method to remove Book object in the list orderBooks
	 * @param book
	 * @return true if remove successfully, otherwise false
	 */
	public boolean removeBookInOrder(Book book){
		try{
			orderBooks.remove(book);
			return true;
		}catch(Exception e){
			return false;
		}
	}
}
