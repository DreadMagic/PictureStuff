import java.awt.Color;

public class Steganography {

    public static void clearLow(Pixel p){
        p.setRed(p.getRed()/4*4);
        p.setBlue(p.getBlue()/4*4);
        p.setGreen(p.getGreen()/4*4);
    }

    public static Picture testClearLow(Picture p){
        Pixel[][] p2 = p.getPixels2D();

        for(Pixel[] p3: p2) for(Pixel p4: p3) clearLow(p4);
        return p;
    }

    public static void setLow(Pixel p, Color c){
        int rp = p.getRed()/4*4;
        int bp = p.getBlue()/4*4;
        int gp = p.getGreen()/4*4;

        p.setRed(c.getRed()/64 + rp);
        p.setBlue(c.getBlue()/64 + bp);
        p.setGreen(c.getGreen()/64 + gp);

    }

    public static Picture testSetLow(Picture p, Color c){
        Pixel[][] p2 = p.getPixels2D();

        for(Pixel[] p3: p2) for(Pixel p4: p3) setLow(p4,c);
        return p;
    }

    public static Picture revealPicture(Picture hidden){
        Picture copy = new Picture(hidden);
        Pixel [][] pixels = copy.getPixels2D();
        Pixel [][] source = hidden.getPixels2D();
        for(int r = 0; r < pixels.length; r++){
            for(int c = 0; c < pixels[0].length; c++){
                Color col = source[r][c].getColor();
                int red = col.getRed() % 4 * 64;
                int blue = col.getBlue() % 4 * 64;
                int green = col.getGreen() % 4 * 64;
                pixels[r][c].setRed(red);
                pixels[r][c].setBlue(blue);
                pixels[r][c].setGreen(green);

            }
        }
        return copy;
    }

    public static void main(String[] args) {
//        Picture beach = new Picture ("beach.jpg");
//        beach.explore();
//        Picture copy = testClearLow(beach);
//        copy.explore();

        Picture beach2 = new Picture ("beach.jpg");
        beach2.explore();
        Picture copy2 = testSetLow(beach2, Color.PINK);
//        copy2.explore();
        Picture copy3 = revealPicture(copy2);
        copy3.explore();
    }
}
