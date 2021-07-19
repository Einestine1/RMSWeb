package pk.com.rsoft.rms.dal;

import java.util.ArrayList;
import java.util.List;

import org.persistence.User;

import pk.com.rsoft.util.DBManager;
import pk.com.rsoft.util.Field;
import pk.com.rsoft.util.RsoftUtil;

public class Login {

	private String userName="";
	private String password="";
	private long userId=-1;
	private static DBManager dbmgr;
	private boolean isPasswordEncoded = false;
	
	
	static
	{
		dbmgr = new DBManager();
	}
	
	public Login(String userName, String password) {
		// TODO Auto-generated constructor stub
		setUserName(userName);
		setPassword(password);
		
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public long getUserId()
	{
		return userId;
	}
	@SuppressWarnings("unused")
	private void encodePassword()
	{
		if(!isPasswordEncoded)
		{
			this.password= RsoftUtil.getMD5(this.password);
			isPasswordEncoded = true;
		}
	}
	public boolean checkLogin()
	{
		if(userName==null)
		{
			return false;
		}
		if(password==null)
		{
			return false;
		}
		if(userName.isEmpty()||password.isEmpty())
		{
			return false;
		}

		String query = "Select u from User u where u.userName = :userName";
		ArrayList<Field> params = new ArrayList<Field>();
		params.add(new Field("userName", userName));

		List<User> lst = dbmgr.executeQuery(User.class, query, params);
		for(User usr : lst)
		{
//			System.out.println("\n\nUser found:-");
//			System.out.println(usr.getUserName()+" " + usr.getPassword());
			userId = usr.getId();
			if(RsoftUtil.match(usr.getPassword(), password))
			{
//				System.out.println(usr.getPassword() + "      " + password );
				return true;
			}
			
		}
			if(lst.size()<1)
			{
				System.out.println("\n\nUser NOT found!!");
				System.out.println();
			}
			else
			{
				System.out.println("\n\nPassword match failed!!");
				System.out.println();			
			}
			
			return false;

	}

}
