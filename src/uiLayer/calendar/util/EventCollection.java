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

/**
 * Edited by Miroslav and Tamás for a school project
 * School: University College Of Northern Denmark
 * Project: Booking System
 * Year: 2021
 * 
 */
package uiLayer.calendar.util;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import modelLayer.Booking;
import uiLayer.calendar.events.SelectionChangedListener;

/**
 * 
 * @author theodorcostache 
 * 
 */
public interface EventCollection 
{
	//XXX CalendarEvents have been renamed to Booking
	void add(Booking event);

	void remove(Booking event);

        void removeAll(List<Booking> calendarEventList);
    
        //XXX added
        void removeAllEvents();

	void addSelected(Booking event);

	void removeSelected(Booking event);

	void clearSelected(Booking event, boolean b);

	//XXX removed
        /*List<Booking> getHolidayEvents(LocalDate date);*/

	Collection<Booking> getSelectedEvents();

	Collection<Booking> getEvents(LocalDate date);

        Collection<Booking> getAllEvents();
        //XXX removed
	/*void addCollectionChangedListener(ModelChangedListener listener);

	void removeCollectionChangedListener(ModelChangedListener listener);
	*/
	void addSelectionChangedListener(SelectionChangedListener selectionChangedListener);

	void removeSelectionChangedListener(SelectionChangedListener selectionChangedListener);
}
