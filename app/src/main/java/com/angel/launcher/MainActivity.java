package com.angel.launcher;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends Activity {
    private static final int BG = Color.rgb(8, 14, 27);
    private static final int PANEL = Color.rgb(16, 27, 47);
    private static final int PANEL2 = Color.rgb(21, 36, 61);
    private static final int BLUE = Color.rgb(40, 165, 255);
    private static final int MUTED = Color.rgb(151, 169, 194);
    private LinearLayout root, content, nav;
    private String page = "Home";
    private SharedPreferences prefs;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(BG);
        getWindow().setNavigationBarColor(BG);
        prefs = getSharedPreferences("angel_launcher", MODE_PRIVATE);
        buildShell();
        showPage("Home");
    }

    private GradientDrawable shape(int color, int radius) {
        GradientDrawable d = new GradientDrawable(); d.setColor(color); d.setCornerRadius(radius); return d;
    }
    private TextView text(String value, int size, int color, boolean bold) {
        TextView t = new TextView(this); t.setText(value); t.setTextSize(size); t.setTextColor(color);
        if (bold) t.setTypeface(Typeface.DEFAULT, Typeface.BOLD); t.setGravity(Gravity.CENTER_VERTICAL); return t;
    }
    private LinearLayout column() { LinearLayout l = new LinearLayout(this); l.setOrientation(LinearLayout.VERTICAL); return l; }
    private LinearLayout row() { LinearLayout l = new LinearLayout(this); l.setOrientation(LinearLayout.HORIZONTAL); l.setGravity(Gravity.CENTER_VERTICAL); return l; }
    private void pad(View v, int a, int b, int c, int d) { v.setPadding(a,b,c,d); }
    private LinearLayout.LayoutParams lp(int w, int h) { return new LinearLayout.LayoutParams(w,h); }
    private void gap(LinearLayout parent, int height) { View v = new View(this); parent.addView(v, lp(1,height)); }

    private void buildShell() {
        root = column(); root.setBackgroundColor(BG); setContentView(root);
        LinearLayout header = row(); pad(header,20,18,20,14);
        TextView brand = text("ANGEL",22,Color.WHITE,true); TextView sub = text("  LAUNCHER",12,BLUE,true);
        header.addView(brand); header.addView(sub); header.addView(new View(this), new LinearLayout.LayoutParams(0,1,1));
        TextView version = text("PREVIEW 0.1",10,MUTED,true); header.addView(version); root.addView(header);
        ScrollView scroll = new ScrollView(this); scroll.setFillViewport(true); content = column(); pad(content,18,8,18,24); scroll.addView(content);
        root.addView(scroll, new LinearLayout.LayoutParams(-1,0,1));
        nav = row(); nav.setBackgroundColor(Color.rgb(10,18,33)); pad(nav,8,8,8,12);
        String[] tabs = {"Home","Instances","Mods","Settings"};
        for (String tab : tabs) {
            TextView b = text(tab,12, tab.equals(page)?BLUE:MUTED,true); b.setGravity(Gravity.CENTER);
            b.setOnClickListener(v -> showPage(tab)); nav.addView(b,new LinearLayout.LayoutParams(0,52,1));
        }
        root.addView(nav);
    }
    private void refreshNav() {
        nav.removeAllViews(); String[] tabs={"Home","Instances","Mods","Settings"};
        for(String tab:tabs){ TextView b=text(tab,12,tab.equals(page)?BLUE:MUTED,true); b.setGravity(Gravity.CENTER); b.setOnClickListener(v->showPage(tab)); nav.addView(b,new LinearLayout.LayoutParams(0,52,1)); }
    }
    private void card(LinearLayout parent, String title, String body) {
        LinearLayout box=column(); box.setBackground(shape(PANEL,22)); pad(box,16,15,16,15);
        box.addView(text(title,16,Color.WHITE,true)); gap(box,7); box.addView(text(body,13,MUTED,false));
        LinearLayout.LayoutParams p=lp(-1,-2); p.bottomMargin=12; parent.addView(box,p);
    }
    private Button action(String label, boolean primary, Runnable run) {
        Button b=new Button(this); b.setText(label); b.setAllCaps(false); b.setTextSize(14); b.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        b.setTextColor(primary?Color.WHITE:BLUE); b.setBackground(shape(primary?BLUE:PANEL2,16)); b.setOnClickListener(v->run.run()); return b;
    }
    private void showPage(String next) {
        page=next; content.removeAllViews(); refreshNav();
        switch(next){
            case "Home": home(); break;
            case "Instances": instances(); break;
            case "Mods": mods(); break;
            case "Settings": settings(); break;
        }
    }
    private void home() {
        gap(content,8); content.addView(text("YOUR JAVA WORLD.\nYOUR WAY.",27,Color.WHITE,true)); gap(content,8);
        content.addView(text("A clean home for your Minecraft Java instances.",14,MUTED,false)); gap(content,20);
        LinearLayout hero=column(); hero.setBackground(shape(PANEL2,24)); pad(hero,18,20,18,18);
        hero.addView(text("ANGEL CLIENT",12,BLUE,true)); gap(hero,8); hero.addView(text("Ready when your setup is.",22,Color.WHITE,true)); gap(hero,6);
        hero.addView(text("The launcher interface is in place. The Minecraft runtime and account authentication still need to be integrated before Play can launch the game.",13,MUTED,false)); gap(hero,16);
        Button play=action("PLAY  ·  RUNTIME NOT CONNECTED",false,()->Toast.makeText(this,"Minecraft Java runtime is not integrated in this preview yet.",Toast.LENGTH_LONG).show()); play.setEnabled(false); play.setAlpha(.65f); hero.addView(play,lp(-1,-2));
        content.addView(hero); gap(content,16);
        card(content,"ACCOUNT","Microsoft sign-in is not connected in this preview. A real OAuth client and secure redirect configuration are required.");
        card(content,"MOD SUPPORT","Planned support: Fabric, Forge and NeoForge, with Modrinth discovery and per-instance mod management.");
        card(content,"COSMETICS","The design reserves space for original free cosmetics. No paid or server-exclusive cosmetics are implied.");
    }
    private void instances() {
        content.addView(text("INSTANCES",25,Color.WHITE,true)); gap(content,5); content.addView(text("Keep separate game versions and mod setups.",13,MUTED,false)); gap(content,16);
        content.addView(action("+  CREATE INSTANCE",true,()->createInstance()),lp(-1,52)); gap(content,16);
        Set<String> names=prefs.getStringSet("instances",new HashSet<>()); ArrayList<String> copy=new ArrayList<>(names);
        if(copy.isEmpty()) card(content,"NO INSTANCES YET","Create an instance to reserve a profile in this launcher preview.");
        for(String name:copy) card(content,name,"Saved local instance profile · Game runtime not attached");
    }
    private void createInstance() {
        EditText input=new EditText(this); input.setSingleLine(true); input.setHint("e.g. 1.21.4 Fabric"); input.setTextColor(Color.WHITE); input.setHintTextColor(MUTED);
        AlertDialog dialog=new AlertDialog.Builder(this).setTitle("New instance").setView(input).setNegativeButton("Cancel",null).setPositiveButton("Create",(d,w)->{
            String name=input.getText().toString().trim(); if(name.isEmpty()) return;
            Set<String> set=new HashSet<>(prefs.getStringSet("instances",new HashSet<>())); set.add(name); prefs.edit().putStringSet("instances",set).apply(); showPage("Instances");
        }).create(); dialog.show();
    }
    private void mods() {
        content.addView(text("MOD LIBRARY",25,Color.WHITE,true)); gap(content,6); content.addView(text("Manage mods by instance, not globally.",13,MUTED,false)); gap(content,16);
        card(content,"MODRINTH","Search, install, update and remove mods from Modrinth. Online catalogue integration is not connected in this preview.");
        card(content,"LOADERS","Fabric · Forge · NeoForge\nLoader installation and version compatibility checks are pending runtime integration.");
        card(content,"RESOURCE PACKS","A dedicated resource-pack manager is planned. Local file import is not implemented yet.");
    }
    private void settings() {
        content.addView(text("SETTINGS",25,Color.WHITE,true)); gap(content,6); content.addView(text("Angel Launcher preview settings.",13,MUTED,false)); gap(content,16);
        card(content,"DEVICE","Designed for Android phones and Android-enabled Chromebooks, including your Lenovo Chromebook.");
        card(content,"MICROSOFT ACCOUNT","Secure Microsoft authentication is not implemented yet. Do not enter your Microsoft password into this preview.");
        card(content,"ABOUT","Angel Launcher · 0.1.0 preview\nDark navy interface with electric-blue accents.");
    }
}
