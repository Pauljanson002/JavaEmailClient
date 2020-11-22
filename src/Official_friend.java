public class Official_friend extends Official implements Wishable {
    private String birthDate;

    public Official_friend(String name, String email, String designation, String birthDate) {
        super(name, email, designation);
        this.birthDate = birthDate;
        String[] dob = birthDate.split("/");
    }


    @Override
    public String getDetails() {
        return "Official_friend:"+this.getName()+","+this.getEmail()+","+this.getDesignation()+","+this.birthDate;
    }


    @Override
    public String getMessage() {
        return "Hearty wishes to your birthday. Sarangan";
    }

    @Override
    public boolean checkBirthday(int day, int month) {
        String date = String.format("%02d/%02d",month,day);
        return birthDate.substring(5).equals(date);
    }
}
