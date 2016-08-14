package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.command.Command;
import controller.command.CommandConstant;
import controller.command.CommandList;
import view.PagesPath;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			String commandName = request.getParameter(CommandConstant.COMMAND);
			Command command = CommandList.valueOf(commandName).getCommand();
			String goTo = command.execute(request, response);
			request.getRequestDispatcher(goTo).forward(request, response);
		}catch(RuntimeException e){
			request.setAttribute(CommandConstant.CRITICAL_ERROR, e.getMessage());
			request.getRequestDispatcher(PagesPath.ERROR_PAGE).forward(request, response);
		}
		
	}

}
