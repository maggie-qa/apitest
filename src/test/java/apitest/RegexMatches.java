package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatches
{
    public static void main( String args[] ){
 
      // 按指定模式在字符串查找
      String line = "This order was placed for QT3000! OK?";
      //规则1
      String pattern = "(\\D*)(\\d+)(.*)";
      
      //规则2
      // String pattern = "(\\d+)(.*)";
 
      // 创建 Pattern 对象
      Pattern r = Pattern.compile(pattern);
 
      // 现在创建 matcher 对象
      Matcher m = r.matcher(line);
      if (m.find( )) {
         System.out.println("匹配到所有数据: " + m.group() );
         System.out.println("第一个分组: " + m.group(1) );
         System.out.println("第二个分组: " + m.group(2) );
         System.out.println("第三个分组: " + m.group(3) ); 
      } else {
         System.out.println("NO MATCH");
      }
   }
}
