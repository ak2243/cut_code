package graphics;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.control.ComboBox;
import javafx.scene.effect.Light;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logicalBlocks.Block;

/**
 * @author Arjun Khanna
 */
public class BooleanBinaryOperatorBlock extends GraphicalBlock {
    private GraphicalBlock leftOperand;
    private GraphicalBlock rightOperand;
    private ComboBox<String> operatorChoice;
    private VBox[] nestBoxes;

    /**
     * @return the operand to the left of the operator
     * @apiNote O(1)
     * @author Arjun Khanna
     */

    public BooleanBinaryOperatorBlock() {
        this(200, 40);
    }

    public BooleanBinaryOperatorBlock(int width, int height) {
        super(width, height);
        HBox line = new HBox();
        nestBoxes = new VBox[2];
        VBox op1 = new VBox();
        op1.setMinWidth(50);
        op1.setMinHeight(30);
        op1.setStyle("-fx-background-color: D96969");
        VBox op2 = new VBox();
        op2.setMinWidth(50);
        op2.setMinHeight(30);
        op2.setStyle("-fx-background-color: D96969");
        String[] choiceOp = {"or", "and", "not"};
        operatorChoice = new ComboBox<String>(FXCollections.observableArrayList(FXCollections.observableArrayList(choiceOp)));
        operatorChoice.setMinWidth(75);
        line.getChildren().addAll(op1, operatorChoice, op2);
        this.getChildren().add(line);
        this.setMinWidth(200);
        this.setStyle("-fx-background-color: D90000");
        this.setPadding(new Insets(8));

        nestBoxes[0] = op1;
        nestBoxes[1] = op2;
    }


    @Override
    public Block getLogicalBlock() {
        return null;
    }

    @Override
    public GraphicalBlock cloneBlock() {
        return new BooleanBinaryOperatorBlock();
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
        System.err.println("NEST");
        try {
            VBox box = nestBoxes[index];
            double incrementWidth = nest.getWidth() - box.getWidth();
            if (incrementWidth > 0) {
                box.setMaxWidth(box.getMinWidth() + incrementWidth);
                this.setMaxWidth(this.getWidth() + incrementWidth);
            }
            double incrementHeight = nest.getHeight() - box.getHeight();
            if (incrementHeight > 0) {
                box.setMinHeight(box.getHeight() + incrementHeight);
                this.setMaxHeight(this.getHeight() + incrementHeight);
            }
            box.getChildren().add(nest);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InvalidNestException();
        }
    }
}