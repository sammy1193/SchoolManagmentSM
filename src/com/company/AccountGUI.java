package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.*;


public class AccountGUI extends JFrame {
    Connection con;
    PreparedStatement pst;
    Statement st;
    //Variables Initialization
    private String FirstName, LastName, UsrName, PassWord, Birthdate; //String variables that store user input
    private String chosenUsrType = " "; //String variables that store user input
    private int newStudentID;
    private int newTeacherID;
    //For JComboBox
    private Boolean isValidType = false;
    private String[] UserType = {"----------------","Students", "Teachers"};
    private JComboBox usrT;
    public Boolean isCreateAccount = false;


    private static Container frame;
    private ImageIcon icon;
    private static JLabel label;
    private static JPanel panel;
    //Textfields
    private static JLabel account, instruct, firstName, lastName, usrName, password ,confirmPass, usrType, dateofBirth;
    //For error messages
    private static JLabel errorMessages, errorDate, errorUsrName, errorUsrType, errorPass, errorfirstName, errorLastName, errorUserExsists;
    //User Inputs
    private static JTextField inputUsr, inputFirst, inputLast, InputdateofBirth;
    //Password Field
    private static JPasswordField pass, confirmedPass;
    //Buttons
    private static JButton cancel, create;
    //Check for date format yyyy/mm/dd
    //Code from: https://www.geeksforgeeks.org/java-date-format-validation-using-regex/
    private static boolean isValidDate(String d)
    {
        String regex = "^[0-9]{4}/(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher((CharSequence)d);
        return matcher.matches();
    }
    //Verifying if the user input is solely string or not
    private static boolean isString(String input)
    {
        return input.matches("[a-zA-Z]+");
    }
    public void Display()
    {
        setTitle("Account Set Up");
        frame = getContentPane();
        label = new JLabel();
        panel = new JPanel();

        //Panel Interfaces
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setLayout(null);
        panel.setBounds(300, 45, 550, 590);
        account = new JLabel("<html><u>Creating an Account........</html>");
        account.setFont(new Font("Default", Font.BOLD, 20));
        account.setBounds( 170, 10, 400, 40);
        instruct = new JLabel("Please fill in the following information:");
        instruct.setFont(new Font("Default", Font.PLAIN, 14));
        instruct.setBounds(15, 50, 550, 20);

        firstName = new JLabel("First Name:");
        firstName.setBounds(15, 85, 150, 20);
        inputFirst = new JTextField(100);
        inputFirst.setBounds(90, 80, 170, 30);
        lastName = new JLabel("Last Name:");
        lastName.setBounds(270,85, 150, 20);
        inputLast = new JTextField(100);
        inputLast.setBounds(345, 80, 170, 30);

        usrName = new JLabel("Username (At least 6 Characters ex: abc123):");
        usrName.setBounds(15, 130, 290, 20);
        inputUsr = new JTextField(150);
        inputUsr.setBounds(300,125, 215, 30);

        password = new JLabel("Password (At least 6 Characters): ");
        password.setBounds(15, 180, 250, 20);
        pass = new JPasswordField(50);
        pass.setBounds(300,180, 215, 30);

        confirmPass = new JLabel( "Confirm Password: ");
        confirmPass.setBounds(15, 230, 250, 20);
        confirmedPass = new JPasswordField(50);
        confirmedPass.setBounds(300, 230, 215,30);

        dateofBirth = new JLabel("Enter Your Date of Birth in (YYYY/DD/MM) Format: ");
        dateofBirth.setBounds(15, 280, 340, 20);
        InputdateofBirth = new JTextField(50);
        InputdateofBirth.setBounds(335,275, 180, 30);

        //Lists of Error Messages
        errorMessages = new JLabel(" ");
        errorMessages.setFont(new Font("Default", Font.BOLD, 14));
        errorMessages.setBounds(15, 430, 600, 50);
        errorUserExsists = new JLabel(" ");
        errorUserExsists.setFont(new Font("Default", Font.BOLD, 14));
        errorUserExsists.setBounds(15, 450, 600, 50);
        errorDate = new JLabel(" ");
        errorDate.setFont(new Font("Default", Font.BOLD, 14));
        errorDate.setBounds(15, 450, 600, 50);
        errorUsrName = new JLabel(" ");
        errorUsrName.setFont(new Font("Default", Font.BOLD, 14));
        errorUsrName.setBounds(15, 470, 600, 50);
        errorUsrType= new JLabel(" ");
        errorUsrType.setFont(new Font("Default", Font.BOLD, 14));
        errorUsrType.setBounds(15, 490, 600, 50);
        errorPass = new JLabel(" ");
        errorPass.setFont(new Font("Default", Font.BOLD, 14));
        errorPass.setBounds(15, 510, 600, 50);
        errorfirstName = new JLabel( " ");
        errorfirstName.setFont(new Font("Default", Font.BOLD, 14));
        errorfirstName.setBounds(15, 530, 600, 50);
        errorLastName = new JLabel( " ");
        errorLastName.setFont(new Font("Default", Font.BOLD, 14));
        errorLastName.setBounds(15, 550, 600, 50);

        //Choosing User Type
        usrType = new JLabel("Choose User Type: ");
        usrType.setBounds(15, 330, 200, 20);
        usrT = new JComboBox(UserType);
        usrT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Checking user type for JComboBox
                if (e.getSource() == usrT) {
                    //System.out.println(usrT.getSelectedItem());
                    //Error: User has not chosen an option yet
                    if (usrT.getSelectedItem() == "----------------") {
                        isValidType = false;
                    }
                    //Valid Selection--Students
                    else if (usrT.getSelectedItem() == "Students") {
                        isValidType = true;
                        chosenUsrType = "Students";
                    }
                    //Valid Selection--Teachers
                    else if (usrT.getSelectedItem() == "Teachers") {
                        isValidType = true;
                        chosenUsrType = "Teachers";
                    }
                }
            }
        });
        usrT.setBounds(335, 330, 185, 20);
        panel.add(usrT);

        //Cancel Button-Return back to login Screen
        cancel = new JButton("Cancel");
        //If Cancel Is pressed then return to Login Screen
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //Return back to Login Screen
                //Calling Login Screen GUI
                Gui GI = new Gui();
                dispose();
            } });
        cancel.setBounds( 15, 370, 80, 50);

        //Create Button for creating an Account
        create = new JButton("CREATE");
        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //Creating Boolean for validating inputs
                Boolean isValidFirst = false;
                Boolean isValidLast = false;
                Boolean isValidUsrName = false;
                Boolean isValidBdate = false;
                Boolean isValidPass = false;
                //Create button is clicked
                if ( e.getSource() == create)
                {
                    //Checking for empty inputs
                    if(inputFirst.getText().equals("") || inputLast.getText().equals("") || inputUsr.getText().equals("")
                            || InputdateofBirth.getText().equals("") || pass.getText().equals("") || confirmedPass.getText().equals(""))
                    {

                        errorMessages.setText("<html><font color='Red'>ERROR!! One or more fields have been left blank!! </font></html>");
                    }
                    //Non-empty inputs
                    else
                    {
                        //Account creation is true=> for later window Listener usage
                        isCreateAccount = true;
                        //Retreiveing the JTextfield data and store it
                        String dateIn = InputdateofBirth.getText();
                        String subInputUsrName = " ";
                        //Clear Error Messages
                        errorMessages.setText(null);
                        errorMessages.setText(null);
                        errorDate.setText(null);
                        errorUsrName.setText(null);
                        errorUsrType.setText(null);
                        errorPass.setText(null);
                        errorUserExsists.setText(null);

                        //Verifying input can only be string for both first and last name
                        //valid first name
                        if (isString(inputFirst.getText()) )
                        {
                            FirstName = inputFirst.getText();
                            isValidFirst = true;
                            //Clear Error Messages
                            errorfirstName.setText(null);
                        }
                        //valid last name
                        if (isString(inputLast.getText()))
                        {
                            LastName = inputLast.getText();
                            isValidLast = true;
                            //Clear Error Messages
                            errorLastName.setText(null);
                        }
                        //invalid first name
                        if ( !isString(inputFirst.getText()))
                        {
                            FirstName = inputFirst.getText();
                            isValidFirst = false;
                            //Clearing the input text field to allow users to re-enter
                            inputFirst.setText(null);
                            errorfirstName.setText("<html><font color='Red'>ERROR!! First Name must be string of characters ONLY!! </font></html>");
                        }
                        //invalid last name
                        if ( !isString(inputLast.getText()))
                        {
                            LastName = inputLast.getText();
                            isValidLast = false;
                            errorLastName.setText("<html><font color='Red'>ERROR!! Last Name must be string of characters ONLY!! </font></html>");
                            //Clearing the input text field to allow users to re-enter
                            inputLast.setText(null);
                        }

                        //Checking For Valid User Inputs (exist or not in database)
                        //Has to be at least 6 characters
                        if ( inputUsr.getText().length() >=6 )
                        {
                            //UsrName = inputUsr.getText();
                            subInputUsrName = inputUsr.getText().substring(0, 3);
                            //Valid UsrName
                            //isValidUsrName = true;
                            //errorUsrName.setText(" ");

                            //First three character of the usrname has to be a character string
                            if (isString(subInputUsrName))
                            {
                                //Valid UsrName
                                isValidUsrName = true;
                                UsrName = inputUsr.getText();
                                errorUsrName.setText(" ");
                                //--------Check it exists in the database or not for the username----------
                                //***************Connection to the database**************
                                //Retrieving all the usrName in the database to verify user input username has not been taken yet
                            }
                            //Invalid usrName
                            else
                            {
                                isValidUsrName = false;
                                inputUsr.setText(null);
                                errorUsrName.setText("<html><font color='Red'>ERROR!! First three characters in the usrname has to be alphabets!!</font></html>");
                            }

                        }
                        //Incomplete username----less than 6 characters
                        else
                        {
                            //Invalid UsrName
                            isValidUsrName = false;
                            errorUsrName.setText("<html><font color='Red'>ERROR!!Username MUST be at least 6 Characters!</font></html>");
                        }
                        //Validating the length of the password has to be at least 6 characters
                        if ( pass.getText().length() >= 6 && confirmedPass.getText().length() >= 6)
                        {
                            //Checking if password matches or not
                            //Password and confirmed Password match
                            //Password and confirmed Password match
                            if (Arrays.equals(pass.getPassword(), confirmedPass.getPassword()))
                            {
                                PassWord = pass.getText();
                                isValidPass = true;
                                errorPass.setText(" ");
                            }
                            //Password and confirmed Password do not match
                            else
                            {
                                isValidPass = false;
                                //Error occur with the confirmed password and the password doesnt match
                                pass.setText(null);
                                confirmedPass.setText(null);
                                errorPass.setText("<html><font color='Red'>Passwords Do Not Match. Please Try Again! </font></html>");
                            }
                        }
                        //Error for password less than 6 characters (exclusive)
                        else
                        {
                            isValidPass = false;
                            //Error occur with the confirmed password and the password doesnt match
                            pass.setText(null);
                            confirmedPass.setText(null);
                            errorPass.setText("<html><font color='Red'>Passwords MUST be at least 6 characters. </font></html>");
                        }
                        //Valid User Type
                        //Valid User Type has chosen
                        if (isValidType)
                        {
                            errorUsrType.setText(" ");
                        }
                        //Invalid User Type: None of the options have been selected
                        else
                        {
                            errorUsrType.setText("<html><font color='Red'>Error! Please Select A User Type </font></html>");
                        }
                        //Verifying date
                        if (isValidDate(dateIn))
                        {
                            isValidBdate = true;
                            Birthdate = dateIn;
                            errorDate.setText(" ");
                        }
                        //Invalid date
                        else if (!isValidDate(dateIn))
                        {
                            isValidBdate = false;
                            InputdateofBirth.setText(null);
                            errorDate.setText("<html><font color='Red'>Incompatible Date Format. Please Try Again with yyyy/MM/dd </font></html>");
                        }

                    }
                    //Verfying every user input is correct, if so, connect to the database and store data and return to the login screen
                    if( isCreateAccount &&  isValidBdate && isValidPass
                            && isValidUsrName &&  isValidLast && isValidFirst && isValidType)
                    {
                        //-----------Connection with the Database------------
                        //Adding data to the database
                        try {
                            //establishing connection with the database server
                            Class.forName("com.mysql.cj.jdbc.Driver");
                            con = DriverManager.getConnection("jdbc:mysql://schoolms.cf6gf0mrmfjb.ca-central-1.rds.amazonaws.com:3306/SMSSytem", "admin", "rootusers");
                            //inserting into the users table (creating an account)

                            //preparing SQL insertion statement
                            pst = con.prepareStatement("insert into Users(username, password, usertype)values(?,?,?)");
                            pst.setString(1, UsrName );
                            pst.setString(2, PassWord);
                            pst.setString(3, chosenUsrType);
                            //Execute the update on the database
                            pst.executeUpdate();
                            //catching class not found  error
                        }catch (ClassNotFoundException classNotFoundException ) {
                            classNotFoundException.printStackTrace();
                            //catching insertion of exisiting username error
                        }catch (SQLIntegrityConstraintViolationException e_SQLIntegrityConstraintViolationException){
                            //failure to add acount
                            isCreateAccount = false;
                            //displaying error
                            errorUserExsists.setText("<html><font color='Red'>Username Already Exists! Please Try again with a Different Username </font></html>");
                            //resetting password fields
                            pass.setText(null);
                            confirmedPass.setText(null);
                            //catching other SQL errors
                        }catch (SQLException other_SQLException){
                            other_SQLException.printStackTrace();
                        }
                        if(isCreateAccount == true){
                            //preparing insertion into the Students table

                            if (chosenUsrType == "Students"){
                                //preparing full name string
                                String fullName = FirstName + " " + LastName;
                                try {
                                    st = con.createStatement();
                                    //fetching the newest student ID added to database
                                    String querySQL = "SELECT max(Student_ID) as maxStudentID FROM SMSSytem.Student;";
                                    ResultSet rs = st.executeQuery(querySQL);
                                    String st_ID = "";
                                    //Getting data out from the query
                                    while (rs.next()) {
                                        st_ID = rs.getString("maxStudentID");
                                        System.out.print("maxStudentID: " + st_ID);
                                    }
                                    //parsing the ID to INT and incrementing it to become the new student ID
                                    newStudentID = Integer.parseInt(st_ID);
                                    newStudentID = newStudentID + 1;
                                    //catching SQL errors
                                }catch (SQLException other_SQLException) {
                                    other_SQLException.printStackTrace();
                                }

                                try{
                                    //actual insertion into the Students table
                                    pst = con.prepareStatement("insert into Student(Student_ID, Name, DOB, userName)values(?,?,?,?)");
                                    String newStudentID_S = Integer.toString(newStudentID);
                                    pst.setString(1, newStudentID_S);
                                    pst.setString(2, fullName);
                                    pst.setString(3, Birthdate);
                                    pst.setString(4, UsrName);
                                    //Execute the update on the database
                                    pst.executeUpdate();

                                }catch (SQLException other_SQLException){
                                    other_SQLException.printStackTrace();
                                }
                            }
                            //preparing insertion into the Teachers table

                            else if (chosenUsrType == "Teachers"){
                                //preparing full name string
                                String fullName = FirstName + " " + LastName;
                                try {
                                    st = con.createStatement();
                                    //fetching the newest Teacher ID added to database
                                    String querySQL = "SELECT max(Teacher_ID) as maxTeacherID FROM SMSSytem.Teacher;";
                                    ResultSet rs = st.executeQuery(querySQL);
                                    String te_ID = "";
                                    System.out.println("Records in current Database:");
                                    //Getting data out from the query
                                    while (rs.next()) {
                                        te_ID = rs.getString("maxTeacherID");
                                        System.out.print("maxTeacherID: " + te_ID);
                                    }
                                    //parsing the ID to INT and incrementing it to become the new teacher ID
                                    newTeacherID = Integer.parseInt(te_ID);
                                    newTeacherID = newTeacherID + 1;
                                }catch (SQLException other_SQLException) {
                                    other_SQLException.printStackTrace();
                                }

                                try{
                                    //actual insertion into the Teacher table
                                    pst = con.prepareStatement("insert into Teacher(Teacher_ID, Name, DOB, userName)values(?,?,?,?)");
                                    String newTeacherID_S = Integer.toString(newTeacherID);
                                    pst.setString(1, newTeacherID_S);
                                    pst.setString(2, fullName);
                                    pst.setString(3, Birthdate);
                                    pst.setString(4, UsrName);
                                    //Execute the update on the database
                                    pst.executeUpdate();

                                }catch (SQLException other_SQLException){
                                    other_SQLException.printStackTrace();
                                }
                            }
                        }

                        /////////////////////////////////////////////////
                        if (isCreateAccount == true) {
                            Gui GI = new Gui();
                            //Closing the windows
                            dispose();
                        }
                    }
                }
            }
        });
        create.setBounds(430, 370, 80, 50);
        //Window lister adds functionality of showing messages upon user closing action
        this.addWindowListener(new WL());

        panel.add(errorDate);
        panel.add(firstName);
        panel.add(inputFirst);
        panel.add(lastName);
        panel.add(inputLast);
        panel.add(inputUsr);
        //Adding onto the panel
        panel.add(pass);
        panel.add(confirmedPass);
        panel.add(usrType);
        panel.add(dateofBirth);
        panel.add(InputdateofBirth);
        panel.add(confirmPass);
        panel.add(password);
        panel.add(usrName);
        panel.add(create);
        panel.add(account);
        panel.add(instruct);
        panel.add(errorMessages);
        panel.add(errorUsrName);
        panel.add(errorUsrType);
        panel.add(errorUserExsists);
        panel.add(cancel);
        panel.add(errorPass);
        panel.add(errorfirstName);
        panel.add(errorLastName);
        add(panel);

        //Image handling/Resizing
        icon = new ImageIcon(this.getClass().getResource("library.jpg"));
        Image im = icon.getImage().getScaledInstance(1100,700, Image.SCALE_SMOOTH);
        icon = new ImageIcon(im);
        label.setIcon(icon);
        label.setBounds(0,0, 1100, 700);
        label.setBorder(null);
        add(label);

        setSize(1100,700);
        setLocationRelativeTo(null);
        setVisible(true);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
                AccountGUI AGui = new AccountGUI();
                AGui.Display();
            }
        }
        @Override
        public void windowClosed(WindowEvent e) {
            //Account Creation (create button is pressed) is successful and close the window with success message prompt
            if ( isCreateAccount == true)
                JOptionPane.showMessageDialog(frame , "User Account Created Successfully!");
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
