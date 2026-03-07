package com.phonebook;

import java.util.Scanner;
import com.phonebook.models.Contact;
import com.phonebook.services.PhonebookService;

public class App {
    public static void main(String[] args) {

        PhonebookService phonebook = new PhonebookService();
        Scanner input = new Scanner(System.in);

        // load existing contacts
        phonebook.loadFromCSV("contacts.csv");

        int choice;

        do {
            System.out.println( "\n=== PHONEBOOK MENU ===\n1. Add\n2. Search\n3. Remove\n4. Display All\n5. Save to CSV\n0. Exit");
            System.out.print("Enter choice: ");
            choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1: // for add option
                    System.out.print("Enter your name: ");
                    String name = input.nextLine();
                    System.out.print("Enter your phone number: ");
                    String phoneNumber = input.nextLine();
                    System.out.print("Enter your email: ");
                    String email = input.nextLine();
                    Contact contact = new Contact(name, phoneNumber, email);
                    phonebook.addContact(contact);
                    break;
                case 2: // for search option
                    System.out.print("Enter name to search: ");
                    String searchedName = input.nextLine();
                    Contact found = phonebook.searchContact(searchedName);
                    if (found != null) {
                        System.out.println("Name: " + found.getName() + ", Phone: " + found.getPhoneNumber() + ", Email: " + found.getEmail());
                    } else {
                        System.out.println("\nContact not found.");
                    }
                    break;
                case 3: // for remove option
                    System.out.print("Enter name to remove: ");
                    String removeName = input.nextLine();

                    boolean removed = phonebook.removeContact(removeName);

                    if (removed) {
                        System.out.println("Contact removed.\n");
                    } else {
                        System.out.println("Contact not found.\n");
                    }
                    break;

                case 4: // prints all the contacts
                    if (phonebook.getAllContacts().isEmpty()) {
                        System.out.println("\nPhonebook is empty.");
                    } else {
                        for (Contact c : phonebook.getAllContacts().values()) {
                            System.out.println("\nName: " + c.getName() + ", Phone: " + c.getPhoneNumber() + ", Email: " + c.getEmail());
                        }
                    }
                    break;

                case 5: // save to csv file
                    phonebook.saveToCSV("contacts.csv");
                    System.out.println("Successfully saved to CSV.\n");
                    break;

                case 0:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 0);

        input.close();
    }
}