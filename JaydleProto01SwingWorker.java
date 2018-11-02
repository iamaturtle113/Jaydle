package applications;
/*180512(Sat)
 * Youtube-Dl-Extended用のプロセスビルダーを書く
 * 
 */
import java.lang.ProcessBuilder;
import static practice.IOMethods.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.Process;
import java.lang.Runtime;
import java.util.*;
import javax.security.auth.callback.TextOutputCallback;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

//import com.sun.javafx.event.EventQueue;

//import sun.awt.image.PixelConverter.Bgrx;
/*180809Thu 
 *181023Tue Try to add JMenuBar
 */
public class JaydleProto01SwingWorker extends JFrame 
{
	 static JTextArea textOut=new JTextArea();
	 JScrollPane scrollpane=new JScrollPane(textOut,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	 static String initText=("URLを入力してください");
	 static JTextField textIn=new JTextField(initText);
	 static String strIn=null;
	 static List<String> cmdList= new ArrayList<>(Arrays.asList("youtube-dl","--no-playlist","--extract-audio","--audio-format","mp3","-o","'%(title)s.%(ext)s'"));
	 static String url;//="https://www.youtube.com/watch?v=FqVTzr3CfEg";
	// String[] audioDl= {"youtube-dl","--no-playlist","--extract-audio","--audio-format","mp3",url,"-o","'%(title)s.%(ext)s'"};
	 
	 BackgroundTask bgt;
	 
	 
	 //static File dir=new File ("");
	
	public class BackgroundTask extends SwingWorker<Integer, String>
	{
		@Override
		public Integer doInBackground() throws Exception 
		{//Use publish() method at here
			try 
			{
				ProcessBuilder pb=new ProcessBuilder(cmdList);
				//pb.directory(dir);
				Process p=pb.start();
				BufferedReader bfr2=new BufferedReader(new InputStreamReader(p.getInputStream()));
				BufferedReader bfrErr=new BufferedReader(new InputStreamReader(p.getErrorStream()));
				String line=null;
					while((line=bfr2.readLine())!=null)
				//最後まで読み込むと、lineがnullになることを利用している
					{
						println(line);
						//textOut.append(line+"\n");
						publish(line);
					}
					while((line=bfrErr.readLine())!=null)
					//最後まで読み込むと、lineがnullになることを利用している
					{
						println(line);
						//textOut.append(line+"\n");
						publish(line);
					}
			}

			/*catch(Exception e)
			{
				e.printStackTrace();
			}*/
			catch(NullPointerException e)
			{
				println("NullPo!!!");
				e.printStackTrace();
				
			}
			finally
			{
			//cmdList.clear();
				cmdList.remove(5);
			}
			
			return null;
		}
	
		@Override
		protected void process(List<String> lines)
		{
			try
			{
				for(String line:lines)
				{
					textOut.append(line+"\n");
				}
			}
			catch(NullPointerException e)
			{
				println("NullPo!!!");
				e.printStackTrace();
				
			}
		}
		
		@Override
		protected void done()
		{
			try
			{
				int result=get();
				textOut.append("result is "+result);
				cmdList.clear();
			}
			catch(Exception ex)
			{}
			
		}
	}
	//End of BackgroundTask
	
	public static  void processBuild(List<String> cmdList)//String[] cmdArray)
	{
		try 
		{
			ProcessBuilder pb=new ProcessBuilder(cmdList);
			//pb.directory(dir);
			Process p=pb.start();
			BufferedReader bfr2=new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader bfrErr=new BufferedReader(new InputStreamReader(p.getErrorStream()));
			String line=null;
				while((line=bfr2.readLine())!=null)
			//最後まで読み込むと、lineがnullになることを利用している
				{
					println(line);
					textOut.append(line+"\n");
					//this.publish(line);
				}
				while((line=bfrErr.readLine())!=null)
				//最後まで読み込むと、lineがnullになることを利用している
				{
					println(line);
					textOut.append(line+"\n");
				}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}//processBuildおわり
	
	public static List<String> toList(String[] strArray)
	{
		try
		{
		//List<String> cmdList=new ArrayList<String>();
		for(String s:strArray)
			{
				cmdList.add(s);
			}
		return cmdList;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static void dlAudio(String url)
	{
		String[] audioDl= {"youtube-dl","--no-playlist","--extract-audio","--audio-format","mp3",url,"-o","'%(title)s.%(ext)s'"};
		List<String> listA=toList(audioDl);
		processBuild(listA);
	}
	
	
	public static void cmdListSetter(String url)
	{
		//String[] audioDl= {"youtube-dl","--no-playlist","--extract-audio","--audio-format","mp3",url,"-o","'%(title)s.%(ext)s'"};
		println("From cmdListGenerator: content of variable url = "+url);
		//cmdList.set(5,url);
		//printList(audioDl);
		cmdList.add(5,url);
	}
	
	

	
	public static void main(String[] args) 
	{
		//JaydleProto01SwingWorker guiApp=new JaydleProto01SwingWorker();
		//BackgroundTask<Integer,String> bgt=new BackgroundTask<Integer,String>();
		//guiApp.initGuiApp();
		//bgt.execute();
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new JaydleProto01SwingWorker().initGuiApp();
			}
		});
		
	}//end of main method
	
	public void initGuiApp() 
	{
		JPanel panel =new JPanel();
		JLabel text =new JLabel("\"JAva Youtube-DL Exteded implementation by Masataka Nakamura\"");
		JFrame frame = new JFrame("Jaydle");
		JMenuBar menuBar=new JMenuBar();
		JMenu menu1=new JMenu("Store");
		menuBar.add(menu1);
		JMenuItem menuItem1=new JMenuItem("Change save directory");
		menu1.add(menuItem1);
		frame.setJMenuBar(menuBar);
		
		textOut.setLineWrap(true);
		JButton button=new JButton("ダウンロードする");
		button.setPreferredSize(new Dimension(100, 30));
		button.addActionListener(new ButtonListener());	
		panel.setLayout(new BorderLayout());//このコードがパネルのレイアウト変更には必要
		panel.add(BorderLayout.NORTH,text);//"Youtube-DL Exteded Java Implementation by Masataka Nakamura"
		panel.add(BorderLayout.SOUTH,textIn);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(BorderLayout.CENTER,scrollpane);
		frame.getContentPane().add(BorderLayout.NORTH,panel);
		frame.getContentPane().add(BorderLayout.SOUTH,button);
		frame.setSize(800,600);
		frame.setVisible(true);
	}
	//End of init()
	
	class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
				strIn=textIn.getText();
				if(strIn.length()>13)
					url=strIn;
					cmdListSetter(strIn);
					printList(cmdList);
					
					//dlAudio(strIn);
				bgt=new BackgroundTask();
				
					bgt.execute();
					
					
		}
	}
	
	class MenuListenerSaveDir implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			 // dir=new File ("");
		}
	}

}
//end of class ProcessBuild


