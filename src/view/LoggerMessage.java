package view;

public class LoggerMessage {
    public static final String SUCCESS_CREATION="success creation ";
    public static final String SUCCESS_TRANSACTION_ROLLBACK="success transaction rollback ";
    public static final String UNSUCCESS_TRANSACTION_ROLLBACK="unsuccess transaction rollback ";
    
    public static final String SUCCESS_UPDATING="success updating ";
    public static final String SUCCESS_DELETING="success deleting by id";
    public static final String SUCCESS_INIT="success init ";
    
    public static final String SUCCESS_COPIED_COPY="success coping copy into Reader temp object in class and size= ";
    public static final String SUCCESS_COPIED_BOOK="success coping book into Reader temp object in class and size= ";
    
    public static final String SUCCESS_COPIED_AUTHOR="success coping author into Book temp object in class and size= ";
    
    public static final String UNSUCCESS_CREATION="unsuccess creation ";
    public static final String UNSUCCESS_UPDATING="unsuccess updating ";
    public static final String UNSUCCESS_DELETING="unsuccess deleting by id";
    public static final String UNSUCCESS_COPIED_COPY="unsuccess coping copy into Reader temp object ";
    public static final String UNSUCCESS_COPIED_BOOK="unsuccess coping book into Reader temp object ";
    public static final String UNSUCCESS_COPIED="unsuccess coping entity's fields into Reader temp object ";
    
    public static final String UNSUCCESS_COPIED_AUTHOR="unsuccess coping author into Book temp object ";
    
    public static final String SUCCESS_SET="success set preparedStatement ";
    public static final String UNSUCCESS_SET="unsuccess set preparedStatement ";
    
    public static final String RETURN_OBJECT_INIT="init method return object= ";
    public static final String RETURN_OBJECT_FIND_READER_COPIES="find reader copies method return list size= ";
    public static final String RETURN_OBJECT_FIND_READER_ORDERS="find reader orders method return list size= ";
    public static final String RETURN_NULL_DUE_TO_NO_LOGIN_OR_PASS="Method doesn't find reader with such login or pass ";
    public static final String RETURN_OBJECT_FIND_BOOK_BY_CATALOG="find books by catalog id. method return list size= wuth id ";
    public static final String RETURN_OBJECT_FIND_BOOK_BY_NAME="find books by name. method return list size= with name ";
    public static final String RETURN_OBJECT_FIND_BOOK_BY_AUTHOR="find books by author. method return list size= with author ";
    public static final String RETURN_OBJECT_FIND_BOOK_BY_COPY="find book by copy ";
    public static final String RETURN_OBJECT_FIND_BOOK_BY_COPY_NULL="find book by copy return null ";
    public static final String RETURN_OBJECT_FIND_COPIES_BY_BOOK="find copies by book id return list size= with id ";
    public static final String RETURN_OBJECT_FIND_COPIES_WITHOUT_FACT_DATE="find copies without data_fact_bring_back ";
    public static final String RETURN_OBJECTS="return list of objects that suits sql request ";
    public static final String RETURN_OBJECT_BY_ID="return object that suits sql request with id ";
    public static final String RETURN_NULL_READER_BY_COPY_ID="return null due to the fact that, no reader with such copy id";
    
    public static final String UNSUCCESS_PREPARED_STATEMENT_EXECUTE="unsuccess preparedStatement execute ";
    public static final String UNSUCCESS_INSERT_ORDER_BOOKS="unsuccess change order books ";
    public static final String UNSUCCESS_INSERT_COPIES_LIST="unsuccess change copies list ";
    public static final String UNSUCCESS_FIND_READER_COPIES="unsuccess find reader copies with id: ";
    public static final String UNSUCCESS_FIND_READER_ORDERS="unsuccess find reader orders with id: ";
    public static final String UNSUCCESS_UPDATE_COPY_LIST="unsuccess reader copy list update ";
    public static final String UNSUCCESS_UPDATE_ORDER_BOOK="unsuccess reader order books update ";
    public static final String UNSUCCESS_FIND_READER_BY_LOGIN_AND_PASS="unsuccess find reader by login and pass ";
    public static final String UNSUCCESS_FIND_ALL="unsucces find all ";
    public static final String UNSUCCESS_FIND_BY_ID="unsucces find by id ";
    public static final String UNSUCCESS_DELETE_AUTHOR_LIST_BY_ID="unsuccess deleting author list ";
    public static final String UNSUCCESS_CHANGE_AUTHOR_LIST="unsuccess change author list";
    public static final String UNSUCCESS_FIND_BOOK_BY_CATALOG="unsuccess find book by catalog id ";
    public static final String UNSUCCESS_FIND_BOOK_BY_NAME="unsuccess find book by name ";
    public static final String UNSUCCESS_FIND_BOOK_BY_AUTHOR="unsuccess find book by author surname ";
    public static final String UNSUCCESS_FIND_BOOK_BY_COPY="unsuccess find book by copy id ";
    public static final String UNSUCCESS_FIND_COPY_BY_BOOK="unsuccess find copy by book id ";
    public static final String UNSUCCESS_FIND_WITHOUT_FACT_DATE="unsuccess find copies without data_fact_bring_back ";
    public static final String UNSUCCESS_FIND_READER_BY_COPY="unsuccess find reader by copy id";
    
    public static final String UNSUCCESS_CONNECTION_TO_DATABASE="unsuccess connection to database";
    public static final String UNSUCCESS_CONNECTION_TO_RESOURCE="unsuccess connection to resource";
    public static final String UNSUCCESS_CLOSE_CONNECTION="unsuccess close connection";
    public static final String ADMIN_IN="admin enter the system ";
    public static final String READER_IN="reader enter the system with id= ";
    public static final String GUEST_IN="guest enter the system ";
    
    public static final String RETURN_COPIES_NULL="no available copies to take ";
}
