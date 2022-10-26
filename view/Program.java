package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import model.EmartMallDAO;
import model.EmartMallVO;

public class Program extends JFrame{
	JTextField tf1, tf2, tfUrl;
	JButton btCopy, btUrlCopy; JProgressBar pb; JLabel lbImage;

	FileInputStream fis; FileOutputStream fos;
	File file1, file2;

	public Program(EmartMallVO vo) { // vo를 입력으로 받아 vo가 가진 img를 출력
		super("::Program::");
		Container cp = getContentPane();
		JPanel p = new JPanel(new GridLayout(0,1));
		cp.add(p);
		lbImage=new JLabel("", JLabel.CENTER);
		cp.add(new JScrollPane(lbImage));
		lbImage.setBorder(new LineBorder(Color.magenta));

		tf2=new JTextField("target.jpg");
		tfUrl=new JTextField("https:"+vo.geteImg());
		btUrlCopy=new JButton("출력");
		pb=new JProgressBar();
		JPanel p2 = new JPanel(); p.add(p2);
		p2.add(btUrlCopy);

		p.setBorder(new BevelBorder(BevelBorder.RAISED)); // 패널을 입체적으로 보여줌 

		// 인터넷 이미지 url 주소의 이미지를 복사하는 버튼 이벤트 리스너
		String urlStr = tfUrl.getText();
		urlFileCopy(urlStr); // 사용자 정의 메소드
		new Thread(){
		}.start();
		setSize(500, 800);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public synchronized void fileCopy(File srcFile, File tarFile){
		// 스트림 연결 => 노드 스트림 => 필터 스트림
		String msg="";
		long fsize=srcFile.length(); // 원본 파일의 크기를 반환
		setTitle("원본 파일 크기 : "+fsize+"bytes");
		//pb의 최대값을 원본 파일 크기로 잡는다.
		pb.setMaximum(0); pb.setMaximum((int)fsize);

		try{
			fis = new FileInputStream(srcFile);
			fos = new FileOutputStream(tarFile);

			//BuffredInputStream으로 처리하면 더 빠르다

			// 반복문 돌면서 읽어들이고 내보낸다.
			int input=0, count=0;

			byte[] data = new byte[1024];

			// 원본 이미지 복사
			while((input=fis.read(data))!=-1){
				fos.write(data, 0, input);
				fos.flush();
				count+=input;
				pb.setValue(count);
				Thread.sleep(10);
			}
			msg=count+"bytes 카피 완료";

			// 스트림 닫기
			fis.close(); fos.close();

			lbImage.setIcon(new ImageIcon(tarFile.getAbsolutePath()));
		}catch(FileNotFoundException e){
			msg="없는 파일이에요: "+e.getMessage();
		}catch(InterruptedException e){
			msg="Interrupted 오류"+e.getMessage();
		}catch(IOException e){
			msg="오류 : "+e.getMessage();
		}

		JOptionPane.showMessageDialog(this, msg);
	}

	public void urlFileCopy(String urlStr){
		// throws로 하면 다른 쪽에서 예외 처리를 해줘야함
		try{
			java.net.URL url = new java.net.URL(urlStr);
			InputStream is = url.openStream();

			java.net.URLConnection con = url.openConnection();
			// url에 있는 파일의 크기를 반환한다.
			int fsize = con.getContentLength();
			setTitle(fsize+"bytes");

			// 해당 url과 노드 연결된 입력 스트림을 반환한다.
			BufferedInputStream bis = new BufferedInputStream(is);
			file2 = new File(tf2.getText());
			fos = new FileOutputStream(file2);
			BufferedOutputStream bos = new BufferedOutputStream(fos);

			int input=0, count=0;
			byte[] data = new byte[3000];
			while((input=bis.read(data))!=-1){
				bos.write(data, 0, input);
				bos.flush();
				count+=input;
				pb.setValue(count);
				Thread.sleep(10);

			}

			bis.close(); bos.close();
			is.close(); fos.close();

			lbImage.setIcon(new ImageIcon(file2.getAbsolutePath()));

		}catch(MalformedURLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws IOException {
		View v=new View();
		EmartMallVO vo=new EmartMallVO();
		EmartMallDAO dao=new EmartMallDAO();
		new Program(dao.selectOne(vo)); // vo에 담겨있는 img를 화면으로 출력
	}
}
