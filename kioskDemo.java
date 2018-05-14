//Joe Ferraro
//9 February 2018
//kioskDemo

//Updated: I add a clear all button and make this gui connect to socketserver directly
//Shuyang Tang


import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.NumberFormat;


public class kioskDemo extends JFrame {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    double total = 0.0;
    double change = 0.0;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        kioskDemo frame = new kioskDemo();
        frame.setVisible(true);


    }


    /**
     * Create the frame.
     */
    public kioskDemo() {
        int result, portNumber = 0;
        String serverResponse, ipAddress, portString;
        try {
            File file = new File("config");
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader("config"));
                ipAddress = br.readLine();
                portString = br.readLine();
                portNumber = Integer.parseInt(portString);
                br.close();
            } else {
                ipAddress = "localhost";
                portNumber = 3333;
            }

            Socket clientSocket = new Socket(ipAddress, portNumber);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            // Frame title
            setTitle("--- Vending Machine ---");

            NumberFormat formatter = new DecimalFormat("#0.00");

            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // size of the frame
            setSize(975, 800);

            // panel title
            JPanel contentPane = new JPanel();
            contentPane.setBorder(new TitledBorder(new EtchedBorder(),
                    "Have a Snack! "));
            ;
            setContentPane(contentPane);


            // list of items that are being bought
            JTextArea textArea = new JTextArea();
            textArea.setBounds(763, 11, 168, 382);
            textArea.setEditable(false);

            contentPane.add(textArea);

            // field where total accumulating price is being displayed
            JTextField textField = new JTextField();
            textField.setEditable(false);
            textField.setBounds(807, 420, 124, 34);
            contentPane.add(textField);
            textField.setColumns(10);

            //field where user enters amount of money they are paying with
            JTextField cashEntered = new JTextField();
            cashEntered.setEditable(true);
            cashEntered.setBounds(807, 500, 124, 34);
            contentPane.add(cashEntered);

            //field where change on transaction is displayed
            JTextField changeText = new JTextField();
            changeText.setEditable(false);
            changeText.setBounds(807, 580, 124, 34);
            contentPane.add(changeText);

            // Item buttons

            // Item 1
            JButton b1 = new JButton("Chips");
            b1.setIcon(new ImageIcon("images/lays.jpg"));
            b1.setBounds(10, 41, 180, 80);
            contentPane.add(b1);

            b1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    textArea.append("Chips             1.00\r\n");
                    total = total + 1.00;

                    textField.setText(formatter.format(total));
                    textField.repaint();
                }
            });

            // Item 2
            JButton b2 = new JButton("Soda");
            b2.setIcon(new ImageIcon("images/cocacola.jpg"));
            b2.setBounds(170, 41, 180, 80);
            contentPane.add(b2);

            b2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    textArea.append("Soda               1.50\r\n");
                    total = total + 1.50;

                    textField.setText(formatter.format(total));
                    textField.repaint();
                }
            });

            // Item 3
            JButton b3 = new JButton("Candy Bar");
            b3.setIcon(new ImageIcon("images/snickers.png"));
            b3.setBounds(330, 41, 180, 80);
            contentPane.add(b3);

            b3.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    textArea.append("Candy Bar       2.00\r\n");
                    total = total + 2.00;

                    textField.setText(formatter.format(total));
                    textField.repaint();
                }
            });

            // Item 4
            JButton b4 = new JButton("Water");
            b4.setIcon(new ImageIcon("images/waterbottle.jpg"));
            b4.setBounds(10, 141, 180, 80);
            contentPane.add(b4);

            b4.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    textArea.append("Water             1.00\r\n");
                    total = total + 1.00;

                    textField.setText(formatter.format(total));
                    textField.repaint();
                }
            });

            // Item 5
            JButton b5 = new JButton("Cookie");
            b5.setIcon(new ImageIcon("images/cookie.jpg"));
            b5.setBounds(170, 141, 180, 80);
            contentPane.add(b5);

            b5.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    textArea.append("Cookie           1.50\r\n");
                    total = total + 1.50;

                    textField.setText(formatter.format(total));
                    textField.repaint();
                }
            });

            // Item 6
            JButton b6 = new JButton("Fruit");
            b6.setIcon(new ImageIcon("images/banana-bunch.jpg"));
            b6.setBounds(330, 141, 180, 80);
            contentPane.add(b6);

            b6.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    textArea.append("Banana             .50\r\n");
                    total = total + .50;

                    textField.setText(formatter.format(total));
                    textField.repaint();
                }
            });

            // Item 7
            JButton b7 = new JButton("Pretzels");
            b7.setIcon(new ImageIcon("images/pretzels.jpg"));
            b7.setBounds(10, 241, 180, 80);
            contentPane.add(b7);

            b7.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    textArea.append("Pretzels          1.00\r\n");
                    total = total + 1.00;

                    textField.setText(formatter.format(total));
                    textField.repaint();
                }
            });


            // Item 8
            JButton b8 = new JButton("M&Ms");
            b8.setIcon(new ImageIcon("images/m&m.jpg"));
            b8.setBounds(170, 241, 180, 80);
            contentPane.add(b8);

            b8.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    textArea.append("M&Ms             1.25\r\n");
                    total = total + 1.25;

                    textField.setText(formatter.format(total));
                    textField.repaint();
                }
            });

            // Item 9
            JButton b9 = new JButton("Twinkie");
            b9.setIcon(new ImageIcon("images/twinkie.jpg"));
            b9.setBounds(330, 241, 180, 80);
            contentPane.add(b9);

            b9.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    textArea.append("Twinkie           1.00\r\n");
                    total = total + 1.00;

                    textField.setText(formatter.format(total));
                    textField.repaint();
                }
            });

            // Item 10
            JButton b10 = new JButton("Gummy Bears");
            b10.setIcon(new ImageIcon("images/gummybears.jpg"));
            b10.setBounds(10, 341, 180, 80);
            contentPane.add(b10);

            b10.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    textArea.append("Gummy Bears 1.50\r\n");
                    total = total + 1.50;

                    textField.setText(formatter.format(total));
                    textField.repaint();
                }
            });

            // Item 11
            JButton b11 = new JButton("Crackers");
            b11.setIcon(new ImageIcon("images/crackers.jpg"));
            b11.setBounds(170, 341, 180, 80);
            contentPane.add(b11);

            b11.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    textArea.append("Crackers         1.00\r\n");
                    total = total + 1.00;

                    textField.setText(formatter.format(total));
                    textField.repaint();
                }
            });

            // Item 12
            JButton b12 = new JButton("Gum");
            b12.setIcon(new ImageIcon("images/gum.jpg"));
            b12.setBounds(330, 341, 180, 80);
            contentPane.add(b12);

            b12.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    textArea.append("Gum                 .50\r\n");
                    total = total + .50;

                    textField.setText(formatter.format(total));
                    textField.repaint();
                }
            });


            JLabel lblNewLabel = new JLabel("    Total Price   $");
            lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lblNewLabel.setBounds(697, 424, 100, 23);
            contentPane.add(lblNewLabel);

            //cash tendered label
            JLabel paymentLabel = new JLabel("    Cash Tendered   $");
            paymentLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
            paymentLabel.setBounds(672, 504, 140, 23);
            contentPane.add(paymentLabel);

            JButton removeAll = new JButton("Remove All Item");
            removeAll.setFont(new Font("Tahoma", Font.PLAIN, 14));
            removeAll.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    total = 0.0;
                    textArea.setText("");
                    textField.setText("");
                }
            });
            removeAll.setBounds(600, 600, 133, 34);
            contentPane.add(removeAll);

            // EXIT Button
            JButton btnNewButton_3 = new JButton("EXIT");
            btnNewButton_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
            btnNewButton_3.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        outToServer.writeBytes("quit" + '\n');
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }

                    if (clientSocket != null)
                        try {
                            clientSocket.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    System.exit(0);

                }
            });
            btnNewButton_3.setBounds(400, 600, 133, 34);
            contentPane.add(btnNewButton_3);

            //transaction complete button
            JButton payButton = new JButton("Transaction Complete");
            payButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
            payButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    double a = Integer.parseInt(cashEntered.getText());
                    double b = total;
                    if (a < b) {
                        JOptionPane.showMessageDialog(null, "Not Enough Money has been entered!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        change = a - b;
                        changeText.setText(formatter.format(change));
                        try {
                            outToServer.writeBytes("Sold item:" + textArea.getText() + '\n');
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        try {
                            outToServer.writeBytes(textField.getText() + '\n');
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            });
            payButton.setBounds(725, 540, 200, 34);
            contentPane.add(payButton);


            // user will do the layout
            contentPane.setLayout(null);

            // position frame in the middle of the screen
            this.setLocationRelativeTo(null);
        } catch (ConnectException e) {
            JOptionPane.showMessageDialog(null,
                    "ERROR : Can not connect to Vending Machine Socket Server Host!\r\n" +
                            "HELP  : Server may be down or try this : \r\n\r\n" +
                            "HELP  : Create a file called config.txt and add two lines, <IP address> and <port number>.\r\n" +
                            "\r\n" +
                            "   192.168.2.15\r\n" +
                            "   3333\r\n",
                    "Vending Machine Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (UnknownHostException e) {
            JOptionPane.showMessageDialog(null,
                    "ERROR : Unknown Host Exception!",
                    "Vending machine Application Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "ERROR : IO Exception!\r\n" +
                            "Communication with Vending machine Socket Server lost!\r\n",
                    "Vending machine Application Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
