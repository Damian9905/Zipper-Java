package Zipper;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.*;

public class FileZipper extends JFrame
{
    //variables and components
    private JButton bAdd;
    private JButton bRemove;
    private JButton bZip;
    private JMenuBar topMenu = new JMenuBar();
    private JList list = new JList();
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
        
        //buttons
        bAdd = new JButton(adding);
        bRemove = new JButton(removing);
        bZip= new JButton(zipping);
        
        list.setBorder(BorderFactory.createEtchedBorder());
        
        //topMenu
        this.setJMenuBar(topMenu);
        JMenu fileMenu = topMenu.add(new JMenu("File"));
            JMenuItem addMenuItem = fileMenu.add(adding);
            JMenuItem removeMenuItem = fileMenu.add(removing);
            JMenuItem zipMenuItem = fileMenu.add(zipping);
        
        //layout
        GroupLayout myLayout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(myLayout);
        myLayout.setAutoCreateContainerGaps(true);
        myLayout.setAutoCreateGaps(true);
        
        myLayout.setHorizontalGroup(
                myLayout.createSequentialGroup()
                .addComponent(list, 100, 150, Short.MAX_VALUE)
                .addGroup(
                        myLayout.createParallelGroup()
                        .addComponent(bAdd)
                        .addComponent(bRemove)
                        .addComponent(bZip)
                )
        );
        myLayout.setVerticalGroup(
                myLayout.createParallelGroup()
                .addComponent(list, 100, 150, Short.MAX_VALUE)
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

        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if(e.getActionCommand().equals("Add"))
                addToZip();
            else if(e.getActionCommand().equals("Remove"))
                System.out.println("Removing");
            else
                System.out.println("Zipping");
        }
        private void addToZip()
        {
            chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            chooser.showDialog(rootPane, "Add to zip");
        }
    }

}
