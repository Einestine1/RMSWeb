/**
 * 
 */
package pk.com.rsoft.rms.dal;

/**
 * @author DELL
 *
 */
public enum OBJECT_TYPE {
	PLOT,USER,PLOT_SIZE,PLOT_CATEGORY,PLOT_STATUS,UNKNOWN, MEMBER;
	
	public static OBJECT_TYPE parse(String reqType)
	{
		OBJECT_TYPE retObjTyp;

		switch(reqType.toUpperCase())
		{
		case "PLOT" :
			retObjTyp = PLOT;
			break;
		case "USER" :
			retObjTyp = USER;
			break;
		case "PLOTSIZE":
			retObjTyp = PLOT_SIZE;
			break;
		case "PLOTCATEGORY" :
			retObjTyp = PLOT_CATEGORY;
			break;
		case "PLOTSTATUS":
			retObjTyp=PLOT_STATUS;
			break;
		case "MEMBER":
			retObjTyp=MEMBER;
			break;
		default: 
			retObjTyp = UNKNOWN;
			break;
		}
		return retObjTyp;
	}
}
