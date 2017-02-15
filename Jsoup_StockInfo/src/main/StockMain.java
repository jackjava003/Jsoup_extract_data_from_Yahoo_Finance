package main;

import java.util.ArrayList;
import java.util.List;

import connect_to_yahoo.StockGetHtml;
import infoDAO.Stock_infoDAO;
import infoDAO.Stock_mothDAO;
import infoDAO.Stock_quartDAO;
import stock_beans.Stock_infoBean;
import stock_beans.Stock_monthBean;
import stock_beans.Stock_quartBean;


public class StockMain {

	public static void main(String[] args) {
		Stock_infoDAO sdaoInit = new Stock_infoDAO();
		Stock_mothDAO smd = new Stock_mothDAO();
		 Stock_quartDAO sqd = new Stock_quartDAO();

		// 新建表格
		sdaoInit.init();
		System.out.println("==========init結束==========");
		//insert 股票基本資料  一次將資料insert 3個表格
		Stock_infoBean sb = null;
		for (int stockNum = 2000; stockNum < 2051; stockNum++) {
			StockGetHtml sgh = new StockGetHtml(stockNum);
			String[] result = sgh._Stock_Info();
			if (result != null) {
				sb = new Stock_infoBean(result);
				// sdaoInit.insert(sb);
			} else {
				continue;
			}
			List<String[]> listString = sgh._Stock_Monthly_Info();
			List<Stock_monthBean> listStock_monthBean = null;
			if (listString != null) {
				listStock_monthBean = new ArrayList<>();
				for (String[] str : listString) {
					Stock_monthBean smb = new Stock_monthBean(str);
					listStock_monthBean.add(smb);
				}
			} else{
				continue;
			}

			List<String[]> listString2 = sgh._Stock_Quarterly_Info();
			List<Stock_quartBean> listStock_quartBean = null;
			if (listString2 != null) {
				listStock_quartBean = new ArrayList<>();
				for (String[] str : listString2) {
					Stock_quartBean sqb = new Stock_quartBean(str);
					listStock_quartBean.add(sqb);
				}
			}else{
				continue;
			}
			if (sb != null && listStock_monthBean != null
					&& listStock_quartBean != null) {
				sdaoInit.insert(sb, listStock_monthBean, listStock_quartBean);
			}else{
				System.out.println(stockNum+"  資料有錯誤  不insert此股票資料" );
			}
			Thread th = Thread.currentThread();
			try {
				th.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("===========insert結束==========");
		
//		===============Stock Info 功能=======================
//		**Insert
//		Stock_infoBean sbInsert = new Stock_infoBean(100, "NONAME",
//				 "DONT_KNOW", "ME", "TEN_CENTS", "NULL", " ");
//		sdaoInit.insert(sbInsert);
		
//		**Update
//			 Stock_infoBean sbUpdate = new Stock_infoBean(2409, "NONAME",
//			 "DONT_KNOW", "ME", "TEN_CENTS", "NULL", " ");
//			 sdaoInit.update(sbUpdate);
//			 StockGetHtml sgh = new StockGetHtml(2409);
//			 Stock_infoBean sbUpdateBack = new Stock_infoBean(sgh._Stock_Info());
//			 sdaoInit.update(sbUpdateBack);
		
//		**delet
//			 sdaoInit.delete(2455);
		
//		**findByPrimaryKey
//		   	 Stock_infoBean sib = new Stock_infoBean();
//			 sib=sdaoInit.findByPrimaryKey(2456);
//			 System.out.println(sib.toString());
		
//		**findAll
//			 List<Stock_infoBean> list = sdaoInit.findAll();
//			 for(Stock_infoBean info:list){
//				System.out.print(info.toString());
//			}
		
//		===============Stock Month 功能=======================
//		**Insert
//		Stock_monthBean smb1 = new Stock_monthBean(2455, "13", 123,
//				new BigDecimal(104), 123, new BigDecimal(105),
//				123, new BigDecimal(105), "ABC");
//		smd.insert(smb1);
		
//		**Update
//			Stock_monthBean smb = new Stock_monthBean(2455, "01", 123,
//					new BigDecimal(104), 123, new BigDecimal(105),
//					123, new BigDecimal(105), "ABC");
//		smd.update(smb);
//			StockGetHtml sgh = new StockGetHtml(2455);
//			List<String[]> listString = sgh._Stock_Monthly_Info();
//			if (listString != null) {
//				for (String[] str : listString) {
//					Stock_monthBean smbUpdateBack = new Stock_monthBean(str);
//					smd.update(smbUpdateBack);
//				}
//			}
		
//		**findByPrimaryKey
//		List<Stock_monthBean> list = smd.findByPrimaryKey(2456);
//		for(Stock_monthBean info:list){
//			System.out.print(info.toString());
//		}
//		Stock_monthBean oneResult = smd.findByPrimaryKey(2049,"02");
//			System.out.print(oneResult.toString());
		
//		**Delete
// 		smd.delete(2455);
//		smd.delete(2049, "04");

//		**findAll
//		List<Stock_monthBean> listAll = smd.findAll();
//		for(Stock_monthBean info:listAll){
//			System.out.print(info.toString());
//		}
		
//		===============Stock Quad 功能=======================
////	**Insert
//		Stock_quartBean sqb1 = new Stock_quartBean(2049, 5, 123,"AAA", 123, "BBB",
//		"123",123, "DDD", 333, "ABC", "EEE");
//		sqd.insert(sqb1);
		
////	**Update
//		Stock_quartBean sqb = new Stock_quartBean(2049, 4, 444,"AAAA", 1234, "BBBB",
//				"1234",1234, "DDDD", 3333, "ABCD", "EEEE");
//		sqd.update(sqb);
		
//		StockGetHtml sgh = new StockGetHtml(2049);
//		List<String[]> listString = sgh._Stock_Quarterly_Info();
//		if (listString != null) {
//			for (String[] str : listString) {
//				Stock_quartBean sqbUpdateBack = new Stock_quartBean(str);
//				sqd.update(sqbUpdateBack);
//			}
//		}
		
////	**findByPrimaryKey
//		List<Stock_quartBean> list = sqd.findByPrimaryKey(2049);
//		for(Stock_quartBean info:list){
//			System.out.print(info.toString());
//		}
//		Stock_quartBean oneResult = sqd.findByPrimaryKey(2049,2);
//			System.out.print(oneResult.toString());
//		
////	**Delete
// 		sqd.delete(2038);
//		sqd.delete(2049, 5);
//		
////	**findAll
//		List<Stock_quartBean> listAll = sqd.findAll();
//		for(Stock_quartBean info:listAll){
//			System.out.print(info.toString());
//		}
		


	}
}
