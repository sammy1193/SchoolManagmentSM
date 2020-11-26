package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class AdminGUI extends JFrame {
    //Variable Initializations
    private static Container frame;
    private ImageIcon icon;
    private static JLabel label;
    private static JPanel panel;

    private String userN = " ";

    //Setter and getter for userName
    //Setter for UserName
    public void setusrN ( String userName )
    {
        this.userN = userName;
    }
    //Getter for username
    public String getusrN()
    {
        return userN;
    }
    //For displaying GUI
    AdminGUI(String usersName)
    {
        // We need search bad First name, username to display the records correspondingly
        //Modify anything but Personal ID and username
        //Just one panel to display one thing
        userN = usersName;

        setTitle("Administrator Interface");
        frame = getContentPane();
        label = new JLabel();
        panel = new JPanel();

        //Panel Interfaces
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setLayout(null);


        //Image handling/Resizing
        icon = new ImageIcon(this.getClass().getResource("Admin.jpg"));
        Image im = icon.getImage().getScaledInstance(1100,700, Image.SCALE_SMOOTH);
        icon = new ImageIcon(im);
        label.setIcon(icon);
        label.setBounds(0,0, 1100, 700);
        label.setBorder(null);
        add(label);
        this.addWindowListener(new WL());
        setSize(1100,700);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    //For Showing Messages after the Window has been disposed
    class WL implements WindowListener
    {
        @Override
        public void windowOpened(WindowEvent e) { }
        @Override
        public void windowClosing(WindowEvent e) {
            int output = JOptionPane.showConfirmDialog(frame , "Are you sure you want to exit?", null , JOptionPane.YES_OPTION);
            //For sure for closing and exiting the program
            if(output == JOptionPane.YES_OPTION)
            {
                JOptionPane.showMessageDialog(null , "Exiting.....");
                //Exit the program
                System.exit(1);
            }
            //No option: remain in the same interface with everything cleared out!!!
            else{
                AdminGUI AdGUI = new AdminGUI(userN);
            }
        }
        @Override
        public void windowClosed(WindowEvent e) {

        }
        @Override
        public void windowIconified(WindowEvent e) { }
        @Override
        public void windowDeiconified(WindowEvent e) { }
        @Override
        public void windowActivated(WindowEvent e) { }
        @Override
        public void windowDeactivated(WindowEvent e) { }
    }
}
