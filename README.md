# LaetLang 💻

LaetLang is an interpreted C style language built by following along Robert Nystrom's book Crafting Interpreters.
This is a toy language that was meant as a learning exercise, please do not use it for any projects.

A few of the things that I decided to add that were not part of the interpreter covered in the book:


## Reading from a file:
```javascript
var textInput = fileR("./out.txt");
print "File content is: " + textInput;
```

## Writting to files:
```javascript
fileln("./out.txt", "Hello World.");
```

## Making a network call:
```javascript
var networkResponse = net("localhost", 2000, "TEST");
print "Server responded with: " + networkResponse;
```
The network call is a TCP write, so a language level library for http calls could be made.

## Async Network call:
```javascript
var promiseResponses = nil;

for(var i = 0; i < 10; i = i + 1) {
    var promiseResponse = asyncNet("localhost", 2000, listN(requestList, i));
    promiseResponses = prepend(promiseResponse, promiseResponses);
}

map(PromiseResolve, promiseResponses);
```
A very naive implementation of promises by using an eventDict to store threaded operation results,
which can then be retrieved by awaiting for the promise to resolve.
This means you can do multiple network requests at the same time, and then await for the results.

------------------------------------

Other things in the language that are straight from the book implementation of the interpreter:

## Defining a function:
```javascript
fun fib(n) {
    if (n <= 1) return n;
    return fib(n-2) + fib(n-1);
}
```

## FizzBuzz is messy because of no elif :(
```javascript
fun mod1(a, m, i) {
    if (m * i == a) {
        return 0;
    } else {
        if (m * i < a) {
            return mod1(a, m, i+1);
        } else {
            return a - m * (i - 1);
        }
    }
}

fun mod(a, b) {
    return mod1(a, b, 1);
}

for(var i = 0; i < 100; i = i + 1) {
    if (mod(i, 15) == 0) {
      print "Fizz Buzz";
    } else {
      if (mod(i, 3) == 0) {
        print "Fizz";
      } else {
        if (mod(i, 5) == 0) {
          print "Buzz";
        } else {
          print i;
        }
      }
    }
}
```
