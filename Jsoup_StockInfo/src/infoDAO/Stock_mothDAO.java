package infoDAO;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import stock_beans.Stock_monthBean;

public class Stock_mothDAO {
	public static final String HOST = "127.0.0.1";
	public static final String DB = "jdbcDB";
	public static final String USER = "root";
	public static final String PASSWORD = "jackjava003";
	public static final String URL = "jdbc:mysql://" + HOST + ":3306/"
			+ SystemConstant.DB + "?user=" + SystemConstant.USER + "&password="
			+ SystemConstant.PASSWORD
			+ "&useSSL=true&useUnicode=yes&characterEncoding=UTF-8";

	public Stock_mothDAO() {
	}

	public Stock_monthBean findByPrimaryKey(int stock_id, String month) {
		Stock_monthBean stock_month = null;

		try (Connection con = DriverManager.getConnection(URL);
				CallableStatement csMI = con
						.prepareCall("{ call myMonthOneInfo(?,?,?,?,?,?,?,?,?) }");) {
			csMI.setInt(1, stock_id);
			csMI.setString(2, month);
			csMI.registerOutParameter(1, Types.INTEGER);
			csMI.registerOutParameter(2, Types.VARCHAR);
			csMI.registerOutParameter(3, Types.BIGINT);
			csMI.registerOutParameter(4, Types.DOUBLE);
			csMI.registerOutParameter(5, Types.BIGINT);
			csMI.registerOutParameter(6, Types.DOUBLE);
			csMI.registerOutParameter(7, Types.BIGINT);
			csMI.registerOutParameter(8, Types.DOUBLE);
			csMI.registerOutParameter(9, Types.VARCHAR);
			csMI.execute();
			
			stock_month = new Stock_monthBean();
			stock_month._setStock_id(csMI.getInt(1));
			stock_month._setMonth(csMI.getString(2));
			stock_month._setRev_104(csMI.getLong(3));
			stock_month._setIncr_rate_104(new BigDecimal(String.valueOf(csMI.getDouble(4))));
			stock_month._setRev_105(csMI.getLong(5));
			stock_month._setIncr_rate_105(new BigDecimal(String.valueOf(csMI.getDouble(6))));
			stock_month._setCumu_rev_105(csMI.getLong(7));
			stock_month._setAnnu_rate_105(new BigDecimal(String.valueOf(csMI.getDouble(8))));
			stock_month._setAchieve_rate_105(csMI.getString(9));

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return stock_month;
	}

	public List<Stock_monthBean> findAll() {
		Stock_monthBean stock_month = null;
		List<Stock_monthBean> list = new ArrayList<>();

		try (Connection con = DriverManager.getConnection(URL);
				CallableStatement csMI = con
						.prepareCall("{ call myMonthInfoALL() }");) {
			ResultSet rs = csMI.executeQuery();
			while (rs.next()) {
				stock_month = new Stock_monthBean();
				stock_month._setStock_id(rs.getInt(1));
				stock_month._setMonth(rs.getString(2));
				stock_month._setRev_104(rs.getLong(3));
				stock_month._setIncr_rate_104(rs.getBigDecimal(4));
				stock_month._setRev_105(rs.getLong(5));
				stock_month._setIncr_rate_105(rs.getBigDecimal(6));
				stock_month._setCumu_rev_105(rs.getLong(7));
				stock_month._setAnnu_rate_105(rs.getBigDecimal(8));
				stock_month._setAchieve_rate_105(rs.getString(9));
				list.add(stock_month);
			}

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return list;
	}

	public List<Stock_monthBean> findByPrimaryKey(int stock_id) {
		Stock_monthBean stock_month = null;
		List<Stock_monthBean> list = new ArrayList<>();

		try (Connection con = DriverManager.getConnection(URL);
				CallableStatement csMI = con
						.prepareCall("{ call myMonthlyInfo(?) }");) {
			csMI.setInt(1, stock_id);
			ResultSet rs = csMI.executeQuery();
			while (rs.next()) {
				stock_month = new Stock_monthBean();
				stock_month._setStock_id(rs.getInt(1));
				stock_month._setMonth(rs.getString(2));
				stock_month._setRev_104(rs.getLong(3));
				stock_month._setIncr_rate_104(rs.getBigDecimal(4));
				stock_month._setRev_105(rs.getLong(5));
				stock_month._setIncr_rate_105(rs.getBigDecimal(6));
				stock_month._setCumu_rev_105(rs.getLong(7));
				stock_month._setAnnu_rate_105(rs.getBigDecimal(8));
				stock_month._setAchieve_rate_105(rs.getString(9));
				list.add(stock_month);
			}

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return list;
	}
	
	public void delete(int stock_id, String month) {
		try (Connection con = DriverManager.getConnection(URL);
				CallableStatement csM = con.prepareCall("{call myMonthDelOne(?,?)}");) {

			csM.setInt(1, stock_id);
			csM.setString(2, month);
			csM.execute();

			System.out.println("從monthly_change中刪除成功  股票代號:" + stock_id +"月份:"+month);

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public void delete(int stock_id) {
		try (Connection con = DriverManager.getConnection(URL);
				CallableStatement csM = con.prepareCall("{call myMonthDel(?)}");) {

			csM.setInt(1, stock_id);
			csM.execute();
			System.out.println("從monthly_change中刪除成功  股票代號:" + stock_id);

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	public int update(Stock_monthBean sm) {
		int updateCount = 0;
		String sql = "UPDATE monthly_change SET 104_rev = ?, 104_incr_rate = ?,"
				+ " 105_rev = ?, 105_incr_rate = ?,  105_cumu_rev = ? , 105_annu_rate = ? , 105_achieve_rate= ?"
				+ " where stock_id = ? and month = ? ";
		try (Connection con = DriverManager.getConnection(URL);
				PreparedStatement pstmt = con.prepareStatement(sql);) {

			Stock_infoDAO.checkLongNullInsert(pstmt, sm._getRev_104(), 1);
			Stock_infoDAO.checkBDNullInsert(pstmt, sm._getIncr_rate_104(), 2);
			Stock_infoDAO.checkLongNullInsert(pstmt, sm._getRev_105(), 3);
			Stock_infoDAO.checkBDNullInsert(pstmt, sm._getIncr_rate_105(), 4);
			Stock_infoDAO.checkLongNullInsert(pstmt, sm._getCumu_rev_105(), 5);
			Stock_infoDAO.checkBDNullInsert(pstmt, sm._getAnnu_rate_105(), 6);
			pstmt.setString(7, sm._getAchieve_rate_105());
			pstmt.setInt(8, sm._getStock_id());
			pstmt.setString(9, sm._getMonth());
			updateCount = pstmt.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		}
		System.out.println("Update成功" + ", 影響row數: " + updateCount);
		return updateCount;
	}

	public int insert(Stock_monthBean sm) {
		int updateCount = 0;
		String sqlStock_month = "INSERT INTO monthly_change VALUES("
				+ " ?, ?, ?, ?, ?, ?, ?,?,? )";
		try (Connection con = DriverManager.getConnection(URL);
				PreparedStatement pstmtStock_month = con
						.prepareStatement(sqlStock_month);) {
			pstmtStock_month.setInt(1, sm._getStock_id());
			pstmtStock_month.setString(2, sm._getMonth());
			Stock_infoDAO.checkLongNullInsert(pstmtStock_month,
					sm._getRev_104(), 3);
			Stock_infoDAO.checkBDNullInsert(pstmtStock_month,
					sm._getIncr_rate_104(), 4);
			Stock_infoDAO.checkLongNullInsert(pstmtStock_month,
					sm._getRev_105(), 5);
			Stock_infoDAO.checkBDNullInsert(pstmtStock_month,
					sm._getIncr_rate_105(), 6);
			Stock_infoDAO.checkLongNullInsert(pstmtStock_month,
					sm._getCumu_rev_105(), 7);
			Stock_infoDAO.checkBDNullInsert(pstmtStock_month,
					sm._getAnnu_rate_105(), 8);
			pstmtStock_month.setString(9, sm._getAchieve_rate_105());

			updateCount = pstmtStock_month.executeUpdate();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		System.out.println("新增成功" + ", 影響row數: " + updateCount);

		return updateCount;
	}
}
