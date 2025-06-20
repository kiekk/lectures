/**
 * 템플릿 리터럴 타입
 */

type Color = "red" | "black" | "green";
type Animal = "dog" | "cat" | "chicken";

// Bad ❌
// type ColoredAnimal1 = `red-dog` | 'red-cat' | 'red-chicken' | 'black-dog' | ...

// Good ✅
type ColoredAnimal2 = `${Color}-${Animal}`;