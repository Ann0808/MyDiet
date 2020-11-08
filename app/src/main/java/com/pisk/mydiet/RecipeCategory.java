package com.pisk.mydiet;

public enum RecipeCategory {
    SALAD(1, "Салаты",R.drawable.salad_new),
    SOUP(2, "Супы",R.drawable.soup_new),
    SNACK(3, "Закуски",R.drawable.snack_new),
    HOT(4, "Горячие" +"\n" + "блюда",R.drawable.hot_new),
    DECERT(5, "Десерты",R.drawable.decert_new);
    private final int number;
    private final String categoryName;
    private final int categoryImage;
    RecipeCategory(int number, String categoryName, int categoryImage) {
        this.number = number;
        this.categoryName = categoryName;
        this.categoryImage = categoryImage;
    }
    int number() { return number; }
    String categoryName() { return categoryName; }
    int categoryImage() { return categoryImage; }
}
