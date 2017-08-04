package neyer.PRS.requests;

import neyer.PRS.business.Requests;
import neyer.PRS.dbutils.DBException;

public interface RequestsWriter {
	void addRequest(Requests request) throws DBException;
	void updateRequest(int id, String status) throws DBException;
}
