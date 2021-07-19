package pk.com.rsoft.util;

import java.lang.annotation.Annotation;

import javax.persistence.Id;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class RsoftUtil {
	
	private static BCryptPasswordEncoder bEncoder;
	private RsoftUtil() {
		// TODO Auto-generated constructor stub
	}
	
	static{
		bEncoder = new BCryptPasswordEncoder();
	}
	public static String getMD5(String val) {
		
		String endocdedStr = bEncoder.encode(val);
		return endocdedStr;

	}
	
	public static boolean match(String existingPasswordHash, String password)
	{
		return bEncoder.matches(password,existingPasswordHash);
	}
	
	public static String getPKColumnName(Class<?> javaObj) {

	    if (javaObj == null) 
	        return null;

	    String name = null;

	    for (java.lang.reflect.Field f : javaObj.getDeclaredFields()) {

	        Id id = null;

	        Annotation[] as = f.getAnnotations();
	        for (Annotation a : as) {
	            if (a.annotationType() == javax.persistence.Id.class) 
	                id = (Id) a;
	        }

	        if (id != null){
	            name = f.getName();
	            break;
	        }
	    }

	    if (name == null && javaObj.getSuperclass() != Object.class)
	        name = getPKColumnName(javaObj.getSuperclass());

	    return name;
	}
	
	public static String toSentenceCase(String string)
	{
		return string.substring(0, 1).toUpperCase() + string.substring(1); 
	}
	
	public static boolean isValidSHA1(String s) {
	    return s.matches("[a-fA-F0-9]{40}");
	}
	
	public static boolean isValidMD5(String s) {
	    return s.matches("[a-fA-F0-9]{32}");
	}
	
}
