package python;

import cutcode.Block;
import cutcode.GraphicalBlock;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import cutcode.InvalidNestException;

import java.util.ArrayList;
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
     * @return an array of 2 points, the top left of the slots of the operand spaces, regardless of whether something
     * is already nested there or not.
     */
    @Override
    public Point2D[] getNestables() {
        Point2D[] ret = new Point2D[nestBoxes.length];
        for (int i = 0; i < nestBoxes.length; i++)
            ret[i] = nestBoxes[i].localToScene(nestBoxes[i].getLayoutBounds().getMinX(), nestBoxes[i].getLayoutBounds().getMinY());
        return ret;
    }


    @Override
    public void nest(int index, GraphicalBlock nest) throws InvalidNestException {
        System.err.println("NEST " + nest + " IN " + this);
        System.err.println("    " + index);
        if (index == 0) {
            VBox box = nestBoxes[0];
            if(nestBoxes[index].getChildren().size() != 0)
                throw new InvalidNestException();
            double incrementWidth = nest.getWidth() - box.getWidth();
            double incrementHeight = nest.getHeight() - box.getHeight();
            increment(box, incrementHeight, incrementWidth);
            box.getChildren().add(nest);

        } else if (index == 1) {
            VBox box = nestBoxes[1];
            double incrementWidth = nest.getWidth() - box.getWidth();
            double incrementHeight = nest.getHeight() - box.getHeight();
            increment(box, incrementHeight, incrementWidth);
            int bindIndex = box.getChildren().size() - 1;
            box.getChildren().add(nest);
            if(bindIndex >= 0) {
                GraphicalBlock prevBlock = (GraphicalBlock) box.getChildren().get(bindIndex);
                prevBlock.setBoundTo(nest);
                nest.setBound(prevBlock);
                nest.layoutXProperty().unbind();
                nest.layoutYProperty().unbind();
                nest.layoutXProperty().bind(prevBlock.layoutXProperty());
                nest.layoutYProperty().bind(prevBlock.layoutYProperty().add(prevBlock.getHeight()));
            }
        } else
            throw new InvalidNestException();
        nest.setNestedIn(this);
    }


    @Override
    public void unnest(VBox box, GraphicalBlock rem) throws InvalidNestException {
        box.getChildren().remove(rem);
        double[] dimensions = nestDimensions.get(box);
        if (dimensions != null && dimensions.length == 2) {
            box.setMinWidth(dimensions[0]);
            box.setMinHeight(dimensions[1]);
        }
        if (nestBoxes[0].getChildren().size() == 0 && nestBoxes[1].getChildren().size() == 0) {
            this.setWidth(200);
            this.setHeight(80);
        }
    }


    @Override
    public ArrayList<GraphicalBlock> getChildBlocks() {
        ArrayList<GraphicalBlock> ret = new ArrayList<>();
        for(VBox box : nestBoxes) {
            for(Node b : box.getChildren()) {
                if(b instanceof GraphicalBlock) {
                    ret.add((GraphicalBlock) b);
                }
            }
        }
        return ret;
    }


}
