import javax.swing.event.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

class MychatServer implements ActionListener
{
static JFrame jf;
static JTextField jt;
JButton jb;
JLabel jl;
 
MychatServer()
{
jf=new JFrame("LOGIN_WINDOW");
WindowUtilities.setNativeLookAndFeel();
jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//Font font = new Font("Serif", Font.PLAIN, 30);
//jf.setFont(font);
String s1="Enter name to login:";
jl=new JLabel(s1,JLabel.LEFT);
jf.add(jl,BorderLayout.WEST);

jt=new JTextField();
jt.addKeyListener(new KeyAdapter(){
                  public void keyPressed(KeyEvent e)
                  {
                  if (e.getKeyCode()== KeyEvent.VK_ENTER){
                  String str=MychatServer.jt.getText();
                  MychatServer.jf.setVisible(false);
                  new Custmore1(str);  
                  }
                  }
                  });
jf.add(jt,BorderLayout.CENTER);

jb=new JButton("LOGIN");
jf.add(jb,BorderLayout.SOUTH);
jb.addActionListener(this);
//KeyStroke entr=KeyStroke.getKeyStroke(ActionEvent.ENTER_MASK);
//jb.setAccelerator(entr);
//jb.setMnemonic(keyEvent.VK_ENTER);
jf.setVisible(true);
jf.setSize(200,200);

}


public void actionPerformed(ActionEvent e)
{
if (e.getSource()==jb)
{
String str=MychatServer.jt.getText();
MychatServer.jf.setVisible(false);
new Custmore1(str);
}
}



public static void main(String... s){
new MychatServer();
}
}



class Custmore1 implements ActionListener
{
String str;
Socket s;
JFrame jf1;
JButton jb1;
static JTextArea ja1,ja2;
static JTextField jt1;
JPanel jp,jp1,jp2;
JLabel jl1,jl2;
JScrollPane js;
DataInputStream din;
DataOutputStream dout;
String S=" ";
Custmore1(String str)
{
this.str=str;
jf1=new JFrame(str);
jb1=new JButton("SEND");
ja1=new JTextArea();
ja2=new JTextArea();
jp=new JPanel();
jp1=new JPanel();
jp2=new JPanel();
jt1=new JTextField();
jt1.addKeyListener(new KeyAdapter(){
                  public void keyPressed(KeyEvent e)
                  {
                  try{
                  if (e.getKeyCode()== KeyEvent.VK_ENTER){
                  dout.writeUTF(str);
                  dout.flush();
                  S=Custmore1.jt1.getText();
                  dout.writeUTF(S);
                  dout.flush();
                  jt1.setText(null);  
                  }
                  }catch (Exception pgl){}
                  }
                  });
//jt1.setBounds(300,200);
ja1.setEditable(false);
ja2.setEditable(false);



String s1="CHATTING";
jl1=new JLabel(s1,JLabel.LEFT);
//jl1.setBorder(BorderFactory.createTitledBorder("Mixed Colors")); 
jp1.add(jl1);

String s2="FRIENDS ONLINE";
jl2=new JLabel(s2,JLabel.RIGHT);
//jl2.setBorder(BorderFactory.createTitledBorder("Mixed Colors1")); 
jp1.add(jl2);
jp1.setLayout(new FlowLayout());
jf1.add(jp1,BorderLayout.NORTH);

jp.add(ja1);
js=new JScrollPane(ja1);
jp.add(js);

jp.add(ja2);
js=new JScrollPane(ja2);
jp.add(js);

jp.setLayout(new GridLayout(0,2,10,40));
jf1.add(jp,BorderLayout.CENTER);


jp2.add(jt1);
js=new JScrollPane(jt1);
jp2.add(js);

jp2.add(jb1);
jp2.setLayout(new GridLayout(0,2));
jf1.add(jp2,BorderLayout.SOUTH);

jb1.addActionListener(this);

jf1.setVisible(true);
jf1.setSize(500,500);
try{
s=new Socket("localHost",10);
din=new DataInputStream(s.getInputStream());
dout=new DataOutputStream(s.getOutputStream());
String ss=str + " Connected";
dout.writeUTF(ss);
dout.flush();
clientChat();
}
catch(Exception e){}
} 

public void clientChat() throws IOException{
My m=new My(din,str);
Thread t1=new Thread(m);
t1.start();
}

public void actionPerformed(ActionEvent e1) 
{
try{
if (e1.getSource()==jb1)
{
dout.writeUTF(str);
dout.flush();
S=Custmore1.jt1.getText();
dout.writeUTF(S);
dout.flush();
jt1.setText(null);
}
}
catch (Exception e){}
}

}


class My implements Runnable{
DataInputStream din;
String P;
My(DataInputStream din,String P)
{
this.din=din;
this.P=P;
}
public void run(){
String s2="";
String s4="";
StringBuffer sb = new StringBuffer();
StringBuffer sb1 = new StringBuffer();

do{
try{
s4=din.readUTF();
if (s4.endsWith("Connected"))
{
sb1.append(s4 + "\n");
Custmore1.ja2.setText(sb1.toString());
}
else
{
sb.append(s4 + ":");
s2=din.readUTF();
sb.append(s2 + "\n");
Custmore1.ja1.setText(sb.toString());
}
}
catch (Exception e){}
}
while(!s2.equals("stop"));
Custmore1.ja2.setText(P + "disconnected");
//Custmore1.ja2.setVisible(false);
}
}

