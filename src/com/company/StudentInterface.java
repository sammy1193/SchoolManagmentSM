package com.company;

import javax.swing.*;
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

public class StudentInterface extends JFrame {
    Connection con;
    PreparedStatement pst;
    Statement st;
    //Dynamic Array for Storing All the Possible Courses for each Student
    private Vector <String> courseEnrolled = new Vector<String>();
    //For selecting the course
    private static JComboBox coursesOptions;
    private static JComboBox courseOptionsAttendance;

    //For Account Panel
    private static JLabel l_image,l_atitle,l_id,l_name,l_username,l_dob,l_usertype,l_newpass,l_confirmpass,errorPass;
    private static JPasswordField newpass,confirmpass;
    private ImageIcon icon, refreshIcon;
    private static JButton b_logoff,b_updatepass,b_confirm,b_cancel;
    private String PassWord;

    //For Grade interface
    private static JLabel gradeTitle, CourseGrade, emptyRecord, course, grade, overallGPA, refreshGrade;
    private static JButton overallG, refreshG, enrol;
    //Verifying a course has been selected for Grade panel
    private Boolean isCourseGradeSelected = false;
    private Boolean isSelectedAllGrade = false;
    private Boolean isSelectedAllAtten = false;
    private String GrCourse = " ";
    //For Attendance Panel
    private static JLabel attendanceTitle, CourseAttendance, emptyAttendanceRecord, refreshAttendance;
    private static JButton refreshA;
    //Verifying a course has been selected for Attendance panel
    private Boolean isCourseAttendanceSelected = false;
    private String AttCourse = " ";
    //For storing usrname
    private String usrN = " ";

    //Setter and getter for userName
    //Setter for UserName
    public void setusrN ( String userName )
    {
        this.usrN = userName;
    }
    //Getter for username
    public String getusrN()
    {
        return usrN;
    }
    Container f;
    private String PID;
    private String SID;
    private String Name;
    private String DOB;

    private String Courses;
    private String Grades;
    private String Attendances;

    private String Course;
    private String Grade;
    String cGPA_s = "";
    //**************Later need to put username and password in parameter in constructor to extract data***************
    //StudentInterface(String usrname, String passWord)
    StudentInterface(String usrName){
        usrN = usrName;
        courseEnrolled.add("-------------");

        //-----------------------------Database Connections Required----------------------
        /*Getting corresponding personal ID, Name, username, Date of Birth and Usertype and
        add it to Display. Current variable, usrN, is holding the username that is being passed in
        and it can be used to retrieve in the database. Display the corresponding data to corresponding
        field as shown from line 91 and onward
        */

        //--------------Database Connections Required to store Personal information------------------


        //According to what courses this specific student (username) has taken, add the course lists to the courseEnrolled
        //Retreive data (What course this student is taking)

        //Connection here with database to display information for Account, Grade, and Attendance
        //For combo box, a list of courses taken by the students
        //Retrieve a list of courses from Database and do "courseEnrolled.add(courses);"
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://schoolms.cf6gf0mrmfjb.ca-central-1.rds.amazonaws.com:3306/SMSSytem", "admin", "rootusers");
            st = con.createStatement();
            //fetching username
            PreparedStatement statement = con.prepareStatement("SELECT Student_ID, Name, DOB FROM SMSSytem.Student where username = ?;");
            statement.setString(1, usrN);
            ResultSet rs = statement.executeQuery();
            //Getting data out from the query
            while (rs.next()) {
                SID = rs.getString("Student_ID");
                PID = usrN + SID;
                Name = rs.getString("Name");
                DOB = rs.getString("DOB");
                System.out.print("usrName: " + usrN);
            }
        }catch (ClassNotFoundException classNotFoundException ) {
            classNotFoundException.printStackTrace();
        }catch (SQLException other_SQLException) {
            other_SQLException.printStackTrace();
        }

