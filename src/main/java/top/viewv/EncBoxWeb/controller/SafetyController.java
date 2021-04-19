package top.viewv.EncBoxWeb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import top.viewv.EncBoxWeb.service.DataService;
import top.viewv.EncBoxWeb.service.SafetyService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class SafetyController {

    @Autowired
    SafetyService safetyService;

    @Autowired
    DataService dataService;

    @GetMapping("sysencrypt")
    @ResponseBody
    public Map<String, Object> sysencrypt(String filenameuuid, String algorithm,
                                          String password, int keylength, Boolean ifAEAD, String associatedDataString) throws IOException {
        String encfileuuid;
        Map<String, Object> result = new HashMap<>();
        try {
            encfileuuid =  safetyService.symmetricEncrypt(filenameuuid,algorithm,keylength,password,ifAEAD,associatedDataString);
            result.put("code",200);
            result.put("msg","Encrypt successfully");
            result.put("uuid",encfileuuid);

        }catch (Exception e){
            result.put("code",-1);
            result.put("msg","Encrypt failed");
            result.put("uuid",null);
        }
        return result;
    }

    @GetMapping("sysdecrypt")
    @ResponseBody
    public Map<String, Object> sysdecrypt(String filenameuuid, String password){

        String decfilename,decfileuuid;

        Map<String, Object> result = new HashMap<>();

        try {
            decfilename = safetyService.symmetricDecrypt(filenameuuid,password);
            decfileuuid = filenameuuid + "dec";
            result.put("code",200);
            result.put("msg","Decrypt successfully");
            result.put("uuid",decfileuuid);
            result.put("filename",decfilename);
        }catch (Exception e){
            result.put("code",-1);
            result.put("msg","Decrypt failed");
            result.put("uuid",null);
            result.put("filename",null);
        }
        return result;
    }
}
