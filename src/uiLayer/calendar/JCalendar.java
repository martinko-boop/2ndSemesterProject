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
 * Edited by Miroslav, Aleks and Tamás for a school project
 * School: University College Of Northern Denmark
 * Project: Booking System
 * Year: 2021
 * 
 */
package uiLayer.calendar;

import uiLayer.BookingPanel;
import uiLayer.calendar.events.*;
import uiLayer.calendar.ui.*;
import uiLayer.calendar.ui.strategy.*;
import uiLayer.calendar.util.EventCollectionRepository;
import uiLayer.calendar.util.EventRepository;
import org.apache.commons.collections4.collection.UnmodifiableCollection;

import modelLayer.Room;
import databus.Databus;
import modelLayer.Booking;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

/**
 * @author theodorcostache
 */
public class JCalendar extends JPanel
{

    private static final long serialVersionUID = 1L;
    private final List<IntervalChangedListener> intervalChangedListener;
    private HeaderPanel headerPane;
    private ContentPanel contentPane;
    private Config config;
    private LocalDate selectedDay;
    // XXX Added room parameter
    private Room room;
    private BookingPanel bookingPanel;

    /**
     * Creates a new instance of {@link JCalendar} XXX changed to accept
     * BookingPanel in its constructor.
     */
    public JCalendar(BookingPanel bookingPanel)
    {
        this.bookingPanel = bookingPanel;
        intervalChangedListener = new ArrayList<IntervalChangedListener>();
        config = new Config();
        // formater = new DefaultCalendarEventFormat();
        selectedDay = LocalDate.now();
        // XXX No room selected when launching.
        room = null;
        initGui();
        bindListeners();

        EventCollectionRepository.register(this);
    }

    /**
     * Initializes the GUI
     */
    private void initGui()
    {
        this.setBackground(Color.white);
        headerPane = new HeaderPanel();
        contentPane = new ContentPanel(this);

        headerPane.getIntervalLabel()
                .setText(contentPane.getStrategy().getDisplayInterval());
        this.setLayout(new GridBagLayout());
        final GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.fill = GridBagConstraints.BOTH;
        add(headerPane, c);
        c.gridx = 0;
        c.gridy = 1;
        c.weighty = 0.9;
        c.insets = new Insets(10, 10, 10, 10);
        add(contentPane, c);

    }

