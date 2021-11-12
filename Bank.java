/**
 *																	Om Namah Shivaay
 *
 * Simulates the working of a real-world banking application.
 * @author: arun-first
 *
 * Compilation: javac Bank.java
 * Execution: java alpha_bank.Bank
 */

package alpha_bank; // package

import java.util.Scanner; // scanner class
import java.io.FileWriter; // file writer
import java.io.BufferedReader; // buffered reader
import java.io.FileReader; // file reader
import java.io.IOException; // io exception

public class Bank {
	// necessary variables
	private String holderName = " "; // holder's name
	private String holderDOB = " "; // holder's DOB
	private String holderSO = " "; // father's name
	private String holderAadhar = " "; // aadhar
	private String holderMob = " "; // mobile number
	private long accNo = 0; // account no
	private int mpin = 0; // for security reasons

	// variables for transaction
	private double bal = 0.00d, transact = 0.00d;

	// variables for menu
	int choice = 0;

	// necessary instantiation
	static Scanner stdInput = new Scanner(System.in);
	static Bank bnk = new Bank();
	static FileWriter fw = null;
	static BufferedReader read = null;

	// menus
	static String[] firstMenu = {"","OPEN ACCOUNT","LOGIN INTO EXISTING ACCOUNT","EXIT"};
	static String[] mainMenu = {"","DEPOSIT MONEY","WITHDRAW MONEY","MINI STATEMENT","CHECK BALANCE","EXIT"}; 
	static String[] userDetails = new String[8]; // to store previous user details

	// main method
	public static void main(String[] args) {
		do {
			bnk.menu();

			// displaying according to menu
			switch (bnk.choice) {
				case 1: bnk.openAcc();
						break;
				case 2: bnk.loginAccount();
						break;
				case 3: bnk.endScreen();
						System.exit(0);
						break;
			}
		} while (bnk.choice != 3);
	}

	// main menu
	public void menu () {
		bnk.startScreen();
		for (int i = 1, n = this.firstMenu.length; i < n; i++)
			System.out.println("\t\t["+i+"] "+firstMenu[i]);
		
		do {
			try {
				System.out.print("\t\tPress [1 - 3]: ");
				bnk.choice = stdInput.nextInt();
			} catch (Exception e) {
				System.out.println("\t\tERROR: invalid input");
			}
		} while (bnk.choice < 1 || bnk.choice > 3);

		bnk.endScreen();
	}

	// account open menu
	public void openAcc () {
		bnk.startScreen();
		bnk.getDetails(); // getting the details of the customer

		// generating acc
		bnk.accNo = bnk.rndmAcc();

		// printing the account number
		System.out.println("\t\tAccount No.: "+bnk.accNo);
		System.out.println("\t\tMPIN: "+bnk.mpin);

		// uploading data
		boolean uploaded = bnk.uploadData();
		if (uploaded) 
			System.out.println("Account successfully created.");
		else
			System.out.println("Server busy. Please try after some time");
		bnk.endScreen();
	}

	// login into your bank account
	public int loginAccount () {
		bnk.startScreen();
		System.out.print("\t\tMPIN: ");
		bnk.mpin = stdInput.nextInt();

		// check for account existence
		boolean isAccount = bnk.recoverData(bnk.mpin);
		if (!isAccount) {
			System.out.println("\t\tERROR: Account doesn't exists!!");
			return 1;
		}

		// getting the details printed
		bnk.printDetails();

		// printing the choice menu
		for (int i = 1, n = this.mainMenu.length;i < n;i++) 
			System.out.println("\t\t["+i+"] "+this.mainMenu[i]);
		System.out.print("\t\tEnter [1 - 5]: ");
		bnk.choice = stdInput.nextInt(); // getting the choice

		// displaying details according to choice
		switch (bnk.choice) {
			case 1: bnk.deposit();
					break;
			case 2: bnk.debit();
					break;
			case 3: System.out.println("\t\tFeature will be availaible soon.");
					break;
			case 4: System.out.println("\t\tRemaining Balance = "+bnk.bal);
					break;
			case 5: bnk.endScreen();
					System.exit(0);
					break;
		}
		bnk.endScreen();
		return 0;
	}

