package application.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * handle all the data process about the DKP file including the format and convert
 */
class DateProcessor {
    static HashSet<String> rangeDate = new HashSet<>();

    static void updateRangeDate(LocalDate startDate, LocalDate endDate) {
        rangeDate.clear();

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            //format to dkp file time stamp
            rangeDate.add(date.toString().replace("-", "/"));
        }
    }

    static void getCurrentWeekDate() {
        rangeDate.clear();

        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        Calendar weeks = Calendar.getInstance();
        weeks.setFirstDayOfWeek(Calendar.MONDAY);
        SimpleDateFormat dkpTimeFormat = new SimpleDateFormat("yyyy/MM/dd");
        for (int i : new int[]{2, 3, 4, 5, 6, 7, 1}) {
            weeks.set(Calendar.DAY_OF_WEEK, i);
            rangeDate.add(dkpTimeFormat.format(weeks.getTime()));
        }
    }
}
