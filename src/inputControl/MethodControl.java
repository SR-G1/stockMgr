package inputControl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

import readWriteInfo.ReadWriteInfo;
import readWriteInfo.StockInfo;

public class MethodControl {
	static int page=1, row=5;
	private static boolean save=false;
	ReadWriteInfo rwi = new ReadWriteInfo();
	Scanner sc = new Scanner(System.in);
	public void display(int s, int r){
		rwi.reverseStockPage();
		ReadWriteInfo.iRead(null, s, r);
		page=1;
	}
	public void read(){
		rwi.reverseStock();
		System.out.print("Enter id: ");
		int index = sc.nextInt();
		index-=1;
		readInfo(index);
	}
	private void readInfo(int index) {
		System.out.println("=====information's detail=====");
		System.out.println("ID: "+ReadWriteInfo.stock.get(index).id);
		System.out.println("NAME: "+ReadWriteInfo.stock.get(index).name);
		System.out.println("UNIT PRICE: "+ReadWriteInfo.stock.get(index).price);
		System.out.println("QUANTITY: "+ReadWriteInfo.stock.get(index).quantity);
		System.out.println("DATE: "+ReadWriteInfo.stock.get(index).date);
	}
	public void write() {
//		@SuppressWarnings("resource")
//		Scanner scc=new Scanner(System.in).useDelimiter(";");`
//		String file="assets/backup.tmp";
		rwi.reverseStock();
		int id = ReadWriteInfo.stock.size()+1;
		System.out.println("Id: "+id);
		System.out.print("Name: ");
		String name=sc.next();
		System.out.print("Price: "); 
		int price=sc.nextInt();
		System.out.print("Quantity: "); 
		int quantity=sc.nextInt();
		page=1;
		ReadWriteInfo.stock.add(new StockInfo(id, name, price, quantity, rwi.getDate("yyyy/MM/dd")));
		display(page, row);
	}
	public void update(){
		System.out.println("Item id to update: ");
		int id = sc.nextInt();
		System.out.println("Do you want to update all?");
		System.out.println("(al)All\t(n)Name\t(p)Price\t(q)Quantity");
		System.out.print("Choose: ");
		String ch=sc.next();
		if(ch.equalsIgnoreCase("al")){
			System.out.print("Name > ");
			String name = sc.next();
			System.out.print("Price > ");
			int price = sc.nextInt();
			System.out.print("Quantity > ");
			int quantity = sc.nextInt();
			updateList(id, name, price, quantity);
		}else{
			System.out.println("bbb");
		}
	}
	public void delete(){
		rwi.reverseStock();
		System.out.println("id to Delete: ");
		int id=sc.nextInt()-1;
		ReadWriteInfo.stock.remove(id);
	}
	public void first(){
		rwi.reverseStockPage();
		ReadWriteInfo.iRead(null,1, MethodControl.row);
		page=1;
	}
	public void previous(){
		rwi.reverseStockPage();
		if(page==1){
			page=ReadWriteInfo.pages;
			ReadWriteInfo.iRead(null,page, row);
		}else{
			ReadWriteInfo.iRead(null,--page, row);
		}
	}
	public void next(){
		rwi.reverseStockPage();
		if(page==ReadWriteInfo.pages){
			page=1;
			ReadWriteInfo.iRead(null, page, row);
		}else{
			ReadWriteInfo.iRead(null,++page, row);
		}
	}
	public void last(){
		page=ReadWriteInfo.pages;
		rwi.reverseStockPage();
		ReadWriteInfo.iRead(null, page, MethodControl.row);
	}
	public void search(){
		System.out.print("Enter to search: ");
		String name = sc.next();
		ArrayList<StockInfo> list = searchProcess("(.*)("+name+"+)(.*)");
		ReadWriteInfo.iRead(list, page, row);
	}
	
	private ArrayList<StockInfo> searchProcess(String key){
		Pattern pattern = Pattern.compile(key);
		ArrayList<StockInfo> list = new ArrayList<>();
		for(StockInfo s:ReadWriteInfo.stock){
			if(pattern.matcher(s.name).matches()){
				list.add(new StockInfo(s.id, s.name, s.price, s.quantity, s.date));
			}
		}
		return list;
	}
	
	public void goTo(){
		rwi.reverseStockPage();
		System.out.print("Page: ");
		page=sc.nextInt();
		ReadWriteInfo.iRead(null, page, row);
	}
	public void setRow(){
		System.out.print("Row: ");
		int row = sc.nextInt();
		MethodControl.row=row;
		page=1;
	}
	public void save(){
		try {
			rwi.writeStock(ReadWriteInfo.stock, ReadWriteInfo.stockFile, 0);
			System.out.println("save complete!");
			save=true;
		} catch (IOException e) {
			System.out.println("Error save");
		}
	}
	public void backup(){
		try {
			rwi.writeStock(ReadWriteInfo.stock, "assets/"+rwi.getDate("yyyyMMdd")+".tmp", 0);
			System.out.println("Backup complete!");
		} catch (IOException e) {
			System.out.println("Error backup..");
		}
	}
	public void restore() {
		ReadWriteInfo.stock=null;
		ReadWriteInfo.stockFile="assets/"+rwi.getDate("yyyyMMdd")+".tmp";
		ReadWriteInfo.iRead(ReadWriteInfo.stock, page, row);
		rwi.reverseStockPage();
		System.out.println("Restore complete!");
	}
	public void help(){}
	public void exit(){
		if(!save){
			System.out.print("Do you to Save?[y/n]");
			String exit=sc.next();
			if(exit.equalsIgnoreCase("y")){
				save();
				System.exit(0);
			}
			else if(exit.equalsIgnoreCase("n")){
				Control c=new Control();
				display(page, row);
				c.showControl();
				c.controlStatement();
			}else{
				System.out.println("please try again, you can input only [y/n].");
				exit();
			}
		}else{
			System.out.println("Thanks for using my app.");
			System.exit(0);
		}
	}
	public void updateList(int id, String name, int price, int quantity){
		System.out.print("Are you sure Updater?");
		String con = sc.next();
		if(con.equalsIgnoreCase("y")){
			rwi.reverseStock();
			ReadWriteInfo.stock.set(id-1, new StockInfo(id, name, price, quantity, rwi.getDate("yyyy/MM/dd")));
		}else if(con.equalsIgnoreCase("n")){
			System.out.println("bye");
		}else{
			System.err.println("You can use (y/n).");
			updateList(id, name, price, quantity);
		}
	}
	
}
