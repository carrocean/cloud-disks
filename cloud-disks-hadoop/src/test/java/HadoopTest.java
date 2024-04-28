import com.example.entity.FileEntity;
import com.example.entity.UserEntity;
import com.example.hadoop.basedao.HdfsDao;
import org.junit.jupiter.api.Test;

public class HadoopTest {

    @Test
    public void testFormatPathMethod() {
        HdfsDao hdfsDao = new HdfsDao();
        FileEntity file = new FileEntity();
        UserEntity user = new UserEntity();
        file.setPath("aaa");
        user.setName("liHong");
        hdfsDao.mkDir(file,user);
    }
}
