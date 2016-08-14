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

//	@Override
//	public void doTag() throws JspException {
//		FileInputStream fis;
//        Properties property = new Properties();
// 
//		try {
//			String greeting;
//			if (localization.equals(CommandConstant.EN_US) ) {
//				try {
//		            fis = new FileInputStream(View.PROP_PATH_EN);
//		            property.load(fis);
//		        } catch (IOException e) {
//		        }
//			} else {
//				try {
//		            fis = new FileInputStream(View.PROP_PATH_UA);
//		            property.load(fis);
//		        } catch (IOException e) {
//		        }
//			}
//			if(user.equals(View.ADMIN_LOGIN)){
//				greeting = property.getProperty(View.HELLO_ADMIN);
//			}else if(user.equals(View.GUEST_IN)){
//				greeting = property.getProperty(View.HELLO_GUEST);
//			}else{
//				greeting = property.getProperty(View.HELLO_USER)+user;
//			}
//			
//			getJspContext().getOut().write(greeting);
//		} catch (IOException ex) {
//			throw new JspException(ex.getMessage());
//		}
//	}

	@Override
	public void doTag() throws JspException {
//		Properties props = new Properties();
//		try {
//			props.load(new FileInputStream(new File("src/properties/message_en_US.properties")));
//		} catch (IOException e) {
//			System.out.println("doesnt find");
//			e.printStackTrace();
//		}
//
//		String hello =(props.getProperty("hello_admin"));
//		System.out.println("custom tag"+hello);
		try {
			String greeting;
			if (localization.equals(CommandConstant.EN_US) ) {
				if(user.equals(View.ADMIN_LOGIN)){
					greeting = View.HELLO_ADMIN_TEXT_EN;
				}else if(user.equals(View.GUEST_IN)){
					greeting = View.HELLO_GUEST_TEXT_EN;
				}else{
					greeting = View.HELLO_USER_TEXT_EN+user;
				}
			} else {
				if(user.equals(View.ADMIN_LOGIN)){
					greeting = View.HELLO_ADMIN_TEXT_UA;
				}else if(user.equals(View.GUEST_IN)){
					greeting = View.HELLO_GUEST_TEXT_UA;
				}else{
					greeting = View.HELLO_USER_TEXT_UA+user;
				}
			}
			
			getJspContext().getOut().write(greeting);
		} catch (IOException ex) {
			throw new JspException(ex.getMessage());
		}
	}
}