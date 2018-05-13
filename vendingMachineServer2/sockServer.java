package vendingMachineServer2;

import java.io.IOException;
import java.io.BufferedReader;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.ParseException;

public class sockServer implements Runnable
{
	   Socket csocket;
	   String ipString;
	   char threadType;

	   static Vector<String> vec = new Vector<String>(5);
	   
	   public static Hashtable<String, kiosk> clients = 
			     new Hashtable<String, kiosk>();
	   
	   static final String newline = "\n";
	   static int first_time = 1;
	   
	   static int port_num = 3333;
	   
	   static int numOfConnections = 0;
	   static int numOfMessages = 0;
	   static int max_connections = 5;
	   static int numOfTransactions = 0; 
	   static double earnings = 0; // sum of all earnings from transactions
	   
	   
	   static double earned; // money received in last transaction

	   sockServer(Socket csocket, String ip)
	   {
	      this.csocket  = csocket;
	      this.ipString = ip;
	   } 

	   public static void runSockServer()   // throws Exception
	   {
	     boolean sessionDone = false;
	  
	     ServerSocket ssock = null;
	   
	     try
	     {
		   ssock = new ServerSocket(port_num);
	     }
	     catch (BindException e)
	     {
		    e.printStackTrace();
	     }
	     catch (IOException e)
	     {
		    e.printStackTrace();
	     }
	 
	     // update the status text area to show progress of program
	     try 
	     {
		     InetAddress ipAddress = InetAddress.getLocalHost();
		     vmServer.textArea.append("IP Address : " + ipAddress.getHostAddress() + newline);
	     }
	     catch (UnknownHostException e1)
	     {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
	     }
	 
	     vmServer.textArea.append("Listening on port " + port_num + newline);
	     
	     // show current money made
	     vmServer.textArea.append("Total earnings: $" + earnings + newline);
	     
	     vmServer.textArea.setCaretPosition(vmServer.textArea.getDocument().getLength());
	     vmServer.textArea.repaint();
	 
	     // initialize the hash table
	     clients.put("kiosk#1", new kiosk("kiosk#1", 0, 0, 0.0));
	     clients.put("kiosk#2", new kiosk("kiosk#2", 0, 0, 0.0));
	     clients.put("kiosk#3", new kiosk("kiosk#3", 0, 0, 0.0));
	     
	     sessionDone = false;
	     while (sessionDone == false)
	     {
	        Socket sock = null;
		    try
		    {
		    // blocking system call
			   sock = ssock.accept();
		    }
		    catch (IOException e)
		    {
			   e.printStackTrace();
		    }
		 
		    // update the status text area to show progress of program
	        vmServer.textArea.append("Client Connected : " + sock.getInetAddress() + newline);
	        vmServer.textArea.setCaretPosition(vmServer.textArea.getDocument().getLength());
	        vmServer.textArea.repaint();
	        
	        new Thread(new sockServer(sock, sock.getInetAddress().toString())).start();
	     }
	 
	     try 
	     {
		    ssock.close();
	     }
	     catch (IOException e) 
	     {
		    e.printStackTrace();
	     }
	}	  

	   
	   
	static synchronized void hashOperation(char type, String key, String ticks, String d)
	{
		switch (type)
		{
			case 'T':
				if (clients.containsKey(key) == true)
		        {
					clients.get(key).incrementTrans();
					clients.get(key).addTickets(Integer.parseInt(ticks));
					clients.get(key).addDollars(Double.parseDouble(d));
		        }	
			break;
		}
	}
	   
	public static String getAllTransactions()
	{
		String rs="";
		
		rs = clients.get("kiosk#1").toString()      + "\r\n";
		rs = rs + clients.get("kiosk#2").toString() + "\r\n";
		rs = rs + clients.get("kiosk#3").toString() + "\r\n";
				
		return rs;
	}

