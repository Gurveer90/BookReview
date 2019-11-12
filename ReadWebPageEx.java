package ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class ReadWebPageEx {

    public void printData() {
        BufferedReader br = null;
        try {
            String theURL = "https://www.ugrad.cs.ubc.ca/~cs210/2018w1/welcomemsg.html"; //this can point to any URL
            URL url = new URL(theURL);
            br = new BufferedReader(new InputStreamReader(url.openStream()));

            String line;

            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {

                sb.append(line);
                sb.append(System.lineSeparator());
            }

            System.out.println(sb);
        } catch (Exception exc) {

        } finally {

            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {
            }

        }
    }
}