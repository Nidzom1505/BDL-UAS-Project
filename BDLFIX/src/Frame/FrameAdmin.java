/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Frame;

import Koneksi.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.*;
import java.sql.*;
import java.util.*;
import java.util.logging.*;
import javax.swing.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Nidzzz
 */
public class FrameAdmin extends javax.swing.JFrame {

    Connection conn;
    PreparedStatement pstmt;
    ResultSet rs;
    Statement stmt;

    /**
     * Creates new form FrameAdmin
     */
    public FrameAdmin() {
        initComponents();
        allConstructor();
        tfNoEdit();
    }

    public void allConstructor() {
        tableModel();
        idAutoSupply();
        tabelSupplier();
        idAutoSupplier();
        tabelLaporan();
        tabelKurir();
        idAutoKurir();
        tabelKaryawan();
        idAutoKaryawan();
        tabelBarang();
        idAutoBarang();
    }

    private void tfNoEdit() {
        txttanggal.setEditable(false);
        txtnosup.setEditable(false);
        tfIdSup.setEditable(false);
        tfIdSup.setEditable(false);
        tfIdKur.setEditable(false);
        tfIdKar.setEditable(false);
        tfKodeBar.setEditable(false);
        jMenuBar1.setVisible(false);
    }

    public void tabelBarang() {
        DefaultTableModel tblbrg = new DefaultTableModel(new String[]{"KODE", "NAMA PRODUK", "HARGA", "STOK"}, 0);
        String sql = "SELECT * FROM barang"; // Query SQL

        try {
            konek k = new konek();
            conn = k.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                tblbrg.addRow(new Object[]{
                    rs.getString("kd_barang"),
                    rs.getString("nama_barang"),
                    rs.getString("harga"),
                    rs.getString("stok"),});
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(FrameAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        tabelBar.setModel(tblbrg);
    }

    public void bersihBarang() {
        idAutoBarang();
        tfNamaBar.setText("");
        tfHargaBar.setText("");
        tfStokBar.setText("");
    }

    public final void idAutoBarang() {
        String sql = "SELECT MAX(CAST(SUBSTRING(kd_barang FROM 4 FOR 4) AS INTEGER)) AS no "
                + "FROM barang "
                + "WHERE kd_barang ~ '^BRG[0-9]{4}$'";
        try {
            konek k = new konek();
            conn = k.getConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                if (rs.getString("no") == null) {
                    tfKodeBar.setText("BRG0001");
                } else {
                    int set_id = rs.getInt("no") + 1;
                    String no = String.format("%04d", set_id);
                    tfKodeBar.setText("BRG" + no);
                }
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(FrameAdmin.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat membuat ID barang.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public final void idAutoKaryawan() {//frame karyawan
        String sql = "SELECT MAX(CAST(SUBSTRING(id_karyawan FROM 4 FOR 4) AS INTEGER)) AS no "
                + "FROM karyawan "
                + "WHERE id_karyawan ~ '^KRW[0-9]{4}$'";
        try {
            konek k = new konek();
            conn = k.getConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                if (rs.getString("no") == null) {
                    tfIdKar.setText("KRW0001");
                } else {
                    int set_id = rs.getInt("no") + 1;
                    String no = String.format("%04d", set_id);
                    tfIdKar.setText("KRW" + no);
                }
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(FrameAdmin.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat membuat ID Karyawan.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void bersihKaryawan() {//frame karyawan
        idAutoKaryawan();
        tfNamaKar.setText(null);
        tfTelpKar.setText(null);
        tfAlamatKar.setText(null);
        buttonGroup1.clearSelection();
        cbJabatan.setSelectedIndex(0);
        tfEmailKar.setText(null);
    }

    public void tabelKaryawan() {//frame karyawan
        DefaultTableModel tblKrw = new DefaultTableModel(new String[]{"ID", "NAMA KARYAWAN", "TELEPON KARYAWAN", "JENIS KELAMIN", "ALAMAT", "ID JABATAN", "EMAIL"}, 0);
        String sql = "SELECT * FROM karyawan";

        try {
            konek k = new konek();
            conn = k.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                tblKrw.addRow(new Object[]{
                    rs.getString("id_karyawan"),
                    rs.getString("nama"),
                    rs.getString("telp"),
                    rs.getString("gender"),
                    rs.getString("alamat"),
                    rs.getString("id_jenis"),
                    rs.getString("email")
                });
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(FrameAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        tabelKar.setModel(tblKrw);
    }

    public void bersihKurir() {
        idAutoKurir();
        tfNamaKur.setText("");
        tfTelpKur.setText("");
        tfAlamatKur.setText("");
        buttonGroup2.clearSelection();
    }

    public final void idAutoKurir() {//frame kurir
        String sql = "SELECT MAX(CAST(SUBSTRING(id_kurir FROM 4 FOR 4) AS INTEGER)) AS no "
                + "FROM kurir "
                + "WHERE id_kurir ~ '^KRR[0-9]{4}$'";
        try {
            konek k = new konek();
            conn = k.getConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                if (rs.getString("no") == null) {
                    tfIdKur.setText("KRR0001");
                } else {
                    int set_id = rs.getInt("no") + 1;
                    String no = String.format("%04d", set_id);
                    tfIdKur.setText("KRR" + no);
                }
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(FrameAdmin.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat membuat ID barang.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void tabelKurir() {//frame kurir
        DefaultTableModel tblkrr = new DefaultTableModel(new String[]{"ID", "NAMA KURIR", "TELEPON KURIR", "JENIS KELAMIN", "ALAMAT"}, 0);
        String sql = "SELECT * FROM kurir";

        try {
            konek k = new konek();
            conn = k.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                tblkrr.addRow(new Object[]{
                    rs.getString("id_kurir"),
                    rs.getString("nama"),
                    rs.getString("telp"),
                    rs.getString("jenis_kelamin"),
                    rs.getString("alamat")
                });
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(FrameAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        tabelKur.setModel(tblkrr);
    }

    public void tabelLaporan() {//frame stat
        konek k = new konek();
        conn = k.getConnection();
        DefaultTableModel tblLap = new DefaultTableModel(
                new String[]{"NO PEMBELIAN", "ID KARYAWAN", "ID PELANGGAN", "TANGGAL", "TOTAL HARGA"}, 0);
        String sql = "SELECT * FROM pembelian";

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                tblLap.addRow(new Object[]{
                    rs.getString("no_pembelian"),
                    rs.getString("id_karyawan"),
                    rs.getString("id_pelanggan"),
                    rs.getDate("tanggal"),
                    rs.getInt("total_harga")
                });
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(FrameAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        tabelLap.setModel(tblLap);
    }

    public void tableModel() { // tabel semu frame supply
        DefaultTableModel model = new DefaultTableModel();

        tabelSupp.setModel(model);

        model.addColumn("No Supply");
        model.addColumn("id Supplier");
        model.addColumn("id karyawan");
        model.addColumn("kode barang");
        model.addColumn("nama barang");
        model.addColumn("jumlah");

        Date date = new Date();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");

        txttanggal.setText(s.format(date));
    }

    private void hitungTotalStok() {//menghitung jumlah stok frame supply
        int totalStok = 0;
        int jumlahBaris = tabelSupp.getRowCount();

        for (int i = 0; i < jumlahBaris; i++) {
            if (tabelSupp.getValueAt(i, 5) != null) {
                try {
                    int stok = Integer.parseInt(tabelSupp.getValueAt(i, 5).toString());
                    totalStok += stok;
                } catch (NumberFormatException e) {
                    System.out.println("Format stok salah di baris: " + i);
                }
            }
        }
        txttotal.setText("" + totalStok);
    }

    private void lookupBarang() {//memanggil data base tabel barang frmae supply
        String idBr = txtkodebrg.getText().trim();
        String sql = "SELECT * FROM barang WHERE kd_barang = ?";

        try {
            konek k = new konek();
            conn = k.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, idBr);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String nama = rs.getString("nama_barang");
                txtnmbrng.setText(nama);
            } else {
                JOptionPane.showMessageDialog(null, "Barang tidak ada", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void lookupKaryawan() {//memanggil tabel karyawan frame supply
        String idKyw = txtidkrywn.getText().toString().trim();
        String sql = "SELECT * FROM karyawan WHERE id_karyawan = ?";

        try {
            konek k = new konek();
            conn = k.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, idKyw);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String nama = rs.getString("nama");
                txtnamakyw.setText(nama);
            } else {
                txtnamakyw.setText("Anggota tidak terdaftar");
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void lookupSupplier() {//memanggil tabel supplier frmae supply
        String idKyw = txtidsup.getText().trim();
        String sql = "SELECT * FROM supplier WHERE id_supplier = ?";

        try {
            konek k = new konek();
            conn = k.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, idKyw);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String nama = rs.getString("nama_supplier");
                txtnamasup.setText(nama);
            } else {
                txtnamasup.setText("Anggota tidak terdaftar");
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public final void idAutoSupply() {//auto increment frame supply
        String sql = "SELECT MAX(CAST(SUBSTRING(no_supply FROM 4 FOR 4) AS INTEGER)) AS no "
                + "FROM supply "
                + "WHERE no_supply ~ '^S[0-9]{4}$'";
        try {
            conn = new konek().getConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                if (rs.getString("no") == null) {
                    txtnosup.setText("S0001");
                } else {
                    int set_id = rs.getInt("no") + 1;
                    String no = String.format("%04d", set_id);
                    txtnosup.setText("S" + no);
                }
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(FrameAdmin.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat membuat ID pembelian.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void kosongSupply() {//clear frame upply
        DefaultTableModel model = (DefaultTableModel) tabelSupp.getModel();

        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
        idAutoSupply();
        txtidsup.setText("");
        txtnamasup.setText("");
        txtidkrywn.setText("");
        txtnamakyw.setText("");
        txtkodebrg.setText("");
        txtnmbrng.setText("");
        txtjml.setText("");
        txttotal.setText("");
    }

    public void tabelSupplier() { //tabel frame supplier
        DefaultTableModel tblsp = new DefaultTableModel(new String[]{"ID", "NAMA SUPPLIER", "TELEPON SUPPLIER"}, 0);
        String sql = "SELECT * FROM supplier";

        try {
            konek k = new konek();
            conn = k.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                tblsp.addRow(new Object[]{
                    rs.getString("id_supplier"),
                    rs.getString("nama_supplier"),
                    rs.getString("telp_supplier")
                });
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(FrameAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        tabelSup.setModel(tblsp);
    }

    public void bersihSupplier() {//frame supplier
        idAutoSupplier();
        tfNamaSup.setText("");
        tfTelpSup.setText("");
    }

    public final void idAutoSupplier() {//frame supplier
        try {
            String sql = "SELECT MAX(CAST(SUBSTRING(id_supplier FROM 4 FOR 4) AS INTEGER)) AS no "
                    + "FROM supplier "
                    + "WHERE id_supplier ~ '^SP[0-9]{4}$'";
            konek k = new konek();
            conn = k.getConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                if (rs.getString("no") == null) {
                    tfIdSup.setText("SP0001");
                } else {
                    int set_id = rs.getInt("no") + 1;
                    String no = String.format("%04d", set_id);
                    tfIdSup.setText("SP" + no);
                }
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(FrameAdmin.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat membuat ID barang.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        PanelPusat = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        Dashboard = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        lblBack = new javax.swing.JLabel();
        lblKRW = new javax.swing.JLabel();
        lblBrg = new javax.swing.JLabel();
        lblSTAT = new javax.swing.JLabel();
        lblKRR = new javax.swing.JLabel();
        lblSUPP = new javax.swing.JLabel();
        lblSUP = new javax.swing.JLabel();
        Barang = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        tfKodeBar = new javax.swing.JTextField();
        tfNamaBar = new javax.swing.JTextField();
        tfHargaBar = new javax.swing.JTextField();
        tfStokBar = new javax.swing.JTextField();
        btnInsertBar = new javax.swing.JButton();
        btnUpdateBar = new javax.swing.JButton();
        btnDeleteBar = new javax.swing.JButton();
        btnClearBar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelBar = new javax.swing.JTable();
        tfCariBar = new javax.swing.JTextField();
        btnCetakBar = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        Stat = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelLap = new javax.swing.JTable();
        btnCetakKaryawan1 = new javax.swing.JButton();
        jLabel37 = new javax.swing.JLabel();
        Kurir = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        tfTelpKur = new javax.swing.JTextField();
        tfIdKur = new javax.swing.JTextField();
        tfNamaKur = new javax.swing.JTextField();
        tfAlamatKur = new javax.swing.JTextField();
        rbLKKur = new javax.swing.JRadioButton();
        rbPRKur = new javax.swing.JRadioButton();
        btnInsertKur = new javax.swing.JButton();
        btnUpdateKur = new javax.swing.JButton();
        btnDeleteKur = new javax.swing.JButton();
        btnClearKur = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelKur = new javax.swing.JTable();
        tfCariKur = new javax.swing.JTextField();
        btnCetakKurir = new javax.swing.JButton();
        jLabel38 = new javax.swing.JLabel();
        Supplier = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        tfTelpSup = new javax.swing.JTextField();
        tfNamaSup = new javax.swing.JTextField();
        tfIdSup = new javax.swing.JTextField();
        btnInsertSup = new javax.swing.JButton();
        btnUpdateSup = new javax.swing.JButton();
        btnDeleteSup = new javax.swing.JButton();
        btnClearSup = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabelSup = new javax.swing.JTable();
        tfCariSup = new javax.swing.JTextField();
        btnCetakSupplier = new javax.swing.JButton();
        jLabel39 = new javax.swing.JLabel();
        Karyawan = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        tfIdKar = new javax.swing.JTextField();
        tfNamaKar = new javax.swing.JTextField();
        tfAlamatKar = new javax.swing.JTextField();
        tfTelpKar = new javax.swing.JTextField();
        tfEmailKar = new javax.swing.JTextField();
        cbJabatan = new javax.swing.JComboBox<>();
        rbLK = new javax.swing.JRadioButton();
        rbPR = new javax.swing.JRadioButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelKar = new javax.swing.JTable();
        btnInsertKar = new javax.swing.JButton();
        btnUpdateKar = new javax.swing.JButton();
        btnDeleteKar = new javax.swing.JButton();
        btnClearKar = new javax.swing.JButton();
        tfCariKar = new javax.swing.JTextField();
        btnCetakKaryawan = new javax.swing.JButton();
        jLabel40 = new javax.swing.JLabel();
        Supply = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jlab = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        txtnosup = new javax.swing.JTextField();
        txtidsup = new javax.swing.JTextField();
        txtidkrywn = new javax.swing.JTextField();
        txtkodebrg = new javax.swing.JTextField();
        txttanggal = new javax.swing.JTextField();
        txtjml = new javax.swing.JTextField();
        txtnamasup = new javax.swing.JLabel();
        txtnamakyw = new javax.swing.JLabel();
        txtnmbrng = new javax.swing.JLabel();
        txttotal = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tabelSupp = new javax.swing.JTable();
        btnTambahSupp = new javax.swing.JButton();
        btnHapusSupp = new javax.swing.JButton();
        btnKonfirmasiSupp = new javax.swing.JButton();
        btnCetakSupply = new javax.swing.JButton();
        jLabel41 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        dashboard = new javax.swing.JMenuItem();
        logout = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        barang = new javax.swing.JMenuItem();
        karyawan = new javax.swing.JMenuItem();
        kurir = new javax.swing.JMenuItem();
        stat = new javax.swing.JMenuItem();
        supplier = new javax.swing.JMenuItem();
        supply = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 204, 51));
        setUndecorated(true);

        PanelPusat.setBackground(new java.awt.Color(102, 255, 255));
        PanelPusat.setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane1.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));

        Dashboard.setPreferredSize(new java.awt.Dimension(777, 507));
        Dashboard.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setText("Log Out");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        Dashboard.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 500, 220, 30));

        lblBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Aset/TampilanAdmin.jpg"))); // NOI18N
        Dashboard.add(lblBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 880, 620));

        lblKRW.setText("jLabel42");
        lblKRW.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblKRWMouseClicked(evt);
            }
        });
        Dashboard.add(lblKRW, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 110, 120, 110));

        lblBrg.setText("jLabel42");
        lblBrg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBrgMouseClicked(evt);
            }
        });
        Dashboard.add(lblBrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 110, 110, 110));

        lblSTAT.setText("jLabel1");
        lblSTAT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSTATMouseClicked(evt);
            }
        });
        Dashboard.add(lblSTAT, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 250, 120, 120));

        lblKRR.setText("jLabel1");
        lblKRR.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblKRRMouseClicked(evt);
            }
        });
        Dashboard.add(lblKRR, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 260, 110, 110));

        lblSUPP.setText("jLabel1");
        lblSUPP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSUPPMouseClicked(evt);
            }
        });
        Dashboard.add(lblSUPP, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 400, 110, 110));

        lblSUP.setText("jLabel1");
        lblSUP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSUPMouseClicked(evt);
            }
        });
        Dashboard.add(lblSUP, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 400, 110, 110));

        jTabbedPane1.addTab("Dashboard", Dashboard);

        Barang.setPreferredSize(new java.awt.Dimension(777, 507));
        Barang.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setText("Barang");
        Barang.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 48, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Kode Barang");
        Barang.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 115, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Nama Barang");
        Barang.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 160, -1, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Harga");
        Barang.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 204, 43, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Stok");
        Barang.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 249, 43, -1));

        tfKodeBar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Barang.add(tfKodeBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(152, 114, 205, -1));

        tfNamaBar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Barang.add(tfNamaBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(152, 159, 205, -1));

        tfHargaBar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Barang.add(tfHargaBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(152, 203, 205, -1));

        tfStokBar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Barang.add(tfStokBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(152, 248, 205, -1));

        btnInsertBar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnInsertBar.setText("Insert");
        btnInsertBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertBarActionPerformed(evt);
            }
        });
        Barang.add(btnInsertBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 305, 100, 35));

        btnUpdateBar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnUpdateBar.setText("Update");
        btnUpdateBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateBarActionPerformed(evt);
            }
        });
        Barang.add(btnUpdateBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(139, 305, 100, 35));

        btnDeleteBar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnDeleteBar.setText("Delete");
        btnDeleteBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteBarActionPerformed(evt);
            }
        });
        Barang.add(btnDeleteBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(257, 305, 100, 35));

        btnClearBar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnClearBar.setText("Clear");
        btnClearBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearBarActionPerformed(evt);
            }
        });
        Barang.add(btnClearBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(207, 358, 100, 35));

        tabelBar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tabelBar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabelBar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelBarMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelBar);

        Barang.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(422, 105, -1, 235));

        tfCariBar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tfCariBar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfCariBarKeyReleased(evt);
            }
        });
        Barang.add(tfCariBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(422, 72, 195, -1));

        btnCetakBar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCetakBar.setText("Cetak");
        btnCetakBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakBarActionPerformed(evt);
            }
        });
        Barang.add(btnCetakBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(77, 358, 100, 35));

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/NewTampilan/Barang.jpg"))); // NOI18N
        Barang.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jTabbedPane1.addTab("Barang", Barang);

        Stat.setPreferredSize(new java.awt.Dimension(777, 507));
        Stat.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setText("Laporan");
        Stat.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 48, -1, -1));

        tabelLap.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tabelLap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(tabelLap);

        Stat.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 135, 852, 398));

        btnCetakKaryawan1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCetakKaryawan1.setText("Cetak");
        btnCetakKaryawan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakKaryawan1ActionPerformed(evt);
            }
        });
        Stat.add(btnCetakKaryawan1, new org.netbeans.lib.awtextra.AbsoluteConstraints(775, 551, 99, 36));

        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/NewTampilan/Stats.jpg"))); // NOI18N
        Stat.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jTabbedPane1.addTab("Stat", Stat);

        Kurir.setPreferredSize(new java.awt.Dimension(777, 507));
        Kurir.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setText("Kurir");
        Kurir.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 50, -1, -1));

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel19.setText("ID Kurir");
        Kurir.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 108, -1, -1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel20.setText("Nama");
        Kurir.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 154, 43, -1));

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel21.setText("Alamat");
        Kurir.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 196, -1, -1));

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel22.setText("Jenis Kelamin");
        Kurir.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 241, -1, -1));

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel23.setText("No Telepon");
        Kurir.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 291, -1, -1));

        tfTelpKur.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Kurir.add(tfTelpKur, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 290, 265, -1));

        tfIdKur.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Kurir.add(tfIdKur, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 107, 265, -1));

        tfNamaKur.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Kurir.add(tfNamaKur, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 153, 265, -1));

        tfAlamatKur.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Kurir.add(tfAlamatKur, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 195, 265, -1));

        buttonGroup2.add(rbLKKur);
        rbLKKur.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rbLKKur.setText("Laki - Laki");
        Kurir.add(rbLKKur, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 241, 98, -1));

        buttonGroup2.add(rbPRKur);
        rbPRKur.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rbPRKur.setText("Perempuan");
        Kurir.add(rbPRKur, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 241, 98, -1));

        btnInsertKur.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnInsertKur.setText("Insert");
        btnInsertKur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertKurActionPerformed(evt);
            }
        });
        Kurir.add(btnInsertKur, new org.netbeans.lib.awtextra.AbsoluteConstraints(56, 343, 100, 35));

        btnUpdateKur.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnUpdateKur.setText("Update");
        btnUpdateKur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateKurActionPerformed(evt);
            }
        });
        Kurir.add(btnUpdateKur, new org.netbeans.lib.awtextra.AbsoluteConstraints(182, 343, 100, 35));

        btnDeleteKur.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnDeleteKur.setText("Delete");
        btnDeleteKur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteKurActionPerformed(evt);
            }
        });
        Kurir.add(btnDeleteKur, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 343, 100, 35));

        btnClearKur.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnClearKur.setText("Clear");
        btnClearKur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearKurActionPerformed(evt);
            }
        });
        Kurir.add(btnClearKur, new org.netbeans.lib.awtextra.AbsoluteConstraints(233, 396, 100, 35));

        tabelKur.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tabelKur.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabelKur.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelKurMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tabelKur);

        Kurir.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(428, 107, 442, 209));

        tfCariKur.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tfCariKur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfCariKurKeyReleased(evt);
            }
        });
        Kurir.add(tfCariKur, new org.netbeans.lib.awtextra.AbsoluteConstraints(428, 80, 160, -1));

        btnCetakKurir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCetakKurir.setText("Cetak");
        btnCetakKurir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakKurirActionPerformed(evt);
            }
        });
        Kurir.add(btnCetakKurir, new org.netbeans.lib.awtextra.AbsoluteConstraints(115, 396, 100, 35));

        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/NewTampilan/Kurir.jpg"))); // NOI18N
        Kurir.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jTabbedPane1.addTab("Kurir", Kurir);

        Supplier.setPreferredSize(new java.awt.Dimension(777, 507));
        Supplier.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel7.setText("Supplier");
        Supplier.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 48, -1, -1));

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel24.setText("ID Supplier");
        Supplier.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 141, -1, -1));

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel25.setText("Nama");
        Supplier.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 184, 43, -1));

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel26.setText("No Telepon");
        Supplier.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 230, -1, -1));

        tfTelpSup.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Supplier.add(tfTelpSup, new org.netbeans.lib.awtextra.AbsoluteConstraints(114, 229, 235, -1));

        tfNamaSup.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tfNamaSup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfNamaSupActionPerformed(evt);
            }
        });
        Supplier.add(tfNamaSup, new org.netbeans.lib.awtextra.AbsoluteConstraints(114, 183, 235, -1));

        tfIdSup.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Supplier.add(tfIdSup, new org.netbeans.lib.awtextra.AbsoluteConstraints(114, 140, 235, -1));

        btnInsertSup.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnInsertSup.setText("Insert");
        btnInsertSup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertSupActionPerformed(evt);
            }
        });
        Supplier.add(btnInsertSup, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 284, 100, 35));

        btnUpdateSup.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnUpdateSup.setText("Update");
        btnUpdateSup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateSupActionPerformed(evt);
            }
        });
        Supplier.add(btnUpdateSup, new org.netbeans.lib.awtextra.AbsoluteConstraints(156, 284, 100, 35));

        btnDeleteSup.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnDeleteSup.setText("Delete");
        btnDeleteSup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteSupActionPerformed(evt);
            }
        });
        Supplier.add(btnDeleteSup, new org.netbeans.lib.awtextra.AbsoluteConstraints(274, 284, 100, 35));

        btnClearSup.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnClearSup.setText("Clear");
        btnClearSup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearSupActionPerformed(evt);
            }
        });
        Supplier.add(btnClearSup, new org.netbeans.lib.awtextra.AbsoluteConstraints(216, 340, 100, 35));

        tabelSup.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tabelSup.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabelSup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelSupMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tabelSup);

        Supplier.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(422, 140, -1, 182));

        tfCariSup.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tfCariSup.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfCariSupKeyReleased(evt);
            }
        });
        Supplier.add(tfCariSup, new org.netbeans.lib.awtextra.AbsoluteConstraints(422, 101, 200, -1));

        btnCetakSupplier.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCetakSupplier.setText("Cetak");
        btnCetakSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakSupplierActionPerformed(evt);
            }
        });
        Supplier.add(btnCetakSupplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(98, 340, 100, 35));

        jLabel39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/NewTampilan/Supplier.jpg"))); // NOI18N
        Supplier.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jTabbedPane1.addTab("Supplier", Supplier);

        Karyawan.setPreferredSize(new java.awt.Dimension(777, 507));
        Karyawan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setText("Karyawan");
        Karyawan.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 55, -1, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("ID Karyawan");
        Karyawan.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 129, -1, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("Nama");
        Karyawan.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 173, 43, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("Jabatan");
        Karyawan.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 215, -1, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setText("Jenis Kelamin");
        Karyawan.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 257, -1, -1));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("Alamat");
        Karyawan.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 299, -1, -1));

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setText("No Telepon");
        Karyawan.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 342, 86, -1));

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel18.setText("Email");
        Karyawan.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 385, 43, -1));

        tfIdKar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Karyawan.add(tfIdKar, new org.netbeans.lib.awtextra.AbsoluteConstraints(158, 128, 205, -1));

        tfNamaKar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Karyawan.add(tfNamaKar, new org.netbeans.lib.awtextra.AbsoluteConstraints(158, 172, 205, -1));

        tfAlamatKar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Karyawan.add(tfAlamatKar, new org.netbeans.lib.awtextra.AbsoluteConstraints(158, 298, 205, -1));

        tfTelpKar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Karyawan.add(tfTelpKar, new org.netbeans.lib.awtextra.AbsoluteConstraints(158, 341, 205, -1));

        tfEmailKar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Karyawan.add(tfEmailKar, new org.netbeans.lib.awtextra.AbsoluteConstraints(158, 384, 205, -1));

        cbJabatan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbJabatan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--pilih--", "KEPALA TOKO", "ADMIN", "KASIR", "SALES", "OFFICE BOY", "MANAGER", "STAFF GUDANG" }));
        Karyawan.add(cbJabatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(158, 214, 205, -1));

        buttonGroup1.add(rbLK);
        rbLK.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rbLK.setText("Laki - Laki");
        Karyawan.add(rbLK, new org.netbeans.lib.awtextra.AbsoluteConstraints(158, 257, 98, -1));

        buttonGroup1.add(rbPR);
        rbPR.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rbPR.setText("Perempuan");
        Karyawan.add(rbPR, new org.netbeans.lib.awtextra.AbsoluteConstraints(284, 257, 98, -1));

        tabelKar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tabelKar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabelKar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelKarMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelKar);

        Karyawan.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(411, 121, -1, 284));

        btnInsertKar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnInsertKar.setText("Insert");
        btnInsertKar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertKarActionPerformed(evt);
            }
        });
        Karyawan.add(btnInsertKar, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 440, 121, 35));

        btnUpdateKar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnUpdateKar.setText("Update");
        btnUpdateKar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateKarActionPerformed(evt);
            }
        });
        Karyawan.add(btnUpdateKar, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 440, 121, 35));

        btnDeleteKar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnDeleteKar.setText("Delete");
        btnDeleteKar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteKarActionPerformed(evt);
            }
        });
        Karyawan.add(btnDeleteKar, new org.netbeans.lib.awtextra.AbsoluteConstraints(299, 440, 121, 35));

        btnClearKar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnClearKar.setText("Clear");
        btnClearKar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearKarActionPerformed(evt);
            }
        });
        Karyawan.add(btnClearKar, new org.netbeans.lib.awtextra.AbsoluteConstraints(233, 493, 121, 35));

        tfCariKar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfCariKarKeyReleased(evt);
            }
        });
        Karyawan.add(tfCariKar, new org.netbeans.lib.awtextra.AbsoluteConstraints(411, 87, 180, -1));

        btnCetakKaryawan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCetakKaryawan.setText("Cetak");
        btnCetakKaryawan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakKaryawanActionPerformed(evt);
            }
        });
        Karyawan.add(btnCetakKaryawan, new org.netbeans.lib.awtextra.AbsoluteConstraints(81, 493, 122, 35));

        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/NewTampilan/Karyawan.jpg"))); // NOI18N
        Karyawan.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jTabbedPane1.addTab("Karyawan", Karyawan);

        Supply.setPreferredSize(new java.awt.Dimension(777, 507));
        Supply.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel6.setText("Supply");
        Supply.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 57, -1, -1));

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel27.setText("No Supply");
        Supply.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 118, -1, -1));

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel28.setText("ID Supplier");
        Supply.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 161, -1, -1));

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel29.setText("ID Karyawan");
        Supply.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 204, -1, -1));

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel30.setText("Kode Barang");
        Supply.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 252, -1, -1));

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel31.setText("Nama Supplier");
        Supply.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(379, 161, -1, -1));

        jlab.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jlab.setText("NamaKaryawan");
        Supply.add(jlab, new org.netbeans.lib.awtextra.AbsoluteConstraints(383, 204, -1, -1));

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel33.setText("Nama Barang");
        Supply.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 252, -1, -1));

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel34.setText("Tanggal");
        Supply.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 67, 60, -1));

        jLabel35.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel35.setText("Jumlah");
        Supply.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(655, 252, -1, -1));

        jLabel36.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel36.setText("Total");
        Supply.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(655, 290, 43, -1));

        txtnosup.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Supply.add(txtnosup, new org.netbeans.lib.awtextra.AbsoluteConstraints(137, 117, 225, -1));

        txtidsup.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtidsup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtidsupActionPerformed(evt);
            }
        });
        Supply.add(txtidsup, new org.netbeans.lib.awtextra.AbsoluteConstraints(137, 160, 225, -1));

        txtidkrywn.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtidkrywn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtidkrywnActionPerformed(evt);
            }
        });
        Supply.add(txtidkrywn, new org.netbeans.lib.awtextra.AbsoluteConstraints(137, 203, 225, -1));

        txtkodebrg.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtkodebrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtkodebrgActionPerformed(evt);
            }
        });
        Supply.add(txtkodebrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(137, 251, 225, -1));

        txttanggal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Supply.add(txttanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(638, 66, 180, -1));

        txtjml.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtjml.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtjmlActionPerformed(evt);
            }
        });
        Supply.add(txtjml, new org.netbeans.lib.awtextra.AbsoluteConstraints(729, 251, 119, -1));

        txtnamasup.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtnamasup.setText("-");
        Supply.add(txtnamasup, new org.netbeans.lib.awtextra.AbsoluteConstraints(516, 161, 108, -1));

        txtnamakyw.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtnamakyw.setText("-");
        Supply.add(txtnamakyw, new org.netbeans.lib.awtextra.AbsoluteConstraints(516, 204, 108, -1));

        txtnmbrng.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtnmbrng.setText("-");
        Supply.add(txtnmbrng, new org.netbeans.lib.awtextra.AbsoluteConstraints(516, 252, 122, -1));

        txttotal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txttotal.setText("-");
        Supply.add(txttotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(716, 290, 87, -1));

        tabelSupp.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tabelSupp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(tabelSupp);

        Supply.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 290, 605, 247));

        btnTambahSupp.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnTambahSupp.setText("Tambah");
        btnTambahSupp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahSuppActionPerformed(evt);
            }
        });
        Supply.add(btnTambahSupp, new org.netbeans.lib.awtextra.AbsoluteConstraints(656, 355, 100, 35));

        btnHapusSupp.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnHapusSupp.setText("Hapus");
        btnHapusSupp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusSuppActionPerformed(evt);
            }
        });
        Supply.add(btnHapusSupp, new org.netbeans.lib.awtextra.AbsoluteConstraints(774, 355, 100, 35));

        btnKonfirmasiSupp.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnKonfirmasiSupp.setText("Konfirmasi");
        btnKonfirmasiSupp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKonfirmasiSuppActionPerformed(evt);
            }
        });
        Supply.add(btnKonfirmasiSupp, new org.netbeans.lib.awtextra.AbsoluteConstraints(656, 446, 218, 60));

        btnCetakSupply.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCetakSupply.setText("Cetak");
        btnCetakSupply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakSupplyActionPerformed(evt);
            }
        });
        Supply.add(btnCetakSupply, new org.netbeans.lib.awtextra.AbsoluteConstraints(718, 402, 100, 35));

        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/NewTampilan/Supply.jpg"))); // NOI18N
        Supply.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jTabbedPane1.addTab("Supply", Supply);

        jPanel1.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -60, 880, 630));

        PanelPusat.add(jPanel1, java.awt.BorderLayout.CENTER);

        getContentPane().add(PanelPusat, java.awt.BorderLayout.CENTER);

        jMenu1.setText("Profile");
        jMenu1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        dashboard.setText("Dashboard");
        dashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dashboardActionPerformed(evt);
            }
        });
        jMenu1.add(dashboard);

        logout.setText("Log Out");
        logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutActionPerformed(evt);
            }
        });
        jMenu1.add(logout);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Menu");
        jMenu2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        barang.setText("Barang");
        barang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                barangActionPerformed(evt);
            }
        });
        jMenu2.add(barang);

        karyawan.setText("Karyawan");
        karyawan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                karyawanActionPerformed(evt);
            }
        });
        jMenu2.add(karyawan);

        kurir.setText("Kurir");
        kurir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kurirActionPerformed(evt);
            }
        });
        jMenu2.add(kurir);

        stat.setText("Stat");
        stat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statActionPerformed(evt);
            }
        });
        jMenu2.add(stat);

        supplier.setText("Supplier");
        supplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supplierActionPerformed(evt);
            }
        });
        jMenu2.add(supplier);

        supply.setText("Supply");
        supply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supplyActionPerformed(evt);
            }
        });
        jMenu2.add(supply);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void dashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dashboardActionPerformed
        jTabbedPane1.setSelectedIndex(0);
        jMenuBar1.setVisible(false);
    }//GEN-LAST:event_dashboardActionPerformed

    private void barangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_barangActionPerformed
        jTabbedPane1.setSelectedIndex(1);
        jMenuBar1.setVisible(true);
    }//GEN-LAST:event_barangActionPerformed

    private void karyawanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_karyawanActionPerformed
        jTabbedPane1.setSelectedIndex(5);
        jMenuBar1.setVisible(true);
    }//GEN-LAST:event_karyawanActionPerformed

    private void statActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statActionPerformed
        jTabbedPane1.setSelectedIndex(2);
        jMenuBar1.setVisible(true);
    }//GEN-LAST:event_statActionPerformed

    private void kurirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kurirActionPerformed
        jTabbedPane1.setSelectedIndex(3);
        jMenuBar1.setVisible(true);
    }//GEN-LAST:event_kurirActionPerformed

    private void supplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supplyActionPerformed
        jTabbedPane1.setSelectedIndex(6);
        jMenuBar1.setVisible(true);
    }//GEN-LAST:event_supplyActionPerformed

    private void supplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supplierActionPerformed
        jTabbedPane1.setSelectedIndex(4);
        jMenuBar1.setVisible(true);
    }//GEN-LAST:event_supplierActionPerformed

    private void btnTambahSuppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahSuppActionPerformed
        //tambah tabel semu frame supply
        DefaultTableModel model = (DefaultTableModel) tabelSupp.getModel();
        model.addRow(new Object[]{
            txtnosup.getText(),
            txtidsup.getText(),
            txtidkrywn.getText(),
            txtkodebrg.getText(),
            txtnmbrng.getText(),
            txtjml.getText()
        });

        txtkodebrg.setText("");
        txtnmbrng.setText("");
        txtjml.setText("");

        hitungTotalStok();
    }//GEN-LAST:event_btnTambahSuppActionPerformed

    private void btnHapusSuppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusSuppActionPerformed
        //hapus tabel semu frame tabel supply
        DefaultTableModel model = (DefaultTableModel) tabelSupp.getModel();
        int row = tabelSupp.getSelectedRow();
        model.removeRow(row);
        hitungTotalStok();
    }//GEN-LAST:event_btnHapusSuppActionPerformed

    private void btnKonfirmasiSuppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKonfirmasiSuppActionPerformed
        //insert database frame supply
        try {
            konek k = new konek();
            conn = k.getConnection();
            conn.setAutoCommit(false);

            String sqlPeminjaman = "INSERT INTO supply(no_supply, id_supplier, id_karyawan, tanggal, total_sup) VALUES (?, ?, ?, ?, ?)";
            String sqlDetail = "INSERT INTO detail_supply (no_supply, kd_barang, jumlah_supply) VALUES (?, ?, ?)";

            Set<String> noSupplySet = new HashSet<>();
            String tanggalPinjam = txttanggal.getText();

            for (int i = 0; i < tabelSupp.getRowCount(); i++) {
                String noSupply = tabelSupp.getValueAt(i, 0).toString();
                String idSupp = tabelSupp.getValueAt(i, 1).toString();
                String idKyw = tabelSupp.getValueAt(i, 2).toString();

                if (!noSupplySet.contains(noSupply)) {
                    try (PreparedStatement pstmtPeminjaman = conn.prepareStatement(sqlPeminjaman)) {
                        pstmtPeminjaman.setString(1, noSupply);
                        pstmtPeminjaman.setString(2, idSupp);
                        pstmtPeminjaman.setString(3, idKyw);
                        pstmtPeminjaman.setDate(4, java.sql.Date.valueOf(tanggalPinjam));
                        pstmtPeminjaman.setInt(5, Integer.parseInt(txttotal.getText()));
                        pstmtPeminjaman.executeUpdate();
                        noSupplySet.add(noSupply);
                    }
                }
            }

            try (PreparedStatement pstmtDetail = conn.prepareStatement(sqlDetail)) {
                for (int i = 0; i < tabelSupp.getRowCount(); i++) {
                    String noSupply = tabelSupp.getValueAt(i, 0).toString();
                    String kodeBrg = tabelSupp.getValueAt(i, 3).toString();
                    String jumlah = tabelSupp.getValueAt(i, 5).toString();

                    pstmtDetail.setString(1, noSupply);
                    pstmtDetail.setString(2, kodeBrg);
                    pstmtDetail.setLong(3, Long.parseLong(jumlah));
                    pstmtDetail.addBatch();
                }
                pstmtDetail.executeBatch();
            }

            conn.commit();
            JOptionPane.showMessageDialog(null, "Supply berhasil!");
        } catch (SQLException ex) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            JOptionPane.showMessageDialog(null, "Transaksi dibatalkan: " + ex.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
            kosongSupply();
        }
    }//GEN-LAST:event_btnKonfirmasiSuppActionPerformed

    private void txtidsupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtidsupActionPerformed
        //text field id sup frame supply
        lookupSupplier();
        txtidkrywn.requestFocus();
    }//GEN-LAST:event_txtidsupActionPerformed

    private void txtidkrywnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtidkrywnActionPerformed
        //text field id karyawan frame supply
        lookupKaryawan();
        txtkodebrg.requestFocus();
    }//GEN-LAST:event_txtidkrywnActionPerformed

    private void txtkodebrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtkodebrgActionPerformed
        //text field kode barang frame supply
        lookupBarang();
        txtjml.requestFocus();
    }//GEN-LAST:event_txtkodebrgActionPerformed

    private void txtjmlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtjmlActionPerformed
        //text field jumlah frame supply
        btnTambahSupp.doClick();
    }//GEN-LAST:event_txtjmlActionPerformed

    private void btnInsertSupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertSupActionPerformed
        //insert frame supplier
        if (tfIdSup.getText().trim().isEmpty() || tfNamaSup.getText().trim().isEmpty() || tfTelpSup.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Isi semua data", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } else {
            String sql = "INSERT INTO supplier (id_supplier, nama_supplier, telp_supplier) VALUES (?, ?, ?)";
            try {
                konek k = new konek();
                conn = k.getConnection();
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, tfIdSup.getText().trim());
                pstmt.setString(2, tfNamaSup.getText().trim());
                pstmt.setString(3, tfTelpSup.getText().trim());

                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan", "Informasi", JOptionPane.INFORMATION_MESSAGE);

                bersihSupplier();
                tabelSupplier();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat menyimpan data: " + ex.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(FrameAdmin.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if (pstmt != null) {
                        pstmt.close();
                    }
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FrameAdmin.class.getName()).log(Level.SEVERE, "Gagal menutup koneksi", ex);
                }
            }
        }
    }//GEN-LAST:event_btnInsertSupActionPerformed

    private void btnUpdateSupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateSupActionPerformed
        //update frame supplier
        if (tfIdSup.getText().equals("") | tfNamaSup.getText().equals("") | tfTelpSup.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Isi semua data");
        } else {
            try {
                konek k = new konek();
                conn = k.getConnection();
                String sql = "UPDATE supplier SET nama_supplier=?, telp_supplier=? where id_supplier=?";
                pstmt = conn.prepareStatement(sql);

                pstmt.setString(1, tfNamaSup.getText());
                pstmt.setString(2, tfTelpSup.getText());
                pstmt.setString(3, tfIdSup.getText());

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Data berhasil diupdate");
                    pstmt.close();
                    conn.close();
                    bersihSupplier();
                    tabelSupplier();
                } else {
                    JOptionPane.showMessageDialog(null, "Data tidak ditemukan");
                }
            } catch (SQLException ex) {
                Logger.getLogger(FrameAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnUpdateSupActionPerformed

    private void btnDeleteSupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteSupActionPerformed
        //delete frame supplier
        if (tfIdSup.getText().equals("") | tfNamaSup.getText().equals("") | tfTelpSup.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Isi semua data");
        } else {
            String id;
            id = tfIdSup.getText();
            try {
                konek k = new konek();
                conn = k.getConnection();
                String pesan = "Yakin ingin menghapus data dengan ID: " + id;
                int jawab = JOptionPane.showConfirmDialog(null, pesan,
                        "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
                switch (jawab) {
                    case JOptionPane.YES_OPTION -> {
                        String deleteSql = "DELETE FROM supplier WHERE id_supplier= ?";
                        pstmt = conn.prepareStatement(deleteSql);
                        pstmt.setString(1, id);
                        pstmt.executeUpdate();
                        pstmt.close();
                        conn.close();
                        bersihSupplier();
                        tabelSupplier();
                    }
                    case JOptionPane.NO_OPTION ->
                        JOptionPane.showMessageDialog(this, "Kamu menjawab tidak");
                }
            } catch (SQLException ex) {
                JOptionPane.showConfirmDialog(null, "Cek Lagi!!!");
                Logger.getLogger(FrameAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnDeleteSupActionPerformed

    private void tabelSupMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelSupMouseClicked
        //ambil data tabel gui frame supplier
        int row = tabelSup.getSelectedRow();
        tfIdSup.setText(tabelSup.getValueAt(row, 0).toString());
        tfNamaSup.setText(tabelSup.getValueAt(row, 1).toString());
        tfTelpSup.setText(tabelSup.getValueAt(row, 2).toString());
    }//GEN-LAST:event_tabelSupMouseClicked

    private void btnClearSupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearSupActionPerformed
        //clear frame supplier
        bersihSupplier();
    }//GEN-LAST:event_btnClearSupActionPerformed

    private void tfCariSupKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfCariSupKeyReleased
        //cari frame supplier
        DefaultTableModel ob = (DefaultTableModel) tabelSup.getModel();
        TableRowSorter<DefaultTableModel> obj = new TableRowSorter<>(ob);
        obj.setRowFilter(RowFilter.regexFilter(tfCariSup.getText()));
        tabelSup.setRowSorter(obj);
    }//GEN-LAST:event_tfCariSupKeyReleased

    private void tfNamaSupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNamaSupActionPerformed
        //auto next frame supplier
        tfTelpSup.requestFocus();
    }//GEN-LAST:event_tfNamaSupActionPerformed

    private void btnInsertKurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertKurActionPerformed
        //insert kurir
        if (tfIdKur.getText().trim().isEmpty() || tfNamaKur.getText().trim().isEmpty() || tfTelpKur.getText().trim().isEmpty() || tfAlamatKur.getText().trim().isEmpty() || buttonGroup2.getSelection() == null) {
            JOptionPane.showMessageDialog(null, "Isi semua data", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } else {
            String sql = "INSERT INTO kurir (id_kurir, nama, telp, jenis_kelamin, alamat) VALUES (?, ?, ?, ?, ?)";
            try {
                konek k = new konek();
                conn = k.getConnection();
                String jenkel = null;
                if (rbLKKur.isSelected()) {
                    jenkel = "L";
                } else {
                    if (rbPRKur.isSelected()) {
                        jenkel = "P";
                    }
                }
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, tfIdKur.getText().trim());
                pstmt.setString(2, tfNamaKur.getText().trim());
                pstmt.setString(3, tfTelpKur.getText().trim());
                pstmt.setString(4, jenkel);
                pstmt.setString(5, tfAlamatKur.getText().trim());

                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan", "Informasi", JOptionPane.INFORMATION_MESSAGE);

                bersihKurir();
                tabelKurir();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat menyimpan data: " + ex.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(FrameAdmin.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if (pstmt != null) {
                        pstmt.close();
                        conn.close();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(FrameAdmin.class.getName()).log(Level.SEVERE, "Gagal menutup koneksi", ex);
                }
            }
        }
    }//GEN-LAST:event_btnInsertKurActionPerformed

    private void btnClearKurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearKurActionPerformed
        bersihKurir();//bersih kurir
    }//GEN-LAST:event_btnClearKurActionPerformed

    private void btnUpdateKurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateKurActionPerformed
        //frame kurir
        if (tfIdKur.getText().trim().isEmpty() || tfNamaKur.getText().trim().isEmpty() || tfTelpKur.getText().trim().isEmpty() || tfAlamatKur.getText().trim().isEmpty() || buttonGroup2.getSelection() == null) {
            JOptionPane.showMessageDialog(null, "Isi semua data");
        } else {
            try {
                konek k = new konek();
                conn = k.getConnection();
                String sql = "UPDATE kurir SET nama=?, telp=?, jenis_kelamin=?, alamat=? where id_kurir=?";
                pstmt = conn.prepareStatement(sql);

                String jenkel = null;
                if (rbLKKur.isSelected()) {
                    jenkel = "L";
                } else {
                    if (rbPRKur.isSelected()) {
                        jenkel = "P";
                    }
                }
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, tfNamaKur.getText().trim());
                pstmt.setString(2, tfTelpKur.getText().trim());
                pstmt.setString(3, jenkel);
                pstmt.setString(4, tfAlamatKur.getText().trim());
                pstmt.setString(5, tfIdKur.getText().trim());

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Data berhasil diupdate");
                    pstmt.close();
                    conn.close();
                    bersihKurir();
                    tabelKurir();
                } else {
                    JOptionPane.showMessageDialog(null, "Data tidak ditemukan");
                }
            } catch (SQLException ex) {
                Logger.getLogger(FrameAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnUpdateKurActionPerformed

    private void tabelKurMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelKurMouseClicked
        //frame kurir
        int row = tabelKur.getSelectedRow();
        tfIdKur.setText(tabelKur.getValueAt(row, 0).toString());
        tfNamaKur.setText(tabelKur.getValueAt(row, 1).toString());
        tfTelpKur.setText(tabelKur.getValueAt(row, 2).toString());
        String jenkel = tabelKur.getValueAt(row, 3).toString();
        if ("L".equals(jenkel)) {
            rbLKKur.setSelected(true);
        } else if ("P".equals(jenkel)) {
            rbPRKur.setSelected(true);
        }
        tfAlamatKur.setText(tabelKur.getValueAt(row, 4).toString());
    }//GEN-LAST:event_tabelKurMouseClicked

    private void btnDeleteKurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteKurActionPerformed
//     frame kurir
        if (tfIdKur.getText().trim().isEmpty() || tfNamaKur.getText().trim().isEmpty() || tfTelpKur.getText().trim().isEmpty() || tfAlamatKur.getText().trim().isEmpty() || buttonGroup2.getSelection() == null) {
            JOptionPane.showMessageDialog(null, "Isi semua data");
        } else {
            String id;
            id = tfIdKur.getText();
            try {
                konek k = new konek();
                conn = k.getConnection();
                String pesan = "Yakin ingin menghapus data dengan ID: " + id;
                int jawab = JOptionPane.showConfirmDialog(null, pesan,
                        "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
                switch (jawab) {
                    case JOptionPane.YES_OPTION -> {
                        String deleteSql = "DELETE FROM kurir WHERE id_kurir= ?";
                        pstmt = conn.prepareStatement(deleteSql);
                        pstmt.setString(1, id);
                        pstmt.executeUpdate();
                        pstmt.close();
                        conn.close();
                        bersihKurir();
                        tabelKurir();
                    }
                    case JOptionPane.NO_OPTION ->
                        JOptionPane.showMessageDialog(this, "Kamu menjawab tidak");
                }
            } catch (SQLException ex) {
                JOptionPane.showConfirmDialog(null, "Cek Lagi!!!");
                Logger.getLogger(FrameAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnDeleteKurActionPerformed

    private void tfCariKurKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfCariKurKeyReleased
        DefaultTableModel ob = (DefaultTableModel) tabelKur.getModel();
        TableRowSorter<DefaultTableModel> obj = new TableRowSorter<>(ob);
        obj.setRowFilter(RowFilter.regexFilter(tfCariKur.getText()));
        tabelKur.setRowSorter(obj);
    }//GEN-LAST:event_tfCariKurKeyReleased

    private void tabelKarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelKarMouseClicked
        int row = tabelKar.getSelectedRow();
        tfIdKar.setText(tabelKar.getValueAt(row, 0).toString());
        tfNamaKar.setText(tabelKar.getValueAt(row, 1).toString());
        tfTelpKar.setText(tabelKar.getValueAt(row, 2).toString());
        String jenkel = tabelKar.getValueAt(row, 3).toString();
        if ("L".equals(jenkel)) {
            rbLK.setSelected(true);
        } else if ("P".equals(jenkel)) {
            rbPR.setSelected(true);
        }
        tfAlamatKar.setText(tabelKar.getValueAt(row, 4).toString());
        tfEmailKar.setText(tabelKar.getValueAt(row, 6).toString());
        String id = tabelKar.getValueAt(row, 5).toString();
        if ("JK001".equals(id)) {
            cbJabatan.setSelectedIndex(1);
        } else if ("JK002".equals(id)) {
            cbJabatan.setSelectedIndex(2);
        } else if ("JK003".equals(id)) {
            cbJabatan.setSelectedIndex(3);
        } else if ("JK004".equals(id)) {
            cbJabatan.setSelectedIndex(4);
        } else if ("JK005".equals(id)) {
            cbJabatan.setSelectedIndex(5);
        } else if ("JK006".equals(id)) {
            cbJabatan.setSelectedIndex(6);
        } else {
            cbJabatan.setSelectedIndex(7);
        }
    }//GEN-LAST:event_tabelKarMouseClicked

    private void btnClearKarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearKarActionPerformed
        bersihKaryawan();//frame karyawan
    }//GEN-LAST:event_btnClearKarActionPerformed

    private void btnUpdateKarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateKarActionPerformed
        if (tfIdKar.getText().trim().isEmpty() || tfNamaKar.getText().trim().isEmpty() || tfTelpKar.getText().trim().isEmpty() || tfAlamatKar.getText().trim().isEmpty() || buttonGroup1.getSelection() == null || cbJabatan.getSelectedIndex() == 0 || tfEmailKar.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Isi semua data");
        } else {
            try {
                konek k = new konek();
                conn = k.getConnection();
                String sql = "UPDATE karyawan SET id_jenis=?, nama=?, telp=?, gender=?, alamat=?, email=? where id_karyawan=?";
                pstmt = conn.prepareStatement(sql);

                String jenkel = null;
                if (rbLK.isSelected()) {
                    jenkel = "L";
                } else {
                    if (rbPR.isSelected()) {
                        jenkel = "P";
                    }
                }

                String id = null;
                if (cbJabatan.getSelectedIndex() == 1) {
                    id = "JK001";
                } else if (cbJabatan.getSelectedIndex() == 2) {
                    id = "JK002";
                } else if (cbJabatan.getSelectedIndex() == 3) {
                    id = "JK003";
                } else if (cbJabatan.getSelectedIndex() == 4) {
                    id = "JK004";
                } else if (cbJabatan.getSelectedIndex() == 5) {
                    id = "JK005";
                } else if (cbJabatan.getSelectedIndex() == 6) {
                    id = "JK006";
                } else {
                    id = "JK007";
                }

                pstmt = conn.prepareStatement(sql);

                pstmt.setString(1, id);
                pstmt.setString(2, tfNamaKar.getText().trim());
                pstmt.setString(3, tfTelpKar.getText().trim());
                pstmt.setString(4, jenkel);
                pstmt.setString(5, tfAlamatKar.getText().trim());
                pstmt.setString(6, tfEmailKar.getText().trim());
                pstmt.setString(7, tfIdKar.getText().trim());

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Data berhasil diupdate");
                    pstmt.close();
                    bersihKaryawan();
                    tabelKaryawan();
                } else {
                    JOptionPane.showMessageDialog(null, "Data tidak ditemukan");
                }
            } catch (SQLException ex) {
                Logger.getLogger(FrameAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnUpdateKarActionPerformed

    private void btnInsertKarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertKarActionPerformed
        //frame karyawan
        if (tfIdKar.getText().trim().isEmpty() || tfNamaKar.getText().trim().isEmpty() || tfTelpKar.getText().trim().isEmpty() || tfAlamatKar.getText().trim().isEmpty() || buttonGroup1.getSelection() == null || cbJabatan.getSelectedIndex() == 0 || tfEmailKar.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Isi semua data", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } else {
            String sql = "INSERT INTO karyawan (id_karyawan, id_jenis, nama, telp, gender, alamat, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try {
                konek k = new konek();
                conn = k.getConnection();
                String jenkel = null;
                if (rbLK.isSelected()) {
                    jenkel = "L";
                } else {
                    if (rbPR.isSelected()) {
                        jenkel = "P";
                    }
                }

                String id = null;
                if (cbJabatan.getSelectedIndex() == 1) {
                    id = "JK001";
                } else if (cbJabatan.getSelectedIndex() == 2) {
                    id = "JK002";
                } else if (cbJabatan.getSelectedIndex() == 3) {
                    id = "JK003";
                } else if (cbJabatan.getSelectedIndex() == 4) {
                    id = "JK004";
                } else if (cbJabatan.getSelectedIndex() == 5) {
                    id = "JK005";
                } else if (cbJabatan.getSelectedIndex() == 6) {
                    id = "JK006";
                } else {
                    id = "JK007";
                }
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, tfIdKar.getText().trim());
                pstmt.setString(2, id);
                pstmt.setString(3, tfNamaKar.getText().trim());
                pstmt.setString(4, tfTelpKar.getText().trim());
                pstmt.setString(5, jenkel);
                pstmt.setString(6, tfAlamatKar.getText().trim());
                pstmt.setString(7, tfEmailKar.getText().trim());

                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan", "Informasi", JOptionPane.INFORMATION_MESSAGE);

                bersihKaryawan();
                tabelKaryawan();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat menyimpan data: " + ex.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(FrameAdmin.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if (pstmt != null) {
                        pstmt.close();
                        conn.close();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(FrameAdmin.class.getName()).log(Level.SEVERE, "Gagal menutup koneksi", ex);
                }
            }
        }
    }//GEN-LAST:event_btnInsertKarActionPerformed

    private void btnDeleteKarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteKarActionPerformed
        if (tfIdKar.getText().trim().isEmpty() || tfNamaKar.getText().trim().isEmpty() || tfTelpKar.getText().trim().isEmpty() || tfAlamatKar.getText().trim().isEmpty() || buttonGroup1.getSelection() == null || cbJabatan.getSelectedIndex() == 0 || tfEmailKar.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Isi semua data");
        } else {
            try {
                konek k = new konek();
                conn = k.getConnection();
                String pesan = "Yakin ingin menghapus data dengan ID: " + tfIdKar.getText();
                int jawab = JOptionPane.showConfirmDialog(null, pesan,
                        "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
                switch (jawab) {
                    case JOptionPane.YES_OPTION -> {
                        String deleteSql = "DELETE FROM karyawan WHERE id_karyawan= ?";
                        pstmt = conn.prepareStatement(deleteSql);
                        pstmt.setString(1, tfIdKar.getText());
                        pstmt.executeUpdate();
                        pstmt.close();
                        conn.close();
                        bersihKaryawan();
                        tabelKaryawan();
                    }
                    case JOptionPane.NO_OPTION ->
                        JOptionPane.showMessageDialog(this, "Kamu menjawab tidak");
                }
            } catch (SQLException ex) {
                JOptionPane.showConfirmDialog(null, "Cek Lagi!!!");
                Logger.getLogger(FrameAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnDeleteKarActionPerformed

    private void tfCariKarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfCariKarKeyReleased
        DefaultTableModel ob = (DefaultTableModel) tabelKar.getModel();
        TableRowSorter<DefaultTableModel> obj = new TableRowSorter<>(ob);
        obj.setRowFilter(RowFilter.regexFilter(tfCariKar.getText()));
        tabelKar.setRowSorter(obj);
    }//GEN-LAST:event_tfCariKarKeyReleased

    private void btnInsertBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertBarActionPerformed
        if (tfKodeBar.getText().trim().isEmpty() || tfNamaBar.getText().trim().isEmpty() || tfHargaBar.getText().trim().isEmpty() || tfStokBar.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Isi semua data", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } else {
            String sql = "INSERT INTO barang (kd_barang, nama_barang, harga, stok) VALUES (?, ?, ?, ?)";
            try {
                konek k = new konek();
                conn = k.getConnection();
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, tfKodeBar.getText().trim());
                pstmt.setString(2, tfNamaBar.getText().trim());
                pstmt.setInt(3, Integer.parseInt(tfHargaBar.getText().trim()));
                pstmt.setLong(4, Long.parseLong(tfStokBar.getText().trim()));

                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan", "Informasi", JOptionPane.INFORMATION_MESSAGE);

                bersihBarang();
                tabelBarang();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat menyimpan data: " + ex.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(FrameAdmin.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if (pstmt != null) {
                        pstmt.close();
                        conn.close();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(FrameAdmin.class.getName()).log(Level.SEVERE, "Gagal menutup koneksi", ex);
                }
            }
        }
    }//GEN-LAST:event_btnInsertBarActionPerformed

    private void btnUpdateBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateBarActionPerformed
        if (tfKodeBar.getText().trim().isEmpty() || tfNamaBar.getText().trim().isEmpty() || tfHargaBar.getText().trim().isEmpty() || tfStokBar.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Isi semua data");
        } else {
            try {
                konek k = new konek();
                conn = k.getConnection();
                String sql = "UPDATE barang SET nama_barang=?, harga=?, stok=? where kd_barang=?";
                pstmt = conn.prepareStatement(sql);

                pstmt.setString(1, tfNamaBar.getText().trim());
                pstmt.setInt(2, Integer.parseInt(tfHargaBar.getText().trim()));
                pstmt.setLong(3, Long.parseLong(tfStokBar.getText().trim()));
                pstmt.setString(4, tfKodeBar.getText().trim());

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Data berhasil diupdate");
                    pstmt.close();
                    conn.close();
                    bersihBarang();
                    tabelBarang();
                } else {
                    JOptionPane.showMessageDialog(null, "Data tidak ditemukan");
                }
            } catch (SQLException ex) {
                Logger.getLogger(FrameAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnUpdateBarActionPerformed

    private void btnDeleteBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteBarActionPerformed
        if (tfKodeBar.getText().trim().isEmpty() || tfNamaBar.getText().trim().isEmpty() || tfHargaBar.getText().trim().isEmpty() || tfStokBar.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Isi semua data");
        } else {
            try {
                konek k = new konek();
                conn = k.getConnection();
                String pesan = "Yakin ingin menghapus data dengan ID: " + tfKodeBar.getText().trim();
                int jawab = JOptionPane.showConfirmDialog(null, pesan,
                        "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
                switch (jawab) {
                    case JOptionPane.YES_OPTION -> {
                        String deleteSql = "DELETE FROM barang WHERE kd_barang= ?";
                        pstmt = conn.prepareStatement(deleteSql);
                        pstmt.setString(1, tfKodeBar.getText().trim());
                        pstmt.executeUpdate();
                        pstmt.close();
                        bersihBarang();
                        tabelBarang();
                    }
                    case JOptionPane.NO_OPTION ->
                        JOptionPane.showMessageDialog(this, "Kamu menjawab tidak");
                }
                conn.close();
            } catch (SQLException ex) {
                JOptionPane.showConfirmDialog(null, "Cek Lagi!!!");
                Logger.getLogger(FrameAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnDeleteBarActionPerformed

    private void btnClearBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearBarActionPerformed
        bersihBarang();
    }//GEN-LAST:event_btnClearBarActionPerformed

    private void tabelBarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelBarMouseClicked
        int row = tabelBar.getSelectedRow();
        tfKodeBar.setText(tabelBar.getValueAt(row, 0).toString());
        tfNamaBar.setText(tabelBar.getValueAt(row, 1).toString());
        tfHargaBar.setText(tabelBar.getValueAt(row, 2).toString());
        tfStokBar.setText(tabelBar.getValueAt(row, 3).toString());
    }//GEN-LAST:event_tabelBarMouseClicked

    private void tfCariBarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfCariBarKeyReleased
        DefaultTableModel ob = (DefaultTableModel) tabelBar.getModel();
        TableRowSorter<DefaultTableModel> obj = new TableRowSorter<>(ob);
        obj.setRowFilter(RowFilter.regexFilter(tfCariBar.getText()));
        tabelBar.setRowSorter(obj);
    }//GEN-LAST:event_tfCariBarKeyReleased

    private void btnCetakBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakBarActionPerformed
        // TODO add your handling code here:
        try {
            konek k = new konek();
            conn = k.getConnection();
            JasperReport reports;
            String path = "src\\Report\\reportBarang.jasper";
            reports = (JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint print = JasperFillManager.fillReport(path, null, conn);
            JasperViewer view = new JasperViewer(print, false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);
        } catch (JRException e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_btnCetakBarActionPerformed

    private void btnCetakKurirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakKurirActionPerformed
        // TODO add your handling code here:
        try {
            konek k = new konek();
            conn = k.getConnection();
            JasperReport reports;
            String path = "src\\Report\\reportKurir.jasper";
            reports = (JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint print = JasperFillManager.fillReport(path, null, conn);
            JasperViewer view = new JasperViewer(print, false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);
        } catch (JRException e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_btnCetakKurirActionPerformed

    private void btnCetakSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakSupplierActionPerformed
        // TODO add your handling code here:
        try {
            konek k = new konek();
            conn = k.getConnection();
            JasperReport reports;
            String path = "src\\Report\\reportSupplier.jasper";
            reports = (JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint print = JasperFillManager.fillReport(path, null, conn);
            JasperViewer view = new JasperViewer(print, false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);
        } catch (JRException e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_btnCetakSupplierActionPerformed

    private void btnCetakSupplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakSupplyActionPerformed
        // TODO add your handling code here:
        try {
            konek k = new konek();
            conn = k.getConnection();
            JasperReport reports;
            String path = "src\\Report\\reportSupply.jasper";
            reports = (JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint print = JasperFillManager.fillReport(path, null, conn);
            JasperViewer view = new JasperViewer(print, false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);
        } catch (JRException e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_btnCetakSupplyActionPerformed

    private void btnCetakKaryawanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakKaryawanActionPerformed
        // TODO add your handling code here:
        try {
            konek k = new konek();
            conn = k.getConnection();
            JasperReport reports;
            String path = "src\\Report\\reportKaryawan.jasper";
            reports = (JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint print = JasperFillManager.fillReport(path, null, conn);
            JasperViewer view = new JasperViewer(print, false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);
        } catch (JRException e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_btnCetakKaryawanActionPerformed

    private void btnCetakKaryawan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakKaryawan1ActionPerformed
        // TODO add your handling code here:
        try {
            konek k = new konek();
            Connection conn = k.getConnection();

            // Tampilkan pop-up untuk input bulan
            String bulanInput = JOptionPane.showInputDialog("Masukkan bulan (1-12):");

            // Periksa jika input kosong atau dibatalkan
            if (bulanInput == null || bulanInput.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Bulan tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Konversi input bulan menjadi Integer
            int bulan;
            try {
                bulan = Integer.parseInt(bulanInput); // Konversi ke Integer
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Input harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Pastikan bulan dalam rentang 1-12
            if (bulan < 1 || bulan > 12) {
                JOptionPane.showMessageDialog(null, "Bulan harus dalam rentang 1-12!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Siapkan parameter untuk laporan
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("bulan", bulan); // Parameter 'bulan' diteruskan sebagai Integer

            // Load laporan JasperReports
            String path = "src\\Report\\reportPembelianBulanan.jasper";
            JasperReport reports = (JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint print = JasperFillManager.fillReport(path, parameters, conn);

            // Tampilkan laporan
            JasperViewer view = new JasperViewer(print, false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);

        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, "Gagal memuat laporan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

    }//GEN-LAST:event_btnCetakKaryawan1ActionPerformed

    private void lblKRWMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblKRWMouseClicked
        jTabbedPane1.setSelectedIndex(5);
        jMenuBar1.setVisible(true);
    }//GEN-LAST:event_lblKRWMouseClicked

    private void lblBrgMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBrgMouseClicked
        jTabbedPane1.setSelectedIndex(1);
        jMenuBar1.setVisible(true);
    }//GEN-LAST:event_lblBrgMouseClicked

    private void lblSTATMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSTATMouseClicked
        jTabbedPane1.setSelectedIndex(2);
        jMenuBar1.setVisible(true);
    }//GEN-LAST:event_lblSTATMouseClicked

    private void lblKRRMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblKRRMouseClicked
        jTabbedPane1.setSelectedIndex(3);
        jMenuBar1.setVisible(true);
    }//GEN-LAST:event_lblKRRMouseClicked

    private void lblSUPPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSUPPMouseClicked
        jTabbedPane1.setSelectedIndex(6);
        jMenuBar1.setVisible(true);
    }//GEN-LAST:event_lblSUPPMouseClicked

    private void lblSUPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSUPMouseClicked
        jTabbedPane1.setSelectedIndex(4);
        jMenuBar1.setVisible(true);
    }//GEN-LAST:event_lblSUPMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
//        System.exit(0);
        NewJFrame p = new NewJFrame();
        p.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutActionPerformed
//        System.exit(0);
        NewJFrame p = new NewJFrame();
        p.setVisible(true);
//        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_logoutActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrameAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameAdmin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Barang;
    private javax.swing.JPanel Dashboard;
    private javax.swing.JPanel Karyawan;
    private javax.swing.JPanel Kurir;
    private javax.swing.JPanel PanelPusat;
    private javax.swing.JPanel Stat;
    private javax.swing.JPanel Supplier;
    private javax.swing.JPanel Supply;
    private javax.swing.JMenuItem barang;
    private javax.swing.JButton btnCetakBar;
    private javax.swing.JButton btnCetakKaryawan;
    private javax.swing.JButton btnCetakKaryawan1;
    private javax.swing.JButton btnCetakKurir;
    private javax.swing.JButton btnCetakSupplier;
    private javax.swing.JButton btnCetakSupply;
    private javax.swing.JButton btnClearBar;
    private javax.swing.JButton btnClearKar;
    private javax.swing.JButton btnClearKur;
    private javax.swing.JButton btnClearSup;
    private javax.swing.JButton btnDeleteBar;
    private javax.swing.JButton btnDeleteKar;
    private javax.swing.JButton btnDeleteKur;
    private javax.swing.JButton btnDeleteSup;
    private javax.swing.JButton btnHapusSupp;
    private javax.swing.JButton btnInsertBar;
    private javax.swing.JButton btnInsertKar;
    private javax.swing.JButton btnInsertKur;
    private javax.swing.JButton btnInsertSup;
    private javax.swing.JButton btnKonfirmasiSupp;
    private javax.swing.JButton btnTambahSupp;
    private javax.swing.JButton btnUpdateBar;
    private javax.swing.JButton btnUpdateKar;
    private javax.swing.JButton btnUpdateKur;
    private javax.swing.JButton btnUpdateSup;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cbJabatan;
    private javax.swing.JMenuItem dashboard;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel jlab;
    private javax.swing.JMenuItem karyawan;
    private javax.swing.JMenuItem kurir;
    private javax.swing.JLabel lblBack;
    private javax.swing.JLabel lblBrg;
    private javax.swing.JLabel lblKRR;
    private javax.swing.JLabel lblKRW;
    private javax.swing.JLabel lblSTAT;
    private javax.swing.JLabel lblSUP;
    private javax.swing.JLabel lblSUPP;
    private javax.swing.JMenuItem logout;
    private javax.swing.JRadioButton rbLK;
    private javax.swing.JRadioButton rbLKKur;
    private javax.swing.JRadioButton rbPR;
    private javax.swing.JRadioButton rbPRKur;
    private javax.swing.JMenuItem stat;
    private javax.swing.JMenuItem supplier;
    private javax.swing.JMenuItem supply;
    private javax.swing.JTable tabelBar;
    private javax.swing.JTable tabelKar;
    private javax.swing.JTable tabelKur;
    private javax.swing.JTable tabelLap;
    private javax.swing.JTable tabelSup;
    private javax.swing.JTable tabelSupp;
    private javax.swing.JTextField tfAlamatKar;
    private javax.swing.JTextField tfAlamatKur;
    private javax.swing.JTextField tfCariBar;
    private javax.swing.JTextField tfCariKar;
    private javax.swing.JTextField tfCariKur;
    private javax.swing.JTextField tfCariSup;
    private javax.swing.JTextField tfEmailKar;
    private javax.swing.JTextField tfHargaBar;
    private javax.swing.JTextField tfIdKar;
    private javax.swing.JTextField tfIdKur;
    private javax.swing.JTextField tfIdSup;
    private javax.swing.JTextField tfKodeBar;
    private javax.swing.JTextField tfNamaBar;
    private javax.swing.JTextField tfNamaKar;
    private javax.swing.JTextField tfNamaKur;
    private javax.swing.JTextField tfNamaSup;
    private javax.swing.JTextField tfStokBar;
    private javax.swing.JTextField tfTelpKar;
    private javax.swing.JTextField tfTelpKur;
    private javax.swing.JTextField tfTelpSup;
    private javax.swing.JTextField txtidkrywn;
    private javax.swing.JTextField txtidsup;
    private javax.swing.JTextField txtjml;
    private javax.swing.JTextField txtkodebrg;
    private javax.swing.JLabel txtnamakyw;
    private javax.swing.JLabel txtnamasup;
    private javax.swing.JLabel txtnmbrng;
    private javax.swing.JTextField txtnosup;
    private javax.swing.JTextField txttanggal;
    private javax.swing.JLabel txttotal;
    // End of variables declaration//GEN-END:variables
}
