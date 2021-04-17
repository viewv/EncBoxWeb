package top.viewv.EncBoxWeb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.viewv.EncBoxWeb.entity.ProgressEntity;
import top.viewv.EncBoxWeb.service.DataService;
import top.viewv.EncBoxWeb.utils.FilenameUUID;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class FileController {

    @Autowired
    private DataService dataService;

    /*
     * 文件上传
     * <p>Title: upload</p>
     * <p>Description: </p>
     * @param file
     * @return
     */
    @PostMapping("upload")
    @ResponseBody
    public Map<String, Object> upload(MultipartFile file){
        Map<String, Object> result = new HashMap<>();
        if (file != null && !file.isEmpty()){
            try {
                System.out.println(System.getProperty("user.dir"));
                String filename = file.getOriginalFilename();
                String filenameuuid = FilenameUUID.getRandomname(filename);
                dataService.addFile(filename,filenameuuid);

                file.transferTo(new File(filenameuuid));

                result.put("code", 200);
                result.put("msg", "success");
                // TODO return file url and filename
            } catch (IOException e) {
                result.put("code", -1);
                result.put("msg", "文件上传出错，请重新上传！");
                e.printStackTrace();
            }
        } else {
            result.put("code", -1);
            result.put("msg", "未获取到有效的文件信息，请重新上传!");
        }
        return result;
    }

    /**
     * 获取文件上传进度
     * @param request
     * @return
     */
    @RequestMapping("getUploadProgress")
    @ResponseBody
    public ProgressEntity getUploadProgress(HttpServletRequest request){
        return (ProgressEntity) request.getSession().getAttribute("uploadStatus");
    }

    @RequestMapping(path = "download", method = RequestMethod.GET)
    public ResponseEntity<Resource> download(String filenameuuid) throws IOException {
        String filename = dataService.getFilenameByFilenameuuid(filenameuuid);
        File file = new File(filename);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename="+filename);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
