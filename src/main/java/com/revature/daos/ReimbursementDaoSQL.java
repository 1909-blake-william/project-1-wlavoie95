package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.models.Reimbursement;
import com.revature.util.ConnectionUtil;

public class ReimbursementDaoSQL implements ReimbursementDao {

	private Logger log = Logger.getRootLogger();

	private Reimbursement extractReimbursement(ResultSet rs) throws SQLException {
		int ticketId = rs.getInt("reimb_id");
		double amount = rs.getDouble("reimb_amount");
		Timestamp submitted = rs.getTimestamp("reimb_submitted");
		String description = rs.getString("reimb_description");
		int authorId = rs.getInt("reimb_author");
		int statusId = rs.getInt("reimb_status_id");

		Timestamp resolved = null; // nullable value in DB
		int resolverId = 0; // nullable value in DB, 0 = unresolved

		if (statusId != 1) { // if the ticket is not pending, then there will be a resolver id and resolver
								// timestamp
			resolved = rs.getTimestamp("reimb_resolved");
			resolverId = rs.getInt("reimb_resolver");
		}

		int typeId = rs.getInt("reimb_type_id");

		return new Reimbursement(ticketId, amount, submitted, resolved, description, authorId, resolverId, statusId,
				typeId);
	}

	@Override
	public int save(Reimbursement r) {
		log.debug("attempting to save a new reimbursement to the DB");
		try (Connection c = ConnectionUtil.getConnection()) {

			String sql = "INSERT INTO ers_reimbursement (reimb_id, reimb_amount, reimb_submitted,"
					+ "reimb_description, reimb_author, reimb_type_id) "
					+ " VALUES (ers_reimbursement_id_seq.nextval , ?, ?, ?, ?, ?)";

			PreparedStatement ps = c.prepareStatement(sql);
			ps.setDouble(1, r.getAmount());
			ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			ps.setString(3, r.getDescription());
			ps.setInt(4, r.getAuthorId());
			ps.setInt(5, r.getTypeId());

			return ps.executeUpdate();
		}

		catch (

		SQLException e) {
			// TODO Auto-generated catch block
//		e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<Reimbursement> showAllReimbursements() {
		log.debug("attempting to show all reiumbursement tickets from DB");
		try (Connection c = ConnectionUtil.getConnection()) {

			String sql = "SELECT * FROM ers_reimbursement";

			PreparedStatement ps = c.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			List<Reimbursement> reimbursements = new ArrayList<Reimbursement>();
			while (rs.next()) {
				reimbursements.add(extractReimbursement(rs));
			}
			return reimbursements;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Reimbursement> getByStatusId(int statusId) {
		log.debug("attempting to show all reiumbursement tickets with selected status from DB");
		try (Connection c = ConnectionUtil.getConnection()) {

			String sql = "SELECT * FROM ers_reimbursement " + "WHERE reimb_status_id = ?";

			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, statusId);

			ResultSet rs = ps.executeQuery();

			List<Reimbursement> reimbursements = new ArrayList<Reimbursement>();
			while (rs.next()) {
				reimbursements.add(extractReimbursement(rs));
			}
			return reimbursements;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Reimbursement> viewUserTickets(int userId) {
		log.debug("attempting to show all reiumbursement tickets submitted by selected user from DB");
		try (Connection c = ConnectionUtil.getConnection()) {

			String sql = "SELECT * FROM ers_reimbursement " + "WHERE reimb_author = ?";

			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, userId);

			ResultSet rs = ps.executeQuery();

			List<Reimbursement> reimbursements = new ArrayList<Reimbursement>();
			while (rs.next()) {
				reimbursements.add(extractReimbursement(rs));
			}
			return reimbursements;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int updateStatus(int reimbursementId, int statusId, int resolverId) {
		log.debug("attempting to show all reiumbursement tickets submitted by selected user from DB");
		try (Connection c = ConnectionUtil.getConnection()) {
			String sql = "UPDATE ers_reimbursement " + "SET  reimb_resolved = ?, reimb_resolver = ?, reimb_status_id = ? "
					+ "WHERE reimb_id = ?";
			
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			ps.setInt(2, resolverId);
			ps.setInt(3, statusId);
			ps.setInt(4, reimbursementId);
			
			return ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

}
