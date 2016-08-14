package controller.command.admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.command.Command;
import controller.command.CommandConstant;
import model.dao.DaoFactory;
import model.entities.Copy;
import model.entities.Reader;
import view.PagesPath;

/**
 * @author Artem Pryzhkov
 * Command that provides operation searching Copy objects that have bring back terms overdued
 */
public class FindOverDueCopiesCommand implements Command{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		List<Copy> copies=DaoFactory.getFactory().createCopyDao().findCopyThatOverdue();
		Map<Copy,Reader> debtors=new HashMap<>(); 
		for(Copy c:copies){
			debtors.put(c,DaoFactory.getFactory().createReaderDao().findReaderByCopy(c.getId()));
		}
		request.setAttribute(CommandConstant.PARAMETER_OVERDUE_MAP, debtors);
		return PagesPath.ADMIN_PAGE;
	}

}
