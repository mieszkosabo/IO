package com.example.ioapka;

import android.view.View;
import android.widget.TableRow;

public abstract class ClickListener implements View.OnClickListener {

    private Ingredient toDelete;
    private TableRow toDel;

    ClickListener(Ingredient toDelete, TableRow toDel) {
        this.toDel = toDel;
        this.toDelete = toDelete;
    }
}
