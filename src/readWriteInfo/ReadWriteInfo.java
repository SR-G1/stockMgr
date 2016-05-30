package readWriteInfo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.CellStyle.HorizontalAlign;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

public class ReadWriteInfo {
	public static int arrSize, pages;
	public static String stockFile="assets/stockdb.dat";
	public static  ArrayList<StockInfo> stock=null, stockTmp=null;
	public void writeStock(ArrayList<StockInfo> list, String file, int record) throws IOException{
		FileOutputStream fos = new FileOutputStream(file);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		if(list==null){
			oos.writeObject(addToList(record));
		}else{
			oos.writeObject(list);
		}
		oos.close(); fos.close();
	}
	
	public ArrayList<StockInfo> addToList(int record){
		ArrayList<StockInfo> list = new ArrayList<>();
		for(int i=1; i<=record; i++)
			list.add(new StockInfo(i, "samurai", 0, 0, "2016"));
		return list;
	}
		
	public String getDate(String datetime){
		DateFormat dateFormat = new SimpleDateFormat(datetime);
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	private static int getPages(double totalPages, double row){
		double getPage=Math.ceil(totalPages/row);
		return (int) getPage;
	}
	
	private static int getRanges(int s, int r){
		return (s-1)*r;
	}
	
	public static void getArrayListSize(int size){
		arrSize=size;
	}
	
	public void reverseStock(){
		if(stock.get(0).id!=1){
			Collections.reverse(stock);
		}
	}
	
	public void reverseStockPage(){
		if(stock.get(0).id==1){
			Collections.reverse(stock);
		}
	}
	
	public static ObjectInputStream readObj(String file) throws IOException{
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream bis = new BufferedInputStream(fis);
		ObjectInputStream ois = new ObjectInputStream(bis);
		return ois;
	}
	
	@SuppressWarnings("unchecked")
	public static void iRead(ArrayList<StockInfo> list, int start, int row){
		try {
			if(list!=null){
				stockTmp=stock;
				stock=list;
			}else if(stock==null){
				stock = (ArrayList<StockInfo>) readObj(stockFile).readObject();
			}
			if(stock.get(0).id==1){
				Collections.reverse(stock);
			}
			
			getArrayListSize(stock.size());
			int s=getRanges(start, row);
			pages=getPages(stock.size(), row);
			Table t = new Table(5, BorderStyle.UNICODE_BOX_HEAVY_BORDER, ShownBorders.SURROUND_HEADER_AND_COLUMNS);
			CellStyle numberStyle = new CellStyle(HorizontalAlign.center);
			    
		    t.setColumnWidth(0, 15, 50);
	        t.setColumnWidth(1,15, 50);
	        t.setColumnWidth(2, 15, 50);
	        t.setColumnWidth(3, 15, 50);
	        t.setColumnWidth(4, 15, 50);
	        
		    t.addCell("ID", numberStyle);
		    t.addCell("NAME",numberStyle);
		    t.addCell("PRICE",numberStyle);
		    t.addCell("QUANTITY",numberStyle);
		    t.addCell("DATE",numberStyle);
		    
			for(int i=s; i<s+row; i++){
				if(i<stock.size()){
					t.addCell(stock.get(i).id+"", numberStyle);
					t.addCell(stock.get(i).name, numberStyle);
					t.addCell(stock.get(i).price+"", numberStyle);
					t.addCell(stock.get(i).quantity+"", numberStyle);
					t.addCell(stock.get(i).date, numberStyle);
				} 
			}
			System.out.println(t.render());
			System.out.println("\nPage: "+start+"-"+pages+"\t\t\tTotal: "+stock.size());
			readObj(stockFile).close();
			if(stock==list){
				stock = stockTmp;
				if(stock.get(0).id!=1){
					Collections.reverse(stock);
				}
			}
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("file not found xx");
		}	
	}
}
