package python;

import cutcode.Block;
import cutcode.GraphicalBlock;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import cutcode.InvalidNestException;

import java.util.HashMap;

public class GraphicalIfBlock extends GraphicalBlock {
    private GraphicalBooleanBinaryOperatorBlock condition; // TODO make this OperatorBlock
    private VBox[] nestBoxes;
    private HashMap<VBox, double[]> nestDimensions;

    public GraphicalIfBlock() {

        super(200, 80);

        nestDimensions = new HashMap<>();

        this.setPadding(new Insets(10));
        this.setBackground(new Background(new BackgroundFill(Color.web("#D06201"), CornerRadii.EMPTY, Insets.EMPTY)));


        HBox topLine = new HBox();
        topLine.getChildren().addAll(new Label("if"));
        VBox bottomLine = new VBox();
        bottomLine.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        bottomLine.setMinWidth(140);
        bottomLine.setMinHeight(40);
        double[] bottomLineDimensions = {40.0, 40.0};
        nestDimensions.put(bottomLine, bottomLineDimensions);
        VBox conditionSpace = new VBox();
        conditionSpace.setMinHeight(30);
        conditionSpace.setMinWidth(140);
        double[] conditionSpaceDimensions = {140.0, 30.0};
        nestDimensions.put(conditionSpace, conditionSpaceDimensions);
        conditionSpace.setBackground(
                new Background(new BackgroundFill(Color.web("#D96969"), CornerRadii.EMPTY, Insets.EMPTY)));
        topLine.getChildren().add(conditionSpace);

        this.getChildren().addAll(topLine, bottomLine);

        nestBoxes = new VBox[2];
        nestBoxes[0] = conditionSpace;
        nestBoxes[1] = bottomLine;
    }

    public GraphicalIfBlock(double width, double height) {
        super(width, height);
    }

    @Override
    public Block getLogicalBlock() {
        return null;
    }

    @Override
    public GraphicalBlock cloneBlock() {
        return new GraphicalIfBlock();
    }

    /**
     *
     * @return an array of 2 points, the top left of the slots of the operand spaces, regardless of whether something
     * is already nested there or not.
     */
    @Override
    public Point2D[] getNestables() {
        Point2D[] ret = new Point2D[nestBoxes.length];
        for(int i = 0; i < nestBoxes.length; i++)
            ret[i] = new Point2D(this.getLayoutX() + nestBoxes[i].getLayoutX(), this.getLayoutY() + nestBoxes[i].getLayoutY());
        return ret;
    }


    @Override
    public void nest(int index, GraphicalBlock nest) throws InvalidNestException {
        if(nest.getBoundTo() != null) {
            nest.getBoundTo().setBoundTo(null);
            nest.getBoundTo().layoutXProperty().unbind();
            nest.getBoundTo().layoutYProperty().unbind();
        }
        nest.setNestedIn(this);
        try {
            VBox box = nestBoxes[index];
            double incrementWidth = nest.getWidth() - box.getWidth();
            double incrementHeight = nest.getHeight() - box.getHeight();
            GraphicalBlock curr = nest;
            box.getChildren().add(nest);
            while(curr.getNestedIn() != null) {
                if (incrementWidth > 0) {
                    box.setMaxWidth(box.getWidth() + incrementWidth);
                    this.setMaxWidth(this.getWidth() + incrementWidth);
                }

                if (incrementHeight > 0) {
                    box.setMaxHeight(box.getHeight() + incrementHeight);
                    this.setMaxHeight(this.getHeight() + incrementHeight);
                }
                box = (VBox) curr.getParent();
                curr = curr.getNestedIn();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InvalidNestException();
        }
    }

    @Override
    public void unnest(VBox box, GraphicalBlock rem) throws InvalidNestException {
        box.getChildren().remove(rem);
        double[] dimensions = nestDimensions.get(box);
        if(dimensions != null && dimensions.length == 2) {
            box.setMinWidth(dimensions[0]);
            box.setMinHeight(dimensions[1]);
        }
        if(nestBoxes[0].getChildren().size() == 0 && nestBoxes[1].getChildren().size() == 0) {
            this.setMaxWidth(200);
            this.setMaxHeight(80);
        }
    }




}
