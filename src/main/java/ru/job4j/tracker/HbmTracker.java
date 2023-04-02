package ru.job4j.tracker;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class HbmTracker implements Store, AutoCloseable {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    @Override
    public Item add(Item item) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(item);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return item;
    }

    @Override
    public boolean replace(Integer id, Item item) {
        Session session = sf.openSession();
        boolean haveChange = false;
        try {
            session.beginTransaction();
            session.createQuery("Update Item set name=:name, created=:created where id=:id")
                    .setParameter("id", id)
                    .setParameter("name", item.getName())
                    .setParameter("created", item.getCreated())
                    .executeUpdate();
            session.getTransaction().commit();
            Item newItem = findById(id);
            haveChange = newItem.equals(item);
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return haveChange;
    }

    @Override
    public boolean delete(Integer id) {
        Session session = sf.openSession();
        boolean haveChange = false;
        try {
            session.beginTransaction();
            Item item = new Item();
            item.setId(id);
            session.delete(item);
            session.getTransaction().commit();
            haveChange = true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return haveChange;
    }

    @Override
    public List<Item> findAll() {
        Session session = sf.openSession();
        List<Item> list = null;
        try {
           list = session.createQuery("From Item", Item.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return list;
    }

    @Override
    public List<Item> findByName(String key) {
        Session session = sf.openSession();
        List<Item> list = null;
        try {
            list = session.createQuery("From Item where name=:name", Item.class)
                    .setParameter("name", key)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return list;
    }

    @Override
    public Item findById(Integer id) {
        Session session = sf.openSession();
        Item item = null;
        try {
            item = session.createQuery("From Item where id=:id", Item.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return item;
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}