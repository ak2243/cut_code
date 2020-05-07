package cutcode;


import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import factories.GUIFactory;

/**
 * @author Arjun Khanna and Peter Timpane
 */
public class Workspace extends Pane {
    private static final int bindDistance = 15;
    private static final int nestDistance = 15;
    private ArrayList<GraphicalBlock> blocks;
    private BorderPane layout;
    private VBox palette;
    private ScrollPane paletteScroll;
    private GUIFactory guiFactory;

    public Workspace(double width, double height, GUIFactory guiFactory) {
        this.guiFactory = guiFactory;
        blocks = new ArrayList<>();

        this.setMinHeight(height);
        this.setMaxHeight(height);
        this.setMinWidth(width);
        this.setMaxWidth(width);
        layout = new BorderPane();
        this.getChildren().add(layout);
        palette = setupPalette();
        palette.setPrefHeight(palette.getHeight() + 100);

        paletteScroll = new ScrollPane();
        paletteScroll.setContent(palette);
        paletteScroll.setMinHeight(this.getMinHeight());
        paletteScroll.setMaxHeight(this.getMaxHeight());
        paletteScroll.setPrefHeight(this.getMaxHeight());
        paletteScroll.setMinWidth(200);

        layout.setLeft(paletteScroll);

        Button run = new Button("RUN");
        run.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Stage stage = new Stage();
                BorderPane root = new BorderPane();

                try {
                    root.setCenter(new Label(run()));
                    Scene scene = new Scene(root, 400, 400);
                    stage.setScene(scene);
                    stage.show();
                } catch (BlockCodeCompilerErrorException e1) {
                }
            }
        });
        layout.setTop(run);

    }

    public VBox setupPalette() {
        VBox palette = new VBox();
        palette.setSpacing(40);
        palette.setPadding(new Insets(30));
        palette.setMinWidth(200);

        palette.setBackground(
                new Background(new BackgroundFill(Color.rgb(255, 10, 10, 0.8), CornerRadii.EMPTY, Insets.EMPTY)));


        int height = 60;
        for (GraphicalBlock b : guiFactory.getAllBlocks()) {

            CreateHandler handler = new CreateHandler(b);
            b.addEventHandler(MouseEvent.MOUSE_DRAGGED, handler);
            b.addEventHandler(MouseEvent.MOUSE_PRESSED, handler);
            b.addEventHandler(MouseEvent.MOUSE_RELEASED, handler);

            palette.getChildren().add(b);
            height = (int) (height + 40 + b.getHeight());
        }

        return palette;
    }

    private class CreateHandler implements EventHandler<MouseEvent> {

        GraphicalBlock block;

        GraphicalBlock current;

        public CreateHandler(GraphicalBlock b) {
            block = b;
        }

        @Override
        public void handle(MouseEvent e) {
            if(e.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
                current = block.cloneBlock();
                Workspace.this.getChildren().add(current);

                current.setLayoutX(e.getSceneX());
                current.setLayoutY(e.getSceneY());

                BlockHandler handler = new BlockHandler(current);
                current.addEventHandler(MouseEvent.MOUSE_DRAGGED, handler);
                current.addEventHandler(MouseEvent.MOUSE_PRESSED, handler);
                current.addEventHandler(MouseEvent.MOUSE_RELEASED, handler);


            } else if (e.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
                current.setLayoutX(e.getSceneX());
                current.setLayoutY(e.getSceneY());

            } else if (e.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
                if (palette.contains(e.getSceneX(), e.getSceneY())) {
                    Workspace.this.getChildren().remove(current);
                    return;
                }

                blocks.add(current); //Need to add the new block to
                for (GraphicalBlock b : blocks) {
                    if (b == current)
                        continue;
                    Point2D checkPoint = new Point2D(b.getLayoutX(), b.getLayoutY() + b.getMaxHeight());
                    Point2D clickPoint = new Point2D(current.getLayoutX(), current.getLayoutY());
                    double distance = checkPoint.distance(clickPoint);
                    if (b.getBound() == current)
                        continue;
                    if (distance < bindDistance && (b.getBoundTo() == null)) {
                        current.setBound(b);
                        b.setBoundTo(current);
                        break;
                    }

                    Point2D[] nestables = b.getNestables();
                    for (int i = 0; i < nestables.length; i++) {
                        distance = clickPoint.distance(nestables[i]);
                        if (distance < nestDistance) {
                            try {
                                b.nest(i, current);
                            } catch (InvalidNestException invalidNestException) {
                                continue; //TODO action here?
                            }

                        }
                    }
                }

            }

        }

    }

    private class BlockHandler implements EventHandler<MouseEvent> {

        private GraphicalBlock block;
        private double offsetX, offsetY;
        private List<GraphicalBlock> clickSequence;
        private List<GraphicalBlock> ignoring;

        public BlockHandler(GraphicalBlock b) {
            block = b;
        }

        @Override
        public void handle(MouseEvent e) {
            if (block.ignoreStatus() && !e.getEventType().equals(MouseEvent.MOUSE_RELEASED))
                return;
            else
                block.actionIgnored();
            if (e.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
                GraphicalBlock add = block;

                clickSequence = new ArrayList<>();
                while (add != null) {
                    add.toFront();
                    clickSequence.add(add);
                    add = add.getBoundTo();
                }
                // If the block was bound, it unbinds. All the blocks bound to this block will
                // stay that way

                offsetX = e.getSceneX() - block.getLayoutX();
                offsetY = e.getSceneY() - block.getLayoutY();
                // offsets make it so the point you clicked on the block follows your mouse instead of the top left.
                // Looks better

                GraphicalBlock above = block.getBound();
                if (above != null)
                    above.setBoundTo(null); // Allows for things to be bound to "above"

                block.setBound(null);


                //TODO: need to check if the click is on a nested block as opposed to it's parent block
                GraphicalBlock nestedIn = block.getNestedIn();
                if (nestedIn != null) {
                    try {
                        offsetX = 0;
                        offsetY = 0;
                        VBox nestBox = (VBox) block.getParent();
                        for (GraphicalBlock block : clickSequence) {
                            nestedIn.unnest(nestBox, block);
                            Workspace.this.getChildren().add(block);
                            block.setNestedIn(null);
                        }
                        block.setLayoutX(e.getSceneX() - offsetX);
                        block.setLayoutY(e.getSceneY() - offsetY);
                        GraphicalBlock ignore = nestedIn;
                        ignoring = new ArrayList<>();
                        do {
                            ignoring.add(ignore);
                            ignore.ignoreNextAction();
                            ignore = ignore.getNestedIn();
                        } while (ignore != null);
                    } catch (ClassCastException | InvalidNestException castException) {
                        //TODO this should never happen. Maybe institute a failsafe here
                        castException.printStackTrace();
                    }

                }

            } else if (e.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
                block.setLayoutX(e.getSceneX() - offsetX);
                block.setLayoutY(e.getSceneY() - offsetY);


            } else if (e.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
                if (ignoring != null) { //Need to unignore all listeners being ignored, otherwise problems occur
                    for (GraphicalBlock b : ignoring) {
                        b.actionIgnored();
                    }
                    ignoring = null;
                }

                if (palette.contains(e.getSceneX() - offsetX, e.getSceneY() - offsetY)) {
                    for (GraphicalBlock rem : clickSequence) {
                        remove(rem);
                    }
                    return;
                } else {
                    for (GraphicalBlock b : blocks) {
                        if (b == block)
                            continue;
                        Point2D clickPoint = new Point2D(block.getLayoutX(), block.getLayoutY());
                        Point2D point = new Point2D(b.getLayoutX(), b.getLayoutY() + b.getMaxHeight());
                        double distance = point.distance(new Point2D(block.getLayoutX(), block.getLayoutY()));
                        if (b == block || b.getBound() == block)
                            continue;
                        if (distance < bindDistance && (b.getBoundTo() == null)) {
                            b.setBoundTo(block);
                            block.setBound(b);
                            block.layoutXProperty().bind(b.layoutXProperty());
                            block.layoutYProperty().bind(b.layoutYProperty().add(b.getHeight()));
                            break;
                        }

                        Point2D[] nestables = b.getNestables();
                        boolean cycle = false;
                        GraphicalBlock cycleCheck = b;
                        while (cycleCheck.getNestedIn() != null && !cycle) {
                            cycle = b == block;
                            cycleCheck = cycleCheck.getNestedIn();
                        }
                        if (!cycle) {
                            for (int i = 0; i < nestables.length; i++) {
                                distance = clickPoint.distance(nestables[i]);

                                if (distance < nestDistance) {
                                    //This needs to be done before returning
                                    try {
                                        b.nest(i, block);
                                        for (GraphicalBlock add : clickSequence) {
                                            if (add == block)
                                                continue;
                                            try {
                                                b.nest(i, add);
                                            } catch (InvalidNestException innerNestException) {
                                                add.getBound().setBoundTo(null);
                                                add.setBound(null);
                                                add.layoutXProperty().unbind();
                                                add.layoutYProperty().unbind();
                                                add.setLayoutX(add.getLayoutX() + 10);
                                                add.setLayoutY(add.getLayoutY() + 10);
                                                break;
                                            }
                                        }
                                    } catch (InvalidNestException | IllegalArgumentException invalidNestException) { //In case it finds a cycle
                                        continue;
                                    }

                                    return;

                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * O(infinity)
     *
     * @return - the output from the program
     * @throws BlockCodeCompilerErrorException if the block code doesn't compile
     */
    private String run() throws BlockCodeCompilerErrorException {
        return "asfd";
    }

    private void remove(GraphicalBlock rem) {
        blocks.remove(rem);
        this.getChildren().remove(rem);
        ArrayList<GraphicalBlock> rems = rem.getChildBlocks();
        if (rems != null) {
            for (GraphicalBlock recurseRem : rems)
                remove(recurseRem);
        }
    }

}
