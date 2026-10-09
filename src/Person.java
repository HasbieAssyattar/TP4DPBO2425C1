public class Person {
    private String id;
    private String nama;
    private String tahunLahir;
    private String alamat;
    private String jabatan;

    public Person(String id, String nama, String tahunLahir, String alamat, String jabatan) {
        this.id = id;
        this.nama = nama;
        this.tahunLahir = tahunLahir;
        this.alamat = alamat;
        this.jabatan = jabatan;
    }

    // Getter dan Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTahunLahir() {
        return tahunLahir;
    }

    public void setTahunLahir(String tahunLahir) {
        this.tahunLahir = tahunLahir;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }
}
