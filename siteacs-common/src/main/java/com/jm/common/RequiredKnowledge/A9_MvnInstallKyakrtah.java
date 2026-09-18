package com.jm.common.RequiredKnowledge;

public class A9_MvnInstallKyakrtah {
}
/*
Run output -
C:\Program Files\JetBrains\IntelliJ IDEA 2026.1.3\plugins\maven\lib\maven3\bin\mvn.cmd -Didea.version=2026.1.3 -Dmaven.ext.class.path=C:\Program Files\JetBrains\IntelliJ IDEA 2026.1.3\plugins\maven\lib\intellij.maven.rt\maven-event-listener.jar -Djansi.passthrough=true -Dstyle.color=always -Dmaven.repo.local=C:\Users\jayam\.m2\repository install -f pom.xml
[INFO] Scanning for projects...
        [INFO]
        [INFO] -----------------------< com.jm:siteacs-backend >-----------------------
        [INFO] Building siteacs-backend 1.0-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
        [INFO]
        [INFO] --- resources:3.3.1:resources (default-resources) @ siteacs-backend ---
        [INFO] Copying 0 resource from src\main\resources to target\classes
[INFO]
        [INFO] --- compiler:3.13.0:compile (default-compile) @ siteacs-backend ---
        [INFO] Recompiling the module because of changed source code.
[INFO] Compiling 1 source file with javac [debug target 21] to target\classes
        [INFO]
        [INFO] --- resources:3.3.1:testResources (default-testResources) @ siteacs-backend ---
        [INFO] skip non existing resourceDirectory C:\Users\jayam\IdeaProjects\siteacs-parent\siteacs-backend\src\test\resources
        [INFO]
        [INFO] --- compiler:3.13.0:testCompile (default-testCompile) @ siteacs-backend ---
        [INFO] Recompiling the module because of changed dependency.
        [INFO]
        [INFO] --- surefire:3.2.5:test (default-test) @ siteacs-backend ---
        Downloading from central: https://repo.maven.apache.org/maven2/org/apache/maven/surefire/maven-surefire-common/3.2.5/maven-surefire-common-3.2.5.jar
        Downloaded from central: https://repo.maven.apache.org/maven2/org/apache/maven/surefire/maven-surefire-common/3.2.5/maven-surefire-common-3.2.5.jar (308 kB at 665 kB/s)
        Downloading from central: https://repo.maven.apache.org/maven2/org/apache/maven/surefire/surefire-api/3.2.5/surefire-api-3.2.5.jar
        Downloading from central: https://repo.maven.apache.org/maven2/org/apache/maven/surefire/surefire-logger-api/3.2.5/surefire-logger-api-3.2.5.jar
        Downloading from central: https://repo.maven.apache.org/maven2/org/apache/maven/surefire/surefire-extensions-api/3.2.5/surefire-extensions-api-3.2.5.jar
        Downloading from central: https://repo.maven.apache.org/maven2/org/apache/maven/surefire/surefire-extensions-spi/3.2.5/surefire-extensions-spi-3.2.5.jar
        Downloading from central: https://repo.maven.apache.org/maven2/org/apache/maven/surefire/surefire-booter/3.2.5/surefire-booter-3.2.5.jar
        Downloaded from central: https://repo.maven.apache.org/maven2/org/apache/maven/surefire/surefire-api/3.2.5/surefire-api-3.2.5.jar (171 kB at 2.4 MB/s)
        Downloading from central: https://repo.maven.apache.org/maven2/org/eclipse/aether/aether-util/1.0.0.v20140518/aether-util-1.0.0.v20140518.jar
        Downloaded from central: https://repo.maven.apache.org/maven2/org/apache/maven/surefire/surefire-logger-api/3.2.5/surefire-logger-api-3.2.5.jar (14 kB at 112 kB/s)
        Downloading from central: https://repo.maven.apache.org/maven2/org/eclipse/aether/aether-api/1.0.0.v20140518/aether-api-1.0.0.v20140518.jar
        Downloaded from central: https://repo.maven.apache.org/maven2/org/apache/maven/surefire/surefire-extensions-spi/3.2.5/surefire-extensions-spi-3.2.5.jar (8.2 kB at 64 kB/s)
        Downloading from central: https://repo.maven.apache.org/maven2/org/apache/maven/shared/maven-common-artifact-filters/3.1.1/maven-common-artifact-filters-3.1.1.jar
        Downloaded from central: https://repo.maven.apache.org/maven2/org/eclipse/aether/aether-util/1.0.0.v20140518/aether-util-1.0.0.v20140518.jar (146 kB at 1.1 MB/s)
        Downloading from central: https://repo.maven.apache.org/maven2/commons-io/commons-io/2.15.1/commons-io-2.15.1.jar
        Downloaded from central: https://repo.maven.apache.org/maven2/org/apache/maven/surefire/surefire-extensions-api/3.2.5/surefire-extensions-api-3.2.5.jar (26 kB at 169 kB/s)
        Downloading from central: https://repo.maven.apache.org/maven2/org/apache/maven/surefire/surefire-shared-utils/3.2.5/surefire-shared-utils-3.2.5.jar
        Downloaded from central: https://repo.maven.apache.org/maven2/org/apache/maven/shared/maven-common-artifact-filters/3.1.1/maven-common-artifact-filters-3.1.1.jar (61 kB at 325 kB/s)
        Downloaded from central: https://repo.maven.apache.org/maven2/org/apache/maven/surefire/surefire-booter/3.2.5/surefire-booter-3.2.5.jar (118 kB at 498 kB/s)
        Downloaded from central: https://repo.maven.apache.org/maven2/commons-io/commons-io/2.15.1/commons-io-2.15.1.jar (501 kB at 2.1 MB/s)
        Downloaded from central: https://repo.maven.apache.org/maven2/org/eclipse/aether/aether-api/1.0.0.v20140518/aether-api-1.0.0.v20140518.jar (136 kB at 550 kB/s)
        Downloaded from central: https://repo.maven.apache.org/maven2/org/apache/maven/surefire/surefire-shared-utils/3.2.5/surefire-shared-utils-3.2.5.jar (2.4 MB at 6.0 MB/s)
        [INFO]
        [INFO] --- jar:3.4.1:jar (default-jar) @ siteacs-backend ---
        [INFO] Building jar: C:\Users\jayam\IdeaProjects\siteacs-parent\siteacs-backend\target\siteacs-backend-1.0-SNAPSHOT.jar
        [INFO]
        [INFO] --- install:3.1.2:install (default-install) @ siteacs-backend ---
        Downloading from central: https://repo.maven.apache.org/maven2/org/apache/maven/resolver/maven-resolver-util/1.9.18/maven-resolver-util-1.9.18.jar
        Downloaded from central: https://repo.maven.apache.org/maven2/org/apache/maven/resolver/maven-resolver-util/1.9.18/maven-resolver-util-1.9.18.jar (196 kB at 3.2 MB/s)
        Downloading from central: https://repo.maven.apache.org/maven2/org/apache/maven/resolver/maven-resolver-api/1.9.18/maven-resolver-api-1.9.18.jar
        Downloaded from central: https://repo.maven.apache.org/maven2/org/apache/maven/resolver/maven-resolver-api/1.9.18/maven-resolver-api-1.9.18.jar (157 kB at 3.0 MB/s)
        [INFO] Installing C:\Users\jayam\IdeaProjects\siteacs-parent\siteacs-backend\pom.xml to C:\Users\jayam\.m2\repository\com\jm\siteacs-backend\1.0-SNAPSHOT\siteacs-backend-1.0-SNAPSHOT.pom
        [INFO] Installing C:\Users\jayam\IdeaProjects\siteacs-parent\siteacs-backend\target\siteacs-backend-1.0-SNAPSHOT.jar to C:\Users\jayam\.m2\repository\com\jm\siteacs-backend\1.0-SNAPSHOT\siteacs-backend-1.0-SNAPSHOT.jar
        [INFO] ------------------------------------------------------------------------
        [INFO] BUILD SUCCESS
        [INFO] ------------------------------------------------------------------------
        [INFO] Total time:  4.022 s
        [INFO] Finished at: 2026-09-02T19:21:52+05:30
        [INFO] ------------------------------------------------------------------------
*/


