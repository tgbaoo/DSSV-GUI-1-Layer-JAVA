import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DSSV extends JFrame implements ActionListener {
    JPanel pnTableDSSV, pnTTDSSV;
    JLabel lbTTSV, lbKQTK, lbMSSV, lbHo, lbTen, lbGmail, lbDob, lbTimKiem, lbLCKTK, lbNTKTK;
    JTextField txtMSSV, txtHo, txtTen, txtGmail, txtDob, txtKhoaTk;
    JButton btnDoc, btnThem, btnXoa, btnSua, btnTimKiem, btnXuat, btnThongKe;
    JComboBox<String> cbbDSKhoaTK;
    public static ArrayList<SinhVien> dssv = new ArrayList<SinhVien>();
    JTable tblDSSV;
    DefaultTableModel model;
    Vector<String> header, row, row1, row2;
    Connection conn = null;
    Statement st = null;
    ResultSet rs = null;

    DSSV() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setTitle("Quản lý danh sách sinh viên");
        this.setLayout(null);

        pnTableDSSV = new JPanel();
        pnTTDSSV = new JPanel();
        pnTableDSSV.setLayout(new GridLayout(3, 1, 0, -300));
        pnTableDSSV.setBounds(320, 0, 1200, 400);
        pnTTDSSV.setLayout(null);
        pnTTDSSV.setBounds(200, 415, 1537, 600);

        setTableSinhVien();
        setInput();

        // add Components
        this.add(pnTTDSSV);

        // add Label
        pnTTDSSV.add(lbMSSV);
        pnTTDSSV.add(lbHo);
        pnTTDSSV.add(lbTen);
        pnTTDSSV.add(lbGmail);
        pnTTDSSV.add(lbDob);
        pnTTDSSV.add(lbTimKiem);
        pnTTDSSV.add(lbLCKTK);
        pnTTDSSV.add(lbNTKTK);

        // add JTextField
        pnTTDSSV.add(txtMSSV);
        pnTTDSSV.add(txtHo);
        pnTTDSSV.add(txtTen);
        pnTTDSSV.add(txtGmail);
        pnTTDSSV.add(txtDob);

        // add JButton
        pnTTDSSV.add(btnDoc);
        pnTTDSSV.add(btnThem);
        pnTTDSSV.add(btnSua);
        pnTTDSSV.add(btnXoa);

        this.setVisible(true);

        tblDSSV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDSSVMouseClicked(evt);
            }
        });
    }


    public void tblDSSVMouseClicked(java.awt.event.MouseEvent evt) {
        int i = tblDSSV.getSelectedRow();
        txtMSSV.setText(tblDSSV.getModel().getValueAt(i, 0).toString());
        txtHo.setText(tblDSSV.getModel().getValueAt(i, 1).toString());
        txtTen.setText(tblDSSV.getModel().getValueAt(i, 2).toString());
        txtGmail.setText(tblDSSV.getModel().getValueAt(i, 3).toString());
        txtDob.setText(tblDSSV.getModel().getValueAt(i, 4).toString());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnDoc) {
            try {
                dssv.clear();
                String userName = "root";
                String password = "";
                String url = "jdbc:mysql://localhost:3306/dssv";
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(url, userName, password);
                String query = "select * from sinhvien";
                st = conn.createStatement();
                rs = st.executeQuery(query);
                while (rs.next()) {
                    SinhVien sv = new SinhVien();
                    sv.setMSSV(rs.getString(1));
                    sv.setHo(rs.getString(2));
                    sv.setTen(rs.getString(3));
                    sv.setGmail(rs.getString(4));
                    sv.setDob(rs.getString(5));
                    dssv.add(sv);
                }
                header = new Vector<String>();
                header.add("MSSV");
                header.add("Ho");
                header.add("Ten");
                header.add("Gmail");
                header.add("Ngay sinh");
                model = new DefaultTableModel(header, 0);
                for (SinhVien sv : dssv) {
                    Vector<String> row = new Vector<String>();
                    row.add(sv.getMSSV());
                    row.add(sv.getHo());
                    row.add(sv.getTen());
                    row.add(sv.getGmail());
                    row.add(sv.getDob());
                    model.addRow(row);
                    tblDSSV.setModel(model);
                }
            } catch (Exception e1) {
                System.out.println(e1.getMessage());
            }
        } else if (e.getSource() == btnThem) {
            String check = txtMSSV.getText();
            if (checkMa(check) == 0) {
                JOptionPane.showMessageDialog(null, "MSSV bi trung", "Loi", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    SinhVien sv = new SinhVien();
                    sv.setMSSV(txtMSSV.getText());
                    sv.setHo(txtHo.getText());
                    sv.setTen(txtTen.getText());
                    sv.setGmail(txtGmail.getText());
                    sv.setDob(txtDob.getText());
                    dssv.add(sv);
                    String userName = "root";
                    String password = "";
                    String url = "jdbc:mysql://localhost:3306/dssv";
                    Class.forName("com.mysql.jdbc.Driver");
                    conn = DriverManager.getConnection(url, userName, password);
                    String query = "insert into sinhvien values (" + "'" + sv.getMSSV() + "'"
                            + "," + "N'" + sv.getHo() + "'" + "," + "N'" + sv.getTen() + "'" + "," + "'"
                            + sv.getGmail() + "'" + "," + "'" + sv.getDob() + "'" + ")";
                    st = conn.createStatement();
                    st.executeUpdate(query);

                    if (model.getRowCount() == 0) {
                        model = new DefaultTableModel(header, 0);
                    }
                    Vector<String> row = new Vector<String>();
                    row.add(sv.getMSSV());
                    row.add(sv.getHo());
                    row.add(sv.getTen());
                    row.add(sv.getGmail());
                    row.add(sv.getDob());
                    model.addRow(row);
                    tblDSSV.setModel(model);
                } catch (Exception e1) {
                    System.out.println(e1.getMessage());
                }
            }
        } else if (e.getSource() == btnSua) {
            int i = tblDSSV.getSelectedRow();
            if (i >= 0) {

                SinhVien sv = new SinhVien();
                SinhVien svBak = dssv.set(i, sv);
                sv.setMSSV(txtMSSV.getText());
                sv.setHo(txtHo.getText());
                sv.setTen(txtTen.getText());
                sv.setGmail(txtGmail.getText());
                sv.setDob(txtDob.getText());
                try {
                    String userName = "root";
                    String password = "";
                    String url = "jdbc:mysql://localhost:3306/dssv";
                    Class.forName("com.mysql.jdbc.Driver");
                    conn = DriverManager.getConnection(url, userName, password);
                    String query = "update sinhvien set " + "MSSV=" + "'" + sv.getMSSV() + "'" +
                            ",Ho=" + "N'" + sv.getHo() + "'" + ",Ten=" + "N'" + sv.getTen() + "'" + ",Gmail=" + "'"
                            + sv.getGmail() + "'" + ",DoB=" + "'" + sv.getDob() + "'" + " " + "where MSSV='"
                            + svBak.getMSSV() + "'";
                    st = conn.createStatement();
                    st.executeUpdate(query);

                    model.setValueAt(sv.getMSSV(), i, 0);
                    model.setValueAt(sv.getHo(), i, 1);
                    model.setValueAt(sv.getTen(), i, 2);
                    model.setValueAt(sv.getGmail(), i, 3);
                    model.setValueAt(sv.getDob(), i, 4);
                    tblDSSV.setModel(model);
                } catch (Exception e1) {
                    System.out.println(e1.getMessage());
                }
            }
        } else if (e.getSource() == btnXoa) {
            String mssv = txtMSSV.getText();
            int i = tblDSSV.getSelectedRow();
            if (i >= 0) {
                try {
                    String userName = "root";
                    String password = "";
                    String url = "jdbc:mysql://localhost:3306/dssv";
                    Class.forName("com.mysql.jdbc.Driver");
                    conn = DriverManager.getConnection(url, userName, password);
                    String query = "delete from sinhvien where MSSV='" + mssv + "'";
                    st = conn.createStatement();
                    st.executeUpdate(query);
                    dssv.remove(i);
                    model.removeRow(i);
                    tblDSSV.setModel(model);
                } catch (Exception e1) {
                    System.out.println(e1.getMessage());
                }
            }
        }
    }

    public int checkMa(String mssv) {
        for (SinhVien sv : dssv) {
            if (sv.getMSSV().equals(mssv))
                return 0;
        }
        return 1;
    }

    public void setTableSinhVien() {
        // Label TTSV
        lbTTSV = new JLabel("Thong tin sinh vien");
        lbTTSV.setFont(new Font("Arial", Font.BOLD, 30));
        lbTTSV.setHorizontalAlignment(SwingConstants.CENTER);
        lbTTSV.setVerticalAlignment(SwingConstants.TOP);
        // Label hien thi ket qua tim kiem
        lbKQTK = new JLabel();
        lbKQTK.setHorizontalAlignment(SwingConstants.CENTER);
        lbKQTK.setVerticalAlignment(SwingConstants.TOP);
        // set up JTABLE
        tblDSSV = new JTable();
        JScrollPane pane = new JScrollPane(tblDSSV);
        pane.setAutoscrolls(true);
        tblDSSV.setRowHeight(20);
        tblDSSV.setFont(new Font(null, 0, 13));
        tblDSSV.setBackground(Color.LIGHT_GRAY);
        // tblDSSV.addMouseListener(this);
        tblDSSV.setDefaultEditor(Object.class, null);
        this.add(pnTableDSSV);
        pnTableDSSV.add(lbTTSV);
        pnTableDSSV.add(lbKQTK);
        pnTableDSSV.add(pane);

    }

    public void setInput() {
        // Label ma so sinh vien
        lbMSSV = new JLabel("MSSV: ");
        lbMSSV.setFont(new Font("Arial", Font.BOLD, 20));
        lbMSSV.setBounds(120, 0, 150, 100);
        // Label Ho
        lbHo = new JLabel("Ho: ");
        lbHo.setFont(new Font("Arial", Font.BOLD, 20));
        lbHo.setBounds(120, 50, 150, 100);
        // Label Ten
        lbTen = new JLabel("Ten: ");
        lbTen.setFont(new Font("Arial", Font.BOLD, 20));
        lbTen.setBounds(120, 100, 150, 100);
        // Label Gmail
        lbGmail = new JLabel("Gmail: ");
        lbGmail.setFont(new Font("Arial", Font.BOLD, 20));
        lbGmail.setBounds(120, 150, 150, 100);
        // Label Dob
        lbDob = new JLabel("Date of Birth: ");
        lbDob.setFont(new Font("Arial", Font.BOLD, 20));
        lbDob.setBounds(120, 200, 150, 100);
        // Label Tim Kiem
        lbTimKiem = new JLabel("Tim Kiem: ");
        lbTimKiem.setFont(new Font("Arial", Font.BOLD, 25));
        lbTimKiem.setBounds(870, 0, 200, 100);
        // Label Lua Chon Khoa Tim Kiem
        lbLCKTK = new JLabel("Lua Chon Khoa Tim Kiem: ");
        lbLCKTK.setFont(new Font("Arial", Font.BOLD, 20));
        lbLCKTK.setBounds(670, 50, 300, 100);
        // Label Tu khoa tim kiem
        lbNTKTK = new JLabel("Nhap tu khoa tim kiem: ");
        lbNTKTK.setFont(new Font("Arial", Font.BOLD, 20));
        lbNTKTK.setBounds(670, 100, 300, 100);

        // ----------------------> JTEXTFIELD <---------------------------------
        // JTF MSSV
        txtMSSV = new JTextField();
        txtMSSV.setBounds(270, 30, 200, 35);
        txtMSSV.setFont(new Font("Arial", Font.PLAIN, 15));
        // JTF Ho
        txtHo = new JTextField();
        txtHo.setBounds(270, 80, 200, 35);
        txtHo.setFont(new Font("Arial", Font.PLAIN, 15));
        // JTF Ten
        txtTen = new JTextField();
        txtTen.setBounds(270, 130, 200, 35);
        txtTen.setFont(new Font("Arial", Font.PLAIN, 15));
        // JTF Gmail
        txtGmail = new JTextField();
        txtGmail.setBounds(270, 180, 200, 35);
        txtGmail.setFont(new Font("Arial", Font.PLAIN, 15));
        // JTF ngay sinh
        txtDob = new JTextField();
        txtDob.setBounds(270, 230, 200, 35);
        txtDob.setFont(new Font("Arial", Font.PLAIN, 15));
        // JTF Khoa tim kiem
        txtKhoaTk = new JTextField();
        txtKhoaTk.setBounds(920, 210, 200, 35);
        // ---------------------> JButton <------------------------
        // btn doc
        btnDoc = new JButton("Doc DS");
        btnDoc.setFont(new Font("Arial", Font.BOLD, 15));
        btnDoc.setBounds(120, 330, 100, 40);
        btnDoc.setBackground(Color.cyan);
        btnDoc.setBorder(new RoundedBorder(10));
        btnDoc.addActionListener(this);

        // btn them
        btnThem = new JButton("Them");
        btnThem.setFont(new Font("Arial", Font.BOLD, 15));
        btnThem.setBounds(230, 330, 100, 40);
        btnThem.setBackground(Color.cyan);
        btnThem.setBorder(new RoundedBorder(10));
        btnThem.addActionListener(this);

        // btn sua
        btnSua = new JButton("Sua");
        btnSua.setFont(new Font("Arial", Font.BOLD, 15));
        btnSua.setBounds(340, 330, 100, 40);
        btnSua.setBackground(Color.cyan);
        btnSua.setBorder(new RoundedBorder(10));
        btnSua.addActionListener(this);

        // btn xoa
        btnXoa = new JButton("Xoa");
        btnXoa.setFont(new Font("Arial", Font.BOLD, 15));
        btnXoa.setBounds(450, 330, 100, 40);
        btnXoa.setBackground(Color.cyan);
        btnXoa.setBorder(new RoundedBorder(10));
        btnXoa.addActionListener(this);
        // btn tim kiem
        btnTimKiem = new JButton("Tim Kiem");
        btnTimKiem.setFont(new Font("Arial", Font.BOLD, 15));
        btnTimKiem.setBounds(450, 330, 100, 40);
        btnTimKiem.setBackground(Color.cyan);
        btnTimKiem.setBorder(new RoundedBorder(10));
        btnTimKiem.addActionListener(this);
        // btn Thong Ke
        btnThongKe = new JButton("Thong Ke");
        btnThongKe.setFont(new Font("Arial", Font.BOLD, 20));
        btnThongKe.setBounds(670, 265, 150, 55);
        btnThongKe.setBackground(Color.cyan);
        btnThongKe.setBorder(new RoundedBorder(10));
        btnThongKe.addActionListener(this);

        // setup cbb
        String[] dsKhoaTK = { "", "MSSV", "Ho", "Ten", "Gmail", "Ngay Sinh" };
        cbbDSKhoaTK = new JComboBox<>(dsKhoaTK);
        cbbDSKhoaTK.setFont(new Font("Arial", Font.BOLD, 13));
        cbbDSKhoaTK.setBounds(920, 160, 120, 35);

    }
}
