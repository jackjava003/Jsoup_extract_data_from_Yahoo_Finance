package stock_beans;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Stock_infoBean {

	private int stock_id;
	private String stock_Name;
	private String business_type;
	private String pres_Name;
	private String capital;
	private byte[] mon_annu_graph;
	private byte[] quart_annu_graph;
	private Date last_update;
	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private Date d = null;

	public Stock_infoBean() {

	}

	public Stock_infoBean(String[] result) {
		stock_id = Integer.parseInt(result[0].trim());
		stock_Name = result[1].trim();
		business_type = result[2].trim();
		pres_Name = result[3].trim();
		capital = result[4].trim();
		_setMon_annu_graph(result[5].trim());
		_setQuart_annu_graph(result[6].trim());
		last_update = new Date(System.currentTimeMillis());
	}

	public Stock_infoBean(int stock_id, String stock_Name, String business_type,
			String pres_Name, String capital, byte[] mon_annu_graph,
			byte[] quart_annu_graph, Date last_update) {
		super();
		this.stock_id = stock_id;
		this.stock_Name = stock_Name;
		this.business_type = business_type;
		this.pres_Name = pres_Name;
		this.capital = capital;
		this.mon_annu_graph = mon_annu_graph;
		this.quart_annu_graph = quart_annu_graph;
		this.last_update = last_update;
	}

	public Stock_infoBean(int stock_id, String stock_Name, String business_type,
			String pres_Name, String capital, String mon_annu_graph,
			String quart_annu_graph, String last_update) {
		super();
		this.stock_id = stock_id;
		this.stock_Name = stock_Name;
		this.business_type = business_type;
		this.pres_Name = pres_Name;
		this.capital = capital;
		_setMon_annu_graph(mon_annu_graph);
		_setQuart_annu_graph(quart_annu_graph);
		_setLast_update(last_update);
	}

	public Stock_infoBean(int stock_id, String stock_Name, String business_type,
			String pres_Name, String capital, byte[] mon_annu_graph,
			byte[] quart_annu_graph, String last_update) {
		super();
		this.stock_id = stock_id;
		this.stock_Name = stock_Name;
		this.business_type = business_type;
		this.pres_Name = pres_Name;
		this.capital = capital;
		this.mon_annu_graph = mon_annu_graph;
		this.quart_annu_graph = quart_annu_graph;
		_setLast_update(last_update);
	}

	public Stock_infoBean(int stock_id, String stock_Name, String business_type,
			String pres_Name, String capital, byte[] mon_annu_graph,
			byte[] quart_annu_graph) {
		super();
		this.stock_id = stock_id;
		this.stock_Name = stock_Name;
		this.business_type = business_type;
		this.pres_Name = pres_Name;
		this.capital = capital;
		this.mon_annu_graph = mon_annu_graph;
		this.quart_annu_graph = quart_annu_graph;
		last_update = new Date(System.currentTimeMillis());
	}

	public Stock_infoBean(int stock_id, String stock_Name, String business_type,
			String pres_Name, String capital, String mon_annu_graph,
			String quart_annu_graph) {
		super();
		this.stock_id = stock_id;
		this.stock_Name = stock_Name;
		this.business_type = business_type;
		this.pres_Name = pres_Name;
		this.capital = capital;
		_setMon_annu_graph(mon_annu_graph);
		_setQuart_annu_graph(quart_annu_graph);
		last_update = new Date(System.currentTimeMillis());
	}

	public int _getStock_id() {
		return stock_id;
	}

	public void _setStock_id(int stock_id) {
		this.stock_id = stock_id;
	}

	public String _getStock_Name() {
		return stock_Name;
	}

	public void _setStock_Name(String stock_Name) {
		this.stock_Name = stock_Name;
	}

	public String _getBusiness_type() {
		return business_type;
	}

	public void _setBusiness_type(String business_type) {
		this.business_type = business_type;
	}

	public String _getPres_Name() {
		return pres_Name;
	}

	public void _setPres_Name(String pres_Name) {
		this.pres_Name = pres_Name;
	}

	public String _getCapital() {
		return capital;
	}

	public void _setCapital(String capital) {
		this.capital = capital;
	}

	public byte[] _getMon_annu_graph() {
		return mon_annu_graph;
	}

	public void _setMon_annu_graph(String url) {
		if(url == null || url.equals(" ")||url.equalsIgnoreCase("NULL")){
			url = "http://www.sggroup.com.tw/teach/img/no.jpg";
		}
		try {
			URLConnection con = new URL(url).openConnection();
			con.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.82 Safari/537.36");
			try (InputStream is = con.getInputStream();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
				int count = 0;
				byte[] ba = new byte[8192];
				while ((count = is.read(ba)) != -1) {
					baos.write(ba, 0, count);
				}
				this.mon_annu_graph = baos.toByteArray();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void _setMon_annu_graph(byte[] mon_annu_graph) {
		this.mon_annu_graph = mon_annu_graph;
	}

	public byte[] _getQuart_annu_graph() {
		return quart_annu_graph;
	}

	public void _setQuart_annu_graph(String url) {
		if(url == null || url.equals(" ")||url.equalsIgnoreCase("NULL")){
			url = "http://www.sggroup.com.tw/teach/img/no.jpg";
		}
		try {
			URLConnection con = new URL(url).openConnection();
			con.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.82 Safari/537.36");
			try (InputStream is = con.getInputStream();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
				int count = 0;
				byte[] ba = new byte[8192];
				while ((count = is.read(ba)) != -1) {
					baos.write(ba, 0, count);
				}
				this.quart_annu_graph = baos.toByteArray();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void _setQuart_annu_graph(byte[] quart_annu_graph) {
		this.quart_annu_graph = quart_annu_graph;
	}

	public String _getLast_update() {
		String date = sf.format(last_update);
		return date;
	}

	public void _setLast_update(Date last_update) {
		this.last_update = last_update;
	}

	public void _setLast_update(String last_update) {
		if (last_update == null || last_update.equals(" ")) {
			this.last_update = new Date(System.currentTimeMillis());
		} else {
			try {
				d = sf.parse(last_update);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			this.last_update = d;
		}
	}
	public String toString() {
		String str = String.format("查詢資料股票代號:%4d %-15s %10s %10s %10s %-20s\n",
				_getStock_id(), " 公司名稱: " + _getStock_Name(), " 產業類別: "
						+ _getBusiness_type(), " 董事長: " + _getPres_Name(), "股本: "
						+ _getCapital(), " 最後更新日期: " + _getLast_update());
		return str;
	}
}
