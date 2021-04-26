package cn.yanfa.controller;


import cn.yanfa.common.CreatCode;
import cn.yanfa.common.SystemConstant;
import cn.yanfa.common.UploadUtils;
import cn.yanfa.model.request.FilesUploadReq;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/load")
public class FileController {

    @Value("${upload.realPath}")
    private String realPath;
    private UploadUtils uploadUtils = new UploadUtils();

    /**
     * 多文件上传
     */
    @RequestMapping("/uploadFiles")
    @ResponseBody
    public void uploadFiles(FilesUploadReq filesUploadReq) throws IOException {
        MultipartFile[] files = filesUploadReq.getFiles();
        if (null == files || files.length <= 0) {
            // 设置错误状态码

        }
        if (StringUtils.isEmpty(filesUploadReq.getIdentification())) {
            filesUploadReq.setIdentification("4");
        }

        for (MultipartFile file : files) {
            // 拿到文件名
            String filename = file.getOriginalFilename();
            //生成文件名
            LocalDate localDate = LocalDate.now();

            // 存放上传图片的文件夹
            Integer year = localDate.getYear();
            Integer month = localDate.getMonthValue();
            Integer day = localDate.getDayOfMonth();
            String path = null;
            String identification = filesUploadReq.getIdentification();
            String suffix = filename.substring(filename.lastIndexOf(".") + 1);
            switch (identification) {
                //文件类型1
                case "1":
                    path = "/stabilityevaluation/" + year + "/" + month + "/" + day + "/" + suffix + "/";
                    break;
                //文件类型2
                case "2":
                    path = "/storagerouting/" + year + "/" + month + "/" + day + "/" + suffix + "/";
                    break;
                //文件类型3
                case "3":
                    path = "/safetylicense/" + year + "/" + month + "/" + day + "/" + suffix + "/";
                    break;
                //文件类型4
                case "4":
                    path = "/yingjiyanlian/" + year + "/" + month + "/" + day + "/" + suffix + "/";
                    break;
                //文件类型5
                case "5":
                    path = "/user/" + year + "/" + month + "/" + day + "/" + suffix + "/";
                    break;
                default:
                    path = "/files/" + year + "/" + month + "/" + day + "/" + suffix + "/";
                    break;
            }
            filename = CreatCode.getUuid() + filename.substring(filename.lastIndexOf("."));
            //递归生成文件夹
            File fileDir = uploadUtils.getImgDirFile(path, realPath);
            try {
                // 构建真实的文件路径
                File newFile = new File(fileDir.getAbsolutePath() + File.separator + filename);
                // 上传图片到 -》 “绝对路径”
                file.transferTo(newFile);
                //虚拟路径
                String s = SystemConstant.URL_ROOT + path + newFile.getName();

            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }
}
