import java.util.HashMap;
import java.util.Stack;

public class ResultsCalculate {

    public String[] checkout(String formula, int length) {
        Stack<String> stackN = new Stack<>();//������
        Stack<String> stackO = new Stack<>();//������
        String[] RPN = new String[length];//�沨�����ʽ

        // ��ϣ������������ȼ�
        HashMap<String, Integer> hashmap = new HashMap<>();
        hashmap.put("(", 0);
        hashmap.put("��", 1);
        hashmap.put("��", 1);
        hashmap.put("��", 2);
        hashmap.put("��", 2);

        for (int i = 0, j = 0; i < formula.length(); ) {
            StringBuilder num = new StringBuilder();
            //�и�ʽ��
            char c = formula.charAt(i);
            //��cΪ���֣�����num
            while (Character.isDigit(c) || c == '/' || c == '\'') {
                num.append(c);
                i++;
                c = formula.charAt(i);
            }
            //num�������֣��������
            if (num.length() == 0) {
                switch (c) {
                    //����ǡ�(��ת��Ϊ�ַ���ѹ���ַ�ջ
                    case '(': {
                        stackO.push(String.valueOf(c));
                        break;
                    }
                    //������)���ˣ����м���
                    case ')': {
                        String operator = stackO.pop();
                        //����ջ���з���ʱ��ȡ����������
                        while (!stackO.isEmpty() && !operator.equals("(")) {
                            String a = stackN.pop();
                            String b = stackN.pop();
                            //��׺���ʽ����
                            RPN[j++] = a;
                            RPN[j++] = b;
                            RPN[j++] = operator;
                            String ansString = calculate(b, a, operator);
                            if (ansString == null)
                                return null;
                            //�����ѹ��ջ
                            stackN.push(ansString);
                            //����ָ����һ���������
                            operator = stackO.pop();
                        }
                        break;
                    }
                    //�����ˡ�=�����������ս��
                    case '=': {
                        String operator;
                        while (!stackO.isEmpty()) {
                            operator = stackO.pop();
                            String a = stackN.pop();
                            String b = stackN.pop();
                            //��׺���ʽ����
                            RPN[j++] = a;
                            RPN[j++] = b;
                            RPN[j++] = operator;
                            String ansString = calculate(b, a, operator);
                            if (ansString == null)
                                return null;
                            stackN.push(ansString);
                        }
                        break;
                    }
                    //����
                    default: {
                        String operator;
                        while (!stackO.isEmpty()) {
                            operator = stackO.pop();
                            if (hashmap.get(operator) >= hashmap.get(String.valueOf(c))) { //�Ƚ����ȼ�
                                String a = stackN.pop();
                                String b = stackN.pop();
                                //��׺���ʽ����
                                RPN[j++] = a;
                                RPN[j++] = b;
                                RPN[j++] = operator;
                                String ansString = calculate(b, a, operator);
                                if (ansString == null)
                                    return null;
                                stackN.push(ansString);
                            } else {
                                stackO.push(operator);
                                break;
                            }

                        }
                        stackO.push(String.valueOf(c));  //������ѹ�����ջ
                        break;
                    }
                }
            }
            //�������֣�ֱ��ѹջ
            else {
                stackN.push(num.toString());
                continue;
            }
            i++;
        }
        //ջ������Ϊ��
        RPN[length - 3] = "=";
        RPN[length - 2] = stackN.peek();
        RPN[length - 1] = formula;
        return RPN;
    }


