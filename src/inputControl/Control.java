package inputControl;

import java.util.Scanner;

public class Control {
	Scanner scc=new Scanner(System.in).useDelimiter(";");
	Scanner sc = new Scanner(System.in);
	MethodControl mc = new MethodControl();
	
	public void controlStatement(){
		System.out.print("Option -> ");
		String control=sc.nextLine();
		menu(control);
	}
	
		public void menu(String control){
			switch(control){
				case "*": 
					mc.display(1,MethodControl.row);
					showControl();
				break;
				case "r": 
					mc.read();
					showControl();
				break;
				case "w":
					mc.write();
					showControl();
					break;
				case "u":
					mc.update();
					mc.display(1,MethodControl.row);
					showControl();
					break;
				case "d": 
					mc.delete();
					mc.display(1,MethodControl.row);
					showControl();
					break;
				case "f":
					mc.first();
					showControl();
					break;
				case "p":
					mc.previous();
					showControl();
					break;
				case "n": 
					mc.next();
					showControl();
					break;
				case "l": mc.last();
				break;
				case "g": 
					mc.goTo();
					showControl();
					break;
				case "se":
					mc.setRow();
					mc.display(1,MethodControl.row);
					showControl();
					break;
				case "s": 
					mc.search();
					showControl();
					break;
				case "sa":
					mc.save();
					break;
				case "b":
					mc.backup();
					showControl();
					break;
				case "re":
					mc.restore();
					showControl();
					break;
				case "h": mc.help();
				break;
				case "e": mc.exit();
				break;
				default: 
					System.out.println("try again!");
					controlStatement();
					break;
			}
		}

		public void showControl(){
			System.out.println("_________________________________________________________________________________");
			System.out.println("*)Display  | W)rite | R)ead | U)pdate | D)elete | F)irst | P)revious N)ext");
			System.out.println("L)ast | S)earch | G)o to | Se)t row | Sa)ve | B)ackup | Re)store | H)elp | E)xit");
			System.out.println("_________________________________________________________________________________");
			controlStatement();
		}
}
