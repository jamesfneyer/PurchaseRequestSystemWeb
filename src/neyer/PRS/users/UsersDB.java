package neyer.PRS.users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import neyer.PRS.business.Users;
import neyer.PRS.dbutils.ConnectionPool;
import neyer.PRS.dbutils.DBException;

public class UsersDB implements UsersDAO {
	Connection connection;
	ConnectionPool pool = ConnectionPool.getInstance();


	public UsersDB() {
	}

	public ArrayList<Users> getAllUsers() throws DBException {
		String sql = "select u.ID, Username, Password, FirstName, LastName, Phone, Email, RoleID, r.Code "
				+ "from Users u, Role r " + "where u.RoleID = r.ID;";
		ArrayList<Users> users = new ArrayList<>();
		connection = pool.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				users.add(getUserFromRow(rs));
			}
			rs.close();
			return users;
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
            pool.freeConnection(connection);
		}
	}
	

	private Users getUserFromRow(ResultSet rs) throws DBException, SQLException {
		try {
			int ID = rs.getInt("u.ID");
			int roleID = rs.getInt("RoleID");
			String username = rs.getString("Username");
			String password = rs.getString("Password");
			String firstName = rs.getString("FirstName");
			String lastName = rs.getString("LastName");
			String phone = rs.getString("Phone");
			String email = rs.getString("Email");
			String code = rs.getString("r.Code");
			Users u = new Users();
			u.setID(ID);
			u.setUsername(username);
			u.setPassword(password);
			u.setFirstName(firstName);
			u.setLastName(lastName);
			u.setPhone(phone);
			u.setEmail(email);
			u.setRole(code);
			u.setRoleID(roleID);
			return u;
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	public Users getUser(String username) throws DBException {
		String sql = "select u.ID, Username, Password, FirstName, LastName, Phone, Email, RoleID, r.Code "
				+ "from Users u, Role r " 
				+ "where u.RoleID = r.ID " 
				+ "and Username = ?; ";
		connection = pool.getConnection();
		Users u = null;
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
			u = getUserFromRow(rs);
			}
			rs.close();
			return u;
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
            pool.freeConnection(connection);
		}

	}

	public String getUsersFullname(int userID) {
		ArrayList<Users> users = new ArrayList<>();
		try {
			users = getAllUsers();
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            pool.freeConnection(connection);
		}
		String fullName = "";
		for (Users u : users) {
			if (u.getID() == userID)
				fullName = u.getLastName() + ", " + u.getFirstName();
		}
		return fullName;
	}

	public int getUserID(String username) throws DBException {
		String sql = "select ID " + "from Users " + "where Username = ?; ";
		connection = pool.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ArrayList<Users> users = getAllUsers();
			int p = 0;
			for (Users a : users) {
				if (username.equalsIgnoreCase(a.getUsername())) {
					ps.setString(1, username);
					ps.executeUpdate();
					ResultSet rs = ps.executeQuery();
					if (rs.next()) {
						p = rs.getInt("ID");
						rs.close();
						return p;

					} else {
						rs.close();
					}
				}
			}
			return p;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
            pool.freeConnection(connection);
		}
	}

	public int getRoleID(String role) throws DBException {
		String sql = "select ID, Code " + "from Role " + "where Code = ?;";
		connection = pool.getConnection();
		int p = 0;
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, role);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				p = rs.getInt("ID");
				rs.close();
				return p;

			} else {
				rs.close();
				return p;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
            pool.freeConnection(connection);
		}
	}

	public void updateUser(Users user) throws DBException {
		String sql = "UPDATE Users SET " 
				+ "FirstName = ?, " 
				+ "Password = ?, " 
				+ "LastName = ?, " 
				+ "Phone = ?, "
				+ "Email = ?, " 
				+ "RoleID = ? " 
				+ "WHERE " 
				+ "Username = ?;";
		connection = pool.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getLastName());
			ps.setString(4, user.getPhone());
			ps.setInt(6, user.getRoleID());
			ps.setString(5, user.getEmail());
			ps.setString(7, user.getUsername());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
            pool.freeConnection(connection);
		}
	}

	public void addUser(Users user) throws DBException {
		String sql = "INSERT INTO Users (Username, Password, FirstName, LastName, Phone, Email, RoleID) "
				+ "VALUES(?,?,?,?,?,?,?);";
		connection = pool.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(7, user.getRoleID());
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getFirstName());
			ps.setString(4, user.getLastName());
			ps.setString(5, user.getPhone());
			ps.setString(6, user.getEmail());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
            pool.freeConnection(connection);
		}
	}

}