	// get account details
	public void getDetails () {
		System.out.print("\t\tName: ");
		bnk.holderName = stdInput.nextLine();
		System.out.print("\t\tFather's Name: ");
		bnk.holderSO = stdInput.nextLine();
		System.out.print("\t\tD.O.B: ");
		bnk.holderDOB = stdInput.nextLine();
		System.out.print("\t\tAadhar: ");
		bnk.holderAadhar = stdInput.nextLine();
		System.out.print("\t\tMobile No.: (+91) ");
		bnk.holderMob = stdInput.nextLine();

		// getting a starting balance
		do {
			System.out.print("\t\tAccount Opening Amount[more than Rs.2000]: ");
			bnk.bal = stdInput.nextDouble();
		} while (bnk.bal < 2000);
	}

	// print account details
	public void printDetails () {
		bnk.mpin = Integer.parseInt(this.userDetails[0]); // mpin
		bnk.holderName = this.userDetails[1]; // holder name
		bnk.accNo = Long.parseLong(this.userDetails[4]); // holder Account Number
		bnk.bal = Double.parseDouble(this.userDetails[7]); // balance

		// printing details
		System.out.println("\t\tWelcome Mr./Mrs. "+bnk.holderName);
	}

	// random account number generator
	public long rndmAcc () {
		long num = 1154630878;
		num = 11546308 + (int)(Math.random()*(50-10+1)+10);

		bnk.mpin = (int)(Math.random() * (5000-1000+1)+1000); // mpin for the user
		return num;
	}
	
	// accept money
	public void deposit() {
		bnk.startScreen(); // first screen

		// taking the amount to be deposited
		do {
			try {
				System.out.print("\t\tAmount: Rs.");
				bnk.transact = stdInput.nextDouble();
			} catch (Exception e) {
				System.out.println("\t\tERROR: invalid amount");
			}
		} while (bnk.transact < 0);

		bnk.bal += bnk.transact; // depositing the money
		bnk.endScreen();
	}

	// withdraw money
	public void debit() {
		bnk.startScreen(); 

		// taking the amount to be deposited
		do {
			try {
				System.out.print("\t\tAmount: Rs.");
				bnk.transact = stdInput.nextDouble();
			} catch (Exception e) {
				System.out.println("\t\tERROR: invalid amount");
			}
		} while (bnk.transact < 0);

		// deducting balance
		if (bnk.bal < bnk.transact) 
			System.out.println("\t\tERROR: Insufficient funds to fulfill transaction");
		else
			bnk.bal -= bnk.transact;
		bnk.endScreen();
	}
	
	// starting screen
	public void startScreen() {
		System.out.println("\t\t##########################################");
		System.out.println("\t\t\t\tALPHA BANK");
		System.out.println("\t\t\t\t**********\n");
	}

	// closing screen
	public void endScreen() {
		System.out.println("\t\t##########################################");
		System.out.println("\t\t\tThanks for being with us!!:)");
	}

	// writting to the file
	public boolean uploadData () {
		boolean up = false;
		try {
			fw = new FileWriter("server.txt",true);
			fw.append(bnk.mpin+","+bnk.holderName+","+bnk.holderSO+","+bnk.holderDOB+","+bnk.accNo+","+bnk.holderAadhar+","+bnk.holderMob+","+bnk.bal+",\n");
			up = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fw.flush();
				fw.close();
				up = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return up;
	}

	// reading from file
	public boolean recoverData (int mpin) {
		boolean rd = false; // to track and find data

		// reading data
		try {
			read = new BufferedReader(new FileReader("server.txt"));
			String line = " ";
			
			while((line = read.readLine()) != null) {
				this.userDetails = line.split(","); // getting data

				// comparing to the given mpin
				if (Integer.parseInt(userDetails[0]) == mpin) {
					rd = true;
					break;
				}
				else
					rd = false;
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		}

		return rd;
	}
}
