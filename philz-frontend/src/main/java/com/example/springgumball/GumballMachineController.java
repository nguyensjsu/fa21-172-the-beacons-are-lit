package com.example.springgumball;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64.Encoder;

import lombok.extern.slf4j.Slf4j;

import com.example.gumballmachine.GumballMachine;

@Slf4j
@Controller
@RequestMapping("/")
public class GumballMachineController {

    private static String key = "kwRg54x2Go9iEdl49jFENRM12Mp711QI";
    private static java.util.Base64.Encoder encoder = java.util.Base64.getEncoder();

    private static byte[] hmac_sha256(String secretKey, String data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] digest = mac.doFinal(data.getBytes());
            return digest;
        } catch (InvalidKeyException e1) {
            throw new RuntimeException("Invalid key exception while converting to HMAC SHA256");
        } catch (NoSuchAlgorithmException e2) {
            throw new RuntimeException("Java Exception Initializing HMAC Crypto Algorithm");
        }
    }

    @GetMapping
    public String getAction(@ModelAttribute("command") GumballCommand command, Model model, HttpSession session) {

        GumballModel g = new GumballModel();
        g.setModelNumber("SB102927");
        g.setSerialNumber("2134998871109");
        model.addAttribute("gumball", g);

        GumballMachine gm = new GumballMachine();
        String message = gm.toString();
        session.setAttribute("gumball", gm);

        // Added HMAC here
        String state = gm.getState().getClass().getName();
        command.setState(state);

        String timeStampString = String.valueOf(java.lang.System.currentTimeMillis());
        command.setTimestamp(timeStampString);

        String dataToHash = (state + "/" + timeStampString);
        String hashString = GumballMachineController.encoder
                .encodeToString(hmac_sha256(GumballMachineController.key, dataToHash));
        command.setHash(hashString);

        String server_ip = "";
        String host_name = "";
        try {
            InetAddress ip = InetAddress.getLocalHost();
            server_ip = ip.getHostAddress();
            host_name = ip.getHostName();

        } catch (Exception e) {
        }

        model.addAttribute("hash", hashString);
        model.addAttribute("message", message);
        model.addAttribute("server", host_name + "/" + server_ip);

        return "gumball";

    }

    @PostMapping
    public String postAction(@Valid @ModelAttribute("command") GumballCommand command,
            @RequestParam(value = "action", required = true) String action, Errors errors, Model model,
            HttpServletRequest request) {

        log.info("Action: " + action);
        log.info("Command: " + command);

        // Hash check:
        String inputHash = command.getHash();
        String inputState = command.getState();
        String inputTimeStamp = command.getTimestamp();
        String inputText = inputState + "/" + inputTimeStamp;
        String prevHash = GumballMachineController.encoder
                .encodeToString(hmac_sha256(GumballMachineController.key, inputText));

        if (!inputHash.equals(prevHash)) {
            throw new Error("Hash does not match!");
        }

        HttpSession session = request.getSession();
        GumballMachine gm = (GumballMachine) session.getAttribute("gumball");

        if (action.equals("Insert Quarter")) {
            gm.insertQuarter();
        }

        if (action.equals("Turn Crank")) {
            command.setMessage("");
            gm.turnCrank();
        }

        session.setAttribute("gumball", gm);
        String message = gm.toString();

        String state = gm.getState().getClass().getName();
        command.setState(state);

        String timeStampString = String.valueOf(java.lang.System.currentTimeMillis());
        command.setTimestamp(timeStampString);

        String dataToHash = (state + "/" + timeStampString);
        String hashString = GumballMachineController.encoder
                .encodeToString(hmac_sha256(GumballMachineController.key, dataToHash));
        command.setHash(hashString);

        String server_ip = "";
        String host_name = "";
        try {
            InetAddress ip = InetAddress.getLocalHost();
            server_ip = ip.getHostAddress();
            host_name = ip.getHostName();

        } catch (Exception e) {
        }

        model.addAttribute("hash", hashString);
        model.addAttribute("message", message);
        model.addAttribute("server", host_name + "/" + server_ip);

        if (errors.hasErrors()) {
            return "gumball";
        }

        return "gumball";
    }

}