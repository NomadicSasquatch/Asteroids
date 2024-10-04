package pkg;
import java.util.ArrayList;
import java.awt.*;

public class ShipPieces {
    ArrayList<ShipPiece> shipPieces;

    public ShipPieces() {
        shipPieces = new ArrayList<>();
    }

    public void moveShipPieces() {
        for(int i = 0; i < shipPieces.size(); i++) {
            ShipPiece shipPiece = shipPieces.get(i);
            shipPiece.moveShipPiece();
        }
    }

    public void drawShipPieces(Graphics g) {
        for(int i = 0; i < shipPieces.size(); i++) {
            ShipPiece shipPiece = shipPieces.get(i);
            shipPiece.drawShipPiece(g, i);
        }
    }
}
