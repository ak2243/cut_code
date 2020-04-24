package graphics;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.control.ComboBox;
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
    private VBox op1;
    private VBox op2;

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
        op1 = new VBox();
        op1.setMinWidth(70);
        op1.setMinHeight(30);
        op1.setStyle("-fx-background-color: D96969");
        op2 = new VBox();
        op2.setMinWidth(70);
        op2.setMinHeight(30);
        op2.setStyle("-fx-background-color: D96969");
        String[] choiceOp = {"or", "and", "not"};
        operatorChoice = new ComboBox<String>(FXCollections.observableArrayList( FXCollections.observableArrayList(choiceOp)));
        line.getChildren().addAll(op1, operatorChoice, op2);
        this.getChildren().add(line);
        this.setMinWidth(180);
        this.setStyle("-fx-background-color: D90000");
        this.setPadding(new Insets(10));
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
    public void nest(int index) throws InvalidNestException {

    }
}