package no.ntnu.idatg1002.grp8.inex.financialdata.util;

import java.util.List;
import java.util.Optional;
import lombok.experimental.UtilityClass;
import no.ntnu.idatg1002.grp8.inex.financialdata.Revenue;

/**
 * The RevenueUtil class contains methods for updating a list of revenue items by adding or removing
 * new revenue data based on the date.
 */
@UtilityClass
public class RevenueUtility {

  /**
   * The function updates a list of revenue items by subtracting the revenue data
   * of a new revenue item
   * from the revenue data of all revenue items with dates equal to or after the new revenue item's
   * date, or by setting the revenue data of the new revenue item to the difference between
   * the revenue
   * data of the revenue item with the previous date and the revenue data of the new revenue item if
   * there is no revenue item with the previous date.
   *
   * @param revenueList a list of Revenue objects representing the revenue data for a certain
   *                   period of time
   * @param newRevenue  The new revenue object that needs to be added or updated in the revenueList.
   */
  public static void updateRevenueExpenseAdded(List<Revenue> revenueList, Revenue newRevenue) {
    if (revenueList.stream().anyMatch(r -> r.getDate().equals(newRevenue.getDate()))) {
      for (Revenue revenueItem : revenueList) {
        if (revenueItem.getDate().isAfter(newRevenue.getDate())
            || revenueItem.getDate().equals(newRevenue.getDate())) {
          revenueItem.setRevenueData(revenueItem.getRevenueData() - newRevenue.getRevenueData());
        }
      }
    } else {
      Optional<Revenue> yesterdayRevenue = revenueList.stream()
          .filter(r -> r.getDate().equals(newRevenue.getDate().minusDays(1)))
          .findFirst();
      if (yesterdayRevenue.isPresent()) {
        newRevenue.setRevenueData(
            yesterdayRevenue.get().getRevenueData() - newRevenue.getRevenueData());
        revenueList.add(newRevenue);
      } else {
        revenueList.add(newRevenue);
      }
    }
  }

  /**
   * This function updates the revenue data of all revenue items in a list that come
   * after or are equal to a new revenue item's date.
   *
   * @param revenueList A list of Revenue objects that contains the revenue data.
   * @param newRevenue  A new revenue object that needs to be added to the revenueList and used to
   *                    update the revenue data of existing revenue objects in the list.
   */
  public static void updateRevenueExpenseRemoved(List<Revenue> revenueList, Revenue newRevenue) {
    for (Revenue revenueItem : revenueList) {
      if (revenueItem.getDate().isAfter(newRevenue.getDate())
          || revenueItem.getDate().equals(newRevenue.getDate())) {
        revenueItem.setRevenueData(revenueItem.getRevenueData() + newRevenue.getRevenueData());
      }
    }
  }

  /**
   * The function updates a list of revenue data by adding a new revenue item or updating existing
   * items based on the date.
   *
   * @param revenueList A list of Revenue objects representing the revenue data for a certain period
   *                   of time.
   * @param newRevenue  A Revenue object representing the new revenue data to be added to the
   *                    revenueList.
   */
  public static void updateRevenueIncomeAdded(List<Revenue> revenueList, Revenue newRevenue) {
    if (revenueList.stream().anyMatch(r -> r.getDate().equals(newRevenue.getDate()))) {
      for (Revenue revenueItem : revenueList) {
        if (revenueItem.getDate().isAfter(newRevenue.getDate())
            || revenueItem.getDate().equals(newRevenue.getDate())) {
          revenueItem.setRevenueData(revenueItem.getRevenueData() + newRevenue.getRevenueData());
        }
      }
    } else {
      Optional<Revenue> yesterdayRevenue = revenueList.stream()
          .filter(r -> r.getDate().equals(newRevenue.getDate().minusDays(1)))
          .findFirst();
      if (yesterdayRevenue.isPresent()) {
        newRevenue.setRevenueData(
            yesterdayRevenue.get().getRevenueData() + newRevenue.getRevenueData());
        revenueList.add(newRevenue);
      } else {
        revenueList.add(newRevenue);
      }
    }
  }

  /**
   * The function updates the revenue data of all revenue items in a list that come after or are
   * equal to a new revenue item by subtracting the new revenue data from their existing
   * revenue data.
   *
   * @param revenueList A list of Revenue objects that contains the revenue data.
   * @param newRevenue  The new revenue object that needs to be removed from the revenueList.
   */
  public static void updateRevenueIncomeRemoved(List<Revenue> revenueList, Revenue newRevenue) {
    for (Revenue revenueItem : revenueList) {
      if (revenueItem.getDate().isAfter(newRevenue.getDate())
          || revenueItem.getDate().equals(newRevenue.getDate())) {
        revenueItem.setRevenueData(revenueItem.getRevenueData() - newRevenue.getRevenueData());
      }
    }
  }
}