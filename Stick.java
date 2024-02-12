package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Stick extends JFrame{

    private final JPanel mainPanel = new JPanel();
    private final JPanel northPanel = new JPanel();
    private final JPanel alignmentPanel = new JPanel(new FlowLayout());
    private JLabel label1;
    private JLabel label2;
    private final ButtonGroup group = new ButtonGroup();
    private final JButton jb = new JButton("make a move");
    private final JRadioButton radio1 = new JRadioButton("one stick");
    private final JRadioButton radio2 = new JRadioButton("two stick");
    private final JRadioButton radio3 = new JRadioButton("three stick");

    private volatile boolean select = false; // true - хід робота; false - хід гравця;
    private int allStick = 20;

    public int numbersOfStickUsedByRobot;
    public int move;

    private Stick(){
        super("palochka");
        createGUI();
        ActionOfStick2();
    }

    private void createGUI(){
        setDefaultCloseOperation( EXIT_ON_CLOSE );
        setResizable(false);

        mainPanel.setLayout(new BorderLayout());
        alignmentPanel.setBorder(BorderFactory.createTitledBorder("Number of sticks"));

        radio1.setVerticalAlignment(JLabel.CENTER);
        radio2.setVerticalAlignment(JLabel.CENTER);
        radio3.setVerticalAlignment(JLabel.CENTER);
        jb.setVerticalAlignment(JLabel.CENTER);


        radio1.setHorizontalAlignment(JLabel.CENTER);
        radio2.setHorizontalAlignment(JLabel.CENTER);
        radio3.setHorizontalAlignment(JLabel.CENTER);
        jb.setHorizontalAlignment(JLabel.CENTER);


        radio1.setPreferredSize(new Dimension(100, 100));
        radio2.setPreferredSize(new Dimension(100, 100));
        radio3.setPreferredSize(new Dimension(100, 100));
        jb.setPreferredSize(new Dimension(100, 100));


        jb.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));


        radio1.setForeground(Color.BLACK);
        radio2.setForeground(Color.BLACK);
        radio3.setForeground(Color.BLACK);
        jb.setForeground(Color.BLACK);


        alignmentPanel.add (radio1);
        alignmentPanel.add (radio2);
        alignmentPanel.add (radio3);
        alignmentPanel.add (jb);


        group.add(radio1);
        group.add(radio2);
        group.add(radio3);

        initLabel();

        mainPanel.add(alignmentPanel, BorderLayout.SOUTH);
        mainPanel.add(northPanel, BorderLayout.NORTH);
        mainPanel.add(label2, BorderLayout.CENTER);

        getContentPane().add(mainPanel);
        setPreferredSize(new Dimension(450, 485));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initLabel(){

        label1 = new JLabel(String.valueOf(allStick));
        label1.setVerticalAlignment(JLabel.CENTER);
        label1.setHorizontalAlignment(JLabel.CENTER);
        label1.setPreferredSize(new Dimension(50, 50));
        label1.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        label1.setFont(new Font(null, Font.ITALIC, 13));
        northPanel.add(label1);

        label2 = new JLabel("Start the game!");
        label2.setVerticalAlignment(JLabel.CENTER);
        label2.setHorizontalAlignment(JLabel.CENTER);
        label2.setPreferredSize(new Dimension(50, 50));
        label2.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
        label2.setFont(new Font(null, Font.ITALIC, 15));

    }


    private void ActionOfStick2() {
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (radio1.isSelected()) {
                    label1.setText(changAllStick(1));
                    robotLogic(1);
                    select = true;
                    label2.setText("Wait for the progress of the robot!");
                }
                if (radio2.isSelected()) {
                    label1.setText(changAllStick(2));
                    robotLogic(2);
                    select = true;
                    label2.setText("Wait for the progress of the robot!");
                }
                if (radio3.isSelected()) {
                    label1.setText(changAllStick(3));
                    robotLogic(3);
                    select = true; //разрешение роботу ходить
                    label2.setText("Wait for the progress of the robot!");
                }
                jb.setEnabled(false);
            }
        });
        robotMove();
    }
    public void robotLogic (int numberOfStickUsedByPlayer){
        move++;
        if (move == 1) {
            if (numberOfStickUsedByPlayer == 1 || numberOfStickUsedByPlayer == 2) {
                numbersOfStickUsedByRobot = 3 - numberOfStickUsedByPlayer;
            }else{ // lose situation
                Random random = new Random();
                numbersOfStickUsedByRobot = random.nextInt(1, 4);
            }
        } else {
            if((allStick - 1) % 4 == 0){ // lose situation
                Random random = new Random();
                numbersOfStickUsedByRobot = random.nextInt(1, 4);
            }else {
                if((allStick) % 4 == 0){
                    numbersOfStickUsedByRobot = 3;
                }else if((allStick + 1) % 4 == 0){
                    numbersOfStickUsedByRobot = 2;
                }else if((allStick + 2) % 4 == 0){
                    numbersOfStickUsedByRobot = 1;
                }
            }
        }
    }
    public void robotMove(){
        while (true){
            if(select){
                if(allStick != 1) {
                    tryToSleep();
                    label1.setText(changAllStick(numbersOfStickUsedByRobot));
                    if(allStick != 1) {
                        select = false;
                        jb.setEnabled(true);
                        label2.setText("The robot came: " + numbersOfStickUsedByRobot);
                    }else{
                        label2.setText("You lose!!");
                        // JOptionPane.showMessageDialog(null, "Tou lose !!", "Result", JOptionPane.PLAIN_MESSAGE);
                        break;
                    }
                }else{
                    label2.setText("You win!!");
                    // JOptionPane.showMessageDialog(null, "You win !!", "Result", JOptionPane.PLAIN_MESSAGE);
                    break;
                }
            }
        }
    }
    public void tryToSleep(){
        try {
            Thread.sleep(3000);
        }catch (Exception ignored){}
    }
    public String changAllStick(int numbOfSticks){
        allStick -= numbOfSticks;
        return String.valueOf(allStick);
    }
    public static void main(String[] args) {
        new Stick();
    }
}

