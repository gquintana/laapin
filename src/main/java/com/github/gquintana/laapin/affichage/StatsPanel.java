package com.github.gquintana.laapin.affichage;

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
        TableColumn<Lapin, String> nomLapinColumn = new TableColumn<>("Lapin");
        nomLapinColumn.setCellValueFactory(new FieldValueFactory<>("nom"));
        TableColumn<Lapin, Integer> scoreLapinColumn = new TableColumn<>("Score");
        scoreLapinColumn.setCellValueFactory(new FieldValueFactory<>("score"));
        lapinsTable.getColumns().addAll(nomLapinColumn, scoreLapinColumn);

        actionsTable = new TableView<>(actionsList);
        TableColumn<ResultatAction, String> lapinActionColumn = new TableColumn<>("Lapin");
        lapinActionColumn.setCellValueFactory(new FieldValueFactory<>("lapin.nom"));
        TableColumn<ResultatAction, Integer> typeActionColumn = new TableColumn<>("Action");
        typeActionColumn.setCellValueFactory(new FieldValueFactory<>("action.type"));
        TableColumn<ResultatAction, Integer> directionActionColumn = new TableColumn<>("Direction");
        directionActionColumn.setCellValueFactory(new FieldValueFactory<>("action.direction"));
        TableColumn<ResultatAction, Integer> messageActionColumn = new TableColumn<>("Message");
        messageActionColumn.setCellValueFactory(new FieldValueFactory<>("message"));
        actionsTable.getColumns().addAll(lapinActionColumn, typeActionColumn, directionActionColumn, messageActionColumn);

        getChildren().addAll(lapinsTable, actionsTable);
    }

    public void setLapinsList(List<Lapin> lapins) {
        lapinsList.setAll(lapins);
    }

    public void addAction(ResultatAction resultatAction) {
        actionsList.add(resultatAction);
        actionsTable.scrollTo(actionsList.size() - 1);
    }

    private static class FieldValueFactory<S, T> implements Callback<TableColumn.CellDataFeatures<S,T>, ObservableValue<T>> {
        private final String[] fieldPath;

        public FieldValueFactory(String name) {
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
            return new ReadOnlyObjectWrapper<>((T) current);
        }
    }
}
