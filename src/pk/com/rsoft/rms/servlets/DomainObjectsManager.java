package pk.com.rsoft.rms.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.persistence.User;
//import org.springframework.boot.json.JsonParser;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import pk.com.rsoft.rms.dal.LocalDBManager;
import pk.com.rsoft.rms.dal.OBJECT_TYPE;
import pk.com.rsoft.rms.dal.ObjectTypeParser;
import pk.com.rsoft.rms.dal.REQUEST_TYPE;
import pk.com.rsoft.rms.dal.RequestTypeParser;
import pk.com.rsoft.util.RsoftUtil;

/**
 * Servlet implementation class DomainObjectsManager
 */
@WebServlet(description = "Responsibe for Adding and Updating Domain Objects based on input parameters.", urlPatterns = { "/DomainObjectsManager" })
public class DomainObjectsManager extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private LocalDBManager lmgr;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DomainObjectsManager() {
        super();
        lmgr = new LocalDBManager();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		
		StringBuffer buffer = new StringBuffer();
		String line = null;
		
//		String strJson = request.getParameter("formData");
//		System.out.println("Json is --> "+strJson);
		BufferedReader reader = request.getReader();
		while((line=reader.readLine())!=null)//read all the received json data
		{
			buffer.append(line);
		}
		
		System.out.println(buffer.toString());
		
		JsonObject jsobj = new JsonParser().parse(buffer.toString()).getAsJsonObject();//Parse json data and create JsonObject

		//Get formData and ObjectType objects from the parsed JsonObject 
		
		System.out.println(jsobj.get("formData").getAsString());
		System.out.println(OBJECT_TYPE.parse(jsobj.get("ObjType").getAsString()));
//		Object obj = 
		
		OBJECT_TYPE requestedType = OBJECT_TYPE.UNKNOWN;
		REQUEST_TYPE requestTYpe = REQUEST_TYPE.UNKNOWN;
		
		try{
			requestedType = OBJECT_TYPE.parse(jsobj.get("ObjType").getAsString());
		}catch(Exception ex)
		{
			//do nothing as we keep the default object type as UNKNOWN
		}
		
		try
		{
			requestTYpe = RequestTypeParser.parse(jsobj.get("request_type").getAsString());
		}catch(Exception ex)
		{
			//do nothing keep default request type as UNKNOWN
		}
		Gson gson = new Gson();
		
		Object obj = gson.fromJson(jsobj.get("formData").getAsString(), ObjectTypeParser.getClassByObjectType(requestedType));
		
//		System.out.println(usr.getEmail());
//		System.out.println(usr.getIsAdmin());
//		System.out.println(usr.getId());
		
		System.out.println(obj.toString());
		
		persist(obj,requestTYpe);
		out.write("<html><head><title>Response from DomainObjectsManager Servlet</title></head><body>Record saved successfully…<br>Form Data : "+jsobj.get("formData").getAsString()+"</body></html>");
		out.flush();
		out.close();
	}

	 private <T> boolean persist(T obj,REQUEST_TYPE reqType)
	{
		 if(obj instanceof User)
		 {
			 User usr = (User)obj;
			 if(!RsoftUtil.isValidMD5(usr.getPassword()))
			 {
				 usr.setPassword(RsoftUtil.getMD5(usr.getPassword()));
			 }
		 }
//		lmgr.getEntityManager().getTransaction().begin();
		 if(reqType==REQUEST_TYPE.UPDATE)
		{
			 lmgr.mergeAndCommit(obj);
			 return true;
		}
		 else //if (reqType==REQUEST_TYPE.ADD)
		 {
			 lmgr.persistAndCommit(obj);
			 //return true;
		 }
		return true;
	}
}
