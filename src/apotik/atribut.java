package apotik;

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import javax.swing.text.*;

/**
 *
 * @author Ryan
 */
public class atribut {
    byte len;
    PlainDocument filter;
    public atribut()
    {}

    public PlainDocument huruf()
    {
        PlainDocument filterHuruf = new PlainDocument()
        {
            @Override
            public void insertString(int offs, String str, AttributeSet ats)throws BadLocationException
            {
                StringBuffer buf = new StringBuffer();
                int c = 0;
                char tinput[] = str.toCharArray();

                for(int i=0;i<tinput.length;i++)
                {
                    boolean hnyHuruf = Character.isLetter(tinput[i]);
                    if(hnyHuruf == true)
                    {
                        tinput[c] = tinput[i];
                        c++;
                    }
                }
                buf.append(tinput,0,c);
                super.insertString(offs, new String(buf), ats);
            }
        };
        return filterHuruf;
    }

    public PlainDocument angka()
    {
        PlainDocument filterHuruf = new PlainDocument()
        {
            @Override
            public void insertString(int offs, String str, AttributeSet ats)throws BadLocationException
            {
                StringBuffer buf = new StringBuffer();
                int c = 0;
                char tinput[] = str.toCharArray();

                for(int i=0;i<tinput.length;i++)
                {
                    boolean hnyAngka = Character.isDigit(tinput[i]);
                    if(hnyAngka == true)
                    {
                        tinput[c] = tinput[i];
                        c++;
                    }
                }
                buf.append(tinput,0,c);
                super.insertString(offs, new String(buf), ats);
            }
        };
        return filterHuruf;
    }

    public PlainDocument atas()
    {
        PlainDocument upFilter = new PlainDocument()
        {
            @Override
            public void insertString(int o,String s,AttributeSet a)throws BadLocationException
            {
                char upp[]=s.toCharArray();
                for(int i=0;i<upp.length;i++)
                {
                    upp[i]=Character.toUpperCase(upp[i]);
                }
                super.insertString(o, new String(upp), a);
            }
        };
        return upFilter;
    }

    public PlainDocument bawah()
    {
        PlainDocument lowFilter = new PlainDocument()
        {
            @Override
            public void insertString(int o,String s,AttributeSet a)throws BadLocationException
            {
                char low[]=s.toCharArray();
                for(int i=0;i<low.length;i++)
                {
                    low[i]=Character.toLowerCase(low[i]);
                }
                super.insertString(o, new String(low), a);
            }
        };
        return lowFilter;
    }

