package models;


import play.data.validation.Constraints;
import play.libs.F;
import play.mvc.PathBindable;
import play.mvc.QueryStringBindable;

import java.util.*;

public class Product implements PathBindable<Product>,
    QueryStringBindable<Product> {

  public static class EanValidator extends Constraints.Validator<String> {

    @Override
    public boolean isValid(String value) {
      String pattern = "^[0-9]{13}$";
      return value != null && value.matches(pattern);
    }

    @Override
    public F.Tuple<String, Object[]> getErrorMessageKey() {
      return new F.Tuple<String, Object[]>("error.invalid.ean",
          new String[]{});
    }
  }

  private static Set<Product> products;

  static {
    products = new HashSet<Product>();
    products.add(new Product("1111111111111", "Paperclips 1",
        "Paperclips description 1"));
    products.add(new Product("2222222222222", "Paperclips 2",
        "Paperclips description "));
    products.add(new Product("3333333333333", "Paperclips 3",
        "Paperclips description 3"));
    products.add(new Product("4444444444444", "Paperclips 4",
        "Paperclips description 4"));
    products.add(new Product("5555555555555", "Paperclips 5",
        "Paperclips description 5"));
  }

  @Constraints.Required
  @Constraints.ValidateWith(EanValidator.class)
  public String ean;
  @Constraints.Required
  public String name;
  public String description;
  public byte[] picture;

  public List<Tag> tags = new LinkedList<>();

  public Product() {
  }

  public Product(String ean, String name, String description) {
    this.ean = ean;
    this.name = name;
    this.description = description;
  }

  public String toString() {
    return String.format("%s - %s", ean, name);
  }

  public static Set<Product> findAll() {
    return new HashSet<Product>(products);
  }

  public static Product findByEan(String ean) {
    for (Product candidate : products) {
      if (candidate.ean.equals(ean)) {
        return candidate;
      }
    }
    return null;
  }

  public static Set<Product> findByName(String term) {
    final Set<Product> results = new HashSet<Product>();
    for (Product candidate : products) {
      if (candidate.name.toLowerCase().contains(term.toLowerCase())) {
        results.add(candidate);
      }
    }

    return results;
  }

  public static boolean remove(Product product) {
    return products.remove(product);
  }

  public void save() {
    products.remove(findByEan(this.ean));
    products.add(this);
  }

  @Override
  public Product bind(String key, String value) {
    return findByEan(value);
  }

  @Override
  public F.Option<Product> bind(String key, Map<String, String[]> data) {
    return F.Option.Some(findByEan(data.get("ean")[0]));
  }

  @Override
  public String unbind(String s) {
    return this.ean;
  }

  @Override
  public String javascriptUnbind() {
    return this.ean;
  }
}
