/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Frame;

import Koneksi.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.*;
import javax.swing.*;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.*;
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
public class FrameKasir extends javax.swing.JFrame {

    Connection conn;
    PreparedStatement pstmt;
    ResultSet rs;
    Statement stmt;

    /**
     * Creates new form FrameKasir
     */
    public FrameKasir() {
        initComponents();
        allContruction();
        noEdit();
    }

    private void allContruction() {
        tanggalKirim();
        tabelPeng();
        idAutoPengiriman();
        tabelSemPenj();
        cbokKyw();
        idAutoPembelian();
        idAutoPembayaran();
        cbokKyw();
        tanggal();
        idAutoPelanggan();
        tabelPel();
    }

    private void noEdit() {
        tfTanggalPeng.setEditable(false);
        tfIdPeng.setEditable(false);
        txtidpembelian.setEditable(false);
        txtidpembayaran.setEditable(false);
        tfIdPel.setEditable(false);
        txthargasatuan.setEditable(false);
        txttotalbrg.setEditable(false);
        txttotalharga.setEditable(false);
        cboxidkyw.addActionListener(e -> lookupKaryawan());
        jMenuBar1.setVisible(false);
    }

    public void bersihPel() {//frame pelanggan
        idAutoPelanggan();
        tfNamaPel.setText(null);
        tfTelpPel.setText(null);
        tfAlamatPel.setText(null);
    }