     private String waktu(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date());
    }

    public void backup(String bck){
        try
        {
            Properties systemProp = System.getProperties();
            String currentDir = systemProp.getProperty("user.home").substring(0,3);
            if(currentDir.substring(0, 1).compareTo("/")==0)currentDir = currentDir.substring(0,1);
            File backup = new File(currentDir + bck + ".adb");
            FileWriter output = new FileWriter(backup,false);
            output.write("TRUNCATE TABLE obat;");
            output.write("TRUNCATE TABLE penjualan;");
            output.write("TRUNCATE TABLE penjualan_detail;");
            output.write("TRUNCATE TABLE retur;");
            output.write("TRUNCATE TABLE retur_detail;");
            output.write("TRUNCATE TABLE supplier;");
            output.write("TRUNCATE TABLE user;");
            try
            {
                Connection con = new sqlConfig().db();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM obat");
                while(rs.next())
                {
                    String kdobat = rs.getString(1);
                    String nmobat = rs.getString(2);
                    String jenis = rs.getString(3);
                    String satuan = rs.getString(4);
                    String ukuran = rs.getString(5);
                    String harga = rs.getString(6);
                    String net = rs.getString(7);
                    String jual = rs.getString(8);
                    String stok = rs.getString(9);
                    String kdsup = rs.getString(10);
                    String out = "INSERT INTO obat VALUES ('"+kdobat+"','"+nmobat+"','"+jenis+"','"+satuan+"','"+ukuran+"',"+harga+",'"+net+"',"+jual+","+stok+",'"+kdsup+"');";
                    output.write(out);
                }
                stmt.close();
                stmt = con.createStatement();
                rs = stmt.executeQuery("SELECT * FROM penjualan");
                while(rs.next())
                {
                    String nota = rs.getString(1);
                    String tgl = rs.getString(2);
                    String nama = rs.getString(3);
                    String alamat = rs.getString(4);
                    String telp = rs.getString(5);
                    String grand = rs.getString(6);
                    String user = rs.getString(7);
                    String out = "INSERT INTO penjualan VALUES ('"+nota+"','"+tgl+"','"+nama+"','"+alamat+"','"+telp+"',"+grand+",'"+user+"');";
                    output.write(out);
                }
                stmt.close();
                stmt = con.createStatement();
                rs = stmt.executeQuery("SELECT * FROM penjualan_detail");
                while(rs.next())
                {
                    String nota = rs.getString(1);
                    String kdobat = rs.getString(2);
                    String harga = rs.getString(3);
                    String jumlah = rs.getString(4);
                    String total = rs.getString(5);
                    String out = "INSERT INTO penjualan_detail VALUES ('"+nota+"','"+kdobat+"',"+harga+","+jumlah+","+total+");";
                    output.write(out);
                }
                stmt.close();
                stmt = con.createStatement();
                rs = stmt.executeQuery("SELECT * FROM retur");
                while(rs.next())
                {
                    String no_retur = rs.getString(1);
                    String tgl = rs.getString(2);
                    String grand = rs.getString(3);
                    String user = rs.getString(4);
                    String nota = rs.getString(5);
                    String out = "INSERT INTO retur VALUES ('"+no_retur+"','"+tgl+"',"+grand+",'"+user+"','"+nota+"');";
                    output.write(out);
                }
                stmt.close();
                stmt = con.createStatement();
                rs = stmt.executeQuery("SELECT * FROM retur_detail");
                while(rs.next())
                {
                    String no_retur = rs.getString(1);
                    String kdobat = rs.getString(2);
                    String harga = rs.getString(3);
                    String jumlah = rs.getString(4);
                    String total = rs.getString(5);
                    String out = "INSERT INTO retur_detail VALUES ('"+no_retur+"','"+kdobat+"',"+harga+","+jumlah+","+total+");";
                    output.write(out);
                }
                stmt.close();
                stmt = con.createStatement();
                rs = stmt.executeQuery("SELECT * FROM supplier");
                while(rs.next())
                {
                    String kdsup = rs.getString(1);
                    String nmsup = rs.getString(2);
                    String alamat = rs.getString(3);
                    String telp = rs.getString(4);
                    String cp = rs.getString(5);
                    String cptelp = rs.getString(6);
                    String out = "INSERT INTO supplier VALUES ('"+kdsup+"','"+nmsup+"','"+alamat+"','"+telp+"','"+cp+"','"+cptelp+"');";
                    output.write(out);
                }
                stmt.close();
                stmt = con.createStatement();
                rs = stmt.executeQuery("SELECT * FROM user");
                while(rs.next())
                {
                    String username = rs.getString(1);
                    String password = rs.getString(2);
                    String nama = rs.getString(3);
                    String tmp_lahir = rs.getString(4);
                    String tgl_lahir = rs.getDate(5).toString();
                    String alamat = rs.getString(6);
                    String telp = rs.getString(7);
                    String status = rs.getString(8);
                    String out = "INSERT INTO user VALUES ('"+username+"','"+password+"','"+nama+"','"+tmp_lahir+"','"+tgl_lahir+"','"+alamat+"','"+telp+"',"+status+",0);";
                    output.write(out);
                }
                stmt.close();
            }
            catch(SQLException ex){System.out.println(ex);}
            output.close();
        }catch (IOException e) {}
    }

    public void log(String user,String title)
    {
        //Pengaturan Tanggal Dan Waktu
        String tgl = Integer.toString(Calendar.getInstance().get(Calendar.DATE));
        String bln = Integer.toString(Calendar.getInstance().get(Calendar.MONTH) + 1);
        String thn = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
        String th = thn.substring(2);
        
        if(tgl.length()==1)tgl="0"+tgl;
        if(bln.length()==1)bln="0"+bln;
        
        String sdf = tgl + bln + th;
        String wkt = waktu();
        
        //Penulisan File
        Properties systemProp = System.getProperties();
        String currentDir = systemProp.getProperty("user.dir");
        File log = new File(currentDir + "/log/" + sdf + ".html");

        try {
            FileWriter output = new FileWriter(log,true);
            output.write("[" + wkt + "] ");
            output.write(user + " - ");
            output.write(title + ";<br>");
            output.close();
        } catch (IOException e) {}
    }
}
