package apotik;

import java.sql.*;

/**
 *
 * @author Ryan
 */
public class sqlConfig {
    sqlConfig()
	{}
	Connection con;

	public Connection db()
	{
		Connection conect = null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
            conect = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbApotik","root","");
		}
		catch(ClassNotFoundException e)
		{}
		catch(SQLException e)
		{}
		return conect;
	}
}
