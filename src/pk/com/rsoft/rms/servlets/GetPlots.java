package pk.com.rsoft.rms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.persistence.Plot;

import com.google.gson.Gson;
import pk.com.rsoft.rms.dal.REQUEST_TYPE;
import pk.com.rsoft.rms.dal.RequestTypeParser;
import pk.com.rsoft.util.DBManager;

/**
 * Servlet implementation class GetPlots
 */
@WebServlet("/getPlots")
public class GetPlots extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static DBManager dbmgr = new DBManager();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetPlots() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		ArrayList<JsonObject> plotslist = new ArrayList<JsonObject>();
		String strReqType = request.getParameter("request_type");
		REQUEST_TYPE requestType = REQUEST_TYPE.UNKNOWN;
		
		if(strReqType!=null)
		{
			requestType = RequestTypeParser.parse(strReqType);
		}
		
		if(requestType==REQUEST_TYPE.FETCH)
		{
			int pageNumber = 1;
			int pageSize = 25;
			
			String strPageNumber = request.getParameter("pageNumber");
			String strPageSize = request.getParameter("pageSize");
			
			if(strPageNumber!=null)
			{
				pageNumber = Integer.parseInt(strPageNumber);
			}
			if(strPageSize!=null)
			{
				pageSize = Integer.parseInt(strPageSize);
			}
			
			ArrayList<Plot> plotsList = getPlots(pageNumber, pageSize);
					
			ArrayList<String> strList = new ArrayList<String>();
			strList.add("Item 1 - Rehan");
			strList.add("Item 2 - Fatima");
			
			ArrayList<Object> lst = new ArrayList<>();
			
			String json= new Gson().toJson(plotsList);
			
			lst.add(plotsList);
			lst.add(strList);
			
			response.setContentType("Application/json");
			PrintWriter out = response.getWriter();
			out.write(json);
			out.flush();
			out.close();
			return ;
		}
		

		response.getWriter().append("Served at: ").append(request.getContextPath());
		
	}

//	private ArrayList<JsonObject> getPlots(int pageIndex,int pageSize)
//	{
//		return new ArrayList<JsonObject>();
//	}
	
	private ArrayList<Plot> getPlots(int pageNumber,int pageSize)
	{
		return dbmgr.getAll(new Plot(),pageNumber, pageSize);
	}
}
