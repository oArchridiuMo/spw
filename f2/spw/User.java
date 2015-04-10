package f2.spw;

import javax.swing.JOptionPane; 

public class User{
	public String name;
	
	public User(){
		this.name = JOptionPane.showInputDialog("Enter your name: ");
	}

	public String getName(){
		return this.name;
	}

}