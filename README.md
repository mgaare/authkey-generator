# authkey-generator

A simple web API that generates cube2/sauerbraten authkey pairs.

## Usage

Start up using `lein run` or generating a jar with `lein uberjar` and
running with `java -jar`.

In a web browser, navigate to `localhost:8080` and you'll see a
generated auth keypair.

## Configuration

The port and the interface bindings are configurable by setting
environment variables.

- `AG_PORT` sets the port, default "8080"

- `AG_HOST` sets the interface/IP binding, default "localhost"

## License

Copyright © 2016 Michael Gaare

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
