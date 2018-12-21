public class Test {
    public long a ;
    public long b ;
    Test(long x , long y){
        a = x;
        b = y;
    }


    static void staticMedthod(){
        for(int i = 0 ; i!= 100000000 ; i++){
            //System.out.println("静态"+ i);
            if ( i == -1){
                break;
            }
        }
    }

    void medthod(){
        for(int i = 0 ; i!= 100000000 ; i++){
            //System.out.println("成员"+ i);
            if ( i == -1){
                break;
            }
        }
    }

    public static void main(String args[]){

        Test mt = new Test(1,1);
        System.out.print("静态方法 和 对象方法 调用对比:\n");

        long time = System.currentTimeMillis();
        for (long i = 0 ; i != 100000000 ; i ++){
            Test.staticMedthod();
            //mt.staticMedthod();
        }
        System.out.print("静态方法调用 : "+(System.currentTimeMillis() - time) + "ms\n");



        time = System.currentTimeMillis();
        for (long j = 0 ; j != 100000000; j ++){
            mt.medthod();
        }
        System.out.print("对象方法调用 : "+(System.currentTimeMillis() - time) + "ms\n");



    }

}
