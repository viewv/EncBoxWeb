package top.viewv.EncBoxWeb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.viewv.EncBoxWeb.model.passwordmanager.PMStorage;
import top.viewv.EncBoxWeb.service.PasswordService;


@RestController
@CrossOrigin
public class PasswordController {

    @Autowired
    PasswordService passwordService;

    @GetMapping("sertest")
    public String pmtest() {
        PMStorage pmStorage = new PMStorage();
        pmStorage.passwords.put("test","testpassword");
        pmStorage.twofa = "test twofa";
        pmStorage.key = "test key";
        pmStorage.userid = "test userid";

        try {
            return passwordService.encode(pmStorage);
        }catch (Exception e){
            return "failed";
        }
    }

    @GetMapping("dertest")
    public String detest(String filename) {
        PMStorage pmStorage;

        try {
            pmStorage =  passwordService.decode(filename);
            return pmStorage.twofa;
        }catch (Exception e){
            return "failed";
        }
    }
}
