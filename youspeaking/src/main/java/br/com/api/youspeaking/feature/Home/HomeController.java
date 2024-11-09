package br.com.api.youspeaking.feature.Home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping("api/v1/FT006/home")
public class HomeController {
    
    @Autowired HomeService service; 

    @GetMapping("/analysis")
    public ObjectNode getAnalysisUser(@RequestParam String login) throws Exception {
        return service.getAnalysisUser(login);
    }

    @GetMapping("/user-info")
    public ObjectNode getUserInfo(@RequestParam String login) throws Exception {
        return service.getUserInfo(login);
    }

    @PutMapping("/logout")
    public ObjectNode logout(@RequestParam String login) throws Exception {
        return service.logout(login);
    }

}
