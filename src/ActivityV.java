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
        int height = base1.getHeight();
        if(height > base2.getHeight()) height = base2.getHeight();
        int width = base1.getWidth();
        if(width > base2.getWidth()) width = base2.getWidth();

        Picture copy = new Picture(height,width);
        Pixel[][] copyPixels = copy.getPixels2D();
        Pixel[][] base1Pixels = base1.getPixels2D();
        Pixel[][] base2Pixels = base2.getPixels2D();
        for (int row = 0; row < copy.getHeight(); row ++)
            for (int col = 0; col < copy.getWidth(); col ++){
                copyPixels[row][col].setRed((base2Pixels[row][col].getRed() + base1Pixels[row][col].getRed()) / 2 );
                copyPixels[row][col].setGreen((base2Pixels[row][col].getGreen() + base1Pixels[row][col].getGreen()) / 2 );
                copyPixels[row][col].setBlue((base2Pixels[row][col].getBlue() + base1Pixels[row][col].getBlue()) / 2 );
            }
        return copy;

    }

    public static Picture plaid(Picture a, Picture b, int size){
        if (!Steganography.canHide(a,b) || !Steganography.canHide(b,a)) return a;

        Picture aCopy = new Picture(a);
        Pixel[][] aPixels = aCopy.getPixels2D();
        Pixel[][] bPixels = b.getPixels2D();
        int checkcol = 0;
        int checkrow = 0;


        for(int row = 0; row < a.getHeight(); row ++) {
            checkcol = 0;
            if(row/size%2==0) checkcol = size;
//
            for (int col = 0; col < a.getWidth(); col++, checkcol++) {
                if (checkcol / size % 2 == 0){
                        aPixels[row][col].setRed(bPixels[row][col].getRed());
                        aPixels[row][col].setGreen(bPixels[row][col].getGreen());
                        aPixels[row][col].setBlue(bPixels[row][col].getBlue());
                }

            }
        }
        return aCopy;
    }
    public static Picture superPlaid(Picture a, Picture b, Picture c, Picture d, int size){
        if (!(Steganography.canHide(a,b) && Steganography.canHide(a,c) && Steganography.canHide(a,d) && Steganography.canHide(b,c) && Steganography.canHide(b,d) && Steganography.canHide(c,d)) ) return a;

        Picture aCopy = new Picture(a);
        Pixel[][] aPixels = aCopy.getPixels2D();
        Pixel[][] bPixels = b.getPixels2D();
        Pixel[][] cPixels = c.getPixels2D();
        Pixel[][] dPixels = d.getPixels2D();
        int checkcol = 0;
        int checkrow = 0;


        for(int row = 0; row < a.getHeight(); row ++) {
            checkcol = 0;
            if(row/size%4==1) checkcol = size;
            if(row/size%4==2) checkcol = 2*size;
            if(row/size%4==3) checkcol = 3*size;

//
            for (int col = 0; col < a.getWidth(); col++, checkcol++) {
                if (checkcol / size % 4 == 1){
                    aPixels[row][col].setRed(bPixels[row][col].getRed());
                    aPixels[row][col].setGreen(bPixels[row][col].getGreen());
                    aPixels[row][col].setBlue(bPixels[row][col].getBlue());
                }
                if (checkcol / size % 4 == 2){
                    aPixels[row][col].setRed(cPixels[row][col].getRed());
                    aPixels[row][col].setGreen(cPixels[row][col].getGreen());
                    aPixels[row][col].setBlue(cPixels[row][col].getBlue());
                }
                if (checkcol / size % 4 == 3){
                    aPixels[row][col].setRed(dPixels[row][col].getRed());
                    aPixels[row][col].setGreen(dPixels[row][col].getGreen());
                    aPixels[row][col].setBlue(dPixels[row][col].getBlue());
                }

            }
        }
        return aCopy;
    }

    public static Picture Plaid4(Picture a, Picture b, Picture c, Picture d, int size){
        if (!(Steganography.canHide(a,b) && Steganography.canHide(a,c) && Steganography.canHide(a,d) && Steganography.canHide(b,c) && Steganography.canHide(b,d) && Steganography.canHide(c,d)) ) return a;

        Picture aCopy = new Picture(a);
        Pixel[][] aPixels = aCopy.getPixels2D();
        Pixel[][] bPixels = b.getPixels2D();
        Pixel[][] cPixels = c.getPixels2D();
        Pixel[][] dPixels = d.getPixels2D();
        int checkcol = 0;
        int checkrow = 0;
        boolean switchrow = false;
        int switchcount = 0;


        for(int row = 0; row < a.getHeight(); row ++) {
            checkcol = 0;
            if(row/size%2==1) checkcol = size;
            if(row/size > switchcount){
                switchcount++;
                switchrow=!switchrow;
            }
//
            for (int col = 0; col < a.getWidth(); col++, checkcol++) {
                if (switchrow) {
                    if (checkcol / size % 2 == 0) {
                        aPixels[row][col].setRed(bPixels[row][col].getRed());
                        aPixels[row][col].setGreen(bPixels[row][col].getGreen());
                        aPixels[row][col].setBlue(bPixels[row][col].getBlue());
                    }
                }
                else {
                    if (checkcol / size % 2 == 0) {
                        aPixels[row][col].setRed(cPixels[row][col].getRed());
                        aPixels[row][col].setGreen(cPixels[row][col].getGreen());
                        aPixels[row][col].setBlue(cPixels[row][col].getBlue());
                        }
                    if (checkcol / size % 2 == 1) {
                        aPixels[row][col].setRed(dPixels[row][col].getRed());
                        aPixels[row][col].setGreen(dPixels[row][col].getGreen());
                        aPixels[row][col].setBlue(dPixels[row][col].getBlue());
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

    public static Picture extraImage(Picture a, Picture b){
        if (a.getHeight() > b.getHeight() && a.getWidth() > b.getWidth()) {
            Picture copyA = new Picture(a);
            Pixel[][] aPixels = copyA.getPixels2D();
            Pixel[][] bPixels = b.getPixels2D();

            for (int row = 0; row < b.getHeight(); row++)
                for (int col = 0; col < b.getWidth(); col++) {
                    aPixels[row][col].setRed(bPixels[row][col].getRed());
                    aPixels[row][col].setGreen(bPixels[row][col].getGreen());
                    aPixels[row][col].setBlue(bPixels[row][col].getBlue());
                }
            return copyA;
        }
        else if (b.getHeight() > a.getHeight() && b.getWidth() > a.getWidth()){
            Picture copyB = new Picture(b);
            Pixel[][] bPixels = copyB.getPixels2D();
            Pixel[][] aPixels = a.getPixels2D();

            for (int row = 0; row < a.getHeight(); row++)
                for (int col = 0; col < a.getWidth(); col++) {
                    bPixels[row][col].setRed(aPixels[row][col].getRed());
                    bPixels[row][col].setGreen(aPixels[row][col].getGreen());
                    bPixels[row][col].setBlue(aPixels[row][col].getBlue());
                }
            return copyB;
        }

        else{
            return a;
        }
    }


    public static void main(String[] args) {

        Picture koala = new Picture("koala.jpg");
        Picture snowman = new Picture("snowman.jpg");
        Picture island = new Picture("CumberlandIsland.jpg");
        Picture hall = new Picture("femaleLionAndHall.jpg");
        Picture gorge = new Picture("gorge.jpg");
        Picture swan = new Picture("swan.jpg");
        Picture blueMotorcycle = new Picture("blueMotorcycle.jpg");
        Picture redMotorcycle = new Picture("redMotorcycle.jpg");
        Picture blueMark = new Picture("blue-mark.jpg");
        Picture moon = new Picture("moon-surface.jpg");
        Picture seagull = new Picture("seagull.jpg");


//        blurTimes(50, koala).show();
//        invertColor(snowman).show();
//        mixedImage(hall, island).show();
//        plaid(gorge, swan, 1).show();
        mixedImage(swan,gorge).show();
//        plaid(gorge, swan, 10).show();
//        plaid(gorge, swan, 50).show();
//        plaid(gorge, swan, 100).show();
//        superPlaid(blueMark, redMotorcycle, blueMotorcycle, moon, 70).show();
//        Plaid4(blueMark, redMotorcycle, blueMotorcycle, moon, 70).show();
//        randomImage(gorge, swan, 2).show(); //1 in frequency chance of pixel being from picture b
//        randomImage(gorge, swan, 5).show(); //1 in frequency chance of pixel being from picture b
//        mixedImage(seagull, blurTimes(50, invertColor(extraImage(seagull, gorge)))).show();

//        for(int i = 1; i<= 100; i++){
//            plaid(swan,gorge,i).show();
//        }
    }
}