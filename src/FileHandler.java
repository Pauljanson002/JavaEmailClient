import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileHandler {



    public String getTextInfo()
    {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines( Paths.get("clientList.txt"), StandardCharsets.UTF_8))
        {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    public void writeToText(Recipient r){
        try {
            FileWriter outputStream = null;
            outputStream = new FileWriter("clientList.txt",true);
            outputStream.append("\n"+r.getDetails());
            outputStream.close();
        } catch (IOException e){
            System.out.println("Error");
        }



    }

}
