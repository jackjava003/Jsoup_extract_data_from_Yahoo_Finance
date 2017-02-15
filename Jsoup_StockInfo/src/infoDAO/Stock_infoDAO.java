package infoDAO;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import stock_beans.Stock_infoBean;
import stock_beans.Stock_monthBean;
import stock_beans.Stock_quartBean;

public class Stock_infoDAO {
	public static final String HOST = "127.0.0.1";
	public static final String DB = "jdbcDB";
	public static final String USER = "root";
	public static final String PASSWORD = "jackjava003";
	public static final String URL = "jdbc:mysql://" + HOST + ":3306/"
			+ SystemConstant.DB + "?user=" + SystemConstant.USER + "&password="
			+ SystemConstant.PASSWORD
			+ "&useSSL=true&useUnicode=yes&characterEncoding=UTF-8";

	public Stock_infoDAO() {
	}

	public List<Stock_infoBean> findAll() {
		List<Stock_infoBean> list = new ArrayList<>();
		Stock_infoBean stock_info = null;
		try (Connection con = DriverManager.getConnection(URL);
				CallableStatement csInfo = con
						.prepareCall("{ call myStockInfoALL() }");) {
			ResultSet rs = csInfo.executeQuery();
			while (rs.next()) {
				stock_info = new Stock_infoBean();
				stock_info._setStock_id(rs.getInt(1));
				stock_info._setStock_Name(rs.getString(2));
				stock_info._setBusiness_type(rs.getString(3));
				stock_info._setPres_Name(rs.getString(4));
				stock_info._setCapital(rs.getString(5));
				;
				stock_info._setMon_annu_graph(rs.getBytes(6));
				stock_info._setQuart_annu_graph(rs.getBytes(7));
				stock_info._setLast_update(rs.getTimestamp(8));
				list.add(stock_info);
			}

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return list;
	}

	public Stock_infoBean findByPrimaryKey(int stock_id) {
		Stock_infoBean stock_info = null;

		try (Connection con = DriverManager.getConnection(URL);
				CallableStatement csInfo = con
						.prepareCall("{ call myStockInfo(?,?,?,?,?,?,?,?) }");) {
			csInfo.setInt(1, stock_id);
			csInfo.registerOutParameter(1, Types.INTEGER);
			csInfo.registerOutParameter(2, Types.VARCHAR);
			csInfo.registerOutParameter(3, Types.VARCHAR);
			csInfo.registerOutParameter(4, Types.VARCHAR);
			csInfo.registerOutParameter(5, Types.VARCHAR);
			csInfo.registerOutParameter(6, Types.BLOB);
			csInfo.registerOutParameter(7, Types.BLOB);
			csInfo.registerOutParameter(8, Types.TIMESTAMP);
			csInfo.execute();
			stock_info = new Stock_infoBean();
			stock_info._setStock_id(csInfo.getInt(1));
			stock_info._setStock_Name(csInfo.getString(2));
			stock_info._setBusiness_type(csInfo.getString(3));
			stock_info._setPres_Name(csInfo.getString(4));
			stock_info._setCapital(csInfo.getString(5));
			stock_info._setMon_annu_graph(csInfo.getBytes(6));
			stock_info._setQuart_annu_graph(csInfo.getBytes(7));
			stock_info._setLast_update(csInfo.getTimestamp(8));

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return stock_info;
	}

	public void delete(int stock_id) {
		try (Connection con = DriverManager.getConnection(URL);
				CallableStatement csQ = con.prepareCall("{call myQuartDel(?)}");
				CallableStatement csM = con.prepareCall("{call myMonthDel(?)}");
				CallableStatement csInfo = con
						.prepareCall("{call myStockInfoDel(?)}");) {
			csQ.setInt(1, stock_id);
			csQ.execute();
			System.out.println("從quart_change中刪除成功  股票代號:" + stock_id);
			
			csM.setInt(1, stock_id);
			csM.execute();
			System.out.println("從monthly_change中刪除成功  股票代號:" + stock_id);
				
			csInfo.setInt(1, stock_id);
			csInfo.execute();
			System.out.println("從stocks_info中刪除成功  股票代號:" + stock_id);


		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	public int update(Stock_infoBean sb) {
		int updateCount = 0;
		String sql = "UPDATE stocks_info SET stock_Name = ?, business_type = ?, "
				+ " pres_Name = ?,  capital = ?, mon_annu_graph = ?,  quart_annu_graph = ? , last_update =?"
				+ " where stock_id = ? ";
		try (Connection con = DriverManager.getConnection(URL);
				PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, sb._getStock_Name());
			pstmt.setString(2, sb._getBusiness_type());
			pstmt.setString(3, sb._getPres_Name());
			pstmt.setString(4, sb._getCapital());
			pstmt.setBytes(5, sb._getMon_annu_graph());
			pstmt.setBytes(6, sb._getQuart_annu_graph());
			pstmt.setString(7, sb._getLast_update());
			pstmt.setInt(8, sb._getStock_id());
			updateCount = pstmt.executeUpdate();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		System.out.println("Update成功" + ", 影響row數: " + updateCount);
		return updateCount;
	}

	protected static void checkLongNullInsert(PreparedStatement pstmtStock,
			long n, int column) {

		try {
			if (n == 0) {
				pstmtStock.setNString(column, null);
			} else {
				pstmtStock.setLong(column, n);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	protected static void checkBDNullInsert(PreparedStatement pstmtStock,
			BigDecimal b, int column) {

		try {
			if (b.doubleValue() == 0) {
				pstmtStock.setBigDecimal(column, null);
			} else {
				pstmtStock.setBigDecimal(column, b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public int insert(Stock_infoBean sb) {
		int updateCount = 0;
		String sqlStock_info = "INSERT INTO stocks_info VALUES("
				+ " ?, ?, ?, ?, ?, ?, ?,? )";
		try (Connection con = DriverManager.getConnection(URL);
				PreparedStatement pstmtStock_info = con
						.prepareStatement(sqlStock_info);) {
			pstmtStock_info.setInt(1, sb._getStock_id());
			pstmtStock_info.setString(2, sb._getStock_Name());
			pstmtStock_info.setString(3, sb._getBusiness_type());
			pstmtStock_info.setString(4, sb._getPres_Name());
			pstmtStock_info.setString(5, sb._getCapital());
			pstmtStock_info.setBytes(6, sb._getMon_annu_graph());
			pstmtStock_info.setBytes(7, sb._getQuart_annu_graph());
			pstmtStock_info.setString(8, sb._getLast_update());
			updateCount = pstmtStock_info.executeUpdate();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		System.out.println("新增成功" + ", 影響row數: " + updateCount);

		return updateCount;
	}

	public int insert(Stock_infoBean sb, List<Stock_monthBean> smb,
			List<Stock_quartBean> sqb) {
		int updateCount = 0;
		String sqlStock_info = "INSERT INTO stocks_info VALUES("
				+ " ?, ?, ?, ?, ?, ?, ?,? )";
		String sqlStock_month = "INSERT INTO monthly_change VALUES("
				+ " ?, ?, ?, ?, ?, ?, ?,?,? )";
		String sqlStock_quart = "INSERT INTO quart_change VALUES("
				+ " ?, ?, ?, ?, ?, ?, ?,?,?,?,?,? )";
		try (Connection con = DriverManager.getConnection(URL);
				PreparedStatement pstmtStock_info = con
						.prepareStatement(sqlStock_info);
				PreparedStatement pstmtStock_month = con
						.prepareStatement(sqlStock_month);
				PreparedStatement pstmtStock_quart = con
						.prepareStatement(sqlStock_quart);) {
			pstmtStock_info.setInt(1, sb._getStock_id());
			pstmtStock_info.setString(2, sb._getStock_Name());
			pstmtStock_info.setString(3, sb._getBusiness_type());
			pstmtStock_info.setString(4, sb._getPres_Name());
			pstmtStock_info.setString(5, sb._getCapital());
			pstmtStock_info.setBytes(6, sb._getMon_annu_graph());
			pstmtStock_info.setBytes(7, sb._getQuart_annu_graph());
			pstmtStock_info.setString(8, sb._getLast_update());
			updateCount = pstmtStock_info.executeUpdate();

			for (Stock_monthBean sm : smb) {
				pstmtStock_month.setInt(1, sm._getStock_id());
				pstmtStock_month.setString(2, sm._getMonth());
				checkLongNullInsert(pstmtStock_month, sm._getRev_104(), 3);
				checkBDNullInsert(pstmtStock_month, sm._getIncr_rate_104(), 4);
				checkLongNullInsert(pstmtStock_month, sm._getRev_105(), 5);
				checkBDNullInsert(pstmtStock_month, sm._getIncr_rate_105(), 6);
				checkLongNullInsert(pstmtStock_month, sm._getCumu_rev_105(), 7);
				checkBDNullInsert(pstmtStock_month, sm._getAnnu_rate_105(), 8);
				pstmtStock_month.setString(9, sm._getAchieve_rate_105());

				pstmtStock_month.executeUpdate();
				pstmtStock_month.clearParameters();
			}

			for (Stock_quartBean sq : sqb) {
				pstmtStock_quart.setInt(1, sq._getStock_id());
				pstmtStock_quart.setInt(2, sq._getQuarterly());
				checkLongNullInsert(pstmtStock_quart, sq._getProfit_AT_104(), 3);
				pstmtStock_quart.setString(4, sq._getProfit_rate_AT_104());
				checkLongNullInsert(pstmtStock_quart, sq._getProfit_BT_104(), 5);
				pstmtStock_quart.setString(6, sq._getProfit_rate_BT_104());
				checkLongNullInsert(pstmtStock_quart, sq._getProfit_AT_105(), 7);
				pstmtStock_quart.setString(8, sq._getProfit_rate_AT_105());
				pstmtStock_quart.setString(9, sq._getAchieve_rate_AT_105());
				checkLongNullInsert(pstmtStock_quart, sq._getProfit_BT_105(),
						10);
				pstmtStock_quart.setString(11, sq._getProfit_rate_BT_105());
				pstmtStock_quart.setString(12, sq._getAchieve_rate_BT_105());

				updateCount = pstmtStock_quart.executeUpdate();
				pstmtStock_quart.clearParameters();
			}

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		System.out.println("新增成功" + ", 影響row數: " + updateCount);

		return updateCount;
	}

	public void init() {

		try (Connection con = DriverManager.getConnection(URL);
				Statement stmt = con.createStatement();) {
			// 刪除表格
			String dropStringQuartChange = "DROP TABLE IF EXISTS quart_change ";
			stmt.executeUpdate(dropStringQuartChange);
			System.out.println("刪除quart_change成功");
			String dropStringMonthlyChange = "DROP TABLE IF EXISTS monthly_change ";
			stmt.executeUpdate(dropStringMonthlyChange);
			System.out.println("刪除monthly_change成功");
			String dropString = "DROP TABLE IF EXISTS stocks_info ";
			stmt.executeUpdate(dropString);
			System.out.println("刪除stocks_info成功");

			// 新建表格
			String createString = "CREATE TABLE stocks_info "
					+ "(stock_id int NOT NULL , "
					+ " stock_Name varchar(32), "
					+ " business_type varchar(32), "
					+ " pres_Name varchar(32), "
					+ " capital varchar(32), "
					+ " mon_annu_graph MediumBlob, "
					+ " quart_annu_graph MediumBlob, "
					+ " last_update datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,"
					+ " CONSTRAINT PRIMARY KEY(stock_id)) ENGINE=INNODB";
			stmt.executeUpdate(createString);
			System.out.println("新建stocks_info成功");

			String createStringMonthlyChange = "CREATE TABLE monthly_change "
					+ "(stock_id int NOT NULL , "
					+ " month varchar(5) NOT NULL, "
					+ " 104_rev bigint, "
					+ " 104_incr_rate Decimal(7,2), "
					+ " 105_rev bigint, "
					+ " 105_incr_rate Decimal(7,2), "
					+ " 105_cumu_rev bigint,"
					+ " 105_annu_rate Decimal(7,2),"
					+ " 105_achieve_rate varchar(10),"
					+ " CONSTRAINT pk_id_month PRIMARY KEY(stock_id,month),"
					+ " CONSTRAINT fk_MID_SID FOREIGN KEY(stock_id) REFERENCES stocks_info(stock_id)) ENGINE=INNODB ";
			stmt.executeUpdate(createStringMonthlyChange);
			System.out.println("新建monthly_change成功");

			String createStringQuartChange = "CREATE TABLE quart_change "
					+ "(stock_id int NOT NULL , "
					+ " quarterly int NOT NULL, "
					+ " 104_profit_AT bigint, "
					+ " 104_profit_rate_AT varchar(10), "
					+ " 104_profit_BT bigint, "
					+ " 104_profit_rate_BT varchar(10), "
					+ " 105_profit_AT bigint, "
					+ " 105_profit_rate_AT varchar(10), "
					+ " 105_achieve_rate_AT varchar(10),"
					+ " 105_profit_BT bigint, "
					+ " 105_profit_rate_BT varchar(10), "
					+ " 105_achieve_rate_BT varchar(10),"
					+ " CONSTRAINT pk_id_quarterly PRIMARY KEY(stock_id,quarterly),"
					+ " CONSTRAINT fk_QID_SID FOREIGN KEY(stock_id) REFERENCES stocks_info(stock_id)) ENGINE=INNODB";
			stmt.executeUpdate(createStringQuartChange);
			System.out.println("新建quart_change成功");

			// 刪除+新建PROCEDURE
			//=======================StockInfo Procedure==========================
			
			String dropProcStockInfo = "Drop PROCEDURE IF EXISTS myStockInfo";
			stmt.executeUpdate(dropProcStockInfo);
			System.out.println("刪除myStockInfo(PROCEDURE)成功");
			String dropProcStockInfoALL = "Drop PROCEDURE IF EXISTS myStockInfoALL";
			stmt.executeUpdate(dropProcStockInfoALL);
			System.out.println("刪除myStockInfoALL(PROCEDURE)成功");
			String dropProcStockInfoDel = "Drop PROCEDURE IF EXISTS myStockInfoDel";
			stmt.executeUpdate(dropProcStockInfoDel);
			System.out.println("刪除myStockInfoDel(PROCEDURE)成功");
			
			String proceStockInfo = "CREATE  PROCEDURE myStockInfo( INOUT  stockNum  Int,OUT stock_Name1 varchar(32), OUT business_type1 varchar(32), OUT pres_Name1 varchar(32),OUT capital1 varchar(32),OUT mon_annu_graph1 mediumblob,OUT quart_annu_graph1 mediumblob,OUT last_update1 DateTime)  "
			+ "BEGIN "
			+ "SELECT  stock_id, stock_Name, "
			+ "business_type, pres_Name, "
			+ "capital, mon_annu_graph, "
			+ "quart_annu_graph, last_update"
			+ " INTO stockNum,stock_Name1, business_type1, pres_Name1, capital1, mon_annu_graph1,"
			+ " quart_annu_graph1, last_update1 "
			+ "FROM stocks_info where stock_id = stockNum; "
			+ "commit ; " + "END";
			stmt.executeUpdate(proceStockInfo);
			System.out.println("新建myStockInfo(PROCEDURE)成功");
			
			String proceStockInfoALL = "CREATE  PROCEDURE myStockInfoALL()  "
			+ "BEGIN " + "SELECT  * " + "FROM stocks_info; "
			+ "commit ; " + "END";
			stmt.executeUpdate(proceStockInfoALL);
			System.out.println("新建myStockInfoALL(PROCEDURE)成功");
			
			String proceStockInfoDel = "CREATE  PROCEDURE myStockInfoDel(IN stockNum  Int)  "
			+ "BEGIN "
			+ "DELETE FROM stocks_info WHERE stock_id = stockNum; "
			+ "commit ; " + "END";
			stmt.executeUpdate(proceStockInfoDel);
			System.out.println("新建myStockInfoDel(PROCEDURE)成功");
			
			
			
			//=======================StockMonth Procedure==========================
			
			String dropProcMonthInfo = "Drop PROCEDURE IF EXISTS myMonthlyInfo";
			stmt.executeUpdate(dropProcMonthInfo);
			System.out.println("刪除myMonthlyInfo(PROCEDURE)成功");
			String dropProcMonthOneInfo = "Drop PROCEDURE IF EXISTS myMonthOneInfo";
			stmt.executeUpdate(dropProcMonthOneInfo);
			System.out.println("刪除myMonthOneInfo(PROCEDURE)成功");
			String dropProcMonthInfoALL = "Drop PROCEDURE IF EXISTS myMonthInfoALL";
			stmt.executeUpdate(dropProcMonthInfoALL);
			System.out.println("刪除myMonthInfoALL(PROCEDURE)成功");
			String dropProcMonthDel = "Drop PROCEDURE IF EXISTS myMonthDel";
			stmt.executeUpdate(dropProcMonthDel);
			System.out.println("刪除myMonthDel(PROCEDURE)成功");
			String dropProcMonthDelOne = "Drop PROCEDURE IF EXISTS myMonthDelOne";
			stmt.executeUpdate(dropProcMonthDelOne);
			System.out.println("刪除myMonthDelOne(PROCEDURE)成功");
			
			String proceMonthlyInfo = "CREATE  PROCEDURE myMonthlyInfo( IN stockNum  Int)"
			+ "BEGIN "
			+ "SELECT  * "
			+ "FROM monthly_change where stock_id = stockNum; "
			+ "commit ; " + "END";
			stmt.executeUpdate(proceMonthlyInfo);
			System.out.println("新建myMonthlyInfo(PROCEDURE)成功");

			String proceMonthOneInfo = "CREATE  PROCEDURE myMonthOneInfo( INOUT stockNum  Int, "
			+ "INOUT month1 varchar(5), "
			+ "OUT rev104 bigint(20), "
			+ "OUT IncR104 decimal(7,2), OUT rev105 bigint(20), "
			+ "OUT IncR105 decimal(7,2), OUT cv105 bigint(20), "
			+ "OUT ar105 decimal(7,2), "
			+ "OUT acr105 varchar(10) )"
			+ "BEGIN "
			+ "SELECT stock_id,month,104_rev,104_incr_rate,105_rev,105_incr_rate,105_cumu_rev,105_annu_rate,105_achieve_rate  "
			+ "INTO stockNum,month1,rev104,IncR104,rev105,IncR105,cv105,ar105,acr105 "
			+ "FROM monthly_change where stock_id = stockNum and month = month1; "
			+ "commit ; " + "END";
			stmt.executeUpdate(proceMonthOneInfo);
			System.out.println("新建myMonthOneInfo(PROCEDURE)成功");
			
			String proceMonthInfoALL = "CREATE  PROCEDURE myMonthInfoALL()  "
			+ "BEGIN " + "SELECT  * " + "FROM monthly_change; "
			+ "commit ; " + "END";
			stmt.executeUpdate(proceMonthInfoALL);
			System.out.println("新建myMonthInfoALL(PROCEDURE)成功");
			
			String proceMonthDel = "CREATE  PROCEDURE myMonthDel(IN stockNum  Int)  "
			+ "BEGIN "
			+ "DELETE FROM monthly_change WHERE stock_id = stockNum; "
			+ "commit ; " + "END";
			stmt.executeUpdate(proceMonthDel);
			System.out.println("新建myMonthDel(PROCEDURE)成功");
	
			String proceMonthDelOne = "CREATE  PROCEDURE myMonthDelOne(IN stockNum  Int, IN month1 varchar(5))  "
					+ "BEGIN "
					+ "DELETE FROM monthly_change WHERE stock_id = stockNum and month = month1; "
					+ "commit ; " + "END";

			stmt.executeUpdate(proceMonthDelOne);
			System.out.println("新建myMonthDelOne(PROCEDURE)成功");
			
			
			//=======================StockQuad Procedure==========================
			
			String dropProcQuartDel = "Drop PROCEDURE IF EXISTS myQuartDel";
			stmt.executeUpdate(dropProcQuartDel);
			System.out.println("刪除myQuartDel(PROCEDURE)成功");
			String dropProcQuartDelOne = "Drop PROCEDURE IF EXISTS myQuartDelOne";
			stmt.executeUpdate(dropProcQuartDelOne);
			System.out.println("刪除myQuartDelOne(PROCEDURE)成功");
			String dropProcQuartInfo = "Drop PROCEDURE IF EXISTS myQuartInfo";
			stmt.executeUpdate(dropProcQuartInfo);
			System.out.println("刪除myQuartInfo(PROCEDURE)成功");
			String dropProcQuartInfoALL = "Drop PROCEDURE IF EXISTS myQuartInfoALL";
			stmt.executeUpdate(dropProcQuartInfoALL);
			System.out.println("刪除myQuartInfoALL(PROCEDURE)成功");
			String dropProcQuartOneInfo = "Drop PROCEDURE IF EXISTS myQuartOneInfo";
			stmt.executeUpdate(dropProcQuartOneInfo);
			System.out.println("刪除myQuartOneInfo(PROCEDURE)成功");


			String proceQuartDel = "CREATE  PROCEDURE myQuartDel(IN stockNum  Int)  "
					+ "BEGIN "
					+ "DELETE FROM quart_change WHERE stock_id = stockNum; "
					+ "commit ; " + "END";
			stmt.executeUpdate(proceQuartDel);
			System.out.println("新建myQuartDel(PROCEDURE)成功");
			
			String proceQuartDelOne = "CREATE  PROCEDURE myQuartDelOne(IN stockNum  Int, IN quart Int)  "
			+ "BEGIN "
			+ "DELETE FROM quart_change WHERE stock_id = stockNum and quarterly = quart; "
			+ "commit ; " + "END";
			stmt.executeUpdate(proceQuartDelOne);
			System.out.println("新建myQuartDelOne(PROCEDURE)成功");
			
			String proceQuartInfo = "CREATE  PROCEDURE myQuartInfo( IN stockNum  Int)"
			+ "BEGIN "
			+ "SELECT  * "
			+ "FROM quart_change where stock_id = stockNum; "
			+ "commit ; " + "END";
			stmt.executeUpdate(proceQuartInfo);
			System.out.println("新建myQuartInfo(PROCEDURE)成功");
			
			String proceQuartInfoALL = "CREATE  PROCEDURE myQuartInfoALL()  "
			+ "BEGIN " + "SELECT  * " + "FROM quart_change; "
			+ "commit ; " + "END";
			stmt.executeUpdate(proceQuartInfoALL);
			System.out.println("新建myQuartInfoALL(PROCEDURE)成功");
			
			String proceQuartOneInfo = "CREATE  PROCEDURE myQuartOneInfo( INOUT stockNum  Int, "
					+ "INOUT quart Int, "
					+ "OUT p104A bigint(20), "
					+ "OUT pr104A varchar(10), OUT p104B bigint(20), "
					+ "OUT pr104B varchar(10), OUT p105A bigint(20), "
					+ "OUT pr105A varchar(10), OUT ar105A varchar(10),"
					+ "OUT p105B bigint(20), OUT pr105B varchar(10),"
					+ "OUT ar105B varchar(10) )"
					+ "BEGIN "
					+ "SELECT stock_id,quarterly,104_profit_AT,104_profit_rate_AT,104_profit_BT"
					+ ",104_profit_rate_BT,105_profit_AT,105_profit_rate_AT,105_achieve_rate_AT,"
					+ " 105_profit_BT, 105_profit_rate_BT, 105_achieve_rate_BT  "
					+ "INTO stockNum,quart,p104A,pr104A,p104B,pr104B,p105A,pr105A,"
					+ "ar105A, p105B, pr105B, ar105B "
					+ "FROM quart_change where stock_id = stockNum and quarterly = quart; "
					+ "commit ; " + "END";
					stmt.executeUpdate(proceQuartOneInfo);
			System.out.println("新建myQuartOneInfo(PROCEDURE)成功");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("新建表格成功");

	}
}
