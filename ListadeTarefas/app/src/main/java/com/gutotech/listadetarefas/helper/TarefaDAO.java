package com.gutotech.listadetarefas.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gutotech.listadetarefas.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class TarefaDAO implements ITarefaDAO {
    private SQLiteDatabase escreve;
    private SQLiteDatabase le;


    public TarefaDAO(Context context) {
        DBHelper db = new DBHelper(context);
        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Tarefa tarefa) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("nome", tarefa.getNomeTarefa());
        try {
            escreve.insert(DBHelper.TABELA_TAREFAS, null, contentValues);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public boolean atualizar(Tarefa tarefa) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("nome", tarefa.getNomeTarefa());

        try {
            String[] args = {String.valueOf(tarefa.getId())};
            escreve.update(DBHelper.TABELA_TAREFAS, contentValues, "id=?", args);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public boolean deletar(Tarefa tarefa) {

        try {
            String[] args = {String.valueOf(tarefa.getId())};
            escreve.delete(DBHelper.TABELA_TAREFAS, "id=?", args);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public List<Tarefa> listar() {
        List<Tarefa> tarefas = new ArrayList<>();

        String sql = "SELECT * FROM " + DBHelper.TABELA_TAREFAS + " ;";
        Cursor cursor = le.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            Tarefa tarefa = new Tarefa();

            Long id = cursor.getLong(cursor.getColumnIndex("id"));
            String nomeTarefa = cursor.getString(cursor.getColumnIndex("nome"));

            tarefa.setId(id);
            tarefa.setNomeTarefa(nomeTarefa);

            tarefas.add(tarefa);
        }

        return tarefas;
    }
}
