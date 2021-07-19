package pk.com.rsoft.rms.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.persistence.User;

import pk.com.rsoft.rms.dal.REQUEST_TYPE;
import pk.com.rsoft.rms.dal.RequestTypeParser;
import pk.com.rsoft.util.DBManager;
import pk.com.rsoft.util.RsoftUtil;


/**
 * Servlet implementation class ManageUsers
 */
@WebServlet("/ManageUsers")
public class ManageUsers extends HttpServlet {
	
	final static Logger logger = Logger.getLogger(ManageUsers.class);
	private static final long serialVersionUID = 1L;
    private static DBManager dbmgr;
    
    static {
    	dbmgr = new DBManager();
    	BasicConfigurator.configure();
    }
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageUsers() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		
		String  strRequestType = request.getParameter("request_type");
		REQUEST_TYPE reqType = RequestTypeParser.parse(strRequestType);

		 logger.debug(reqType.toString());
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		if(reqType==REQUEST_TYPE.ADD)
		{
			String strUserName = request.getParameter("userName");
			String password = RsoftUtil.getMD5(request.getParameter("pass"));
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String email= request.getParameter("emailAddress");
			String strIsAdmin = request.getParameter("isAdmin");
			
			User user = new User();
			user.setUserName(strUserName);
			user.setPassword(password);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmail(email);

			user.setIsAdmin((strIsAdmin.equals("Yes")?true:false));

			if(!isDuplicate(user))
			{
				addUser(user);
				out.write("<html><body>"+user.getUserName()+" Added!</body></html>");
			}
			else
			{
				out.write("<html><body>"+user.getUserName()+" Already exists!</body></html>");
			}
		}
		else if(reqType==REQUEST_TYPE.UPDATE)
		{
			String strUserName = request.getParameter("userName");
			String password = RsoftUtil.getMD5(request.getParameter("pass"));	
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String email= request.getParameter("emailAddress");			
			String strIsAdmin = request.getParameter("isAdmin");

			User user = new User();
			user.setUserName(strUserName);
			user.setPassword(password);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmail(email);		
			user.setIsAdmin((strIsAdmin.equals("Yes")?true:false));

			
			updateUser(user);
			out.write("<html><body>"+user.getUserName()+" updated!</body></html>");
		}
		out.flush();
		out.close();
	}
	private boolean addUser(User user)
	{
		dbmgr.createUser(user);
		
		return true;
	}
	
	private boolean updateUser(User user)
	{

		logger.debug("User is :" +user.toString());

		dbmgr.updateUser(user);
		return true;
	}
	
	private boolean isDuplicate(User user)
	{
		try{
				if(dbmgr.findById(user, "userName", user.getUserName())!=null)
				{
					return true;
				}
			}
		catch(NoResultException nore)
		{
			return false;
		}
		return false;
	}
}


