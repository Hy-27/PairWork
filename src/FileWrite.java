import java.io.*;
import java.util.ArrayList;

public class FileWrite {

    //生成并输出Exercises.txt、Answer.txt
    public void createProblemSet(int n,int r){

        FormulaCheck temporarySet = new FormulaCheck();
        ArrayList returnList = temporarySet.generate(n,r);
        ArrayList<String> exercisesList = new ArrayList<>();
        ArrayList<String> answerList = new ArrayList<>();

        for (int i = 0;i < 2*n;i++) {
            if(i < n) exercisesList.add(returnList.get(i).toString());
            else answerList.add(returnList.get(i).toString());
        }
        createExercisesFile(exercisesList);
        createAnserFile(answerList);
    }

    //生成并输出Exercises.txt
    public static void createExercisesFile(ArrayList txtList){
        try{
            File exercisesTxt = new File("Exercises.txt");

            //若文件存在，删除文件
            if (exercisesTxt.exists()) {
                exercisesTxt.delete();
            }
            if(exercisesTxt.createNewFile()){
                System.out.println("\n————生成Exercises.txt中————");
                FileOutputStream txtFile = new FileOutputStream(exercisesTxt);
                PrintStream q = new PrintStream(exercisesTxt);

                for(int i = 0;i < txtList.size();i++){
                    q.println(i+1 + ". " +txtList.get(i));
                }
                txtFile.close();
                q.close();
                System.out.println("\n****** Exercises.txt 生成成功! ******");

            }
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    //生成并输出Answer.txt
    public static void createAnserFile(ArrayList ansList){
        try{

            File answerTxt = new File("Answer.txt");

            //若文件存在，删除文件
            if (answerTxt.exists()) {
                answerTxt.delete();
            }

            if(answerTxt.createNewFile()){
                System.out.println("\n————生成Answer.txt中————");
                FileOutputStream ansFile = new FileOutputStream(answerTxt);
                PrintStream p = new PrintStream(answerTxt);
                for(int i = 0;i < ansList.size();i++){
                    p.println(i+1 + ". " +ansList.get(i));
                }
                ansFile.close();
                p.close();
                System.out.println("\n****** Answer.txt 生成成功! ******");
            }
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    // 生成并输出Grade.txt
    public void createGradeFile(String submitPath, String answersPath) {
        try {

            ArrayList<String> submitList = obtainAnswer(submitPath);
            ArrayList<String> answersList = obtainAnswer(answersPath);
            ArrayList<String> WQuesNum = new ArrayList<>();
            ArrayList<String> TQuesNum = new ArrayList<>();

            for (int i = 0; i < submitList.size(); i++) {
                if (submitList.get(i).equals(answersList.get(i)))
                    WQuesNum.add(String.valueOf(i+1));
                else
                    TQuesNum.add(String.valueOf(i+1));
            }

            File gradeTxt = new File("Grade.txt");

            //若文件存在，删除文件
            if (gradeTxt.exists()) {
                gradeTxt.delete();
            }

            if (gradeTxt.createNewFile()) {
                System.out.print("创建Grade.txt:");
                FileOutputStream gradeFile = new FileOutputStream(gradeTxt);
                PrintStream p = new PrintStream(gradeTxt);

                p.print("Correct:");
                output(p, TQuesNum);
                p.print("Wrong:");
                output(p, WQuesNum);

                gradeFile.close();
                p.close();
                System.out.println("Grade.txt 创建成功！");
            }
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    //输出答案
    private void output(PrintStream p,ArrayList quesNum) {
        p.print(quesNum.size() +"(");
        for(int i = 0;i < quesNum.size();i++){
            System.out.print(">");
            if (i < quesNum.size()-1)
                p.print(" " + quesNum.get(i) + "，");
            else
                p.print(" " + quesNum.get(i));
        }
        p.println(" )\n");
    }

    //获取文件答案
    private ArrayList<String> obtainAnswer(String path) throws IOException {
        ArrayList<String> answerList = new ArrayList<>();
        BufferedReader answerFile = new BufferedReader(new FileReader(path));
        String answerLine = null;

        while ((answerLine = answerFile.readLine()) != null) {
            answerLine = answerLine.replace(" ", "");
            if (answerLine.indexOf('.') >= 0) {
                if (answerLine.length() > 2)
                    answerList.add(answerLine);
            }
        }
        return answerList;
    }
}
