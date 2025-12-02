import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class DBInit {
    // Table: exam_user (exam_no PK, password)
    private static final List<String> PAIRS = Arrays.asList(
        "f1d01,pw0a3k",
"f1d02,rt92mx",
"f1d03,h1k20s",
"f1d04,a99qwe",
"f1d05,b7t1kp",
"f1d06,pp3l9a",
"f1d07,cx82rt",
"f1d08,k0lpp2",
"f1d09,z1p9sd",
"f1d10,as72km",
"f1d11,qp44lx",
"f1d12,k33mbs",
"f1d13,tt1z8p",
"f1d14,cd79qa",
"f1d15,l19vxs",
"f1d16,bv7n3e",
"f1d17,x23lkp",
"f1d18,ff2z02",
"f1d19,vv9i1k",
"f1d20,ss81lp",
"f1d21,qa3s8z",
"f1d22,ty6b1d",
"f1d23,pm91ss",
"f1d24,mmq21p",
"f1d25,f8l93z",
"f1d26,lm9a01",
"f1d27,st88ap",
"f1d28,k1l3ns",
"f1d29,bc93q2",
"f1d30,a12txm",
"f1d31,wk77h2",
"f1d32,zx1p3s",
"f1d33,cd9s0l",
"f1d34,pl1xx8",
"f1d35,pd2kw7",
"f1d36,hj6m4k",
"f1d37,as909l",
"f1d38,tr3bxs",
"f1d39,nn7p8j",
"f1d40,z2l4pt",
"f1d41,x4bqp1",
"f1d42,cq74mh",
"f1d43,xx1opd",
"f1d44,rt93pk",
"f1d45,mm1a3t",
"f1d46,sk4l2x",
"f1d47,mt8osq",
"f1d48,ww0pl9",
"f1d49,pp22zi",
"f1d50,qa3b11",
"f1d51,l1m4tt",
"f1d52,z9x81p",
"f1d53,hr7q5s",
"f1d54,cs21mb",
"f1d55,bc7x4l",
"f1d56,jp86k2",
"f1d57,h0l1qp",
"f1d58,mn92sy",
"f1d59,a8w30s",
"f1d60,tt9l7p",
"f1d61,qk77ds",
"f1d62,pp2kwm",
"f1d63,as18lo",
"f1d64,w8m92x",
"f1d65,cb11pp",
"f1d66,xx710s",
"f1d67,ld93k2",
"f1d68,pw321s",
"f1d69,nn4swt",
"f1d70,sk85pz",
"f1d71,cs7l0k",
"f1d72,bm9lsq",
"f1d73,wl4b80",
"f1d74,j9m21s",
"f1d75,qp86dd",
"f1d76,as5xx1",
"f1d77,ty77pq",
"f1d78,lm08z1",
"f1d79,ss92nb",
"f1d80,qt11mk",
"f1d81,wa44bd",
"f1d82,xp93kk",
"f1d83,cd17pa",
"f1d84,ak88n2",
"f1d85,hh71lw",
"f1d86,ox2p90",
"f1d87,pp1qnb",
"f1d88,bk50ss",
"f1d89,st9a41",
"f1d90,lx72pm",
"f1d91,zk21xa",
"f1d92,qa92ym",
"f1d93,pp31ck",
"f1d94,sd1pn8",
"f1d95,mm0aqs",
"f1d96,kz82qp",
"f1d97,qw43as",
"f1d98,bd9l2i",
"f1d99,pl71rr"
    );

    public static void main(String[] args) {
        System.out.println("Initializing exam_user table and inserting credentials...");
        try (Connection conn = DBConnection.getConnection()) {
            // create table if not exists
            String create = "CREATE TABLE IF NOT EXISTS exam_user (\n"
                    + " exam_no VARCHAR(16) PRIMARY KEY,\n"
                    + " password VARCHAR(64) NOT NULL\n"
                    + ");";
            try (PreparedStatement ps = conn.prepareStatement(create)) {
                ps.execute();
            }

            String sql = "INSERT INTO exam_user (exam_no, password) VALUES (?, ?) "
                    + "ON DUPLICATE KEY UPDATE password = VALUES(password)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                int count = 0;
                for (String p : PAIRS) {
                    String[] parts = p.split(",");
                    String exam = parts[0].trim();
                    String pass = parts[1].trim();
                    ps.setString(1, exam);
                    ps.setString(2, pass);
                    ps.addBatch();
                    count++;
                    if (count % 200 == 0) ps.executeBatch();
                }
                ps.executeBatch();
            }

            System.out.println("Insert completed.");
        } catch (SQLException ex) {
            System.err.println("DB error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
