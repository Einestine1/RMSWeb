package pk.com.rsoft.rms.dal;

public class RequestTypeParser {

	private RequestTypeParser() {
		// TODO Auto-generated constructor stub
	}
	public static REQUEST_TYPE parse(String RequestType)
	{
		REQUEST_TYPE reqTupe;
		RequestType = RequestType.toUpperCase();
		switch (RequestType) {
		case "ADD":
			reqTupe = REQUEST_TYPE.ADD;
			break;
		case "UPDATE":
			reqTupe = REQUEST_TYPE.UPDATE;
			break;
			
		case "DELETE":
			reqTupe = REQUEST_TYPE.DELETE;
			break;
			
		case "LOGIN":
			reqTupe = REQUEST_TYPE.LOGIN;
			break;

		case "LOGOUT":
			reqTupe = REQUEST_TYPE.LOGOUT;
			break;
		case "FETCH":
			reqTupe = REQUEST_TYPE.FETCH;
			break;
		case "SEARCH":
			reqTupe = REQUEST_TYPE.SEARCH;
			break;
		case "OBJECT_STRUCTURE":
			reqTupe = REQUEST_TYPE.OBJECT_STRUCTURE;
			break;
		default:
			reqTupe = REQUEST_TYPE.UNKNOWN;
			break;
		}
		return reqTupe;
	}
	
}
