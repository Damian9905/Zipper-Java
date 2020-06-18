package Zipper;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;

public class FileZipper extends JFrame
{
    //variables and components
    private JButton bAdd;
    private JButton bRemove;
    private JButton bZip;
    private JMenuBar topMenu = new JMenuBar();
    private DefaultListModel listModel = new DefaultListModel()
   {
       ArrayList arrayList = new ArrayList();
       
       @Override
       public void addElement(Object obj) 
       {
           arrayList.add(obj);
           super.addElement(obj);
       }
        @Override
        public Object get(int index) 
        {
            return arrayList.get(index);
        }
        @Override
        public Object remove(int index)
        {
            arrayList.remove(index);
            return super.remove(index);
        }
       
    };
    private JList list = new JList(listModel);
    private JFileChooser chooser = new JFileChooser();
    
    
    //constructors
    public FileZipper()
    {
        this.initComponents();
    }
    
    //methods
    public void initComponents()
    {
        //setting components 
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.setBounds(screenWidth/2 - 200, screenHeight/2 - 150, 400, 250);
        this.setTitle("Zipper.java");
        
            //adding components
        //actions to put in diff buttons or menu
        Action adding = new MyAction("Add", "Add to zipper", "ctrl A" /*new ImageIcon("add.png")*/);
        Action removing = new MyAction("Remove", "Remove from zipper", "ctrl R");
        Action zipping = new MyAction("Zip", "Zip!", "ctrl Z");
        Action about = new MyAction("See About");
        //buttons
        bAdd = new JButton(adding);
        bRemove = new JButton(removing);
        bZip= new JButton(zipping);
        JScrollPane scroll = new JScrollPane(list);
        
        list.setBorder(BorderFactory.createEtchedBorder());
        
        //topMenu
        this.setJMenuBar(topMenu);
        JMenu fileMenu = topMenu.add(new JMenu("File"));
            JMenuItem addMenuItem = fileMenu.add(adding);
            JMenuItem removeMenuItem = fileMenu.add(removing);
            JMenuItem zipMenuItem = fileMenu.add(zipping);
        JMenu aboutMenu = topMenu.add(new JMenu("About"));
            JMenuItem seeAboutItem = aboutMenu.add(about);
        
            
        
        //layout
        GroupLayout myLayout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(myLayout);
        myLayout.setAutoCreateContainerGaps(true);
        myLayout.setAutoCreateGaps(true);
        
        myLayout.setHorizontalGroup(
                myLayout.createSequentialGroup()
                .addComponent(scroll, 100, 150, Short.MAX_VALUE)
                .addGroup(
                        myLayout.createParallelGroup()
                        .addComponent(bAdd)
                        .addComponent(bRemove)
                        .addComponent(bZip)
                )
        );
        myLayout.setVerticalGroup(
                myLayout.createParallelGroup()
                .addComponent(scroll, 100, 150, Short.MAX_VALUE)
                .addGroup(
                        myLayout.createSequentialGroup()
                        .addComponent(bAdd)
                        .addComponent(bRemove)
                        .addGap(20, 50,  Short.MAX_VALUE)
                        .addComponent(bZip)
                )
        );
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.pack();
    }
    private class MyAction extends AbstractAction
    {
        public MyAction(String name, String opis, String shortcut)
        {
            this.putValue(Action.NAME, name);
            this.putValue(Action.SHORT_DESCRIPTION, opis);
            this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(shortcut));
        }
        
         public MyAction(String name, String opis, String shortcut, Icon ico)
        {
            this(name,opis,shortcut);
            //z dodaniem ikony
            this.putValue(Action.SMALL_ICON, ico);
        }
        public MyAction(String nazwa)
        {
            this.putValue(Action.NAME, nazwa);
        }


        @Override
        public void actionPerformed(ActionEvent e) 
        {
            switch (e.getActionCommand()) {
                case "Add":
                    addToZip();
                    break;
                case "Remove":
                    removeFromZip();
                    break;
                case "Zip":
                    System.out.println("sd");//zipping();
                    break;
                case "See About":
                    getDesc();
                default:
                    break;
            }
        }
        private void addToZip()
        {
            chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            chooser.setMultiSelectionEnabled(true);
            chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            int choose = chooser.showDialog(rootPane, "Add to zip");
            if(choose == JFileChooser.APPROVE_OPTION)
            {
                File[] choosedFiles = chooser.getSelectedFiles();
                for (File file : choosedFiles) {
                    if (!isElementAlreadyInZip(file.getPath())) {
                        listModel.addElement(file.getPath());
                    }
                }
            }
            
        }
        private boolean isElementAlreadyInZip(String element)
        {
            for(int i = 0; i < listModel.getSize(); i++)
            {
                if((listModel.get(i)).equals(element))
                    return true;
            }
            return false;
        }
        
        private void removeFromZip()
        {
            int[] indexy = list.getSelectedIndices();
                for(int i=0; i < indexy.length; i++)
                    listModel.remove(indexy[i]-i);
        }
        private void getDesc()
        {
            JDialog aboutDialog = new JDialog();
            aboutDialog.setVisible(true);
            aboutDialog.setTitle("About");
            JPanel dialogPanel = new JPanel();
            aboutDialog.getContentPane().add(dialogPanel);
            JLabel opis = new JLabel("<html> <p>Hi Everyone!</p> <p>This is my very first"
                                    + " app in Java with Swing</p> <p>I am glad to see you there :)</p></html",SwingConstants.CENTER);
            opis.setFont(new Font("Courier New", Font.PLAIN, 12));
            dialogPanel.add(opis);
            
            //setting components 
            int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
            int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
            aboutDialog.setBounds(screenWidth/2 - 200, screenHeight/2 - 150, 400, 250);
            aboutDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            
        }
    }

}
