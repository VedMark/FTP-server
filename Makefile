.SUFFIXES: .java .class

JFLAGS =
JC = /usr/lib/jvm/java-8-openjdk-amd64/bin/javac
JVM = /usr/lib/jvm/java-8-openjdk-amd64/bin/java
SRC = :src/main/java/:
RSR = :src/main/resources/:
LIBS = :/usr/local/apache-log4j-1.2.15/log4j-1.2.15.jar:

CLASSES = \
	src/main/java/ftp_server/Main.java \
        src/main/java/ftp_server/command/ABOR.java \
        src/main/java/ftp_server/command/ACCT.java \
        src/main/java/ftp_server/command/ALLO.java \
        src/main/java/ftp_server/command/APPE.java \
        src/main/java/ftp_server/command/CDUP.java \
        src/main/java/ftp_server/command/CWD.java \
        src/main/java/ftp_server/command/DELE.java \
        src/main/java/ftp_server/command/HELP.java \
        src/main/java/ftp_server/command/LIST.java \
        src/main/java/ftp_server/command/MKD.java \
        src/main/java/ftp_server/command/MODE.java \
        src/main/java/ftp_server/command/NLST.java \
        src/main/java/ftp_server/command/NOOP.java \
        src/main/java/ftp_server/command/PASS.java \
        src/main/java/ftp_server/command/PASV.java \
        src/main/java/ftp_server/command/PORT.java \
        src/main/java/ftp_server/command/PWD.java \
        src/main/java/ftp_server/command/QUIT.java \
        src/main/java/ftp_server/command/REIN.java \
        src/main/java/ftp_server/command/REST.java \
        src/main/java/ftp_server/command/RETR.java \
        src/main/java/ftp_server/command/RMD.java \
        src/main/java/ftp_server/command/RNFR.java \
        src/main/java/ftp_server/command/RNTO.java \
        src/main/java/ftp_server/command/SITE.java \
        src/main/java/ftp_server/command/SMNT.java \
        src/main/java/ftp_server/command/STAT.java \
        src/main/java/ftp_server/command/STOR.java \
        src/main/java/ftp_server/command/STOU.java \
        src/main/java/ftp_server/command/STRU.java \
        src/main/java/ftp_server/command/SYST.java \
        src/main/java/ftp_server/command/TYPE.java \
	src/main/java/ftp_server/command/UnexpectedCodeException.java \
        src/main/java/ftp_server/command/USER.java \
        src/main/java/ftp_server/reply/Reply.java \
	src/main/java/ftp_server/server/ActiveConnection.java \
        src/main/java/ftp_server/server/ConfigException.java \
	src/main/java/ftp_server/server/Form.java \
        src/main/java/ftp_server/server/FTPCommandController.java \
        src/main/java/ftp_server/server/FTPProperties.java \
        src/main/java/ftp_server/server/FTPServerDTP.java \
        src/main/java/ftp_server/server/FTPServer.java \
        src/main/java/ftp_server/server/FTPServerPI.java \
        src/main/java/ftp_server/server/FTPTransferParameters.java \
        src/main/java/ftp_server/server/Mode.java \
	src/main/java/ftp_server/server/PassiveConnection.java \
	src/main/java/ftp_server/server/ServerChannelException.java \
        src/main/java/ftp_server/server/Structure.java \
        src/main/java/ftp_server/server/Type.java \
	src/main/java/ftp_server/utils/FileSystem.java \
        src/main/java/ftp_server/view/FXServerApp.java \
        src/main/java/ftp_server/view/LogComponent.java \
        src/main/java/ftp_server/view/View.java \

.java.class:
	$(JC) $(JFLAGS) -cp "$(SRC)$(RSR)$(LIBS)" $*.java

default: classes

classes: $(CLASSES:.java=.class)

run:
	$(JVM) -cp "$(SRC)$(RSR)$(LIBS)" ftp_server.Main

clean:
	$(RM) src/main/java/ftp_server/command/*.class
	$(RM) src/main/java/ftp_server/reply/*.class
	$(RM) src/main/java/ftp_server/server/*.class
	$(RM) src/main/java/ftp_server/view/*.class
	$(RM) src/main/java/ftp_server/utils/*.class
	$(RM) src/main/java/ftp_server/*.class

