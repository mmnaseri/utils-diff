# Introduction

This library offers a simple way to compare to lists against each other, and
derive a list of changes that can convert one to the other.

As an example, consider the two following lists:

    ["123", "456", "789"]

and

    ["123", "446", "567", "789"]

One way to convert the first to the second would be:

1. Delete the item at index `1`
2. Insert `"446"` at index `1`
3. Insert `"567"` at index `2`

which indeed does yield the second list. Another way to do this, would be to
take the following actions, instead:

1. Modify the item at index `1` to `"446"`
2. Insert `"567"` at index `1`

Which of these is the better option is debatable, but both are possible
scenarios. There are many, many other ways to derive the second list from
the first (take for instance the obvious way of deleting everything from
the first and inserting everything from the second).

This library will let you configure your preferences, and evaluate your options
when it comes to comparing two lists.

# JavaDoc

This library is fully documented with JavaDoc to help you navigate the code. Take
advantage of the documentation available to you, and file a bug whenever you think
the documentation is insufficient.

# Components

This library has divided its task into multiple components to allow for an easy
and maintainable configuration to all the necessary steps.

## Domain Objects

The domain objects include:

### Sequence

A sequence is anything that has a length, and whose items can be randomly accessed.
A sample sequence would be a sequence of characters (i.e. a `String` object) or a
sequence of binary digits (e.g. an `Integer`).

Sequences can be compared to see if their values are relatively close, or if they
are sufficiently different to merit a wholesale replacement.

### Item

