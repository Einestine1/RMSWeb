package pk.com.rsoft.rms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import pk.com.rsoft.rms.dal.LocalDBManager;
import pk.com.rsoft.rms.dal.OBJECT_TYPE;
import pk.com.rsoft.rms.dal.ObjectTypeParser;
import pk.com.rsoft.util.DBManager;
import pk.com.rsoft.util.Field;
import pk.com.rsoft.util.Field.COMPARISION_OPERATOR;
import pk.com.rsoft.util.Field.FIELD_TYPE;
import pk.com.rsoft.util.RsoftUtil;

/**
 * Servlet implementation class DomainObjectsService
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/DomainObjectsServiceWithWrappedMeta" })
public class DomainObjectsServiceWithWrappedMeta extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final static Logger logger = Logger.getLogger(DomainObjectsServiceWithWrappedMeta.class);
	LocalDBManager lemgr = new LocalDBManager();
	// static{//This code was required when log4j was not setup in
	// log4j.properties file
	// BasicConfigurator.configure();
	// }

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DomainObjectsServiceWithWrappedMeta() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		
		String strReqObjType = request.getParameter("ObjType");
		logger.debug("Object Type requested -->" + strReqObjType);
//		OBJECT_TYPE reqObjType = strReqObjType == null ? OBJECT_TYPE.UNKNOWN : OBJECT_TYPE.parse(strReqObjType);
		ArrayList<?> retList = new ArrayList<>();

		boolean singleRecord = false;
		Object singleObj = new Object();
		
		try {
			singleRecord = Boolean.parseBoolean(request.getParameter("sngRec"));
		} catch (Exception ex) {
		} // do nothing and keep the default value

		String pq_curPage = request.getParameter("pq_curpage");//int pq_curPage, int pq_rPP
		String pq_rPP = request.getParameter("pq_rpp");
		
		int pageNumber = 0;
		int pageSize = 0;
		
		String strQryFilter = request.getParameter("pq_filter");
		if(strQryFilter==null)
			{
				strQryFilter = "{}";
			}
		
		if(strQryFilter!=null)
		{
			Filter filter = new Filter().parse(strQryFilter);
			if(!filter.getStrValue().equals(""))
			{
//				sendFilteredData(request,response,reqObjType, strObjType, strObjValue, operator,dataType);
				sendFilteredData(request,response,filter,strReqObjType);
				return ;
			}

		}
		
		if (pq_curPage != null) {
			pageNumber = Integer.parseInt(pq_curPage);
		} else {
			try {
				pageNumber = Integer.parseInt(request.getParameter("PageNumber"));
			} catch (Exception ex) {
			} // do nothing and keep the default value
		}
		if (pq_rPP != null) {
			pageSize = Integer.parseInt(pq_rPP);
		} else {
			try {
				pageSize = Integer.parseInt(request.getParameter("PageSize"));
			} catch (Exception ex) {
			} // do noting keep the default vale
		}
		response.setContentType("application/json");
		Gson gson = new Gson();

		PrintWriter out = response.getWriter();

		if (singleRecord) {
			singleObj= getDomainObjectByID(request);
			out.write(gson.toJson(singleObj));
		}
		else 
		{
			long totlaRecords = lemgr.getAllCount(ObjectTypeParser.getClass(strReqObjType));
			retList = lemgr.getObjectsByType(strReqObjType, pageNumber, pageSize);
			if(pageNumber<=0)
			{
				pageNumber=1;
			}
			WrappedJSONObject obj = new WrappedJSONObject(totlaRecords, pageNumber, retList);
			out.write(gson.toJson(obj));			
		}

		
		//Test 
		//End Test
		
		
		
		out.flush();
		out.close();
	}

	/**
	 * @param request
	 * @param response
	 * @param reqObjType
	 * @param strObjType
	 * @param strObjValue
	 * @param operator
	 */
	@SuppressWarnings("unused")
	private void sendFilteredData(HttpServletRequest request, HttpServletResponse response,Filter filter,OBJECT_TYPE reqObjType) {
		Field field = new Field(filter.getStrFieldName(), filter.getOperator(), filter.getStrValue());
		System.out.println(lemgr.getEntityClass("User",false).toString());
		
//		if(filter.strType==DATA_TYPE.STRING)
//		{
//			field.setFieldType(FIELD_TYPE.STRING);
//		}
//		else
//		{
//			field.setFieldType(FIELD_TYPE.LONG);
//		}

		field.setFieldType(tranlsate(filter.strType));
		
		
		ArrayList<Field> fieldList = new ArrayList<>();
		fieldList.add(field);
		
		long count = lemgr.getAllCount(ObjectTypeParser.getClassByObjectType(reqObjType), fieldList);
		ArrayList<?> retList = lemgr.getAll(ObjectTypeParser.getClassByObjectType(reqObjType), fieldList);
		
		response.setContentType("application/json");
		Gson gson = new Gson();
		try{
			PrintWriter out = response.getWriter();			
			WrappedJSONObject obj = new WrappedJSONObject(count, 1, retList);
			out.write(gson.toJson(obj));	
			out.flush();
			out.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}

	}

	private void sendFilteredData(HttpServletRequest request, HttpServletResponse response,Filter filter,String strReqObjType) {
		Field field = new Field(filter.getStrFieldName(), filter.getOperator(), filter.getStrValue());
		System.out.println(lemgr.getEntityClass("User",false).toString());
		
//		if(filter.strType==DATA_TYPE.STRING)
//		{
//			field.setFieldType(FIELD_TYPE.STRING);
//		}
//		else
//		{
//			field.setFieldType(FIELD_TYPE.LONG);
//		}

		field.setFieldType(tranlsate(filter.strType));
		
		
		ArrayList<Field> fieldList = new ArrayList<>();
		fieldList.add(field);
		
		long count = lemgr.getAllCount(ObjectTypeParser.getClass(strReqObjType), fieldList);
		ArrayList<?> retList = lemgr.getAll(ObjectTypeParser.getClass(strReqObjType), fieldList);
		
		response.setContentType("application/json");
		Gson gson = new Gson();
		try{
			PrintWriter out = response.getWriter();			
			WrappedJSONObject obj = new WrappedJSONObject(count, 1, retList);
			out.write(gson.toJson(obj));	
			out.flush();
			out.close();
		}catch(Exception ex)
		{
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
	
	class Filter{
		private String strFieldName;//Name of filed to be filtered
		private String strCondition;//Filtering condition
		private String strValue;//Value of filtered filed
		private DATA_TYPE strType;//Type of the filtered field 
		private COMPARISION_OPERATOR operator;//The comparison operator 
		public Filter()
		{
			this("","","","");
		}
		public Filter(String strFieldName,String strCondition,String strValue,String strType)
		{
			this.setStrFieldName(strFieldName);
			this.setStrCondition(strCondition);
			this.setStrValue(strValue);
			this.setStrType(DATA_TYPE.parse(strType));

		}
		String getStrFieldName() {
			return strFieldName;
		}
		void setStrFieldName(String strFieldName) {
			this.strFieldName = strFieldName;
		}
		String getStrCondition() {
			return strCondition;
		}
		void setStrCondition(String strCondition) {
			this.strCondition = strCondition;
		}
		String getStrValue() {
			return strValue;
		}
		void setStrValue(String strValue) {
			this.strValue = strValue;
		}
		DATA_TYPE getStrType() {
			return strType;
		}
		void setStrType(DATA_TYPE strType) {
			this.strType = strType;
		}
		
		
		public Filter parse(String strFilterJsonObj)
		{
			JsonObject jsobj = new JsonParser().parse(strFilterJsonObj).getAsJsonObject();//Parse json data and create JsonObject
			System.out.println(jsobj.toString());
			if(jsobj!=null)
			{
				if(jsobj.has("dataIndx"))
				{
					this.strFieldName =jsobj.get("dataIndx").getAsString();
				}
				if(jsobj.has("value"))
				{
					this.strValue = jsobj.get("value").getAsString();
				}
				if(jsobj.has("dataType"))
				{
					this.strType = DATA_TYPE.parse(jsobj.get("dataType").getAsString());
					if(strType==DATA_TYPE.STRING)
					{
						operator = COMPARISION_OPERATOR.LIKE;
					}
					else
					{
						this.operator = COMPARISION_OPERATOR.EQUAL;
					}
					
				}
				
			}

			return this;
		}
		public COMPARISION_OPERATOR getOperator() {
			return operator;
		}
		public void setOperator(COMPARISION_OPERATOR operator) {
			this.operator = operator;
		}
		
	}
	public enum DATA_TYPE {
		STRING,INTEGER,LONG,FLOAT,DOUBLE,BOOLEAN,UNKNOWN;
		public static DATA_TYPE parse(String strVal)
		{
			String val = strVal.toLowerCase();
			DATA_TYPE retType = DATA_TYPE.UNKNOWN;
			switch(val)
			{
			case "string":
				retType = STRING;
				break;
			case "integer":
				retType = INTEGER;
				break;
			case "long" :
				retType = LONG;
				break;
			case "float":
				retType = FLOAT;
				break;
			case "double":
				retType = DOUBLE;
				break;
			case "boolean":
				retType = BOOLEAN;
				break;
			default:
				break;
			}
			return retType;
		}
	}
	
	private FIELD_TYPE tranlsate(DATA_TYPE type)
	{
		FIELD_TYPE retType = FIELD_TYPE.OBJECT;
		switch(type)
		{
		case STRING:
			retType=FIELD_TYPE.STRING;
			break;
		case BOOLEAN:
			retType=FIELD_TYPE.BOOLEAN;
			break;
		case INTEGER:
			retType=FIELD_TYPE.INTEGER;
			break;
		case LONG:
			retType = FIELD_TYPE.LONG;
			break;
		case DOUBLE:
			retType = FIELD_TYPE.DOUBLE;
		case FLOAT:
			retType = FIELD_TYPE.FLOAT;
			break;
		case UNKNOWN:
			retType = FIELD_TYPE.OBJECT;
		default:
			break;
		}
		return retType;
	}
}
