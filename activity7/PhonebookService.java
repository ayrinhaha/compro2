package com.phonebook.services;

import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.HashMap;
import com.phonebook.models.Contact;

public class PhonebookService {
    private HashMap<String, Contact> contacts;

    // methods
    public PhonebookService() {
        contacts = new HashMap<>();
    }

    public void addContact(Contact c) {
        contacts.put(c.getName(), c);
    }

    public Contact searchContact(String name) {
        return contacts.get(name);
    }

    public boolean removeContact(String name) {
        return contacts.remove(name) != null;
    }

    public HashMap<String, Contact> getAllContacts() {
        return contacts;
    }

    // save to csv
    public void saveToCSV(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Contact contact : contacts.values()) {
                writer.write(contact.toCsvString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // load from csv
    public void loadFromCSV(String filename) {
    File file = new File(filename);
    if (!file.exists()) return; 

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();       
            if (line.isEmpty()) continue; 

            String[] parts = line.split(",");
            if (parts.length == 3) {
                String name = parts[0].trim();
                String phone = parts[1].trim();
                String email = parts[2].trim();
                Contact contact = new Contact(name, phone, email);
                contacts.put(name, contact);
            }
        }
    } catch (IOException e) {
        System.out.println("Error reading CSV: " + e.getMessage());
    }
}
}