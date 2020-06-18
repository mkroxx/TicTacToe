/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tic.tac.toe;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Mukund
 */
public class TicTacToe extends javax.swing.JFrame {

    /**
     * Creates new form TicTacToe
     */
    private int f=0;
    private URL zero,cross;
    private JLabel[][] lbls;
    private char board[][];
    private char ai='X',human='O';
    private char currentPlayer=ai;
    private Map<Character,Integer> scores=new HashMap<Character,Integer>();
    private final Dimension screen;
        
    public TicTacToe() {
       initComponents();
       screen=getToolkit().getScreenSize();
       this.setBounds((int)(screen.getWidth()/2.8),(int)(screen.getHeight()/3.5), this.getWidth(), this.getHeight());
       scores.put(ai,10);
       scores.put(human,-10);
       scores.put('T',99);
       zero=getClass().getResource("images/oh.png");
       cross=getClass().getResource("images/ex.png");
       lbls=new JLabel[3][3];
       board=new char[3][3];
       lbls[0][0]=jLabel1;
       lbls[0][1]=jLabel2;
       lbls[0][2]=jLabel3;
       lbls[1][0]=jLabel4;
       lbls[1][1]=jLabel5;
       lbls[1][2]=jLabel6;
       lbls[2][0]=jLabel7;
       lbls[2][1]=jLabel8;
       lbls[2][2]=jLabel9;
	  for(int i=0; i<3; i++){
        for(int j=0; j<3; j++){
		final int l=i,m=j;
          lbls[i][j].addMouseListener(new MouseListener() {

              @Override
              public void mouseClicked(MouseEvent e) {
		Point p=new Point(l,m);
		userTurn(p);
              }

              @Override
              public void mousePressed(MouseEvent e) {
                //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
              }

              @Override
              public void mouseReleased(MouseEvent e) {
                //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
              }

              @Override
              public void mouseEntered(MouseEvent e) {
                  onMouseHover(e);
                 // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
              }

              @Override
              public void mouseExited(MouseEvent e) {
                 // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
              }
          });
       }
    }
	bestMove();
    }

      private void userTurn(Point p){
                                checkResult();
				if(board[p.X][p.Y]=='\0'){
				board[p.X][p.Y]=human;
				updateBoard();
                                if(openSpots()>0)bestMove();
                                else checkResult();
		 }
                
	}
    
        private void startNewGame(){
            if(currentPlayer==ai){
            currentPlayer=human;
            }
            else{
            currentPlayer=ai;
            bestMove();
            }
        }
      
	private void checkResult(){
		char winner=checkWinner();
			 if(winner!='\0' && winner=='O') {
				JOptionPane.showMessageDialog(this,"You Win!!");
				board=new char[3][3];
				updateBoard();
                                startNewGame();
			 }
			if(winner!='\0' && winner=='X'){  
				JOptionPane.showMessageDialog(this,"AI Won!!");
				board=new char[3][3];
				updateBoard();
                                startNewGame();
			}
			
			if(openSpots()==0 && winner=='T'){
				JOptionPane.showMessageDialog(this,"Game Tied!!");
				board=new char[3][3];
				updateBoard();
                                startNewGame();
			}
			
	}
    
   
    
