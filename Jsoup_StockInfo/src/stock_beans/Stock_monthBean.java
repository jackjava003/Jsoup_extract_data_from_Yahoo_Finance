package stock_beans;

import java.math.BigDecimal;

public class Stock_monthBean {
	private int stock_id;
	private String month;
	private long rev_104;
	private BigDecimal incr_rate_104;
	private long rev_105;
	private BigDecimal incr_rate_105;
	private long cumu_rev_105;
	private BigDecimal annu_rate_105;
	private String achieve_rate_105;

	public Stock_monthBean() {

	}

	public Stock_monthBean(int stock_id, String month, long rev_104,
			BigDecimal incr_rate_104, long rev_105, BigDecimal incr_rate_105,
			long cumu_rev_105, BigDecimal annu_rate_105, String achieve_rate_105) {
		super();
		this.stock_id = stock_id;
		this.month = month;
		this.rev_104 = rev_104;
		this.incr_rate_104 = incr_rate_104;
		this.rev_105 = rev_105;
		this.incr_rate_105 = incr_rate_105;
		this.cumu_rev_105 = cumu_rev_105;
		this.annu_rate_105 = annu_rate_105;
		this.achieve_rate_105 = achieve_rate_105;
	}

	public Stock_monthBean(String[] str) {
		stock_id = Integer.parseInt(str[0]);
		month = str[1];
		rev_104 = _str2Long(str[2]) * 1000;
		incr_rate_104 = _str2BigDecimal(str[3]);
		rev_105 = _str2Long(str[4]) * 1000;
		incr_rate_105 = _str2BigDecimal(str[5]);
		cumu_rev_105 = _str2Long(str[6]) * 1000;
		annu_rate_105 = _str2BigDecimal(str[7]);
		achieve_rate_105 = str[8];

	}

	public int _getStock_id() {
		return stock_id;
	}

	public void _setStock_id(int stock_id) {
		this.stock_id = stock_id;
	}

	public String _getMonth() {
		return month;
	}

	public void _setMonth(String month) {
		this.month = month;
	}

	public long _getRev_104() {
		return rev_104;
	}

	public void _setRev_104(long rev_104) {
		this.rev_104 = rev_104;
	}

	public BigDecimal _getIncr_rate_104() {
		return incr_rate_104;
	}

	public void _setIncr_rate_104(BigDecimal incr_rate_104) {
		this.incr_rate_104 = incr_rate_104;
	}

	public long _getRev_105() {
		return rev_105;
	}

	public void _setRev_105(long rev_105) {
		this.rev_105 = rev_105;
	}

	public BigDecimal _getIncr_rate_105() {
		return incr_rate_105;
	}

	public void _setIncr_rate_105(BigDecimal incr_rate_105) {
		this.incr_rate_105 = incr_rate_105;
	}

	public long _getCumu_rev_105() {
		return cumu_rev_105;
	}

	public void _setCumu_rev_105(long cumu_rev_105) {
		this.cumu_rev_105 = cumu_rev_105;
	}

	public BigDecimal _getAnnu_rate_105() {
		return annu_rate_105;
	}

	public void _setAnnu_rate_105(BigDecimal annu_rate_105) {
		this.annu_rate_105 = annu_rate_105;
	}

	public String _getAchieve_rate_105() {
		return achieve_rate_105;
	}

	public void _setAchieve_rate_105(String achieve_rate_105) {
		this.achieve_rate_105 = achieve_rate_105;
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

	private BigDecimal _str2BigDecimal(String s) {
		s = s.replace(",", "");
		s = s.replace("%", "");
		if (s.length() == 1) {
			s = s.replace("-", "0");
		}
		BigDecimal d = new BigDecimal(s.trim());

		return d;
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
	private String bDToNull(BigDecimal db) {
		String result = "";
		if (db == null) {
			result = "-";
		} else {
			result = db+"%" ;
		}
		return result;
	}

	public String toString() {
		String str = String.format(

		"查詢資料股票代號:%4d %-5s %10s %10s %10s %10s %10s %10s %-20s\n",
				_getStock_id(), " 月份: " + _getMonth(), " 104年度營收: "
						+ longToNull(_getRev_104()), " 104年度年增率: " + bDToNull(_getIncr_rate_104()), 
						"105年度營收:" + longToNull(_getRev_105()), " 105年度年增率: "
						+ bDToNull(_getIncr_rate_105()), "105年度累計營收:"
						+ longToNull(_getCumu_rev_105()), "105年度累計年增率:"
						+ bDToNull(_getAnnu_rate_105()), "105年度達成率: "
						+ _getAchieve_rate_105());
		return str;
	}
}
