import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.*;
import java.util.*;



public class adminDashboard extends JFrame {

    JPanel content;
    JFrame previous;   // Login page reference

    // =====================================================
    // =============== CONSTRUCTOR =========================
    // =====================================================
    adminDashboard(JFrame prev) {

        this.previous = prev;

        setUndecorated(true);
        setSize(1370, 897);
        setLocationRelativeTo(null);
        setLayout(null);

        // =====================================================
        // ================= MAIN PANEL =========================
        // =====================================================
        JPanel mainPanel = new JPanel(null);
        mainPanel.setBounds(0, 0, 1370, 897);
        mainPanel.setBackground(new Color(230, 230, 250));
        add(mainPanel);

        // =====================================================
        // ================= TOP BAR ============================
        // =====================================================
        JPanel topBar = new JPanel(null);
        topBar.setBounds(0, 0, 1370, 70);
        topBar.setBackground(new Color(60, 63, 65));
        mainPanel.add(topBar);

        JLabel title = new JLabel(
                "ADMIN CONTROL PANEL",
                SwingConstants.CENTER
        );
        title.setBounds(450, 10, 500, 50);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(Color.WHITE);
        topBar.add(title);

        // ===== Close App Button =====
        JButton close = new JButton("X");
        close.setBounds(1320, 20, 40, 30);
        close.setBackground(Color.RED);
        close.setForeground(Color.WHITE);
        close.addActionListener(e -> System.exit(0));
        topBar.add(close);

        // ===== Minimize Button =====
        JButton minimize = new JButton("_");

        minimize.setBounds(1270, 20, 40, 30);

        minimize.setBackground(Color.GRAY);
        minimize.setForeground(Color.WHITE);
        minimize.setFocusPainted(false);

        topBar.add(minimize);

// Action
        minimize.addActionListener(e ->
                setState(JFrame.ICONIFIED)
        );

        // =====================================================
        // ================= SIDEBAR ===========================
        // =====================================================
        JPanel sidebar = new JPanel(null);
        sidebar.setBounds(0, 70, 250, 827);
        sidebar.setBackground(new Color(0, 102, 102));
        mainPanel.add(sidebar);

        int y = 50;

        JButton home = createBtn("Home", y);
        sidebar.add(home);
        y += 60;

        JButton addStudent = createBtn("Add Student", y);
        sidebar.add(addStudent);
        y += 60;
        addStudent.addActionListener(e -> {

            content.removeAll();

            AddStudentPanel panel = new AddStudentPanel();
            panel.setBounds(0, 0, 1120, 827);   // ⭐ IMPORTANT

            content.add(panel);

            content.repaint();
            content.revalidate();

        });



        JButton uploadResult = createBtn("Upload Result", y);
        sidebar.add(uploadResult);
        y += 60;
        uploadResult.addActionListener(e -> {

            content.removeAll();

            content.add(new UploadResultPanel());

            content.repaint();
            content.revalidate();
        });



        JButton attendance = createBtn("Update Attendance", y);
        sidebar.add(attendance);
        y += 60;
        attendance.addActionListener(e->{

            content.removeAll();

            content.add(
                    new AttendanceEntryPanel(content)
            );

            content.repaint();
            content.revalidate();
        });





        JButton fees = createBtn("Update Fees", y);
        sidebar.add(fees);
        y += 60;
        fees.addActionListener(e -> {

            content.removeAll();

            content.add(new UpdateFeesPanel());

            content.repaint();
            content.revalidate();
        });



        JButton notices = createBtn("Post Notices", y);
        sidebar.add(notices);
        y += 60;

        notices.addActionListener(e -> {

            content.removeAll();

            content.add(new NoticePanel());  // panel load

            content.repaint();
            content.revalidate();
        });






        // ===== Logout Button =====
        JButton logout = createBtn("Logout", 720);
        sidebar.add(logout);

        logout.addActionListener(e -> {

            dispose();

            if(previous != null) {

                previous.setVisible(true);

            } else {

                System.exit(0); // direct exit if no login page

            }
        });

        // =====================================================
        // ================= CONTENT PANEL =====================
        // =====================================================
        content = new JPanel(null);
        content.setBounds(250, 70, 1120, 827);
        content.setBackground(Color.WHITE);
        mainPanel.add(content);

        // ===== Default Home Screen =====
        JLabel homeLabel = new JLabel(
                "WELCOME ADMIN",
                SwingConstants.CENTER
        );
        homeLabel.setBounds(300, 200, 500, 50);
        homeLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        content.add(homeLabel);

        setVisible(true);
    }

