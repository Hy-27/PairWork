import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

            int n = 0;
            int r = 0;
            boolean is = true;
            String submitPath = null;
            String answersPath = null;

            try {
                // ��ȡ����ָ��
                System.out.println("������ָ�");
                System.out.println("ָ���ʽ��-n ������Ŀ���� -r ��ֵ��Χ-e Exercises�ļ�·�� -a Answer�ļ�·��");
                Scanner command = new Scanner(System.in);
                String arr[] = command.nextLine().split("\\s");

                if (arr.length > 1) {
                    for (int i = 0; i < arr.length; i = i + 2) {
                        switch (arr[i]) {
                            case "-n":
                                n = Integer.parseInt(arr[i + 1]);
                                if (n  <= 0) {
                                    System.out.println("-n����������������0������");
                                    return;
                                }
                                break;
                            case "-r":
                                r = Integer.parseInt(arr[i + 1]);
                                if (r <= 1) {
                                    System.out.println("-r����������������1������");
                                    return;
                                }
                                break;
                            case "-e":
                                submitPath = arr[i + 1];
                                if (submitPath == null) {
                                    System.out.println("��������Ҳ�����Ӧ���ļ�·��");
                                    return;
                                }
                                break;
                            case "-a":
                                answersPath = arr[i + 1];
                                if (answersPath == null) {
                                    System.out.println("��������Ҳ�����Ӧ���ļ�·��");
                                    return;
                                }
                                break;
                            default:
                                System.out.println("ָ�����");
                                is = false;
                                break;
                        }
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("�����ָ���������������");
                is = false;
                System.out.println("ʾ����n: " + "����Ȼ����" + " r: " + "����Ȼ����");
            }
            if(is) {
                FileWrite makefile = new FileWrite();
                System.out.println("������Ŀ����: " + n + ", ��ֵ��Χ: " + (r-1));
                if (submitPath != null && answersPath != null) {
                    makefile.createProblemSet(n,r);
                    makefile.createGradeFile(submitPath,answersPath);
                }
                else
                    makefile.createProblemSet(n,r);
            }
        }
}

