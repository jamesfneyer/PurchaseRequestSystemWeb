package neyer.PRS.requests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

import neyer.PRS.business.Requests;
import neyer.PRS.dbutils.ConnectionPool;
import neyer.PRS.dbutils.DBException;

public class RequestsDB implements RequestsDAO {
	ConnectionPool pool = ConnectionPool.getInstance();
	Connection connection;

	public RequestsDB() {
	}

	public ArrayList<Requests> getAllRequests() throws DBException {
		String sql = "select * from Requests;";
		connection = pool.getConnection();
		ArrayList<Requests> requests = new ArrayList<>();
		try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				requests.add(getRequestFromRow(rs));
			}
			rs.close();
			return requests;
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
            pool.freeConnection(connection);
		}
	}
	
	public ArrayList<Requests> getSubmittedRequests() throws DBException {
		String sql = "select * from Requests "
				+ "where Status = 'submitted';";
		connection = pool.getConnection();
		ArrayList<Requests> requests = new ArrayList<>();
		try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				requests.add(getRequestFromRow(rs));
			}
			rs.close();
			return requests;
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
            pool.freeConnection(connection);
		}
	}

	public ArrayList<Requests> getUserRequests(int userID) throws DBException {
		String sql = "select * from Requests where UserID = ?;";
		connection = pool.getConnection();
		ArrayList<Requests> requests = new ArrayList<>();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, userID);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				requests.add(getRequestFromRow(rs));
			}
			rs.close();
			return requests;
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
            pool.freeConnection(connection);
		}
	}

	private Requests getRequestFromRow(ResultSet rs) throws DBException, SQLException {
		try {
			int id = rs.getInt("ID");
			String description = rs.getString("description");
			String justification = rs.getString("Justification");
			Date dateNeeded = rs.getDate("DateNeeded");
			String deliveryMode = rs.getString("DeliveryMode");
			boolean docAttached = rs.getBoolean("DocAttached");
			String status = rs.getString("Status");
			double total = rs.getDouble("Total");
			Date dateSubmitted = rs.getDate("SubmittedDate");

			Requests r = new Requests();
			r.setID(id);
			r.setDateNeeded(dateNeeded);
			r.setDescription(description);
			r.setJustification(justification);
			r.setDeliveryMode(deliveryMode);
			r.setDocAttached(docAttached);
			r.setStatus(status);
			r.setTotal(total);
			r.setSubmittedDate(dateSubmitted);
			return r;

		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	public int getRequestID(String description, String justification) throws DBException {
		String sql = "select ID, Description from Requests where Description = ? AND Justification = ?;";
		connection = pool.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {

			int p = 0;
			ps.setString(1, description);
			ps.setString(2, justification);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				p = rs.getInt("ID");
				rs.close();

			} else {
				rs.close();
			}

			return p;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally{
            pool.freeConnection(connection);

		}
	}

	public void addRequest(Requests request) throws DBException {
		String sql = "INSERT INTO Requests "
				+ "(Description, Justification, DateNeeded, UserID, DeliveryMode, DocAttached, Status, Total, SubmittedDate) "
				+ "VALUES(?,?,?,?,?,?,?,?,?);";
		connection = pool.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, request.getDescription());
			ps.setString(2, request.getJustification());
			ps.setDate(3, request.getDateNeeded());
			ps.setInt(4, request.getUsersID());
			ps.setString(5, request.getDeliveryMode());
			ps.setBoolean(6, request.isDocAttached());
			ps.setString(7, request.getStatus());
			ps.setDouble(8, request.getTotal());
			ps.setDate(9, request.getSubmittedDate());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
            pool.freeConnection(connection);
		}
	}

	public void updateRequest(int id, String status) throws DBException {
		String sql = "UPDATE Requests SET "
				+ "Status = ? " 
				+ "Where ID = ?;";
		connection = pool.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, status);
			ps.setInt(2, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
            pool.freeConnection(connection);
		}
	}

}
