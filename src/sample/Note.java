package sample;

public class Note extends SignInController{
    private String title;
    private String note;
    private int users_idUser;

    public Note(String title, String note, int users_idUser) {
        this.title = title;
        this.note = note;
        this.users_idUser = users_idUser;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getUsers_idUser() {
        ForeignKeyID fk = new ForeignKeyID();
        return fk.getID();
    }

    public void setUsers_idUser(int users_idUser) {
        this.users_idUser = users_idUser;
    }
}
