package ftp_server.server;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.Path;

abstract class Connection implements Runnable {

    private enum Operation {
        SEND, RECEIVE
    }

    private static final Integer BYTE_BUFFER_SIZE = 0x4000;

    private DataTransferProcess serverDTP;
    TransferParameters parameters;
    SocketChannel channel;
    private Path filePath;

    private Operation operation;

    private Thread thread = null;
    private final Object lock = new Object();
    private Boolean notified = false;

    Connection(DataTransferProcess serverDTP, TransferParameters parameters) {
        this.serverDTP = serverDTP;
        this.parameters = parameters;
        start();
    }

    public abstract void negotiate() throws IOException;
    protected abstract void destroy();

    public void receiveFile(Path path) {
        filePath = path;
        operation = Operation.RECEIVE;
        notifyLock();
    }

    private void read() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocateDirect(BYTE_BUFFER_SIZE);
        if(!Files.exists(filePath)) {
            Files.createFile(filePath);
        }
        FileChannel fileChannel = new FileOutputStream(filePath.toFile()).getChannel();

        while(channel.read(buffer) >= 1) {
            buffer.flip();
            while(buffer.hasRemaining()) {
                fileChannel.write(buffer);
            }
            buffer.clear();
        }

        fileChannel.close();
    }

    public void sendFile(Path path) {
        filePath = path;
        operation = Operation.SEND;
        notifyLock();
    }

    private void write() throws IOException {
        if(parameters.getType() == TypeEnum.ASCII) {
            writeAscii();
        } else {
            writeImage();
        }
    }

    private void writeAscii() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocateDirect(BYTE_BUFFER_SIZE);
        BufferedReader reader = new BufferedReader(new StringReader(new String(Files.readAllBytes(filePath), "ASCII")));

        String currentLine;

        while ((currentLine = reader.readLine()) != null) {
            buffer.clear();
            buffer.put((currentLine + "\r\n").getBytes());
            buffer.flip();
            channel.write(buffer);
        }

        reader.close();
    }

    private void writeImage() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocateDirect(BYTE_BUFFER_SIZE);
        FileChannel fileChannel = new FileInputStream(filePath.toFile()).getChannel();

        while(true) {
            buffer.clear();
            if(fileChannel.read(buffer) < 1) {
                break;
            }
            buffer.flip();
            while(buffer.hasRemaining()) {
                channel.write(buffer);
            }
        }

        fileChannel.close();
    }

    public void run() {
        try {
            synchronized(lock)
            {
                if(!notified)
                {
                    lock.wait();
                }
            }

            if(Operation.RECEIVE == operation) {
                read();
            } else {
                write();
            }
            serverDTP.handleCompleted(true);
        }
        catch (InterruptedException | IOException exception) {
            serverDTP.handleCompleted(false);
        }
        finally {
            stop();
        }
    }

    public void start() {
        if(thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    public void stop() {
        destroy();

        if(channel != null) {
            try {
                channel.close();
            }
            catch (IOException ignored) { }
        }

        if(thread != null) {
            thread.interrupt();
            thread = null;
        }
    }

    private void notifyLock() {
        synchronized (lock) {
            lock.notify();
            notified = true;
        }
    }
}
