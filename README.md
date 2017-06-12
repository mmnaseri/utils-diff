Diff Utils
==========

This utility library will let you take the difference of any two lists in the form of a series of
incremental changes.

For instance:

```java
final List<Change<Character, Item<Character>>> changes;
final List<Item<Character>> source = Item.asList("hello", "world!");
final List<Item<Character>> source = Item.asList("hello", "myself!");
changes = usingConfiguration(configuration).compare(source, target);
```

might lead to changes being equal to the two following actions:

1. Delete item at index `1`
2. Insert `"myself!"` at index `1`

which when applied sequentially to the source list will yield the target list:

```java
list = source;
for (Change<Character, Item<Character>> change : changes) {
    list = change.apply(list);
}
```

For more on how to use the framework and what things it can do, refer to
[the usage documentation](USAGE.md).
