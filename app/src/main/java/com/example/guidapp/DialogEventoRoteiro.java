package com.example.guidapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

public class DialogEventoRoteiro implements DialogInterface.OnClickListener {

    private Roteiro parentActivity;
    private int idEvento;
    private View viewOrigem;

    DialogEventoRoteiro(Roteiro activity, View viewOrigem, int idEvento) {
        this.parentActivity = activity;
        this.idEvento = idEvento;
        this.viewOrigem = viewOrigem;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(which == 0) { // ver evento
            Intent intent = new Intent(parentActivity, DescricaoEvento.class);
            parentActivity.startActivity(intent);
        } else if(which == 1) { // remover do roteiro
            parentActivity.removerEvento(viewOrigem);
        } else { // voltar
            dialog.dismiss();
        }
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public Roteiro getParentActivity() {
        return parentActivity;
    }

    public void setParentActivity(Roteiro parentActivity) {
        this.parentActivity = parentActivity;
    }
}
