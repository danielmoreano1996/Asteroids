/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Timer;


public class FrmAsteroids extends javax.swing.JFrame implements KeyListener
{
    Objeto nave = new Objeto(200,300,0,0,0);
    Objeto asteroide = new Objeto(100,100,0,0,0);
    Objeto disparo = new Objeto(20,20,0,0,0);

    int numRand = 0;//numero randomico
    int posiNega1;//define rotacion
    int cambioNave = 0;
    int cambioAsteroideX = 1;
    int cambioAsteroideY = 1;
    int cordDebris = 0;
    int cordDebrisS = -640;
    int numAsteroides = 1;
    
    int cambioExploX = 0;
    int cambioExploY = 0;
    
    double distancia;
    double distancia1;
    int colision=0;
    
    boolean valDisparo = false;
   
    public FrmAsteroids() 
    {
        initComponents();
        addKeyListener(this);
        asteroide.setDrawLocationX((Math.random())*800);
        asteroide.setDrawLocationY((Math.random())*600);
    }
    
    public void paint(Graphics g)
    {
        BufferedImage imgNebula = null;
        BufferedImage imgDebris = null;
        BufferedImage imgAsteroide = null;
        BufferedImage imgShip = null;
        BufferedImage imgShipMov = null;
        BufferedImage imgShot = null;
        BufferedImage imgExplosion = null;
        
        try 
        {
            imgNebula = ImageIO.read(new File("C:\\Users\\Daniel Moreano\\Desktop\\Segundo\\InformaticaII\\Asteroids\\asteroidsImagenes\\nebula_blue.png"));            
            imgDebris = ImageIO.read(new File("C:\\Users\\Daniel Moreano\\Desktop\\Segundo\\InformaticaII\\Asteroids\\asteroidsImagenes\\debris2_blue.png"));
            imgShip = ImageIO.read(new File("C:\\Users\\Daniel Moreano\\Desktop\\Segundo\\InformaticaII\\Asteroids\\asteroidsImagenes\\nave1.png"));
            imgShipMov = ImageIO.read(new File("C:\\Users\\Daniel Moreano\\Desktop\\Segundo\\InformaticaII\\Asteroids\\asteroidsImagenes\\nave2.png"));
            imgAsteroide = ImageIO.read(new File("C:\\Users\\Daniel Moreano\\Desktop\\Segundo\\InformaticaII\\Asteroids\\asteroidsImagenes\\asteroid_blue.png"));
            imgExplosion = ImageIO.read(new File("C:\\Users\\Daniel Moreano\\Desktop\\Segundo\\InformaticaII\\Asteroids\\asteroidsImagenes\\explosion.hasgraphics.png"));
            imgShot = ImageIO.read(new File("C:\\Users\\Daniel Moreano\\Desktop\\Segundo\\InformaticaII\\Asteroids\\asteroidsImagenes\\shot3.png"));
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(FrmAsteroids.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        super.paint(g);
        
        g.drawImage(imgNebula,0,0,800,600,this); //fondo de pantalla
        g.drawImage(imgDebris,cordDebris,0,640,480,this); //asteroides Debris 1eros
        g.drawImage(imgDebris,cordDebrisS,0,640,480,this);// asteroides Debris 2dos
        //g.drawImage(imgNebula, 100, 30,190,120,90,0,180,90,this);//con llama

       nave.setDrawLocationX(nave.getDrawLocationX() + nave.getAccX());
       nave.setDrawLocationY(nave.getDrawLocationY() + nave.getAccY());//posicion de la nave
       
       asteroide.setDrawLocationX(asteroide.getDrawLocationX() + (cambioAsteroideX)*5);
       asteroide.setDrawLocationY(asteroide.getDrawLocationY() + (cambioAsteroideY)*5);//posicion asteroides
       
       disparo.setDrawLocationX(disparo.getDrawLocationX() + disparo.getAccX());
       disparo.setDrawLocationY(disparo.getDrawLocationY() + disparo.getAccY());
       
       if(valDisparo == true)
       {
            double rotationRequired = Math.toRadians(disparo.getAngulo());//busca el angulo
            double locationX = imgShot.getWidth() / 2;
            double locationY = imgShot.getHeight() / 2;
            AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
            Graphics2D g2=(Graphics2D) g;
            // Drawing the rotated image at the required drawing locations
            g2.drawImage(op.filter(imgShot, null), (int)disparo.getDrawLocationX(), (int)disparo.getDrawLocationY(), null);
       }
       
            
       
       
       //dibuja la nave con la aceleracion en caso de que exista en caso contrario seguira el ultimo rumbo de la aceleracion dada
        if (cambioNave == 0)
        {
            double rotationRequired = Math.toRadians(nave.getAngulo());//busca el angulo
            double locationX = imgShip.getWidth() / 2;
            double locationY = imgShip.getHeight() / 2;
            AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
            Graphics2D g2=(Graphics2D) g;
            // Drawing the rotated image at the required drawing locations
            g2.drawImage(op.filter(imgShip, null), (int)nave.getDrawLocationX(), (int)nave.getDrawLocationY(), null); 
        }
        else
        {
            double rotationRequired = Math.toRadians(nave.getAngulo());//busca el angulo
            double locationX = imgShipMov.getWidth() / 2;
            double locationY = imgShipMov.getHeight() / 2;
            AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
            Graphics2D g2=(Graphics2D) g;
            // Drawing the rotated image at the required drawing locations
            g2.drawImage(op.filter(imgShipMov, null), (int)nave.getDrawLocationX(), (int)nave.getDrawLocationY(), null);
        }
        
       
        //determina los parametro de dibujo del asteroide.
            double rotationRequired = Math.toRadians(asteroide.getAngulo());
            double locationX = imgAsteroide.getWidth() / 2;
            double locationY = imgAsteroide.getHeight() / 2;
            AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
            Graphics2D g2=(Graphics2D) g;
            
            //dibuja el asteroide con colision o sin ella
            if (colision == 0)
            {
               // Drawing the rotated image at the required drawing locations
                g2.drawImage(op.filter(imgAsteroide, null), (int)asteroide.getDrawLocationX(), (int)asteroide.getDrawLocationY(), null);
            }
            else
            {
                g.drawImage(imgExplosion, (int)asteroide.getDrawLocationX(),(int) asteroide.getDrawLocationY(),(int) asteroide.getDrawLocationX()+90, (int)asteroide.getDrawLocationY()+90, cambioExploX, cambioExploY, cambioExploX+100, cambioExploY+100, this);
            }
            
        //dibuja el disparo en la direccion a donde apunta la nave
            
            
        //condicion para que la nave regrese por los bordes
        if(nave.getDrawLocationX() < 0)
        {
            nave.setDrawLocationX(799);
        }
        if(nave.getDrawLocationY() < 0)
        {
            nave.setDrawLocationY(599);
        }
        nave.setDrawLocationX(nave.getDrawLocationX()%800);
        nave.setDrawLocationY(nave.getDrawLocationY()%600);
        
        //condicion para que los asteroides regresen por los bordes
        if(asteroide.getDrawLocationX() < 0)
        {
            asteroide.setDrawLocationX(799);
        }
        if(asteroide.getDrawLocationY() < 0)
        {
            asteroide.setDrawLocationY(599);
        }
        asteroide.setDrawLocationX(asteroide.getDrawLocationX()%800);
        asteroide.setDrawLocationY(asteroide.getDrawLocationY()%600);
        
    }
    ///////////////////////////////////////////////////////////////////////////////////////
    Timer tiempo = new Timer(1 , new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            
            //movmiento de los asteroides de fondo
            cordDebris++; 
            cordDebrisS++;
            
            if(cordDebris == 640)
            {
                cordDebris = -640;
            }
            if(cordDebrisS == 640)
            {
                cordDebrisS = -640; 
            }
            
            //Set de los asteroides.
            if(numAsteroides <= 1)
            {
                asteroide.setDrawLocationX((Math.random())*800); //genera posicionX inicial del asteroide
                asteroide.setDrawLocationY((Math.random())*600); //genera posicionY inicial del asteroide 
                //genera la direccion en X del asteroide
                numRand = ((int)((Math.random())*10))%2;
                if(numRand == 0)
                {
                    cambioAsteroideX = -1;
                }
                else
                {
                    cambioAsteroideX = 1;
                }
                //genera la direccion en Y del asteroide
                numRand = ((int)((Math.random())*10))%2;
                if(numRand == 0)
                {
                    cambioAsteroideY = -1;
                }
                else
                {
                    cambioAsteroideY = 1;
                }
               
                posiNega1 = ((int)((Math.random())*10))%2; // condicion de giro del asteroide
                asteroide.setAccX(Math.cos(Math.toRadians(asteroide.getAngulo()%360))*3);//aceleracion en x de los asteroide
                asteroide.setAccY(Math.sin(Math.toRadians(asteroide.getAngulo()%360))*3);//aceleracion en y de los asteroides
                numAsteroides++;
            }
               
            if(posiNega1 == 0)
            {
                asteroide.setAngulo(asteroide.getAngulo() - 10);
            }
            else
            {
                asteroide.setAngulo(asteroide.getAngulo() + 10);
            }
            
            //distancia entre asteroides y nave
            distancia = Math.sqrt(Math.pow(nave.getDrawLocationX() - asteroide.getDrawLocationX(), 2)+Math.pow(nave.getDrawLocationY()-asteroide.getDrawLocationY(), 2));
            distancia1 = Math.sqrt(Math.pow(disparo.getDrawLocationX() - asteroide.getDrawLocationX(), 2)+Math.pow(disparo.getDrawLocationY()-asteroide.getDrawLocationY(), 2));
            if(distancia <= 90 || distancia1 <= 55)
            {
                colision = 1;
                
                cambioExploX +=100;
                if(cambioExploX > 900)
                {
                    cambioExploY+=100;
                    cambioExploX = 0;
                }   
            }
            else
            {
                colision =0;
                cambioExploX = 0;
                cambioExploY = 0;
            }

            
            
            repaint();
        }
    });
    
    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e)
    {
        if(e.getKeyCode() == 32)
        {
           disparo.setDrawLocationX(nave.getDrawLocationX()+35);
           disparo.setDrawLocationY(nave.getDrawLocationY()+35);
           disparo.setAngulo(nave.getAngulo());
           disparo.setAccX(Math.cos(Math.toRadians(nave.getAngulo()%360))*10);
           disparo.setAccY(Math.sin(Math.toRadians(nave.getAngulo()%360))*10);
           valDisparo = true;
        }
    }
    public void keyPressed(KeyEvent e)
    {
       System.out.println(e.getKeyCode());
       if(e.getKeyCode() == 87)
       {
          nave.setAccX(Math.cos(Math.toRadians(nave.getAngulo()%360))*6);
          nave.setAccY(Math.sin(Math.toRadians(nave.getAngulo()%360))*6);
          cambioNave = 1;
       }
       else
       {
           cambioNave = 0;
       }
       
       if(e.getKeyCode() == 65)
       {
           nave.setAngulo(nave.getAngulo()-10);
       }
       if(e.getKeyCode() == 68)
       {
           nave.setAngulo(nave.getAngulo()+10);
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(800, 600));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 599, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 438, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tiempo.start();
    }//GEN-LAST:event_formWindowOpened

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmAsteroids.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmAsteroids.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmAsteroids.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmAsteroids.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmAsteroids().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
