package stock_beans;

public class Stock_quartBean {

	private int stock_id;
	private int quarterly;
	private long profit_AT_104;
	private String profit_rate_AT_104;
	private long profit_BT_104;
	private String profit_rate_BT_104;
	private long profit_AT_105;
	private String profit_rate_AT_105;
	private String achieve_rate_AT_105;
	private long profit_BT_105;
	private String profit_rate_BT_105;
	private String achieve_rate_BT_105;

	public Stock_quartBean(int stock_id, int quarterly, long profit_AT_104,
			String profit_rate_AT_104, long profit_AT_105,
			String profit_rate_AT_105, String achieve_rate_AT_105,
			long profit_BT_104, String profit_rate_BT_104,
			long profit_BT_105, String profit_rate_BT_105,
			String achieve_rate_BT_105) {
		super();
		this.stock_id = stock_id;
		this.quarterly = quarterly;
		this.profit_AT_104 = profit_AT_104;
		this.profit_rate_AT_104 = profit_rate_AT_104;
		this.profit_AT_105 = profit_AT_105;
		this.profit_rate_AT_105 = profit_rate_AT_105;
		this.achieve_rate_AT_105 = achieve_rate_AT_105;
		this.profit_BT_104 = profit_BT_104;
		this.profit_rate_BT_104 = profit_rate_BT_104;
		this.profit_BT_105 = profit_BT_105;
		this.profit_rate_BT_105 = profit_rate_BT_105;
		this.achieve_rate_BT_105 = achieve_rate_BT_105;
	}

	public Stock_quartBean(String[] str) {
		super();
		this.stock_id = Integer.parseInt(str[0]);
		this.quarterly = Integer.parseInt(str[1]);
		this.profit_AT_104 = _str2Long(str[2])*1000;
		this.profit_rate_AT_104 = str[3];
		this.profit_AT_105 = _str2Long(str[4])*1000;
		this.profit_rate_AT_105 = str[5];
		this.achieve_rate_AT_105 = str[6];
		this.profit_BT_104 = _str2Long(str[7])*1000;
		this.profit_rate_BT_104 = str[8];
		this.profit_BT_105 = _str2Long(str[9])*1000;
		this.profit_rate_BT_105 = str[10];
		this.achieve_rate_BT_105 = str[11];
	}

	public Stock_quartBean() {
	}

	public int _getStock_id() {
		return stock_id;
	}

	public void _setStock_id(int stock_id) {
		this.stock_id = stock_id;
	}

	public int _getQuarterly() {
		return quarterly;
	}

	public void _setQuarterly(int quarterly) {
		this.quarterly = quarterly;
	}

	public long _getProfit_AT_104() {
		return profit_AT_104;
	}

	public void _setProfit_AT_104(long profit_AT_104) {
		this.profit_AT_104 = profit_AT_104;
	}

	public String _getProfit_rate_AT_104() {
		return profit_rate_AT_104;
	}

	public void _setProfit_rate_AT_104(String profit_rate_AT_104) {
		this.profit_rate_AT_104 = profit_rate_AT_104;
	}

	public long _getProfit_BT_104() {
		return profit_BT_104;
	}

	public void _setProfit_BT_104(long profit_BT_104) {
		this.profit_BT_104 = profit_BT_104;
	}

	public String _getProfit_rate_BT_104() {
		return profit_rate_BT_104;
	}

	public void _setProfit_rate_BT_104(String profit_rate_BT_104) {
		this.profit_rate_BT_104 = profit_rate_BT_104;
	}

	public long _getProfit_AT_105() {
		return profit_AT_105;
	}

	public void _setProfit_AT_105(long profit_AT_105) {
		this.profit_AT_105 = profit_AT_105;
	}

	public String _getProfit_rate_AT_105() {
		return profit_rate_AT_105;
	}

	public void _setProfit_rate_AT_105(String profit_rate_AT_105) {
		this.profit_rate_AT_105 = profit_rate_AT_105;
	}

	public String _getAchieve_rate_AT_105() {
		return achieve_rate_AT_105;
	}

	public void _setAchieve_rate_AT_105(String achieve_rate_AT_105) {
		this.achieve_rate_AT_105 = achieve_rate_AT_105;
	}

	public long _getProfit_BT_105() {
		return profit_BT_105;
	}

	public void _setProfit_BT_105(long profit_BT_105) {
		this.profit_BT_105 = profit_BT_105;
	}

	public String _getProfit_rate_BT_105() {
		return profit_rate_BT_105;
	}

	public void _setProfit_rate_BT_105(String profit_rate_BT_105) {
		this.profit_rate_BT_105 = profit_rate_BT_105;
	}

	public String _getAchieve_rate_BT_105() {
		return achieve_rate_BT_105;
	}

	public void _setAchieve_rate_BT_105(String achieve_rate_BT_105) {
		this.achieve_rate_BT_105 = achieve_rate_BT_105;
	}

	private long _str2Long(String s) {
		long n = 0;
		s = s.replace(",", "");
		if (s.length() == 1) {
			s = s.replace("-", "0");
		}
		try {
			n = Long.parseLong(s.trim());
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
		return n;
	}
	
	private String longToNull(long n) {
		String result = "";
		if (n == 0) {
			result = "-";
		} else {
			result = n+"" ;
		}
		return result;
	}
	
	public String toString() {
		String str = String.format(

		"查詢資料股票代號:%4d %-5s %10s %10s %10s %10s %10s %10s %-10s %-10s %-10s %-10s\n",
				_getStock_id(), " 第 " + _getQuarterly() + "季", "  104年度稅後盈餘: "
						+ longToNull(_getProfit_AT_104()), " 104年度稅後年增率: " + _getProfit_rate_AT_104() , 
						"105年度稅後盈餘:" + longToNull(_getProfit_AT_105()), " 105年度稅後年增率: "
						+ _getProfit_rate_AT_105(), "105年度稅後達成率:"
						+ _getAchieve_rate_AT_105(), "104年度稅前盈餘:"
						+ longToNull(_getProfit_BT_104()), "104年度稅前年增率: "
						+ _getProfit_rate_BT_104(), "105年度稅前盈餘:"+  longToNull(_getProfit_BT_105()), 
						"105年度稅前年增率: "+ _getProfit_rate_BT_105() , " 105年度稅前達成率:"+_getAchieve_rate_BT_105());
		return str;
	}

}
