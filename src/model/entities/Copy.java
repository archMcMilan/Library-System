package model.entities;

import java.sql.Date;

/**
 * @author Artem Pryzhkov
 * Class provides which field Copy object must have.
 * Inherit of the Entity class that provides to all entities have id
 */
public class Copy extends Entity{
    private PresenceEnum presence;
    private Date dateTaking;
    private Date dateBringBack;
    private Date dateFactBringBack;
    private Book book;

    public Copy(long id) {
        super(id);
    }

    public void setPresence(int presenceIntValue) {
        if(PresenceEnum.ARMS.ordinal()+1==presenceIntValue){
            presence=PresenceEnum.ARMS;
        }else if(PresenceEnum.HALL.ordinal()+1==presenceIntValue){
            presence=PresenceEnum.HALL;
        }else if(PresenceEnum.SHELF.ordinal()+1==presenceIntValue){
            presence=PresenceEnum.SHELF;
        }

    }

    public void setDateTaking(Date DateTaking) {
        this.dateTaking = DateTaking;
    }

    public void setDateBringBack(Date DateBringBack) {
        this.dateBringBack = DateBringBack;
    }

    public void setDateFactBringBack(Date DateFactBringBack) {
        this.dateFactBringBack = DateFactBringBack;
    }

    public void setBook(Book book) {
        this.book = book;
    }

	public PresenceEnum getPresence() {
		return presence;
	}

	public void setPresence(PresenceEnum presence) {
		this.presence = presence;
	}

	public Date getDateTaking() {
		return dateTaking;
	}

	public Date getDateBringBack() {
		return dateBringBack;
	}

	public Date getDateFactBringBack() {
		return dateFactBringBack;
	}

	public Book getBook() {
		return book;
	}
    
    
}
