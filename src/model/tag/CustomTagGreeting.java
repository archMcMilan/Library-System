package model.tag;

import java.io.IOException;
import java.io.Writer;
import java.io.*;
import java.util.Properties;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import controller.command.CommandConstant;
import view.View;

public class CustomTagGreeting extends SimpleTagSupport {
	private String user;
	private String localization;

	public void setUser(String user) {
		this.user = user;
	}

	public void setLocalization(String localization) {
		this.localization = localization;
	}

	@Override
	public void doTag() throws JspException {
		FileInputStream fis;
        Properties property = new Properties();
 
		try {
			String greeting;
			if (localization.equals(CommandConstant.EN_US) ) {
				try {
		            fis = new FileInputStream(View.PROP_PATH_EN);
		            property.load(fis);
		        } catch (IOException e) {
		        }
			} else {
				try {
		            fis = new FileInputStream(View.PROP_PATH_UA);
		            property.load(fis);
		        } catch (IOException e) {
		        }
			}
			if(user.equals(View.ADMIN_LOGIN)){
				greeting = property.getProperty(View.HELLO_ADMIN);
			}else if(user.equals(View.GUEST_IN)){
				greeting = property.getProperty(View.HELLO_GUEST);
			}else{
				greeting = property.getProperty(View.HELLO_USER)+user;
			}
			
			getJspContext().getOut().write(greeting);
		} catch (IOException ex) {
			throw new JspException(ex.getMessage());
		}
	}
}