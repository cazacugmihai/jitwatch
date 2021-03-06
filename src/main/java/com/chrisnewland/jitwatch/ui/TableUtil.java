/*
 * Copyright (c) 2013, 2014 Chris Newland.
 * Licensed under https://github.com/AdoptOpenJDK/jitwatch/blob/master/LICENSE-BSD
 * Instructions: https://github.com/AdoptOpenJDK/jitwatch/wiki
 */
package com.chrisnewland.jitwatch.ui;

import com.chrisnewland.jitwatch.toplist.ITopListScore;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableUtil
{
    public static TableView<ITopListScore> buildTableTopListScore(ObservableList<ITopListScore> scores)
    {
        TableView<ITopListScore> tv = new TableView<>();

        TableColumn<ITopListScore, Long> colScore = new TableColumn<ITopListScore, Long>("Value");
        colScore.setCellValueFactory(new PropertyValueFactory<ITopListScore, Long>("score"));
        colScore.prefWidthProperty().bind(tv.widthProperty().divide(8));

        TableColumn<ITopListScore, Object> colKey = new TableColumn<ITopListScore, Object>("Item");
        colKey.setCellValueFactory(new PropertyValueFactory<ITopListScore, Object>("key"));
        colKey.prefWidthProperty().bind(tv.widthProperty().divide(8).multiply(7));

        tv.getColumns().add(colScore);
        tv.getColumns().add(colKey);

        tv.setItems(scores);

        return tv;
    }

    public static TableView<AttributeTableRow> buildTableMemberAttributes(ObservableList<AttributeTableRow> rows)
    {
        TableView<AttributeTableRow> tv = new TableView<>();

        TableColumn<AttributeTableRow, String> colType = new TableColumn<AttributeTableRow, String>("Type");
        colType.setCellValueFactory(new PropertyValueFactory<AttributeTableRow, String>("type"));
        colType.prefWidthProperty().bind(tv.widthProperty().divide(3));

        TableColumn<AttributeTableRow, String> colName = new TableColumn<AttributeTableRow, String>("Name");
        colName.setCellValueFactory(new PropertyValueFactory<AttributeTableRow, String>("name"));
        colName.prefWidthProperty().bind(tv.widthProperty().divide(3));

        TableColumn<AttributeTableRow, String> colValue = new TableColumn<AttributeTableRow, String>("Value");
        colValue.setCellValueFactory(new PropertyValueFactory<AttributeTableRow, String>("value"));
        colValue.prefWidthProperty().bind(tv.widthProperty().divide(3));

        tv.getColumns().add(colType);
        tv.getColumns().add(colName);
        tv.getColumns().add(colValue);

        tv.setItems(rows);

        return tv;
    }

    public static TableView<StatsTableRow> buildTableStats(ObservableList<StatsTableRow> rows)
    {
        TableView<StatsTableRow> tv = new TableView<>();

        TableColumn<StatsTableRow, String> colName = new TableColumn<StatsTableRow, String>("Name");
        colName.setCellValueFactory(new PropertyValueFactory<StatsTableRow, String>("name"));
        colName.prefWidthProperty().bind(tv.widthProperty().multiply(0.33));

        TableColumn<StatsTableRow, Long> colValue = new TableColumn<StatsTableRow, Long>("Value");
        colValue.setCellValueFactory(new PropertyValueFactory<StatsTableRow, Long>("value"));
        colValue.prefWidthProperty().bind(tv.widthProperty().multiply(0.66));

        tv.getColumns().add(colName);
        tv.getColumns().add(colValue);

        tv.setItems(rows);

        return tv;
    }
}