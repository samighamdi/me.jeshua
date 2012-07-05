package jeshua.rl.pgrd.demo;


import java.awt.*;

import javax.swing.*;


import jeshua.rl.pgrd.OLGARB_Agent;
import jeshua.rl.uct.demo.DemoState;
import jeshua.rl.uct.demo.Maze;
import jeshua.rl.uct.demo.MazeGFX;

public class DemoVisualizeOG {
	JFrame frame;
	JPanel panel;
	Maze maze;
	int agx;
	int agy;
	OLGARB_Agent agent;
	
	class Canvas extends JPanel{
		private static final long serialVersionUID = 1L;

		@Override
		public void paint(Graphics g) {
			Graphics2D g2d = (Graphics2D)g;
			double[][] overlay = new double[maze.width()][maze.height()];
			double max = Double.NEGATIVE_INFINITY;
			double min = Double.POSITIVE_INFINITY;
			DemoQFunction rf = (DemoQFunction)agent.getQF();
			DemoState st = new DemoState(0,0);
			for(int x=0;x<overlay.length;x++){
				for(int y=0;y<overlay[x].length;y++){
					st.x = x;
					st.y = y;
					double mx = Double.NEGATIVE_INFINITY;
					double[] Q = rf.getQ(st);
					for(int ind=0;ind<Q.length;ind++)
					  if(Q[ind] > mx) mx = Q[ind];
					double r = mx;
					overlay[x][y] = r;
					if(r > max)
						max = r;
					if(r < min)
						min = r;
					System.out.printf("%.2f ",overlay[x][y]);
				}
			}
			for(int x=0;x<overlay.length;x++){
				for(int y=0;y<overlay[x].length;y++){
					overlay[x][y] = (overlay[x][y] - min) / (max - min);					
				}
			}
			
			MazeGFX.draw(g2d, this.getSize(), maze, agx, agy,overlay);
		}
	}
	
	public DemoVisualizeOG(Maze maze, OLGARB_Agent agent){
		frame = new JFrame();
		panel = new Canvas();
		this.agent = agent;
		this.agx = 0;
		this.agy = 0;
		this.maze = maze;
		
		panel.setMinimumSize(new Dimension(500,500));
		frame.setMinimumSize(new Dimension(500,500));
		frame.add(panel);
		frame.setVisible(true);
	}
	
	public void redraw(int agx, int agy){
		this.agx = agx;
		this.agy = agy;
		this.panel.repaint();
	}
	
	
}
