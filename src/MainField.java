// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

class MainField extends JPanel {

    private static final long serialVersionUID = 1L;
    private final JTable tableInitialData = new JTable(6, 7);
    private final JTable tableReceivedData = new JTable(6, 7);
    private final JLabel labelInitialData = new JLabel("Исходные данные");
    private final JLabel labelReceivedData = new JLabel("Полученные данные");
    private JLabel labelResidual = new JLabel("Невязка: ");
    private JLabel labelFullCost = new JLabel("Стоимость перевозки: ");
    private final JButton buttonAdd = new JButton("Добавить матрицу");
    private final JButton buttonSolution = new JButton("Получить решение");
    private final JButton buttonAbout = new JButton("О программе");
    private final JButton buttonDiagram = new JButton("Диаграмма");
    private final JButton buttonHelp = new JButton("Помощь");
    private final JButton buttonHowItWorks = new JButton("О работе метода");
    private final JButton buttonMethod = new JButton("О методе");

    private int[][] arrayInitialData = new int[6][7];
    private int[][] arrayValue = new int[6][7];
    private int[][] arrayGoods = new int[6][7];
    private boolean check = true;

    private void initialization() {

        setLayout(null);

        add(tableInitialData);
        add(tableReceivedData);
        add(labelInitialData);
        add(labelReceivedData);
        add(labelResidual);
        add(labelFullCost);

        add(buttonAdd);
        add(buttonSolution);
        add(buttonAbout);
        add(buttonDiagram);
        add(buttonHelp);
        add(buttonHowItWorks);
        add(buttonMethod);

        tableInitialData.setRowHeight(30);
        tableInitialData.setShowVerticalLines(true);
        tableInitialData.setShowHorizontalLines(true);

        tableReceivedData.setRowHeight(30);
        tableReceivedData.setShowVerticalLines(true);
        tableReceivedData.setShowHorizontalLines(true);

        tableInitialData.setBounds(20, 60, 450, 180);
        tableReceivedData.setBounds(500, 60, 450, 180);
        labelInitialData.setBounds(20, 30, 150, 20);
        labelReceivedData.setBounds(500, 30, 150, 20);
        labelResidual.setBounds(500, 260, 150, 20);
        labelFullCost.setBounds(500, 300, 200, 20);

        buttonAdd.setBounds(20, 260, 170, 30);
        buttonSolution.setBounds(20, 320, 170, 30);
        buttonAbout.setBounds(20, 380, 170, 30);
        buttonDiagram.setBounds(210, 380, 170, 30);
        buttonHelp.setBounds(400, 380, 170, 30);
        buttonHowItWorks.setBounds(590, 380, 170, 30);
        buttonMethod.setBounds(780, 380, 170, 30);

    }

    MainField() {

        initialization();
        listener();
    }

    private void buttonAdd() {

        arrayFill();
        subtract();
        arrayInitial();

    }

    private void buttonSolution() {

        execute();
    }

    private void subtract() {

        if (check) {
            subtractLowestElementRow();
            subtractLowestElementCol();
        }
    }

    private void execute() {
        calculate();
    }

    private void arrayFill() {

        int colSum = 0;
        int rowSum = 0;
        int value;

        arrayInitialData = new int[][]{{10, 15, 17, 9, 20, 30, 25}, {13, 27, 4, 24, 26, 26, 15}, {22, 26, 24, 30, 27, 29, 19},
                {25, 18, 12, 11, 24, 23, 16}, {10, 1, 20, 14, 17, 16, 17}, {10, 15, 24, 11, 19, 13, 0}};

        for (int i = 0; i < 6; i++) {
            System.arraycopy(arrayInitialData[i], 0, arrayValue[i], 0, 7);
        }

        for (int i = 0; i < 6; i++) {
            if (i == 5) {
                rowSum = arrayInitialData[i][6];
                rowSum += rowSum; //TODO: пофиксить
            }
            for (int j = 0; j < 7; j++) {
                value = arrayInitialData[i][j];
                tableInitialData.setValueAt(value, i, j);

                if (j == 6) {
                    colSum = arrayInitialData[5][j];
                    colSum += colSum;
                    System.out.println(colSum); //TODO: пофиксить
                }
            }
        }

        if (colSum != rowSum) {
            JOptionPane.showMessageDialog(null, "Суммы не совпадают", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            check = false;
        }
    }

    private void subtractLowestElementRow() {

        int[] minElement = new int[5];

        for (int i = 0; i < 5; i++) {
            minElement[i] = arrayValue[i][0];
            for (int j = 0; j < 6; j++) {
                if (arrayValue[i][j] <= minElement[i]) {
                    minElement[i] = arrayValue[i][j];
                }
            }
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                arrayValue[i][j] -= minElement[i];
            }
        }
    }

