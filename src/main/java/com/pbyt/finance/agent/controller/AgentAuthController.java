package com.pbyt.finance.agent.controller;

import com.pbyt.finance.agent.model.AgentModel;
import com.pbyt.finance.agent.model.LoginModel;
import com.pbyt.finance.agent.service.AgentService;
import com.pbyt.finance.exception.AlreadyPresent;
import com.pbyt.finance.exception.NotFound;
import com.pbyt.finance.global.model.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/agent")
public class AgentAuthController {
    @Autowired
    AgentService agentService;

    @PostMapping("/register")
    public ResponseEntity<?> registerAgent(@RequestBody @Validated AgentModel agentModel) throws Exception {
        var isRegistered = agentService.isRegistered(agentModel.getMobileNumber());
        if (isRegistered) throw new AlreadyPresent("Already Registered");
        var agent = agentService.createAgent(agentModel);

        MessageResponse response = MessageResponse.builder()
                .status(HttpStatus.CREATED)
                .message("Registered Successfully")
                .object(agent).build();
        return ResponseEntity.ok(response);
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginAgent(@RequestBody @Validated LoginModel loginModel) throws Exception{
        var isRegistered = agentService.isRegistered(loginModel.getMobileNumber());
        if (!isRegistered) throw new NotFound("Not Found");
        MessageResponse response = MessageResponse.builder()
                .status(HttpStatus.OK)
                .message("Login Successfully")
                .object(agentService.login(loginModel)).build();
        return ResponseEntity.ok(response);
    }
}