    /**
     * Binds listeners to the components
     */
    private void bindListeners()
    {

        /*
         * final ActionListener strategyActionListener = new ActionListener() {
         * 
         * @Override public void actionPerformed(final ActionEvent e) { //final
         * boolean isDay = e.getSource().equals(headerPane.getDayButton());
         * //final boolean isWeek =
         * e.getSource().equals(headerPane.getWeekButton()); //final
         * DisplayStrategy.Type type = isDay ? Type.DAY : isWeek ? Type.WEEK :
         * Type.MONTH;
         * 
         * //if (getDisplayStrategy() != type) // setDisplayStrategy(type,
         * getSelectedDay());
         * 
         * } };
         */

        // headerPane.getDayButton().addActionListener(strategyActionListener);
        // headerPane.getWeekButton().addActionListener(strategyActionListener);
        // headerPane.getMonthButton().addActionListener(strategyActionListener);

        headerPane.getScrollLeft().addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent e)
            {
                final DisplayStrategy strategy = contentPane.getStrategy();
                strategy.moveIntervalLeft();
                headerPane.getIntervalLabel().setText(
                        contentPane.getStrategy().getDisplayInterval());
                final IntervalChangedEvent event = new IntervalChangedEvent(
                        JCalendar.this, strategy.getType(),
                        config.getIntervalStart(), config.getIntervalEnd());
                
                for (final IntervalChangedListener listener : intervalChangedListener)
                {
                    listener.intervalChanged(event);
                }
                // XXX New code 
                try
                {
	                bookingPanel.getAllBookingsForAWeek(event.getIntervalStart().atStartOfDay());
	                Databus.getInstance().clearBookings();
	                Databus.getInstance().queryBookings(event.getIntervalStart().atStartOfDay());
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }

            }
        });

        headerPane.getScrollRight().addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent e)
            {
                final DisplayStrategy strategy = contentPane.getStrategy();
                strategy.moveIntervalRight();
                headerPane.getIntervalLabel().setText(
                        contentPane.getStrategy().getDisplayInterval());
                final IntervalChangedEvent event = new IntervalChangedEvent(
                        JCalendar.this, strategy.getType(),
                        config.getIntervalStart(), config.getIntervalEnd());

                for (final IntervalChangedListener listener : intervalChangedListener)
                {
                    listener.intervalChanged(event);
                }

                // XXX New code
                try
                {
                    bookingPanel.getAllBookingsForAWeek(
                            event.getIntervalStart().atStartOfDay());
                    Databus.getInstance().clearBookings();
                    Databus.getInstance().queryBookings(event.getIntervalStart().atStartOfDay());

                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });

        // XXX Added month change
        headerPane.getScrollMonthLeft().addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent e)
            {
                final DisplayStrategy strategy = contentPane.getStrategy();
                strategy.moveMonthIntervalLeft();
                headerPane.getIntervalLabel().setText(
                        contentPane.getStrategy().getDisplayInterval());
                final IntervalChangedEvent event = new IntervalChangedEvent(
                        JCalendar.this, strategy.getType(),
                        config.getIntervalStart(), config.getIntervalEnd());

                for (final IntervalChangedListener listener : intervalChangedListener)
                {
                    listener.intervalChanged(event);
                }
                // XXX New code
                try
                {
                    bookingPanel.getAllBookingsForAWeek(
                            event.getIntervalStart().atStartOfDay());
                	Databus.getInstance().clearBookings();
                	Databus.getInstance().queryBookings(event.getIntervalStart().atStartOfDay());
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }

            }
        });

        headerPane.getScrollMonthRight().addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent e)
            {
                final DisplayStrategy strategy = contentPane.getStrategy();
                strategy.moveMonthIntervalRight();
                headerPane.getIntervalLabel().setText(
                        contentPane.getStrategy().getDisplayInterval());
                final IntervalChangedEvent event = new IntervalChangedEvent(
                        JCalendar.this, strategy.getType(),
                        config.getIntervalStart(), config.getIntervalEnd());

                for (final IntervalChangedListener listener : intervalChangedListener)
                {
                    listener.intervalChanged(event);
                }
                // XXX New code
                try
                {
                    bookingPanel.getAllBookingsForAWeek(
                            event.getIntervalStart().atStartOfDay());
                    Databus.getInstance().clearBookings();
                    Databus.getInstance().queryBookings(event.getIntervalStart().atStartOfDay());
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }

            }
        });
    }

    /**
     * Returns the selected day in the calendar
     *
     * @return
     */
    public LocalDate getSelectedDay()
    {
        return selectedDay;
    }

    /**
     * XXX changed to accept LocalDate instead of Calendar
     * @param date 
     */
    public void setSelectedDay(final LocalDate date)
    {
        // selectedDay = CalendarUtil.getCalendar(date, true);
        selectedDay = date;
        final DisplayStrategy strategy = contentPane.getStrategy();
        strategy.setIntervalStart(date);
        headerPane.getIntervalLabel().setText(strategy.getDisplayInterval());
        final IntervalChangedEvent event = new IntervalChangedEvent(
                JCalendar.this, strategy.getType(), config.getIntervalStart(),
                config.getIntervalEnd());

        for (final IntervalChangedListener listener : intervalChangedListener)
        {
            listener.intervalChanged(event);
        }
    }

    /**
     * Gets the current display strategy.
     *
     * @return {@link Type}
     */
    public DisplayStrategy.Type getDisplayStrategy()
    {
        return contentPane.getStrategy().getType();
    }

    /**
     * Sets the display strategy
     *
     * @param strategyType the {@link Type} of strategy to be used
     * @param displayDate if not null then this value will be used as a
     *            reference for calculating the interval start
     */
    /*
     * public void setDisplayStrategy(final Type strategyType, final LocalDate
     * displayDate) {
     * 
     * //final DisplayStrategy strategy =
     * DisplayStrategyFactory.getStrategy(contentPane, strategyType);
     * contentPane.setStrategy(strategy); if (displayDate != null) {
     * setSelectedDay(displayDate); } }
     */

    /**
     * Returns a {@link Collection} of selected {@link CalendarEvent}
     *
     * @return an {@link UnmodifiableCollection}
     */
    // XXX Changed to booking
    public Collection<Booking> getSelectedCalendarEvents()
    {
        return EventCollectionRepository.get(this).getSelectedEvents();
    }

    /**
     * Returns a {@link Collection} of all {@link CalendarEvent}
     *
     * @return an {@link UnmodifiableCollection}
     */
    // XXX CalendarEvent changed to Booking
    public Collection<Booking> getCalendarEvents()
    {
        return EventCollectionRepository.get(this).getAllEvents();
    }

    /**
     * Adds a {@link IntervalChangedListener}
     *
     * @param listener
     */
    public void addIntervalChangedListener(
            final IntervalChangedListener listener)
    {
        this.intervalChangedListener.add(listener);
    }

    /**
     * Removes the given {@link IntervalChangedListener}
     *
     * @param listener
     */
    public void removeIntervalChangedListener(
            final IntervalChangedListener listener)
    {
        this.intervalChangedListener.remove(listener);
    }

    /*
     * XXX used for changing the model - day, week, month public void
     * addCollectionChangedListener(final ModelChangedListener listener) {
     * EventCollectionRepository.get(this).addCollectionChangedListener(listener
     * ); }
     * 
     * 
     * public void removeCollectionChangedListener(final ModelChangedListener
     * listener) {
     * EventCollectionRepository.get(this).removeCollectionChangedListener(
     * listener); }
     */

    /**
     * @param selectionChangedListener
     */
    public void addSelectionChangedListener(
            final SelectionChangedListener selectionChangedListener)
    {
        EventCollectionRepository.get(this)
                .addSelectionChangedListener(selectionChangedListener);
    }

    /**
     * @param selectionChangedListener
     */
    public void removeSelectionChangedListener(
            final SelectionChangedListener selectionChangedListener)
    {
        EventCollectionRepository.get(this)
                .removeSelectionChangedListener(selectionChangedListener);
    }

    /**
     * @param intervalSelectionListener
     */
    public void addIntervalSelectionListener(
            final IntervalSelectionListener intervalSelectionListener)
    {
        EventRepository.get().addIntervalSelectionListener(this,
                intervalSelectionListener);
    }

    /**
     * @param intervalSelectionListener
     */
    public void removeIntervalSelectionListener(
            final IntervalSelectionListener intervalSelectionListener)
    {
        EventRepository.get().removeIntervalSelectionListener(this,
                intervalSelectionListener);
    }

    /**
     * 
     * @param event
     */
    // XXX CalendarEvent changed to Booking
    public void addCalendarEvent(final Booking event)
    {
        EventCollectionRepository.get(this).add(event);
        validate();
        repaint();
    }

    /**
     * 
     * @param event
     */
    // XXX CalendarEvent changed to Booking
    public void removeCalendarEvent(final Booking event)
    {
        EventCollectionRepository.get(this).remove(event);
        validate();
        repaint();
    }

    // XXX Added
    public void removeAllEvents()
    {
        EventCollectionRepository.get(this).removeAllEvents();
        validate();
        repaint();
    }

    /**
     * @return
     */
    public Config getConfig()
    {
        return config;
    }

    /**
     * @param config
     */
    public void setConfig(final Config config)
    {
        this.config = config;
        validate();
        repaint();
    }

    // XXX removed Popup in calendar view
    /**
     * @return the {@link JPopupMenu} attached to this component
     */
    /*
     * public JPopupMenu getPopupMenu() { return popupMenu; }
     */

    /**
     * @param popupMenu the {@link JPopupMenu} to attach to this component
     */
    /*
     * public void setJPopupMenu(final JPopupMenu popupMenu) { this.popupMenu =
     * popupMenu; }
     */
    /**
     * XXX Added room getter
     * 
     * @return the room selected
     */
    public Room getRoom()
    {
        return room;
    }

    /**
     * XXX Added room setters
     * 
     * @param room
     */
    public void setRoom(Room room)
    {
        this.room = room;
        contentPane.repaint();
    }

}
