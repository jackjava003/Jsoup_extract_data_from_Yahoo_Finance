package infoDAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import stock_beans.Stock_quartBean;



public class Stock_quartDAO {
	public static final String HOST = "127.0.0.1";
	public static final String DB = "jdbcDB";
	public static final String USER = "root";
	public static final String PASSWORD = "jackjava003";
	public static final String URL = "jdbc:mysql://" + HOST + ":3306/"
			+ SystemConstant.DB + "?user=" + SystemConstant.USER + "&password="
			+ SystemConstant.PASSWORD
			+ "&useSSL=true&useUnicode=yes&characterEncoding=UTF-8";

	public Stock_quartDAO() {
	}
	
	public Stock_quartBean findByPrimaryKey(int stock_id, int  quart) {
		Stock_quartBean stock_quart = null;

		try (Connection con = DriverManager.getConnection(URL);
				CallableStatement csQ = con
						.prepareCall("{ call myQuartOneInfo(?,?,?,?,?,?,?,?,?,?,?,?) }");) {
			csQ.setInt(1, stock_id);
			csQ.setInt(2, quart);
			csQ.registerOutParameter(1, Types.INTEGER);
			csQ.registerOutParameter(2, Types.INTEGER);
			csQ.registerOutParameter(3, Types.BIGINT);
			csQ.registerOutParameter(4, Types.VARCHAR);
			csQ.registerOutParameter(5, Types.BIGINT);
			csQ.registerOutParameter(6, Types.VARCHAR);
			csQ.registerOutParameter(7, Types.BIGINT);
			csQ.registerOutParameter(8, Types.VARCHAR);
			csQ.registerOutParameter(9, Types.VARCHAR);
			csQ.registerOutParameter(10, Types.BIGINT);
			csQ.registerOutParameter(11, Types.VARCHAR);
			csQ.registerOutParameter(12, Types.VARCHAR);
			csQ.execute();
			
			stock_quart = new Stock_quartBean();
			stock_quart._setStock_id(csQ.getInt(1));
			stock_quart._setQuarterly(csQ.getInt(2));
			stock_quart._setProfit_AT_104(csQ.getLong(3));
			stock_quart._setProfit_rate_AT_104(csQ.getString(4));
			stock_quart._setProfit_BT_104(csQ.getLong(5));
			stock_quart._setProfit_rate_BT_104(csQ.getString(6));
			stock_quart._setProfit_AT_105(csQ.getLong(7));
			stock_quart._setProfit_rate_AT_105(csQ.getString(8));
			stock_quart._setAchieve_rate_AT_105(csQ.getString(9));
			stock_quart._setProfit_BT_105(csQ.getLong(10));
			stock_quart._setProfit_rate_BT_105(csQ.getString(11));
			stock_quart._setAchieve_rate_BT_105(csQ.getString(12));

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return stock_quart;
	}

	
	public List<Stock_quartBean> findAll() {
		Stock_quartBean stock_quart = null;
		List<Stock_quartBean> list = new ArrayList<>();

		try (Connection con = DriverManager.getConnection(URL);
				CallableStatement csQ = con
						.prepareCall("{ call myQuartInfoALL() }");) {
			ResultSet rs = csQ.executeQuery();
			while (rs.next()) {
				stock_quart = new Stock_quartBean();
				stock_quart._setStock_id(rs.getInt(1));
				stock_quart._setQuarterly(rs.getInt(2));
				stock_quart._setProfit_AT_104(rs.getLong(3));
				stock_quart._setProfit_rate_AT_104(rs.getString(4));
				stock_quart._setProfit_BT_104(rs.getLong(5));
				stock_quart._setProfit_rate_BT_104(rs.getString(6));
				stock_quart._setProfit_AT_105(rs.getLong(7));
				stock_quart._setProfit_rate_AT_105(rs.getString(8));
				stock_quart._setAchieve_rate_AT_105(rs.getString(9));
				stock_quart._setProfit_BT_105(rs.getLong(10));
				stock_quart._setProfit_rate_BT_105(rs.getString(11));
				stock_quart._setAchieve_rate_BT_105(rs.getString(12));
				list.add(stock_quart);
			}

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return list;
	}
	
	public List<Stock_quartBean> findByPrimaryKey(int stock_id) {
		Stock_quartBean stock_quart = null;
		List<Stock_quartBean> list = new ArrayList<>();

		try (Connection con = DriverManager.getConnection(URL);
				CallableStatement csQ = con
						.prepareCall("{ call myQuartInfo(?) }");) {
			csQ.setInt(1, stock_id);
			ResultSet rs = csQ.executeQuery();
			while (rs.next()) {
				stock_quart = new Stock_quartBean();
				stock_quart._setStock_id(rs.getInt(1));
				stock_quart._setQuarterly(rs.getInt(2));
				stock_quart._setProfit_AT_104(rs.getLong(3));
				stock_quart._setProfit_rate_AT_104(rs.getString(4));
				stock_quart._setProfit_BT_104(rs.getLong(5));
				stock_quart._setProfit_rate_BT_104(rs.getString(6));
				stock_quart._setProfit_AT_105(rs.getLong(7));
				stock_quart._setProfit_rate_AT_105(rs.getString(8));
				stock_quart._setAchieve_rate_AT_105(rs.getString(9));
				stock_quart._setProfit_BT_105(rs.getLong(10));
				stock_quart._setProfit_rate_BT_105(rs.getString(11));
				stock_quart._setAchieve_rate_BT_105(rs.getString(12));
				list.add(stock_quart);
			}

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return list;
	}
	
	public void delete(int stock_id, int quart) {
		try (Connection con = DriverManager.getConnection(URL);
				CallableStatement csM = con.prepareCall("{call myQuartDelOne(?,?)}");) {

			csM.setInt(1, stock_id);
			csM.setInt(2, quart);
			csM.execute();

			System.out.println("從quart_change中刪除成功  股票代號:" + stock_id +" 第"+quart+"季");

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public void delete(int stock_id) {
		try (Connection con = DriverManager.getConnection(URL);
				CallableStatement csQ = con.prepareCall("{call myQuartDel(?)}");) {

			csQ.setInt(1, stock_id);
			csQ.execute();
			System.out.println("從quart_change中刪除成功  股票代號:" + stock_id);

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public int update(Stock_quartBean sqb) {
		int updateCount = 0;
		String sql = "UPDATE quart_change SET 104_profit_AT = ?, 104_profit_rate_AT = ?,"
				+ " 104_profit_BT = ?, 104_profit_rate_BT = ?,  105_profit_AT = ? , 105_profit_rate_AT = ? , 105_achieve_rate_AT= ?, "
				+ " 105_profit_BT = ?, 105_profit_rate_BT = ?, 105_achieve_rate_BT = ?  "
				+ " where stock_id = ? and quarterly = ? ";
		try (Connection con = DriverManager.getConnection(URL);
				PreparedStatement pstmt = con.prepareStatement(sql);) {

			Stock_infoDAO.checkLongNullInsert(pstmt, sqb._getProfit_AT_104(), 1);
			pstmt.setString(2, sqb._getProfit_rate_AT_104());
			Stock_infoDAO.checkLongNullInsert(pstmt, sqb._getProfit_BT_104(), 3);
			pstmt.setString(4, sqb._getProfit_rate_BT_104());
			Stock_infoDAO.checkLongNullInsert(pstmt, sqb._getProfit_AT_105(), 5);
			pstmt.setString(6, sqb._getProfit_rate_AT_105());
			pstmt.setString(7, sqb._getAchieve_rate_AT_105());
			Stock_infoDAO.checkLongNullInsert(pstmt, sqb._getProfit_BT_105(), 8);
			pstmt.setString(9, sqb._getProfit_rate_BT_105());
			pstmt.setString(10, sqb._getAchieve_rate_BT_105());
			pstmt.setInt(11, sqb._getStock_id());
			pstmt.setInt(12, sqb._getQuarterly());
			updateCount = pstmt.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		}
		System.out.println("Update成功" + ", 影響row數: " + updateCount);
		return updateCount;
	}

	public int insert(Stock_quartBean sqb) {
		int updateCount = 0;
		String sql = "INSERT INTO quart_change VALUES("
				+ " ?, ?, ?, ?, ?, ?, ?,?,?,?,?,? )";
		try (Connection con = DriverManager.getConnection(URL);
				PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, sqb._getStock_id());
			pstmt.setInt(2, sqb._getQuarterly());
			Stock_infoDAO.checkLongNullInsert(pstmt,sqb._getProfit_AT_104(),3);
			pstmt.setString(4, sqb._getProfit_rate_AT_104());
			Stock_infoDAO.checkLongNullInsert(pstmt,sqb._getProfit_BT_104(),5);
			pstmt.setString(6, sqb._getProfit_rate_BT_104());
			Stock_infoDAO.checkLongNullInsert(pstmt,sqb._getProfit_AT_105(),7);
			pstmt.setString(8, sqb._getProfit_rate_AT_105());
			pstmt.setString(9, sqb._getAchieve_rate_AT_105());
			Stock_infoDAO.checkLongNullInsert(pstmt,sqb._getProfit_BT_105(),10);
			pstmt.setString(11, sqb._getProfit_rate_BT_105());
			pstmt.setString(12, sqb._getAchieve_rate_BT_105());

			updateCount = pstmt.executeUpdate();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		System.out.println("新增成功" + ", 影響row數: " + updateCount);
		return updateCount;
	}
}
