// Kaeta Shiga
// Team 8
// Software Engineering
// Last modified: 2/21/2018
// Summary: Basic non-functional UI for vending machine program.

package vendingMachine;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;

public class vendingMachine extends JFrame
{
	private static final long serialVersionUID = 1L;

		// Application launch
		public static void main(String[] args)
		{
			vendingMachine frame = new vendingMachine();
			frame.setVisible(true);
		}

		// Frame
		public vendingMachine() 
		{
			// Frame title
			setTitle(" Vending Machine UI ");
			
			NumberFormat formatter = new DecimalFormat("#0.00");
			
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			// Frame size
			setSize(700,700);
			
			// Panel title
			JPanel contentPane = new JPanel();
			contentPane.setBorder(new TitledBorder(new EtchedBorder(), "Display Area - Vending Machine"));;
			setContentPane(contentPane);
			
			
			// Item buttons
			
			// Item 1
			JButton b1 = new JButton("Item 1");
			b1.setIcon(new ImageIcon("item1.jpg"));
			b1.setBounds(10, 41, 150, 80);
			contentPane.add(b1);
			
			// Item 2
			JButton b2 = new JButton("Item 2");
			b2.setIcon(new ImageIcon("item2.jpg"));
			b2.setBounds(170, 41, 150, 80);
			contentPane.add(b2);
			
			// Item 3
			JButton b3 = new JButton("Item 3");
			b3.setIcon(new ImageIcon("item3.jpg"));
			b3.setBounds(330, 41, 150, 80);
			contentPane.add(b3);
			
			// Item 4
			JButton b4 = new JButton("Item 4");
			b4.setIcon(new ImageIcon("item4.jpg"));
			b4.setBounds(10, 141, 150, 80);
			contentPane.add(b4);
			
			// Item 5
			JButton b5 = new JButton("Item 5");
			b5.setIcon(new ImageIcon("item5.jpg"));
			b5.setBounds(170, 141, 150, 80);
			contentPane.add(b5);
			
			// Item 6
			JButton b6 = new JButton("Item 6");
			b6.setIcon(new ImageIcon("item6.jpg"));
			b6.setBounds(330, 141, 150, 80);
			contentPane.add(b6);
			
			// Item 7
			JButton b7 = new JButton("Item 7");
			b7.setIcon(new ImageIcon("item7.jpg"));
			b7.setBounds(10, 241, 150, 80);
			contentPane.add(b7);
			
			// Item 8
			JButton b8 = new JButton("Item 8");
			b8.setIcon(new ImageIcon("item8.jpg"));
			b8.setBounds(170, 241, 150, 80);
			contentPane.add(b8);
			
			// Item 9
			JButton b9 = new JButton("Item 9");
			b9.setIcon(new ImageIcon("item9.jpg"));
			b9.setBounds(330, 241, 150, 80);
			contentPane.add(b9);
			
			// Item 10
			JButton b10 = new JButton("Item 10");
			b10.setIcon(new ImageIcon("item10.jpg"));
			b10.setBounds(10, 341, 150, 80);
			contentPane.add(b10);
			
			// Item 11
			JButton b11 = new JButton("Item 11");
			b11.setIcon(new ImageIcon("item11.jpg"));
			b11.setBounds(170, 341, 150, 80);
			contentPane.add(b11);
			
			// Item 12
			JButton b12 = new JButton("Item 12");
			b12.setIcon(new ImageIcon("item12.jpg"));
			b12.setBounds(330, 341, 150, 80);
			contentPane.add(b12);
			
			
			// Exit button
			JButton btnNewButton_3 = new JButton("EXIT");
			btnNewButton_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
			btnNewButton_3.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					System.exit(0);	
				}
			});
			btnNewButton_3.setBounds(520, 600, 133, 34);
			contentPane.add(btnNewButton_3);
			
			// Layout
			contentPane.setLayout(null);
			
			// Set position of the window to center screen
			this.setLocationRelativeTo(null);
		}
}