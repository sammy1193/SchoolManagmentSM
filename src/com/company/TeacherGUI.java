package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class TeacherGUI extends JFrame{

    //Variable Initializations
    private static Container frame;
    private ImageIcon icon;
    private static JLabel label;
    private static JPanel panel;

    //For Grade Panel
    private static JLabel gradeTitle, CourseGrade, emptyRecord, course, grade, overallGPA;
    private static JButton nextG, backG, overallG;
    //For Attendance Panel
    private static JLabel attendanceTitle, CourseAttendance, emptyAttendanceRecord;
    private static JButton nextA, backA;

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
    TeacherGUI(String userName)
    {
        userN = userName;
        //Create instance of JPanel
        JPanel p=new JPanel();
        //For Grade Panel
        JPanel p_grade = new JPanel();
        //For Attendance Panel
        JPanel p_attend = new JPanel();

        setTitle("Teacher Interface");
        frame = getContentPane();
        label = new JLabel();
        panel = new JPanel();

        //Panel Interfaces
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setLayout(null);


        //For grade panel
        p_grade.setBounds( 30, 290, 420, 320);
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
        CourseGrade.setBounds(15,25, 250, 40);
        p_grade.add(CourseGrade);

        //For attendance panel
        p_attend.setBounds( 500, 290, 420, 320);
        p_attend.setBackground(Color.white);
        p.add(p_attend);
        p_attend.setLayout(null);
        attendanceTitle = new JLabel("<html><u>ATTENDANCE</html>");
        attendanceTitle.setFont(new Font("Default", Font.BOLD, 16));
        attendanceTitle.setBounds(160, 5, 120, 30);
        p_attend.add(attendanceTitle);
        //Semester Options for Attendance
        //Semester Options for Attendance
        CourseAttendance = new JLabel("Select a Course to Display Attendance:");
        CourseAttendance.setFont(new Font("Default", Font.PLAIN, 14));
        CourseAttendance.setBounds(15,25, 270, 40);
        p_attend.add(CourseAttendance);

        //Image handling/Resizing
        icon = new ImageIcon(this.getClass().getResource("Office-Teacher.jpg"));
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
                TeacherGUI tGUI = new TeacherGUI(userN);
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

