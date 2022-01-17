package com.nokhrin.gui;


import com.nokhrin.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

@Component
public class GWUserForm extends JFrame {


    WordService wordService;
    public boolean isQuit=false;
    private JLabel wordLabel = new JLabel("word");
    private JLabel nameLabel = new JLabel("name");
    private JLabel scoreLabel = new JLabel("score");
    private JLabel messageLabel = new JLabel("message");
    private JTextArea textArea = new JTextArea();
    private JPanel panel;
    private JButton closeButton;
    public GWUserForm(WordService wordService){
        super( "dasdas");

        this.wordService=wordService;
        this.setBounds(150,200,850,600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void init(String username){

        closeButton = new JButton("Close");
        closeButton.setBounds(730,10,90,40);
    //    closeButton.setEnabled(true);
         closeButton.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                       // System.out.println("quit");
                        isQuit=true;


                    }
                }
        );
    
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.decode("#FFC573"));
        wordLabel=createLabel(350,65,5200,100,32);
        nameLabel=createLabel(20,20,1000,100,30);
        scoreLabel=createLabel(20,60,1000,100,20);
        messageLabel=createLabel(220,10,400,40,24);
        messageLabel.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.BLACK));
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(nameLabel);
        panel.add(scoreLabel);
        panel.add(wordLabel);
        panel.add(messageLabel);
        panel.add(closeButton);
        add(panel);
        textArea=createTextArea(170,140,500,200,30);
        panel.add(textArea);
        setButtons(username,'A','M',20,400);
        setButtons(username,'N','Z',20,470);
        panel.updateUI();

    }

    private void setButtons(String userName, char firstLetter, char lastLetter,  int startX, int y){
        for(char i=firstLetter; i<=lastLetter; i++){
            panel.add(createButton(i,startX+((int)i-(int)firstLetter)*60,y, userName));
        }
    }
    private JTextArea createTextArea(int x,int y,int wight,int height, int size) {
    JTextArea jTextArea = new JTextArea();
        jTextArea.setBounds(x,y,wight,height);
        jTextArea.setFont(new Font("Serif", Font.ITALIC, size));
        jTextArea.setLineWrap(true);
        jTextArea.setEditable(false);
        jTextArea.setWrapStyleWord(true);
        return jTextArea;
    }
    private int countXForWord(int xDesc,int wight,int wordSize){

        return (xDesc+wight/2)-10*wordSize;
    }
    private JLabel createLabel(int x,int y,int wight,int height, int size){
        JLabel label = new JLabel();
        label.setBounds(x,y,wight,height);
        label.setFont(new Font("Serif", Font.BOLD, size));
        return label;
    }
    private JButton createButton(char s,int x, int y, String userName){
        JButton button = new JButton(String.valueOf(s));
        button.setBounds(x,y,50,50);
        button.addActionListener(new ButtonEventListener(s,wordService,userName));
        return button;
    }

    public String selectUsername(){
        String username = (String) JOptionPane.showInputDialog(this,"Set username","",
                JOptionPane.PLAIN_MESSAGE,null,null,"");
        return username;
    }

    public void checkButtons(List<Character> letters){
        for (java.awt.Component component : panel.getComponents()
             ) {
                if(component  instanceof JButton){
                    if(!letters.contains(((JButton) component).getText())&&!((JButton) component).getText().equals("Close")){
                        component.setEnabled(false);
                    }else {
                        component.setEnabled(true);
                    }
                }
        }
    }

    public void setDescription(String description){
        textArea.setText(description);
    }
    public void setWord(String word){
        wordLabel.setText(word);
        wordLabel.setBounds(countXForWord(170,500,word.length()),wordLabel.getY(),wordLabel.getWidth(),wordLabel.getHeight());
    }
    public void setUserName(String name){
        nameLabel.setText(name);
    }
    public void setScoreLabel(int score){
          scoreLabel.setText(String.valueOf("Your score: " + score));
    }
    public void setMessage(String word){
        messageLabel.setText(word);
    }
    public void showMessage(String text){
        JOptionPane.showMessageDialog(null, text);
    }
}

class ButtonEventListener implements ActionListener{

    WordService wordService;
    private char cc;
    private String username;
    public ButtonEventListener(char c, WordService wordService, String username){
        cc=c;
        this.wordService=wordService;
        this.username=username;
    }
    public void actionPerformed(ActionEvent e){
        if(wordService.isInGame(username)) {
            System.out.println(cc);
            wordService.getAnswer(cc);
        }
        else {
           JOptionPane.showMessageDialog(null, "Its not your turn");
        }
    }
}