package controller.command.user;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.command.Command;
import controller.command.CommandConstant;
import model.entities.Book;
import model.logic.OperationWithBooks;
import model.service.BookService;
import view.PagesPath;
import view.View;

/**
 * @author Artem Pryzhkov
 * Command that provides operation searching Book objects by Name
 */
public class FindBookByNameCommand implements Command{
	BookService bookService=BookService.getInstance();
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter(CommandConstant.PARAMETER_BOOK_NAME);
		List<Book> result = bookService.findBookByName(name);
		Map<Book,Integer> resultMap= new HashMap<>();
		for(Book b:result){
			resultMap.put(b, OperationWithBooks.countAvailableCopy(b.getId()));
		}
		request.setAttribute(CommandConstant.PARAMETER_MAP, resultMap);
		request.setAttribute(CommandConstant.FIND_AMOUNT, resultMap.size());
		HttpSession session = request.getSession();
		if(session.getAttribute(CommandConstant.SESSION_USER_ATTR)==null){
			return PagesPath.GUEST_PAGE;
		}else{
			return PagesPath.USER_PAGE;
		}
	}

}
