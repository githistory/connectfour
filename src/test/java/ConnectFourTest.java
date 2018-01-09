import org.junit.Before;
import org.junit.Test;

public class ConnectFourTest {
    private ConnectFour cf;

    @Before
    public void setup() {
        cf = new ConnectFour(7, 6);
    }

    @Test
    public void moveTest() {
        cf.move('R', 3);
        assert(cf.getCharAt(3,0)=='R');
    }

    @Test
    public void horizontalTest() {
        cf.move('R', 3);
        cf.move('G', 4);
        cf.move('R', 3);
        cf.move('G', 4);
        assert(cf.horizontal().equals("   RG  "));
    }

    @Test
    public void verticalTest() {
        cf.move('R', 3);
        cf.move('G', 3);
        cf.move('R', 3);
        cf.move('G', 3);
        assert(cf.vertical().equals("RGRG  "));
    }

    @Test
    public void slashTest() {
        cf.move('R', 3);
        cf.move('G', 4);
        cf.move('R', 4);
        assert(cf.slash().equals("RR  "));
    }

    @Test
    public void backslashTest() {
        cf.move('R', 3);
        cf.move('G', 3);
        cf.move('R', 4);
        assert(cf.backslash().equals("   GR"));
    }

    @Test
    public void isWinningMoveTest() {
        cf.move('R', 3);
        cf.move('R', 4);
        cf.move('R', 5);
        // not winning
        assert(!cf.isWinningMove());

        cf.move('R', 6);
        // winning
        assert(cf.isWinningMove());
    }
}