/*
Har Step Ko Samajhते Hain
1. (default-compile), (default-testCompile)

Ye Maven ka standard lifecycle hai — jab bhi mvn install chalate ho, Maven hamesha ye phases chalाता hai:

compile → aapka .java code, .class files mein convert hota hai
        testCompile → test files (agar hon) compile hoती hain

Chahe aapne koi business-logic likhi ho ya nahi, khaali project mein bhi ye phases chalते hain — bas kuch compile karne ko nahi hoga, phir bhi phase "run" hota hai (empty result ke saath).

        2. (default-resources), (default-testResources)

Ye src/main/resources aur src/test/resources folders ko copy karta hai target/ folder mein. Agar aapne application.properties bhi nahi banayi, ye phase phir bhi chalega — bas kuch copy karne ko nahi milega.

3. surefire:3.2.5:test (default-test)

Ye Maven ka test-runner plugin hai — automatically Spring Boot parent POM se aata hai (aapने specifically add nahi kiya, lekin spring-boot-starter-parent ise already include karta hai). Isका kaam hai src/test/java mein jo bhi test classes hon, unko run karna. Agar koi test hi nahi likhi, ye "0 tests run" bol ke aage badh jaayega.

4. Downloading from central: many then common-io jar

Ye interesting hai — commons-io jaisi library kisी na kisी Spring Boot starter ki transitive dependency hai (matlab aapne seedha add nahi kiya, lekin jo bhi starter aapने liya, uski andar-khaने wali dependency hai commons-io pe depend karti hai). Maven automatically poori dependency-tree resolve karta hai, isliye ye download ho rahi hai.

5. (default-install) — .m2 Mein JAR/POM Copy Hona

Ye sabse important part hai samajhne ke liye:

Downloading from central...
then pom to .m2\repository\com\jm\siteacs-backend\1.0-SNAPSHOT\siteacs-backend-1.0-SNAPSHOT.pom
then target\...\siteacs-backend-1.0-SNAPSHOT.jar to .m2\repository\...\siteacs-backend-1.0-SNAPSHOT.jar

Ye bilkul wahi cheez hai jo humne pehle discuss ki thi — jab aap mvn install chalate ho, Maven aapke module ki JAR file (aur pom.xml bhi) ko aapke local repository (.m2 folder) mein daal deta hai.

Isliye zaroori hai: Kyunki siteacs-common (ya jo bhi module tha) ko doosre modules (jaise siteacs-backend) ko dependency ki tarah use karna hai — aur Maven dependencies .m2 repository se hi uthाता hai, seedha folder se nahi. Isliye install phase zaroori hai, na ki sirf compile/package.

        6. BUILD SUCCESS*/
