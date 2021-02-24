# Create and Verify JWT on Java

You can do this using two approaches:
- first, using the [jjwt](https://mvnrepository.com/artifact/io.jsonwebtoken) library
- second, make it manual (without third party libraries)

to make privatekey and publickey please follow the following method
- create private key
```sh
openssl genrsa -out privatekey.pem 2048
```
- create public key
```sh
openssl req -new -x509 -key privatekey.pem -out publickey.cer
```
- create private key .der file from private key
```sh
openssl pkcs8 -topk8 -inform PEM -outform DER -in privatekey.pem -out privatekey.der -nocrypt
```
- create public key .der from private key 
```sh
openssl rsa -in privatekey.pem -pubout -outform DER -out publickey.der
```

`privatekey.der` will be used to create a signature.

`publickey.der` is used for the verify process.

**save the key file in the `resources` folder, and run the application!**
