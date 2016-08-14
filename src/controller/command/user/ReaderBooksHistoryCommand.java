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
import model.entities.Copy;
import model.entities.Reader;
import model.logic.OperationWithBooks;
import model.service.BookService;
import model.service.ReaderService;
import view.PagesPath;

/**
 * @author Artem Pryzhkov
 * Command that output Reader taken history
 */
public class ReaderBooksHistoryCommand implements Command{
	ReaderService readerService=ReaderService.getInstance();
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		List<Copy> result = readerService.readerBooks(Long.parseLong((String) session.getAttribute(CommandConstant.SESSION_USER_ATTR)));
		
		Map<Copy,Book> resultMap= new HashMap<>();
		for(Copy c:result){
			resultMap.put(c, BookService.getInstance().findBookByCopy(c.getId()));
		}
		
		request.setAttribute(CommandConstant.PARAMETER_READER_MAP, resultMap);
		
		return PagesPath.USER_BOOK_HISTORY;
	}

}