    // =====================================================
    // =============== BUTTON DESIGN METHOD =================
    // =====================================================
    JButton createBtn(String text, int y) {

        JButton btn = new JButton(text);
        btn.setBounds(25, y, 200, 40);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBackground(new Color(0,153,153));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);

        return btn;
    }
}


//=================================================================================================
//=================================================================================================

class AttendanceEntryPanel extends JPanel {

    AttendanceEntryPanel(JPanel content){

        setLayout(null);
        setBounds(0,0,1120,827);
        setBackground(Color.WHITE);

        JLabel title = new JLabel(
                "ATTENDANCE ENTRY",
                SwingConstants.CENTER
        );

        title.setBounds(350,50,400,40);
        title.setFont(
                new Font("Segoe UI",Font.BOLD,26)
        );
        add(title);

        JLabel rollLbl =
                new JLabel("Enter Roll No:");

        rollLbl.setBounds(400,200,200,30);
        add(rollLbl);

        JTextField rollField =
                new JTextField();

        rollField.setBounds(400,240,200,30);
        add(rollField);

        JButton ok =
                new JButton("OPEN CALENDAR");

        ok.setBounds(390,300,220,40);
        add(ok);

        ok.addActionListener(e->{

            String roll =
                    rollField.getText();

            if(roll.isEmpty()){

                JOptionPane.showMessageDialog(
                        this,
                        "Enter Roll No"
                );
                return;
            }

            content.removeAll();

            content.add(
                    new AttendanceCalendarPanel(
                            roll
                    )
            );

            content.repaint();
            content.revalidate();
        });
    }
}


//=====================================

class AttendanceCalendarPanel extends JPanel {

    String rollNo;
    Set<String> absentDates =
            new HashSet<>();

