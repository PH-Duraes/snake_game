import javax.swing.JFrame;

public class GameFrame extends JFrame {

	GameFrame() {

		this.add(new GamePanel());	//Instanciando GamePanel	
		this.setTitle("Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);  //Interface aparecer no meio da tela
	
	
	}
}
