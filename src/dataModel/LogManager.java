package dataModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.*;

/**
 * A LogManager object is in charge of the log generated by "ingredient manager", "order manager",
 * "cook manager". And it has the function of printing logs from a specific date to another specific
 * date Moreover, we can get the income/outcome situation using this manager
 */
public class LogManager {

  private ObservableList<IngredientLog> ingredientLogs;
  private ObservableList<BillLog> billLogs;

  /** Creates a LogManager object. */
  public LogManager() {
    ingredientLogs = FXCollections.observableArrayList();
    billLogs = FXCollections.observableArrayList();
  }

  // getters of the properties of this LogManager.
  public ObservableList<IngredientLog> getIngredientLogs() {
    return ingredientLogs;
  }

  public ObservableList<BillLog> getBillLogs() {
    return billLogs;
  }

  /**
   * get the ingredient logs in the given date interval
   *
   * @param start the start date
   * @param end the end date
   * @return ObservableList of ingredient logs
   */
  public ObservableList<IngredientLog> getIngredientLogs(LocalDate start, LocalDate end) {
    ObservableList<IngredientLog> ret = FXCollections.observableArrayList();
    for (IngredientLog l : ingredientLogs) {
      if (l.getLocalDate().isBefore(end.plusDays(1))
          && l.getLocalDate().isAfter(start.minusDays(1))) {
        ret.add(l);
      }
    }
    return ret;
  }

  /**
   * get the ingredient logs after the given date
   *
   * @param date the date
   * @return ObservableList of ingredient logs
   */
  public ObservableList<IngredientLog> getIngredientLogsAfter(LocalDate date) {
    ObservableList<IngredientLog> ret = FXCollections.observableArrayList();
    for (IngredientLog l : ingredientLogs) {
      if (l.getLocalDate().isAfter(date.minusDays(1))) {
        ret.add(l);
      }
    }
    return ret;
  }

  /**
   * get the ingredient logs before the given date
   *
   * @param date the date
   * @return ObservableList of ingredient logs
   */
  public ObservableList<IngredientLog> getIngredientLogsBefore(LocalDate date) {
    ObservableList<IngredientLog> ret = FXCollections.observableArrayList();
    for (IngredientLog l : ingredientLogs) {
      if (l.getLocalDate().isBefore(date.plusDays(1))) {
        ret.add(l);
      }
    }
    return ret;
  }

  /**
   * Get the ingredient logs of the given date
   *
   * @param date the date
   * @return arrayList of ingredient logs
   */
  private ArrayList<IngredientLog> getIngredientLogs(LocalDate date) {
    ArrayList<IngredientLog> logs = new ArrayList<>();
    for (IngredientLog l : getIngredientLogs()) {
      if (l.getLocalDate().equals(date)) logs.add(l);
    }
    return logs;
  }

  /**
   * get the order logs in the given date interval
   *
   * @param start the start date
   * @param end the end date
   * @return ObservableList of order logs
   */
  public ObservableList<BillLog> getBillLogs(LocalDate start, LocalDate end) {
    ObservableList<BillLog> ret = FXCollections.observableArrayList();
    for (BillLog l : billLogs) {
      if (l.getLocalDate().isBefore(end.plusDays(1))
          && l.getLocalDate().isAfter(start.minusDays(1))) {
        ret.add(l);
      }
    }
    return ret;
  }

  /**
   * get the order logs after the given date
   *
   * @param date the date
   * @return ObservableList of order logs
   */
  public ObservableList<BillLog> getBillLogsAfter(LocalDate date) {
    ObservableList<BillLog> ret = FXCollections.observableArrayList();
    for (BillLog l : billLogs) {
      if (l.getLocalDate().isAfter(date.minusDays(1))) {
        ret.add(l);
      }
    }
    return ret;
  }

  /**
   * get the order logs before the given date
   *
   * @param date the date
   * @return ObservableList of order logs
   */
  public ObservableList<BillLog> getBillLogsBefore(LocalDate date) {
    ObservableList<BillLog> ret = FXCollections.observableArrayList();
    for (BillLog l : billLogs) {
      if (l.getLocalDate().isBefore(date.plusDays(1))) {
        ret.add(l);
      }
    }
    return ret;
  }

