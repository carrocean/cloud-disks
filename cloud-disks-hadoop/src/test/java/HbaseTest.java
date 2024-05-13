import com.example.entity.FileEntity;
import com.example.entity.UserEntity;
import com.example.hadoop.basedao.HbaseDao;
import com.example.hadoop.basedao.HdfsDao;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class HbaseTest {

    @Test
    public void testUpdateOneData() {
        String tableName = "student";
        String rowKey = "2";
        String family = "schoolinfo";
        String column = "ttt";
        String value = "lihong";

        HbaseDao hbaseDao = new HbaseDao();
        hbaseDao.updateOneData(tableName,rowKey,family,column,value);
    }

    @Test
    public void testCreateTable() throws IOException {
        String tableName = "test";
        String[] familys = {"school", "teacher", "base"};

        HbaseDao hbaseDao = new HbaseDao();
        hbaseDao.createTable(tableName,familys);
    }
}