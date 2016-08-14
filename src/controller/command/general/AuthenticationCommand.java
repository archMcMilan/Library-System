package controller.command.general;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import controller.command.Command;
import controller.command.CommandConstant;
import model.entities.Reader;
import model.service.ReaderService;
import view.PagesPath;
import view.View;
import view.LoggerMessage;


/**
 * @author Artem Pryzhkov
 * Command that provides operation authentication by login and password
 */
public class AuthenticationCommand implements Command{
	ReaderService readerSevice=ReaderService.getInstance(); 

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter(CommandConstant.PARAMETER_LOGIN);
        String pass = request.getParameter(CommandConstant.PARAMETER_PASSWORD);
        HttpSession session = request.getSession(true);
        
        if(login==null && pass==null){
        	Logger.getLogger(this.getClass()).info(LoggerMessage.GUEST_IN);
        	session.setAttribute(CommandConstant.SESSION_USER_NAME, CommandConstant.PARAMETER_GUEST);
        	return PagesPath.GUEST_PAGE;
        }
        
        if(login.equals(View.ADMIN_LOGIN) && pass.equals(View.ADMIN_PASS)){
    		session.setAttribute(CommandConstant.SESSION_USER_ATTR, CommandConstant.PARAMETER_ADMIN);
    		session.setAttribute(CommandConstant.SESSION_USER_NAME, CommandConstant.PARAMETER_ADMIN);
    		Logger.getLogger(this.getClass()).info(LoggerMessage.ADMIN_IN);
    		
    		return PagesPath.ADMIN_PAGE;
    	}
        if(login.matches(View.REGEX_LOGIN) && pass.matches(View.REGEX_PASSWORD)){
    		Reader reader=readerSevice.checkLogin(login, pass.hashCode());
       	 	if(reader==null){
       	 	request.setAttribute(CommandConstant.MESSAGE_ERROR_LOGIN, true);
       	 		return PagesPath.HOME_PAGE;
       	 	}else{
       	 		session.setAttribute(CommandConstant.SESSION_USER_ATTR, login);
       	 		session.setAttribute(CommandConstant.SESSION_USER_NAME, reader.getName());
       	 		request.setAttribute(CommandConstant.MESSAGE_ERROR_LOGIN, false);
       	 		Logger.getLogger(this.getClass()).info(LoggerMessage.READER_IN+login);
       	 		return PagesPath.USER_PAGE;
       	 	}
        	
        }
        request.setAttribute(CommandConstant.MESSAGE_ERROR_LOGIN, true);
        return PagesPath.HOME_PAGE;
    }
}
