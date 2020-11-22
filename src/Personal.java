public class Personal extends Recipient implements Wishable {
    private String birthDate;
    private String nickName;

    public Personal(String name, String email, String birthDate, String nickName) {
        super(name, email);
        this.birthDate = birthDate;
        this.nickName = nickName;

    }

    public Personal(String name, String email, String birthDate) {
        this(name,email,birthDate,"");
    }

    @Override
    public String getDetails() {
        if(this.nickName.equals(""))
            return "Personal:"+this.getName()+","+this.getEmail()+","+this.birthDate;
        else
            return "Personal:"+this.getName()+","+this.nickName+","+this.getEmail()+","+this.birthDate;
    }

    @Override
    public String getMessage() {
        return "Happy birthday. Sarangan";
    }

    @Override
    public boolean checkBirthday(int day, int month) {
        String date = String.format("%02d/%02d",month,day);
        System.out.println(birthDate.substring(5));
        return birthDate.substring(5).equals(date);
    }
}
