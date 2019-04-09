package com.example.gametracker;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

public class Helper {

    public static TextDrawable getDrawableName (String name){
        ColorGenerator generator = ColorGenerator.MATERIAL;
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(name.substring(0,1), generator.getRandomColor());
        return drawable;
    }
}
