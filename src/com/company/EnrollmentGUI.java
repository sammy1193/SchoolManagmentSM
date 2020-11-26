package com.company;

import javax.naming.Name;
import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import java.sql.*;
import javax.swing.*;
import java.util.*;


public class EnrollmentGUI extends JFrame{
    //Variable Initializations
    Connection con;
    PreparedStatement pst;
    Statement st;

    Container frame;
    private static JLabel title;
    private static JPanel panel, backgroundPanel;
    private static JButton enroll, cancel;
    private static JTextField courseSearch;
    private static JList resultList;
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

    //Constructor
    EnrollmentGUI(String userName)
    {
        userN = userName;
        frame = getContentPane();
        setTitle("Course Enrollment");

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(25, 15, 450, 450);
        panel.setBackground(Color.white);
        backgroundPanel = new JPanel();
        backgroundPanel.setLayout(null);
        backgroundPanel.setBounds(0, 0, 500, 500);
        backgroundPanel.setBackground(Color.LIGHT_GRAY);

        //Title for enrollment
        title = new JLabel("<html><u>COURSE ENROLLMENT</html>");
        title.setBounds(135, 5, 200, 20);
        title.setFont(new Font("Default", Font.BOLD, 16));
        panel.add(title);

        //------------------Data Connection Needed-----------------
        //List all the courses offered that is stored in the database


        //Enroll Buttons
        enroll = new JButton("ENROLL");
        enroll.setBounds(150, 400, 300, 40);

        courseSearch = new JTextField("");
        courseSearch.setBounds(100,40,250,35);

        Vector<String> resultData = new Vector<String>();
        resultList = new JList<String>(resultData);
        resultList.setBounds(100, 80, 250, 95);
        resultList.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        resultList.setVisible(false);

        courseSearch.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                if(courseSearch.getText().length()>0) {
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        con = DriverManager.getConnection("jdbc:mysql://schoolms.cf6gf0mrmfjb.ca-central-1.rds.amazonaws.com:3306/SMSSytem", "admin", "rootusers");
                        st = con.createStatement();
                        PreparedStatement statement = con.prepareStatement("SELECT Name FROM SMSSytem.Student where Name like ?");
                        String enteredName = courseSearch.getText();
                        statement.setString(1, "%" + enteredName.trim() + "%");
                        ResultSet rs = statement.executeQuery();
                        System.out.print(enteredName + " ");
                        resultData.clear();
                        resultList.updateUI();
                        while (rs.next()) {
                            String foundName = rs.getString("Name");
                            resultList.setVisible(true);
                            resultData.add(foundName);
                            resultList.updateUI();
                        }


                    }catch (ClassNotFoundException classNotFoundException ) {
                        classNotFoundException.printStackTrace();
                    }catch (SQLException other_SQLException) {
                        other_SQLException.printStackTrace();
                    }
                }
                else if(courseSearch.getText().length()==0){
                    resultData.clear();
                    resultList.setVisible(false);
                }
            }
        });

        enroll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        panel.add(enroll);
        //Cancel
        cancel = new JButton("Cancel");
        cancel.setBounds(0, 400, 150, 40);
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ( e.getSource() == cancel )
                {
                    StudentInterface sGUI = new StudentInterface(userN);
                    dispose();
                }

            }
        });
        panel.add(cancel);
        panel.add(courseSearch);
        panel.add(resultList);

        backgroundPanel.add(panel);
        frame.add(backgroundPanel);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        this.addWindowListener(new WL());
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
                JOptionPane.showMessageDialog(null , "Return to Student Interface");

            }
            else
            {
                //Display the Enrollment Interface
                EnrollmentGUI EGUI = new EnrollmentGUI(userN);
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
