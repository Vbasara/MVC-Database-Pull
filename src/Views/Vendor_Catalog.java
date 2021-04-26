package Views;//this will have the GUI
			  import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//use swing and make it into a desktop app that someone can open
					//in their desktop
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import Controllers.Purchase_Order;
import Controllers.VendorController;
import Model.VendorModel;

//import javax.sql.rowset.serial.SerialException;

public class Vendor_Catalog  extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Connection dbconn;
	private VendorController contr;
	private Purchase_Order POcontr;
	
	JButton VCbutton;
	JButton button1;
	JButton BomButton;
	JButton sendButton;    //responsible for sending the Purchase ORder
	JTextField vcTF = new JTextField();
	JTextField BomTF = new JTextField();
	JTextArea textAreaV = new JTextArea();
	JTextArea textAreaBOM = new JTextArea();
	JTextArea textAreaPO = new JTextArea();
	JTextField textField4 = new JTextField();
	JScrollPane scrollV= new JScrollPane(textAreaV);
	JScrollPane scrollBOM = new JScrollPane(textAreaBOM);
	JScrollPane scrollPO = new JScrollPane(textAreaPO);
	public Vendor_Catalog() {
		
		//insert GUI code here.
		
		vcTF.setPreferredSize(new Dimension (250,40));
		BomTF.setPreferredSize(new Dimension (250,40));
		textAreaV.setEditable(false);
		scrollV = new JScrollPane(textAreaV);
		textAreaBOM.setEditable(false);
		//textAreaPO.setEditable(false);
		scrollPO = new JScrollPane(textAreaPO);
		scrollBOM = new JScrollPane(textAreaBOM);
		scrollBOM.setPreferredSize(new Dimension (280,300));
		scrollV.setPreferredSize(new Dimension (280,300));
		scrollPO.setPreferredSize(new Dimension (280,300));
		textField4.setPreferredSize(new Dimension (250,40));
		
	
		
        VCbutton = new JButton("Search VC");
        VCbutton.setBounds(400,200,200,100);
        BomButton = new JButton("Search BOM");
        BomButton.setBounds(400,200,200,100);
        button1 = new JButton("Add VC Item");
        button1.setBounds(400,200,200,100);
        sendButton = new JButton("Send PO & Close");
        sendButton.setBounds(400,200,200,100);
		
        
        
		this.setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
		this.setTitle("EDU Erp Employee");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,520);
        this.setVisible(true);
        this.getContentPane().setBackground(Color.LIGHT_GRAY);
        
        JPanel Row1 = new JPanel();//row 1
        	//Row1.add(new JLabel("Hello"));
        	Row1.setBackground(Color.LIGHT_GRAY);
        	Row1.add(vcTF);
        	
        	Row1.add(BomTF);
        	Row1.add(Box.createRigidArea(new Dimension(300,0)));
        this.add(Row1);   
        
        JPanel Row2 = new JPanel();//row 2
        Row2.setBackground(Color.LIGHT_GRAY);
        Row2.add(VCbutton);
        Row2.add(Box.createRigidArea(new Dimension(130,0)));
        Row2.add(BomButton);
        Row2.add(Box.createRigidArea(new Dimension(180,0)));
        Row2.add(new JLabel("Purchase Order List"));
        VCbutton.setActionCommand("Give");
    	VCbutton.addActionListener(this);
    	BomButton.setActionCommand("Give2");
    	BomButton.addActionListener(this);
    	this.add(Row2);
    	
    	JPanel Row3 = new JPanel(); //row 3
    	
    	Row3.setBackground(Color.LIGHT_GRAY);
    	Row3.add(scrollV);
    	Row3.add(scrollBOM);
    	Row3.add(Box.createRigidArea(new Dimension(20,0)));
    	Row3.add(scrollPO);
    	
    	
    	this.add(Row3);
    	
    	JPanel Row4 = new JPanel (); //row 4
    	
    	Row4.setBackground(Color.LIGHT_GRAY);
    	Row4.add(button1);     
    	button1.setActionCommand("Add");
    	button1.addActionListener(this);
    	Row4.add(textField4);
        this.add(Row4);
        
        JPanel Row5 = new JPanel (); //row 5 
        
        Row5.setBackground(Color.LIGHT_GRAY);
        Row5.add(sendButton);
        sendButton.setActionCommand("Send");
    	sendButton.addActionListener(this);
    	this.add(Row5);
    	
    	HoldConn();
        
	}//constructor
	public void HoldConn() {
	String server = "penguin.cairn.edu";
	String portnum = "3306";
	String dbname = "eduerp";
	String user = "cis221";
	String password = "";
	String connString = "jdbc:mariadb://"+server+":"+portnum+"/"+dbname;
	try {
		dbconn = DriverManager.getConnection(connString,user,password);
		System.out.println("Connection established");
		
		contr = new VendorController(dbconn);
		POcontr = new Purchase_Order();//not using database, otherwise parameter would be dbconn.
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	} // HoldConn()

	public static void main(String args[]) {
		 //TODO Auto-generated method stub
	//move this stuff to constructor
		  SwingUtilities.invokeLater(new Runnable() {
			    public void run() {
			    	new Vendor_Catalog();
			    	
			    }
			  });

			} // main()
	//@Override
	private final static String newline = "\n";
	public void actionPerformed(ActionEvent e) {
		
		String cmd = e.getActionCommand();
		
		if (cmd.equals("Give")) { 			//if the action is Give(button 1) 
			//input what button does 
			System.out.println("VC Button Pressed");
			textAreaV.setText("");
			contr.SearchDatabase(vcTF.getText());
			int resultsCount = contr.getProductListSize();
			if (resultsCount==0) {
				JOptionPane.showMessageDialog(this, "No results found");
			}else {
				for (int i=0;i<resultsCount;i++) {
					
					textAreaV.setText(textAreaV.getText()+ "\r\n"+contr.getProductFromList(i));
				}
			}		
		}
		if (cmd.equals("Give2")) {
			System.out.println("BOM Button Pressed ");
			textAreaBOM.setText("");
			contr.SearchBOM(BomTF.getText());
			int resultsCount = contr.getProductListSize();
			if (resultsCount==0) {
				JOptionPane.showMessageDialog(this, "No results found");
			}else {
				for (int i=0;i<resultsCount;i++) {
					
					textAreaBOM.setText(textAreaBOM.getText()+ "\r\n"+contr.getProductFromList(i));
				}
			}		
		}
		
		if (cmd.equals("Add")) {
			System.out.println("button pressed add to PO");
			String text = textField4.getText();
			textField4.setText("");
			textAreaPO.append( text + newline);
		}
		
		if (cmd.equals("Send")) {
				System.out.println("button pressed send PO");
				//
				this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				this.dispose();
				if (textAreaPO == null) {
					return;
				}else {
					//send data within Text area to PO
				}
		}
		
			
		}
	} // class