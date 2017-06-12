Diff Utils
==========

This utility library will let you take the difference of any two lists in the form of a series of
incremental changes.

For instance:

```java
ChangeCalculator calculator = new TopDownChangeCalculator();
final List<Change<Character, Item<Character>>> changes;
final List<Item<Character>> source = Arrays.asList(
        Item.of("hello"),
        Item.of("world!")
);
final List<Item<Character>> source = Arrays.asList(
        Item.of("hello"),
        Item.of("myself!")
);
changes = changeCalculator.calculate(getConfiguration(), source, target);
```

might lead to changes being equal to the two following actions:

1. Delete item at index `1`
2. Insert `"myself!"` at index `1`

which when applied sequentially to the source list will yield the target list.

For more, refer to the usage doc.