  /**
   * Get the bill logs of the given date
   *
   * @param date the date
   * @return arrayList of bill logs
   */
  private ArrayList<BillLog> getBillLogs(LocalDate date) {
    ArrayList<BillLog> logs = new ArrayList<>();
    for (BillLog l : getBillLogs()) {
      if (l.getLocalDate().equals(date)) logs.add(l);
    }
    return logs;
  }

  /**
   * Add a new ingredient log
   *
   * @param ingredient the name of the ingredient
   * @param number the number added
   */
  public void addIngredientLog(Ingredient ingredient, double number) {
    ingredientLogs.add(new IngredientLog(ingredient, number));
  }

  /**
   * Add a new order log
   *
   * @param orderID the orderID
   * @param tableID the tableID
   */
  public void addBillLog(int orderID, int tableID) {
    billLogs.add(new BillLog(orderID, tableID));
  }

  /**
   * Get bill log by its id
   *
   * @param billID the ID
   * @return the bill log
   */
  private BillLog getOrderLog(int billID) {
    for (BillLog l : billLogs) {
      if (l.getBillID() == billID) {
        return l;
      }
    }
    return null;
  }

  /**
   * Add a new action to the bill log
   *
   * @param billID the order ID
   * @param action the action
   */
  public void addAction(int billID, String action) {
    BillLog l = getOrderLog(billID);
    if (l != null) l.addAction(action);
  }

  /**
   * Add the last action, the finish action to the bill log, set the price at the same time
   *
   * @param billID the bill ID
   * @param price the price of the bill
   */
  public void finishBillLog(int billID, double price) {
    BillLog l = getOrderLog(billID);
    if (l != null) {
      l.addAction("Total: " + String.valueOf(Math.round(price * 100) / 100.0));
      l.setPrice(price);
    }
  }

  /**
   * Calculate the income by the given date
   *
   * @param d the date
   * @return the total income
   */
  public double getIncome(LocalDate d) {
    double income = 0;
    for (BillLog l : getBillLogs(d)) {
      income += l.getPrice();
    }
    return income;
  }

  /**
   * Get the total expenses of ingredients of the given date
   *
   * @param d the date
   * @return the total expenses, always <= 0
   */
  public double getExpenses(LocalDate d) {
    double outcome = 0;
    for (IngredientLog l : getIngredientLogs(d)) {
      outcome += l.getPrice();
    }
    return outcome;
  }

  /**
   * get the name and number of ingredients added, from most to least in a given time interval
   *
   * @param start the start date
   * @param end the end date
   * @return the Observable list of strings of the info
   */
  public ObservableList<String> getIngredientStats(LocalDate start, LocalDate end) {
    ObservableList<IngredientLog> logs = getIngredientLogs(start, end);
    HashMap<String, Double> map = new HashMap<>();
    for (IngredientLog l : logs) {
      if (map.containsKey(l.getIngredient())) {
        map.replace(l.getIngredient(), map.get(l.getIngredient()) + l.getNumber());
      } else {
        map.put(l.getIngredient(), l.getNumber());
      }
    }
    List<Map.Entry<String, Double>> list = new ArrayList<>(map.entrySet());
    list.sort(Comparator.comparing(Map.Entry::getValue));
    ObservableList<String> ret = FXCollections.observableArrayList();
    for (int i = list.size() - 1; i >= 0; i--) {
      ret.add(list.get(i).getKey() + (" :  ") + (list.get(i).getValue()) + System.lineSeparator());
    }
    return ret;
  }

  /**
   * get the name and number of dishes sold, from most to least in a given time interval
   *
   * @param start the start date
   * @param end the end date
   * @return the Observable list of strings of the info
   */
  public ObservableList<String> getDishStats(LocalDate start, LocalDate end) {
    ObservableList<BillLog> logs = getBillLogs(start, end);
    HashMap<String, Integer> map = new HashMap<>();
    for (BillLog l : logs) {
      for (String i : l.getDishes()) {
        if (map.containsKey(i)) {
          map.replace(i, map.get(i) + 1);
        } else {
          map.put(i, 1);
        }
      }
    }
    List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
    list.sort(Comparator.comparing(Map.Entry::getValue));
    ObservableList<String> ret = FXCollections.observableArrayList();
    for (int i = list.size() - 1; i >= 0; i--) {
      ret.add(list.get(i).getKey() + (" :  ") + (list.get(i).getValue()) + System.lineSeparator());
    }
    return ret;
  }
}
