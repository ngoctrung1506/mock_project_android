package bu22.fga.mockproject_group2.util;

/**
 * Created by Admin on 25/04/2018.
 */

public class GridViewUtil {

  public static String getDayOfWeekByPosition(int position) {
    String dayOfWeek = null;
    switch (position) {
      case 1:
        dayOfWeek = "Monday";
        break;
      case 2:
        dayOfWeek = "Tuesday";
        break;
      case 3:
        dayOfWeek = "Wednesday";
        break;
      case 4:
        dayOfWeek = "Thurday";
        break;
      case 5:
        dayOfWeek = "Friday";
        break;
      case 6:
        dayOfWeek = "Saturday";
        break;
    }
    return dayOfWeek;
  }

}
