import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

            int n = 0;
            int r = 0;
            boolean is = true;
            String submitPath = null;
            String answersPath = null;

            try {
                // 获取输入指令
                System.out.println("请输入指令：");
                System.out.println("指令格式：-n 生成题目个数 -r 数值范围-e Exercises文件路径 -a Answer文件路径");
                Scanner command = new Scanner(System.in);
                String arr[] = command.nextLine().split("\\s");

                if (arr.length > 1) {
                    for (int i = 0; i < arr.length; i = i + 2) {
                        switch (arr[i]) {
                            case "-n":
                                n = Integer.parseInt(arr[i + 1]);
                                if (n  <= 0) {
                                    System.out.println("-n输入错误，请输入大于0的数字");
                                    return;
                                }
                                break;
                            case "-r":
                                r = Integer.parseInt(arr[i + 1]);
                                if (r <= 1) {
                                    System.out.println("-r输入错误，请输入大于1的数字");
                                    return;
                                }
                                break;
                            case "-e":
                                submitPath = arr[i + 1];
                                if (submitPath == null) {
                                    System.out.println("输入错误，找不到相应的文件路径");
                                    return;
                                }
                                break;
                            case "-a":
                                answersPath = arr[i + 1];
                                if (answersPath == null) {
                                    System.out.println("输入错误，找不到相应的文件路径");
                                    return;
                                }
                                break;
                            default:
                                System.out.println("指令错误！");
                                is = false;
                                break;
                        }
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("输入的指令错误，请重新输入");
                is = false;
                System.out.println("示例：n: " + "（自然数）" + " r: " + "（自然数）");
            }
            if(is) {
                FileWrite makefile = new FileWrite();
                System.out.println("生成题目个数: " + n + ", 数值范围: " + (r-1));
                if (submitPath != null && answersPath != null) {
                    makefile.createProblemSet(n,r);
                    makefile.createGradeFile(submitPath,answersPath);
                }
                else
                    makefile.createProblemSet(n,r);
            }
        }
}

