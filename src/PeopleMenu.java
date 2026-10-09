import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PeopleMenu extends JFrame {
    public static void main(String[] args) {
        // buat object window
        PeopleMenu menu = new PeopleMenu();

        // atur ukuran window
        menu.setSize(700, 600);

        // letakkan window di tengah layar
        menu.setLocationRelativeTo(null);

        // isi window
        menu.setContentPane(menu.mainPanel);

        // ubah warna background
        menu.getContentPane().setBackground(Color.WHITE);

        // tampilkan window
        menu.setVisible(true);

        // agar program ikut berhenti saat window diclose
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // index baris yang diklik (-1 jika tidak ada yang dipilih)
    private int selectedIndex = -1;
    // list untuk menampung semua data person
    private ArrayList<Person> listPerson;

    private JTextField textFieldId;
    private JTextField textFieldNama;
    private JTextField textFieldTahun;
    private JTextField textFieldAlamat;
    private JButton buttonAdd;
    private JButton buttonRemove;
    private JComboBox comboBoxJabatan;
    private JLabel labelId;
    private JLabel labelNama;
    private JLabel labelTahun;
    private JLabel labelAlamat;
    private JLabel labelJabatan;
    private JTable table1;
    private JLabel labelJudul;
    private JPanel mainPanel;

    public PeopleMenu() {
        // inisialisasi list data
        listPerson = new ArrayList<>();

        // isi item pilihan pada comboBoxJabatan
        comboBoxJabatan.addItem("Ketua Kelas");
        comboBoxJabatan.addItem("Wakil Ketua");
        comboBoxJabatan.addItem("Sekretaris");
        comboBoxJabatan.addItem("Bendahara");
        comboBoxJabatan.addItem("Anggota");

        // isi data dummy awal
        populateList();

        // set model tabel awal
        table1.setModel(setTable());

        // sembunyikan tombol remove saat belum ada data yang dipilih
        buttonRemove.setVisible(false);

        // Listener tombol Add / Update
        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex == -1) {
                    insertData();
                } else {
                    updateData();
                }
            }
        });

        // Listener tombol Remove (dengan prompt konfirmasi penghapusan)
        buttonRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex >= 0) {
                    deleteData();
                }
            }
        });

        // Listener saat baris di tabel diklik
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedIndex = table1.getSelectedRow();
                if (selectedIndex >= 0) {
                    Person person = listPerson.get(selectedIndex);
                    textFieldId.setText(person.getId());
                    textFieldNama.setText(person.getNama());
                    textFieldTahun.setText(person.getTahunLahir());
                    textFieldAlamat.setText(person.getAlamat());
                    comboBoxJabatan.setSelectedItem(person.getJabatan());

                    // ubah teks tombol Add menjadi Update
                    buttonAdd.setText("Update");
                    // tampilkan tombol Remove
                    buttonRemove.setVisible(true);
                }
            }
        });

        // Listener klik area panel untuk reset/batalkan seleksi tabel
        mainPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clearForm();
                table1.clearSelection();
            }
        });
    }

    // Method untuk mengisi data dummy awal
    public void populateList() {
        listPerson.add(new Person("2512345", "Yusuf Wilman", "2005", "Cimahi", "Ketua Kelas"));
        listPerson.add(new Person("2523456", "Hasbie Assyattar", "2006", "Bandung", "Wakil Ketua"));
        listPerson.add(new Person("2534567", "Ilal Zhafran", "2007", "Bekasi", "Sekretaris"));
        listPerson.add(new Person("2545678", "Luthfi Naufal", "2008", "Cibiru", "Bendahara"));
        listPerson.add(new Person("2556789", "Riza Wahyu", "2009", "Depok", "Anggota"));
    }

    // Method untuk membuat model data tabel
    public final DefaultTableModel setTable() {
        Object[] cols = {"Id", "Nama", "Tahun lahir", "Alamat", "Jabatan"};
        DefaultTableModel temp = new DefaultTableModel(null, cols);

        for (int i = 0; i < listPerson.size(); i++) {
            Person person = listPerson.get(i);
            Object[] data = {
                person.getId(),
                person.getNama(),
                person.getTahunLahir(),
                person.getAlamat(),
                person.getJabatan()
            };
            temp.addRow(data);
        }

        return temp;
    }

    // Method untuk menambah data baru
    public void insertData() {
        String id = textFieldId.getText().trim();
        String nama = textFieldNama.getText().trim();
        String tahun = textFieldTahun.getText().trim();
        String alamat = textFieldAlamat.getText().trim();
        String jabatan = comboBoxJabatan.getSelectedItem() != null ? comboBoxJabatan.getSelectedItem().toString() : "";

        // Validasi input kosong
        if (id.isEmpty() || nama.isEmpty() || tahun.isEmpty() || alamat.isEmpty() || jabatan.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Tambah ke list
        listPerson.add(new Person(id, nama, tahun, alamat, jabatan));

        // Refresh tabel
        table1.setModel(setTable());

        // Reset form
        clearForm();

        // Pesan sukses
        JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
    }

    // Method untuk mengubah data yang dipilih
    public void updateData() {
        String id = textFieldId.getText().trim();
        String nama = textFieldNama.getText().trim();
        String tahun = textFieldTahun.getText().trim();
        String alamat = textFieldAlamat.getText().trim();
        String jabatan = comboBoxJabatan.getSelectedItem() != null ? comboBoxJabatan.getSelectedItem().toString() : "";

        // Validasi input kosong
        if (id.isEmpty() || nama.isEmpty() || tahun.isEmpty() || alamat.isEmpty() || jabatan.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Update data pada list
        Person person = listPerson.get(selectedIndex);
        person.setId(id);
        person.setNama(nama);
        person.setTahunLahir(tahun);
        person.setAlamat(alamat);
        person.setJabatan(jabatan);

        // Refresh tabel
        table1.setModel(setTable());

        // Reset form
        clearForm();

        // Pesan sukses
        JOptionPane.showMessageDialog(this, "Data berhasil diubah!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
    }

    // Method untuk menghapus data dengan confirmation prompt
    public void deleteData() {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Apakah Anda yakin ingin menghapus data ini?",
            "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            listPerson.remove(selectedIndex);
            table1.setModel(setTable());
            clearForm();
            JOptionPane.showMessageDialog(this, "Data berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Method untuk mereset form input dan status tombol
    public void clearForm() {
        textFieldId.setText("");
        textFieldNama.setText("");
        textFieldTahun.setText("");
        textFieldAlamat.setText("");
        if (comboBoxJabatan.getItemCount() > 0) {
            comboBoxJabatan.setSelectedIndex(0);
        }
        buttonAdd.setText("Add");
        buttonRemove.setVisible(false);
        selectedIndex = -1;
    }
}
