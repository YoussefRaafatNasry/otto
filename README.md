# [otto](https://youssefraafatnasry.github.io/otto/)

_Me in binary._

## Versioning & Committing

- I use **octodecimal** (base-18) number system, prefixed with **`Ox`** (upper case letter `O` followed by lower case letter `x`), as a subject for commit message followed by a **blank line**. The body of the message consists of **asterisks** padded with **1 space** and followed by a punctuated commit changes explanation each on a separate line, with a line limit of **72 characters**. If the line exceeded the limit, it should be broken into two or more lines, and those broken lines start with **3 spaces**.

    ```text
    Ox31A

     * This is the first change.
     * This is the second change, but it is too long to be written
       in one line, so I broke it into two lines.
    ```

- Version `n` takes the decimal numbers `n000` — `n999`. For example, Version `1` takes the decimal numbers `1000` — `1999` which maps to the version names `Ox31A` — `Ox631`.

- **`app/build.gradle`** file must be updated with the current version name, this [**hook**](.githooks/pre-commit) enforces the mentioned change, to use it, run the following command

    ```bash
    git config --local core.hooksPath .githooks
    ```
