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
import model.entities.Book;
import model.entities.Copy;
import model.entities.PresenceEnum;
import model.entities.Reader;
import model.jdbc.JdbcCopyDao;
import model.logic.OperationWithBooks;
import model.service.ReaderService;
import view.PagesPath;

/**
 * @author Artem Pryzhkov
 * Command that output all ordered Books
 */
public class ShowOrderListCommand implements Command {
	ReaderService readerService = ReaderService.getInstance();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Reader> readers = DaoFactory.getFactory().createReaderDao().findAll();
		Map<Reader, Map<Book, Boolean>> readerOrderMap = new HashMap<>();

		for (Reader r : readers) {
			List<Book> orderedBooks = readerService.readerOrders(r.getId());
			Map<Book, Boolean> bookAvailability = new HashMap<>();
			for (Book b : orderedBooks) {
				bookAvailability.put(b, false);
				if (OperationWithBooks.countAvailableCopy(b.getId())>0) {
					bookAvailability.put(b, true);
					break;
				}
			}
			readerOrderMap.put(r, bookAvailability);

		}
		request.setAttribute(CommandConstant.PARAMETER_ORDER_MAP, readerOrderMap);

		return PagesPath.ADMIN_PAGE;
	}

}