    private void subtractLowestElementCol() {

        int[] minElement = new int[6];

        for (int j = 0; j < 6; j++) {
            minElement[j] = arrayValue[0][j];
            for (int i = 0; i < 5; i++) {
                if (arrayValue[i][j] <= minElement[j]) {
                    minElement[j] = arrayValue[i][j];
                }
            }
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                arrayValue[i][j] -= minElement[j];
            }
        }
    }

    private void arrayInitial() {

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {

                arrayGoods[i][j] = 0;

                if ((i != 5) || (j != 6)) {
                    if (arrayValue[i][j] == 0) {
                        if (arrayValue[5][j] >= arrayValue[i][6]) {
                            arrayGoods[i][j] = arrayValue[i][6];
                            arrayValue[i][6] -= arrayGoods[i][j];
                            arrayValue[5][j] -= arrayGoods[i][j];
                        } else {
                            arrayGoods[i][j] = arrayValue[5][j];
                            arrayValue[i][6] -= arrayValue[5][j];
                            arrayValue[5][j] -= arrayGoods[i][j];
                        }
                    }
                }
                if ((i == 5) || (j == 6)) {
                    arrayGoods[i][j] = arrayValue[i][j];
                }
            }
        }
    }

    private void calculate() {

        boolean[] negElement = new boolean[6];
        int minElem = 0;

        for (int i = 0; i < 6; i++) {
            int sumGoods = 0;
            int minValue = arrayValue[i][1];
            for (int j = 0; j < 6; j++) {
                sumGoods += arrayGoods[i][j];

                if ((minValue > arrayValue[i][j]) && (arrayValue[i][j] != 0)) {

                    minValue = arrayValue[i][j];
                }
            }
            if (sumGoods < arrayValue[i][6]) {
                for (int j = 0; j < 6; j++) {
                    arrayValue[i][j] -= minValue;
                }
                break;
            }
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                if (arrayValue[i][j] < 0) {
                    negElement[j] = true;
                }
            }
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                if (arrayValue[i][j] < 0) {
                    minElem = arrayValue[i][j] * (-1);
                }
            }
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                if (negElement[j]) {
                    arrayValue[i][j] += minElem;
                }
            }
        }

        for (int i = 0; i < 6; i++) {
            int minValue = arrayValue[i][1];
            for (int j = 0; j < 6; j++) {

                if (minValue > arrayValue[i][j]) {
                    minValue = arrayValue[i][j];

                }
            }
            for (int j = 0; j < 6; j++) {
                arrayValue[i][j] -= minValue;
            }
        }

        for (int i = 0; i < 5; i++) {
            minElem = arrayValue[i][0];
            if (minElem == 0) {
                minElem = arrayValue[i][1];
            }
            if (minElem == 0) {
                minElem = arrayValue[i][2];
            }
            if (minElem == 0) {
                minElem = arrayValue[i][3];
            }
            if (minElem == 0) {
                minElem = arrayValue[i][4];
            }
            if (minElem == 0) {
                minElem = arrayValue[i][5];
            }
            if (arrayValue[i][6] != 0) {
                for (int j = 0; j < 6; j++) {
                    if (arrayValue[i][j] != 0) {
                        if (minElem > arrayValue[i][j]) {
                            minElem = arrayValue[i][j];
                        }
                    }
                }
                for (int j = 0; j < 6; j++) {
                    arrayValue[i][j] -= minElem;
                }
                break;
            }
        }

        for (int j = 0; j < 6; j++) {
            negElement[j] = false;
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                if (arrayValue[i][j] < 0) {
                    negElement[j] = true;
                }
            }
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                if (negElement[j]) {
                    arrayValue[i][j] += minElem;
                }
            }
        }

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                arrayValue[i][6] = arrayInitialData[i][6];
                arrayValue[5][j] = arrayInitialData[5][j];
            }
        }

        fillTable();
        arrayInitial();
    }

    private void fillTable() {

        int value;
        int goods;

        int sumRow = 0;
        int sumCol = 0;
        int sumColRow;
        int sumCost = 0;

        int fullCost = 0;

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if ((i < 5) && (j < 6)) {
                    goods = arrayGoods[i][j];
                    value = arrayInitialData[i][j];
                    tableReceivedData.setValueAt(String.valueOf(value) + ' ' + '[' + String.valueOf(goods) + ']', i, j);
                } else {
                    value = arrayInitialData[i][j];
                    tableReceivedData.setValueAt(String.valueOf(value), i, j);
                }
            }
        }

        for (int i = 0; i < 6; i++) {
            sumRow += arrayInitialData[i][6];
        }
        for (int j = 0; j < 7; j++) {
            sumCol += arrayInitialData[5][j];
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                if (arrayGoods[i][j] != 0) {
                    sumCost += arrayGoods[i][j];
                }
            }
        }
        sumColRow = sumCol + sumRow;
        int residual = sumColRow - sumCost * 2;

        labelResidual.setText("Невязка: " + residual);

        if (residual == 0) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 6; j++) {
                    fullCost += arrayGoods[i][j] * arrayInitialData[i][j];
                }
            }
            labelFullCost.setText("Стоимость перевозки: " + fullCost);
            JOptionPane.showMessageDialog(null, "Транспортная задача решена",
                    "Готово", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void buttonAbout() {

        JOptionPane.showMessageDialog(null, "Разработчик: Еремеев Евгений, группа БИСТ-211",
                "О программе", JOptionPane.INFORMATION_MESSAGE);
    }

    private void buttonDiagram() throws IOException {

        File file = new File("C:/Users/Aurelian/Учеба/Зайцева/MancresMethod/muncres.png");
        Desktop desktop = Desktop.getDesktop();
        if (file.exists()) desktop.open(file);
    }

    private void buttonHelp() {
        JOptionPane.showMessageDialog(null, "Для решения транспортной задачи методом Манкреса, заполните \n " +
                        "матрицу соответствующими значениями, после чего нажмите на кнопку Добавить матрицу. после чего нажмите \n" +
                        "на кнопку Получить решение. Когда суммарная невязка станет равна нулю, это будет значить, что оптитимальное решение \n" + "найдено.",
                "Помощь", JOptionPane.INFORMATION_MESSAGE);
    }

    private void buttonHowItWorks() {
        JOptionPane.showMessageDialog(null, "Строится начальный план перевозок, не удовлетворяющий в общем случае всем условиям задачи. \n" +
                        "Далее осуществляется переход к новому плану, более близкому к оптимальному. Последовательное применение \n" +
                        "этого приема за конечное число итераций приводит к решению задачи.\n" +
                        "Алгоритм венгерского метода состоит из подготовительного этапа и из конечного числа итераций.\n" +
                        " На подготовительном этапе строится матрица, элементы которой неотрицательны и удовлетворяют неравенствам\n" +
                        "Если эти условия являются равенствами, то матрица Хo - решение транспортной задачи.\n" +
                        " Если среди условий имеются неравенства, то осуществляется переход к первой итерации.\n" +
                        " На k-й итерации строится матрица .Близость этой матрицы к решению задачи характеризует число\n" +
                        " Dk — суммарная невязка матрицы Хk\n " +
                        "В результате первой итерации строится матрица Хl, состоящая из неотрицательных элементов.\n" +
                        "Если Dl = 0, то Хl - оптимальное решение задачи. Если Dl>=0, то переходят к следующей итерации.\n" +
                        " Они проводятся до тех пор, пока Dk при некотором k не станет равным нулю.\n" +
                        " Соответствующая матрица Хk является решением транспортной задачи. ",
                "О работе метода", JOptionPane.INFORMATION_MESSAGE);
    }

    private void buttonMethod() {
        JOptionPane.showMessageDialog(null, "Венгерский алгоритм — алгоритм оптимизации, решающий задачу о\n" +
                        " назначениях за полиномиальное время. Он был разработан и опубликован Гарольдом Куном в 1955 году.\n" +
                        "Автор дал ему имя «венгерский метод» в связи с тем, что алгоритм в значительной степени основан на более \n" +
                        " ранних работах двух венгерских математиков).\n" +
                        "Джеймс Манкрес в 1957 году заметил, что алгоритм является (строго) полиномиальным. \n " +
                        "С этого времени алгоритм известен также как алгоритм Куна — Манкреса или алгоритм Манкреса  \n" +
                        "решения задачи о назначениях. \n " +
                        "Временная сложность оригинального алгоритма была O(n^4), однако Эдмондс и Карп (а также \n" +
                        " Томидзава независимо от них) показали, что его можно модифицировать так,\n" +
                        " чтобы достичь времени выполнения O(n^3). \n",
                "О методе", JOptionPane.INFORMATION_MESSAGE);
    }

    private void listener() {

        final ActionListener addListener = e -> buttonAdd();
        buttonAdd.addActionListener(addListener);

        final ActionListener solutionListener = e -> buttonSolution();
        buttonSolution.addActionListener(solutionListener);

        ActionListener aboutListener = e -> buttonAbout();
        buttonAbout.addActionListener(aboutListener);

        ActionListener diagramListener = e -> {
            try {
                buttonDiagram();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        };
        buttonDiagram.addActionListener(diagramListener);

        ActionListener helpListener = e -> buttonHelp();
        buttonHelp.addActionListener(helpListener);

        ActionListener howItWorksListener = e -> buttonHowItWorks();
        buttonHowItWorks.addActionListener(howItWorksListener);

        ActionListener methodListener = e -> buttonMethod();
        buttonMethod.addActionListener(methodListener);
    }
}
