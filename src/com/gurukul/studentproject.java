
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.net.URL;
import javax.swing.*;
import java.awt.*;






public class studentproject extends JFrame {

    int mouseX, mouseY;

    // ===== Background Panel =====
    static class BackgroundPanel extends JPanel {

        private Image image;

        public BackgroundPanel() {

            URL url = BackgroundPanel.class.getResource(
                    "/images/blackboard-background-1600-x-1200-ycyhrbf0gbd83oyo.jpg"
            );


            if (url != null) {
                image = new ImageIcon(url).getImage();
            } else {
                System.out.println("Image NOT found");
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (image != null) {   // ⭐ IMPORTANT
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }







    // ===== Constructor =====
    public studentproject() {

        setUndecorated(true);
        setSize(1370, 897);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        BackgroundPanel background = new BackgroundPanel();
        background.setLayout(null);
        setContentPane(background);

        // ===== Drag Window =====
        background.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        background.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                setLocation(
                        e.getXOnScreen() - mouseX,
                        e.getYOnScreen() - mouseY
                );
            }
        });

        // ===== Close Button =====
        JButton close = new JButton("X");
        close.setBounds(1320, 10, 40, 30);
        close.setBackground(Color.RED);
        close.setForeground(Color.WHITE);
        close.setFocusPainted(false);
        close.addActionListener(e -> System.exit(0));
        background.add(close);

        // ===== Minimize Button =====
        JButton min = new JButton("-");
        min.setBounds(1270, 10, 40, 30);
        min.setBackground(Color.GRAY);
        min.setForeground(Color.WHITE);
        min.setFocusPainted(false);
        min.addActionListener(e -> setState(JFrame.ICONIFIED));
        background.add(min);

        // ===== School Logo =====
        ImageIcon logoIcon = new ImageIcon("images/download.png");
        Image img = logoIcon.getImage()
                .getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(img);

        JLabel logo = new JLabel(logoIcon);
        logo.setBounds(600, 270, 170, 130);
        background.add(logo);

        // ===== Title =====
        JLabel title = new JLabel(
                "Gurukul CBSE Global School Welcomes You.",
                SwingConstants.CENTER
        );

        title.setBounds(250, 150, 900, 60);
        title.setFont(new Font("Serif", Font.BOLD, 40));
        title.setForeground(new Color(9, 0, 139, 237));
        background.add(title);

        // ===== Tagline =====
        JLabel tagline = new JLabel(
                "Where Tradition Meets Modern Education",
                SwingConstants.CENTER
        );

        tagline.setBounds(350, 210, 700, 40);
        tagline.setFont(new Font("Lucida calligraphy", Font.ITALIC, 22));
        tagline.setForeground(Color.WHITE);
        background.add(tagline);

        // ===== Buttons =====
        Color lightBlue = new Color(200, 173, 230);


        JButton login = new JButton("Log In");
        login.setBounds(520, 430, 300, 50);
        login.setBackground(lightBlue);
        login.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 18));
        background.add(login);






        // ===== Login Action =====
