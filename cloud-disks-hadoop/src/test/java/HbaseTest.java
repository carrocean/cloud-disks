import com.example.entity.FileEntity;
import com.example.entity.UserEntity;
import com.example.hadoop.basedao.HbaseDao;
import com.example.hadoop.basedao.HdfsDao;
import org.junit.jupiter.api.Test;

public class HbaseTest {

    @Test
    public void testUpdateOneData() {
        String tableName = "student";
        String rowKey = "2";
        String family = "schoolinfo";
        String column = "name";
        String value = "lihong";

        HbaseDao hbaseDao = new HbaseDao();
        hbaseDao.updateOneData(tableName,rowKey,family,column,value);
    }
}
