package util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FileOperation {

    public void writeIntoFile(List<Object> records, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(records);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public List<Object> readFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            ArrayList<Object> records = ((ArrayList<Object>) ois.readObject());
            return records;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return new ArrayList<Object>();
    }
}
