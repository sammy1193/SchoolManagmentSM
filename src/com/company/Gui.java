package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.*;

public class Gui extends JFrame{
    Connection con;
    PreparedStatement pst;
    Statement st;
    private static JLabel l_title,l_user,l_pass,l_image;
    private static JTextField tf_user;
    private static JButton b_login,b_register;
    private ImageIcon icon;
    private static JPasswordField pass;
    private static JLabel err_user,err_pass;
    Container f;
    private String usernameS ,password;

    Gui(){
        //Use Java Swing to create an input GUI
        //Create instance of JFrame
        //JFrame f=new JFrame("Login Screen");
        setTitle("Login Screen");
        f = getContentPane();
        //f.setLayout(new FlowLayout());

        //Create instance of JPanel
        JPanel p=new JPanel();
        p.setBounds(340,440,400,220);
        p.setBackground(Color.lightGray);
        f.add(p);

        l_image=new JLabel();
        icon = new ImageIcon(this.getClass().getResource("school.jpg"));
        Image im = icon.getImage().getScaledInstance(1100,700,Image.SCALE_SMOOTH);
        icon = new ImageIcon(im);
        l_image.setIcon(icon);
        l_image.setBounds(0,0,1100,700);
        f.add(l_image);

        //Create instance of JLabel for input value
        l_title = new JLabel("<html><u>School Management System</html>");
        l_title.setFont(new Font("Default", Font.BOLD, 17));
        l_title.setBounds( 85, 1, 400, 30);
        l_user=new JLabel("Username: ");
        l_user.setBounds(50,30, 150,30);
        l_pass=new JLabel("Password: ");
        l_pass.setBounds(50,80, 150,30);
        p.add(l_title);
        p.add(l_user);
        p.add(l_pass);

        //Create instance of JTextField for input values
        tf_user=new JTextField();
        tf_user.setBounds(160,35,150,30);
        pass=new JPasswordField();
        pass.setBounds(160,85,150,30);
        p.add(tf_user);
        p.add(pass);

        //Create instance of JLabel for error message
        err_user = new JLabel("Username Does Not Exist!");
        err_user.setBounds(100,170,150,30);
        err_user.setForeground(Color.RED);
        err_user.setVisible(false);
        err_pass=new JLabel("Password Doesn't match given Username!");
        err_pass.setBounds(100,190,150,30);
        err_pass.setForeground(Color.RED);
        err_pass.setVisible(false);
        p.add(err_user);
        p.add(err_pass);

        //Create instance of JButton
        b_login=new JButton("Login");
        b_login.setBounds(60,135,90, 30);
        b_login.setBackground(new Color (25,100,205));
        b_login.setForeground(Color.white);
        b_register=new JButton("Register");
        b_register.setBounds(210,135,90, 30);
        b_register.setBackground(new Color (25,100,205));
        b_register.setForeground(Color.white);
        p.add(b_login);
        p.add(b_register);

        b_login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //********* Login Credential Here **************//
                usernameS = tf_user.getText();
                password = pass.getText();

                //Connection to Database Here
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://schoolms.cf6gf0mrmfjb.ca-central-1.rds.amazonaws.com:3306/SMSSytem", "admin", "rootusers");
                    st = con.createStatement();
                    //fetching username
                    PreparedStatement statement = con.prepareStatement("SELECT password, userType FROM SMSSytem.Users where username = ?;");
                    statement.setString(1, usernameS);
                    ResultSet rs = statement.executeQuery();
                    String pass = "";
                    String userTypeS = "";
                    //Getting data out from the query
                    while (rs.next()) {
                        pass = rs.getString("password");
                        userTypeS = rs.getString("userType");
                        System.out.print("usrName: " + usernameS);
                        System.out.println(", password: " + pass);
                        System.out.println(", entered password: " + password);
                    }
                        if (pass.isEmpty()){
                            err_user.setVisible(true);
                            System.out.print("null: "+ usernameS);
                        }

                        else if(!(pass.equals(password))){
                            err_pass.setVisible(true);
                            System.out.print("WP: "+ usernameS);
                        }

                        else{
                            System.out.print("IN: "+ usernameS);
                            if(userTypeS.equals("Students")){
                                StudentInterface SI = new StudentInterface(usernameS);
                                dispose();
                            }
                            else if (userTypeS.equals("Teachers")){
                                TeacherGUI TI = new TeacherGUI(usernameS);
                                dispose();
                            }
                            else if (userTypeS.equals("Admin")){
                                AdminGUI AI = new AdminGUI(usernameS);
                                dispose();
                            }

                        }


                }catch (ClassNotFoundException classNotFoundException ) {
                    classNotFoundException.printStackTrace();
                }catch (SQLException other_SQLException) {
                    other_SQLException.printStackTrace();
                }



                //////////////////////////////




            }
        });

        b_register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AccountGUI AI = new AccountGUI();
                AI.Display();
                dispose();
            }
        });

        //1100 width and 700 height
        //f.setSize(1100,700);
        setSize(1100,700);

        //Use no layout managers
        //f.setLayout(null);
        p.setLayout(null);

        //Center frame on screen
        //f.setLocationRelativeTo(null);
        setLocationRelativeTo(null);

        //Let JFrame to close properly
        //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Make the frame visible
        //f.setVisible(true);
        setVisible(true);
    }
}


