import java.awt.Color;
import java.util.ArrayList;

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

    public static boolean canHide(Picture source, Picture secret){
        return source.getHeight() >= secret.getHeight() && source.getWidth() >= secret.getWidth();
    }
    public static Picture hidePicture(Picture source, Picture secret){
        if(!canHide(source, secret)) return source;

        Pixel[][] sourceA = source.getPixels2D();
        Pixel[][] secretA = secret.getPixels2D();

        for(int r = 0; r < sourceA.length; r++){
            for(int c = 0; c < sourceA[0].length; c++){
                setLow(sourceA[r][c], secretA[r][c].getColor());
            }
        }

        return source;
    }
    public static Picture hidePicture(Picture source, Picture secret, int startRow, int startColumn){
        if(!canHide(source, secret)) return source;
        Picture copy = new Picture(source);
        Pixel[][] sourceA = copy.getPixels2D();
        Pixel[][] secretA = secret.getPixels2D();

        for(int r = startRow; r < secretA.length + startRow; r++){
            for(int c = startColumn; c < secretA[0].length + startColumn ; c++){
                setLow(sourceA[r][c], secretA[r-startRow][c-startColumn].getColor());
            }
        }

        return copy;
    }


    public static boolean isSame(Picture s, Picture p){
        Pixel[][] sa = s.getPixels2D();
        Pixel[][] pa = p.getPixels2D();

        if(!(sa.length == pa.length && sa[0].length == pa[0].length)) return false;

        for(int i = 0; i < sa.length; i++)
            for(int c = 0; c < sa[0].length; c++){
                if(!(sa[i][c].getColor().equals(pa[i][c].getColor()))) return false;
            }
        return true;
    }
    public static ArrayList<Point> findDifferences(Picture p, Picture s){
        ArrayList<Point> diff = new ArrayList<Point>();
        if(isSame(p,s)) return diff;
        Pixel[][] pa = p.getPixels2D();
        Pixel[][] sa = s.getPixels2D();
        if(pa.length != sa.length || pa[0].length != sa[0].length) return diff;

        for(int r = 0; r < pa.length; r++)
            for(int c = 0; c < pa[0].length; c++){
                if(!pa[r][c].getColor().equals(sa[r][c].getColor()))
                    diff.add(new Point(r,c));
            }
        return diff;
    }
    public static Picture showDifferentArea(Picture ss, ArrayList<Point> p){
        Picture s = new Picture(ss);
        if(p.isEmpty()) return s;
        int x = s.getWidth(), X = 0, y = s.getWidth(), Y = 0;
        for(Point a: p){
            if(a.getX()>X) X=a.getX();
            if(a.getX()<x) x=a.getX();
            if(a.getY()>Y) Y=a.getY();
            if(a.getY()<y) y=a.getY();
        }
        Pixel[][] pix = s.getPixels2D();
        for(int r = x; r<=X; r++) {
            if (r == x || r == X)
                for (int c = Y; c >= y; c--)
                    pix[r][c].setColor(Color.BLACK);
            pix[r][y].setColor(Color.BLACK);
            pix[r][Y].setColor(Color.BLACK);
        }
        return s;
    }
    public static ArrayList<Integer> encodeString(String s){
        s = s.toUpperCase();
        String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        ArrayList<Integer> result = new ArrayList<Integer>();

        for(int i = 0; i < s.length(); i++)
            result.add(alpha.indexOf(s.charAt(i)) + 1);

        result.add(0);
        return result;
    }
    private static String decodeString(ArrayList<Integer> s){
        String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String result = "";

        for(int i = 0; i < s.size(); i++) {
            if (s.get(i) == 0) break;
            result = result + alpha.indexOf(s.get(i) - 1);
        }

        return result;
    }

//    public static int[] getBitPairs(int num){
//    }


    public static void main(String[] args) {
//        Picture beach = new Picture ("beach.jpg");
//        beach.explore();
//        Picture copy = testClearLow(beach);
//        copy.explore();

//        Picture beach2 = new Picture ("beach.jpg");
//        beach2.explore();
//        Picture copy2 = testSetLow(beach2, Color.PINK);
////        copy2.explore();
//        Picture copy3 = revealPicture(copy2);
//        copy3.explore();

//        Picture swan = new Picture("swan.jpg");
//        Picture swan2 = new Picture("swan.jpg");
//        Picture gore = new Picture("gorge.jpg");
//        Picture gore2 = new Picture("gorge.jpg");
//        swan.explore();
//        Picture hiddenSwan = hidePicture(swan, gore);
//        hiddenSwan.explore();
//        Picture revealedSwan = revealPicture(hiddenSwan);
//        revealedSwan.explore();

//        Picture beach = new Picture ("beach.jpg");
//        Picture robot = new Picture("robot.jpg");
//        Picture flower1 = new Picture("flower1.jpg");
//        beach.explore();
//
//        Picture hidden1 = hidePicture(beach, robot, 65, 208);
//        Picture hidden2 = hidePicture(hidden1, flower1, 280,110);
//        hidden2.explore();
//        revealPicture(hidden2).explore();

//
//        Picture arch = new Picture("arch.jpg");
//        revealPicture(arch).explore();
//        Picture koala = new Picture("koala.jpg");
//        Picture robot1 = new Picture("robot.jpg");
//        ArrayList<Point> pointList = findDifferences(arch, arch);
//        System.out.println("Pointlist after comparing two identical pictures has a size of " + pointList.size());
//        pointList = findDifferences(arch, koala);
//        System.out.println("Pointlist after comparing two different sized pictures has a size of " + pointList.size());
//        Picture arch2 = hidePicture(arch, robot1, 65, 102);
//        pointList = findDifferences(arch, arch2);
//        System.out.println("Pointlist after hiding a picture has a size of " + pointList.size());
//        arch.show();
//        arch2.show();
//        revealPicture(arch).explore();
//        revealPicture(arch2).explore();

        Picture hall = new Picture ("femaleLionAndHall.jpg");
        Picture robot2 = new Picture ("robot.jpg");
        Picture flower2 = new Picture ("flower1.jpg");
        Picture hall2 = hidePicture (hall, robot2, 50, 300);
        Picture hall3 = hidePicture (hall2, flower2, 115, 275);
        hall3.explore();
        if(!isSame(hall, hall3)) {
            Picture hall4 = showDifferentArea(hall, findDifferences(hall, hall3));
            hall4.show();
            Picture unhiddenHall3 = revealPicture(hall3);
            unhiddenHall3.show();
        }

    }
}
