package grability.com.grability;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class JsonAPI {
    public static String getHtmlContent(String link) {
        URL obj = null;
        HttpURLConnection con = null;
        String response = null;
        String text = null;

        try {
            text="";
            obj = new URL(link);
            URLConnection yc = obj.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    yc.getInputStream()));
            while ((response = in.readLine()) != null)
                text += response + "\r\n";
            in.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
}