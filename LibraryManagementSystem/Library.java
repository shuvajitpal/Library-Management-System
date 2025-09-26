package com.ElevateLabs.LibraryManagementSystem;

import java.util.ArrayList;
import java.util.List;

public class Library {
   private List<Book> books;
   private List<User> users;

   public Library() {
      books = new ArrayList<>();
      users = new ArrayList<>();
   }

   public void addBook(Book book) {
      books.add(book);
   }

   public void addUser(User user) {
      users.add(user);
   }

   public void issueBook(String isbn, int userId) {
      Book book = findBook(isbn);
      User user = findUser(userId);

      if (book != null && user != null) {
         if (!book.isIssued()) {
            book.issue();
            user.borrowBook(book);
            System.out.println("Book issued: " + book.getTitle() + " to " + user.getName());
         } else {
            System.out.println("Book already issued!");
         }
      } else {
         System.out.println("Book or User not found!");
      }
   }

   public void returnBook(String isbn, int userId) {
      Book book = findBook(isbn);
      User user = findUser(userId);

      if (book != null && user != null && book.isIssued()) {
         book.returnBook();
         user.returnBook(book);
         System.out.println("Book returned: " + book.getTitle() + " by " + user.getName());
      } else {
         System.out.println("Return failed! Either book not issued or user not found.");
      }
   }

   private Book findBook(String isbn) {
      for (Book b : books) {
         if (b.getIsbn().equals(isbn)) return b;
      }
      return null;
   }

   private User findUser(int userId) {
      for (User u : users) {
         if (u.getUserId() == userId) return u;
      }
      return null;
   }

   public void showAllBooks() {
      System.out.println("===== All Available Books =====");
      int index = 1;
      for (Book b : books) {
         if (!b.isIssued()) {  // show only books not yet issued
            System.out.println(index + ". " + b.getTitle() + " (ISBN: " + b.getIsbn() + ")");
            index++;
         }
      }
      if (index == 1) {
         System.out.println("No books available right now.");
      }
   }
   public void showAllUsers() {
      System.out.println("===== All Users =====");
      if (users.isEmpty()) {
         System.out.println("No users in the library yet.");
         return;
      }
      for (User u : users) {
         System.out.println("Name: " + u.getName());
         System.out.println("User ID: " + u.getUserId());
         if (u.getBorrowedBooks().isEmpty()) System.out.println("Borrowed Books: None");
         else {
            System.out.println("Borrowed Books:");
            for (Book b : u.getBorrowedBooks()) System.out.println("  - " + b.getTitle() + " (ISBN: " + b.getIsbn() + ")");
         }
         System.out.println("-------------------------");
      }
   }
}
