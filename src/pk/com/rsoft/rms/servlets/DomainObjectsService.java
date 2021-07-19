package pk.com.rsoft.rms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import com.google.gson.Gson;
import pk.com.rsoft.rms.dal.LocalDBManager;
import pk.com.rsoft.rms.dal.ObjectTypeParser;
import pk.com.rsoft.util.DBManager;
import pk.com.rsoft.util.RsoftUtil;

/**
 * Servlet implementation class DomainObjectsService
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/DomainObjectsService" })
public class DomainObjectsService extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final static Logger logger = Logger.getLogger(DomainObjectsService.class);
	LocalDBManager lemgr = new LocalDBManager();
	// static{//This code was required when log4j was not setup in
	// log4j.properties file
	// BasicConfigurator.configure();
	// }

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DomainObjectsService() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String strReqObjType = request.getParameter("ObjType");
		
//		String strRequestType = request.getParameter("request_type");
//		if(strRequestType!=null)
//		{
//			sendObjectStructure(request,response,strReqObjType);
//			return;
//		}
		logger.debug("Object Type requested -->" + strReqObjType);
//		OBJECT_TYPE reqObjType = strReqObjType == null ? OBJECT_TYPE.UNKNOWN : OBJECT_TYPE.parse(strReqObjType);
		ArrayList<?> retList = new ArrayList<>();

		boolean singleRecord = false;
		Object singleObj = new Object();
		
		try {
			singleRecord = Boolean.parseBoolean(request.getParameter("sngRec"));
		} catch (Exception ex) {
		} // do nothing and keep the default value

		
		int pageNumber = 0;
		int pageSize = 0;
		
			try {
				pageNumber = Integer.parseInt(request.getParameter("PageNumber"));
			} catch (Exception ex) {
			} // do nothing and keep the default value

			try {
				pageSize = Integer.parseInt(request.getParameter("PageSize"));
			} catch (Exception ex) {
			} // do noting keep the default vale
		response.setContentType("application/json");
		Gson gson = new Gson();

		PrintWriter out = response.getWriter();

		if (singleRecord) {
			singleObj= getDomainObjectByID(request);
			out.write(gson.toJson(singleObj));
		}
		else 
		{

			retList = lemgr.getObjectsByType(strReqObjType, pageNumber, pageSize);
			out.write(gson.toJson(retList));			
		}

		out.flush();
		out.close();
	}

	@SuppressWarnings("unused")
	private void sendObjectStructure(HttpServletRequest request, HttpServletResponse response, String strReqObjType) 
	{
		Constructor<?> construct;
		try {
				construct = ObjectTypeParser.getClass(strReqObjType).getConstructor();
				Object obj = construct.newInstance();
				Gson gson = new Gson();
				response.setContentType("application/json");
				PrintWriter out = response.getWriter();
				out.write(gson.toJson(obj).toString());
				out.flush();
				out.close();
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		} 
	}

	private Object getDomainObjectByID(HttpServletRequest request) {
		String strObjType = request.getParameter("ObjType");
		String strIDValue = request.getParameter("IdValue");

		Object obj = new Object();

		if (strObjType != null) {

			DBManager dbmgr = new DBManager();

			Metamodel model = dbmgr.getEntityManager().getMetamodel();

			Pattern thePattern = Pattern.compile(".*\\." + strObjType);
			Matcher m = null;
			for (EntityType<?> e : model.getEntities()) {
				m = thePattern.matcher(e.getJavaType().getName());
				if (m.matches()) {
					Object value = new Object();
					try {
						value = (int) Integer.parseInt(strIDValue);						
					} catch (Exception exOutter) {
						exOutter.printStackTrace();
						try {
							value = (long) Long.parseLong(strIDValue);
						} catch (Exception exInner) {
							exInner.printStackTrace();
							value = strIDValue.toString();
						}
					}

					obj = dbmgr.findById(e.getJavaType(), RsoftUtil.getPKColumnName(e.getJavaType()), value);

				}

			}
		}
		return obj;
	}
	@SuppressWarnings("unused")	
	class WrappedJSONObject {

		private Object data;
		private long NumberOfRecords;
		private long PageNumber;
		public WrappedJSONObject(long TotalNumberOfRecoreds,long PageNumber,Object data)
		{
			this.data = data;
			this.NumberOfRecords = TotalNumberOfRecoreds;
			this.PageNumber = PageNumber;
		}
	}
}