    public final void idAutoPelanggan() {//frame pelanggan
        String sql = "SELECT MAX(CAST(SUBSTRING(id_pelanggan FROM 4 FOR 4) AS INTEGER)) AS no "
                + "FROM pelanggan "
                + "WHERE id_pelanggan ~ '^P[0-9]{4}$'";
        try {
            konek k = new konek();
            conn = k.getConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                if (rs.getString("no") == null) {
                    tfIdPel.setText("P0001");
                } else {
                    int set_id = rs.getInt("no") + 1;
                    String no = String.format("%04d", set_id);
                    tfIdPel.setText("P" + no);
                }
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(FrameKasir.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat membuat ID barang.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void tabelPel() {//frame pelanggan
        DefaultTableModel tblPel = new DefaultTableModel(new String[]{"ID", "NAMA PELANGGAN", "TELEPON", "ALAMAT"}, 0);
        String sql = "SELECT * FROM pelanggan"; // Query SQL

        try {
            konek k = new konek();
            conn = k.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                tblPel.addRow(new Object[]{
                    rs.getString("id_pelanggan"),
                    rs.getString("nama"),
                    rs.getString("telp"),
                    rs.getString("alamat"),});
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(FrameKasir.class.getName()).log(Level.SEVERE, null, ex);
        }

        tabelPel.setModel(tblPel);
    }

    private void tanggal() {//frame penjualan
        java.util.Date date = new java.util.Date();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        txttanggal.setText(s.format(date));
    }

    public void tanggalKirim() {//frame pengiriman
        java.util.Date date = new java.util.Date();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");

        tfTanggalPeng.setText(s.format(date));
    }

    public void tabelPeng() {//frame pengiriman
        konek k = new konek();
        conn = k.getConnection();
        DefaultTableModel tblPeng = new DefaultTableModel(new String[]{"NO PEMBELIAN", "ID PELANGGAN", "TANGGAL", "TOTAL BARANG"}, 0);
        String sql = "SELECT * from pembelian;";

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                tblPeng.addRow(new Object[]{
                    rs.getString("no_pembelian"),
                    rs.getString("id_pelanggan"),
                    rs.getDate("tanggal"),
                    rs.getInt("total_barang")
                });
            }
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(FrameKasir.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        tabelRelPeng.setModel(tblPeng);

        DefaultTableModel model = new DefaultTableModel();

        tabelSemPeng.setModel(model);

        model.addColumn("ID PENGIRIMAN");
        model.addColumn("NO PEMBELIAN");
        model.addColumn("ID KURIR");
        model.addColumn("BIAYA");
    }

    public final void idAutoPengiriman() {//frame pengiriman
        String sql = "SELECT MAX(CAST(SUBSTRING(id_pengiriman FROM 4 FOR 4) AS INTEGER)) AS no "
                + "FROM pengiriman "
                + "WHERE id_pengiriman ~ '^PG[0-9]{4}$'";
        try {
            konek k = new konek();
            conn = k.getConnection();
            conn = new konek().getConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                if (rs.getString("no") == null) {
                    tfIdPeng.setText("PG0001");
                } else {
                    int set_id = rs.getInt("no") + 1;
                    String no = String.format("%04d", set_id);
                    tfIdPeng.setText("PG" + no);
                }
            }
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(FrameKasir.class
                    .getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat membuat ID pembelian.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void kosongPeng() {//frame pengiriman
        DefaultTableModel model = (DefaultTableModel) tabelSemPeng.getModel();

        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
        idAutoPengiriman();
        txtIdKurPeng.setText("");
        txtNoPembePeng.setText("-");
        txtLblNamaPeng.setText("-");
        txtBiaya.setText("-");
    }

    public void tabelSemPenj() {//frame penjualan
        DefaultTableModel model = new DefaultTableModel();

        tblsemupsn.setModel(model);

        model.addColumn("Id pesan");
        model.addColumn("Id pel");
        model.addColumn("Id krywn");
        model.addColumn("kode brg");
        model.addColumn("nama brg");
        model.addColumn("harga");
        model.addColumn("jumlah");

    }

    private void cbokKyw() {//frame penjualan
        try {
            konek k = new konek();
            conn = k.getConnection();
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            String sql = "SELECT DISTINCT id_karyawan FROM karyawan";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id_karyawan");
                model.addElement(id);
            }
            cboxidkyw.setModel(model);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hitungTotalHarga() {//frame penjualan
        int totalStok = 0;
        int jumlahBaris = tblsemupsn.getRowCount();

        for (int i = 0; i < jumlahBaris; i++) {
            if (tblsemupsn.getValueAt(i, 5) != null && tblsemupsn.getValueAt(i, 6) != null) {
                try {
                    int harga = Integer.parseInt(tblsemupsn.getValueAt(i, 5).toString());
                    int jumlah = Integer.parseInt(tblsemupsn.getValueAt(i, 6).toString());
                    totalStok += harga * jumlah;
                } catch (NumberFormatException e) {
                    System.out.println("Format stok salah di baris: " + i);
                }
            }
        }
        txttotalharga.setText("" + totalStok);
    }

    private void hitungTotalBarang() {//frame penjualan
        int totalJumlah = 0;
        int jumlahBaris = tblsemupsn.getRowCount();

        for (int i = 0; i < jumlahBaris; i++) {
            if (tblsemupsn.getValueAt(i, 6) != null) {
                try {
                    int jumlah = Integer.parseInt(tblsemupsn.getValueAt(i, 6).toString());
                    totalJumlah += jumlah;
                } catch (NumberFormatException e) {
                    System.out.println("Format stok salah di baris: " + i);
                }
            }
        }
        txttotalbrg.setText("" + totalJumlah);
    }

    public final void idAutoPembelian() {//frame penjualan
        try {
            conn = new konek().getConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(
                    "SELECT MAX(CAST(SUBSTRING(no_pembelian FROM 4 FOR LENGTH(no_pembelian) - 3) AS INTEGER)) AS no FROM pembelian"
            );
            if (rs.next()) {
                if (rs.getString("no") == null) {
                    txtidpembelian.setText("PMB0001");
                } else {
                    int set_id = rs.getInt("no") + 1;
                    String no = String.format("%04d", set_id);
                    txtidpembelian.setText("PMB" + no);
                }
            }
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(FrameKasir.class
                    .getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat membuat ID pembelian.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public final void idAutoPembayaran() {//frame penjualan
        try {
            conn = new konek().getConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); // Query untuk mendapatkan nilai maksimum dari bagian numerik ID
            rs = stmt.executeQuery(
                    "SELECT MAX(CAST(SUBSTRING(id_pembayaran FROM 4 FOR LENGTH(id_pembayaran) - 3) AS INTEGER)) AS no FROM pembayaran"
            );
            if (rs.next()) {
                if (rs.getString("no") == null) {
                    txtidpembayaran.setText("BYR0001");
                } else {
                    int set_id = rs.getInt("no") + 1;
                    String no = String.format("%04d", set_id);
                    txtidpembayaran.setText("BYR" + no);
                }
            }
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(FrameKasir.class
                    .getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat membuat ID pembayaran.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void lookupPelanggan() {//frame penjualan
        String idPel = txtidpel.getText().trim();
        String sql = "SELECT nama FROM pelanggan WHERE id_pelanggan = ?";

        try {
            konek k = new konek();
            conn = k.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, idPel);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                String nama = rs.getString("nama");
                txtnamapel.setText(nama);
            } else {
                JOptionPane.showMessageDialog(null, "Anggota tidak terdaftar", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void lookupBarang() {//frame penjualan
        String idBr = txtkodebrg.getText().trim();
        String sql = "SELECT * FROM barang WHERE kd_barang = ?";

        try {
            konek k = new konek();
            conn = k.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, idBr);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                String nama = rs.getString("nama_barang");
                int harga = rs.getInt("harga");
                txtnamabrg.setText(nama);
                txthargasatuan.setText(String.valueOf(harga));
            } else {
                JOptionPane.showMessageDialog(null, "Barang tidak ada", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void lookupKaryawan() {//frame penjualan
        if (cboxidkyw.getSelectedItem() == null) {
            return;
        }

        String idKyw = cboxidkyw.getSelectedItem().toString().trim();
        String sql = "SELECT nama FROM karyawan WHERE id_karyawan = ?";

        try {
            konek k = new konek();
            conn = k.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, idKyw);
            rs = pstmt.executeQuery();

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

    public void kosongPenj() {//frame penjualan
        DefaultTableModel model = (DefaultTableModel) tblsemupsn.getModel();

        while (model.getRowCount() > 0) {
            model.removeRow(0);
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

        PanelPusat = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        Dasboard = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lblPelanggan = new javax.swing.JLabel();
        lblPenjualan = new javax.swing.JLabel();
        lblPengiriman = new javax.swing.JLabel();
        Pelanggan = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnInsertPel = new javax.swing.JButton();
        btnUpdatePel = new javax.swing.JButton();
        btnClearPel = new javax.swing.JButton();
        tfIdPel = new javax.swing.JTextField();
        tfNamaPel = new javax.swing.JTextField();
        tfTelpPel = new javax.swing.JTextField();
        tfAlamatPel = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelPel = new javax.swing.JTable();
        btnCetakBar = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        Penjualan = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtidpembelian = new javax.swing.JTextField();
        txtidpel = new javax.swing.JTextField();
        txtkodebrg = new javax.swing.JTextField();
        txthargasatuan = new javax.swing.JTextField();
        cboxidkyw = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        Nama = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtnamapel = new javax.swing.JTextField();
        txtnamakyw = new javax.swing.JLabel();
        txtnamabrg = new javax.swing.JLabel();
        txtjumlahbeli = new javax.swing.JTextField();
        btnInsertPen = new javax.swing.JButton();
        btnHapusPen = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblsemupsn = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        btnKonfirmasiPen = new javax.swing.JButton();
        txtidpembayaran = new javax.swing.JTextField();
        txttotalbrg = new javax.swing.JTextField();
        txttotalharga = new javax.swing.JTextField();
        cboxmetodpmbayaran = new javax.swing.JComboBox<>();
        txttanggal = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        Pengiriman = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelRelPeng = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        tfIdPeng = new javax.swing.JTextField();
        txtIdKurPeng = new javax.swing.JTextField();
        txtBiaya = new javax.swing.JLabel();
        txtNoPembePeng = new javax.swing.JLabel();
        txtLblNamaPeng = new javax.swing.JLabel();
        tfTanggalPeng = new javax.swing.JTextField();
        btnInputPeng = new javax.swing.JButton();
        btnHapusPeng = new javax.swing.JButton();
        btnBersihPeng = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelSemPeng = new javax.swing.JTable();
        btnKonfirmasiPeng = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        PanelPusat.setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane1.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));

        Dasboard.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setText("Log Out");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        Dasboard.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 470, 220, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Aset/TampilanKasir.jpg"))); // NOI18N
        Dasboard.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 880, 580));

        lblPelanggan.setText("jLabel30");
        lblPelanggan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblPelangganMouseClicked(evt);
            }
        });
        Dasboard.add(lblPelanggan, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 100, 130, 380));

        lblPenjualan.setText("jLabel30");
        lblPenjualan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblPenjualanMouseClicked(evt);
            }
        });
        Dasboard.add(lblPenjualan, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 100, 120, 380));

        lblPengiriman.setText("jLabel30");
        lblPengiriman.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblPengirimanMouseClicked(evt);
            }
        });
        Dasboard.add(lblPengiriman, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 100, 130, 380));

        jTabbedPane1.addTab("tab1", Dasboard);

        Pelanggan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setText("Pelanggan");
        Pelanggan.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 52, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("ID Pelanggan");
        Pelanggan.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 133, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Nama");
        Pelanggan.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 176, 37, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("ALamat");
        Pelanggan.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 267, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("No Telepon");
        Pelanggan.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 219, 85, -1));

