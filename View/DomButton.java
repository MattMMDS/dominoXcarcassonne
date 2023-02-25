package View;

import Model.ObjectCarcassonne;
import Model.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class DomButton extends JButton {

    public DomButton (Piece<Integer> e, int size){
        setIcon(resizeIcon(new ImageOfDomino(e.vals), 1000 /size , 1000 /size));
    }

    public DomButton() {
    }

    public DomButton(AbstractAction action, int size) {
        super(action);
        setIcon(resizeIcon(new ImageIcon(("View/img/posable_img.png")) , 1000/size , 1000/size));
    }

    public void changeIcon(int size){
        setIcon(resizeIcon((ImageIcon) getIcon(), 1000 /size , 1000 /size));
    }

    public void changeIcon(Piece e , int size){
        if (e != null){
            setIcon(resizeIcon(new ImageOfDomino(e.vals), 1000 /size , 1000 /size));
        }
    }

    private static Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
        Image img = icon.getImage();
        if (img != null){
            Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,  Image.SCALE_SMOOTH);
            return new ImageIcon(resizedImage);
        }else{
            return icon;
        }
    }

    public static class CarcassonneButton extends JButton{
        private String filename;

        public CarcassonneButton (String s, int size){
            filename = "View/img/" + s+".png";
            setIcon(resizeIcon(new ImageOfCarcassonne(filename), 1000 /size , 1000 /size));
        }

        public CarcassonneButton() {
        }

        public CarcassonneButton(AbstractAction action, int size) {
            super(action);
            setIcon(resizeIcon(new ImageIcon(("View/img/posable_img.png")) , 1000/size , 1000/size));
        }

        public static String getFilename(Piece e){
            StringBuilder nomFic = new StringBuilder();
            try {
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 3; j++) {
                        ObjectCarcassonne tmp = (ObjectCarcassonne) e.vals[i][j];
                        Object tmp2 = e.vals[i][j];
                        if (tmp.typeTerrain.equals("ville")){
                            nomFic.append("v");
                        }else if (tmp.typeTerrain.equals("prairie")){
                            nomFic.append("p");
                        }else {
                            nomFic.append("c");
                        }
                    }
                }
                ObjectCarcassonne tmp = (ObjectCarcassonne) e.centre;
                if (tmp.typeTerrain.equals("ville")){
                    nomFic.append("v");
                }else if (tmp.typeTerrain.equals("prairie")){
                    nomFic.append("p");
                }else {
                    nomFic.append("c");
                }
            }catch (Exception ignored){
            }
            return nomFic.toString();
        }

        public void changeIcon(int size){
            setIcon(resizeIcon((ImageIcon) getIcon(), 1000 /size , 1000 /size));
        }

        public void changeIcon(Piece e , int size){
            if (e != null){
                filename = "View/img/" + getFilename(e)+".png";
                setIcon(resizeIcon(new ImageOfCarcassonne(filename), 1000 /size , 1000 /size));
            }
        }

        private static Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
            Image img = icon.getImage();
            if (img != null){
                Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,  Image.SCALE_SMOOTH);
                return new ImageIcon(resizedImage);
            }else{
                return icon;
            }
        }

        private int deg = 0;

        public void turnImage(int ignore) {
            File f = new File(filename);
            BufferedImage imageToRotate = null;
            try {
                imageToRotate = ImageIO.read(f);
            } catch (IOException ignored) {}
            if (imageToRotate!= null){
                //TODO a voir si c'est fixable
//                int widthOfImage = 1000/size;
//                int heightOfImage = &000/size;
                int widthOfImage = imageToRotate.getWidth();
                int heightOfImage = imageToRotate.getHeight();
                int typeOfImage = imageToRotate.getType() ;

                BufferedImage newImageFromBuffer = new BufferedImage(widthOfImage, heightOfImage, typeOfImage);

                Graphics2D graphics2D = newImageFromBuffer.createGraphics();

                deg= (deg + 90) % 360;

                graphics2D.rotate(Math.toRadians(deg), widthOfImage/2, heightOfImage/2);
                graphics2D.scale(2 , 2);
                graphics2D.drawImage(imageToRotate, null, 0, 0);

                this.setIcon(new ImageIcon(newImageFromBuffer));
            }
        }
        public void turnImage() {
            File f = new File(filename);
            BufferedImage imageToRotate = null;
            try {
                imageToRotate = ImageIO.read(f);
            } catch (IOException ignored) {}
            if (imageToRotate != null){
                int widthOfImage = imageToRotate.getWidth();
                int heightOfImage = imageToRotate.getHeight();
                int typeOfImage = imageToRotate.getType();

                BufferedImage newImageFromBuffer = new BufferedImage(widthOfImage, heightOfImage, typeOfImage);

                Graphics2D graphics2D = newImageFromBuffer.createGraphics();

                deg= (deg + 90) % 360;

                graphics2D.rotate(Math.toRadians(deg), widthOfImage / 2, heightOfImage / 2);
                graphics2D.drawImage(imageToRotate, null, 0, 0);

                this.setIcon(new ImageIcon(newImageFromBuffer));
            }
        }

        public void dessinePion(int x, int y , Color color) {
            File f = new File(filename);
            BufferedImage imageToRotate = null;
            BufferedImage pion = null;
            try {
                imageToRotate = ImageIO.read(f);
                pion = ImageIO.read(new File("View/img/pion" + getColorFileName(color) + ".png"));
            } catch (IOException ignored) {}
            Graphics g = Objects.requireNonNull(imageToRotate).getGraphics();
            int factor = imageToRotate.getHeight() / 3;
            g.drawImage(pion, factor * (x), factor * (y), null);

        }

        private static String getColorFileName(Color c){
            Color[] colors = new Color[]{Color.GRAY , Color.BLACK , Color.YELLOW , Color.blue , Color.green , Color.red};
            if (c.equals(colors[0])) {
                return "gray";
            } else if (c.equals(colors[1])) {
                return "black";
            } else if (c.equals(colors[2])) {
                return "yellow";
            } else if (c.equals(colors[3])) {
                return "blue";
            } else if (c.equals(colors[4])) {
                return "green";
            } else if (c.equals(colors[5])) {
                return "red";
            }else {
                return null;
            }
        }
    }

    private static class ImageOfDomino extends ImageIcon{
        public ImageOfDomino(Object[][] vals){
            try {
                BufferedImage[][] images = new BufferedImage[4][3];
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 3; j++) {
                        images[i][j] = ImageIO.read(new File("View/img/"+vals[i][j]+"_img.png"));
                    }
                }

                // Créer une nouvelle image avec la largeur et la hauteur des deux images
                int width = ImageIO.read(new File("View/img/0_img.png")).getWidth()*5;
                int height = ImageIO.read(new File("View/img/0_img.png")).getHeight()*5;
                BufferedImage combined = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);


                Graphics g = combined.getGraphics();

                g.drawImage(images[0][0], (width/5), 0, null);
                g.drawImage(images[0][1], (width/5)*(2), 0, null);
                g.drawImage(images[0][2], (width/5)*(3), 0, null);

                g.drawImage(images[1][0], (width/5)*(4), (height/5), null);
                g.drawImage(images[1][1], (width/5)*(4), (height/5)*2, null);
                g.drawImage(images[1][2], (width/5)*(4), (height/5)*3, null);

                g.drawImage(images[2][0], (width/5)*(3), (height/5)*4, null);
                g.drawImage(images[2][1], (width/5)*(2), (height/5)*4, null);
                g.drawImage(images[2][2], (width/5), (height/5)*4, null);

                g.drawImage(images[3][0], 0, (height/5)*3, null);
                g.drawImage(images[3][1], 0, (height/5)*2, null);
                g.drawImage(images[3][2], 0, (height/5), null);


                setImage(combined);
            } catch (Exception ignored) {}
        }
    }

    public static class ImageOfCarcassonne extends ImageIcon{

        public ImageOfCarcassonne(String s){
            try {
                BufferedImage image = ImageIO.read(new File(s));
                setImage(image);
            } catch (Exception ignored) {}
        }
    }
}


