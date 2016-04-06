package com.github.gquintana.laapin.affichage;

import com.github.gquintana.laapin.joueur.Direction;
import com.github.gquintana.laapin.joueur.TypeAction;
import com.github.gquintana.laapin.moteur.Lapin;
import com.github.gquintana.laapin.moteur.ResultatAction;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.lang.reflect.Field;
import java.util.List;

public class StatsPanel extends VBox {
    private ObservableList<Lapin> lapinsList = FXCollections.observableArrayList();
    private ObservableList<ResultatAction> actionsList = FXCollections.observableArrayList();
    private final TableView<ResultatAction> actionsTable;
    private final TableView<Lapin> lapinsTable;

    public StatsPanel() {
        lapinsTable = new TableView<>(lapinsList);
        lapinsTable.getColumns().add(tableColumn("Lapin", "nom", String.class));
        lapinsTable.getColumns().add(tableColumn("Score", "score", Integer.class));

        actionsTable = new TableView<>(actionsList);
        actionsTable.getColumns().add(tableColumn("Lapin", "lapin.nom", String.class));
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
