

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Properties;

public class MailHandler {

    private HashMap<String, ArrayList<Email>> log;
    public void start(){
        try{
            FileInputStream fis = new FileInputStream("log.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            this.log = (HashMap) ois.readObject();
            ois.close();
            fis.close();


        }catch (IOException e){
            this.log = new HashMap<String, ArrayList<Email>>();

        }catch (ClassNotFoundException exception){
            this.log = new HashMap<String, ArrayList<Email>>();
        }
    }


    public void close(){
        try {
            FileOutputStream fos = new FileOutputStream("log.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this.log);
            oos.close();
            fos.close();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void sendMail(String addresslist, String subject, String sendtext) {

        final String username = "123@gmail.com";
        final String password = "cs234";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("nimalpereracs@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(addresslist)
            );
            message.setSubject(subject);
            message.setText(sendtext);
           // Transport.send(message);
            System.out.println("Mail send");
            Email mail = new Email(addresslist,subject,sendtext);
            Calendar date = Calendar.getInstance();
            SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
            String ds = f.format(date.getTime());
            if(log.containsKey(ds)){
                log.get(ds).add(mail);
            } else{
                ArrayList<Email> temp = new ArrayList<>();
                temp.add(mail);
                log.put(ds,temp);

            }



        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Email> retrieve(String date){
        if(this.log.containsKey(date)){
            return this.log.get(date);
        } else{
            return null;
        }

    }

}