        try {
            //Class.forName("com.mysql.cj.jdbc.Driver");
            //con = DriverManager.getConnection("jdbc:mysql://schoolms.cf6gf0mrmfjb.ca-central-1.rds.amazonaws.com:3306/SMSSytem", "admin", "rootusers");
            //st = con.createStatement();
            //fetching username
            PreparedStatement statement = con.prepareStatement("SELECT C.Course_Name, C.Teacher_ID, T.Grade, T.Attendance FROM SMSSytem.Course C, SMSSytem.Takes T where SMSSytem.T.Student_ID = ? and SMSSytem.C.Course_ID = SMSSytem.T.Course_ID");
            statement.setString(1, SID);
            ResultSet rs = statement.executeQuery();
            //Getting data out from the query
            while (rs.next()) {
                Courses = rs.getString("Course_Name");
                Grades = rs.getString("Grade");
                Attendances = rs.getString("Attendance");

                System.out.print(Courses + " ");
                courseEnrolled.add(Courses);
            }
        }catch (SQLException other_SQLException) {
            other_SQLException.printStackTrace();
        }



        courseEnrolled.add("-------------");
        courseEnrolled.add("   SHOW ALL  ");

        setTitle("Student Interface");
        f = getContentPane();

        //Create instance of JPanel
        JPanel p=new JPanel();
        //For Grade Panel
        JPanel p_grade = new JPanel();
        //For Attendance Panel
        JPanel p_attend = new JPanel();

        p.setBounds(70,10,950,640);
        p.setBackground(Color.lightGray);
        f.add(p);

        //For account panel
        JPanel pa=new JPanel();
        pa.setBounds(165,10,620,250);
        pa.setBackground(Color.white);
        p.add(pa);


        //Create instance of JLabel for input value
        l_atitle=new JLabel("<html><u>Welcome to Student Interface!</html>");
        l_atitle.setFont(new Font("Default", Font.BOLD, 20));
        l_atitle.setBounds(165,4, 350,30);
        l_id=new JLabel("Personal ID: "); //{personalID
        l_id.setBounds(50,35, 150,30);
        l_name=new JLabel("Name: "); //  "Name :
        l_name.setBounds(50,75, 150,30);
        l_username=new JLabel("Username: "); //  "Username:
        l_username.setBounds(50,115,150,30);
        l_dob=new JLabel("Date of Birth: "); // {Date of Birth retrieved from database}
        l_dob.setBounds(50,155,150,30);
        l_usertype=new JLabel("User Type: "); // "User Type:
        l_usertype.setBounds(50,195,150,30);
        l_newpass = new JLabel("");
        l_newpass.setBounds(350,85,150, 30);
        l_confirmpass = new JLabel("");
        l_confirmpass.setBounds(330,125,150, 30);
        errorPass = new JLabel("");
        errorPass.setFont(new Font("Default", Font.BOLD, 14));
        errorPass.setBounds(300,195,315, 30);
        pa.add(l_atitle);
        pa.add(l_id);
        pa.add(l_name);
        pa.add(l_username);
        pa.add(l_dob);
        pa.add(l_usertype);
        pa.add(l_newpass);
        pa.add(l_confirmpass);
        pa.add(errorPass);

        l_name.setText(l_name.getText()+" "+Name);
        l_username.setText(l_username.getText()+" "+usrN);
        l_dob.setText(l_dob.getText()+" "+DOB);
        l_id.setText(l_id.getText()+" "+PID);
        l_usertype.setText(l_usertype.getText()+" Student");

        newpass = new JPasswordField();
        newpass.setBounds(460,90,150, 30);
        confirmpass = new JPasswordField();
        confirmpass.setBounds(460,130,150, 30);
        newpass.setEditable(false);
        confirmpass.setEditable(false);
        newpass.setBorder(new LineBorder(Color.white));
        confirmpass.setBorder(new LineBorder(Color.white));
        newpass.setBackground(Color.white);
        confirmpass.setBackground(Color.white);
        pa.add(newpass);
        pa.add(confirmpass);
        this.addWindowListener(new WL());

        b_logoff=new JButton("Log Off");
        b_logoff.setBounds(480,4,80, 30);
        b_logoff.setBackground(new Color (250,5,5));
        b_logoff.setForeground(Color.white);
        b_updatepass=new JButton("Update Password");
        b_updatepass.setBounds(400,45,140, 30);
        b_updatepass.setBackground(new Color (25,100,205));
        b_updatepass.setForeground(Color.white);
        b_confirm = new JButton("Confirm");
        b_confirm.setBounds(435,165,80, 30);
        b_confirm.setBackground(new Color (25,100,205));
        b_confirm.setForeground(Color.white);
        b_confirm.setEnabled(false);
        b_confirm.setVisible(false);
        b_cancel = new JButton("Cancel");
        b_cancel.setBounds(535,165,80, 30);
        b_cancel.setBackground(new Color (25,100,205));
        b_cancel.setForeground(Color.white);
        b_cancel.setEnabled(false);
        b_cancel.setVisible(false);
        pa.add(b_logoff);
        pa.add(b_updatepass);
        pa.add(b_confirm);
        pa.add(b_cancel);

