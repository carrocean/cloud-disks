import com.example.entity.FileEntity;
import com.example.entity.UserEntity;
import com.example.hadoop.dao.FileDao;
import com.example.hadoop.dao.basedao.HdfsDao;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class HadoopTest {

    @Test
    public void testFormatPathMethod() {
        HdfsDao hdfsDao = new HdfsDao();
        FileEntity file = new FileEntity();
        UserEntity user = new UserEntity();
        file.setPath("aaa");
        user.setUserName("liHong");
        hdfsDao.mkDir(file,user);
    }

    @Test
    public void testPut() {
        HdfsDao hdfsDao = new HdfsDao();
        FileEntity file = new FileEntity();
        UserEntity user = new UserEntity();
        file.setPath("bbb");
        user.setUserName("bbblibai");

        String filePath = "C:\\Users\\夏和圆周\\Desktop\\zookeeper安装.txt";

        try {

            InputStream inputStream = new FileInputStream(filePath);

            hdfsDao.put(inputStream,file,user);

            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
