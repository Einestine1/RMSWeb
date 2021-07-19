/**
 * 
 */
package pk.com.rsoft.rms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pk.com.rsoft.rms.dal.Login;
import pk.com.rsoft.rms.dal.REQUEST_TYPE;
import pk.com.rsoft.rms.dal.RequestTypeParser;

/**
 * @author DELL
 *
 */
@WebServlet(name="login",urlPatterns={"/doLogin"})
public class DoLogin extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final static Logger logger = Logger.getLogger(DoLogin.class);

//	static{
//		BasicConfigurator.configure();
//	}
	/**
	 * 
	 */

	public DoLogin() {
		// TODO Auto-generated constructor stub
	}

@Override
public void doPost(HttpServletRequest req, HttpServletResponse res)
{
	doLoginUser(req, res);
//	try {
////		res.setContentType("text/html");
//
//		
//		res.setContentType("Appication/json");
//		ArrayList<String> lst = new ArrayList<String>();
//		lst.add("Rehan");
//		lst.add("Farooq");
//		String json = new Gson().toJson(lst);		
//		
//		PrintWriter out  =  res.getWriter();
//
//		out.write(json);
//		
//		out.flush();
//		out.close();
//	} catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
}	
@Override
public void doGet(HttpServletRequest req, HttpServletResponse res)
{

	doLoginUser(req, res);

}

private void doLoginUser(HttpServletRequest req, HttpServletResponse res) {
	try {
		
		String strReqType = req.getParameter("request_type");
		REQUEST_TYPE reqType = REQUEST_TYPE.UNKNOWN;
		if(strReqType!=null)
		{
			reqType = RequestTypeParser.parse(req.getParameter("request_type"));
		}
		if(reqType==REQUEST_TYPE.LOGOUT)
		{
			setLoginSession(req, res, null, false);
			sendLogoutMsg(res);
			return;
		}
		
		String user = req.getParameter("Login");
		String pass = req.getParameter("password");
		Login login = new Login(user, pass);
		boolean retvale = login.checkLogin();


		if(retvale==true)
		{

			logger.debug("User "+login.getUserName()+" :Login Successfull..............");
			setLoginSession(req, res, login, true);
			res.setContentType("text/html");
			PrintWriter out  =  res.getWriter();
			out.write("<html><head></head> <body><div style=\"color:blue\"> Hello <i>"+ user +"</i> from DoLogin Servlet!</div> </body> </html>");			
			out.flush();
			out.close();

		}
		else
		{
			setLoginSession(req, res, login, false);
			logger.debug("User "+login.getUserName()+" :Login Failed....................");

			res.setContentType("text/html");
			PrintWriter out  =  res.getWriter();
			/*Got stuck here because unintentionally 
			 * was just changing the <title></title> text of the response!!!!!! 
			*/
			out.write("<html><head> </head> <body><div style=\"color:Red\"> Login failed for user  <i>"+ user +"</i> </div></body> </html>");
			out.flush();
			out.close();

		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

void setLoginSession(HttpServletRequest req,  HttpServletResponse res, Login login, Boolean isLogedIn)
{
	HttpSession session = req.getSession();
	session.setMaxInactiveInterval(5000);
	if(isLogedIn)
	{
		session.setAttribute("LoginName", login.getUserName());
	    session.setAttribute("logedin", new Boolean(true));
	    session.setAttribute("uid", new Long(login.getUserId()));
	}
	else
	{
		session.setAttribute("LoginName", "");
	    session.setAttribute("logedin", new Boolean(false));
	    session.setAttribute("uid", new Long(-1));
	}
}

void sendLogoutMsg(HttpServletResponse res)
{
	try {
//		res.setContentType("text/html");
//		
//		PrintWriter out = res.getWriter();
//		out.println(logoutMsg);
//		out.flush();
//		out.close();
		res.sendRedirect("jsp/loggedout.jsp");
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
}
