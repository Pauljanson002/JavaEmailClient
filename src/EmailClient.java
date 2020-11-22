// your index number

//import libraries
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class EmailClient {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        FileHandler fw = new FileHandler();
        MailHandler mail = new MailHandler();
        mail.start();
        String[] textInput = fw.getTextInfo().split("\\r?\\n");
        ArrayList<Recipient> recipients = new ArrayList<Recipient>();
        for(String text : textInput){
            String[] recipientDetail = text.split(":|,");
            Recipient recipient = null;
            try {
                if (recipientDetail[0].equals("Official")) {
                    recipient = new Official(recipientDetail[1], recipientDetail[2], recipientDetail[3]);
                } else if (recipientDetail[0].equals("Official_friend")) {
                    recipient = new Official_friend(recipientDetail[1], recipientDetail[2], recipientDetail[3], recipientDetail[4]);
                } else if (recipientDetail[0].equals("Personal")) {
                    if (recipientDetail.length == 4) {
                        recipient = new Personal(recipientDetail[1], recipientDetail[2], recipientDetail[3]);
                    } else if (recipientDetail.length == 5) {
                        recipient = new Personal(recipientDetail[1], recipientDetail[3], recipientDetail[4], recipientDetail[2]);
                    } else {
                        System.out.println("Please check your input ");
                    }
                } else {
                    System.out.println("Please check your input ");
                }
            } catch (ArrayIndexOutOfBoundsException error){
                System.out.println("Not enough data present ");
            }
            recipients.add(recipient);
        }


        ArrayList<Wishable> todayBirthday = new ArrayList<Wishable>();
        for(Recipient recipient : recipients){
            if(recipient instanceof Wishable){
                Wishable wishingContact = (Wishable) recipient ;
                Calendar c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH)+1;
                if(wishingContact.checkBirthday(day,month)) todayBirthday.add(wishingContact);
            }
        }
        for(Wishable wisher: todayBirthday) {
            Recipient r = (Recipient) wisher;
            mail.sendMail(r.getEmail(),"Wish",wisher.getMessage());
        }

        System.out.println("Enter option type: \n"
                + "1 - Adding a new recipient\n"
                + "2 - Sending an email\n"
                + "3 - Printing out all the recipients who have birthdays\n"
                + "4 - Printing out details of all the emails sent\n"
                + "5 - Printing out the number of recipient  in the application");

        int option = sc.nextInt();

        switch(option){
            case 1:

                sc.nextLine();
                String input = sc.nextLine();
                String[] infos  = input.split(":|,");
                Recipient recipient = null;
                try {
                    if (infos[0].equals("Official")) {
                        recipient = new Official(infos[1], infos[2], infos[3]);
                    } else if (infos[0].equals("Official_friend")) {
                        recipient = new Official_friend(infos[1], infos[2], infos[3], infos[4]);
                    } else if (infos[0].equals("Personal")) {
                        if (infos.length == 4) {
                            recipient = new Personal(infos[1], infos[2], infos[3]);
                        } else if (infos.length == 5) {
                            recipient = new Personal(infos[1], infos[3], infos[4], infos[2]);
                        } else {
                            System.out.println("Please Check your input ");
                        }
                    } else {
                        System.out.println("Please check your input ");
                    }
                } catch (ArrayIndexOutOfBoundsException error){
                    System.out.println("Not enough data present");
                }
                if (recipient != null ){
                    fw.writeToText(recipient);
                }
                break;
            case 2:

                sc.nextLine();
                String userInput = sc.nextLine();
                String email,subject,content;
                String[] arr = userInput.split(",");
                try {
                    email = arr[0];
                    subject = arr[1];
                    content = arr[2];
                    mail.sendMail(email,subject,content);
                }
                catch(ArrayIndexOutOfBoundsException e) {
                    System.out.println("Not enough inputs");
                }

                break;
            case 3:
                sc.nextLine();
                String q = sc.nextLine();
                String[] query = q.split("/");
                ArrayList<Recipient> det = new ArrayList<Recipient>();
                for(Recipient r: recipients){
                    if(r instanceof Wishable) {
                        Wishable b = (Wishable) r;
                        if(b.checkBirthday(Integer.parseInt(query[2]),Integer.parseInt(query[1])))
                            det.add(r);
                    }
                }
                for(Recipient r : det)
                    System.out.println(r.getDetails());
                break;

            case 4:
                sc.nextLine();
                String queryAsString = sc.nextLine();
                String[] temp = queryAsString.split("/");
                Calendar temp_date = Calendar.getInstance();
                temp_date.set(Integer.parseInt(temp[0]),Integer.parseInt(temp[1])-1,Integer.parseInt(temp[2]));
                SimpleDateFormat ff = new SimpleDateFormat("yyyy/MM/dd");
                queryAsString = ff.format(temp_date.getTime());
                ArrayList<Email> mailList  = mail.retrieve(queryAsString);
                if(mailList == null){
                    System.out.println("No messages sent on that day ");
                }else{
                    for(Email e : mailList){
                        System.out.println(e.getDetails());
                    }
                }
                break;
            case 5:

                System.out.println(Recipient.getCount());
                break;

        }
        mail.close();

    }
}
