public class ActivityV {

                                                ///////////////////
                                                ///SINGLE IMAGES///
                                                ///////////////////

    public static Picture blurImage(Picture base){
        Picture blur = new Picture(base);

        Pixel[][] basepix = base.getPixels2D();
        Pixel[][] blurpix = blur.getPixels2D();

        for(int row = 0; row < base.getHeight(); row++)
            for(int col = 0; col < base.getWidth(); col++) {
                int avgred = 0, avggreen = 0, avgblue =0;
                int numSquares = 0;

                if (row != 0){
                    avgred += basepix[row-1][col].getRed();
                    avggreen += basepix[row-1][col].getGreen();
                    avgblue += basepix[row-1][col].getBlue();
                    numSquares++;
                }
                if (row != base.getHeight()-1){
                    avgred += basepix[row+1][col].getRed();
                    avggreen += basepix[row+1][col].getGreen();
                    avgblue += basepix[row+1][col].getBlue();
                    numSquares++;
                }
                if (col != 0){
                    avgred += basepix[row][col-1].getRed();
                    avggreen += basepix[row][col-1].getGreen();
                    avgblue += basepix[row][col-1].getBlue();
                    numSquares++;
                }
                if (col != base.getWidth()-1){
                    avgred += basepix[row][col+1].getRed();
                    avggreen += basepix[row][col+1].getGreen();
                    avgblue += basepix[row][col+1].getBlue();
                    numSquares++;
                }
                //Set to average
                avgred += basepix[row][col].getRed();
                avggreen += basepix[row][col].getGreen();
                avgblue += basepix[row][col].getBlue();
                numSquares++;

                avgred/=numSquares;
                avggreen/=numSquares;
                avgblue/=numSquares;

                blurpix[row][col].setRed(avgred);
                blurpix[row][col].setGreen(avggreen);
                blurpix[row][col].setBlue(avgblue);
            }

        return blur;
    }

    public static Picture blurTimes(int blurTime, Picture base){
        if(blurTime == 0) return base;
        Picture copy = new Picture(base);
        return blurTimes(blurTime-1, blurImage(copy));
    }

    public static Picture invertColor(Picture base) {
        Picture copy = new Picture(base);
        Pixel[][] copyPixels = copy.getPixels2D();
        for (int row = 0; row < copy.getHeight(); row++)
            for (int col = 0; col < copy.getWidth(); col++) {
                copyPixels[row][col].setRed(255 - copyPixels[row][col].getRed());
                copyPixels[row][col].setGreen(255 - copyPixels[row][col].getGreen());
                copyPixels[row][col].setBlue(255 - copyPixels[row][col].getBlue());
            }
        return copy;
    }
                                ///////////////////
                                //MULTIPLE IMAGES//
                                ///////////////////

    public static Picture mixedImage(Picture base1, Picture base2){


        if (Steganography.canHide(base1, base2)){
            Picture copy = new Picture(base2);
            Pixel[][] copyPixels = copy.getPixels2D();
            Pixel[][] base1Pixels = base1.getPixels2D();
            for (int row = 0; row < base2.getHeight(); row ++)
                for (int col = 0; col < base2.getWidth(); col ++){
                    copyPixels[row][col].setRed((copyPixels[row][col].getRed() + base1Pixels[row][col].getRed()) / 2 );
                    copyPixels[row][col].setGreen((copyPixels[row][col].getGreen() + base1Pixels[row][col].getGreen()) / 2 );
                    copyPixels[row][col].setBlue((copyPixels[row][col].getBlue() + base1Pixels[row][col].getBlue()) / 2 );
                }
            return copy;
        }
        else if (Steganography.canHide(base2, base1)) {
            Picture copy = new Picture(base1);
            Pixel[][] copyPixels = copy.getPixels2D();
            Pixel[][] base2Pixels = base2.getPixels2D();
            for (int row = 0; row < base1.getHeight(); row++)
                for (int col = 0; col < base1.getWidth(); col++) {
                    copyPixels[row][col].setRed((copyPixels[row][col].getRed() + base2Pixels[row][col].getRed()) / 2);
                    copyPixels[row][col].setGreen((copyPixels[row][col].getGreen() + base2Pixels[row][col].getGreen()) / 2);
                    copyPixels[row][col].setBlue((copyPixels[row][col].getBlue() + base2Pixels[row][col].getBlue()) / 2);
                }
            return copy;
        }
        return base1;
    }

    public static Picture plaidImage(Picture a, Picture b, int size){
        if (!Steganography.canHide(a,b) || !Steganography.canHide(b,a)) return a;

        Picture aCopy = new Picture(a);
        Pixel[][] aPixels = aCopy.getPixels2D();
        Pixel[][] bPixels = b.getPixels2D();
        int check = 0;

        boolean switchpic = false;
        boolean switchrow = false;


        for(int row = 0; row < a.getHeight(); row ++) {
            check = 0;
            if(row%size==0) switchrow = true;
            if(switchrow) check = size;
//            System.out.println(switchrow);
            for (int col = 0; col < a.getWidth(); col++, check++) {
                if (check % size == 0) switchpic = !switchpic;
                if (switchpic) {
                    {
                        aPixels[row][col].setRed(bPixels[row][col].getRed());
                        aPixels[row][col].setGreen(bPixels[row][col].getGreen());
                        aPixels[row][col].setBlue(bPixels[row][col].getBlue());
                    }
                }

            }
        }
        return aCopy;
    }

    public static Picture randomImage(Picture a, Picture b, int frequency){
        if (!Steganography.canHide(a,b) || !Steganography.canHide(b,a)) return a;

        Picture copy = new Picture(a);
        Pixel[][] copyPixels = copy.getPixels2D();
        Pixel[][] bPixels = b.getPixels2D();

        for(int row = 0; row < a.getHeight(); row++)
            for(int col = 0; col < a.getWidth(); col++){
                int rand = (int) (Math.random()*frequency);

                if (rand == 1){
                    copyPixels[row][col].setRed((bPixels[row][col].getRed()));
                    copyPixels[row][col].setGreen((bPixels[row][col].getGreen()));
                    copyPixels[row][col].setBlue((bPixels[row][col].getBlue()));
                }
            }
        return copy;
    }

//    public static Picture extraImage(Picture a, Picture b){
//        //Will print the bigger image with the smaller image taking up the space in the bigger image it can
//    }


    public static void main(String[] args) {
//        Picture hottie = new Picture("raghav2.jpg");
//        Picture babe = new Picture("daddyMihir.jpg");
//        Picture jo = new Picture("joe.jpg");

        Picture gore = new Picture("gorge.jpg");
        Picture swan = new Picture("swan.jpg");

//        gore.show();
//        invertColor(gore).show();
//        blurImage(gore).show();
//        blurTimes(50,gore).show();
//        mixedImage(gore, swan).show();
        for(int i = 10; i<= 20; i++){
            plaidImage(gore,swan,i).show();
        }
//        plaidImage(gore,swan,13).show();
//        randomImage(gore, swan, 5).show(); //1 in frequency chance of pixel being from picture b
    }
}