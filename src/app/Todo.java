package app;

public class Todo {
    private static int lastIndex = 0;
    private int index;
    private String value;
    private boolean isChecked;

    public Todo(String val){
        index = lastIndex + 1;
        value = val;
        isChecked = false;
        lastIndex++;
    }

    public int getIndex(){
        return index;
    }

    public String getValue(){
        return value;
    }

    public boolean getIsChecked(){
        return isChecked;
    }

    public void toggleCheck(){
        isChecked = !isChecked;
    }
}
