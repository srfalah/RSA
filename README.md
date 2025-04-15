* This is a simple RSA encryption that shows student how the Algorithms works.
* Used JDK 21 with Javafx23.0.1
* Used Ikonli for icons
* Used FlatIcon for the program logo
* Program Functionality:
  - You can generate 2 prime numbers
  - you can write 2 prime numbers and the program checks if they prime or not
  - n and Phi(n) will be generated
  - you can pick the value of e and it must be a co-prime with phi(n)
  - the value of d will be generated based on the value od e
  - you can generate the keys
  - you can write a message as number, the message M should be less than n
  - you can generate the cipher
  - you can decrypt the cipher

*
![Alt text](https://raw.githubusercontent.com/srfalah/RSA/master/src/main/resources/screenshots/screenshot-1.png)
*
![Alt text](https://raw.githubusercontent.com/srfalah/RSA/master/src/main/resources/screenshots/screenshot-2.png)
*
![Alt text](https://raw.githubusercontent.com/srfalah/RSA/master/src/main/resources/screenshots/screenshot-3.png)
*
![Alt text](https://raw.githubusercontent.com/srfalah/RSA/master/src/main/resources/screenshots/screenshot-4.png)





---

## 🛠 How to Use This Library in Your Maven Project

To use the `RSACryptography` library in your Maven project:

### 1️⃣ Add GitHub Packages repository to your `pom.xml`:

```xml
<repositories>
  <repository>
    <id>github</id>
    <url>https://maven.pkg.github.com/srfalah/rsa</url>
  </repository>
</repositories>

### 2️⃣ Add the dependency:
```xml
<dependency>
  <groupId>com.example</groupId>
  <artifactId>rsa</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>


