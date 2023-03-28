package ru.job4j.tracker;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;

public class TrackerTest {
    @Test
    public void whenFindAll() {
        Tracker tracker = new Tracker();
        Item bug = new Item("Bug");
        tracker.add(bug);
        assertThat(tracker.findAll().get(0).getName(), is(bug.getName()));
    }

    @Test
    public void whenFindByName() {
        Tracker tracker = new Tracker();
        tracker.add(new Item("bug"));
        tracker.add(new Item("name"));
        Item item = tracker.add(new Item("first"));
        assertThat(tracker.findByName("first").get(0).getId(), is(item.getId()));
    }

    @Test
    public void whenReplace() {
        Tracker tracker = new Tracker();
        Item bug = new Item("Bug");
        tracker.add(bug);
        int id = bug.getId();
        Item bugWithDesc = new Item("Bug with description");
        tracker.replace(id, bugWithDesc);
        assertThat(tracker.findById(id).getName(), is(bugWithDesc.getName()));
    }

    @Test
    public void whenDelete() {
        Tracker tracker = new Tracker();
        Item bug = new Item("Bug");
        tracker.add(bug);
        int id = bug.getId();
        tracker.delete(id);
        assertThat(tracker.findById(id), is(nullValue()));
    }
}