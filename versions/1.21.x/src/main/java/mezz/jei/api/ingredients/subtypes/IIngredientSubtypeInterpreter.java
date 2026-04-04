package mezz.jei.api.ingredients.subtypes;

public interface IIngredientSubtypeInterpreter<T> {
    String apply(T ingredient, UidContext context);
}