//        = = = = = == = = = = = = = = =
//       = = = = = =  = = = = = = = = =

        login.addActionListener(e -> {

            // ===== Hide Home Buttons =====
           logo.setVisible(false);
            login.setVisible(false);
            tagline.setVisible(false);
            title.setVisible(false);

            // ===== Login Panel =====
            JPanel loginPanel = new JPanel(null);
            loginPanel.setBounds(435, 200, 500, 380);
            loginPanel.setBackground(Color.BLACK);

            background.add(loginPanel);
            background.repaint();
            background.revalidate();

            // ===== Title =====
            JLabel logintitle = new JLabel("LOGIN", SwingConstants.CENTER);
            logintitle.setBounds(150, 30, 200, 40);
            logintitle.setForeground(Color.WHITE);
            logintitle.setFont(new Font("Arial", Font.BOLD, 24));
            loginPanel.add(logintitle);

            // ===== Username =====
            JLabel userLbl = new JLabel("Username:");
            userLbl.setBounds(60, 120, 120, 30);
            userLbl.setForeground(Color.WHITE);
            loginPanel.add(userLbl);

            JTextField username = new JTextField();
            username.setBounds(180, 120, 240, 30);
            loginPanel.add(username);

            // ===== Password =====
            JLabel passLbl = new JLabel("Password:");
            passLbl.setBounds(60, 180, 120, 30);
            passLbl.setForeground(Color.WHITE);
            loginPanel.add(passLbl);

            JPasswordField password = new JPasswordField();
            password.setBounds(180, 180, 240, 30);
            loginPanel.add(password);

            // ===== ENTER SHIFT =====
            username.addActionListener(ev -> password.requestFocus());

            // ===== Login Button =====
            JButton loginBtn = new JButton("Login");
            loginBtn.setBounds(150, 240, 200, 35);
            loginPanel.add(loginBtn);

            // =====================================================
            // =============== LOGIN BUTTON ACTION =================
            // =====================================================
            loginBtn.addActionListener(ev -> {


                String user =
                        username.getText().trim();

                String pass =
                        new String(password.getPassword()).trim();

                if(user.isEmpty() || pass.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Enter Username & Password"
                    );
                    return;
                }

                try {

                    Connection con =
                            Dbmanage.getConnection();

                    String query =
                            "SELECT * FROM students " +
                                    "WHERE username=? AND password=?";

                    PreparedStatement ps =
                            con.prepareStatement(query);

                    ps.setString(1, user);
                    ps.setString(2, pass);

                    ResultSet rs =
                            ps.executeQuery();

                    // ===== LOGIN SUCCESS =====
                    if(rs.next()) {

                        String role =
                                rs.getString("role");

                        // ADMIN LOGIN
                        if(role.equals("admin")) {

                            JOptionPane.showMessageDialog(
                                    null,
                                    "Admin Login Successful"
                            );

                            setVisible(false);
                            new adminDashboard(studentproject.this);

                        }

                        // STUDENT LOGIN
                        else {

                            JOptionPane.showMessageDialog(
                                    null,
                                    "Student Login Successful"
                            );

                            setVisible(false);
                            new Dashboard(studentproject.this, user);
                        }

                    }

                    // ===== LOGIN FAIL =====
                    else {

                        JOptionPane.showMessageDialog(
                                null,
                                "Incorrect Username or Password"
                        );
                    }

                } catch(Exception ex) {
                    ex.printStackTrace();
                }

            });






            // ===== Cancel Button =====
            JButton cancel = new JButton("Cancel");
            cancel.setBounds(180, 290, 140, 30);
            loginPanel.add(cancel);




            // =====================================================
            // ================= CANCEL ACTION =====================
            // =====================================================
            cancel.addActionListener(ev -> {

                background.remove(loginPanel);


                login.setVisible(true);
                tagline.setVisible(true);
                title.setVisible(true);
                logo.setVisible(true);

                background.repaint();
                background.revalidate();
                background.updateUI();
            });

        });


        // ===== Helpdesk =====
        JLabel help = new JLabel(
                "<HTML><CENTER>" +
                        "📞 902250XXXX<br>" +
                        "📧 helpdesk@gurukulschool.com" +
                        "</CENTER></HTML>"
        );

        help.setBounds(1100, 730, 200, 100);
        help.setForeground(Color.WHITE);
        background.add(help);

        // ===== Footer =====
        JLabel footer = new JLabel(
                "© 2026 Gurukul CBSE Global School"
        );

        footer.setBounds(80, 770, 300, 60);
        footer.setForeground(Color.WHITE);
        footer.setFont(new Font("JokerMan", Font.PLAIN, 15));
        background.add(footer);

        setVisible(true);
    }





