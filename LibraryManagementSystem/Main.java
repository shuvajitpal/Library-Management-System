package com.ElevateLabs.LibraryManagementSystem;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
   public static void main(String[] args) {
      Library library = new Library();
      Scanner sc = new Scanner(System.in);

      Map<String, Integer> nameToId = new HashMap<>();
      Map<Integer, User> idToUser = new HashMap<>();

      int userIdCounter = 100;
      // Add 10 books (different subjects)
      library.addBook(new Book("Java Programming", "James Gosling", "B001"));
      library.addBook(new Book("Data Structures", "Robert Lafore", "B002"));
      library.addBook(new Book("Clean Code", "Robert Martin", "B003"));
      library.addBook(new Book("Operating System", "Silberschatz", "B004"));
      library.addBook(new Book("Computer Networks", "Andrew Tanenbaum", "B005"));
      library.addBook(new Book("Database System Concepts", "Korth", "B006"));
      library.addBook(new Book("Artificial Intelligence", "Stuart Russell", "B007"));
      library.addBook(new Book("Machine Learning", "Tom Mitchell", "B008"));
      library.addBook(new Book("Web Development", "Jon Duckett", "B009"));
      library.addBook(new Book("Cloud Computing", "Rajkumar Buyya", "B010"));

      System.out.println("===== Welcome to Library Management System =====");

      while (true) {
         System.out.println("\nChoose an option:");
         System.out.println("1. Show all books");
         System.out.println("2. Issue a book");
         System.out.println("3. Return a book");
         System.out.println("4. Show all users");
         System.out.println("5. Exit");
         System.out.print("Enter choice: ");
         String raw = sc.nextLine().trim();

         int choice;
         try {
            choice = Integer.parseInt(raw);
         } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
            continue;
         }

         switch (choice) {
            case 1: library.showAllBooks(); break;
            case 2: {
               User user;
               int uid;

               if (idToUser.isEmpty()) {
                  System.out.println("No users found. Please Insert Your Name.");
                  System.out.print("Enter your Name: ");
                  String name = sc.nextLine().trim();
                  uid = ++userIdCounter;
                  user = new User(name, uid);
                  library.addUser(user);
                  nameToId.put(name, uid);
                  idToUser.put(uid, user);
                  System.out.println("Account created. Hello " + name + "! Your User ID is: " + uid);
               } else {
                  System.out.print("Are you an existing user? (y/n): ");
                  String existing = sc.nextLine().trim().toLowerCase();

                  if (existing.equals("y") || existing.equals("yes")) {
                     System.out.print("Enter your User ID: ");
                     String idStr = sc.nextLine().trim();
                     try {
                        uid = Integer.parseInt(idStr);
                     } catch (NumberFormatException e) {
                        System.out.println("Invalid ID format.");
                        break;
                     }
                     user = idToUser.get(uid);
                     if (user == null) {
                        System.out.println("User ID not found in this session. If you are new, choose 'n' to create a new account.");
                        break;
                     }
                  } else {
                     System.out.print("Enter your Name: ");
                     String name = sc.nextLine().trim();
                     if (nameToId.containsKey(name)) {
                        uid = nameToId.get(name);
                        user = idToUser.get(uid);
                        System.out.println("Welcome back, " + name + "! Your User ID is: " + uid);
                     } else {
                        uid = ++userIdCounter;
                        user = new User(name, uid);
                        library.addUser(user);
                        nameToId.put(name, uid);
                        idToUser.put(uid, user);
                        System.out.println("Account created. Hello " + name + "! Your User ID is: " + uid);
                     }
                  }
               }
               if (user.getBorrowedBooks().size() >= 2) {
                  System.out.println("You already borrowed 2 books. Return one to borrow again.");
                  break;
               }
               System.out.println("Books in library:");
               library.showAllBooks();
               System.out.print("Enter Book ISBN to issue: ");
               String isbnIssue = sc.nextLine().trim().toUpperCase();
               library.issueBook(isbnIssue, user.getUserId());

               break;
            }
            case 3: {
               System.out.print("Enter your User ID: ");
               String idStr = sc.nextLine().trim();
               int uid;
               try {
                  uid = Integer.parseInt(idStr);
               } catch (NumberFormatException e) {
                  System.out.println("Invalid ID.");
                  break;
               }
               User user = idToUser.get(uid);
               if (user == null) {
                  System.out.println("User ID not found (in this session).");
                  break;
               }
               System.out.print("Enter Book ISBN to return: ");
               String isbnReturn = sc.nextLine().trim().toUpperCase();
               boolean hasBook = false;
               for (Book b : user.getBorrowedBooks()) {
                  if (b.getIsbn().toUpperCase().equals(isbnReturn)) {
                     hasBook = true;
                     break;
                  }
               }
               if (!hasBook) {
                  System.out.println("Return failed: this user did not borrow book with ISBN " + isbnReturn);
                  break;
               }
               library.returnBook(isbnReturn, uid);
               break;
            }
            case 4:
               library.showAllUsers();
               break;
            case 5:
               System.out.println("Exiting... Thank you for using the Library System!");
               return;
            default: System.out.println("Invalid choice! Please try again.");
         }
      }
   }
}