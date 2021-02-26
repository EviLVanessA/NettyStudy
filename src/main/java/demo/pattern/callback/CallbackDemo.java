package demo.pattern.callback;

/**
 * @author jianghui
 * @date 2021-02-24 16:46
 */
public class CallbackDemo {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            System.out.println("休息啦");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("回调拉");
        });
        thread.start();
    }

}