        b_logoff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Gui GI = new Gui();
                dispose();
            }
        });

        b_updatepass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                l_newpass.setText("New Password: ");
                l_confirmpass.setText("Confirmed Password: ");
                newpass.setEditable(true);
                confirmpass.setEditable(true);
                newpass.setBorder(new LineBorder(Color.black));
                confirmpass.setBorder(new LineBorder(Color.black));
                b_confirm.setEnabled(true);
                b_confirm.setVisible(true);
                b_cancel.setEnabled(true);
                b_cancel.setVisible(true);
            }
        });

        b_confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Validating the length of the password has to be at least 6 characters
                if ( newpass.getText().length() >= 6 && confirmpass.getText().length() >= 6)
                {
                    //Checking if password matches or not
                    //Password and confirmed Password match
                    //Password and confirmed Password match
                    if (Arrays.equals(newpass.getPassword(), confirmpass.getPassword()))
                    {
                        PassWord = newpass.getText();
                        errorPass.setText(" ");
                        JOptionPane.showMessageDialog(f, "Update Password Successfully!");
                        l_newpass.setText("");
                        l_confirmpass.setText("");
                        newpass.setText(null);
                        confirmpass.setText(null);
                        newpass.setEditable(false);
                        confirmpass.setEditable(false);
                        newpass.setBorder(new LineBorder(Color.white));
                        confirmpass.setBorder(new LineBorder(Color.white));
                        newpass.setBackground(Color.white);
                        confirmpass.setBackground(Color.white);
                        b_confirm.setEnabled(false);
                        b_confirm.setVisible(false);
                        b_cancel.setEnabled(false);
                        b_cancel.setVisible(false);
                    }
                    //Password and confirmed Password do not match
                    else
                    {
                        //Error occur with the confirmed password and the password doesnt match
                        newpass.setText(null);
                        confirmpass.setText(null);
                        errorPass.setText("<html><font color='Red'>Passwords Do Not Match. Please Try Again! </font></html>");
                    }
                }
                else{
                    //Error occur with the confirmed password and the password doesnt match
                    newpass.setText(null);
                    confirmpass.setText(null);
                    errorPass.setText("<html><font color='Red'>Password should be at least 6 characters! </font></html>");
                }
            }
        });

        b_cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                errorPass.setText(" ");
                l_newpass.setText("");
                l_confirmpass.setText("");
                newpass.setText(null);
                confirmpass.setText(null);
                newpass.setEditable(false);
                confirmpass.setEditable(false);
                newpass.setBorder(new LineBorder(Color.white));
                confirmpass.setBorder(new LineBorder(Color.white));
                newpass.setBackground(Color.white);
                confirmpass.setBackground(Color.white);
                b_confirm.setEnabled(false);
                b_confirm.setVisible(false);
                b_cancel.setEnabled(false);
                b_cancel.setVisible(false);
            }
        });


        //For grade panel
        p_grade.setBounds( 15, 290, 445, 320);
        p_grade.setBackground(Color.white);
        p.add(p_grade);
        p_grade.setLayout(null);
        //Grade Text
        grade = new JLabel("<html><u>GRADE</html>");
        grade.setFont(new Font("Default", Font.BOLD, 16));
        grade.setBounds(180, 5, 60,30);
        p_grade.add(grade);
        //Semester Options for Grade
        CourseGrade = new JLabel("Select a Course to Display Grade:");
        CourseGrade.setFont(new Font("Default", Font.PLAIN, 14));
        CourseGrade.setBounds(10,30, 250, 40);
        p_grade.add(CourseGrade);
        //For selecting courses----ComboBox
        coursesOptions = new JComboBox(courseEnrolled);
        coursesOptions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //When the course JCombobox is triggered
                if ( e.getSource() == coursesOptions )
                {
                    //No course has been selected
                    if (coursesOptions.getSelectedItem() == "-------------")
                    {
                        isCourseGradeSelected = false;
                    }
                    //a course or show all options has been selected
                    else
                    {
                        isCourseGradeSelected = true;
                        //If Show all is selected->Display all grade
                        if (coursesOptions.getSelectedItem() == "   SHOW ALL  ")
                        {
                            isSelectedAllGrade = true;
                        }
                        //Show all is not selected
                        else
                        {
                            isSelectedAllGrade = false;
                            GrCourse = (String) coursesOptions.getSelectedItem();
                        }
                    }
                }
            }
        });
        coursesOptions.setBounds(235, 30, 160, 40);
        p_grade.add(coursesOptions);
        //For refresh Button Icon
        refreshIcon = new ImageIcon(this.getClass().getResource("refresh.png"));
        Image imRef = refreshIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        refreshIcon = new ImageIcon(imRef);
        //Refresh Button
        refreshG = new JButton(refreshIcon);
        //When there is no record
        emptyRecord = new JLabel("<html>No Records Found. Please select a course to display or " +
                "click SHOW ALL to display all <br> of the courses you have taken before.<br>" +
                "If you are not enrol in any course, please click ENROLL button to add course.</html>");
        emptyRecord.setBounds(60, 65, 300, 190);
        emptyRecord.setFont(new Font("Default", Font.PLAIN, 14));
        p_grade.add(emptyRecord);

        //For Enrol button
        enrol = new JButton("ENROLL");
        enrol.setBounds(250, 250, 100, 40);
        enrol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //If enroll is called, then call another enroll interface
                if ( e.getSource() == enrol )
                {
                    //Display Enrol GUI
                    EnrollmentGUI eGUI = new  EnrollmentGUI(usrN);
                }
            }
        });
        p_grade.add(enrol);

        //Action Listener when the REFRESH ICON is CLICKED for grade
        refreshG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Refresh Button is clicked
                if ( e.getSource() == refreshG )
                {
                    //A course is selected, hide the emptyRecord messages
                    if ( isCourseGradeSelected )
                    {
                        emptyRecord.setText(" ");
                        overallGPA.setText(" ");
                        //conditions to check if student choose all or only a specific course has been chosen
                        if (isSelectedAllGrade)
                        {
                            //--------------------Database Connection Needed--------------
                            //A list of courses that student takes
                            //Extract all courses that this student take
                            emptyRecord.setText("");
                            try{
                                PreparedStatement statement = con.prepareStatement("SELECT C.Course_Name, T.Grade FROM SMSSytem.Course C, SMSSytem.Takes T where SMSSytem.T.Student_ID = ? and SMSSytem.C.Course_ID = SMSSytem.T.Course_ID");
                                statement.setString(1, SID);
                                ResultSet rs = statement.executeQuery();
                                //Getting data out from the query
                                emptyRecord.setText("");
                                String GradeList = "";
                                while (rs.next()) {
                                    Course = rs.getString("Course_Name");
                                    Grade = rs.getString("Grade");
                                    //Attendances = rs.getString("Attendance");

                                    System.out.print("ALL " + Courses + " ");
                                    GradeList = GradeList + Course + ": " + Grade + "%" + "<br/>";
                                }
                                emptyRecord.setText("<html>" + GradeList + "</html>");
                            }catch (SQLException other_SQLException) {
                                other_SQLException.printStackTrace();
                            }

                        }
                        else
                        {
                            //Retrieve Records for a specific course. "GrCourse" variable can retrieve a specific course that
                            //user has chosen specifically
                            try{
                            PreparedStatement statement = con.prepareStatement("SELECT C.Course_Name, T.Grade FROM SMSSytem.Course C, SMSSytem.Takes T where SMSSytem.T.Student_ID = ? and SMSSytem.C.Course_ID = SMSSytem.T.Course_ID and SMSSytem.C.Course_Name = ?");
                            statement.setString(1, SID);
                            statement.setString(2, GrCourse);
                            ResultSet rs = statement.executeQuery();
                            //Getting data out from the query
                            while (rs.next()) {
                                Course = rs.getString("Course_Name");
                                Grade = rs.getString("Grade");

                                System.out.print(Courses + " ");
                                emptyRecord.setText("");
                                emptyRecord.setText(emptyRecord.getText() + Course + ": " + Grade + "%");
                            }
                        }catch (SQLException other_SQLException) {
                        other_SQLException.printStackTrace();
                    }
                        }
                    }
                    //Display error
                    else
                    {
                        emptyRecord.setText("<html>No Records Found. Please select a course to display or " +
                                "click SHOW ALL to display all <br> of the courses you have taken before.<br> " +
                               "If you are not enrol in any course, please click ENROLL button to add course.</html>");
                    }
                }
            }
        });
        refreshG.setBounds(395, 40, 20, 20);
        p_grade.add(refreshG);
        //Overall GPA Display
        overallGPA = new JLabel(" ");
        overallGPA.setBounds(10, 70, 280, 30);
        p_grade.add(overallGPA);

        //For overall GPA button
        overallG = new JButton("Overall GPA");
        overallG.setBounds( 70, 250, 100, 40);
        overallG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Triggered when overall Grade Button is clicked
                if (e.getSource() == overallG)
                {
                    //Displaying OVerall GPA message
                    emptyRecord.setText( " ");
                    //---------------Connection with Database---------------
                    //Extracting Cummulative GPA for this particular student
                    try{
                        PreparedStatement statement = con.prepareStatement("SELECT count(T.Course_ID) as numCourses, sum(T.Grade) as gradeSum FROM SMSSytem.Course C, SMSSytem.Takes T where SMSSytem.T.Student_ID = ? and SMSSytem.C.Course_ID = SMSSytem.T.Course_ID");
                        statement.setString(1, SID);
                        ResultSet rs = statement.executeQuery();
                        //Getting data out from the query
                        overallGPA.setText("");
                        Float cGPA_f;
                        Float numCourses;
                        Float gradeSum;
                        while (rs.next()) {
                            numCourses = Float.parseFloat(rs.getString("numCourses"));
                            gradeSum = Float.parseFloat(rs.getString("gradeSum"));
                            cGPA_f = 4.33f * (gradeSum/numCourses)/100;
                            cGPA_s = String.format("%.2f", cGPA_f);


                        }
                    }catch (SQLException other_SQLException) {
                        other_SQLException.printStackTrace();
                    }


                    overallGPA.setText("Your Cumulative (Overall) GPA is: " + cGPA_s + "/4.33");
                }
            }
        });
        p_grade.add(overallG);

        //For attendance panel
        p_attend.setBounds( 490, 290, 445, 320);
        p_attend.setBackground(Color.white);
        p.add(p_attend);
        p_attend.setLayout(null);
        attendanceTitle = new JLabel("<html><u>ATTENDANCE</html>");
        attendanceTitle.setFont(new Font("Default", Font.BOLD, 16));
        attendanceTitle.setBounds(160, 5, 120, 30);
        p_attend.add(attendanceTitle);
        //Semester Options for Attendance
        CourseAttendance = new JLabel("Select a Course to Display Attendance:");
        CourseAttendance.setFont(new Font("Default", Font.PLAIN, 14));
        CourseAttendance.setBounds(5,30, 270, 40);
        p_attend.add(CourseAttendance);
        //For selecting the course for Attendance
        courseOptionsAttendance = new JComboBox(courseEnrolled);
        courseOptionsAttendance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ( e.getSource() == courseOptionsAttendance )
                {
                    //No course has been selected
                    if (courseOptionsAttendance.getSelectedItem() == "-------------")
                    {
                        isCourseAttendanceSelected = false;
                    }
                    //a course or show all option has been selected
                    else
                    {
                        isCourseAttendanceSelected = true;
                        //All courses have been chosen to display attendance
                        if(courseOptionsAttendance.getSelectedItem() == "   SHOW ALL  ")
                        {
                            isSelectedAllAtten = true;
                        }
                        //A specific course has been chosen
                        else
                        {
                            isSelectedAllAtten = false;
                            AttCourse = (String) courseOptionsAttendance.getSelectedItem();
                        }
                    }
                }
            }
        });

        //Empty Attendance Record
        emptyAttendanceRecord = new JLabel("<html>No Records Found. Please select a course to display or " +
                "click SHOW ALL to display all <br> of the courses you have taken before.</html>");
        emptyAttendanceRecord.setBounds(70, 100, 300, 150);
        emptyAttendanceRecord.setFont(new Font("Default", Font.PLAIN, 14));
        p_attend.add(emptyAttendanceRecord);

        //Course Selections for Attendance
        courseOptionsAttendance.setBounds(265, 30, 160, 40);
        p_attend.add(courseOptionsAttendance);
        refreshA = new JButton(refreshIcon);
        refreshA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Refresh for attendance button is pressed
                if ( e.getSource()== refreshA)
                {
                    //a course has been selected or select ALL has been selected
                    if(isCourseAttendanceSelected)
                    {
                        emptyAttendanceRecord.setText(" ");
                        //Users have chosen to display all attendance records for each course(s) that he/she has taken so far
                        //ALL records
                        if (isSelectedAllAtten)
                        {
                            try{
                                PreparedStatement statement = con.prepareStatement("SELECT C.Course_Name, T.Attendance FROM SMSSytem.Course C, SMSSytem.Takes T where SMSSytem.T.Student_ID = ? and SMSSytem.C.Course_ID = SMSSytem.T.Course_ID");
                                statement.setString(1, SID);
                                ResultSet rs = statement.executeQuery();
                                //Getting data out from the query
                                emptyAttendanceRecord.setText("");
                                String AttendanceList = "";
                                while (rs.next()) {
                                    Course = rs.getString("Course_Name");
                                    Attendances = rs.getString("Attendance");

                                    System.out.print("ALL " + Courses + " ");
                                    AttendanceList = AttendanceList + Course + ": " + Attendances + "<br/>";
                                }
                                emptyAttendanceRecord.setText("<html>" + AttendanceList + "</html>");
                            }catch (SQLException other_SQLException) {
                                other_SQLException.printStackTrace();
                            }
                        }
                        //a specific course is selected and display attendance to that specific course only
                        else
                        {
                            //Student has chosen a specific course and that specific option is stored in variable 'AttCourse'
                            //Retrieve attendance record specific to that course only
                            try{
                                PreparedStatement statement = con.prepareStatement("SELECT C.Course_Name, T.Attendance FROM SMSSytem.Course C, SMSSytem.Takes T where SMSSytem.T.Student_ID = ? and SMSSytem.C.Course_ID = SMSSytem.T.Course_ID and SMSSytem.C.Course_Name = ?");
                                statement.setString(1, SID);
                                statement.setString(2, AttCourse);
                                ResultSet rs = statement.executeQuery();
                                //Getting data out from the query
                                while (rs.next()) {
                                    Course = rs.getString("Course_Name");
                                    Attendances = rs.getString("Attendance");

                                    System.out.print(Courses + " ");
                                    emptyAttendanceRecord.setText("");
                                    emptyAttendanceRecord.setText(emptyAttendanceRecord.getText() + Course + ": " + Attendances);
                                }
                            }catch (SQLException other_SQLException) {
                                other_SQLException.printStackTrace();
                            }

                        }
                    }
                    //None has been selected
                    else
                    {
                        emptyAttendanceRecord.setText("<html>No Records Found. Please select a course to display or " +
                             "click SHOW ALL to display all <br> of the courses you have taken before.</html>");
                    }
                }

            }
        });
        refreshA.setBounds( 423, 40, 20, 20);
        p_attend.add(refreshA);


        //Account UI
        l_image=new JLabel();
        icon = new ImageIcon(this.getClass().getResource("student.jpg"));
        Image im = icon.getImage().getScaledInstance(1100,700,Image.SCALE_SMOOTH);
        icon = new ImageIcon(im);
        l_image.setIcon(icon);
        l_image.setBounds(0,0,1100,700);
        f.add(l_image);


        //1100 width and 700 height
        setSize(1100,700);

        //Use no layout managers
        p.setLayout(null);
        pa.setLayout(null);

        //Center frame on screen
        setLocationRelativeTo(null);

        //Let JFrame to close properly
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Make the frame visible
        setVisible(true);
    }

    //For Showing Messages after the Window has been disposed
    class WL implements WindowListener
    {
        @Override
        public void windowOpened(WindowEvent e) { }
        @Override
        public void windowClosing(WindowEvent e) {
            int output = JOptionPane.showConfirmDialog(f , "Are you Sure ? ", null , JOptionPane.YES_OPTION);
            if(output == JOptionPane.YES_OPTION)
            {
                System.exit(1);
            }
            else{
                StudentInterface SI = new StudentInterface(usrN);
            }
        }
        @Override
        public void windowClosed(WindowEvent e) {
            int output = JOptionPane.showConfirmDialog(f , "Are you Sure ? ", null , JOptionPane.YES_OPTION);
            if(output == JOptionPane.YES_OPTION)
            {
                JOptionPane.showMessageDialog(null , "Return to Login Screen!");
            }
            else{
                StudentInterface SI = new StudentInterface(usrN);
            }
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
