package com.example.organigramma.UserInterface;

import com.example.organigramma.Composite.CompoundUnit;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class OrgChartController {

    @FXML
    private TextField unitNameField;  // Campo per inserire nome unità
    @FXML
    private TextField unitLevelField; // Campo per inserire livello unità
    @FXML
    private Pane orgChartPane;        // Area grafica per disegnare l'organigramma

    private CompoundUnit rootUnit;         // L'unità radice dell'organigramma
    private List<CompoundUnit> orgUnits = new ArrayList<>();  // Lista di tutte le unità

    // Metodo chiamato quando si aggiunge una nuova unità
    @FXML
    private void addUnit() {
        String name = unitNameField.getText();
        int level = Integer.parseInt(unitLevelField.getText());

        CompoundUnit newUnit = new CompoundUnit(name, level);
        orgUnits.add(newUnit);

        // Se è il livello 0, è la root dell'organigramma
        if (level == 0) {
            rootUnit = newUnit;
        } else {
            // Aggiungi come sottounità dell'unità superiore più vicina
            CompoundUnit parentUnit = findParentUnit(level);
            if (parentUnit != null) {
                parentUnit.addSubUnit(newUnit);
            }
        }

        // Reset dei campi di input
        unitNameField.clear();
        unitLevelField.clear();
    }

    // Metodo per generare e visualizzare l'organigramma
    @FXML
    private void generateOrgChart() {
        orgChartPane.getChildren().clear();
        if (rootUnit != null) {
            drawOrgChart(rootUnit, 300, 50);
        }
    }

    // Disegna l'organigramma a partire dall'unità radice
    private void drawOrgChart(CompoundUnit unit, double x, double y) {
        Rectangle rect = new Rectangle(200, 50);
        rect.setFill(Color.LIGHTBLUE);
        rect.setStroke(Color.BLACK);

        Label unitLabel = new Label(unit.getName());
        StackPane unitBox = new StackPane();
        unitBox.getChildren().addAll(rect, unitLabel);
        unitBox.setLayoutX(x - rect.getWidth() / 2);
        unitBox.setLayoutY(y);

        orgChartPane.getChildren().add(unitBox);

        // Evento di clic per mostrare i dettagli dell'unità
        unitBox.setOnMouseClicked((MouseEvent event) -> {
            showUnitDetails(unit);
        });

        double childY = y + 100; // Posizionamento verticale delle sottounità
        double childXStart = x - (unit.getSubUnits().size() * 150) / 2; // Posizionamento orizzontale iniziale

        for (CompoundUnit child : unit.getSubUnits()) {
            Line line = new Line(x, y + rect.getHeight(), childXStart + 100, childY);
            orgChartPane.getChildren().add(line);

            drawOrgChart(child, childXStart + 100, childY);
            childXStart += 150;
        }
    }

    // Trova l'unità genitore per il livello corrente
    private CompoundUnit findParentUnit(int level) {
        for (CompoundUnit unit : orgUnits) {
            if (unit.getLevel() == level - 1) {
                return unit;
            }
        }
        return null;
    }

    // Mostra i dettagli dell'unità in un popup
    private void showUnitDetails(CompoundUnit unit) {
        StringBuilder details = new StringBuilder("Dettagli Unità: " + unit.getName() + "\n");
        details.append("Livello: " + unit.getLevel() + "\n");
        details.append("Sottounità:\n");

        for (CompoundUnit child : unit.getSubUnits()) {
            details.append("- " + child.getName() + "\n");
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Dettagli Unità");
        alert.setHeaderText("Informazioni sull'unità: " + unit.getName());
        alert.setContentText(details.toString());
        alert.showAndWait();
    }
}
