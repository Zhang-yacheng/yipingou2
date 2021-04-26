package cn.yanfa.common;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

public class UploadUtils {
    @Autowired
    private HttpServletRequest request;

    //private static String resources = "/tp_data/src/main/resources/";
    public File getImgDirFile(String path, String realPath) throws IOException {
        String fileDirPath = realPath + path;
        File fileDir = new File(fileDirPath);
        if (!fileDir.exists()) {
            // 递归生成文件夹
            fileDir.mkdirs();
        }
        return fileDir;
    }


}
