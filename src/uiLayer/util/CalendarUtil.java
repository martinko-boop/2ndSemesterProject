/**
 * Copyright 2013 Theodor Costache
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License. 
 */
package uiLayer.util;

import uiLayer.model.CalendarEvent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author theodorcostache
 */
public class CalendarUtil {

    public static boolean isSameDay(final LocalDateTime date1, final LocalDateTime date2) {
        return date1.toLocalDate().equals(date2.toLocalDate());
    }

    public static boolean isSameMonth(final Calendar c1, final Calendar c2) {
        return c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH);
    }

    /**
     * XXX changed Date to LocalDateTime
     */
    public static boolean isToday(final LocalDateTime date) {
        /*final Calendar now = Calendar.getInstance();
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime().equals(now.getTime());*/
    	return date.toLocalDate().equals(LocalDate.now());
    }

    public static Calendar copyCalendar(final Calendar calendar, final boolean stripTime) {
        final Calendar c = Calendar.getInstance();
        c.setTime(calendar.getTime());
        if (stripTime) {
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
        }
        return c;
    }

    public static Calendar getCalendar(final Date date, final boolean stripTime) {
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        if (stripTime) {
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
        }
        return c;
    }

    public static Date createDate(final int year, final int month, final int day, final int hour,
                                  final int minutes, final int seconds, final int miliseconds) {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, day);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, seconds);
        calendar.set(Calendar.MILLISECOND, miliseconds);
        return calendar.getTime();
    }

    /**
     * XXX changed Date to LocalDateTime
    public static LocalDateTime stripTime(final LocalDateTime date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
     */
/*
 *      * XXX Deleted as part of the Util
    public static LocalDate createInDays(final LocalDate from, final int amount) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(from);
        cal.add(Calendar.DATE, amount);
        return cal.getTime();
    }

    public static Date createInWeeks(final Date date, final int amount) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.WEEK_OF_YEAR, amount);
        return cal.getTime();
    }

    public static Date createInMonths(final Date date, final int amount) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, amount);
        return cal.getTime();
    }
      */

    /**
     * XXX changed from Date to LocalDate/LocalDateTime. gets localdatetime and provides all LocalDates between them.
     */
    public static Collection<LocalDate> getDates(final LocalDateTime start, final LocalDateTime end) {

        final Set<LocalDate> result = new HashSet<LocalDate>();
        final LocalDate endDay = end.toLocalDate();
        LocalDate date = start.toLocalDate();
        result.add(date);
        while (date.plusDays(1).isBefore(endDay))
            result.add(date);

        result.add(endDay);
        return result;
    }
    
    /* XXX Deprecated
    public static long getTotalSeconds(final Date date) {
        final Calendar c = CalendarUtil.getCalendar(date, false);
        long seconds = c.get(Calendar.HOUR_OF_DAY) * 60 * 60;
        seconds += c.get(Calendar.MINUTE) * 60;
        seconds += c.get(Calendar.SECOND);
        return seconds;
    }*/

    public static int secondsToPixels(final LocalDateTime date, final int maxHeight) {
        //final long seconds = getTotalSeconds(date);
        final long seconds = date.toLocalTime().toSecondOfDay();
    	return Math.round(seconds * maxHeight / 86400.0f);
    }

    /*
     * XXX removed code, concerning Date.
     */
    public static LocalDateTime pixelToDate(final LocalDate day, final int posY, final int maxHeight) {
        final long seconds = Math.round(posY * 86400.0f / maxHeight);
        /*final Calendar c = CalendarUtil.getCalendar(day, false);
        c.set(Calendar.HOUR_OF_DAY, (int) (seconds / 3600));
        c.set(Calendar.MINUTE, (int) (seconds % 3600) / 60);
        c.set(Calendar.SECOND, (int) (seconds % 3600) % 60);
        return c.getTime();*/
        return day.atTime((int) (seconds / 3600), (int) (seconds % 3600) / 60, (int) (seconds % 3600) % 60);
    }

    public static Map<CalendarEvent, List<CalendarEvent>> getConflicting(final Collection<CalendarEvent> calendarEvents) {
        final List<CalendarEvent> clonedCollection = new ArrayList<CalendarEvent>(calendarEvents);

        final Map<CalendarEvent, List<CalendarEvent>> conflictingEvents = new HashMap<CalendarEvent, List<CalendarEvent>>();

        for (int i = 0; i < clonedCollection.size(); i++) {
            final CalendarEvent event1 = clonedCollection.get(i);
             conflictingEvents.put(event1, new ArrayList<CalendarEvent>());
            for (int j = 0; j < clonedCollection.size(); j++) {
                final CalendarEvent event2 = clonedCollection.get(j);
                if (event2.isAllDay() || event2.isHoliday())
                    continue;
                final LocalDateTime startA = event1.getStart();
                final LocalDateTime endA = event1.getEnd();
                final LocalDateTime startB = event2.getStart();
                final LocalDateTime endB = event2.getEnd();

                final boolean isStartABeforeEndB = (startA.compareTo(endB)) < 0;
                final boolean isEndAAfterStartB = (endA.compareTo(startB)) > 0;

                boolean isCurrentPairOverlap = false;

                isCurrentPairOverlap = isStartABeforeEndB && isEndAAfterStartB;

                if (isCurrentPairOverlap) {
                    conflictingEvents.get(event1).add(event2);
                }
            }

            Collections.sort(conflictingEvents.get(event1));
        }
        final Set<CalendarEvent> keys = new HashSet<CalendarEvent>(conflictingEvents.keySet());
        final Map<CalendarEvent, List<CalendarEvent>> result = new HashMap<CalendarEvent, List<CalendarEvent>>();

        for (final CalendarEvent event : keys) {
            final Set<CalendarEvent> visitedEvents = new HashSet<CalendarEvent>();
            final Set<CalendarEvent> tempSet = new HashSet<CalendarEvent>();
            copyAll(visitedEvents, tempSet, conflictingEvents, event);
            final List<CalendarEvent> newConflictingEventsList = new ArrayList<CalendarEvent>(tempSet);
            Collections.sort(newConflictingEventsList);
            result.put(event, newConflictingEventsList);
        }

        return result;
    }

    private static void copyAll(final Set<CalendarEvent> visitedEvents, final Set<CalendarEvent> tempSet,
                                final Map<CalendarEvent, List<CalendarEvent>> conflictingEvents, final CalendarEvent event) {

        for (final CalendarEvent ce : conflictingEvents.get(event)) {

            if (!visitedEvents.contains(ce)) {
                visitedEvents.add(ce);
                tempSet.addAll(conflictingEvents.get(ce));
                copyAll(visitedEvents, tempSet, conflictingEvents, ce);
            }
        }
    }

    public static Date roundDateToHalfAnHour(Date date, boolean roundUp) {
        Calendar c = getCalendar(date, false);
        int time = c.get(Calendar.MINUTE);

        if (time <= 30) {
            c.set(Calendar.MINUTE, roundUp ? 30 : 0);
        } else if (time > 30) {
            c.set(Calendar.MINUTE, roundUp ? 0 : 30);
            if(roundUp)
            c.add(Calendar.HOUR, 1);
        }
        c.set(Calendar.SECOND,0);
        c.set(Calendar.MILLISECOND,0);
        return c.getTime();
    }

}