//  = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    //  = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    //  = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    //  = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
// =============================
// = = = = = = = = = = = = =

    // ========= ======= =======
    // main interface opening
    // ========= ======== ========

    class Dashboard extends JFrame {

        JPanel content;
        JFrame previous;

        Dashboard(JFrame prev , String username) {

            this.previous = prev;

            setUndecorated(true);
            setSize(1370, 897);
            setLocationRelativeTo(null);
            setLayout(null);

            // =====================================================
            // =============== MAIN LIGHT BLUE PANEL ================
            // =====================================================
            JPanel mainPanel = new JPanel(null);
            mainPanel.setBounds(0, 0, 1370, 897);
            mainPanel.setBackground(new Color(221, 173, 230, 255));
            add(mainPanel);

            // =====================================================
            // ================= SIDEBAR ============================
            // =====================================================
            JPanel sidebar = new JPanel(null);
            sidebar.setBounds(0, 0, 250, 897);
            sidebar.setBackground(new Color(0, 111, 139, 237));
            mainPanel.add(sidebar);

            // =====================================================
            // ================= CONTENT PANEL ======================
            // =====================================================
            JPanel content = new JPanel(null);
            content.setBounds(250, 0, 1120, 897);
            content.setBackground(Color.WHITE);
            mainPanel.add(content);

            // =====================================================
            // ================= CLOSE BUTTON ======================
            // =====================================================
            JButton close = new JButton("X");
            close.setBounds(1320, 10, 40, 30);
            close.setBackground(Color.RED);
            close.setForeground(Color.WHITE);
            close.setFocusPainted(false);
            mainPanel.add(close);

            close.addActionListener(e -> System.exit(0));

            // ===== Minimize Button =====
            JButton minimize = new JButton("_");

            minimize.setBounds(1270, 10, 40, 30);

            minimize.setBackground(Color.GRAY);
            minimize.setForeground(Color.WHITE);
            minimize.setFocusPainted(false);

            mainPanel.add(minimize);

// Action
            minimize.addActionListener(e ->
                    setState(JFrame.ICONIFIED)
            );






            // =====================================================
            // ================= SIDEBAR BUTTONS ===================
            // =====================================================
            int y = 150;

            // ===== HOME BUTTON =====
            JButton home = new JButton("Home");
            home.setBounds(25, y, 200, 40);
            home.setFont(new Font("Segoe UI", Font.BOLD, 18));
            sidebar.add(home);
            y += 60;
            // ===== DEFAULT HOME LOAD =====

            content.removeAll();

            content.add(new StudentNoticePanel());

            content.repaint();
            content.revalidate();
            // home action button
            home.addActionListener(e -> {

                content.removeAll();

                content.add(new StudentNoticePanel());

                content.repaint();
                content.revalidate();
            });





            JButton profile = new JButton("Profile");
            profile.setBounds(25, y, 200, 40);
            profile.setFont(new Font("Segoe UI", Font.BOLD, 18));
            sidebar.add(profile);
            y += 60;
            profile.addActionListener(e -> {

                content.removeAll();
                content.add(new ProfilePanel(username));
                content.repaint();
                content.revalidate();

            });



            JButton attendance = new JButton("Attendance");
            attendance.setBounds(25, y, 200, 40);
            attendance.setFont(new Font("Segoe UI", Font.BOLD, 18));
            sidebar.add(attendance);
            y += 60;
            attendance.addActionListener(e -> {

                content.removeAll();

                content.add(
                        new StudentAttendancePanel(
                                username   // login username
                        )
                );

                content.repaint();
                content.revalidate();
            });


            JButton fees = new JButton("Fees");
            fees.setBounds(25, y, 200, 40);
            fees.setFont(new Font("Segoe UI", Font.BOLD, 18));
            sidebar.add(fees);
            y += 60;
            fees.addActionListener(e -> {

                content.removeAll();


                content.add(new FeesPanel(username));

                content.repaint();
                content.revalidate();
            });


            JButton result = new JButton("Result");
            result.setBounds(25, y, 200, 40);
            result.setFont(new Font("Segoe UI", Font.BOLD, 18));
            sidebar.add(result);
            y += 80;
            result.addActionListener(e -> {

                content.removeAll();

                content.add(
                        new ResultPanel(username)
                );

                content.repaint();
                content.revalidate();
            });




            // =====================================================
            // ================= LOGOUT ============================
            // =====================================================
            JButton logout = new JButton("Logout");
            logout.setBounds(25, 800, 200, 40);
            sidebar.add(logout);

            logout.addActionListener(e -> {
                dispose();
                if(previous != null){
                    previous.setVisible(true);
                }
            });

            // close button
            JButton closedashboard = new JButton("X");
            close.setBounds(1320, 10, 40, 30);
            close.setBackground(Color.RED);
            close.setForeground(Color.WHITE);
            close.setFocusPainted(false);
            mainPanel.add(close);
            close.addActionListener(e -> System.exit(0));


            // =====================================================
            // ========== DEFAULT HOME SCREEN LOAD ==================
            // =====================================================
            JLabel homeLabel = new JLabel(
                    "WELCOME TO GURUKUL GLOBAL SCHOOL",
                    SwingConstants.CENTER
            );
            homeLabel.setBounds(200, 50, 700, 50);
            homeLabel.setFont(new Font("Eras Demi ITC", Font.BOLD, 28));
            content.add(homeLabel);

            // =====================================================
            setVisible(true);
        }
    }


