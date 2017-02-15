package connect_to_yahoo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class StockGetHtml {
	private final String buri = "https://tw.stock.yahoo.com/";
	private String stock_info;
	private String stock_earning;
	private int stockNum;
	Map<String, String> cookies = null;
	private String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.82 Safari/537.36";

	public StockGetHtml(int stockNum) {
		stock_info = "https://tw.stock.yahoo.com/d/s/company_" + stockNum
				+ ".html";
		stock_earning = "https://tw.stock.yahoo.com/d/s/earning_" + stockNum
				+ ".html";
		this.stockNum = stockNum;
		
		//使用cookies模仿使用者行為  防止被偵測是使用程式抓取資料
		try {
			Response res = Jsoup.connect(stock_info).userAgent(userAgent)
					.execute();
			cookies = res.cookies();
		} catch (IOException e) {
			//無此股票  將會獲取cookies失敗
			//e.printStackTrace();
			System.out.println("獲取cookies失敗");
		}

		// URL url = new
		// URL("https://tw.stock.yahoo.com/d/s/earning_2353.html");
		// URLConnection con = url.openConnection();
		// con.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.82 Safari/537.36");
		// Map<String, List<String>> headers = con.getHeaderFields();
		// System.out.println(headers.size());
		// Set<String> set = headers.keySet();
		// for (String str : set) {
		// for(String st : headers.get(str) ){
		// System.out.println(str + "   " + st);
		// }
		// }

	}

	private Document getDoc(String url, String referer) throws IOException {
		Document doc = null;
		doc = Jsoup
				.connect(url)
				.header("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
				.header("Accept-Encoding", "gzip, deflate, sdch, br")
				.header("Accept-Language",
						"zh-TW,zh;q=0.8,en-US;q=0.6,en;q=0.4")
				.header("Connection", "keep-alive").header("Host", buri)
				.header("Cache-Control", "max-age=0")
				.header("Upgrade-Insecure-Requests", "1")
				.header("Referer", referer).userAgent(userAgent)
				.cookies(cookies).get();

		return doc;
	}

	public List<String[]> _Stock_Quarterly_Info() {
		List<String[]> listString = new ArrayList<>();
		String[][] strArr = new String[8][];
		try {
			Document doc = getDoc(stock_earning, stock_info);
			Element body = doc.body();
			Elements els = body.select("tr[bgcolor*=#FFFFFF]");
			int n = 0;
			for (Element e : els) {

				Elements etd2 = e.getElementsByTag("td");
				if (etd2.size() == 7 && n < 4) {

					String str = stockNum + "|";
					str += etd2.get(0).ownText().trim() + "|";
					str += etd2.get(1).ownText().trim() + "|";
					str += etd2.get(2).ownText().trim() + "|";
					str += etd2.get(4).ownText().trim() + "|";
					str += etd2.get(5).ownText().trim() + "|";
					str += etd2.get(6).ownText().trim() + "|";
					String[] result = str.split("\\|");
					strArr[n] = result;
					n++;
					// listString.add(result);
				} else if (etd2.size() == 7 && n >= 4) {
					// String str = stockNum + "|";
					String str = "";
					// str += etd2.get(0).ownText().trim() + "|";
					str += etd2.get(1).ownText().trim() + "|";
					str += etd2.get(2).ownText().trim() + "|";
					str += etd2.get(4).ownText().trim() + "|";
					str += etd2.get(5).ownText().trim() + "|";
					str += etd2.get(6).ownText().trim() + "|";
					String[] result = str.split("\\|");
					strArr[n] = result;
					n++;
					// listString.add(result);
				}
			}
			if (strArr[0] != null && strArr[0].length == 7) {
				for (int x = 0, y = 4; x < 4; x++, y++) {
					// System.out.println(Arrays.toString(strArr[x]));
					// System.out.println(Arrays.toString(strArr[y]));
					String[] concat = new String[strArr[x].length
							+ strArr[y].length];
					System.arraycopy(strArr[x], 0, concat, 0, strArr[x].length);
					System.arraycopy(strArr[y], 0, concat, strArr[x].length,
							strArr[y].length);
					listString.add(concat);
				}
			} else {
				throw new Exception();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			strArr = null;
		}
		return listString;

	}

	public List<String[]> _Stock_Monthly_Info() {
		List<String[]> listString = new ArrayList<>();
		try {
			Document doc = getDoc(stock_earning, stock_info);
			Element body = doc.body();
			Elements els = body.select("tr[bgcolor*=#FFFFFF]");

			for (Element e : els) {

				Elements etd2 = e.getElementsByTag("td");
				if (etd2.size() == 9) {
					String str = stockNum + "|";
					if (etd2.get(0).ownText().trim().length() == 1) {
						str += "0" + etd2.get(0).ownText().trim() + "|";
					} else {
						str += etd2.get(0).ownText().trim() + "|";
					}
					str += etd2.get(1).ownText().trim() + "|";
					str += etd2.get(2).ownText().trim() + "|";
					str += etd2.get(4).ownText().trim() + "|";
					str += etd2.get(5).ownText().trim() + "|";
					str += etd2.get(6).ownText().trim() + "|";
					str += etd2.get(7).ownText().trim() + "|";
					str += etd2.get(8).ownText().trim();
					String[] result = str.split("\\|");
					int count = 0;
					for (int x = 0; x < result.length; x++) {
						if (result[x].equals("-")) {
							count++;
						}
					}
					if (count < 7) {
						listString.add(result);
					} else {
						throw new Exception();
					}
				}
			}
		} catch (Exception ex) {
			System.out.println("MONTH 無此股票資料  股票代號為: " + stockNum);
			listString = null;
		}
		return listString;

	}

	public String[] _Stock_Info() {
		Document doc;
		String str = "";
		String[] result = null;
		try {
			doc = getDoc(stock_info, buri + "q?s=" + stockNum);
			Element body = doc.body(); // 取出Body標籤(元素)
			// 股聘代號名稱
			Elements els = body.select("[color=#F70000]");
			for (Element e : els) {
				// System.out.println(e.text());
				str += e.text().substring(0, 4).trim() + "|";
				str += e.text().substring(4, e.text().length()).trim() + "|";
			}

			// 公司type 董事長
			Elements els2 = body.select("tr[bgcolor*=#FFFFFF]");
			int count = 0;
			for (Element e : els2) {
				count += 1;
				Elements etd = e.select("td[align=left]");
				if (count == 1 || count == 4) {
					// System.out.println(etd.text());
					str += etd.text().trim() + "|";
				}
			}
			// 股本
			count = 0;
			Elements els3 = body.getElementsByClass("yui-td-left");
			for (Element e : els3) {
				count += 1;
				if (count == 3) {
					// System.out.println(e.ownText());
					str += e.ownText().trim() + "|";
				}
			}
			doc = getDoc(stock_earning, stock_info);
			body = doc.body();
			Elements etd = doc.select("[width=560]");
			for (Element e : etd) {
				String[] strArray = e.toString().split("\"");
				str += buri + strArray[1] + "|";
			}
			result = str.split("\\|");
		} catch (Exception e1) {
			System.out.println("INFO 無此股票資料  股票代號為: " + stockNum);
		}
		return result;
	}

}
