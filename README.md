# lein-s3-uberjar-release

[![Clojars Project](http://clojars.org/lein-s3-uberjar-release/latest-version.svg)](http://clojars.org/lein-s3-uberjar-release)

Lein plugin to upload an uberjar to s3.

## Configuration

```clojure
:plugins [[lein-s3-uberjar-release "0.1.0"]]
:s3-uberjar-release {:bucket "deploys.domain.com"
                     :prefix "/jars/project/%s" ; %s is current git hash
                     :filename "project-standalone.jar"}
```

Ensure you have `aws` client (use your package manager). And set you AWS credentials by doing `aws --configure`.

## Use

```
lein s3-uberjar-release
```

This will build an uberjar and upload it along with its sha512 to the location desired.

## License

MIT in `LICENSE` file.