///                           dashboard sec Ended
//  = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    //  = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    //  = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =

    //   attendance panel

    class StudentAttendancePanel extends JPanel {

        StudentAttendancePanel(String username){

            setLayout(null);
            setBounds(0,0,1120,897);
            setBackground(Color.WHITE);

            // ===== TITLE =====
            JLabel title = new JLabel(
                    "MY ATTENDANCE",
                    SwingConstants.CENTER
            );

            title.setBounds(350,30,400,40);
            title.setFont(
                    new Font("Segoe UI",Font.BOLD,26)
            );
            add(title);

            String rollNo = "";

            try{

                Connection con =
                        Dbmanage.getConnection();

                // ===== Fetch Roll No =====
                String sql1 =
                        "SELECT roll_no FROM students "+
                                "WHERE username=?";

                PreparedStatement ps1 =
                        con.prepareStatement(sql1);

                ps1.setString(1,username);

                ResultSet rs1 =
                        ps1.executeQuery();

                if(rs1.next()){

                    rollNo =
                            rs1.getString("roll_no");
                }

                // ===== Count Absents =====
                String sql2 =
                        "SELECT COUNT(*) FROM "+
                                "attendance "+
                                "WHERE roll_no=?";

                PreparedStatement ps2 =
                        con.prepareStatement(sql2);

                ps2.setString(1,rollNo);

                ResultSet rs2 =
                        ps2.executeQuery();

                int absentDays = 0;

                if(rs2.next()){

                    absentDays =
                            rs2.getInt(1);
                }

                // ===== Total Days (3 Months) =====
                LocalDate today =
                        LocalDate.now();

                LocalDate start =
                        today.minusMonths(3);

                long totalDays =
                        ChronoUnit.DAYS.between(
                                start,today
                        );

                int presentDays =
                        (int)totalDays - absentDays;

                double percent =
                        (presentDays*100.0)/totalDays;

                // ===== UI DISPLAY =====

                addBox(
                        "Total School Days",
                        String.valueOf(totalDays),
                        200,150
                );

                addBox(
                        "Present Days",
                        String.valueOf(presentDays),
                        450,150
                );

                addBox(
                        "Absent Days",
                        String.valueOf(absentDays),
                        700,150
                );

                addBox(
                        "Attendance %",
                        String.format("%.2f",percent)+"%",
                        450,300
                );

                con.close();

            }catch(Exception ex){

                ex.printStackTrace();
            }
        }

        // ===== UI BOX METHOD =====
        void addBox(
                String title,
                String value,
                int x,int y
        ){

            JPanel card =
                    new JPanel(null);

            card.setBounds(x,y,200,120);
            card.setBorder(
                    BorderFactory
                            .createLineBorder(Color.GRAY)
            );

            JLabel t =
                    new JLabel(
                            title,
                            SwingConstants.CENTER
                    );

            t.setBounds(0,10,200,30);
            t.setFont(
                    new Font(
                            "Segoe UI",
                            Font.BOLD,14
                    )
            );

            JLabel v =
                    new JLabel(
                            value,
                            SwingConstants.CENTER
                    );

            v.setBounds(0,60,200,30);
            v.setFont(
                    new Font(
                            "Segoe UI",
                            Font.BOLD,18
                    )
            );

            card.add(t);
            card.add(v);

            add(card);
        }
    }







