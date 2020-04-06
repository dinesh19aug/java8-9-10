package com.dinesh.vo;

import lombok.Getter;

public class Apple
{
    @Getter private int weight;
    @Getter private Color color;

    public enum Color{
        GREEN,
        RED;
    }

    public Apple(int weight,
                 Color color)
    {
        this.weight = weight;
        this.color = color;
    }

    @Override
    public String toString(){
        return new StringBuilder().append("Weight: ").append(getWeight()).append("| color: ").append(getColor()).toString();
    }
}
