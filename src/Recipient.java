public abstract class Recipient {
    private static int count = 0;

    public static int getCount() {
        return count;
    }

    private String name;
    private String email;



    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Recipient(String name, String email) {
        this.name = name;
        this.email = email;
        ++count;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
    public abstract String getDetails();
}