    AttendanceCalendarPanel(String rollNo){

        this.rollNo = rollNo;

        setLayout(new BorderLayout());
        setBounds(0,0,1120,827);

        JPanel grid =
                new JPanel(
                        new GridLayout(0,7,5,5)
                );

        JScrollPane scroll =
                new JScrollPane(grid);

        add(scroll,BorderLayout.CENTER);

        LocalDate today =
                LocalDate.now();

        LocalDate start =
                today.minusMonths(3);

        for(LocalDate date=start;
            !date.isAfter(today);
            date=date.plusDays(1)){

            JButton dayBtn =
                    new JButton(
                            String.valueOf(
                                    date.getDayOfMonth()
                            )
                    );

            dayBtn.setBackground(
                    Color.BLUE
            );

            dayBtn.setForeground(
                    Color.WHITE
            );

            String fullDate =
                    date.toString();

            dayBtn.setToolTipText(
                    fullDate
            );

            dayBtn.addActionListener(e->{

                if(absentDates
                        .contains(fullDate)){

                    absentDates.remove(
                            fullDate
                    );

                    dayBtn.setBackground(
                            Color.BLUE
                    );

                }else{

                    absentDates.add(
                            fullDate
                    );

                    dayBtn.setBackground(
                            Color.RED
                    );
                }
            });

            grid.add(dayBtn);
        }

        // ===== SAVE BUTTON =====
        JButton save =
                new JButton("SAVE ATTENDANCE");

        save.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,16
                )
        );

        save.addActionListener(e->{
            saveAttendance();
        });

        add(save,BorderLayout.SOUTH);
    }

    // ===== SAVE ABSENTS =====
    void saveAttendance(){

        try{

            Connection con =
                    Dbmanage.getConnection();

            String sql =
                    "INSERT INTO attendance "+
                            "(roll_no,attendance_date,status) "+
                            "VALUES (?,?,?)";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            for(String date :
                    absentDates){

                ps.setString(1,rollNo);
                ps.setString(2,date);
                ps.setString(3,"ABSENT");

                ps.executeUpdate();
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Attendance Saved ✔"
            );

            con.close();

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}




//=================================================================================================

//===============================================
//=============== Upload Result =================



class UploadResultPanel extends JPanel {

    UploadResultPanel(){

        setLayout(null);
        setBackground(Color.WHITE);
        setBounds(0,0,1120,827);

        // ===== TITLE =====
        JLabel title = new JLabel(
                "UPLOAD STUDENT RESULT",
                SwingConstants.CENTER
        );
        title.setBounds(300,30,500,40);
        title.setFont(
                new Font("Segoe UI",Font.BOLD,26)
        );
        add(title);

        int y = 150;

        // ===== FIELDS =====
        JTextField rollField =
                addField("Roll No:",y);
        y+=70;

        JTextField subjectField =
                addField("Subject:",y);
        y+=70;

        JTextField marksField =
                addField("Marks Obtained:",y);
        y+=90;

        // ENTER SHIFT
        rollField.addActionListener(
                ev -> subjectField.requestFocus()
        );

        subjectField.addActionListener(
                ev -> marksField.requestFocus()
        );


        // ===== SAVE BUTTON =====
        JButton save =
                new JButton("Add Subject Marks");

        save.setBounds(420,y,250,40);
        save.setFont(
                new Font("Segoe UI",Font.BOLD,16)
        );
        add(save);

        // ============================================
        // =============== SAVE ACTION =================
        // ============================================
        save.addActionListener(e -> {

            try{

                Connection con =
                        Dbmanage.getConnection();

                // ⭐ max_marks DEFAULT 80 hai
                String sql =
                        "INSERT INTO result " +
                                "(roll_no,subject,marks_obtained) " +
                                "VALUES (?,?,?)";

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ps.setInt(1,
                        Integer.parseInt(
                                rollField.getText()
                        )
                );

                ps.setString(2,
                        subjectField.getText()
                );

                ps.setInt(3,
                        Integer.parseInt(
                                marksField.getText()
                        )
                );

                ps.executeUpdate();

                JOptionPane.showMessageDialog(
                        this,
                        "Subject Marks Added ✔"
                );

                // CLEAR FIELDS
                subjectField.setText("");
                marksField.setText("");

                con.close();

            }catch(Exception ex){

                ex.printStackTrace();

                JOptionPane.showMessageDialog(
                        this,
                        "Error Saving Marks ❌"
                );
            }
        });
    }

    // ===============================================
    // FIELD DESIGN METHOD
    // ===============================================
    JTextField addField(String label,int y){

        JLabel lbl = new JLabel(label);
        lbl.setBounds(300,y,150,30);
        lbl.setFont(
                new Font("Segoe UI",Font.BOLD,16)
        );
        add(lbl);

        JTextField field = new JTextField();
        field.setBounds(450,y,250,30);
        add(field);

        return field;
    }
}

// =====================================================
// =============== Notice box ===================
// =====================================================


class NoticePanel extends JPanel {

    String img1Path="";
    String img2Path="";

    JLabel preview1 = new JLabel();
    JLabel preview2 = new JLabel();

    NoticePanel(){

        setLayout(null);
        setBounds(0,0,1120,827);
        setBackground(Color.WHITE);

        // ===== TITLE =====
        JLabel title =
                new JLabel(
                        "POST NOTICE",
                        SwingConstants.CENTER
                );

        title.setBounds(350,30,400,40);
        title.setFont(
                new Font("Segoe UI",Font.BOLD,26)
        );
        add(title);

        // =================================================
        // ===== IMAGE 1 SECTION ============================
        // =================================================

        JButton img1Btn =
                new JButton("Upload Photo 1");

        img1Btn.setBounds(200,120,200,35);
        add(img1Btn);

        preview1.setBounds(200,170,200,150);
        preview1.setBorder(
                BorderFactory.createLineBorder(Color.BLACK)
        );
        add(preview1);

        img1Btn.addActionListener(e->{

            JFileChooser fc = new JFileChooser();

            if(fc.showOpenDialog(null)
                    == JFileChooser.APPROVE_OPTION){

                File file = fc.getSelectedFile();

                img1Path = file.getAbsolutePath();

                ImageIcon img =
                        new ImageIcon(img1Path);

                Image scaled =
                        img.getImage()
                                .getScaledInstance(
                                        200,150,
                                        Image.SCALE_SMOOTH
                                );

                preview1.setIcon(
                        new ImageIcon(scaled)
                );
            }
        });


        // ===== DELETE BUTTON IMAGE 1 =====
        JButton delImg1 = new JButton("X");

        delImg1.setBounds(370,170,30,30); // preview corner
        delImg1.setForeground(Color.WHITE);
        delImg1.setBackground(Color.RED);
        delImg1.setFocusPainted(false);

        add(delImg1);

// Action → Remove image
        delImg1.addActionListener(e -> {

            preview1.setIcon(null);
            img1Path = "";

        });

        // =================================================
        // ===== IMAGE 2 SECTION ============================
        // =================================================

        JButton img2Btn =
                new JButton("Upload Photo 2");

        img2Btn.setBounds(500,120,200,35);
        add(img2Btn);

        preview2.setBounds(500,170,200,150);
        preview2.setBorder(
                BorderFactory.createLineBorder(Color.BLACK)
        );
        add(preview2);

        img2Btn.addActionListener(e->{

            JFileChooser fc = new JFileChooser();

            if(fc.showOpenDialog(null)
                    == JFileChooser.APPROVE_OPTION){

                File file = fc.getSelectedFile();

                img2Path = file.getAbsolutePath();

                ImageIcon img =
                        new ImageIcon(img2Path);

                Image scaled =
                        img.getImage()
                                .getScaledInstance(
                                        200,150,
                                        Image.SCALE_SMOOTH
                                );

                preview2.setIcon(
                        new ImageIcon(scaled)
                );
            }
        });


        // ===== DELETE BUTTON IMAGE 2 =====
        JButton delImg2 = new JButton("X");

        delImg2.setBounds(670,170,30,30);

        delImg2.setForeground(Color.WHITE);
        delImg2.setBackground(Color.RED);
        delImg2.setFocusPainted(false);

        add(delImg2);

        delImg2.addActionListener(e -> {

            preview2.setIcon(null);
            img2Path = "";

        });

        // =================================================
        // ===== NOTICE TEXT BOX ============================
        // =================================================

        JTextArea noticeText =
                new JTextArea();

        JScrollPane scroll =
                new JScrollPane(noticeText);

        scroll.setBounds(200,350,500,180);
        add(scroll);

        // =================================================
        // ===== POST BUTTON ================================
        // =================================================

        JButton post =
                new JButton("POST NOTICE");

        post.setBounds(350,560,200,40);
        add(post);

        post.addActionListener(e->{

            try{

                Connection con =
                        Dbmanage.getConnection();

                String sql =
                        "INSERT INTO notices " +
                                "(image1_path,image2_path,message) " +
                                "VALUES (?,?,?)";

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ps.setString(1,img1Path);
                ps.setString(2,img2Path);
                ps.setString(3,
                        noticeText.getText()
                );

                ps.executeUpdate();

                JOptionPane.showMessageDialog(
                        this,
                        "Notice Posted ✔"
                );

                con.close();

            }catch(Exception ex){
                ex.printStackTrace();
            }
        });
    }
}








class UpdateFeesPanel extends JPanel {

    UpdateFeesPanel(){

        setLayout(null);
        setBackground(Color.WHITE);
        setBounds(0,0,1120,827);

        // ===== Title =====
        JLabel title = new JLabel(
                "UPDATE STUDENT FEES",
                SwingConstants.CENTER
        );
        title.setBounds(300,30,500,40);
        title.setFont(new Font("Segoe UI",Font.BOLD,26));
        add(title);

        int y = 150;

        // ===== Fields =====
        JTextField rollField =
                addField("Roll No:", y); y+=70;

        JTextField totalField =
                addField("Total Fees:", y); y+=70;

        JTextField paidField =
                addField("Paid Fees:", y); y+=90;


        // ===== Save Button =====
        JButton save = new JButton("Save Fees");
        save.setBounds(420, y, 200, 40);
        save.setFont(new Font("Segoe UI",Font.BOLD,16));
        add(save);

        // =================================================
        // =============== SAVE ACTION ======================
        // =================================================
        save.addActionListener(e -> {

            try{

                Connection con =
                        Dbmanage.getConnection();

                String sql =
                        "INSERT INTO fees " +
                                "(roll_no,total_fees,fees_paid) " +
                                "VALUES (?,?,?)";

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ps.setString(1,
                        rollField.getText());

                ps.setDouble(2,
                        Double.parseDouble(
                                totalField.getText()
                        ));

                ps.setDouble(3,
                        Double.parseDouble(
                                paidField.getText()
                        ));

                ps.executeUpdate();

                JOptionPane.showMessageDialog(
                        this,
                        "Fees Saved Successfully ✔"
                );

                con.close();

            }catch(Exception ex){

                ex.printStackTrace();

                JOptionPane.showMessageDialog(
                        this,
                        "Error Saving Fees ❌"
                );
            }
        });
    }

    // ===== Field Method =====
    JTextField addField(String label,int y){

        JLabel lbl = new JLabel(label);
        lbl.setBounds(300,y,150,30);
        lbl.setFont(new Font("Segoe UI",Font.BOLD,16));
        add(lbl);

        JTextField field = new JTextField();
        field.setBounds(450,y,250,30);
        add(field);

        return field;
    }
}







// =====================================================
// =============== ADD STUDENT PANEL ===================
// =====================================================

class AddStudentPanel extends JPanel {

    String photoPath = "";   // ===== Photo Path Store =====

    AddStudentPanel() {

        setLayout(null);
        setBackground(Color.WHITE);

        // ===== TITLE =====
        JLabel title =
                new JLabel("ADD NEW STUDENT", SwingConstants.CENTER);

        title.setBounds(250,20,500,40);
        title.setFont(
                new Font("Segoe UI",Font.BOLD,26)
        );
        add(title);

        int y = 100;

        JTextField name = addField("Name:", y); y+=50;
        JTextField roll = addField("Roll No:", y); y+=50;
        JTextField sclass = addField("Class:", y); y+=50;
        JTextField dob = addField("Date of Birth:", y); y+=50;
        JTextField phone = addField("Phone:", y); y+=50;
        JTextField address = addField("Address:", y); y+=50;
        JTextField parent = addField("Parent Name:", y); y+=50;
        JTextField blood = addField("Blood Group:", y); y+=50;
        JTextField group = addField("School Group:", y); y+=50;

        // ===== LOGIN FIELDS =====
        JTextField username = addField("Username:", y); y+=50;
        JTextField password = addField("Password:", y); y+=60;

        // =====================================================
        // ===== PHOTO UPLOAD SECTION ==========================
        // =====================================================

        JLabel photoLbl =
                new JLabel("Student Photo:");

        photoLbl.setBounds(250,y,150,30);
        add(photoLbl);

        JButton uploadBtn =
                new JButton("Upload Photo");

        uploadBtn.setBounds(400,y,150,30);
        add(uploadBtn);

        JLabel preview =
                new JLabel();

        preview.setBounds(580,y,120,120);
        preview.setBorder(
                BorderFactory.createLineBorder(Color.BLACK)
        );
        add(preview);

        y += 140;

        // ===== Upload Action =====
        uploadBtn.addActionListener(e -> {

            JFileChooser fc =
                    new JFileChooser();

            if(fc.showOpenDialog(null)
                    == JFileChooser.APPROVE_OPTION){

                File file =
                        fc.getSelectedFile();

                photoPath =
                        file.getAbsolutePath();

                ImageIcon img =
                        new ImageIcon(photoPath);

                Image scaled =
                        img.getImage()
                                .getScaledInstance(
                                        120,120,
                                        Image.SCALE_SMOOTH
                                );

                preview.setIcon(
                        new ImageIcon(scaled)
                );
            }
        });

        // =====================================================
        // ===== ENTER SHIFT ==================================
        // =====================================================

        name.addActionListener(ev -> roll.requestFocus());
        roll.addActionListener(ev -> sclass.requestFocus());
        sclass.addActionListener(ev -> dob.requestFocus());
        dob.addActionListener(ev -> phone.requestFocus());
        phone.addActionListener(ev -> address.requestFocus());
        address.addActionListener(ev -> parent.requestFocus());
        parent.addActionListener(ev -> blood.requestFocus());
        blood.addActionListener(ev -> group.requestFocus());
        group.addActionListener(ev -> username.requestFocus());
        username.addActionListener(ev -> password.requestFocus());

        // =====================================================
        // ===== ADD BUTTON ===================================
        // =====================================================

        JButton addBtn =
                new JButton("Add Student");

        addBtn.setBounds(350, y-80, 200, 40);
        add(addBtn);

        // =====================================================
        // ===== INSERT ACTION ================================
        // =====================================================

        addBtn.addActionListener(e -> {

            try {

                Connection con =
                        Dbmanage.getConnection();

                // ===== DUPLICATE CHECK =====
                String check =
                        "SELECT * FROM students "+
                                "WHERE username=?";

                PreparedStatement cps =
                        con.prepareStatement(check);

                cps.setString(
                        1,username.getText()
                );

                ResultSet rs =
                        cps.executeQuery();

                if(rs.next()){

                    JOptionPane.showMessageDialog(
                            this,
                            "Username already exists ⚠️"
                    );
                    return;
                }

                // ===== INSERT =====
                String sql =
                        "INSERT INTO students "+
                                "(username,password,role,name,"+
                                "roll_no,class,dob,phone,address,"+
                                "parent_name,blood_group,"+
                                "school_group,photo_path) "+
                                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ps.setString(1, username.getText());
                ps.setString(2, password.getText());
                ps.setString(3, "student");

                ps.setString(4, name.getText());
                ps.setString(5, roll.getText());
                ps.setString(6, sclass.getText());
                ps.setString(7, dob.getText());
                ps.setString(8, phone.getText());
                ps.setString(9, address.getText());
                ps.setString(10,parent.getText());
                ps.setString(11,blood.getText());
                ps.setString(12,group.getText());

                // ===== PHOTO PATH =====
                ps.setString(13,photoPath);

                ps.executeUpdate();

                JOptionPane.showMessageDialog(
                        this,
                        "Student Added Successfully ✔"
                );

                con.close();

            } catch (Exception ex) {

                ex.printStackTrace();

                JOptionPane.showMessageDialog(
                        this,
                        "Error Adding Student ❌"
                );
            }
        });
    }

    // =====================================================
    // ===== FIELD METHOD ==================================
    // =====================================================

    JTextField addField(String label,int y){

        JLabel lbl = new JLabel(label);
        lbl.setBounds(250,y,150,30);
        add(lbl);

        JTextField field =
                new JTextField();

        field.setBounds(400,y,250,30);
        add(field);

        return field;
    }
}

