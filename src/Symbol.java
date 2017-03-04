

/**
 *
 * @author Brion Mario, Apparecium Labs
 */

public class Symbol implements ISymbol{
    String Image; // stores path of the image
    int Value; // score of the symbol

    public Symbol(String Image,int Value){
        this.Image = Image;
        this.Value = Value;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public int getValue() {
        return Value;
    }

    public void setValue(int value) {
        Value = value;
    }

    public static int matchCheck(Symbol symbol1, Symbol symbol2,Symbol symbol3) {
        int match;

        if(symbol1.getValue()==symbol2.getValue() && symbol2.getValue()==symbol3.getValue()){
            match = 3;
        }else if(symbol1.getValue()==symbol2.getValue()){
            match = 2;
        }else if(symbol2.getValue()==symbol3.getValue()){
            match = 2;
        }else if(symbol1.getValue()==symbol3.getValue()){
            match = 2;
        }else{
            match =0;
        }
        return match;
    }

    @Override
    public String toString() {
        return Image +','+ Value;
    }
}