//  = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    // NOTICE PANEL
    class StudentNoticePanel extends JPanel {

        StudentNoticePanel(){

            setLayout(null);
            setBounds(0,0,1120,897);
            setBackground(Color.WHITE);

            JLabel title =
                    new JLabel(
                            "SCHOOL NOTICES",
                            SwingConstants.CENTER
                    );

            title.setBounds(350,20,400,40);
            title.setFont(
                    new Font("Segoe UI",Font.BOLD,26)
            );
            add(title);

            // ===== Scroll Container Panel =====
            JPanel container = new JPanel();
            container.setLayout(null);
            container.setBackground(Color.WHITE);

            JScrollPane scroll =
                    new JScrollPane(container);

            scroll.setBounds(50,80,1000,750);
            add(scroll);

            int y = 20;

            try{

                Connection con =
                        Dbmanage.getConnection();

                String sql =
                        "SELECT * FROM notices " +
                                "ORDER BY notice_id DESC";

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery();

                while(rs.next()){

                    JPanel noticeCard =
                            new JPanel(null);

                    noticeCard.setBounds(
                            50,y,900,300
                    );

                    noticeCard.setBorder(
                            BorderFactory
                                    .createLineBorder(Color.GRAY)
                    );

                    container.add(noticeCard);

                    // ===== Image 1 =====
                    String img1 =
                            rs.getString(
                                    "image1_path"
                            );

                    if(img1!=null &&
                            !img1.isEmpty()){

                        ImageIcon icon =
                                new ImageIcon(img1);

                        Image scaled =
                                icon.getImage()
                                        .getScaledInstance(
                                                200,150,
                                                Image.SCALE_SMOOTH
                                        );

                        JLabel pic1 =
                                new JLabel(
                                        new ImageIcon(
                                                scaled
                                        )
                                );

                        pic1.setBounds(
                                50,30,200,150
                        );

                        noticeCard.add(pic1);
                    }

                    // ===== Image 2 =====
                    String img2 =
                            rs.getString(
                                    "image2_path"
                            );

                    if(img2!=null &&
                            !img2.isEmpty()){

                        ImageIcon icon =
                                new ImageIcon(img2);

                        Image scaled =
                                icon.getImage()
                                        .getScaledInstance(
                                                200,150,
                                                Image.SCALE_SMOOTH
                                        );

                        JLabel pic2 =
                                new JLabel(
                                        new ImageIcon(
                                                scaled
                                        )
                                );

                        pic2.setBounds(
                                300,30,200,150
                        );

                        noticeCard.add(pic2);
                    }

                    // ===== Message =====
                    JTextArea msg =
                            new JTextArea(
                                    rs.getString(
                                            "message"
                                    )
                            );

                    msg.setBounds(
                            50,200,700,100
                    );

                    msg.setLineWrap(true);
                    msg.setWrapStyleWord(true);
                    msg.setEditable(false);

                    noticeCard.add(msg);

                    // ===== Date =====
                    JLabel date =
                            new JLabel(
                                    "Posted: "+
                                            rs.getString(
                                                    "posted_date"
                                            )
                            );

                    date.setBounds(
                            600,20,250,30
                    );

                    noticeCard.add(date);

                    y += 320;
                }

                container.setPreferredSize(
                        new Dimension(
                                900,y+50
                        )
                );

                con.close();

            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }







    //  = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    // ====================================
    // ================= RESULT PANEL =================
    class ResultPanel extends JPanel {

        ResultPanel(String username){

            setLayout(null);
            setBackground(Color.WHITE);
            setBounds(0,0,1120,897);

            // =========================================
            // ===== TITLE ==============================
            // =========================================
            JLabel title = new JLabel(
                    "ANNUAL RESULT",
                    SwingConstants.CENTER
            );
            title.setBounds(350,30,400,40);
            title.setFont(
                    new Font("Arial",Font.BOLD,26)
            );
            add(title);

            // =========================================
            // ===== STUDENT INFO FETCH ================
            // =========================================

            int rollNo = 0;
            String name = "";
            String studentClass = "";

            try{

                Connection con =
                        Dbmanage.getConnection();

                String sql =
                        "SELECT roll_no,name,class " +
                                "FROM students WHERE username=?";

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ps.setString(1,username);

                ResultSet rs = ps.executeQuery();

                if(rs.next()){

                    rollNo = rs.getInt("roll_no");
                    name   = rs.getString("name");
                    studentClass =
                            rs.getString("class");
                }

                con.close();

            }catch(Exception e){
                e.printStackTrace();
            }

            // =========================================
            // ===== STUDENT INFO LABELS ===============
            // =========================================

            JLabel nameLbl =
                    new JLabel("Name : "+name);
            nameLbl.setBounds(80,100,400,30);
            nameLbl.setFont(
                    new Font("Segoe UI",
                            Font.BOLD,18)
            );
            add(nameLbl);

            JLabel classLbl =
                    new JLabel(
                            "Class : "+
                                    studentClass+
                                    " (Previous Year)"
                    );
            classLbl.setBounds(80,140,500,30);
            classLbl.setFont(
                    new Font("Segoe UI",
                            Font.BOLD,18)
            );
            add(classLbl);

            // =========================================
            // ===== TABLE STRUCTURE ===================
            // =========================================

            String[] columns = {
                    "Subject",
                    "Marks Obtained",
                    "Max Marks"
            };

            DefaultTableModel model =
                    new DefaultTableModel(columns,0);

            JTable table =
                    new JTable(model);

            table.setFont(
                    new Font("Segoe UI",
                            Font.PLAIN,14)
            );

            table.setRowHeight(25);

            JScrollPane scroll =
                    new JScrollPane(table);

            scroll.setBounds(80,200,900,250);
            add(scroll);

            // =========================================
            // ===== RESULT FETCH ======================
            // =========================================

            int total = 0;
            int maxTotal = 0;

            try{

                Connection con =
                        Dbmanage.getConnection();

                String sql =
                        "SELECT subject,marks_obtained,max_marks " +
                                "FROM result WHERE roll_no=?";

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ps.setInt(1,rollNo);

                ResultSet rs = ps.executeQuery();

                while(rs.next()){

                    int marks =
                            rs.getInt(
                                    "marks_obtained"
                            );

                    int max =
                            rs.getInt(
                                    "max_marks"
                            );

                    total += marks;
                    maxTotal += max;

                    model.addRow(new Object[]{

                            rs.getString("subject"),
                            marks,
                            max
                    });
                }

                con.close();

            }catch(Exception e){
                e.printStackTrace();
            }

            // =========================================
            // ===== PERCENTAGE + GRADE ================
            // =========================================

            double percent = 0;

            if(maxTotal>0){
                percent =
                        (total*100.0)/maxTotal;
            }

            String grade;

            if(percent>=90) grade="A+";
            else if(percent>=75) grade="A";
            else if(percent>=60) grade="B";
            else if(percent>=40) grade="C";
            else grade="FAIL";

            // =========================================
            // ===== SUMMARY ===========================
            // =========================================

            JLabel totalLbl =
                    new JLabel(
                            "Total Marks : "+
                                    total+
                                    " / "+
                                    maxTotal
                    );

            totalLbl.setBounds(80,500,300,30);
            totalLbl.setFont(
                    new Font("Segoe UI",
                            Font.BOLD,18)
            );
            add(totalLbl);

            JLabel percentLbl =
                    new JLabel(
                            "Percentage : "+
                                    String.format(
                                            "%.2f",
                                            percent
                                    )+"%"
                    );

            percentLbl.setBounds(80,540,300,30);
            percentLbl.setFont(
                    new Font("Segoe UI",
                            Font.BOLD,18)
            );
            add(percentLbl);

            JLabel gradeLbl =
                    new JLabel("Grade : "+grade);

            gradeLbl.setBounds(80,580,200,30);
            gradeLbl.setFont(
                    new Font("Segoe UI",
                            Font.BOLD,18)
            );
            gradeLbl.setForeground(
                    new Color(0,128,0)
            );
            add(gradeLbl);
        }
    }













        //  = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    // ================= FEES PANEL =================
    class FeesPanel extends JPanel {

        FeesPanel(String username) {

            setLayout(null);
            setBackground(Color.WHITE);
            setBounds(0,0,1120,897);

            JLabel title = new JLabel(
                    "FEES DETAILS",
                    SwingConstants.CENTER
            );
            title.setBounds(350,30,400,40);
            title.setFont(new Font("Arial",Font.BOLD,26));
            add(title);

            JPanel card = new JPanel(null);
            card.setBounds(250,120,600,400);
            card.setBackground(new Color(245,245,245));
            card.setBorder(
                    BorderFactory.createLineBorder(Color.GRAY,2)
            );
            add(card);

            int y=50;

            JLabel classVal   = addValue(card,300,y); y+=60;
            JLabel totalVal   = addValue(card,300,y); y+=60;
            JLabel paidVal    = addValue(card,300,y); y+=60;
            JLabel pendingVal = addValue(card,300,y); y+=60;
            JLabel dueVal     = addValue(card,300,y); y+=60;

            JLabel status = new JLabel();
            status.setBounds(300,y,200,30);
            status.setFont(new Font("Segoe UI",Font.BOLD,16));
            card.add(status);

            // Labels
            y=50;
            addLabel(card,"Class:",80,y); y+=60;
            addLabel(card,"Total Fees:",80,y); y+=60;
            addLabel(card,"Paid Fees:",80,y); y+=60;
            addLabel(card,"Pending Fees:",80,y); y+=60;
            addLabel(card,"Due Date:",80,y); y+=60;
            addLabel(card,"Status:",80,y);

            // ================= DB FETCH =================
            try{

                Connection con = Dbmanage.getConnection();

                String sql =
                        "SELECT s.class, f.total_fees, f.fees_paid, f.due_date " +
                                "FROM students s " +
                                "JOIN fees f ON s.roll_no = f.roll_no " +
                                "WHERE s.username=?";

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ps.setString(1,username);

                ResultSet rs = ps.executeQuery();

                if(rs.next()){

                    double total = rs.getDouble("total_fees");
                    double paid  = rs.getDouble("fees_paid");
                    double pending = total - paid;

                    classVal.setText(rs.getString("class"));
                    totalVal.setText("₹ "+total);
                    paidVal.setText("₹ "+paid);
                    pendingVal.setText("₹ "+pending);
                    dueVal.setText(rs.getString("due_date"));

                    if(pending<=0){
                        status.setText("PAID");
                        status.setForeground(new Color(0,128,0));
                    }else{
                        status.setText("PENDING");
                        status.setForeground(Color.RED);
                    }
                }

                con.close();

            }catch(Exception e){
                e.printStackTrace();
            }
        }

        // ===== Helpers =====

        void addLabel(JPanel p,String t,int x,int y){
            JLabel l=new JLabel(t);
            l.setBounds(x,y,200,30);
            l.setFont(new Font("Segoe UI",Font.BOLD,16));
            p.add(l);
        }

        JLabel addValue(JPanel p,int x,int y){
            JLabel v=new JLabel();
            v.setBounds(x,y,250,30);
            v.setFont(new Font("Segoe UI",Font.PLAIN,16));
            p.add(v);
            return v;
        }
    }
















    //  = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    // ================= ATTENDANCE PANEL =================
    class AttendancePanel extends JPanel {

        AttendancePanel() {

            setLayout(null);
            setBackground(Color.WHITE);

            // Content area full cover
            setBounds(0, 0, 1120, 897);

            // ===== Title =====
            JLabel title = new JLabel(
                    "STUDENT ATTENDANCE",
                    SwingConstants.CENTER
            );
            title.setBounds(350, 30, 400, 40);
            title.setFont(new Font("Arial", Font.BOLD, 26));
            add(title);

            // ===== Overall Attendance =====
            JLabel overall = new JLabel("Overall Attendance: 87%");
            overall.setBounds(50, 100, 300, 30);
            overall.setFont(new Font("Segoe UI", Font.BOLD, 18));
            add(overall);

            // ===== Table Data (Dummy) =====
            String[] columns = {
                    "Subject",
                    "Total Classes",
                    "Present",
                    "Absent",
                    "Percentage"
            };

            String[][] data = {
                    {"Mathematics", "120", "110", "10", "91%"},
                    {"Science", "115", "100", "15", "87%"},
                    {"English", "110", "96", "14", "87%"},
                    {"History", "105", "90", "15", "85%"},
                    {"Computer", "98", "92", "6", "94%"}
            };

            JTable table = new JTable(data, columns);

            table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            table.setRowHeight(25);

            JScrollPane scroll = new JScrollPane(table);
            scroll.setBounds(50, 160, 1000, 400);

            add(scroll);
        }
    }







    //  = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    // profile
    //  =  = = = = = = = = = = = = =
    class ProfilePanel extends JPanel {

        ProfilePanel(String username) {

            setLayout(null);
            setBackground(Color.WHITE);
            setBounds(0, 0, 1120, 897);

            JLabel title = new JLabel(
                    "STUDENT PROFILE",
                    SwingConstants.CENTER
            );
            title.setBounds(350, 30, 400, 40);
            title.setFont(new Font("Arial", Font.BOLD, 26));
            add(title);

            // =================================================
            // ===== PHOTO LABEL (Preview Area) ================
            // =================================================
            JLabel photo = new JLabel();
            photo.setBounds(80, 120, 150, 150);
            photo.setBorder(
                    BorderFactory.createLineBorder(Color.BLACK, 2)
            );
            add(photo);

            // ===== Value Labels =====
            int y = 130;

            JLabel nameVal   = addValueLabel(450,y); y+=50;
            JLabel rollVal   = addValueLabel(450,y); y+=50;
            JLabel classVal  = addValueLabel(450,y); y+=50;
            JLabel dobVal    = addValueLabel(450,y); y+=50;
            JLabel phoneVal  = addValueLabel(450,y); y+=50;
            JLabel addrVal   = addValueLabel(450,y); y+=50;
            JLabel parentVal = addValueLabel(450,y); y+=50;
            JLabel bloodVal  = addValueLabel(450,y); y+=50;
            JLabel groupVal  = addValueLabel(450,y);

            // ===== Label Names =====
            y = 130;

            addLabel("Name:",300,y); y+=50;
            addLabel("Roll No:",300,y); y+=50;
            addLabel("Class:",300,y); y+=50;
            addLabel("DOB:",300,y); y+=50;
            addLabel("Phone:",300,y); y+=50;
            addLabel("Address:",300,y); y+=50;
            addLabel("Parent Name:",300,y); y+=50;
            addLabel("Blood Group:",300,y); y+=50;
            addLabel("School Group:",300,y);

            // =================================================
            // ============ DATABASE FETCH ======================
            // =================================================
            try {

                Connection con = Dbmanage.getConnection();

                String sql =
                        "SELECT * FROM students WHERE username=?";

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ps.setString(1, username);

                ResultSet rs = ps.executeQuery();

                if(rs.next()) {

                    // ===== TEXT DATA =====
                    nameVal.setText(rs.getString("name"));
                    rollVal.setText(rs.getString("roll_no"));
                    classVal.setText(rs.getString("class"));
                    dobVal.setText(rs.getString("dob"));
                    phoneVal.setText(rs.getString("phone"));
                    addrVal.setText(rs.getString("address"));
                    parentVal.setText(rs.getString("parent_name"));
                    bloodVal.setText(rs.getString("blood_group"));
                    groupVal.setText(rs.getString("school_group"));

                    // =================================================
                    // ===== PHOTO FETCH ================================
                    // =================================================

                    String photoPath =
                            rs.getString("photo_path");

                    if(photoPath != null &&
                            !photoPath.isEmpty()) {

                        ImageIcon img =
                                new ImageIcon(photoPath);

                        Image scaled =
                                img.getImage()
                                        .getScaledInstance(
                                                150,150,
                                                Image.SCALE_SMOOTH
                                        );

                        photo.setIcon(
                                new ImageIcon(scaled)
                        );
                    }
                    else {

                        // Default text if no photo
                        photo.setText("No Photo");
                        photo.setHorizontalAlignment(
                                SwingConstants.CENTER
                        );
                    }
                }
                else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Profile Data Not Found"
                    );
                }

                con.close();

            } catch(Exception e) {

                e.printStackTrace();

                JOptionPane.showMessageDialog(
                        this,
                        "Database Error"
                );
            }
        }

        // =====================================================
        // ===== Helpers =======================================
        // =====================================================

        void addLabel(String text,int x,int y){

            JLabel lbl = new JLabel(text);
            lbl.setBounds(x,y,150,30);
            lbl.setFont(
                    new Font("Segoe UI",Font.BOLD,16)
            );
            add(lbl);
        }

        JLabel addValueLabel(int x,int y){

            JLabel val = new JLabel();

            val.setBounds(x,y,300,30);
            val.setFont(
                    new Font("Segoe UI",Font.PLAIN,16)
            );
            add(val);

            return val;
        }
    }
//  = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =










    // ===== Main =====
    public static void main(String[] args) {
        SwingUtilities.invokeLater(studentproject::new);

    }
}





