package top.viewv.EncBoxWeb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.viewv.EncBoxWeb.service.DataService;

@RestController
@CrossOrigin
public class DataController {

    @Autowired
    private DataService dataService;

    @GetMapping("getfilename")
    public @ResponseBody String getFilenameByFilenameuuid(String filenameuuid){
        return dataService.getFilenameByFilenameuuid(filenameuuid);
    }
}