    //����ʽ��
    private String calculate(String m, String n, String operator) {
        String ansFormula = null;
        char op = operator.charAt(0);
        int[] indexFraction = {m.indexOf('\''), m.indexOf('/'), n.indexOf('\''), n.indexOf('/')};//���� ������ �и�λ��

        //�����������
        if (indexFraction[1] > 0 || indexFraction[3] > 0) {
            int[] denominator = new int[3];
            int[] molecule = new int[3];
            int[] integralPart = new int[3];

            //�и����
            if (indexFraction[1] > 0) {
                for (int i = 0; i < m.length(); i++) {
                    if (i < indexFraction[0]) {
                        integralPart[0] = Integer.parseInt(integralPart[0] + String.valueOf(m.charAt(i) - '0'));
                    } else if (i > indexFraction[0] && i < indexFraction[1]) {
                        molecule[0] = Integer.parseInt(molecule[0] + String.valueOf(m.charAt(i) - '0'));
                    } else if (i > indexFraction[1]) {
                        denominator[0] = Integer.parseInt(denominator[0] + String.valueOf(m.charAt(i) - '0'));
                    }
                }
            } else {
                integralPart[0] = Integer.parseInt(m);
                denominator[0] = 1;
                molecule[0] = 0;
            }

            if (indexFraction[3] > 0) {
                for (int i = 0; i < n.length(); i++) {
                    if (i < indexFraction[2]) {
                        integralPart[1] = Integer.parseInt(integralPart[1] + String.valueOf(n.charAt(i) - '0'));
                    } else if (i > indexFraction[2] && i < indexFraction[3]) {
                        molecule[1] = Integer.parseInt(molecule[1] + String.valueOf(n.charAt(i) - '0'));
                    } else if (i > indexFraction[3]) {
                        denominator[1] = denominator[1] + n.charAt(i) - '0';
                    }
                }
            } else {
                integralPart[1] = Integer.parseInt(n);
                denominator[1] = 1;
                molecule[1] = 0;
            }

            //��������
            switch (op) {
                case '��': {
                    denominator[2] = denominator[0] * denominator[1];
                    molecule[2] = integralPart[0] * denominator[2] + molecule[0] * denominator[1] + integralPart[1] * denominator[2] + molecule[1] * denominator[0];
                    break;
                }
                case '��': {
                    denominator[2] = denominator[0] * denominator[1];
                    molecule[2] = integralPart[0] * denominator[2] + molecule[0] * denominator[1] - integralPart[1] * denominator[2] - molecule[1] * denominator[0];
                    break;
                }
                default:
                    return null;
            }

            //��ȡ��������
            if (molecule[2] >= denominator[2] && molecule[2] > 0) {
                integralPart[2] = molecule[2] / denominator[2];
                molecule[2] = Math.abs(molecule[2] % denominator[2]);
            } else if (molecule[2] < 0) {
                return null;
            }

            //�������
            if (molecule[2] != 0) {
                ansFormula = greatFraction(integralPart[2], molecule[2], denominator[2]);
            } else ansFormula = String.valueOf(integralPart[2]);

        } else { //������������
            int a = Integer.parseInt(m);
            int b = Integer.parseInt(n);

            switch (op) {
                case '��': {
                    ansFormula = String.valueOf(a + b);
                    break;
                }
                case '��': {
                    if (a - b >= 0)
                        ansFormula = String.valueOf(a - b);
                    else
                        return null;
                    break;
                }
                case '��': {
                    ansFormula = String.valueOf(a * b);
                    break;
                }
                case '��': {
                    if (b == 0) {
                        return null;
                    } else if (a % b != 0) {
                        ansFormula = a % b + "/" + b;
                        if (a / b > 0) ansFormula = a / b + "'" + ansFormula;
                    } else
                        ansFormula = String.valueOf(a / b);
                    break;
                }
            }
        }
        return ansFormula;
    }

    //�������
    private String greatFraction(int integralPart, int molecule, int denominator) {
        String ansFormula;
        int commonFactor = 1;

        //�����Լ��
        FormulaCreate create = new FormulaCreate();
        commonFactor = create.commonFactor(denominator, molecule);

        //�������
        denominator /= commonFactor;
        molecule /= commonFactor;

        //������
        if (integralPart == 0 && molecule > 0) {
            ansFormula = String.valueOf(molecule) + '/' + String.valueOf(denominator);
        } else if (molecule == 0)
            ansFormula = String.valueOf(integralPart);
        else {
            ansFormula = String.valueOf(integralPart) + "'" + String.valueOf(molecule) + '/' + String.valueOf(denominator);
        }
        return ansFormula;
    }
}
