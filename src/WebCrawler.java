import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebCrawler {
    private Queue<String> urlQueue;
    private List<String> visitedUrl;

    public WebCrawler() {
        urlQueue = new LinkedList<>();
        visitedUrl = new ArrayList<>();
    }
    public void crawl(String rootUrl, int breakPoint){
        urlQueue.add(rootUrl);
        visitedUrl.add(rootUrl);
        while (!urlQueue.isEmpty()){

            String s = urlQueue.remove();
            String rawHTML = "";
            try {
                URL url = new URL(s);
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String inputLine = in.readLine();
                while (inputLine != null){
                    rawHTML += inputLine;
                    inputLine = in.readLine();
                }
                in.close();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            String urlPattern = "(www|http:|https:)+[^\\s]+[\\w]";
            Pattern pattern = Pattern.compile(urlPattern);
            Matcher matcher = pattern.matcher(rawHTML);

            breakPoint = getBreakPoint(breakPoint, matcher);

            if (breakPoint == 0){
                break;
            }
        }
    }

    private  int getBreakPoint(int breakPoint, Matcher matcher){
        while (matcher.find()){
            String actualUrl = matcher.group();
            if (!visitedUrl.contains(actualUrl)){
                visitedUrl.add(actualUrl);
                System.out.println("Website found with URL: " + actualUrl);
                urlQueue.add(actualUrl);
            }
            if (breakPoint == 0){
                break;
            }
            breakPoint--;
        }
        return breakPoint;
    }
}
