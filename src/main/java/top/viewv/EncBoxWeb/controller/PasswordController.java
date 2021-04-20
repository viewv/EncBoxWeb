package top.viewv.EncBoxWeb.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.viewv.EncBoxWeb.model.passwordmanager.PMSerialize;
import top.viewv.EncBoxWeb.model.passwordmanager.PMStorage;

import java.io.FileNotFoundException;

@RestController
@CrossOrigin
public class PasswordController {

    @GetMapping("sertest")
    public void pmtest() throws FileNotFoundException {
        PMStorage pmStorage = new PMStorage();
        pmStorage.passwords.put("test","testpassword");
        pmStorage.twofa = "test twofa";

        PMSerialize pmSerialize = new PMSerialize();
        pmSerialize.serialize(pmStorage,"testpm.ser");
    }

    @GetMapping("dertest")
    public String detest() throws FileNotFoundException {
        PMStorage pmStorage;

        PMSerialize pmSerialize = new PMSerialize();
        pmStorage =  pmSerialize.deserialize("testpm.ser");
        return pmStorage.twofa;
    }
}
