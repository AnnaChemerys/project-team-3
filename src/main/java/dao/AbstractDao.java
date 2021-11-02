package dao;

import model.HasId;
import util.FileOperation;

import java.util.List;

public abstract class AbstractDao<T extends HasId> {

    protected final String filename;
    protected List<T> items;
    protected final FileOperation<T> fileOperation;

    public AbstractDao() {
        this.filename = getFileName();
        this.fileOperation = new FileOperation<>();
        this.items = fileOperation.readFromFile(filename);
    }

    protected abstract String getFileName();

    public void save(T t) {
        List<T> tempList = items;
        tempList.add(t);

        fileOperation.writeIntoFile(tempList, filename);
        items.add(t);
    }

    public abstract void update(T t);

    public void delete(T t) {
        List<T> tempList = items;
        tempList.remove(t);

        fileOperation.writeIntoFile(tempList, filename);
        items = tempList;
    }

    public T getById(String id) {
        return items.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<T> getAll() {
        return items;
    }
}