        btnInsertPel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnInsertPel.setText("Insert");
        btnInsertPel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertPelActionPerformed(evt);
            }
        });
        Pelanggan.add(btnInsertPel, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 305, 100, 35));

        btnUpdatePel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnUpdatePel.setText("Update");
        btnUpdatePel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdatePelActionPerformed(evt);
            }
        });
        Pelanggan.add(btnUpdatePel, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 305, 100, 35));

        btnClearPel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnClearPel.setText("Clear");
        btnClearPel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearPelActionPerformed(evt);
            }
        });
        Pelanggan.add(btnClearPel, new org.netbeans.lib.awtextra.AbsoluteConstraints(281, 305, 100, 35));

        tfIdPel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Pelanggan.add(tfIdPel, new org.netbeans.lib.awtextra.AbsoluteConstraints(125, 132, 250, -1));

        tfNamaPel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Pelanggan.add(tfNamaPel, new org.netbeans.lib.awtextra.AbsoluteConstraints(125, 175, 250, -1));

        tfTelpPel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Pelanggan.add(tfTelpPel, new org.netbeans.lib.awtextra.AbsoluteConstraints(125, 218, 250, -1));

        tfAlamatPel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Pelanggan.add(tfAlamatPel, new org.netbeans.lib.awtextra.AbsoluteConstraints(125, 266, 250, -1));

        tabelPel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tabelPel.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelPel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelPelMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelPel);

        Pelanggan.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(403, 132, -1, 206));

        btnCetakBar.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnCetakBar.setText("Cetak");
        btnCetakBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakBarActionPerformed(evt);
            }
        });
        Pelanggan.add(btnCetakBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 352, 100, 35));

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/NewTampilan/Pelanggan.jpg"))); // NOI18N
        Pelanggan.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 580));

        jTabbedPane1.addTab("tab1", Pelanggan);

        Penjualan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setText("Penjualan");
        Penjualan.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 61, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("ID Pembelian");
        Penjualan.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 119, 85, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("ID Pelanggan");
        Penjualan.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 163, -1, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("ID Karyawan");
        Penjualan.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 202, 85, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Kode Barang");
        Penjualan.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 255, 85, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("Harga Satuan");
        Penjualan.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 298, -1, -1));

        txtidpembelian.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Penjualan.add(txtidpembelian, new org.netbeans.lib.awtextra.AbsoluteConstraints(141, 116, 130, -1));

        txtidpel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtidpel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtidpelActionPerformed(evt);
            }
        });
        Penjualan.add(txtidpel, new org.netbeans.lib.awtextra.AbsoluteConstraints(141, 160, 130, -1));

        txtkodebrg.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtkodebrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtkodebrgActionPerformed(evt);
            }
        });
        Penjualan.add(txtkodebrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(141, 252, 130, -1));

        txthargasatuan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Penjualan.add(txthargasatuan, new org.netbeans.lib.awtextra.AbsoluteConstraints(141, 295, 130, -1));

        cboxidkyw.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cboxidkyw.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        Penjualan.add(cboxidkyw, new org.netbeans.lib.awtextra.AbsoluteConstraints(141, 199, 130, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("Nama");
        Penjualan.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(289, 163, 43, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setText("Nama");
        Penjualan.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(289, 202, 43, -1));

        Nama.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Nama.setText("Nama");
        Penjualan.add(Nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(289, 255, 43, -1));

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setText("Jumlah Beli");
        Penjualan.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(289, 298, 77, -1));

        txtnamapel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Penjualan.add(txtnamapel, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 160, 145, -1));

        txtnamakyw.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtnamakyw.setText("-");
        Penjualan.add(txtnamakyw, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 202, 145, -1));

        txtnamabrg.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtnamabrg.setText("-");
        Penjualan.add(txtnamabrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 255, 145, -1));

        txtjumlahbeli.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Penjualan.add(txtjumlahbeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(372, 295, 89, -1));

        btnInsertPen.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnInsertPen.setText("Tambah");
        btnInsertPen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertPenActionPerformed(evt);
            }
        });
        Penjualan.add(btnInsertPen, new org.netbeans.lib.awtextra.AbsoluteConstraints(151, 336, -1, 35));

        btnHapusPen.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnHapusPen.setText("Hapus");
        btnHapusPen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusPenActionPerformed(evt);
            }
        });
        Penjualan.add(btnHapusPen, new org.netbeans.lib.awtextra.AbsoluteConstraints(274, 336, 100, 35));

        tblsemupsn.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblsemupsn.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblsemupsn);

        Penjualan.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 383, 505, 180));

        jButton6.setText("Tambah");
        Penjualan.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(507, 161, -1, -1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel20.setText("ID Pembayaran");
        Penjualan.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(531, 202, -1, -1));

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel21.setText("Total Barang");
        Penjualan.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(531, 234, -1, -1));

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel22.setText("Total Harga");
        Penjualan.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(531, 266, -1, -1));

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel23.setText("Metode Pembayaran");
        Penjualan.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 298, -1, -1));

        btnKonfirmasiPen.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnKonfirmasiPen.setText("Konfirmasi");
        btnKonfirmasiPen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKonfirmasiPenActionPerformed(evt);
            }
        });
        Penjualan.add(btnKonfirmasiPen, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 380, 259, 50));

        txtidpembayaran.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Penjualan.add(txtidpembayaran, new org.netbeans.lib.awtextra.AbsoluteConstraints(658, 199, 209, -1));

        txttotalbrg.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Penjualan.add(txttotalbrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(658, 234, 209, -1));

        txttotalharga.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Penjualan.add(txttotalharga, new org.netbeans.lib.awtextra.AbsoluteConstraints(658, 263, 209, -1));

        cboxmetodpmbayaran.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cboxmetodpmbayaran.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cash", "Debit", "E-Wallet" }));
        Penjualan.add(cboxmetodpmbayaran, new org.netbeans.lib.awtextra.AbsoluteConstraints(658, 295, 209, -1));

        txttanggal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txttanggal.setText("jLabel18");
        Penjualan.add(txttanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(764, 71, 103, -1));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/NewTampilan/Penjualan.jpg"))); // NOI18N
        Penjualan.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 580));

        jTabbedPane1.addTab("tab1", Penjualan);

        Pengiriman.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setText("Pengiriman");
        Pengiriman.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 61, -1, -1));

        tabelRelPeng.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tabelRelPeng.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelRelPeng.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelRelPengMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tabelRelPeng);

        Pengiriman.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 96, 868, 98));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("ID Pengiriman");
        Pengiriman.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 226, -1, -1));

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel24.setText("ID Kurir");
        Pengiriman.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 268, -1, -1));

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel25.setText("Biaya");
        Pengiriman.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 312, 43, -1));

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel26.setText("No Pembelian");
        Pengiriman.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(395, 235, 94, -1));

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel27.setText("Nama Kurir");
        Pengiriman.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(395, 279, -1, -1));

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel28.setText("Tanggal");
        Pengiriman.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(608, 209, 62, -1));

        tfIdPeng.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Pengiriman.add(tfIdPeng, new org.netbeans.lib.awtextra.AbsoluteConstraints(122, 223, 245, -1));

        txtIdKurPeng.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtIdKurPeng.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdKurPengActionPerformed(evt);
            }
        });
        Pengiriman.add(txtIdKurPeng, new org.netbeans.lib.awtextra.AbsoluteConstraints(122, 265, 245, -1));

        txtBiaya.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtBiaya.setText("-");
        Pengiriman.add(txtBiaya, new org.netbeans.lib.awtextra.AbsoluteConstraints(122, 312, 182, -1));

        txtNoPembePeng.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNoPembePeng.setText("-");
        Pengiriman.add(txtNoPembePeng, new org.netbeans.lib.awtextra.AbsoluteConstraints(497, 235, 173, -1));

        txtLblNamaPeng.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtLblNamaPeng.setText("-");
        Pengiriman.add(txtLblNamaPeng, new org.netbeans.lib.awtextra.AbsoluteConstraints(497, 279, 173, -1));

        tfTanggalPeng.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Pengiriman.add(tfTanggalPeng, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 206, 144, -1));

        btnInputPeng.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnInputPeng.setText("Input");
        btnInputPeng.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInputPengActionPerformed(evt);
            }
        });
        Pengiriman.add(btnInputPeng, new org.netbeans.lib.awtextra.AbsoluteConstraints(487, 314, 105, 37));

        btnHapusPeng.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnHapusPeng.setText("Hapus");
        btnHapusPeng.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusPengActionPerformed(evt);
            }
        });
        Pengiriman.add(btnHapusPeng, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 314, 105, 37));

        btnBersihPeng.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnBersihPeng.setText("Bersih");
        btnBersihPeng.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBersihPengActionPerformed(evt);
            }
        });
        Pengiriman.add(btnBersihPeng, new org.netbeans.lib.awtextra.AbsoluteConstraints(733, 314, 105, 37));

        tabelSemPeng.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tabelSemPeng.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(tabelSemPeng);

        Pengiriman.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 363, 868, 144));

        btnKonfirmasiPeng.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnKonfirmasiPeng.setText("Konfirmasi");
        btnKonfirmasiPeng.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKonfirmasiPengActionPerformed(evt);
            }
        });
        Pengiriman.add(btnKonfirmasiPeng, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 525, 868, 48));

        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/NewTampilan/Pengiriman.jpg"))); // NOI18N
        Pengiriman.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 580));

        jTabbedPane1.addTab("tab1", Pengiriman);

        jPanel1.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -60, 880, 610));

        PanelPusat.add(jPanel1, java.awt.BorderLayout.CENTER);

        getContentPane().add(PanelPusat, java.awt.BorderLayout.CENTER);

        jMenu1.setText("Profile");
        jMenu1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jMenuItem1.setText("Dashboard");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Log Out");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Menu");
        jMenu2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jMenuItem3.setText("Pelanggan");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuItem4.setText("Penjualan");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuItem5.setText("Pengiriman");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        jTabbedPane1.setSelectedIndex(1);
        jMenuBar1.setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        jTabbedPane1.setSelectedIndex(2);
        jMenuBar1.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        jTabbedPane1.setSelectedIndex(3);
        jMenuBar1.setVisible(true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        jTabbedPane1.setSelectedIndex(0);
        jMenuBar1.setVisible(false);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void tabelRelPengMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelRelPengMouseClicked
        int row = tabelRelPeng.getSelectedRow();
        txtNoPembePeng.setText(tabelRelPeng.getValueAt(row, 0).toString());
        txtBiaya.setText("50000");
    }//GEN-LAST:event_tabelRelPengMouseClicked

    private void txtIdKurPengActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdKurPengActionPerformed
        try {//frame pengiriman
            konek k = new konek();
            conn = k.getConnection();
            String sql = "SELECT * FROM kurir WHERE id_kurir=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, txtIdKurPeng.getText());
            rs = pstmt.executeQuery();

            if (rs.next()) { // Pindah ke baris pertama jika ada
                txtLblNamaPeng.setText(rs.getString("nama"));
            } else {
                JOptionPane.showMessageDialog(null, "Data tidak ditemukan");
            }
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(FrameKasir.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txtIdKurPengActionPerformed

    private void btnInputPengActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInputPengActionPerformed
        DefaultTableModel model = (DefaultTableModel) tabelSemPeng.getModel();//frame pengiriman
        model.addRow(new Object[]{
            tfIdPeng.getText(),
            txtNoPembePeng.getText(),
            txtIdKurPeng.getText(),
            txtBiaya.getText()
        });
        txtNoPembePeng.setText("-");
        txtBiaya.setText("-");
    }//GEN-LAST:event_btnInputPengActionPerformed

    private void btnBersihPengActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBersihPengActionPerformed
        kosongPeng();//frame pengiriman
    }//GEN-LAST:event_btnBersihPengActionPerformed

    private void btnHapusPengActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusPengActionPerformed
        DefaultTableModel model = (DefaultTableModel) tabelSemPeng.getModel();//frame pengiriman
        int row = tabelSemPeng.getSelectedRow();
        model.removeRow(row);
    }//GEN-LAST:event_btnHapusPengActionPerformed

    private void btnKonfirmasiPengActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKonfirmasiPengActionPerformed
        try {//frame pengiriman
            konek k = new konek();
            conn = k.getConnection();
            conn.setAutoCommit(false);

            String sqlPengiriman = "INSERT INTO pengiriman (id_pengiriman, id_kurir, tanggal_pengiriman, biaya) VALUES (?, ?, ?, ?)";
            String sqlDetailPeng = "INSERT INTO detail_pengiriman (id_pengiriman, no_pembelian) VALUES (?, ?)";

            Set<String> noSupplySet = new HashSet<>();

            for (int i = 0; i < tabelSemPeng.getRowCount(); i++) {
                String idPeng = tabelSemPeng.getValueAt(i, 0).toString();
                String noPem = tabelSemPeng.getValueAt(i, 1).toString();
                String idKurir = tabelSemPeng.getValueAt(i, 2).toString();
                String bIaya = tabelSemPeng.getValueAt(i, 3).toString();
                String tanggal = tfTanggalPeng.getText();

                if (!noSupplySet.contains(idPeng)) {
                    try (PreparedStatement pstmtPeminjaman = conn.prepareStatement(sqlPengiriman)) {
                        pstmtPeminjaman.setString(1, idPeng);
                        pstmtPeminjaman.setString(2, idKurir);
                        pstmtPeminjaman.setDate(3, java.sql.Date.valueOf(tanggal));
                        pstmtPeminjaman.setInt(4, Integer.parseInt(bIaya));
                        pstmtPeminjaman.executeUpdate();
                        noSupplySet.add(idPeng);
                    }
                }
            }

            for (int i = 0; i < tabelSemPeng.getRowCount(); i++) {
                String idPengi = tabelSemPeng.getValueAt(i, 0).toString();
                String noPemb = (String) tabelSemPeng.getValueAt(i, 1);

                try (PreparedStatement pstmtDetail = conn.prepareStatement(sqlDetailPeng)) {
                    pstmtDetail.setString(1, idPengi);
                    pstmtDetail.setString(2, noPemb);
                    pstmtDetail.executeUpdate();
                }
            }

            conn.commit();
            conn.close();
            JOptionPane.showMessageDialog(null, "Pengiriman berhasil diatur!");
            kosongPeng();
            tabelPeng();
        } catch (SQLException ex) {
            if (conn != null) {
                try {
                    conn.rollback();
                    JOptionPane.showMessageDialog(null, "Transaksi dibatalkan: " + ex.getMessage());
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_btnKonfirmasiPengActionPerformed

    private void btnKonfirmasiPenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKonfirmasiPenActionPerformed
        Connection conn = null;
        try {
            konek k = new konek();
            conn = k.getConnection();
            conn.setAutoCommit(false); // Disable auto-commit

            // SQL Statements
            String sqlPembelian = "INSERT INTO pembelian (no_pembelian, id_pelanggan, id_karyawan, tanggal, total_barang, total_harga) VALUES (?, ?, ?, ?, ?, ?)";
            String sqlDetail = "INSERT INTO detail_pembelian (no_pembelian, kd_barang, jumlah_pembelian) VALUES (?, ?, ?)";
            String sqlPembayaran = "INSERT INTO pembayaran (no_pembelian, id_pembayaran, metode_pem, tanggal_pem) VALUES (?, ?, ?, ?)";

            Set<String> idPembelian = new HashSet<>();
            String idPelanggan = "";
            String idKaryawan = "";
            String idPmb = "";
            String idPmbyrn = "";
            String tanggal = "";
            String totalBarang = "";
            String totalHarga = "";
            String metodByr = "";

            for (int i = 0; i < tblsemupsn.getRowCount(); i++) {
                String idBeli = tblsemupsn.getValueAt(i, 0).toString();
                idPelanggan = (String) tblsemupsn.getValueAt(i, 1).toString();
                idKaryawan = (String) tblsemupsn.getValueAt(i, 2).toString();
                tanggal = txttanggal.getText();
                totalBarang = txttotalbrg.getText();
                totalHarga = txttotalharga.getText();

                if (!idPembelian.contains(idBeli)) {
                    try (PreparedStatement pstmtPeminjaman = conn.prepareStatement(sqlPembelian)) {
                        pstmtPeminjaman.setString(1, idBeli);
                        pstmtPeminjaman.setString(2, idPelanggan);
                        pstmtPeminjaman.setString(3, idKaryawan);
                        pstmtPeminjaman.setDate(4, java.sql.Date.valueOf(tanggal));
                        pstmtPeminjaman.setInt(5, Integer.parseInt(totalBarang));
                        pstmtPeminjaman.setInt(6, Integer.parseInt(totalHarga));
                        pstmtPeminjaman.executeUpdate();
                        idPembelian.add(idBeli);
                    }
                }
            }

            for (int i = 0; i < tblsemupsn.getRowCount(); i++) {
                String idBeli = tblsemupsn.getValueAt(i, 0).toString();
                String kdBrg = (String) tblsemupsn.getValueAt(i, 3);
                String jumlah = tblsemupsn.getValueAt(i, 6).toString();

                try (PreparedStatement pstmtDetail = conn.prepareStatement(sqlDetail)) {
                    pstmtDetail.setString(1, idBeli);
                    pstmtDetail.setString(2, kdBrg);
                    pstmtDetail.setLong(3, Long.parseLong(jumlah));
                    pstmtDetail.executeUpdate();
                }
            }

            idPmb = txtidpembelian.getText();
            idPmbyrn = txtidpembayaran.getText();
            metodByr = cboxmetodpmbayaran.getSelectedItem().toString();

            try (PreparedStatement pstmtPembayaran = conn.prepareStatement(sqlPembayaran)) {
                pstmtPembayaran.setString(1, idPmb);
                pstmtPembayaran.setString(2, idPmbyrn);
                pstmtPembayaran.setString(3, metodByr);
                pstmtPembayaran.setDate(4, java.sql.Date.valueOf(tanggal));
                pstmtPembayaran.executeUpdate();
            }

            idAutoPembayaran();
            idAutoPembelian();
            // Inserting into pembelian, detail_pembelian, and pembayaran (same as previous code)
            conn.commit();

            JOptionPane.showMessageDialog(null, "Pembelian berhasil!");
            try {
                conn = k.getConnection();
                JasperReport reports;
                String path = "src\\Report\\NotaPembelian.jasper";

                // Buat map parameter untuk JasperReports
                Map<String, Object> parameters = new HashMap<>();
                parameters.put("no_pembelian", txtidpembelian.getText()); // Ambil nilai dari txtidpembelian

                reports = (JasperReport) JRLoader.loadObjectFromFile(path);
                JasperPrint print = JasperFillManager.fillReport(path, parameters, conn); // Tambahkan parameter ke laporan

                JasperViewer view = new JasperViewer(print, false);
                view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                view.setVisible(true);
            } catch (JRException e) {
                System.out.println(e.getMessage());

            }

            kosongPenj();

        } catch (SQLException ex) {
            try {
                if (conn != null) {
                    conn.rollback(); // Rollback in case of error
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, "Transaksi dibatalkan: " + ex.getMessage());
        } finally {
            // Clear text fields after the transaction
            txtidpel.setText("");
            txtnamapel.setText("");
            txtnamakyw.setText("");
            txtkodebrg.setText("");
            txtnamabrg.setText("");
            txthargasatuan.setText("");
            txtjumlahbeli.setText("");
            txttotalbrg.setText("0");
            txttotalharga.setText("0");
            idAutoPembayaran();
            idAutoPembelian();
        }
    }//GEN-LAST:event_btnKonfirmasiPenActionPerformed

    private void txtidpelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtidpelActionPerformed
        lookupPelanggan();//frame penjualan
    }//GEN-LAST:event_txtidpelActionPerformed

    private void btnHapusPenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusPenActionPerformed
        DefaultTableModel model = (DefaultTableModel) tblsemupsn.getModel();//frame penjualan
        int row = tblsemupsn.getSelectedRow();
        model.removeRow(row);

        hitungTotalBarang();
        hitungTotalHarga();
    }//GEN-LAST:event_btnHapusPenActionPerformed

    private void btnInsertPenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertPenActionPerformed
        DefaultTableModel model = (DefaultTableModel) tblsemupsn.getModel();//frame penjualan
        model.addRow(new Object[]{
            txtidpembelian.getText(),
            txtidpel.getText(),
            cboxidkyw.getSelectedItem().toString(),
            txtkodebrg.getText(),
            txtnamabrg.getText(),
            txthargasatuan.getText(),
            txtjumlahbeli.getText()
        });

        hitungTotalHarga();
        hitungTotalBarang();
        txtkodebrg.setText("");
        txtnamabrg.setText("");
        txthargasatuan.setText("");
        txtjumlahbeli.setText("");
    }//GEN-LAST:event_btnInsertPenActionPerformed

    private void txtkodebrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtkodebrgActionPerformed
        lookupBarang();//frame penjualan
    }//GEN-LAST:event_txtkodebrgActionPerformed

    private void btnInsertPelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertPelActionPerformed
        if (tfIdPel.getText().trim().isEmpty() || tfNamaPel.getText().trim().isEmpty() || tfTelpPel.getText().trim().isEmpty() || tfAlamatPel.getText().trim().isEmpty()) {//frame pelanggan
            JOptionPane.showMessageDialog(null, "Isi semua data", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } else {
            String sql = "INSERT INTO pelanggan (id_pelanggan, nama, telp, alamat) VALUES (?, ?, ?, ?)";
            try {
                konek k = new konek();
                conn = k.getConnection();
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, tfIdPel.getText().trim());
                pstmt.setString(2, tfNamaPel.getText().trim());
                pstmt.setString(3, tfTelpPel.getText().trim());
                pstmt.setString(4, tfAlamatPel.getText().trim());

                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                conn.close();

                bersihPel();
                tabelPel();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat menyimpan data: " + ex.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(FrameKasir.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if (pstmt != null) {
                        pstmt.close();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(FrameKasir.class.getName()).log(Level.SEVERE, "Gagal menutup koneksi", ex);
                }
            }
        }
    }//GEN-LAST:event_btnInsertPelActionPerformed

    private void btnUpdatePelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdatePelActionPerformed
        if (tfIdPel.getText().trim().isEmpty() || tfNamaPel.getText().trim().isEmpty() || tfTelpPel.getText().trim().isEmpty() || tfAlamatPel.getText().trim().isEmpty()) {//frame pelanggan
            JOptionPane.showMessageDialog(null, "Isi semua data");
        } else {
            try {
                konek k = new konek();
                conn = k.getConnection();
                String sql = "UPDATE pelanggan SET nama=?, telp=?, alamat=? where id_pelanggan=?";
                pstmt = conn.prepareStatement(sql);

                pstmt.setString(1, tfNamaPel.getText().trim());
                pstmt.setString(2, tfTelpPel.getText().trim());
                pstmt.setString(3, tfAlamatPel.getText().trim());
                pstmt.setString(4, tfIdPel.getText().trim());

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Data berhasil diupdate");
                    pstmt.close();
                    conn.close();
                    bersihPel();
                    tabelPel();
                } else {
                    JOptionPane.showMessageDialog(null, "Data tidak ditemukan");
                }
            } catch (SQLException ex) {
                Logger.getLogger(FrameKasir.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnUpdatePelActionPerformed

    private void tabelPelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelPelMouseClicked
        int row = tabelPel.getSelectedRow();//frame pelanggan
        tfIdPel.setText(tabelPel.getValueAt(row, 0).toString());
        tfNamaPel.setText(tabelPel.getValueAt(row, 1).toString());
        tfTelpPel.setText(tabelPel.getValueAt(row, 2).toString());
        tfAlamatPel.setText(tabelPel.getValueAt(row, 3).toString());
    }//GEN-LAST:event_tabelPelMouseClicked

    private void btnClearPelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearPelActionPerformed
        bersihPel();//frame pelanggan
    }//GEN-LAST:event_btnClearPelActionPerformed

    private void btnCetakBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakBarActionPerformed
        // TODO add your handling code here:
        try {
            konek k = new konek();
            conn = k.getConnection();
            JasperReport reports;
            String path = "src\\Report\\reportPelanggan.jasper";
            reports = (JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint print = JasperFillManager.fillReport(path, null, conn);
            JasperViewer view = new JasperViewer(print, false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);
        } catch (JRException e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_btnCetakBarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        NewJFrame p = new NewJFrame();
        p.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        NewJFrame p = new NewJFrame();
        p.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void lblPelangganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPelangganMouseClicked
        jTabbedPane1.setSelectedIndex(1);
        jMenuBar1.setVisible(true);
    }//GEN-LAST:event_lblPelangganMouseClicked

    private void lblPenjualanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPenjualanMouseClicked
        jTabbedPane1.setSelectedIndex(2);
        jMenuBar1.setVisible(true);
    }//GEN-LAST:event_lblPenjualanMouseClicked

    private void lblPengirimanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPengirimanMouseClicked
        jTabbedPane1.setSelectedIndex(3);
        jMenuBar1.setVisible(true);
    }//GEN-LAST:event_lblPengirimanMouseClicked

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
            java.util.logging.Logger.getLogger(FrameKasir.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameKasir.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameKasir.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameKasir.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameKasir().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Dasboard;
    private javax.swing.JLabel Nama;
    private javax.swing.JPanel PanelPusat;
    private javax.swing.JPanel Pelanggan;
    private javax.swing.JPanel Pengiriman;
    private javax.swing.JPanel Penjualan;
    private javax.swing.JButton btnBersihPeng;
    private javax.swing.JButton btnCetakBar;
    private javax.swing.JButton btnClearPel;
    private javax.swing.JButton btnHapusPen;
    private javax.swing.JButton btnHapusPeng;
    private javax.swing.JButton btnInputPeng;
    private javax.swing.JButton btnInsertPel;
    private javax.swing.JButton btnInsertPen;
    private javax.swing.JButton btnKonfirmasiPen;
    private javax.swing.JButton btnKonfirmasiPeng;
    private javax.swing.JButton btnUpdatePel;
    private javax.swing.JComboBox<String> cboxidkyw;
    private javax.swing.JComboBox<String> cboxmetodpmbayaran;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblPelanggan;
    private javax.swing.JLabel lblPengiriman;
    private javax.swing.JLabel lblPenjualan;
    private javax.swing.JTable tabelPel;
    private javax.swing.JTable tabelRelPeng;
    private javax.swing.JTable tabelSemPeng;
    private javax.swing.JTable tblsemupsn;
    private javax.swing.JTextField tfAlamatPel;
    private javax.swing.JTextField tfIdPel;
    private javax.swing.JTextField tfIdPeng;
    private javax.swing.JTextField tfNamaPel;
    private javax.swing.JTextField tfTanggalPeng;
    private javax.swing.JTextField tfTelpPel;
    private javax.swing.JLabel txtBiaya;
    private javax.swing.JTextField txtIdKurPeng;
    private javax.swing.JLabel txtLblNamaPeng;
    private javax.swing.JLabel txtNoPembePeng;
    private javax.swing.JTextField txthargasatuan;
    private javax.swing.JTextField txtidpel;
    private javax.swing.JTextField txtidpembayaran;
    private javax.swing.JTextField txtidpembelian;
    private javax.swing.JTextField txtjumlahbeli;
    private javax.swing.JTextField txtkodebrg;
    private javax.swing.JLabel txtnamabrg;
    private javax.swing.JLabel txtnamakyw;
    private javax.swing.JTextField txtnamapel;
    private javax.swing.JLabel txttanggal;
    private javax.swing.JTextField txttotalbrg;
    private javax.swing.JTextField txttotalharga;
    // End of variables declaration//GEN-END:variables
}
