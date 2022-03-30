import Entities.Article;
import Entities.CreditCard;
import Entities.Customer;
import Entities.Order;
import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Joan Anton Perez Branya
 * @since 19/02/2017
 *
 */

public class TShirtsDB4O {
	public static ArrayList<Order> orders;
	static ObjectContainer db;
	

	/**
	 * Implement TODO methods and run to test
	 * 
	 * @param args
	 *            no args
	 * @throws IOException
	 *             in order to read files
	 * @throws ParseException
	 *             in order to parse data formats
	 */
	public static void main(String[] args) throws IOException, ParseException {
		TShirtsDB4O TSM = new TShirtsDB4O();
		FileAccessor fa = new FileAccessor();
		fa.readArticlesFile("articles.csv");
		fa.readCreditCardsFile("creditCards.csv");
		fa.readCustomersFile("customers.csv");
		fa.readOrdersFile("orders.csv");
		fa.readOrderDetailsFile("orderDetails.csv");
		orders = fa.orders;
		try {

			File file = new File("orders.db");
			String fullPath = file.getAbsolutePath();
			db = Db4o.openFile(fullPath);

			TSM.addOrders();
			TSM.listOrders();
			TSM.listArticles();
			TSM.addArticle(7, "CALCETINES EJECUTIVOS 'JACKSON 3PK'", "gris", "45", 18.00);
			TSM.updatePriceArticle(7, 12.00);
			TSM.llistaArticlesByName("CALCETINES EJECUTIVOS 'JACKSON 3PK'");
			TSM.deletingArticlesByName("POLO BÁSICO 'MANIA'");
			TSM.deleteArticleById(7);
			TSM.listArticles();
			TSM.listCustomers();
			TSM.changeCreditCardToCustomer(1);
			TSM.listCustomers();
			TSM.llistaCustomerByName("Laura");
			TSM.showOrdersByCustomerName("Laura");
			TSM.showCreditCardByCustomerName("Laura");
			TSM.deleteCustomerbyId(2);
			TSM.retrieveOrderContentById_Order(2);
			TSM.deleteOrderContentById_Order(2);
			TSM.retrieveOrderContentById_Order(2);
			TSM.listCustomers();
			TSM.clearDatabase();
			TSM.listOrders();

		} finally {
			// close database
			db.close();
		}
	}

	/**
	 * Select Customer using customer id and next generate a new credit card and
	 * update customer using the new credit card
	 * 
	 * @param idCustomer
	 *            idCustomer
	 */
	public void changeCreditCardToCustomer(int idCustomer) {
		System.out.println("Alan");
		List<Customer> customers = db.query(new Predicate<Customer>() {
			public boolean match(Customer customer) {
				if(customer.getIdCustomer() == idCustomer){
				customer.setCreditCard(new CreditCard("a","a",2,2));
				}
				return false;
			}
		});
	}

	/**
	 * Select Article using id and next update price
	 * 
	 * @param id
	 *            article
	 * @param newPrice
	 */
	public void updatePriceArticle(int id, double newPrice) {
		List<Article> articles = db.query(new Predicate<Article>() {
			@Override
			public boolean match(Article article) {
				if(article.getIdArticle() == id){
					article.setRecommendedPrice((float) newPrice);
				}
				return false;
			}
		});
	}

	/**
	 * Add a new article into database
	 * 
	 * @param i
	 *            article id
	 * @param string
	 *            article name
	 * @param string2
	 *            article colour
	 * @param string3
	 *            article size
	 * @param d
	 *            article price
	 */
	public void addArticle(int i, String string, String string2, String string3, double d) {
		Article articleGOD = new Article();
		List<Article> articles = db.query(new Predicate<Article>() {
			@Override
			public boolean match(Article article) {
					articleGOD.setIdArticle(i);
					articleGOD.setName(string);
					articleGOD.setRecommendedPrice((float) d);
					articleGOD.setColour(string2);
					articleGOD.setSize(string3);
				return false;
			}
		});
	}

	/**
	 * Delete an article using idArticle
	 * 
	 * @param i
	 *            idArticle
	 */
	public void deleteArticleById(int i) {
		System.out.println("Alan2");
		List<Article> articles = db.query(new Predicate<Article>() {
			@Override
			public boolean match(Article article) {
				if(article.getIdArticle() == i){
					db.delete(article);
					System.out.println(article.getName());
				}

				return false;
			}
		});

	}

