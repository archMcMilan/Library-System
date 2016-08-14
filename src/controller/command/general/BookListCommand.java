package controller.command.general;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.command.Command;
import controller.command.CommandConstant;
import model.service.BookService;
import view.PagesPath;

/**
 * @author Artem Pryzhkov
 * Command that provides operation that output all books in the library
 */
public class BookListCommand implements Command {
	BookService bookService=BookService.getInstance();
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	List result = bookService.findAll();
		request.setAttribute(CommandConstant.PARAMETER_LIST, result);
		return PagesPath.BOOK_LIST_PAGE;
    }
}
