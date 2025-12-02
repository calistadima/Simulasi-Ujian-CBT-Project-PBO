import java.sql.SQLException;
import java.util.List;

public interface QuestionLoader {
    List<JenisSoal> loadAll() throws SQLException;
}
