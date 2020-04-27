package graphics;

import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import logicalBlocks.Block;

public class IfBlock extends GraphicalBlock{
    private BooleanBinaryOperatorBlock condition; // TODO make this OperatorBlock
    private VBox bottomLine;
    private VBox conditionSpace;

    public IfBlock() {
        super(200, 80);

        this.setPadding(new Insets(10));
        this.setBackground(new Background(new BackgroundFill(Color.web("#D06201"), CornerRadii.EMPTY, Insets.EMPTY)));

        HBox topLine = new HBox();
        topLine.getChildren().addAll(new Label("if"));
        bottomLine = new VBox();
        bottomLine.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        bottomLine.setMinWidth(40);
        bottomLine.setMinHeight(40);
        conditionSpace = new VBox();
        conditionSpace.setMinHeight(30);
        conditionSpace.setMinWidth(140);
        conditionSpace.setBackground(
                new Background(new BackgroundFill(Color.web("#D96969"), CornerRadii.EMPTY, Insets.EMPTY)));
        topLine.getChildren().add(conditionSpace);

        this.getChildren().addAll(topLine, bottomLine);
        bottomLine.setPadding(new Insets(5));
    }

    public IfBlock(double width, double height) {
        super(width, height);
    }

    @Override
    public Block getLogicalBlock() {
        return null;
    }

    @Override
    public GraphicalBlock cloneBlock() {
        return null;
    }

    @Override
    public Point2D[] getNestables() {
        return new Point2D[0];
    }

    @Override
    public void nest(int index, GraphicalBlock nest) throws InvalidNestException {

    }
}
