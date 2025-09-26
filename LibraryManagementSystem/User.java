package com.ElevateLabs.LibraryManagementSystem;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class User {
   private String name;
   private int userId;
   private List<Book> borrowedBooks;

   public User(String name, int userId) {
      this.name = name;
      this.userId = userId;
      this.borrowedBooks = new ArrayList<>();
      saveUserToFile();
   }

   public String getName() { return name; }
   public int getUserId() { return userId; }
   public List<Book> getBorrowedBooks() { return borrowedBooks; }

   public void borrowBook(Book book) {
      borrowedBooks.add(book);
   }

   public void returnBook(Book book) {
      borrowedBooks.remove(book);
   }

   @Override
   public String toString() {
      return "User{" +
              "Name='" + name + '\'' +
              ", ID=" + userId +
              ", Borrowed Books=" + borrowedBooks.size() +
              '}';
   }

   private void saveUserToFile() {
      try (FileWriter fw = new FileWriter("users.txt", true)) {  // append mode
         fw.write("Name: " + name + ", ID: " + userId + "\n");
      } catch (IOException e) {
         System.out.println("Error saving user: " + e.getMessage());
      }
   }
}
