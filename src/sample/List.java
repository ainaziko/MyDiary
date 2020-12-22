package sample;

public class List {
     private String title;
     private int id;
     private String note;

    public List(String title, int id, String note) {
        this.title = title;
        this.id = id;
        this.note = note;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public String getNote() {
        return note;
    }
}
