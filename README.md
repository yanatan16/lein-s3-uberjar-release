# lein-s3-uberjar-release

[![Clojars Project](http://clojars.org/com.rafflecopter/lein-s3-uberjar-release/latest-version.svg)](http://clojars.org/com.rafflecopter/lein-s3-uberjar-release)

Lein plugin to upload an uberjar to s3.

## Configuration

```clojure
:plugins [[lein-s3-uberjar-release "0.1.0"]]
:s3-uberjar-release {:bucket "deploys.rafflecopter.com"
                     :prefix "/jars/raflui/%s" ; %s is current git hash
                     :filename "raflui-standalone.jar"}
```

Ensure you have `s3cmd` (use your package manager). And set you AWS credentials by doing `s3cmd --configure`.

## Use

```
lein s3-uberjar-release
```

This will build an uberjar and upload it along with its sha512 to the location desired.

## License

MIT in `LICENSE` file.
