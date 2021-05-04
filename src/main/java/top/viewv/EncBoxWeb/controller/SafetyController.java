package top.viewv.EncBoxWeb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
            encfileuuid = safetyService.symmetricEncrypt(filenameuuid,algorithm,keylength,password,ifAEAD,associatedDataString);
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

    @GetMapping("asykey")
    @ResponseBody
    public Map<String, Object> asykey(){
        HashMap<String, String> map = safetyService.generateKeypair();

        String publickey = map.get("publickey");
        String privatekey = map.get("privatekey");

        Map<String, Object> result = new HashMap<>();
        result.put("code",200);
        result.put("public",publickey);
        result.put("private",privatekey);

        return result;
    }

    @PostMapping("asyenc")
    @ResponseBody
    public Map<String, Object> asyenc(@RequestBody Map<String,String> params){
        Map<String, Object> result = new HashMap<>();
        try {
            String publickey = params.get("public");
            String context = params.get("context");

            String cipher = safetyService.asymmetricEncrypt(publickey,context);

            if (cipher != null){
                result.put("code",200);
                result.put("cipher",cipher);
            }else {
                result.put("code",400);
                result.put("cipher",null);
            }
        }catch (Exception e){
            result.put("code",400);
            result.put("cipher",null);
        }

        return result;
    }

    @PostMapping("asydec")
    @ResponseBody
    public Map<String, Object> asydec(@RequestBody Map<String,String> params){
        Map<String, Object> result = new HashMap<>();
        try {
            String privatekey = params.get("private");
            String context = params.get("context");

            String plain = safetyService.asymmetricDecrypt(privatekey,context);

            if (plain != null){
                result.put("code",200);
                result.put("plain",plain);
            }else {
                result.put("code",400);
                result.put("plain",null);
            }
        }catch (Exception e){
            result.put("code",400);
            result.put("plain",null);
        }

        return result;
    }

    @GetMapping("hash")
    @ResponseBody
    public Map<String, Object> hash(String filenameuuid, String algorithm){
        Map<String, Object> result = new HashMap<>();

        String hashSum = safetyService.hash(filenameuuid,algorithm);

        if (hashSum != null){
            result.put("code",200);
            result.put("hash",hashSum);
        }else {
            result.put("code",400);
            result.put("hash",null);
        }

        return result;
    }
}
