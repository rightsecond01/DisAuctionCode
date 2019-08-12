package com.ch2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MenuAndFileDialogEx extends JFrame{
JLabel la = new JLabel();
    
    MenuAndFileDialogEx(){
        this.setTitle("Menu와 JFileChooser 활용 예제");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(la);
        createMenu();
        this.setLocationRelativeTo(null);
        this.setSize(300, 200);
        this.setVisible(true);
    }
    void createMenu(){
        JMenuBar mb = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem openItem = new JMenuItem("Open");
        
        openItem.addActionListener(new OpenActionListener());
        fileMenu.add(openItem);
        mb.add(fileMenu);
        this.setJMenuBar(mb);
    }
    
    class OpenActionListener implements ActionListener{
        JFileChooser chooser;
        OpenActionListener(){
            chooser=new JFileChooser();
        }
        @Override
        public void actionPerformed(ActionEvent ae) {
            FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG&GIF Images","jpg","gif","png");
            chooser.setFileFilter(filter);
            
            int ret=chooser.showOpenDialog(null);
            if(ret!=JFileChooser.APPROVE_OPTION){
                JOptionPane.showMessageDialog(null, "파일을 선택하지 않았습니다.","경고",JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            String filePath=chooser.getSelectedFile().getPath();
            la.setIcon(new ImageIcon(filePath));
            pack();
        }
        
    }

    public static void main(String[] args) {
        new MenuAndFileDialogEx();
    }


}
