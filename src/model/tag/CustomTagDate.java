package model.tag;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.log4j.Logger;

import controller.command.CommandConstant;


public class CustomTagDate extends SimpleTagSupport{
	private String localization;
	private Date date;

	public void setLocalization(String localization) {
		this.localization = localization;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public void doTag() throws JspException {
		 SimpleDateFormat dateFormat = null;
		if (localization.equals(CommandConstant.EN_US) ) {
			dateFormat = new SimpleDateFormat("yyyy.MM.dd");
		} else {
			dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		}
		try {
			getJspContext().getOut().write(dateFormat.format(date));
		} catch (IOException e) {
			Logger.getLogger(getClass()).
				error("Error date");
		}
	}
}
