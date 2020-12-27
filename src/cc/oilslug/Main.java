package cc.oilslug;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Enumeration;

public class Main {

    public static void main(String args[]) throws SocketException {
        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        String longHex = ":@:";
        for (NetworkInterface netint : Collections.list(nets))
            longHex += displayInterfaceInformation(netint);
        longHex += ":@:";
        try {
            System.out.println("HWID: " + getStringFromSHA256(longHex));
        }catch (Exception e){

        }
    }

    static String displayInterfaceInformation(NetworkInterface netint) throws SocketException {
        String value = "";
        value += ":" + netint.getDisplayName() + ":";
        value += ":" + netint.getName() + ":";
        Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
        for (InetAddress inetAddress : Collections.list(inetAddresses)) {
            value += ":" + inetAddress + ":";
        }
        return value;
    }
    private static final char[] hex = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    public static String byteArray2Hex(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length * 2);
        for(final byte b : bytes) {
            sb.append(hex[(b & 0xF0) >> 4]);
            sb.append(hex[b & 0x0F]);
        }
        return sb.toString();
    }

    public static String getStringFromSHA256(String stringToEncrypt) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(stringToEncrypt.getBytes());
        return byteArray2Hex(messageDigest.digest());
    }
}

