import java.util.Random;

public class FormulaCreate {

        // ������ʽ
        public String[] createFormula(int r){
            Random random = new Random();
            String[] operator = {"��","��","��","��","��"};

            String[] totalO = new String[1 + random.nextInt(3)];//�����
            String[] totalF = new String[totalO.length+1];//������
            String formula = new String();

            //�ж��Ƿ���ڷ���
            Boolean existFraction = false;

            //���������������
            for (int i = 0;i < totalF.length;i++) {

                // ���ȷ���������������
                int fractionOrNot = random.nextInt(2);
                if (fractionOrNot == 0) { //��������
                    int integralPart = random.nextInt(r+1);
                    totalF[i] = String.valueOf(integralPart);
                } else { //���ɷ���

                    int denominator = 1+random.nextInt(r);
                    int molecule = random.nextInt(denominator);
                    int integralPart = random.nextInt(r+1);

                    //�������
                    if (molecule != 0) {
                        int commonFactor = commonFactor(denominator, molecule);
                        denominator /= commonFactor;
                        molecule /= commonFactor;
                    }

                    //���������
                    if (integralPart == 0 && molecule > 0) {
                        totalF[i] = molecule + "/" + denominator;
                        existFraction = true;
                    }
                    else if (molecule == 0)
                        totalF[i] = String.valueOf(integralPart);
                    else {
                        totalF[i] = integralPart + "'" + molecule + "/" + denominator;
                        existFraction = true;
                    }
                }
            }

            //��������������
            for (int i = 0;i < totalO.length;i++) {
                if (existFraction)
                    totalO[i] = operator[random.nextInt(2)];
                else
                    totalO[i] = operator[random.nextInt(4)];
            }

            int choose = totalF.length;
            if (totalF.length != 2 )
                choose = random.nextInt(totalF.length);

            //����ʽ��
            for (int i = 0;i < totalF.length;i++) {
                if (i == choose && choose < totalO.length) {
                    formula = formula + "(" + totalF[i] + totalO[i] ;
                } else if (i == totalF.length - 1 && i == choose+1 && choose<totalO.length) {
                    formula = formula + totalF[i] + ")" + "=";
                } else if (i == choose + 1 && choose < totalO.length) {
                    formula = formula + totalF[i] + ")" + totalO[i];
                } else if (i == totalF.length - 1) {
                    formula = formula + totalF[i] + "=";
                } else {
                    formula = formula + totalF[i] + totalO[i];
                }
            }

            ResultsCalculate checkAns = new ResultsCalculate();
            String[] ansFormula = checkAns.checkout(formula,3*totalO.length+2+1);

            if (ansFormula!=null)
                return ansFormula;
            return null;
        }

        //���������
        public int commonFactor(int a,int b) {
            while(true)
            {
                if(a%b == 0)return b;
                int temp = b;
                b = a%b;
                a = temp;
            }
        }
}

