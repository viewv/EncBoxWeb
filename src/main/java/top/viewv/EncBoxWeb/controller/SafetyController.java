package top.viewv.EncBoxWeb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import top.viewv.EncBoxWeb.service.SafetyService;

@RestController
@CrossOrigin
public class SafetyController {

    @Autowired
    SafetyService safetyService;
}
