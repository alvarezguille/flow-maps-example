package org.vaadin.example;

import com.vaadin.componentfactory.maps.Map;
import com.vaadin.componentfactory.maps.model.Configuration;
import com.vaadin.componentfactory.maps.model.DataLabels;
import com.vaadin.componentfactory.maps.model.MapDataSeries;
import com.vaadin.componentfactory.maps.model.MapDataSeriesItem;
import com.vaadin.componentfactory.maps.model.PlotOptionsMap;
import com.vaadin.componentfactory.maps.model.Series;
import com.vaadin.componentfactory.maps.model.Tooltip;
import com.vaadin.componentfactory.maps.model.style.FontWeight;
import com.vaadin.componentfactory.maps.model.style.SolidColor;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and use @Route
 * annotation to announce it in a URL as a Spring managed bean.
 * <p>
 * A new instance of this class is created for every new user and every browser
 * tab/window.
 * <p>
 * The main view contains a text field for getting the user name and a button
 * that shows a greeting message in a notification.
 */
@Route
@JavaScript("./js/maps/europe-topo.js")
public class MainView extends VerticalLayout {

    /**
     * Construct a new Vaadin view.
     * <p>
     * Build the initial UI state for the user accessing the application.
     *
     * @param service
     *                The message service. Automatically injected Spring managed
     *                bean.
     */
    public MainView() {
        add(createMap());
    }

    private Map createMap() {
        Map map = new Map();
        map.setSizeFull();
        Configuration config = map.getConfiguration();
        config.setTitle("Europe time zones");
        config.getChart().setMap("custom/europe-topo");
        config.getChart().setSpacingBottom(20);
        config.getChart().setHeight(600);

        config.getLegend().setEnabled(true);
        config.getCredits().setEnabled(false);

        PlotOptionsMap options = new PlotOptionsMap();
        options.setAllAreas(false);
        config.setPlotOptions(options);

        DataLabels labels = options.getDataLabels();
        labels.setEnabled(true);
        labels.getStyle().setFontWeight(FontWeight.BOLD);
        labels.setColor(SolidColor.WHITE);
        labels.setFormat("{#if (lt point.properties.labelrank 5)} {point.properties.iso-a2} {/if}");

        Tooltip tooltip = config.getTooltip();
        tooltip.setHeaderFormat("");
        tooltip.setPointFormat("{point.name}: <b>{series.name}</b>");

        config.setSeries(getMapSeriesList());
        return map;
    }

    private List<Series> getMapSeriesList() {
        ArrayList<Series> seriesList = new ArrayList<>();
        MapDataSeries series;

        series = new MapDataSeries("UTC");
        for (String code : Arrays.asList("IE", "IS", "GB", "PT")) {
            series.add(new MapDataSeriesItem(code, 1));
        }
        series.setJoinBy("iso-a2", "code");
        seriesList.add(series);

        series = new MapDataSeries("UTC + 1");
        for (String code : Arrays.asList(
                "NO", "SE", "DK", "DE", "NL", "BE", "LU", "ES", "FR", "PL",
                "CZ", "AT", "CH", "LI", "SK", "HU", "SI", "IT", "SM", "HR",
                "BA", "YF", "ME", "AL", "MK")) {
            series.add(new MapDataSeriesItem(code, 1));
        }
        series.setJoinBy("iso-a2", "code");
        seriesList.add(series);

        series = new MapDataSeries("UTC + 2");
        for (String code : Arrays.asList(
                "FI", "EE", "LV", "LT", "BY", "UA", "MD", "RO", "BG", "GR",
                "TR", "CY")) {
            series.add(new MapDataSeriesItem(code, 1));
        }
        series.setJoinBy("iso-a2", "code");
        seriesList.add(series);

        series = new MapDataSeries("UTC + 3");
        series.add(new MapDataSeriesItem("RU", 1));
        series.setJoinBy("iso-a2", "code");
        seriesList.add(series);

        return seriesList;
    }
}
