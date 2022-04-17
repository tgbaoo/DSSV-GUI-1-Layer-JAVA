import java.util.*;
import java.lang.*;

public class SinhVien {
    private String mssv;
    private String ho;
    private String ten;
    private String gmail;
    private String dob;

    public SinhVien() {
    }

    public SinhVien(String mssv, String ho, String ten, String gmail, String dob) {
        this.mssv = mssv;
        this.ho = ho;
        this.ten = ten;
        this.gmail = gmail;
        this.dob = dob;
    }

    public void setMSSV(String mssv) {
        this.mssv = mssv;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getMSSV() {
        return mssv;
    }

    public String getHo() {
        return ho;
    }

    public String getTen() {
        return ten;
    }

    public String getGmail() {
        return gmail;
    }

    public String getDob() {
        return dob;
    }
}
