package comp3005_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class bookstore {

	static int orderNum;
	
	public static void main(String[] args) {
		String jdbcURL = "jdbc:postgresql://localhost:5432/comp3005";
		String username = "postgres";
		String password = "student";
		
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			System.out.println("connected to postgresql server");
			
			/*
			String sql = "INSERT INTO authors (author_name)"
					+ " VALUES (?)";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setString(1, "Bobette Bobbert");  // column, value
			int rows = statement.executeUpdate();
			System.out.println(rows);  // 1 means inserted, regardless of total num of rows
			
			
			
			String sql = "SELECT * FROM authors";
			Statement state = connection.createStatement();
			ResultSet result = state.executeQuery(sql);
			
			while( result.next()) {
				String id = result.getString("author_name");
				System.out.println(id);
			}
			
			int rows = statement.executeUpdate(sql);
			if(rows > 0) {
				System.out.println(rows);
			}*/
			
			Scanner scanner = new Scanner(System.in);
			System.out.println("Welcome to the book store, are you the owner? (y/n)");
			
			String input = scanner.nextLine();
			if(input.equals("y")) {
				owner(connection);
			}
			else {
				System.out.println("Please enter your id to login");
				input = scanner.nextLine();
				
				String sql = "SELECT * FROM customers WHERE customer_id = ?";

				PreparedStatement statement;
				try {
					statement = connection.prepareStatement(sql);
					statement.setString(1, input); 
					ResultSet result = statement.executeQuery();
					if(!result.next()) {
						System.out.println("You are not a customer");
						return;
					}
					String name = result.getString("customer_name");
					String address = result.getString("address");
					System.out.println("Welcome to the bookstore, " + name);
					
					sql = "SELECT order_number FROM shop_orders";
					Statement state = connection.createStatement();
					ResultSet orders = state.executeQuery(sql);
					
					String num = "0";
					while(orders.next()) {
						num = orders.getString("order_number").substring(1);
					}
					orderNum = Integer.parseInt(num.replace(" ", "")) + 1;
					
					//System.out.println(orderNum);
					customer(input, name, address, connection);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			
			connection.close();
		} catch (SQLException e) {
			System.out.println("error connecting to postgresql server");
			e.printStackTrace();
		}
	}
	
	public static void customer(String customer_id, String name, String address, Connection connection) {
		int choice = 0;
		
		while(choice != -1) {
			Scanner scanner = new Scanner(System.in);
			System.out.println("What would you like to do? \n Search for a book(1)\n Add a book to your basket(2)\n Track an order(3)\n "
					+ "View your cart(4)\n Checkout(5)\n Log out(any other key)");
			
			String input = scanner.nextLine();
			
			if(input.equals("1")) { // search for book
				System.out.println("What would you like to search for books by?"
						+ "\n Name"
						+ "\n Author"
						+ "\n ID"
						+ "\n genre");
				
				String search = scanner.nextLine();
				
				System.out.println("Please enter the " + search + " of the book");
				String value = scanner.nextLine();
				
				search_book(search, value, connection);
				
			}
			else if(input.equals("2")) {  // add book to basket
				System.out.println("Please enter the ID of the book");
				String search = scanner.nextLine();
				
				String sql = "SELECT book_id FROM books WHERE book_id = ?";
				PreparedStatement statement;
				try {
					statement = connection.prepareStatement(sql);
					statement.setString(1, search); 
					ResultSet result = statement.executeQuery();
					if(!result.next()) {
						System.out.println("Cannot find that book");
					}
					else {
						sql = "INSERT INTO added (customer_id, book_id)"
								+ "VALUES (?,?)";
						statement = connection.prepareStatement(sql);
						statement.setString(1, customer_id);
						statement.setString(2, search);
						int r = statement.executeUpdate();
						if(r == 1) {
							System.out.println("The book has been added to your cart");
						} 
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
			else if(input.equals("3")) {  // track order
				System.out.println("Here are all your current orders");
				
				String sql = "SELECT order_number FROM orders WHERE customer_id = ?";
				PreparedStatement statement;
				try {
					statement = connection.prepareStatement(sql);
					statement.setString(1, customer_id); 
					ResultSet result = statement.executeQuery();
					
					while(result.next()) {
						String orderNumber = result.getString("order_number");
						System.out.println(orderNumber);
					}
					System.out.println("Please enter the order number of the order you'd like to track");
					String search = scanner.nextLine();
					
					sql = "SELECT curr_location FROM shop_orders WHERE order_number = ?";
					statement = connection.prepareStatement(sql);
					statement.setString(1, search); 
					ResultSet result2 = statement.executeQuery();
					
					if(!result2.next()) {
						System.out.println("Cannot find that order");
					}
					else {
						String location = result2.getString("curr_location");
						System.out.println("That order is currently in " + location);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else if(input.equals("4")) {  // view cart
				System.out.println("Items currently in your cart:");
				
				String sql = "SELECT book_id FROM added WHERE customer_id = ?";
				PreparedStatement statement;
				try {
					statement = connection.prepareStatement(sql);
					statement.setString(1, customer_id); 
					ResultSet result = statement.executeQuery();
					while(result.next()) {
						sql = "SELECT * FROM books WHERE book_id = ?";
						statement = connection.prepareStatement(sql);
						statement.setString(1, result.getString("book_id")); 
						ResultSet result2 = statement.executeQuery();
						
						while(result2.next()) {
							String id = result2.getString("book_id") + ", ";
							String title = result2.getString("title");
							String author = " Written by " + result2.getString("author_name");
							String co_author = result2.getString("co_author_name");
							if(!co_author.equals("")) {
								co_author = " and " + co_author;
							}
							String genre = ", Genre: " + result2.getString("genre");
							String pages = ", Pages: " + result2.getString("num_pages");
							String publisher = ", Publisher: " + result2.getString("publisher_id");
							String price = ", $" + result2.getString("price");
							System.out.println(id + title + author + co_author + genre + pages + publisher + price);
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else if(input.equals("5")) {  // check out
				System.out.println("Please your address");
				String a = scanner.nextLine();
				
				System.out.println("Please enter your billing info");
				String b = scanner.nextLine();
				
				String sql = "SELECT book_id FROM added WHERE customer_id = ?";
				PreparedStatement statement;
				try {
					statement = connection.prepareStatement(sql);
					statement.setString(1, customer_id); 
					ResultSet result = statement.executeQuery();
					while(result.next()) {
						sql = "DELETE FROM added WHERE book_id = ? and customer_id = ?";
						statement = connection.prepareStatement(sql);
						statement.setString(1, result.getString("book_id")); 
						statement.setString(2, customer_id);
						statement.execute();
						
						sql = "SELECT quantity FROM books WHERE book_id = ?";
						statement = connection.prepareStatement(sql);
						statement.setString(1, result.getString("book_id")); 
						ResultSet result2 = statement.executeQuery();
						int quantity = 0;
						if(result2.next()) {
							quantity = result2.getInt("quantity") - 1;
						}
						
						if(quantity <= 10) {
							// send email to order more
							quantity = 30;
						}
						
						sql = "UPDATE books SET quantity = ? WHERE book_id = ?";
						statement = connection.prepareStatement(sql);
						statement.setInt(1, quantity); 
						statement.setString(2, result.getString("book_id"));
						statement.executeUpdate();
						
						sql = "INSERT INTO bought (customer_id, book_id) "
								+ "VALUES (?, ?)";						
						statement = connection.prepareStatement(sql);
						statement.setString(1, customer_id);
						statement.setString(2, result.getString("book_id"));						
						statement.executeUpdate();
					}
					
					sql = "INSERT INTO shop_orders (order_number, curr_location) "
							+ "VALUES (?, ?)";						
					statement = connection.prepareStatement(sql);
					statement.setString(1, "o" + Integer.toString(orderNum));
					statement.setString(2, "Canada");						
					statement.executeUpdate();
					
					sql = "INSERT INTO orders (order_number, customer_id) "
							+ "VALUES (?, ?)";					
					statement = connection.prepareStatement(sql);
					statement.setString(1, "o" + Integer.toString(orderNum));
					statement.setString(2, customer_id);						
					statement.executeUpdate();
					
					System.out.println("Your order number is o" + orderNum);
					orderNum++;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else {
				choice = -1;
				System.out.println("logging out" + input);
			}
		}
	}
	
	public static void search_book(String attribute, String value, Connection connection) {
		System.out.println("Found results:");
		if(attribute.equals("Name")) {
			String sql = "SELECT * FROM books WHERE title = ?";

			PreparedStatement statement;
			try {
				statement = connection.prepareStatement(sql);
				statement.setString(1, value); 
				ResultSet result = statement.executeQuery();
				while(result.next()) {
					String id = result.getString("book_id") + ", ";
					String title = result.getString("title");
					String author = " Written by " + result.getString("author_name");
					String co_author = result.getString("co_author_name");
					if(!co_author.equals("")) {
						co_author = " and " + co_author;
					}
					String genre = ", Genre: " + result.getString("genre");
					String pages = ", Pages: " + result.getString("num_pages");
					String publisher = ", Publisher: " + result.getString("publisher_id");
					String price = ", $" + result.getString("price");
					System.out.println(id + title + author + co_author + genre + pages + publisher + price);
				}			
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if(attribute.equals("Author")) {
			String sql = "SELECT * FROM books WHERE author_name = ?";

			PreparedStatement statement;
			try {
				statement = connection.prepareStatement(sql);
				statement.setString(1, value); 
				ResultSet result = statement.executeQuery();
				System.out.println("Found results:");
				while(result.next()) {
					String id = result.getString("book_id") + ", ";
					String title = result.getString("title");
					String author = " Written by " + result.getString("author_name");
					String co_author = result.getString("co_author_name");
					if(!co_author.equals("")) {
						co_author = " and " + co_author;
					}
					String genre = ", Genre: " + result.getString("genre");
					String pages = ", Pages: " + result.getString("num_pages");
					String publisher = ", Publisher: " + result.getString("publisher_id");
					String price = ", $" + result.getString("price");
					System.out.println(id + title + author + co_author + genre + pages + publisher + price);
				}			
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if(attribute.equals("ID")) {
			String sql = "SELECT * FROM books WHERE book_id = ?";

			PreparedStatement statement;
			try {
				statement = connection.prepareStatement(sql);
				statement.setString(1, value); 
				ResultSet result = statement.executeQuery();
				System.out.println("Found results:");
				while(result.next()) {
					String id = result.getString("book_id") + ", ";
					String title = result.getString("title");
					String author = " Written by " + result.getString("author_name");
					String co_author = result.getString("co_author_name");
					if(!co_author.equals("")) {
						co_author = " and " + co_author;
					}
					String genre = ", Genre: " + result.getString("genre");
					String pages = ", Pages: " + result.getString("num_pages");
					String publisher = ", Publisher: " + result.getString("publisher_id");
					String price = ", $" + result.getString("price");
					System.out.println(id + title + author + co_author + genre + pages + publisher + price);
				}			
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if(attribute.equals("genre")) {
			String sql = "SELECT * FROM books WHERE genre = ?";

			PreparedStatement statement;
			try {
				statement = connection.prepareStatement(sql);
				statement.setString(1, value); 
				ResultSet result = statement.executeQuery();
				System.out.println("Found results:");
				while(result.next()) {
					String id = result.getString("book_id") + ", ";
					String title = result.getString("title");
					String author = " Written by " + result.getString("author_name");
					String co_author = result.getString("co_author_name");
					if(!co_author.equals("")) {
						co_author = " and " + co_author;
					}
					String genre = ", Genre: " + result.getString("genre");
					String pages = ", Pages: " + result.getString("num_pages");
					String publisher = ", Publisher: " + result.getString("publisher_id");
					String price = ", $" + result.getString("price");
					System.out.println(id + title + author + co_author + genre + pages + publisher + price);
				}			
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static void owner(Connection connection) {
		int choice = 0;
		
		while(choice != -1) {
			Scanner scanner = new Scanner(System.in);
			System.out.println("What would you like to do? \n Add a book(1)\n Remove a book(2)\n View publishers(3)\n "
					+ "View sales(4)\n Log out(any other key)");
			
			String input = scanner.nextLine();
			
			if(input.equals("1")) {  // adding a book
				System.out.println("what is the book id?");
				String id = scanner.nextLine();
				
				System.out.println("what is the author name?");
				String author = scanner.nextLine();
				
				System.out.println("what is the book title?");
				String title = scanner.nextLine();
				
				System.out.println("what is the book genre?");
				String genre = scanner.nextLine();
				
				System.out.println("what is the publisher id?");
				String publisher = scanner.nextLine();
				
				System.out.println("what is the number of pages?");
				String pages = scanner.nextLine();
				
				System.out.println("what is the price?");
				String price = scanner.nextLine();
				
				System.out.println("what is the quantity remaining?");
				String quantity = scanner.nextLine();
				
				String sql = "INSERT INTO books (book_id, title, author_name, co_author_name, genre, num_pages, publisher_id, price, quantity) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";						
				PreparedStatement statement;
				try {
					statement = connection.prepareStatement(sql);
					statement.setString(1, id);
					statement.setString(2, title);
					statement.setString(3, author);
					statement.setString(4, "");
					statement.setString(5, genre);
					statement.setInt(6, Integer.parseInt(pages));
					statement.setString(7, publisher);
					statement.setInt(8, Integer.parseInt(price));
					statement.setInt(9, Integer.parseInt(quantity));
					int r = statement.executeUpdate();
					if(r > 0) {
						System.out.println("book has been inserted");
					}
					else {
						System.out.println("couldn't insert book");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else if(input.equals("2")) {
				System.out.println("what is the book id?");
				String id = scanner.nextLine();
				
				String sql = "DELETE FROM books WHERE book_id = ?";					
				PreparedStatement statement;
				try {
					statement = connection.prepareStatement(sql);
					statement.setString(1, id); 
					statement.execute();
					System.out.println("the book has been removed from the store");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else if(input.equals("3")) {	// view publishers
				String sql = "SELECT * FROM publishers";
				try {
					Statement state = connection.createStatement();
					ResultSet result = state.executeQuery(sql);
					
					System.out.println("All publishers:");
					while(result.next()) {
						String id = "\n" + result.getString("publisher_id") + " ";
						String name = result.getString("publisher_name") + " ";
						String address = result.getString("address") + " ";
						String email = result.getString("email") + " ";
						String phone = result.getString("phone") + " ";
						String bank = result.getString("bank_account");
						System.out.println(id + name + address + email+ phone + bank);
					}			
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else if(input.equals("4")) {
				System.out.println("view sales by:\n genre\n author");
				String c = scanner.nextLine();
				
				if(c.equals("genre")) {
					System.out.println("what is the genre");
					String genre = scanner.nextLine();
					
					String sql = "SELECT book_id FROM books WHERE genre = ?";
					PreparedStatement statement;
					try {
						statement = connection.prepareStatement(sql);
						statement.setString(1, genre); 
						ResultSet result = statement.executeQuery();
						while(result.next()) {
							sql = "SELECT COUNT(book_id) AS numSales FROM bought WHERE book_id = ?";
							
							statement = connection.prepareStatement(sql);
							statement.setString(1, result.getString("book_id")); 
							ResultSet result2 = statement.executeQuery();
							
							if(result2.next()) {
								System.out.println("Sold " + result2.getInt("numSales") + " copies of " + result.getString("book_id"));
							}
						}			
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				else if(c.equals("author")) {
					System.out.println("what is the author name");
					String author = scanner.nextLine();
					
					String sql = "SELECT book_id FROM books WHERE author = ?";
					PreparedStatement statement;
					try {
						statement = connection.prepareStatement(sql);
						statement.setString(1, author); 
						ResultSet result = statement.executeQuery();
						while(result.next()) {
							sql = "SELECT COUNT(book_id) AS numSales FROM bought WHERE book_id = ?";
							
							statement = connection.prepareStatement(sql);
							statement.setString(1, result.getString("book_id")); 
							ResultSet result2 = statement.executeQuery();
							
							if(result2.next()) {
								System.out.println("Sold " + result2.getInt("numSales") + " copies of " + result.getString("book_id"));
							}
						}			
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				else {
					System.out.println("cannot search");
				}
			}
			else {
				choice = -1;
				System.out.println("logging out" + input);
			}
		}
	}

}