An item is anything that can be seen as a [#Sequence]. This domain object has been
introduced to allow for exposing other things as items as well, and to not limit the
use of this library to things that are immediately convertible to sequences. For instance,
one might write code to expose a big decimal number as a sequence to allow for meticulous
comparison.

To wrap an object in an item, one can write:

    Item.of(1, 2, 3);

which will create an item with a sequence equivalent to `<1,2,3>`.

To create a list of items from an array of items, one can write:

    Item.asList(
        new int[]{1, 2 ,3},
        new int[]{4, 5, 6},
        new int[]{7, 8, 9}
    );
    
#### Special Treatment for CharSequence Objects

Since `CharSequence` objects are already sequence of characters, they can be exposed as
`Sequence<Character>`, so, their corresponding items will be of type `Item<Character>`.
Therefore, one can bind a string to items this way:

    Item<Character> i = Item.of(string.chars().mapToObj(c -> (char) c).toArray(Character[]::new));

However, since strings are popular and this is a mouthful, and besides, disassemling and
reassembling a string is senseless (since `CharSequence` already does provide all the
necessary functionality of a sequence), there is a light, wrapper sequence for this very
purpose: `CharacterSequence`. Therefore, you can just do:

    Item<Character> i = Item.of(string);

and:

    Item.asList(
        "hello",
        "world"
    );

### Change

A change is an operation that if performed on a list of items, will affect exactly one 
item of the list by applying an edit operation. Ideally, a change should bring a source
list one step closer to the desired target list.

### EditOperation

An edit operation can be one of the following:

- Insert: an item is inserted into the list
- Modify: an item in the list will be modified, but its index will remain unchanged
- Detete: an item in the list will be removed

## APIs

This library offers APIs that are meant to facilitate the calculation of a changelist based
on an input and an output.

### ItemComparison

Item comparison is the decision made when looking at two individual *items* side by side.

The comparison can be one of the following:

* Equal: the two items are identical.
* Edited: the source item can be modified slightly to get the target item
* Replaced: the source item and the target item are different enough to warrant
one being discarded in the favor of the other.

### ItemComparator

The item comparator is designed to abstract the item-to-item comparison that is performed when
comparing two lists against each other.

The default implementation, `DistanceItemComparator` uses the concept of "edit distance" between
two items (i.e. how costly is it to edit one item and get the other vs. just plain replace it) to
determine what item comparison should be determined.

### CostCalculator

The cost calculator will determine, in numeric terms, the relative cost of performing a proposed
change. This can be leveraged to great lengths to fine-tune the differentiation operation.

The default implementation, `OperationBasedCostCalculator`, can be used to associate a static cost
to edit operations, regardless of the other details of the change (modified value, index, etc.).

### ChangeCalculationConfiguration

This configuration wraps the details of cost calculation and item comparison to allow for a reusable
configuration for performing change list calculations.

You can either implement the relatively simplistic interface yourself, or use the builder class
provided out-of-the-box with this library (`ChangeCalculationConfigurationBuilder`) like this:

    configuration = usingItemComparator(...)
    .andCostCalculator(...)
    .build();

### ChangeCalculator

The change calculator is the central piece of this framework, by providing the following method:

    <V, E extends Item<V>> List<Change<V, E>> calculate(
        ChangeCalculationConfiguration<V, E> configuration,
        List<E> source, List<E> target);

which takes a configuration object, and using the specifics defined therein, will calculate the
changes that can convert the source list to the target list.

The following implementations are provided to you by this framework:

* `DumbChangeCalculator`: This change calculator will determine that the best course of action
is to wipe the source list and insert everything in the target list.
* `BruteForceChangeCalculator`: This change calculator will try to determine the least costly way
of changing the source list to the target list by way of calculating all possible changes and opting
for the best.
* `TopDownChangeCalculator`: This is a modification on the brute force version that uses the principles
of dynamic programming to cache the results of repetitive actions and use them for subsequent decisions.
This is often the best implementation to use, and the cache is not prohibitively large.
* `BottomUpChangeCalculator`: This implementation modifies the top-down approach to eliminate recursion
and instead relies on prepopulating the cache for future values. This is often not the best, as it usually
lags behind the top-down implementation by a small margin.

# DSL: Simple Comparison

The library comes equipped with a DSL designed to make it easier to perform comparisons
and differentiations in a more natural way:

Consider the following examples:

    final List<Change<Character, Item<Character>>> changes = forItemsOfType(Character.class)
            .usingItemComparator(new DistanceItemComparator<>(30))
            .andCostCalculator(new OperationBasedCostCalculator(1, 2, 3))
            .compare(source, target);

The above setup will:
* compare items using their edit distance (see above), considering them to be near if
less than `30%` of the source item needs to be modified,
* considers edit operation costs to be `1`, `2`, and `3`, for *delete*, *insert*, and *modify*
 respectively,
* and compares the two lists via the default change calculator (the top-down version).

Once the list of changes has been calculated, you can check its results using the following
simple piece of code:

    List<Item<Character>> list = source;
    for (Change<Character, Item<Character>> change : changes) {
        list = change.apply(list);
    }

By the end of this execution, `list` has to be equal to `target`.

## Grammar

The DSL, rooted at `ChangeCalculationDsl` has a simple grammar:

    start -> itemType configuration
    start -> config comparison
    config -> `usingConfiguration`
    itemType -> `forItemsOfType`
    configuration -> `usingItemComparator` costConfig
    costConfig -> `andCostCalculator` configurator
    configurator -> `configure`
    configrator -> comparison
    comparison -> `compare`
    
Or, more simply, there are two ways to start in on the DSL:

1. By providing a configuration:
    
        usingConfiguration(config). ...
        
    by which point you can perform a comparison as outlined below.

2. By providing an item type and starting the configuration:

        forItemsOfType(type)
        .usingItemComparator(itemComparator)
        .andCostCalculator(costCalculator)
    
    by which point you can either call `.configure();` to get an object of type
    `CostCalculationConfiguration` and use it later (and maybe even keep around for reusability)
    or perform a comparison as outlined below.

And to perform a comparison, you would either call:

    .compare(source, target);

to compare the two lists using the default calculator, or:

    .compare(calculator, source, target);

to compare the two lists using the calculator you provide.