     private void onMouseHover(MouseEvent e){
         for(int i=0; i<3; i++)
				for(int j=0; j<3; j++)
           lbls[i][j].setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
   
	private boolean equals3(char a, char b, char c){
		if(a==b && b==c && a!='\0')return true;
		return false;
	}
    
	//Checks Winner
    private char checkWinner(){
		char winner='\0';
		//For horizontal
		 for(int i=0; i<3; i++){
			 if(equals3(board[i][0],board[i][1],board[i][2]))
				 winner=board[i][0];
		 }
		 
		 //For vertical
		 for(int j=0; j<3; j++){
			 if(equals3(board[0][j],board[1][j],board[2][j]))
				 winner=board[0][j];
		 }
		 
		 //For Diagonal 
		 if(equals3(board[0][0],board[1][1],board[2][2])){
			winner=board[0][0];
		 }
		 
		 //For Diagonal
		  if(equals3(board[0][2],board[1][1],board[2][0])){
			winner=board[0][2];
		 }
		 
		 if(winner=='\0' && openSpots()==0)
		 {
			return 'T';
		 }
		 return winner;
	}
	
	//Check OpenSpots on Board. 
	private int openSpots(){
		int spot=0;
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				if(board[i][j]=='\0')spot++;
			}
		}
		return spot;
	}
	
	private void updateBoard(){
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				if(board[i][j]=='X')
					lbls[i][j].setIcon(new ImageIcon(cross));
				if(board[i][j]=='O')
					lbls[i][j].setIcon(new ImageIcon(zero));
				if(board[i][j]=='\0')
					lbls[i][j].setIcon(null);
			}
		}
		
	}
	
	private void bestMove(){
		int score, bestScore=Integer.MIN_VALUE;
		Point position=new Point();
			for(int i=0; i<3; i++){
				for(int j=0; j<3; j++){
					//check whether openspots are available
					if(board[i][j]=='\0'){
						board[i][j]=ai;
						score=minimax(board,0,false);
						board[i][j]='\0';
						if(score>bestScore){
							bestScore=score;
							position.X=i;
							position.Y=j;
						}
					}
				}
			}
		board[position.X][position.Y]=ai;
		updateBoard();
		checkResult();
	}
	
	
    
	private int minimax(char board[][],int depth, boolean isMaximizing){
		char winner=checkWinner();
		if(winner!='\0'){
			return scores.get(winner);
		}
			
			if(isMaximizing){
				int score, bestScore=Integer.MIN_VALUE;
				for(int i=0; i<3; i++){
					for(int j=0; j<3; j++){
					//check whether openspots are available
						if(board[i][j]=='\0'){
							board[i][j]=ai;
							score=minimax(board,depth+1,false);
							board[i][j]='\0';
							if(score>bestScore)bestScore=score;
						}
					}
				}
				return bestScore;
			}
			else{
				int score, bestScore=Integer.MAX_VALUE;
				for(int i=0; i<3; i++){
					for(int j=0; j<3; j++){
					//check whether openspots are available
						if(board[i][j]=='\0'){
							board[i][j]=human;
							score=minimax(board,depth+1,true);
							board[i][j]='\0';
							if(score<bestScore)bestScore=score;
						}
					}
				}
				return bestScore;
			}		
	}

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("\"TICTacToe\"");
        setBackground(new java.awt.Color(0, 0, 51));
        setResizable(false);

        jLabel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 4, 4, new java.awt.Color(102, 0, 204)));
        jLabel1.setPreferredSize(new java.awt.Dimension(88, 88));

        jLabel2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 4, 4, new java.awt.Color(102, 0, 204)));

        jLabel3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 4, 0, new java.awt.Color(102, 0, 204)));
        jLabel3.setPreferredSize(new java.awt.Dimension(88, 88));

        jLabel4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 4, 4, new java.awt.Color(102, 0, 204)));
        jLabel4.setPreferredSize(new java.awt.Dimension(88, 88));

        jLabel5.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 4, 4, new java.awt.Color(102, 0, 204)));
        jLabel5.setPreferredSize(new java.awt.Dimension(88, 88));

        jLabel6.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 4, 0, new java.awt.Color(102, 0, 204)));
        jLabel6.setPreferredSize(new java.awt.Dimension(88, 88));

        jLabel7.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 4, new java.awt.Color(102, 0, 204)));
        jLabel7.setPreferredSize(new java.awt.Dimension(88, 88));

        jLabel8.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 4, new java.awt.Color(102, 0, 204)));
        jLabel8.setPreferredSize(new java.awt.Dimension(88, 88));

        jLabel9.setPreferredSize(new java.awt.Dimension(88, 88));

        jMenu2.setText("Settings");

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.SHIFT_MASK));
        jMenuItem2.setText("About");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem1.setText("Exit");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    Component[] cmps;
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
      JOptionPane.showMessageDialog(this,"<html><center><b> This game is developed by: Mukund Kumar <br/> im.mukundkr@gmail.com </b></center></html>");
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TicTacToe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TicTacToe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TicTacToe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TicTacToe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TicTacToe().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    // End of variables declaration//GEN-END:variables
}
