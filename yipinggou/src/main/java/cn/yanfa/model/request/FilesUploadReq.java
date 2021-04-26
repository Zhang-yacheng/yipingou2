package cn.yanfa.model.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FilesUploadReq {
    private MultipartFile[] files;
    private String vrPc;
    private String tailingsId;
    private String identification;
    private String fileDirectory;
}
