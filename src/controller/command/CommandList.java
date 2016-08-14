package controller.command;

import controller.command.admin.FindOverDueCopiesCommand;
import controller.command.admin.GiveOrderedBookInArms;
import controller.command.admin.GiveOrderedBookInHall;
import controller.command.admin.ShowOrderListCommand;
import controller.command.general.AuthenticationCommand;
import controller.command.general.BookListCommand;
import controller.command.general.GoBackCommand;
import controller.command.general.LocalizationCommand;
import controller.command.general.LogoutCommand;
import controller.command.user.BringBackCommand;
import controller.command.user.FindBookByAuthorCommand;
import controller.command.user.FindBookByCatalogCommand;
import controller.command.user.FindBookByNameCommand;
import controller.command.user.OrderBookCommand;
import controller.command.user.ReaderBooksHistoryCommand;
import controller.command.user.TakeBookCommandInArms;
import controller.command.user.TakeBookCommandInHall;

/**
 * @author Artem Pryzhkov
 * Class provides all commands and forward to the realization classes
 */
public enum CommandList {
    AUTHENTICATION(new AuthenticationCommand()),
    LOCALIZATION(new LocalizationCommand()),
    GO_BACK(new GoBackCommand()),
    LOGOUT(new LogoutCommand()),
    FIND_ALL_BOOKS(new BookListCommand()),
	READER_BOOKS(new ReaderBooksHistoryCommand()),
	FIND_BOOK_BY_CATALOG(new FindBookByCatalogCommand()),
	FIND_BOOK_BY_NAME(new FindBookByNameCommand()),
	FIND_BOOK_BY_AUTHOR(new FindBookByAuthorCommand()),
	TAKE_BOOK_IN_ARMS(new TakeBookCommandInArms()),
	TAKE_BOOK_IN_HALL(new TakeBookCommandInHall()),
	ORDER_BOOK(new OrderBookCommand()),
	BRING_BACK(new BringBackCommand()),
	FIND_OVERDUE_COPIES(new FindOverDueCopiesCommand()),
	SHOW_ORDER_LIST(new ShowOrderListCommand()),
	GIVE_BOOK_IN_ARMS(new GiveOrderedBookInArms()),
	GIVE_BOOK_IN_HALL(new GiveOrderedBookInHall());

	private Command command;
	
    CommandList(Command command){
        this.command =command;
    }
    
    public Command getCommand() {
        return command;
    }
}
