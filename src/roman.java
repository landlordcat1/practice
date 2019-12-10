import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class roman
{
    public static void main(String[] arg){
        String a=new String();
        Scanner in=new Scanner(System.in);
        long n=0;
        a=in.next();
            if(a.equals("IV"))
            {
                n=4;
            }
            else if(a.equals("IX"))
            {
              n=9;
            }
            else if(a.equals("XL"))
            {
               n=40;
            }
            else if (a.equals("XC"))
            {
               n=90;
            }
            else if (a.equals("CD"))
            {
              n=400;
            }
            else if(a.equals("CM"))
            {
              n=900;
            }
            else {
                for(int j=0;j<a.length();j++) {

                    if (a.charAt(j) == 'I') {
                        if (a.charAt(j + 1) == 'V') {
                            n += 4;
                            ++j;
                        } else if (a.charAt(j + 1) == 'X') {
                            n += 9;
                            ++j;

                        } else {
                            n++;
                        }
                    } else if (a.charAt(j) == 'V') {
                        n += 5;
                    } else if (a.charAt(j) == 'X') {
                        if (a.charAt(j + 1) == 'L') {
                            n += 40;
                            ++j;
                        } else if (a.charAt(j + 1) == 'C') {
                            n += 90;
                            ++j;
                        } else {
                            n+=10;
                        }
                    }
                  else if (a.charAt(j)=='L')
                  {
                      n+=50;
                  }
                  else if (a.charAt(j)=='C')
                  {
                      if(a.charAt(j+1)=='D')
                      {
                          n+=400;
                          ++j;
                      }
                      else if(a.charAt(j+1)=='M')
                      {
                          n+=900;
                          ++j;

                      }
                      else {
                          n+=100;
                      }
                  }
                  else if(a.charAt(j)=='D')
                  {
                      n+=500;
                  }
                  else
                  {
                      n+=1000;
                  }
                }
            }
        System.out.println(n);
        }
}
