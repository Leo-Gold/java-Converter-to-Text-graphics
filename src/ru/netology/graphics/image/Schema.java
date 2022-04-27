package ru.netology.graphics.image;

public class Schema implements TextColorSchema{
    private char[] variableForMac = {'▇', '●', '◉', '◍', '◎', '○', '☉', '◌', '-'};
    private char[] variableForWindows = { '#', '$', '@', '%', '*', '+', '-', '\''};

    @Override
    public char convert(int color) {
        char[] arr = variableForWindows;
        int ratio = 255 / arr.length;
        int index = (int) (color / ratio);
        return arr[index];
    }
}
