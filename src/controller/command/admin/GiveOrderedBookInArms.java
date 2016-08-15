package controller.command.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.command.Command;
import controller.command.CommandConstant;
import model.dao.DaoFactory;
import model.entities.Copy;
import model.entities.PresenceEnum;
import model.entities.Reader;
import model.logic.OperationWithBooks;
import model.service.CopyService;
import model.service.ReaderService;
import view.PagesPath;
import view.View;

/**
 * @author Artem Pryzhkov
 * Command that provides operation giving ordered Book to Reader in arms
 */
public class GiveOrderedBookInArms implements Command{

	CopyService copyService=CopyService.getInstance();
	ReaderService readerService=ReaderService.getInstance();
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long bookId = Long.parseLong(request.getParameter(CommandConstant.PARAMETER_BOOK_ID));
		long readerId=Long.parseLong(request.getParameter(CommandConstant.PARAMETER_READER_ID));
		
		Copy copy=copyService.takeAvailableCopyInArms(bookId);
		Reader reader=readerService.distribute(copy, readerId);
		reader.removeBookInOrder(DaoFactory.getFactory().createBookDao().find(bookId));
		DaoFactory.getFactory().createReaderDao().updateOrderBooks(reader);
		request.setAttribute(CommandConstant.OPERATION_SUCCESS, true);
		
		return PagesPath.ADMIN_PAGE;
	}

}