	/**
	 * Delete Order and its orderdetails using idOrder
	 * 
	 * @param i
	 *            idOrder
	 */
	public void deleteOrderContentById_Order(int i) {
		List<Order> orders = db.query(new Predicate<Order>() {
			@Override
			public boolean match(Order order) {
				if(order.getIdOrder() == i){
					order.getDetails();
					db.delete(i);
				}
				return false;
			}
		});
	}

	/**
	 * Select Order using his id and order details
	 * 
	 * @param i
	 *            idOrder
	 */
	public void retrieveOrderContentById_Order(int i) {
		// TODO Auto-generated method stub

	}

	/**
	 * Delete Customer using idCustomer
	 * 
	 * @param i
	 *            idCustomer
	 */
	public void deleteCustomerbyId(int i) {
		// TODO Auto-generated method stub
		List<Customer> customers = db.query(new Predicate<Customer>() {
			@Override
			public boolean match(Customer customer) {
				if(customer.getIdCustomer() == i){
					db.delete(customer);
				}
				return false;
			}
		});
	}

	/**
	 * Select Customer using customer name and next select the credit card
	 * values
	 * 
	 * @param string
	 *            customer name
	 */
	public void showCreditCardByCustomerName(String string) {
		// TODO Auto-generated method stub
		List<Customer> customers = db.query(new Predicate<Customer>() {
			@Override
			public boolean match(Customer customer) {
				if(customer.getName().equals(string)){
					System.out.println(customer.getCreditCard());
				}
				return false;
			}
		});
	}

	/**
	 * Method to list Oders and orderdetails from the database using the
	 * customer name
	 */
	public void showOrdersByCustomerName(String string) {
		// TODO Auto-generated method stub
		List<Order> orders = db.query(new Predicate<Order>() {
			@Override
			public boolean match(Order order) {
				order.getCustomer().getName().equals(string);
				System.out.println(order);
				return false;
			}
		});
	}

	/** delete all objects from the whole database */
	public void clearDatabase() {
		// TODO Auto-generated method stub

	}

	/**
	 * Delete Article using article name
	 * 
	 * @param string
	 *            Article name
	 */
	public void deletingArticlesByName(String string) {
		// TODO Auto-generated method stub
		List<Article> articles = db.query(new Predicate<Article>() {
			@Override
			public boolean match(Article article) {
				if(article.getName().equals(string)){
					db.delete(article);
				}
				return false;
			}
		});

	}

	/** Method to list Articles from the database using their name */
	public void llistaArticlesByName(String string) {
		// TODO Auto-generated method stub
		List<Article> articles = db.query(new Predicate<Article>() {
			@Override
			public boolean match(Article article) {
				if(article.getName().equals(string)){
					System.out.println(article);
				}
				return false;
			}
		});
	}

	/** Method to list Customers from the database using their name */
	public void llistaCustomerByName(String string) {
		// TODO Auto-generated method stub
		List<Customer> customers = db.query(new Predicate<Customer>() {
			@Override
			public boolean match(Customer customer) {
				if(customer.getName().equals(string)){
					System.out.println(customer);
				}

				return false;
			}
		});
	}

	/** Method to list all Customers from the database */
	public void listCustomers() {
		// TODO Auto-generated method stub
		List<Customer> customers = db.query(new Predicate<Customer>() {
			@Override
			public boolean match(Customer customer) {
				System.out.println(customer.toString());
				return false;
			}
		});
	}

	/** Method to list all Articles from the database */
	public void listArticles() {
		// TODO Auto-generated method stub
		List<Article> articles = db.query(new Predicate<Article>() {
			@Override
			public boolean match(Article article) {
				System.out.println(article.toString());
				return false;
			}
		});
	}

	/** Method to add all orders from ArrayList and store them into database */
	public void addOrders() {
		// TODO Auto-generated method stub
		for (int i = 0; i < orders.size(); i++) {
			db.store(orders.get(i));
		}
	}

	/** Method to list all Orders from the database */
	public void listOrders() {
		// TODO Auto-generated method stub
		List<Order> orders = db.query(new Predicate<Order>() {
			@Override
			public boolean match(Order order) {
				System.out.println(order.toString());
				return false;
			}
		});
	}
}
