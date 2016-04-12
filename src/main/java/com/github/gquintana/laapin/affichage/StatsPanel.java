package com.github.gquintana.laapin.affichage;

import com.github.gquintana.laapin.joueur.Direction;
import com.github.gquintana.laapin.joueur.TypeAction;
import com.github.gquintana.laapin.moteur.Lapin;
import com.github.gquintana.laapin.moteur.ResultatAction;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.lang.reflect.Field;
import java.util.List;

public class StatsPanel extends VBox {
    private static final Insets INSETS = new Insets(1D, 1D, 1D, 1D);
    private ObservableList<Lapin> lapinsList = FXCollections.observableArrayList();
    private ObservableList<ResultatAction> actionsList = FXCollections.observableArrayList();
    private final TableView<ResultatAction> actionsTable;
    private final TableView<Lapin> lapinsTable;

    public StatsPanel() {
        lapinsTable = new TableView<>(lapinsList);
        lapinsTable.getColumns().add(tableColumn("Lapin", "nom", String.class));
        TableColumn<Lapin, Color> colorColumn = tableColumn("C", "couleur", Color.class);
        colorColumn.setMaxWidth(32D);
        colorColumn.setCellFactory(new Callback<TableColumn<Lapin, Color>, TableCell<Lapin, Color>>() {
            @Override
            public TableCell<Lapin, Color> call(TableColumn<Lapin, Color> column) {
                return new TableCell<Lapin, Color>(){
                    @Override
                    protected void updateItem(Color couleur, boolean empty) {
                        setText("");
                        if (couleur != null) {
                            setBackground(new Background(new BackgroundFill(couleur, null, INSETS)));
                        }
                    }
                };
            }
        });
        lapinsTable.getColumns().add(colorColumn);
        lapinsTable.getColumns().add(tableColumn("Score", "score", Integer.class));
        lapinsTable.getColumns().add(tableColumn("Energie", "energie", Integer.class));

        actionsTable = new TableView<>(actionsList);
        TableColumn<ResultatAction, Lapin> lapinColumn = tableColumn("Lapin", "lapin", Lapin.class);
        lapinColumn.setCellFactory(new Callback<TableColumn<ResultatAction, Lapin>, TableCell<ResultatAction, Lapin>>() {
            @Override
            public TableCell<ResultatAction, Lapin> call(TableColumn<ResultatAction, Lapin> param) {
                return new TableCell<ResultatAction, Lapin>() {
                    @Override
                    protected void updateItem(Lapin item, boolean empty) {
                        if (item == null) {
                            super.updateItem(item, empty);
                        } else {
                            if (item.couleur != null) {
                                setTextFill(item.couleur);
                            }
                            setText(item.nom);
                        }
                    }
                };
            }
        });
        actionsTable.getColumns().add(lapinColumn);
        actionsTable.getColumns().add(tableColumn("Action", "action.type", TypeAction.class));
        actionsTable.getColumns().add(tableColumn("Direction", "action.direction", Direction.class));
        actionsTable.getColumns().add(tableColumn("Message", "message", String.class));

        getChildren().addAll(lapinsTable, actionsTable);
    }

    public void setLapinsList(List<Lapin> lapins) {
        lapinsList.setAll(lapins);
    }

    public void addAction(ResultatAction resultatAction) {
        actionsList.add(resultatAction);
        actionsTable.scrollTo(actionsList.size() - 1);
    }

    private static <S, T> TableColumn<S, T> tableColumn(String titre, String champ, Class<T> type) {
        TableColumn<S, T> tableColumn = new TableColumn<>(titre);
        tableColumn.setCellValueFactory(new FieldValueFactory<>(champ, type));
        return tableColumn;
    }

    private static class FieldValueFactory<S, T> implements Callback<TableColumn.CellDataFeatures<S,T>, ObservableValue<T>> {
        private final String[] fieldPath;
        private final Class<T> fieldType;

        public FieldValueFactory(String name, Class<T> fieldType) {
            this.fieldType = fieldType;
            this.fieldPath = name.split("\\.");
        }

        @Override
        public ObservableValue<T> call(TableColumn.CellDataFeatures<S, T> param) {
            return getField(param.getValue());
        }

        private ObservableValue<T> getField(S value) {
            Object current = value;
            for(String field : fieldPath) {
                if (value == null) {
                    break;
                } else {
                    try {
                        Field reflectField = current.getClass().getDeclaredField(field);
                        if (!reflectField.isAccessible()) {
                            reflectField.setAccessible(true);
                        }
                        current = reflectField.get(current);
                    } catch (ReflectiveOperationException e) {
                        System.err.println(current.getClass()+"."+field);
                        e.printStackTrace();
                    }
                }
            }
            return new ReadOnlyObjectWrapper<>(fieldType.cast(current));
        }
    }
}
