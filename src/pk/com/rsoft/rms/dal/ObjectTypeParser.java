package pk.com.rsoft.rms.dal;

import org.apache.log4j.Logger;
import org.persistence.Member;
import org.persistence.Plot;
import org.persistence.PlotCategory;
import org.persistence.PlotSize;
import org.persistence.PlotStatus;
import org.persistence.User;


public class ObjectTypeParser {

	private static final Logger logger = Logger.getLogger(ObjectTypeParser.class);
	private ObjectTypeParser() {
		// TODO Auto-generated constructor stub
	}

	public static Class<?> getClassByObjectType(OBJECT_TYPE objType)
	{
		Class<?> retVal= Object.class;
		switch(objType){
		case PLOT:
			retVal =  Plot.class;
			break;
		case USER:
			retVal = User.class;
			break;
		case PLOT_SIZE:
			retVal = PlotSize.class;
			break;
		case PLOT_CATEGORY:
			retVal = PlotCategory.class;
			break;
		case MEMBER:
			retVal = Member.class;
			break;
		case PLOT_STATUS:
			retVal = PlotStatus.class;
			break;
		case UNKNOWN:
			retVal =OBJECT_TYPE.class;
		default:
			break;
		}
		
		return retVal;
	}
	
	public static Class<?> getClass(String className)
	{
		logger.info("Class name is --->> " +className);
		return new LocalDBManager().getEntityClass(className,false);
	}
}