	// This is the thread code that ALL clients will run()
	public void run()
	{
	   try
	   {
		  boolean session_done = false; 
	      long threadId;
	      String clientString;
	      String keyString = "";
	    
	      threadId = Thread.currentThread().getId();
	      
	      numOfConnections++;
	      
	      vmServer.textArea.append("Num of Connections = " + numOfConnections + newline);
	      vmServer.textArea.setCaretPosition(vmServer.textArea.getDocument().getLength());
	      vmServer.textArea.repaint();
	      
	      keyString = ipString + ":" + threadId;
	      
	      if (vec.contains(keyString) == false)
	        {
	    	    int counter = 0;
	        	vec.addElement(keyString);
	        	
	        	vmServer.textArea_2.setText("");
	        	Enumeration<String> en = vec.elements();
	        	while (en.hasMoreElements())
	        	{
	        		vmServer.textArea_2.append(en.nextElement() + " || ");
	        		
	        		if (++counter >= 6)
	        		{
	        			vmServer.textArea_2.append("\r\n");
	        			counter = 0;
	        		}
	        	}

  	            vmServer.textArea_2.repaint();
	        }
	       
	      PrintStream pstream = new PrintStream (csocket.getOutputStream());
	      BufferedReader rstream = new BufferedReader(new InputStreamReader(csocket.getInputStream()));
	       
	      while (session_done == false)
	      {
	       	if (rstream.ready())   // check for any data messages
	       	{
	              clientString = rstream.readLine();
	              
	              DecimalFormat decimalFormat = new DecimalFormat("#");
	              
	               // Displayed transaction details and add earnings from last transaction to total earnings.
	               try {
		                 earned = decimalFormat.parse(clientString).doubleValue();
		                 earnings += earned;
		                 vmServer.textArea.append("$" + earned + " received." + newline);
		                 vmServer.textArea.append("Total earning: $" + earnings + newline);
		              } catch (ParseException e) {
		            	  vmServer.textArea.append(clientString + " purchased." + newline);
		              }
	     	       vmServer.textArea.setCaretPosition(vmServer.textArea.getDocument().getLength());
	     	       vmServer.textArea.repaint();
	              
	              /* old transaction log
	              // update the status text area to show progress of program
	   	           vmServer.textArea.append("RECV : " + clientString + newline);
	     	       vmServer.textArea.setCaretPosition(vmServer.textArea.getDocument().getLength());
	     	       vmServer.textArea.repaint();
	     	       // update the status text area to show progress of program
	     	       vmServer.textArea.append("RLEN : " + clientString.length() + newline);
	     	       vmServer.textArea.setCaretPosition(vmServer.textArea.getDocument().getLength());
	     	       vmServer.textArea.repaint();
	     	       */
	              
	              if (clientString.length() > 128)
	              {
	           	   session_done = true;
	           	   continue;
	              }

	              if (clientString.contains("quit"))
	              {
	                 session_done = true;
	              }
	              else if (clientString.contains("QUIT"))
	              {
	                 session_done = true;
	              }
	              else if (clientString.contains("Quit"))
	              {
	                 session_done = true;
	              }
	              else if (clientString.contains("Query>"))
	              {
	            	  String tokens[] = clientString.split("\\>");
	            	  
	            	  if (clients.containsKey(tokens[1]) == true)
	            	  {
	            		  pstream.println(clients.get(tokens[1]).toString());  
	            	  }
	            	  else
	            	  {
	            		  pstream.println("NACK : ERROR : No such kiosk number!");
	            	  }
	              }
	              else if (clientString.contains("Transaction>"))
	              {
	            	  String tokens[] = clientString.split("\\>");
	            	  String args[]   = tokens[1].split("\\,");
	            	  
	            	  if (clients.containsKey(args[0]) == true)
	            	  {
	            		  hashOperation('T', args[0], args[1], args[2]);
	            		  
	            		  pstream.println("ACK");
	            	  }
	            	  else
	            	  {
	            		  pstream.println("NACK : ERROR : No such kiosk number!");
	            	  }
	              }
	              else if (clientString.contains("Date>"))
	              {
	            	numOfMessages++;
	            	  
	            	// Create an instance of SimpleDateFormat used for formatting 
	            	// the string representation of date (month/day/year)
	            	DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

	            	// Get the date today using Calendar object.
	            	Date today = Calendar.getInstance().getTime();
	            	
	            	// Using DateFormat format method we can create a string 
	            	// representation of a date with the defined format.
	            	String reportDate = df.format(today);

	            	// Print what date is today!
	            	pstream.println("Num Of Messages : " + numOfMessages + "   Simple Date: " + reportDate);
	              }
	              else
	              {
	            	  pstream.println("NACK : ERROR : No such command!");
	              }
	       	   }
	         			    		        	
	           Thread.sleep(500);
	           
	        }    // end while loop
	
            keyString = ipString + ":" + threadId;
	      
	        if (vec.contains(keyString) == true)
	        {
	        	int counter = 0;
	        	vec.removeElement(keyString);
	        	
	        	vmServer.textArea_2.setText("");
	        	Enumeration<String> en = vec.elements();
	        	while (en.hasMoreElements())
	        	{        		     		
                    vmServer.textArea_2.append(en.nextElement() + " || ");
	        		
	        		if (++counter >= 6)
	        		{
	        			vmServer.textArea_2.append("\r\n");
	        			counter = 0;
	        		}
	        	}

  	            vmServer.textArea_2.repaint();
	        }
	      
	        numOfConnections--;

	        // close client socket
	        csocket.close();
	       
	        // update the status text area to show progress of program
		     vmServer.textArea.append("Child Thread : " + threadId + " : is Exiting!!!" + newline);
		     vmServer.textArea.append("Num of Connections = " + numOfConnections);
		     vmServer.textArea.setCaretPosition(vmServer.textArea.getDocument().getLength());
		     vmServer.textArea.repaint();
		     
	     } // end try  
	 
	     catch (SocketException e)
	     {
		  // update the status text area to show progress of program
	      vmServer.textArea.append("ERROR : Socket Exception!" + newline);
	      vmServer.textArea.setCaretPosition(vmServer.textArea.getDocument().getLength());
	      vmServer.textArea.repaint();
	     }
	     catch (InterruptedException e)
	     {
		  // update the status text area to show progress of program
	      vmServer.textArea.append("ERROR : Interrupted Exception!" + newline);
	      vmServer.textArea.setCaretPosition(vmServer.textArea.getDocument().getLength());
	      vmServer.textArea.repaint();
	     }
	     catch (UnknownHostException e)
	     {
		  // update the status text area to show progress of program
	      vmServer.textArea.append("ERROR : Unkonw Host Exception" + newline);
	      vmServer.textArea.setCaretPosition(vmServer.textArea.getDocument().getLength());
	      vmServer.textArea.repaint();
	     }
	     catch (IOException e) 
	     {
	     // update the status text area to show progress of program
	      vmServer.textArea.append("ERROR : IO Exception!" + newline);
	      vmServer.textArea.setCaretPosition(vmServer.textArea.getDocument().getLength());
	      vmServer.textArea.repaint();       
	     }     
	     catch (Exception e)
	     { 
		  numOfConnections--;
		  
		  // update the status text area to show progress of program
	      vmServer.textArea.append("ERROR : Generic Exception!" + newline);
	      vmServer.textArea.setCaretPosition(vmServer.textArea.getDocument().getLength());
	      vmServer.textArea.repaint(); 
	     }
	   
	  }  // end run() thread method
	
	
}