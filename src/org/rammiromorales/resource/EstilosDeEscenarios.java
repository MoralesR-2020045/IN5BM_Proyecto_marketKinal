/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rammiromorales.resource;

import javafx.animation.FadeTransition;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class EstilosDeEscenarios {
    private FadeTransition fadeTransition;

    public void anchorPaneInvisible(AnchorPane anchorPane) {
        fadeTransition = new FadeTransition(Duration.seconds(2), anchorPane);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setOnFinished(event -> anchorPane.setVisible(false));
        fadeTransition.play();
    }

    public void anchorPaneVisible(AnchorPane anchorPane) {
        fadeTransition = new FadeTransition(Duration.seconds(2), anchorPane);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setOnFinished(event -> anchorPane.setVisible(true));
        anchorPane.setVisible(true);
        fadeTransition.play();
    }
}
