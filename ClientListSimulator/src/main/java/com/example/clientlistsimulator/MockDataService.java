package com.example.clientlistsimulator;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class MockDataService {

    private List<String> emails;
    private List<String> emailBodies;
    private List<String> smss;
    private List<String> smsBodies;

    public MockDataService() {
        this.emails = generateEmails(10);
        this.emailBodies = generateEmailBodies(10);
        this.smss = generateSmss(10);
        this.smsBodies = generateSmsBodies(10);
    }

    public List<String> getAllEmails() {
        return emails;
    }

    public List<String> getAllEmailBodies() {
        return emailBodies;
    }

    public List<String> getAllSmss() {
        return smss;
    }

    public List<String> getAllSmsBodies() {
        return smsBodies;
    }

    public List<String> searchEmails(String query) {
        return emails.stream()
                .filter(email -> email.contains(query))
                .collect(Collectors.toList());
    }

    public List<String> searchSmss(String query) {
        return smss.stream()
                .filter(sms -> sms.contains(query))
                .collect(Collectors.toList());
    }

    private List<String> generateEmails(int count) {
        List<String> emails = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            emails.add("email" + i + "@example.com");
        }
        return emails;
    }

    private List<String> generateEmailBodies(int count) {
        List<String> emailBodies = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            emailBodies.add("E-mail body Daniel ");
        }
        return emailBodies;
    }

    private List<String> generateSmss(int count) {
        List<String> smss = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            StringBuilder sms = new StringBuilder("+");
            for (int j = 0; j < 11; j++) {
                sms.append(random.nextInt(10));
            }
            smss.add(sms.toString());
        }
        return smss;
    }

    private List<String> generateSmsBodies(int count) {
        List<String> smsBodies = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            smsBodies.add("SMS body Martin ");
        }
        return smsBodies;
    }
}
