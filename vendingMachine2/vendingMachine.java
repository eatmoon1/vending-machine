// Vending Machine client code
// Software engineering team 8
package vendingMachine2;

import java.awt.Dimension;
import java.net.ConnectException;
import java.util.Locale;
import java.awt.GridLayout;
import java.io.*;
import java.net.*;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class vendingMachine
{
	   public static void main(String argv[])
	   {
		 int result, portNumber=0;
		 double price = 0.0, cashTendered = 0.0, change = 0.0; // money transaction variables
	     String serverResponse, ipAddress, portString;
	     String jpgPath = null;

	     try
	     {
	    	File file = new File("config.txt");
	    	if (file.exists())
	    	{
	    	   BufferedReader br = new BufferedReader(new FileReader("config.txt"));
	           ipAddress  = br.readLine();
	           portString = br.readLine();
	           portNumber = Integer.parseInt(portString);
	           br.close();
	    	}
	    	else
	    	{
	    	   ipAddress = "localhost";
	    	   portNumber = 3333;
	    	}
	        
	        Socket clientSocket = new Socket(ipAddress, portNumber);
	        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
	        BufferedReader inFromServer  = new BufferedReader(
	 	    	                       new InputStreamReader(clientSocket.getInputStream()));
	     
	        
	        JTextField field1 = new JTextField();
	        JTextField field2 = new JTextField();
	        JTextField field3 = new JTextField();
	        
	        JPanel panel = new JPanel(new GridLayout(0, 1));
	        panel.setPreferredSize(new Dimension(700,600));
	     
	        
	        panel.add(new JLabel("[Buttons displayed here]"));
	        
	        panel.add(new JLabel("Queue of items to be purchased"));
			field1.setBounds(10, 591, 470, 50);
			panel.add(field1);
			
	        panel.add(new JLabel("Cash tendered"));
	        panel.add(field2);
	        
	        panel.add(new JLabel("Total price of items in queue"));
	        field3.setEditable(false);
	        panel.add(field3);
	        
	        panel.add(new JLabel("\r\n\r\n\r\nClick [OK] when you type in both fields."));
	        panel.setSize(new Dimension(300, 10));
	        panel.setPreferredSize(new Dimension(700, panel.getPreferredSize().height));
			
	        
	        

	        String osType = System.getProperty("os.name","generic").toUpperCase(Locale.ENGLISH);
	        
			if (osType.contains("WIN"))
			{
				jpgPath = "C:\\Temp\\vendingMachine.jpg";
			}
			else if (osType.contains("MAC"))
			{
	            String userPath = System.getProperty("user.name");
	            
	            jpgPath = "/Users/" + userPath + "/vendingMachine.jpg";
			}
			
	        final ImageIcon icon = new ImageIcon(jpgPath);
	        	        
	        result = JOptionPane.showConfirmDialog(null, 
	        		                       panel, 
	  		                               "Vending machine client",
	                                       JOptionPane.OK_CANCEL_OPTION, 
	                                       JOptionPane.PLAIN_MESSAGE,
	                                       icon);
	     
	        if (result == JOptionPane.OK_OPTION)
	        {
	        	if(cashTendered < price)
	        	{
	        		// TODO error message "insufficient funds"
	        	}
	        	else
	        	{
		    	   outToServer.writeBytes("Items bought>" + field1.getText() + '\n'); // items bought
		    	   serverResponse = inFromServer.readLine();
		    	   outToServer.writeBytes(field2.getText() + '\n'); //money received
		    	   serverResponse = inFromServer.readLine();
	        	}
	    	   
	        	
	    	   Thread t = new Thread(new Runnable()
	    	   {
	    	        public void run()
	    	        {
	    	        	JOptionPane.showMessageDialog(null,
	    	        			       field1.getText() + "   Transaction processed");
	    	        }
	    	   });
	    	   t.start();
	        	
	    	 
	    	   osType = System.getProperty("os.name","generic").toUpperCase(Locale.ENGLISH);
		        
			   if (osType.contains("WIN"))
			   {
					jpgPath = "C:\\Temp\\uberOK.jpg";
			   }
			   else if (osType.contains("MAC"))
			   {
		            String userPath = System.getProperty("user.name");
		            
		            jpgPath = "/Users/" + userPath + "/uberOK.jpg";
			   }
				
	    	   final ImageIcon icon2 = new ImageIcon(jpgPath);
	    	   
	    	   serverResponse = inFromServer.readLine();
	    	   JOptionPane.showMessageDialog(null, 
	    			                         serverResponse,
	    		                             "Vending machine Message",
       			                             JOptionPane.INFORMATION_MESSAGE,
       			                             icon2);
	    	   
	    	   //Reset queue and money display
	    	   field1.setText("");
	    	   field2.setText("");
	    	   field3.setText("");
	    	 
	    	 
	    	   outToServer.writeBytes("quit" + '\n');
	    	   Thread.sleep(5000);
	    	   
	           if (clientSocket != null)
	    	       clientSocket.close();
	    	}
	        else if (result == JOptionPane.OK_CANCEL_OPTION)
	        {
	        	outToServer.writeBytes("quit" + '\n');
	        }
	        else if (result == JOptionPane.CANCEL_OPTION)
	        {
	        	outToServer.writeBytes("quit" + '\n');
	        }
	        else if (result == JOptionPane.CLOSED_OPTION)
	        {
	        	outToServer.writeBytes("quit" + '\n');
	        }
	     } 
	     catch (ConnectException e)
	     {
	    	 JOptionPane.showMessageDialog(null,  
	    			 "ERROR : Can not connect to Vending Machine Socket Server Host!\r\n" +
	    	         "HELP  : Server may be down or try this : \r\n\r\n"       +
	    	         "HELP  : Create a file called config.txt and add two lines, <IP address> and <port number>.\r\n" +
	    			 "\r\n" +		 
	    			 "   192.168.2.15\r\n" +
	    	         "   3333\r\n",
	    			 "UBER Driver Application Error", 
	    			 JOptionPane.ERROR_MESSAGE);
	     }
		 catch (InterruptedException e)
	     {
			 JOptionPane.showMessageDialog(null, 
					 "ERROR : Interrupted Exception!",
					 "Vending machine Application Error", 
	    			 JOptionPane.ERROR_MESSAGE);
	     }
	     catch (UnknownHostException e)
	     {
	    	 JOptionPane.showMessageDialog(null, 
	    			 "ERROR : Unknown Host Exception!",
	    			 "Vending machine Application Error", 
	    			 JOptionPane.ERROR_MESSAGE);
		 }
	     catch (IOException e) 
	     {
	    	 JOptionPane.showMessageDialog(null, 
	    			 "ERROR : IO Exception!\r\n" +
	    	         "Communication with Vending machine Socket Server lost!\r\n",
	    			 "Vending machine Application Error", 
	    			 JOptionPane.ERROR_MESSAGE);
		 }   
	}
}