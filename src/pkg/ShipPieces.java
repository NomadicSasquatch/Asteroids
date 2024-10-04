package pkg;
import java.util.ArrayList;
import java.awt.*;

public class ShipPieces {
    private ArrayList<ShipPiece> shipPieces;

    public ShipPieces() {
        shipPieces = new ArrayList<>();
    }

    public void moveShipPieces() {
        for(int i = 0; i < shipPieces.size(); i++) {
            ShipPiece shipPiece = shipPieces.get(i);
            shipPiece.moveShipPiece();
        }
    }

    public void splitPieces(Ship ship) {
        shipPieces.add(new ShipPiece(ship.xCoordinate, ship.yCoordinate, ship.angle - 60));
        shipPieces.add(new ShipPiece(ship.xCoordinate, ship.yCoordinate, ship.angle + 60));
        shipPieces.add(new ShipPiece(ship.xCoordinate, ship.yCoordinate, ship.angle + 180));
    }

    public void drawShipPieces(Graphics g) {
        for(int i = 0; i < shipPieces.size(); i++) {
            ShipPiece shipPiece = shipPieces.get(i);
            shipPiece.drawShipPiece(g, i);
        }
    }
}
