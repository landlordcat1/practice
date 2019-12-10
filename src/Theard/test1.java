package Theard;

public class test1
{
    public static class Num{
        int i=1;
        boolean flag=false;
    }
    public static class PrintJi implements Runnable {
        Num num;

        public PrintJi(Num num) {
            this.num = num;
        }

        @Override
        public void run() {
            while (num.i <= 100) {
                synchronized (num) {
                    if (num.flag) {
                        try {
                            num.wait();
                        } catch (Exception e) {

                        }
                    } else {
                        System.out.println("奇数" + num.i);
                        num.i++;
                        num.flag = true;
                        num.notify();
                    }
                }
            }
        }
    }
    public static class PrintOu implements Runnable{
        Num num;
        public PrintOu(Num num) {
            this.num = num;
        }
        public void run()
        {
            while(num.i<=100)
            {
                synchronized (num)/* 必须要用一把锁对象，这个对象是num*/ {
                    if(!num.flag)
                    {
                        try
                        {
                            num.wait();  //操作wait()函数的必须和锁是同一个
                        } catch (Exception e)
                        {}
                    }
                    else {
                        System.out.println("偶数"+num.i);
                        num.i++;
                        num.flag = false;
                        num.notify();
                    }
                }
            }
        }
    }
    public static void main(String[] arg){
           Num num=new Num();
           PrintJi p1=new PrintJi(num);
           PrintOu p2=new PrintOu(num);
           Thread a=new Thread(p1);
           Thread b=new Thread(p2);
           a.start();
           b.start();

    }
}
