import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class regxTest {
    private static Pattern timeStamp = Pattern.compile("(?<=（)(.+?)(?=[\u4e00-\u9fa5]）)");

    public static void main(String[] args) {
        String test = "2018/10/17 12:34    \t完成帮派委任（10次）的DKP为1";
        Matcher m = timeStamp.matcher(test);
        if(m.find()){
            System.out.println(m.group());
        }

        System.out.println(test.replace("/","-"));
    }
}
